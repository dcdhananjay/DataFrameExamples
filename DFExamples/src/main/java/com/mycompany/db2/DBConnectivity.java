package com.mycompany.db2;

import java.util.Properties;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.max;

public class DBConnectivity implements IDBConnectivity {

    public DBConnectivity() {

    }

    @Override
    public void makeConnection(String inputURL, String inputUsername, String inputPassword, String inpuTablename, String outputURL, String outputUsername, String outputPassword, String outputTablename) {
        SparkSession session = SparkSession.builder().appName("Test").config("key", "value").master("local")
                .getOrCreate();

        Dataset<Row> jdbcDF;
        try {
            jdbcDF = session.read()
                    .format("jdbc")
                    .option("driver", "org.postgresql.Driver")
                    .option("url", inputURL)
                    .option("dbtable", inpuTablename)
                    .option("user", inputUsername)
                    .option("password", inputPassword)
                    .load();
        } catch (Exception ex) {
            System.out.println("Exception occured while reading database :" + ex.getMessage());
            return;
        }

        Properties inputConnectionProperties = new Properties();
        inputConnectionProperties.put("user", inputUsername);
        inputConnectionProperties.put("password", inputPassword);

        Properties outputConnectionProperties = new Properties();
        outputConnectionProperties.put("user", outputUsername);
        outputConnectionProperties.put("password", outputPassword);

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

        try {
            withColumn.write().mode(SaveMode.Append)
                    .jdbc(outputURL, outputTablename, outputConnectionProperties);
        } catch (Exception ex) {
            System.out.println("Exception occured while writing to database :" + ex.getMessage());
        }
    }
}
