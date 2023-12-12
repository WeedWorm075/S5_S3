/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.Generic2;

import java.sql.Connection;
import java.sql.Date;
import annotation.Column;
import annotation.Table;
import connexion.Connexion_projet;
import dao.Generic2;
import java.util.List;


/**
 *
 * @author wenze
 */
@Table(libelle = "client",base = "postgresql")
public class Client extends Generic2 {
    @Column(libelle = "id",primaryKey = true)
    Integer id;
    @Column(libelle = "nom")
    String nom;
    @Column(libelle = "prenom")
    String prenom;

    public Client() {
    }

    public Client(String nom, String prenom) {
        setNom(nom);
        setPrenom(prenom);
    }
    
    public Client(int id,String nom, String prenom) {
        setId(id);
        setNom(nom);
        setPrenom(prenom);
    }
    
    // public List<Client> allClient(Connexion_projet co) throws Exception{
    public List<Client> allClient(Connection co) throws Exception{
        try {
            String sql = "SELECT * from client";
            // return select(co.getconnection(), sql);
            return select(co, sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    // public String login(Connexion_projet co)throws Exception {
    //     try {
    //         String sql = "SELECT * from utilisateur where mail = '"+getMail()+"' and mdp ='"+getMdp()+"'";
    //         Utilisateur user_found = (Utilisateur) select_one(co.getconnection(), sql);
    //         if(user_found == null) {
    //             throw new Exception("User not found");
    //         }
    //         return user_found.getid();
    //     } catch (Exception e) {
    //         throw e;
    //     }
    // }
    
 
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    
    
    
}
