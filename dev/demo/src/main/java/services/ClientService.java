package services;
import models.Client;

import java.util.ArrayList;
import java.util.List;

public interface ClientService {


    Client create(final Client client);
    Client getByName(final String client);
    List<Client> findAll();
    Client update(Client client);
    void delete(final String name);
}
