package controller;

import models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


import java.util.List;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.*;
import services.ClientService;


@RestController
//Mapeia as requisições de localhost:8080/person/
@RequestMapping("/api/client")
public class ClientController {

    private static final Logger logger =Logger.getLogger(ClientController.class.toString());
    @Autowired
    private ClientService ClientService;


            //Mapeia as requisições GET para localhost:8080/person/
            //recebendo um ID como @PathVariable


    // Produz JSON como retorno
    @GetMapping(path = "/client", params = {"name"},produces = MediaType.APPLICATION_JSON_VALUE)
    public Client get(@PathVariable(value = "name") String name){
        return ClientService.getByName(name);
    }

    @ResponseStatus(HttpStatus.OK)
    //Por padrão responde com o status code 200 success
    @RequestMapping(value = "/findAll",
            //Mapeia as requisições GET para localhost:8080/person/findAll
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    // Produz JSON como retorno
    @GetMapping
    public List<Client> findAll(){
        return ClientService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    //Por padrão responde com o status code 200 success
    @RequestMapping(method = RequestMethod.PUT,
            //Mapeia as requisições PUT para localhost:8080/person/
            consumes = MediaType.APPLICATION_JSON_VALUE,
            // Consome JSON enviado no corpo da requisição
            produces = MediaType.APPLICATION_JSON_VALUE)
    // Produz JSON como retorno
    @PostMapping
    public Client create(@RequestBody Client client){
        return ClientService.create(client);
    }

    @ResponseStatus(HttpStatus.OK)
    //Por padrão responde com o status code 200 success
    @RequestMapping(method = RequestMethod.POST,
            //Mapeia as requisições POST para localhost:8080/person/
            consumes = MediaType.APPLICATION_JSON_VALUE)
    // Consome JSON enviado no corpo da requisição
    @PutMapping
    public Client update(@RequestBody Client client){
        return ClientService.update(client);
    }

    @ResponseStatus(HttpStatus.OK)
    //Por padrão responde com o status code 200 success
    @RequestMapping(value = "/{name}",
            method = RequestMethod.DELETE)
    //Mapeia as requisições DELETE para localhost:8080/person/
    //recebendo um ID como @PathVariable
    @DeleteMapping
    public void delete(@PathVariable(value = "name") String name){
        ClientService.delete(name);
        }
    }

