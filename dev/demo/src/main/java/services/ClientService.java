package services;
import models.Client;

import java.util.ArrayList;
import java.util.List;

public interface ClientService {


    void create(final Client client);
    Client getByName(final String client);
    List<Client> findAll();
    void update(Client client);
    void delete(final String name);
}
