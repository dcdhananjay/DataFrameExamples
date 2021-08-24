package com.mycompany.db2;

public class DataAccess {

    IDBConnectivity dbObj;

    public DataAccess() {
        dbObj = DBConnectivityFactory.GetDataAccessObj();
    }

    public void makeConnection(String url, String username, String password, String tablename) {
        dbObj.makeConnection(url, username, password, tablename);
    }

    public static void main(String[] args) {
        String url = "";
        String username = "";
        String password = "";
        String tablename = "";
        try {
            String credentials = args[0];
            String[] credArray = credentials.split("#");
            url = credArray[0];
            username = credArray[1];
            password = credArray[2];
            tablename = args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Inputs not given properly. Using default parameters");
            url = "jdbc:postgresql://localhost:5433/postgres";
            username = "postgres";
            password = "root";
            tablename = "public.employeedata";
        }
        DataAccess obj = new DataAccess();
        obj.makeConnection(url, username, password, tablename);
    }
}
