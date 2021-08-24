package com.mycompany.db2;

public interface IDBConnectivity {

    void makeConnection(String inputURL, String inputUsername, String inputPassword, String inpuTablename, String outputURL, String outputUsername, String outputPassword, String outputTablename);
}
