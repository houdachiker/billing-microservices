# Billing microservices - base de depart

3 services Spring Boot independants (Java 17, Spring Boot 3.3, Maven, H2).
Pas d'Eureka ni de Gateway : a ajouter toi-meme.

| Service              | Port | Contenu                                            | Base H2        |
|----------------------|------|----------------------------------------------------|----------------|
| service-clients      | 8081 | Client (CRUD), Payment                             | clientsdb      |
| service-abonnements  | 8082 | Plan (CRUD), Subscription                          | abonnementsdb  |
| service-facturation  | 8083 | Invoice + InvoiceLine, machine a etats, HT/TVA/TTC | facturationdb  |

Aucun service n'appelle un autre : les liens entre services sont de simples identifiants
(clientId, planId, subscriptionId, invoiceId). C'est la base de la V1.

## Lancer un service
    cd service-clients
    mvn spring-boot:run
Console H2 : http://localhost:<port>/h2-console  (JDBC URL : jdbc:h2:mem:clientsdb, user sa, mot de passe vide)

## Endpoints
**service-clients (8081)**
- GET/POST /clients, GET/PUT/DELETE /clients/{id}
- GET/POST /payments, GET /payments/{id}, PATCH /payments/{id}/encaisser, PATCH /payments/{id}/annuler

**service-abonnements (8082)**
- GET/POST /plans, GET/PUT/DELETE /plans/{id}
- GET/POST /subscriptions, GET /subscriptions/{id}, PATCH /subscriptions/{id}/resilier

**service-facturation (8083)**
- GET/POST /invoices, GET /invoices/{id}
- PATCH /invoices/{id}/status?statut=EMISE|PAYEE|EN_RETARD|ANNULEE

## Exemples (curl)
    curl -X POST localhost:8081/clients -H "Content-Type: application/json" \
      -d '{"nom":"Acme SARL","email":"acme@mail.com","numeroTva":"FR123","modePaiement":"CARTE"}'

    curl -X POST localhost:8082/plans -H "Content-Type: application/json" \
      -d '{"nom":"Pro","prixMensuel":30,"quotaUtilisateurs":5,"actif":true}'

    curl -X POST localhost:8082/subscriptions -H "Content-Type: application/json" \
      -d '{"clientId":1,"planId":1}'

    curl -X POST localhost:8083/invoices -H "Content-Type: application/json" \
      -d '{"clientId":1,"subscriptionId":1,"periode":"2026-11","dateEcheance":"2026-12-05",
           "lignes":[{"description":"Plan Pro - novembre","quantite":1,"prixUnitaire":30}]}'

    curl -X PATCH "localhost:8083/invoices/1/status?statut=EMISE"
    curl -X PATCH "localhost:8083/invoices/1/status?statut=PAYEE"
    curl -X PATCH "localhost:8083/invoices/1/status?statut=ANNULEE"   # -> 409 (facture payee)

## Ce que tu ajoutes ensuite
1. Eureka + Gateway + Config Server (spring.application.name est deja defini dans chaque service).
2. V4 : OpenFeign dans service-facturation (verifier client, abonnement, plan ; facture enrichie).
3. V5 : RabbitMQ + service-notification.
4. V7 : Kafka (nouvelle branche), avec idempotence et unicite deja prevues (contrainte subscriptionId + periode).
