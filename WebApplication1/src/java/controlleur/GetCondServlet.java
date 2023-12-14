package controlleur;

import dao.Connexion_projet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import voyage.Bouquet;

public class GetCondServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String page = request.getParameter("page");
            String search = request.getParameter("bouquet");
            System.out.println("bouquet"+search+page);

            if (page.startsWith("list") && page.endsWith(".jsp")) {
                String className = page.substring("list".length(), page.length() - ".jsp".length());
                System.out.println("__className getCond : "+className);

//                try {
                    Class<?> clazz = Class.forName("voyage." + className);
                    Object instance = clazz.newInstance();
                    
                    String query = "Select * from vue_activite_bouquet where idBouquet='"+search+"'";
                    java.lang.reflect.Method method = clazz.getMethod("select", Connection.class, String.class);
                    System.out.println("__method getCond : "+method.getName());

//                    try (Connection co = new Connexion_projet().getconnection()) {
                        Connection co = new Connexion_projet().getconnection();
                        List<?> result = (List<?>) method.invoke(instance, co,query);
                        
                        Bouquet b = new Bouquet();
                        List<Bouquet> lb = b.allBouquet(co);
                        request.setAttribute("allbouquet", lb);
                        
                        System.out.println("__tab size getCond = "+result.size());
                        request.setAttribute("result", result);
//                        System.out.println("-->"+((voyage.Activite)result.get(0)).getClass());
//                    }
//                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | java.lang.reflect.InvocationTargetException e) {
//                    e.printStackTrace();
//                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors du traitement de la demande.");
//                }
            } else {
                System.out.println("La chaîne ne suit pas le format attendu.");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La chaîne ne suit pas le format attendu.");
            }
            System.out.println("hoho");
            request.setAttribute("page", page);

            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            // Log or handle the exception
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            // Log or handle the exception
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
