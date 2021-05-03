package com.example.demo;

import clientservices.impl.ClientDAO;

import models.Client;

import java.util.List;

public class test {
    public static void main(String[] args) {
        ClientDAO consultas = new ClientDAO();

        List<Client> clients = consultas.findAll();

        clients.stream().forEach(System.out::println);



    }
}
