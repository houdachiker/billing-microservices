package com.billing.clients.service;

import com.billing.clients.exception.ResourceNotFoundException;
import com.billing.clients.model.Client;
import com.billing.clients.repository.ClientRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientRepository repository;

    public ClientService(ClientRepository repository) { this.repository = repository; }

    public List<Client> findAll() { return repository.findAll(); }

    public Client findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable : " + id));
    }

    public Client create(Client client) {
        client.setId(null);
        return repository.save(client);
    }

    public Client update(Long id, Client data) {
        Client c = findById(id);
        c.setNom(data.getNom());
        c.setEmail(data.getEmail());
        c.setNumeroTva(data.getNumeroTva());
        c.setModePaiement(data.getModePaiement());
        return repository.save(c);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }
}
