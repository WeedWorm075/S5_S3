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
@Table(libelle = "Lieu",base = "postgresql")
public class Lieu extends Generic2{
    @Column(libelle = "idLieu",primaryKey = true)
    String idLieu;
    @Column(libelle = "nomLieu")
    String nomLieu;

    public Lieu(String idLieu,String nomLieu) {
        this.idLieu = idLieu;
        this.nomLieu = nomLieu;
    }
    
    public String getIdLieu() {
        return idLieu;
    }

    public void setIdLieu(String idLieu) {
        this.idLieu = idLieu;
    }


    public Lieu() {
    }

    public String getNomLieu() {
        return nomLieu;
    }

    public void setNomLieu(String nomLieu) {
        this.nomLieu = nomLieu;
    }
    
    public List<Lieu> allLieu(Connection co) throws Exception{
        try {
            String sql = "SELECT * from lieu";
            // return select(co.getconnection(), sql);
            return select(co, sql);
        } catch (Exception e) {
            throw e;
        }
    }
}
