package com.mycompany.db;

import java.util.Properties;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.max;

class DBConnect {

    private String url = "";
    private String username = "";
    private String password = "";
    private String tablename = "";

    DBConnect(String url, String username, String password, String tablename) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.tablename = tablename;
    }

    void connect() {
        SparkSession session = SparkSession.builder().appName("Test").config("key", "value").master("local")
                .getOrCreate();
        Dataset<Row> jdbcDF = session.read()
                .format("jdbc")
                .option("driver", "org.postgresql.Driver")
                .option("url", url)
                .option("dbtable", tablename)
                .option("user", username)
                .option("password", password)
                .load();

        Properties connectionProperties = new Properties();
        connectionProperties.put("user", username);
        connectionProperties.put("password", password);

        jdbcDF.show();

        jdbcDF.createOrReplaceTempView("employees");

        session.sql("select * from employees where salary>20000").show();

        session.sql("select count(*), department from employees group by department").show();

        Dataset<Row> jdbcDF2 = jdbcDF.groupBy("department").agg(max("salary").as("Maximum Salary"));
        jdbcDF2.show();

        Dataset<Row> withColumn = jdbcDF.withColumn("grade", functions.when(col("salary").$greater$eq(30000), "A").
                when(col("salary").$greater$eq(20000), "B").otherwise("C"));
        withColumn.show();
        System.out.println(withColumn.schema());

        withColumn.write().mode(SaveMode.Append)
                .jdbc(url, tablename, connectionProperties);
    }

}
