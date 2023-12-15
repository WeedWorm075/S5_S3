package dao;

import java.sql.Connection;
import java.util.List;

import dao.*;

public class Main {
    public static void main(String[] args) {

        Test ca = new Test();
        Test cf =new Test(1,"mama","3");
        
        Test cd =new Test(1, "pp", "dd");
        Test yy = new Test(1,"oo","pp");
        Test viser = new Test(2);
        Test modif = new Test(2,"dadada", "jhfjsbgjfbg");
        Connexion_projet c = new Connexion_projet();
        Connection co = null;
        try {
            co = c.getconnection();
            cf.create(co);
//            cd.create(co);
//            
//            int nb = cf.executeDeleteQuery(co);
//            System.out.println("hihi "+nb);
//            List<Test> l = ca.allTest(co);
//            for (Test cc : l) {
//                System.out.println(cc.getNom());
//            }
            viser.update(co, modif);
            co.commit();
            
//            System.out.println(cd.findById(co));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        finally{
            try {
                co.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
        
        }

    }
}
