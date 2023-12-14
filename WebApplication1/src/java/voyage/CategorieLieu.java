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
@Table(libelle = "CategorieLieu",base = "postgresql")
public class CategorieLieu extends Generic2{
    @Column(libelle = "idCategorieLieu",primaryKey = true)
    String idCategorieLieu;
    @Column(libelle = "nomCategorieLieu")
    String nomCategorieLieu;
    public CategorieLieu(){}

    public String getIdCategorieLieu() {
        return idCategorieLieu;
    }

    public CategorieLieu(String categorieLieu, String nomCategorieLieu) {
        this.idCategorieLieu = categorieLieu;
        this.nomCategorieLieu = nomCategorieLieu;
    }

    public void setIdCategorieLieu(String categorieLieu) {
        this.idCategorieLieu = categorieLieu;
    }

    public String getNomCategorieLieu() {
        return nomCategorieLieu;
    }

    public void setNomCategorieLieu(String nomCategorieLieu) {
        this.nomCategorieLieu = nomCategorieLieu;
    }
    
    public List<CategorieLieu> allCategorieLieu(Connection co) throws Exception{
        try {
            String sql = "SELECT * from CategorieLieu";
            // return select(co.getconnection(), sql);
            return select(co, sql);
        } catch (Exception e) {
            throw e;
        }
    }
}
