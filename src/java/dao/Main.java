package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import connexion.*;
import model.Client;

public class Main {
    public static void main(String[] args) {

        Client ca = new Client();

        Connexion_projet c = new Connexion_projet();
        Connection co = null;
        try {
            co = c.getconnection();
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        finally{
            try {
                List<Client> l = ca.allClient(co);
                for (Client cc : l) {
                    System.out.println(cc.getNom());
                }
                System.out.println();
                co.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
        
        }

    }
}
