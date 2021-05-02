package services;
import models.Client;

import java.util.List;

public interface ClientService {
    Client create(final Client client);
    Client getByName(final Integer client);
    List<Client> findAll();
    void update(Client client);
    void delete(final Integer ID);
}
