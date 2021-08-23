package com.mycompany.db;

public class DataAccess {

    public static void main(String[] args) {
        String url = "";
        String username = "";
        String password = "";
        String tablename = "";
        DBConnect con;
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
        con = new DBConnect(url, username, password, tablename);
        con.connect();
    }
}
