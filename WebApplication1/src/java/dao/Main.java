package dao;

import java.sql.Connection;
import java.util.List;

import dao.*;

public class Main {
    public static void main(String[] args) {

        Test ca = new Test();
        Test cf =new Test(1, "gg", "dd");
        
        Test cd =new Test(1, "pp", "dd");
        
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
                System.out.println(cf.generateUpdateQuery(cd));
                int nb = cf.executeDeleteQuery(co);
                System.out.println("hihi "+nb);
                List<Test> l = ca.allTest(co);
                for (Test cc : l) {
                    System.out.println(cc.getNom());
                }
                cf.delete(co);
                co.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
        
        }

    }
}
