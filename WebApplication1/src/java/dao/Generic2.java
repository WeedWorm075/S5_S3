/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import annotation.Column;
import annotation.Table;
import dao.Connexion;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hasinjo
 */
public class Generic2 implements Serializable{
    
    
   /*** Connection via une Class normal ou class mapping ***/ 
    /*
    * Fonction qui retourne la connectiion apartir d'une annotation Table 
    * ou par un class annoter par l'annoation qui recois tout les valeur de l'annotation 
    * nom_table, base, database, user, password 
    */
    private Connection getConnectionViaTable() throws  Exception{
        Table table = this.getClass().getAnnotation(Table.class);
        Connexion connexion = new Connexion(table.base(), table.user(), table.password(), table.database());
        return connexion.getconnection();
    }
    


    public String generateUpdateQueryTest() {
        Class<?> clazz = this.getClass();
        StringBuilder queryBuilder = new StringBuilder();

        try {
            // Récupérer le nom de la table depuis l'annotation @Table
            if (clazz.isAnnotationPresent(Table.class)) {
                Table tableAnnotation = clazz.getAnnotation(Table.class);
                queryBuilder.append("UPDATE ").append(tableAnnotation.libelle()).append(" SET ");
            }

            // Récupérer les colonnes et valeurs depuis les annotations @Column
            List<String> columnValuePairs = new ArrayList<>();
            String primaryKeyColumn = "";
            Object primaryKeyValue = null;

            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    field.setAccessible(true);
                    Object value = field.get(this);
                    String columnName = columnAnnotation.libelle();

                    if (columnAnnotation.primaryKey()) {
                        // Si la colonne est la clé primaire, la stocker pour la clause WHERE
                        primaryKeyColumn = columnName;
                        primaryKeyValue = value;
                    } else {
                        // Sinon, l'ajouter à la liste des colonnes à mettre à jour
                        columnValuePairs.add(columnName + "=" + escapeValue(value));
                    }
                }
            }

            // Ajouter les colonnes à mettre à jour dans la requête
            queryBuilder.append(String.join(", ", columnValuePairs));

            // Ajouter la clause WHERE avec la colonne de clé primaire
            queryBuilder.append(" WHERE ").append(primaryKeyColumn).append("=").append(escapeValue(primaryKeyValue));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return queryBuilder.toString();
    }
    
    public <T> int executeUpdateQuery(Connection connection, T updatedObject) {
        String updateQuery = generateUpdateQuery(updatedObject);
        boolean open = false;

        try  {
            if(connection==null) {
                try {
                    connection = new Connexion_projet().getconnection();
                    open = true;
                } catch (Exception e) {
                }
                
            }
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            int rowAffected = preparedStatement.executeUpdate();
            connection.commit();
            return rowAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // Retourne 0 en cas d'erreur
        }
        finally{
            if(open){
                try {
                    connection.close();
                    
                } catch (Exception e) {
                }
            }
            
        }
    }
    
    
    public <T> String generateDeleteQuery() {
        Class<?> clazz = this.getClass();
        StringBuilder queryBuilder = new StringBuilder();

        try {
            // Récupérer le nom de la table depuis l'annotation @Table
            if (clazz.isAnnotationPresent(Table.class)) {
                Table tableAnnotation = clazz.getAnnotation(Table.class);
                queryBuilder.append("DELETE FROM ").append(tableAnnotation.libelle()).append(" WHERE ");
            }

            // Récupérer la colonne de clé primaire et sa valeur depuis les annotations @Column
            String primaryKeyColumn = "";
            Object primaryKeyValue = null;

            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    field.setAccessible(true);
                    Object value = field.get(this);
                    String columnName = columnAnnotation.libelle();

                    if (columnAnnotation.primaryKey()) {
                        primaryKeyColumn = columnName;
                        primaryKeyValue = value;
                        break; // On suppose qu'il n'y a qu'une seule clé primaire
                    }
                }
            }

            // Ajouter la clause WHERE avec la colonne de clé primaire
            queryBuilder.append(primaryKeyColumn).append("=").append(escapeValue(primaryKeyValue));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return queryBuilder.toString();
    }
    
    public <T> int executeDeleteQuery(Connection connection) {
    String deleteQuery = generateDeleteQuery();
    System.out.println("deleteQuery : "+deleteQuery);
        boolean open = false;

        try  {
            if(connection==null) {
                try {
                    connection = new Connexion_projet().getconnection();
                    open = true;
                } catch (Exception e) {
                }
                
            }
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            int rowAffected = preparedStatement.executeUpdate();
            connection.commit();
            return rowAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // Retourne 0 en cas d'erreur
        }
        finally{
            if(open){
                try {
                    connection.close();
                    
                } catch (Exception e) {
                }
            }
            
        }
    }
    
    public <T> String generateUpdateQuery(T updatedObject) {
    Class<?> clazz = updatedObject.getClass();
    StringBuilder queryBuilder = new StringBuilder();

    try {
        // Récupérer le nom de la table depuis l'annotation @Table
        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            queryBuilder.append("UPDATE ").append(tableAnnotation.libelle()).append(" SET ");
        }

        // Récupérer les colonnes et valeurs depuis les annotations @Column
        List<String> columnValuePairs = new ArrayList<>();
        String primaryKeyColumn = "";
        Object primaryKeyValue = null;

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                field.setAccessible(true);
                Object value = field.get(updatedObject);
                String columnName = columnAnnotation.libelle();

                if (columnAnnotation.primaryKey()) {
                    // Si la colonne est la clé primaire, la stocker pour la clause WHERE
                    primaryKeyColumn = columnName;
                    primaryKeyValue = value;
                } else {
                    // Sinon, l'ajouter à la liste des colonnes à mettre à jour
                    columnValuePairs.add(columnName + "=" + escapeValue(value));
                }
            }
        }

        // Ajouter les colonnes à mettre à jour dans la requête
        queryBuilder.append(String.join(", ", columnValuePairs));

        // Ajouter la clause WHERE avec la colonne de clé primaire
        queryBuilder.append(" WHERE ").append(primaryKeyColumn).append("=").append(escapeValue(primaryKeyValue));

    } catch (Exception e) {
        e.printStackTrace();
    }

    return queryBuilder.toString();
}


    private String escapeValue(Object value) {
        if (value == null) {
            return "NULL";
        } else if (value instanceof String || value instanceof Character) {
            return "'" + value.toString() + "'";
        } else {
            return value.toString();
        }
    }

    
//    public void updateMain(Connection c){
//        
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//
//        try {
//            if(c==null){
//                Connexion_projet cc = new Connexion_projet();
//                c = cc.getconnection();
//            }
//            String sql = "UPDATE "+this.getClass().getSimpleName()+" SET where idSecteur = ?";
//            statement = c.prepareStatement(sql);
//            statement.setString(1, secteurId);
//            resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                String id = resultSet.getString("idClasse");
//                String nomClasse = resultSet.getString("nomClasse");
//                Classe classe = new Classe(nomClasse, new ArrayList<>());
//                classe.setId(id);
//                classes.add(classe);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (resultSet != null) resultSet.close();
//                    if (statement != null) statement.close();
//                    if (connection != null) connection.close();
//        }
//    }
    
    /** base utiliser par le table **/
    private String basetable(){
        Table table = this.getClass().getAnnotation(Table.class);
        return table.base();
    }
    
    /*** name privary key table ***/
    private String getPrimaryKey() throws Exception{
        Field[] champs = this.getClass().getDeclaredFields();
        for (Field champ : champs) {
            if(champ.getAnnotation(Column.class).primaryKey() == true)
                return champ.getAnnotation(Column.class).libelle();
        }
        throw  new Exception("il faut entrer un cle informatique sur une table et la class utiliser");
    }
        
    /***** nom de table dans la base *****/
    private String Table() {
        Table table = this.getClass().getAnnotation(Table.class);
        if (table == null) {
            return this.getClass().getSimpleName();
        }
        return table.libelle();
    }
    
    /****** 
     * Fonction qui return la valeur d'une attribut si cette attribut est annotté par
     * Column
     *****/
    private String getAnnotationColumn(Field champ) {
        if(champ.isAnnotationPresent(Column.class)){
            if (champ.getAnnotation(Column.class).libelle().equals("")) {
                return champ.getName();
            }
            return champ.getAnnotation(Column.class).libelle();
        }
        return null;
    }
    
    /**** valeur des annotation  des attributs d'une class 
     *****/
    private List<String> getValueAnnotation() {
        Field[] champs = this.getClass().getDeclaredFields();
        List<String> listeAnnotation = new ArrayList<>();
        for (Field field : champs) {
            
            String value = getAnnotationColumn(field);
//            System.out.println("getValueAnnotation : "+value);
            if (value != null) { listeAnnotation.add(value); }
        }
        return listeAnnotation;
    }
    
    /** attribut qui ont des annotation Column : attribut (methodeField) **/  
    private List<Field> FieldAnnotationColumn(){
        Field[] champs = this.getClass().getDeclaredFields();
        List<Field> fields = new ArrayList<>();
        for (Field field : champs) {
            String value = getAnnotationColumn(field);
            if (value != null) fields.add(field);
        }
        return fields;
    }
    
    /***  methodFieldNotNull 
    *   Fonction qui retourne les attributs qui a de valeur non null 
    *   et annotté par l'annotation Column 
    ***/
    private List<Field> FieldColumnNotNull() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Field[] champs = this.getClass().getDeclaredFields();
        List<Field> fields = new ArrayList<>();
        Method m = null;
        for (Field field : champs) {
            if ( getAnnotationColumn(field) != null ){
                m = this.getClass().getMethod("get" + utility.Utility.capitalword(field.getName()));
                if( m.invoke(this) != null ) fields.add(field);
            }
        }
        return fields;
    }
    
    /// avec parametre valueAnnotation
    private List<Field> methodFieldNotNull(List<String> valueAnnotation) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Field[] champs = this.getClass().getDeclaredFields();
        List<Field> fields = new ArrayList<>();
        Method m = null;
        for (Field field : champs) {
            if (getAnnotationColumn(field)!= null) {
                for (String namecolumn : valueAnnotation) {
                    m = this.getClass().getMethod("get" + utility.Utility.capitalword(field.getName()));
                    if(getAnnotationColumn(field).equals(namecolumn) && m.invoke(this) != null){
                        fields.add(field);
                    }
                }
            }
        }
        return fields;
    }
    
    /***
    *  Fonction qui retourne le nom des attribut qui a de valeur non null 
    *   et annotté par l'annotation Column 
    *   list des libelles de Column
    ***/
    private List<String> methodFieldNotNullAnnotation(List<String> valueAnnotation) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Field[] champs = this.getClass().getDeclaredFields();
        List<String> rep = new ArrayList<>();
        Method m = null;
        for (Field field : champs) {
            String value = getAnnotationColumn(field);
            if (value != null){
                m = this.getClass().getMethod("get" + utility.Utility.capitalword(field.getName()));
                if( m.invoke(this) != null ) rep.add(value);
            }
        }
        return rep;
    }
    
    /***
    *  Fonction qui retourne les attributs qui a de valeur non null 
    *   et annotté par l'annotation Column 
    *   list des libelles de Column
    ***/
    private List<Field> FieldNotNullAnnotation(List<String> valueAnnotation) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Field[] champs = this.getClass().getDeclaredFields();
        List<Field> rep = new ArrayList<>();
        Method m = null;
        for (Field field : champs) {
            String value = getAnnotationColumn(field);
            if (value != null){
                m = this.getClass().getMethod("get" + utility.Utility.capitalword(field.getName()));
                if( m.invoke(this) != null ) rep.add(field);
            }
        }
        return rep;
    }
    
    
/********************** REQUETE D'INSERTION *****************************/    
    /***
     * Fonction qui retourne le requet d'insertion dans un base 
     */
    private String requetinsertColumn(){
        String query = "insert into "+ this.Table() +" ( ";
        int i = 0;
        List<String> listeColumn = this.getValueAnnotation();
        for (String Column : listeColumn) {
            if(i == 0){ query = query + Column ;i++;     
            }else{
                query = query + ","+ Column  ;
            }
        }
        return query;
    }
    
    /**
     * Fonction qui retourne le requet d'insertion des donner insert 
     **/
     private String requetinsertvalues() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, Exception{
        int i = 0;
        String query = requetinsertColumn();
        query = query + ") values ( ";
        for (Field field : FieldAnnotationColumn()) {
            if(i == 0){
                if(this.basetable().equals("postgresql")){
                    query = query +"default";
                }else if(this.basetable().equals("oracle")){
                    query = query + getPrimaryKey()+".nextval";
                }
                
            }else{
//                System.out.println("field name : "+field.getName());
                Method getter = this.getClass().getMethod("get"+utility.Utility.capitalword(field.getName()));
//                System.out.println("getter : "+getter.getName());
                Object value = getter.invoke(this);
                if(field.getType() == String.class || field.getType() == Date.class || field.getType() == Timestamp.class) query = query +","+"'"+value+"'";
                else query = query +","+value;
            }
            i++;
        }
        return  query + " ) ";
    }
     
    /**
     * Fonction qui fait le setting de l'objet dans le preparedStatement
     * pour create
     **/
    private void setObject(PreparedStatement statement, List<Field> valueObject, int debut) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
        Method m = null;
        for(int j = debut;j<valueObject.size();j++){
            m = this.getClass().getMethod("get" + utility.Utility.capitalword(valueObject.get(j).getName()));
            if(debut == 0){
                if( j==0 && !valueObject.get(j).getType().getSimpleName().equals("String") ){
                    statement.setObject(j+1, m.invoke(this)); 
                }else if(!valueObject.get(j).getType().getSimpleName().equals("String") )
                    statement.setObject(j, m.invoke(this));
            }else{
                if(j==0) statement.setObject(j+1, m.invoke(this)); 
                else statement.setObject(j, m.invoke(this));       
            }
          
        }   
    }
    
    /*** INSERTION DU TABLE
     * @param connexion
     * @throws java.lang.NoSuchMethodException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     * @throws java.sql.SQLException ***/
    public void  create(Connection connexion) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException, Exception{
        String query = this.requetinsertvalues();
        System.out.println("create query : "+query);
        boolean open = false;
        Statement statement = null;
        try {
//            if(connexion == null){
//                connexion = getConnectionViaTable();
//                open = true;
//                connexion.setAutoCommit(false);
//            }
//            statement = connexion.prepareStatement(query);
//            setObject(statement, FieldAnnotationColumn(), 1);
            statement = connexion.createStatement();

            int value = statement.executeUpdate(query);
            System.out.println("row affected for create : "+value);
            connexion.commit();
//            if(open){ connexion.commit(); }
        } catch (Exception e) {
            if(connexion != null && open){
                 connexion.rollback();
            }
            throw e;
        }finally{
            if(open){ connexion.close(); }
            if(statement!= null){ statement.close(); }
        }
    }
    
    /************ CONDITION ***************/
    private String AvecCondition(String query) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        query =  query + " where 0=0 ";
        List<Field> listeColumn = FieldNotNullAnnotation(this.getValueAnnotation());
        Method m = null;
        int i = 0,j=0;
        for (Field field : listeColumn) {
            String value = getAnnotationColumn(field);
            System.out.println("__value : "+value);
            if(field.getType().getSimpleName().equals("String")){
                System.out.println("in update field begin");
                m = this.getClass().getMethod("get" + utility.Utility.capitalword(value));
                System.out.println("method : "+m.getName());
                query = query + " and " + value + " LIKE '%"+m.invoke(this)+"%'";
                System.out.println("in update field : "+query);
            }else{
                query = query + " and " + value + " = ?";
            }
        }
        System.out.println("update query : "+query);
        return query;
    }
    
    /****  SELECT ****/
    private String requetselect(String[] column){
        String query = "select ";
        if(column != null){
            for (int i = 0; i < column.length; i++) {
                if(i==0)
                    query = query + column[i];
                else
                    query = query + "," +column[i];
            }
        }else{
            query =  query + " * ";
        }
        return query + " from "+Table();
    }
    
    /**
     * Fonction qui retourne les attribut du column selectionné
     * @param column
     * @return
     * @throws NoSuchMethodException 
     */
    private List<Field> MethodSet(String[] column) throws NoSuchMethodException, Exception{
        List<String> annotation = new ArrayList<>();
        if(column != null){
            Field[] champs = this.getClass().getDeclaredFields();
            List<Field> fields = new ArrayList<>();
            for (String string : column) { 
                boolean check = false;
                for (Field champ : champs) {
                    String value = getAnnotationColumn(champ);
                    if(value != null && value.equals(string)) fields.add(champ); check = true;
                }
                if(check) throw  new Exception("Le column "+ string+" n'existe pas dans cette class "+this.getClass()); 
            }
            return fields;
        }
        return FieldAnnotationColumn();
    }
    
    /** Fonction qui transfome le tableau string[] column en List  **/
    private List<String> ColumnLabel(String[] column) throws NoSuchMethodException{
        List<String> annotation = new ArrayList<>();
        if(column != null){
            for (String string : column) {
                annotation.add(string);
            }
            return annotation;
        }
        return getValueAnnotation();
    }
    
    /**** Finction selecte generaliser ****/
    private List readSelect(Connection connexion,String[] column) throws SQLException, Exception{
        PreparedStatement statement = null;
        boolean open = false;
        List<dao.Generic2> results = new ArrayList<>();
        String query = new String();
        ResultSet resultat = null;
        try {
            if(connexion == null){
                connexion = getConnectionViaTable();
                open = true;
                connexion.setAutoCommit(false);
            }
            query = AvecCondition(requetselect(column));
            statement = connexion.prepareStatement(query); 
            setObject(statement,FieldColumnNotNull(), 0);
            dao.Generic2 g = null;
            resultat = statement.executeQuery();
            List<Field> fields =  MethodSet(column);
            List<String> annotation = ColumnLabel(column);
            int i = 0;
            while(resultat.next()){
                g = this.getClass().newInstance();
                for (Field field : fields) {
                    g.getClass().getMethod("set" + utility.Utility.capitalword(field.getName()), field.getType()).invoke(g, resultat.getObject(annotation.get(i)));
                    i++;
                }
                results.add(g);
                i=0;
            }
            return results;
        } catch (Exception e) {
            if(connexion != null)  connexion.rollback();
            e.printStackTrace();
            
            throw  e;
        }finally{
            if(open) connexion.close();
            if(statement!= null)  statement.close();
            if(resultat != null)  resultat.close();
        }
    }
    
    public List findAll(Connection connexion) throws Exception{
        return readSelect(connexion,null);
    }
    
    public List find(Connection connexion,String[] column) throws Exception{
        return readSelect(connexion,column);
    }
    
    public Generic2 findById(Connection connexion, Object id) throws Exception{
        String sql = requetselect(null);
        String where = " where "+getPrimaryKey();
        if(id instanceof Number) {
            where = where + " = "+id.toString();
        }
        else {
            where = where + " = '"+id.toString()+"'";
        }
        sql = sql + where;
        //System.out.println(sql);
        return select_one(connexion, sql);
    }
    
    public Generic2 findBy(Connection connexion, String column ,Object value) throws Exception{
        String sql = requetselect(null);
        String where = " where "+column;
        if(value instanceof Number) {
            where = where + " = "+value.toString();
        }
        else {
            where = where + " = '"+value.toString()+"'";
        }
        sql = sql + where;
        //System.out.println(sql);
        return select_one(connexion, sql);
    }
    
    public List findAllBy(Connection connexion, String column ,Object value) throws Exception{
        String sql = requetselect(null);
        String where = " where "+column;
        if(value instanceof Number) {
            where = where + " = "+value.toString();
        }
        else {
            where = where + " = '"+value.toString()+"'";
        }
        sql = sql + where;
        System.out.println(sql);
        return select(connexion, sql);
    }
    
    public Generic2 findById(Connection connexion) throws Exception{
        return (Generic2) findAll(connexion).get(0);
    }
    
    public Generic2 findById(Connection connexion,String[] column) throws Exception{
        return (Generic2) find(connexion, column).get(0);
    }
    
/** Update **/
    //Requete pour update
    private String requetupdate() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        List<String> annotations = this.methodFieldNotNullAnnotation(this.getValueAnnotation());
        String query = "update "+Table() +" set ";
        int i = 0;
        if( annotations.size() == 1 ){
            query = query + annotations.get(0) +" = ? ";
        }else {
            for (String annotation : annotations) {
                if( i == (annotations.size()-1) ) query = query + annotation +" = ?";
                else{
                    query = query + annotation +" = ? , ";
                } i += 1;
            }
        }
        
        return query; 
    }
    
    /**
     * Fonction qui fait le setting de l'objet dans le preparedStatement
     * pour update
     **/
    private void setObject(PreparedStatement statement, List<Field> valueObjectUpdate, List<Field> valueObject, Generic2 objupd) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
        Method m = null;
        int j = 0,fin = 0;
        for(;j<valueObjectUpdate.size();j++){
            m = objupd.getClass().getMethod("get" + utility.Utility.capitalword(valueObjectUpdate.get(j).getName()));
            fin = j+1;
            statement.setObject(fin, m.invoke(objupd));
        }
        for (int i = 0; i <  valueObject.size(); i++) {
            if(!valueObject.get(i).getType().getSimpleName().equals("String") ){
                m = this.getClass().getMethod("get" + utility.Utility.capitalword(valueObject.get(i).getName())); 
                fin += 1;
                statement.setObject(fin, m.invoke(this));
            }
        }
    }
    
    //fonction update

    /**
     *
     * @param connexion
     * @param objupdate (object qu'on fait la modification avec l'object appellant)
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws SQLException
     * @throws Exception
     */
    public void update(Connection connexion,Generic2 objupdate) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException, Exception{
        System.out.println("begin");
        String query = this.AvecCondition(objupdate.requetupdate());
        System.out.print(query);
        List<String> listeColumn = this.getValueAnnotation();
        List<String> listeColumnObjUpadte =  objupdate.methodFieldNotNullAnnotation(objupdate.getValueAnnotation());;
        boolean open = false;
        PreparedStatement statement = null;
        try {
            if(connexion == null){
                connexion = getConnectionViaTable();
                open = true;
                connexion.setAutoCommit(false);
            }
            statement = connexion.prepareStatement(query);
            setObject(statement, objupdate.methodFieldNotNull(listeColumnObjUpadte), this.methodFieldNotNull(listeColumn), objupdate);
            int value = statement.executeUpdate();
            System.out.println("update row affected : "+value);
            connexion.commit();
//            if(open) connexion.commit();
        } catch (Exception e) {
            if(connexion != null) connexion.rollback();
            throw e;
        }finally{
            if(open) connexion.close();
            if(statement!= null) statement.close();     
        }
    }
    
    /**** Delete ***/
    private String requetedelete() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        String query = "delete from "+Table() + " ";
        return AvecCondition(query);
    }
    
    public void delete(Connection connexion) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException, Exception{
        String query = requetedelete();
        System.out.println(query);
        List<String> listeColumn = this.getValueAnnotation();
        boolean open = false;
        PreparedStatement statement = null;
        System.out.println("delete before try");
        try {
            if(connexion == null){
                connexion = getConnectionViaTable();
                open = true;
                connexion.setAutoCommit(false);
            }
            statement = connexion.prepareStatement(query);
            setObject(statement,this.methodFieldNotNull(listeColumn),0);
            int value = statement.executeUpdate();
            System.out.println("delete value "+value);
            if(open)  connexion.commit();
        } catch (Exception e) {
            if(connexion != null) connexion.rollback();
            throw  e;
        }finally{
            if(open) connexion.close();
            if(statement!= null)statement.close();
        }
    }
    
    public List select(Connection connexion,String query) throws SQLException, Exception{
//        System.out.println(query);
        PreparedStatement statement = null;
        boolean open = false;
        List<dao.Generic2> results = new ArrayList<>();
        ResultSet resultat = null;
        try {
            if(connexion == null){
                connexion = getConnectionViaTable();
                open = true;
                connexion.setAutoCommit(false);
            }
            statement = connexion.prepareStatement(query); 
            //setObject(statement,FieldColumnNotNull(), 0);
            dao.Generic2 g = null;
            resultat = statement.executeQuery();
            List<Field> fields =  MethodSet(null);
            List<String> annotation = ColumnLabel(null);
//            int i = 0;
//            while(resultat.next()){
//                g = this.getClass().newInstance();
//                for (Field field : fields) {
//                    g.getClass().getMethod("set" + utility.Utility.capitalword(field.getName()), field.getType()).invoke(g, resultat.getObject(annotation.get(i)));
//                    i++;
//                }
//                results.add(g);
//                i=0;
//            }

//            while (resultat.next()) {
//            g = this.getClass().newInstance();
//            for (int i = 0; i < fields.size(); i++) {
////                g.getClass().getMethod("set" + utility.Utility.capitalword(fields.get(i).getName()), fields.get(i).getType()).invoke(g, resultat.getObject(annotation.get(i)));
//                g.getClass().getMethod("set" + utility.Utility.capitalword(fields.get(i).getName()), fields.get(i).getType()).invoke(g, String.valueOf(resultat.getObject(annotation.get(i))));
//
//            }
//            results.add(g);
//        }
        while (resultat.next()) {
//            System.out.println(resultat.getS);
            g = this.getClass().newInstance();
            for (int i = 0; i < fields.size(); i++) {
                Object value = resultat.getObject(annotation.get(i));
                // Convertir la valeur au type attendu par la méthode set
                Object convertedValue = convertValueToFieldType(value, fields.get(i).getType());
                g.getClass().getMethod("set" + utility.Utility.capitalword(fields.get(i).getName()), fields.get(i).getType()).invoke(g, convertedValue);
            }
            results.add(g);
        }
            return results;
        } catch (Exception e) {
            if(connexion != null)  connexion.rollback();
            e.printStackTrace();
            
            throw  e;
        }finally{
            if(open) connexion.close();
            if(statement!= null)  statement.close();
            if(resultat != null)  resultat.close();
        }
    }
    
    // Méthode pour convertir la valeur au type attendu par la méthode set
    private Object convertValueToFieldType(Object value, Class<?> fieldType) {
        // Ajoutez ici la logique de conversion appropriée en fonction du type du champ
        if (fieldType == Integer.class) {
            return (value == null) ? null : Integer.valueOf(value.toString());
        } else if (fieldType == String.class) {
            return (value == null) ? null : value.toString();
        }
        // Ajoutez des conversions supplémentaires si nécessaire

        // Si le type n'est pas géré, renvoyez simplement la valeur sans conversion
        return value;
    }
    
    public Generic2 select_one(Connection connexion,String query) throws SQLException, Exception{
        PreparedStatement statement = null;
        boolean open = false;
        //List<dao.Generic2> results = new ArrayList<>();
        ResultSet resultat = null;
        try {
            if(connexion == null){
                connexion = getConnectionViaTable();
                open = true;
                connexion.setAutoCommit(false);
            }
            statement = connexion.prepareStatement(query); 
            //setObject(statement,FieldColumnNotNull(), 0);
            dao.Generic2 g = null;
            resultat = statement.executeQuery();
            List<Field> fields =  MethodSet(null);
            List<String> annotation = ColumnLabel(null);
            int i = 0;
            while(resultat.next()){
                g = this.getClass().newInstance();
                for (Field field : fields) {
                    g.getClass().getMethod("set" + utility.Utility.capitalword(field.getName()), field.getType()).invoke(g, resultat.getObject(annotation.get(i)));
                    i++;
                }
                return g;
                //results.add(g);
                //i=0;
            }
            return null;
        } catch (Exception e) {
            if(connexion != null)  connexion.rollback();
            e.printStackTrace();
            
            throw  e;
        }finally{
            if(open) connexion.close();
            if(statement!= null)  statement.close();
            if(resultat != null)  resultat.close();
        }
    }
    
    public void delete(Connection connexion, String query) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException, Exception{
        List<String> listeColumn = this.getValueAnnotation();
        boolean open = false;
        PreparedStatement statement = null;
        try {
            if(connexion == null){
                connexion = getConnectionViaTable();
                open = true;
                connexion.setAutoCommit(false);
            }
            statement = connexion.prepareStatement(query);
            int value = statement.executeUpdate();
            if(open)  connexion.commit();
        } catch (Exception e) {
            if(connexion != null) connexion.rollback();
            throw  e;
        }finally{
            if(open) connexion.close();
            if(statement!= null)statement.close();
        }
    }
    
}
