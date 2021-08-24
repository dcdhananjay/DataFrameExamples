package com.mycompany.db2;

public interface IDBConnectivity {

    void makeConnection(String url, String username, String password, String tablename);
}
