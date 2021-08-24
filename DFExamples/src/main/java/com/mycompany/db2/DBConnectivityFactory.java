package com.mycompany.db2;

public class DBConnectivityFactory {

    public static IDBConnectivity GetDataAccessObj() {
        return new DBConnectivity();
    }
}
