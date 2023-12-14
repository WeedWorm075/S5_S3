/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voyage;

import dao.Generic2;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author hp
 */
public class LieuCategorie extends Generic2{
    Integer lieuCategorie;

    public LieuCategorie(Integer lieuCategorie, String nomLieu, String nomCategorieLieu) {
        this.lieuCategorie = lieuCategorie;
        this.nomLieu = nomLieu;
        this.nomCategorieLieu = nomCategorieLieu;
    }
    String nomLieu;
    String nomCategorieLieu;

    public Integer getLieuCategorie() {
        return lieuCategorie;
    }

    public void setLieuCategorie(Integer lieuCategorie) {
        this.lieuCategorie = lieuCategorie;
    }

    public LieuCategorie() {
    }

    public String getNomLieu() {
        return nomLieu;
    }

    public void setNomLieu(String nomLieu) {
        this.nomLieu = nomLieu;
    }

    public String getNomCategorieLieu() {
        return nomCategorieLieu;
    }

    public void setNomCategorieLieu(String nomCategorieLieu) {
        this.nomCategorieLieu = nomCategorieLieu;
    }
    
    public List<LieuCategorie> allLieuCategorie(Connection co) throws Exception{
        try {
            String sql = "SELECT * from client";
            // return select(co.getconnection(), sql);
            return select(co, sql);
        } catch (Exception e) {
            throw e;
        }
    }
}
