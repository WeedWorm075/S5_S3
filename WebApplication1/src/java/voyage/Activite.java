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
@Table(libelle = "Activite",base = "postgresql")
public class Activite extends Generic2{
    @Column(libelle = "idActivite",primaryKey = true)
    String idActivite;
    @Column(libelle = "nomActivite")
    String nomActivite;
    public Activite(){}

    public Activite(String id, String nom) {
        setIdActivite(id);
        setNomActivite(nom);
    }

    public String getIdActivite() {
        return idActivite;
    }

    public void setIdActivite(String idActivite) {
        this.idActivite = idActivite;
    }

    public String getNomActivite() {
        return nomActivite;
    }

    public void setNomActivite(String nomActivite) {
        this.nomActivite = nomActivite;
    }
    
    public List<Activite> allActivite(Connection co) throws Exception{
        try {
            String sql = "SELECT * from activite";
            // return select(co.getconnection(), sql);
            List<Activite> a = select(co, sql);
            return select(co, sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
}
