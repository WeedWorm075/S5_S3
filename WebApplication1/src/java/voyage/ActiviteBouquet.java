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
public class ActiviteBouquet extends Generic2{

    public ActiviteBouquet(int idActivite, String nomBouquet, String nomActiviteBouquet) {
        this.idActivite = idActivite;
        this.nomBouquet = nomBouquet;
        this.nomActiviteBouquet = nomActiviteBouquet;
    }
    
    public ActiviteBouquet(){}
    Integer idActivite;
    String nomBouquet;
    String nomActiviteBouquet;

    public Integer getIdActivite() {
        return idActivite;
    }

    public void setIdActivite(Integer idActivite) {
        this.idActivite = idActivite;
    }

    public String getNomBouquet() {
        return nomBouquet;
    }

    public void setNomBouquet(String nomBouquet) {
        this.nomBouquet = nomBouquet;
    }

    public String getNomActiviteBouquet() {
        return nomActiviteBouquet;
    }

    public void setNomActiviteBouquet(String nomActiviteBouquet) {
        this.nomActiviteBouquet = nomActiviteBouquet;
    }
    
    public List<ActiviteBouquet> allActivitebouquet(Connection co) throws Exception{
        try {
            String sql = "SELECT * from client";
            // return select(co.getconnection(), sql);
            return select(co, sql);
        } catch (Exception e) {
            throw e;
        }
    }
}
