/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voyage;

import annotation.Column;
import annotation.Table;
import dao.Generic2;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author hp
 */
@Table(libelle = "Bouquet",base = "postgresql")
public class Bouquet extends Generic2{
    @Column(libelle = "idBouquet",primaryKey = true)
    String idBouquet;
    @Column(libelle = "nomBouquet")
    String nomBouquet;
    public Bouquet(){}

    public String getIdBouquet() {
        return idBouquet;
    }

    public Bouquet(String idBouquet, String nomBouquet) {
        this.idBouquet = idBouquet;
        this.nomBouquet = nomBouquet;
    }

    public void setIdBouquet(String idBouquet) {
        this.idBouquet = idBouquet;
    }

    public String getNomBouquet() {
        return nomBouquet;
    }

    public void setNomBouquet(String nomBouquet) {
        this.nomBouquet = nomBouquet;
    }
    
    public List<Bouquet> allBouquet(Connection co) throws Exception{
        try {
            String sql = "SELECT * from bouquet";
            // return select(co.getconnection(), sql);
            return select(co, sql);
        } catch (Exception e) {
            throw e;
        }
    }
}
