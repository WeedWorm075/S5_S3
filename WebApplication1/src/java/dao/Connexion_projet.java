/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author Hasinjo
 */
public class Connexion_projet extends Connexion{
    
    public Connexion_projet(String base, String user, String password, String database) {
        super(base, user, password, database);
    }

    public Connexion_projet() {
        setBase("postgresql");
        setUser("postgres");
        setPassword("root");
        setDatabase("voyage");
    }
    
    
}
