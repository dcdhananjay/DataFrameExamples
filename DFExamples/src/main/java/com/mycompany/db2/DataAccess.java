package com.mycompany.db2;

public class DataAccess {

    IDBConnectivity dbObj;

    public DataAccess() {
        dbObj = DBConnectivityFactory.GetDataAccessObj();
    }

    public void makeConnection(String inputURL, String inputUsername, String inputPassword, String inpuTablename, String outputURL, String outputUsername, String outputPassword, String outputTablename) {
        dbObj.makeConnection(inputURL, inputUsername, inputPassword, inpuTablename, outputURL, outputUsername, outputPassword, outputTablename);
    }

    public static void main(String[] args) {
        String inputURL = "";
        String inputUsername = "";
        String inputPassword = "";
        String inpuTablename = "";
        String outputURL = "";
        String outputUsername = "";
        String outputPassword = "";
        String outputTablename = "";
        try {
            String inputCredentials = args[0];
            String[] inputCredArray = inputCredentials.split("#");
            inputURL = inputCredArray[0];
            inputUsername = inputCredArray[1];
            inputPassword = inputCredArray[2];
            inpuTablename = inputCredArray[3];

            String outputCredentials = args[1];
            String[] outputCredArray = outputCredentials.split("#");
            outputURL = outputCredArray[0];
            outputUsername = outputCredArray[1];
            outputPassword = outputCredArray[2];
            outputTablename = outputCredArray[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Inputs not given properly.");
            System.out.println("Required Format :: inputDBURL#inputDBUsername#inputDBPassword#inputDBTablename outputDBURL#outputDBUsername#outputDBPassword#outputDBTablename");
            return;
        }
        DataAccess obj = new DataAccess();
        obj.makeConnection(inputURL, inputUsername, inputPassword, inpuTablename, outputURL, outputUsername, outputPassword, outputTablename);
    }
}
