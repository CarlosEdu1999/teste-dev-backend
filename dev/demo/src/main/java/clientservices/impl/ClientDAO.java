package clientservices.impl;

import models.Client;
import models.HealthIssues;
import services.ClientService;
import models.ConnectionFactory;
import services.ClientService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ClientDAO implements ClientService {

    // 1 - Consulta
    @Override
    public List<Client> findAll() {
        //Preparar lista que irá retornar clients após consultar o banco de dados;
        List<Client> clients = new ArrayList<>();
        String name = new String();
        String name2 = new String();
        String lastname = new String();
        HealthIssues healthIssues = new HealthIssues();
        Date birthDate = new Date(null);
        String gender = new String();
        Date dateOfCreation = new Date(null);
        Date dateOfUpdate = new Date(null) ;
        Boolean verifier = Boolean.FALSE;
        try (Connection conn = ConnectionFactory.getConnection()) {
            //Preparar consulta SQL.
            String sql = "  select c.name , c.birth_date, c.gender ,c.date_of_creation , c.date_of_update , h.issue , h.issue_degree , h.client_name\n" +
                    "        from client as c\n" +
                    "        inner join health_issues as h\n" +
                    "        on c.name = h. client_name\n" +
                    "        order by c.name";

            //Preparar statement com os parâmetros recebidos (nesta função não tem parâmetros, pois irá retornar todos os valores da tabela client)
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Executa consulta e armazena o retorno da consulta no objeto "rs".
            ResultSet rs = stmt.executeQuery();

            //Criar um objeto client e guardar na lista de clients.
            while(rs.next()){


                if (name != lastname){
                    name2  = rs.getString("name");
                    if (name2!=name){
                        clients.add(new Client(
                                name,
                                birthDate,
                                gender,
                                healthIssues,
                                dateOfCreation,
                                dateOfUpdate));
                        healthIssues = new HealthIssues();
                        lastname = name;
                }
                }



                if(name== lastname){
                    name = rs.getString("nome");
                    birthDate = rs.getDate("data_de_nascimento");
                    gender = rs.getString("sexo");
                    dateOfCreation = rs.getDate("nome_doenca");
                    dateOfUpdate = rs.getDate("date_of_update");
                    healthIssues.addToList(rs.getString("issue"),rs.getInt("issue_degree"));
                }

            }
        } catch (SQLException e) {
            System.out.println("Listagem de clients FALHOU!");
            e.printStackTrace();
        }

        //Retornar todos os clients encontrados no banco de dados.
        return clients;
    }


    // 1.1 - Consulta com filtro
    @Override
    public Client getByName(String name) {
        //Preparar objeto client para receber os valores do banco de dados.
        Client client = new Client(null,null,null,null,null,null);
        HealthIssues healthIssues = new HealthIssues();
        try (Connection conn = ConnectionFactory.getConnection()) {
            //Preparar consulta SQL
            String sql = " select c.name , c.birth_date, c.gender ,c.date_of_creation , c.date_of_update , h.issue , h.issue_degree , h.client_name\n" +
                    "        from client as c\n" +
                    "        inner join health_issues as h\n" +
                    "        on c.name = h. client_name\n" +
                    "        where c.name = ?\n" +
                    "        order by c.name";

            //Preparar statement com os parâmetros recebidos
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);

            //Executa consulta e armazena o retorno da consulta no objeto "rs".
            ResultSet rs = stmt.executeQuery();

            //Guardar valores retornados da tabela client no objeto client
            if (rs.next()){


                healthIssues.addToList(rs.getString("issue"),rs.getInt("issue_degree"));
                client.setName(rs.getString("name"));
                client.setGender(rs.getString("gender"));
                client.setBirthDate(rs.getDate("birth_date"));
                client.setDateOfUpdate(rs.getDate("date_of_update"));
                client.setHealthIssues(healthIssues);
                client.setDateOfCreation(rs.getDate("date_of_creation"));

            }

        } catch (SQLException e) {
            System.out.println("Fail to list Clients");
            e.printStackTrace();
        }

        //Retorna client encontrado no banco de dados.
        return client;
    }

    // 2 - Inserção
    @Override
    public void create(Client client) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String holder;
            List<String> list;
            Iterator<String> itr;
            //Preparar SQL para inserção de dados do client.
            String sql = "INSERT INTO client (name,birth_date,gender,date_of_creation,date_of_update)\n" +
                    "VALUES ('?',?,'?',?,?);\n";


            //Preparar statement com os parâmetros recebidos
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1 , client.getName());
            stmt.setDate(2 , (java.sql.Date) client.getBirthDate());
            stmt.setString(3 , client.getGender());
            stmt.setDate(4, (java.sql.Date) client.getDateOfCreation());
            stmt.setDate(5, (java.sql.Date) client.getDateOfUpdate());


            int rowsAffected = stmt.executeUpdate();
            String sql2 = "insert into health_issues (client_name , issue , issue_degree)\n" +
                    "    values('?','?',1);\n";

            String[] A = client.getHealthIssues().getHealthIssuesList().get(0).split(",");

            list= client.getHealthIssues().getHealthIssuesList();
            itr = list.listIterator();
            while(itr.hasNext()) {
                if (A[1].endsWith("1")) {
                    holder = "1";
                } else {
                    holder = "2";
                }

                PreparedStatement stmt2 = conn.prepareStatement(sql2);
                stmt2.setString(1, client.getName());
                stmt2.setString(2, A[0]);
                stmt2.setString(3, holder);
            }


            System.out.println("Inserção BEM SUCEDIDA!. Foi adicionada " + rowsAffected + " linha");
        } catch (SQLException e) {
            System.out.println("Inserção FALHOU!");
            e.printStackTrace();
        }
    }

    // 3 - Delete
    @Override
    public void delete(String name) {
        try (Connection conn = ConnectionFactory.getConnection()) {

            //Preparar SQL para deletar uma linha.
            String sql = "delete from client where name = ?\n" +
                    "   delete from health_issues  where name = ?\n" +
                    "   ";

            //Preparar statement com os parâmetros recebidos
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1 , name);
            stmt.setString(2,name);

            //Executa delete e armazena o numero de linhas afetadas
            int rowsAffected = stmt.executeUpdate();

            System.out.println("Delete BEM SUCEDIDA! Foi deletada " + rowsAffected + " linha");
        } catch (SQLException e) {
            System.out.println("Delete FALHOU!");
            e.printStackTrace();
        }
    }

    // 4 - Atualizar
    @Override
    public void update(Client client) {
        try (Connection conn = ConnectionFactory.getConnection()) {

            //Preparar SQL para atualizar linhas.
            String sql = "  update client set name = ? , birth_date = ? , gender = ? , date_of_creation = ? , date_of_update = ?";
            Date newdate = new Date();
            //Preparar statement com os parâmetros recebidos
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, client.getName());
            stmt.setDate(2, (java.sql.Date) client.getBirthDate());
            stmt.setString(3, client.getGender());
            stmt.setDate(4, (java.sql.Date) client.getDateOfCreation());
            stmt.setDate(5, (java.sql.Date) newdate);

            //Executa atualização e armazena o numero de linhas afetadas
            int rowsAffected = stmt.executeUpdate();

            System.out.println("Atualização BEM SUCEDIDA! Foi atualizada: " + rowsAffected + " linha");
        } catch (SQLException e) {
            System.out.println("Atualização FALHOU!");
            e.printStackTrace();
        }
    }

}
