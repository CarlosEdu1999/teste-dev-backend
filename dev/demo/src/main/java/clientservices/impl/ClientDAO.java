package clientservices.impl;

import models.Client;
import models.HealthIssues;
import org.springframework.data.relational.core.sql.TrueCondition;
import org.springframework.stereotype.Service;
import services.ClientService;
import models.ConnectionFactory;
import services.ClientService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;


@Service
public class ClientDAO implements ClientService {



    // 1 - Consulta
    @Override
    public  List<Client> findAll() {
        //Preparar lista que irá retornar clients após consultar o banco de dados;
        List<Client> clients = new ArrayList<>();
        String name = "A";
        String name2 = new String();
        String lastname = "A";
        HealthIssues healthIssues = new HealthIssues();
        LocalDate birthDate ;
        String gender = new String();
        LocalDate dateOfCreation  ;
        LocalDate dateOfUpdate ;
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

                if (lastname!= rs.getString("name")) {
                    healthIssues = new HealthIssues();
                }

                    name = rs.getString("name");
                    birthDate = rs.getDate("birth_date").toLocalDate();
                    gender = rs.getString("gender");
                    dateOfCreation = rs.getDate("date_of_creation").toLocalDate();
                    dateOfUpdate = rs.getDate("date_of_update").toLocalDate();
                    healthIssues.addToList(rs.getString("issue"),rs.getInt("issue_degree"));
                    lastname = rs.getString("name");



                    clients.add(new Client(
                                name,
                                birthDate,
                                gender,
                                healthIssues,
                                dateOfCreation,
                                dateOfUpdate));


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
                client.setBirthDate(rs.getDate("birth_date").toLocalDate());
                client.setDateOfUpdate(rs.getDate("date_of_update").toLocalDate());
                client.setHealthIssues(healthIssues);
                client.setDateOfCreation(rs.getDate("date_of_creation").toLocalDate());

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
            int holder;
            List<String> list;
            Iterator<String> itr;
            //Preparar SQL para inserção de dados do client.
            String sql = "INSERT INTO client (name,birth_date,gender,date_of_creation,date_of_update)\n" +
                    "VALUES (?,?,?,?,?);\n";
            java.sql.Date dataSql;
            java.sql.Date dataSql2;
            java.sql.Date dataSql3;
            //Preparar statement com os parâmetros recebidos
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1 , client.getName());
            stmt.setDate(2 , dataSql= java.sql.Date.valueOf(client.getBirthDate()) );
            stmt.setString(3 , client.getGender());
            stmt.setDate(4, dataSql2= java.sql.Date.valueOf(client.getDateOfCreation()));
            stmt.setDate(5, dataSql3= java.sql.Date.valueOf(client.getDateOfUpdate()));


            int rowsAffected = stmt.executeUpdate();


            int b=-1;

            list= client.getHealthIssues().getHealthIssuesList();
            itr = list.listIterator();
            while(itr.hasNext()) {
                String sql2 = "insert into health_issues (client_name , issue , issue_degree)\n" +
                        "    values(?,?,?);\n";



                b++;
                String[] A = client.getHealthIssues().getHealthIssuesList().get(b).split(",");

                System.out.println(b);

                if (A[1].endsWith("1")) {
                    holder = 1;
                } else {
                    holder = 2;
                }

                System.out.println("1loop");
                PreparedStatement stmt2 = conn.prepareStatement(sql2);
                stmt2.setString(1, client.getName());
                stmt2.setString(2, A[0]);
                stmt2.setInt(3, holder);
                stmt2.execute();

                itr.next();
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
            String sql = "delete from health_issues  where client_name = ?;\n" +
                    "delete from client where name = ?;\n" ;



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
            java.sql.Date dataSql;
            java.sql.Date dataSql2;
            java.sql.Date dataSql3;
            //Preparar SQL para atualizar linhas.
            String sql = "  update client \nset name = ? , birth_date = ? , gender = ? , date_of_creation = ? , date_of_update = ?\n"+
                    "where name = ?";
            Date newdate = new Date();
            //Preparar statement com os parâmetros recebidos
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, client.getName());
            stmt.setDate(2, dataSql= java.sql.Date.valueOf(client.getBirthDate()));
            stmt.setString(3, client.getGender());
            stmt.setDate(4, dataSql2= java.sql.Date.valueOf(client.getDateOfCreation()));
            stmt.setDate(5,dataSql3= java.sql.Date.valueOf(client.getDateOfUpdate()));
            stmt.setString(6, client.getName());
            //Executa atualização e armazena o numero de linhas afetadas
            int rowsAffected = stmt.executeUpdate();

            System.out.println("Atualização BEM SUCEDIDA! Foi atualizada: " + rowsAffected + " linha");
        } catch (SQLException e) {
            System.out.println("Atualização FALHOU!");
            e.printStackTrace();
        }
    }

}
