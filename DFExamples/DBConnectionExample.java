package com.mycompany.dfexamples;

import java.util.Properties;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.max;

public class DBConnectionExample {

    public static void main(String[] args) {
        SparkSession session = SparkSession.builder().appName("Test").config("key", "value").master("local")
                .getOrCreate();
        Dataset<Row> jdbcDF = session.read()
                .format("jdbc")
                .option("driver", "org.postgresql.Driver")
                .option("url", "jdbc:postgresql://localhost:5433/postgres")
                .option("dbtable", "public.employeedata")
                .option("user", "postgres")
                .option("password", "root")
                .load();

        Properties connectionProperties = new Properties();
        connectionProperties.put("user", "postgres");
        connectionProperties.put("password", "root");

        jdbcDF.show();

        jdbcDF.createOrReplaceTempView("employees");

        session.sql("select * from employees where salary>20000").show();

        session.sql("select count(*), department from employees group by department").show();

        Dataset<Row> jdbcDF2 = jdbcDF.groupBy("department").agg(max("salary").as("Maximum Salary"));
        jdbcDF2.show();

//        jdbcDF2.write()
//                .option("public.department", "departmentname VARCHAR(64), Maximum Salary VARCHAR(64)")
//                .jdbc("jdbc:postgresql://localhost:5433/postgres", "public.department", connectionProperties);
//        jdbcDF2.write().mode(SaveMode.Append)
//                .jdbc("jdbc:postgresql://localhost:5433/postgres", "public.department", connectionProperties);
        jdbcDF2.write().mode(SaveMode.Overwrite)
                .jdbc("jdbc:postgresql://localhost:5433/postgres", "public.department", connectionProperties);

        Dataset<Row> withColumn = jdbcDF.withColumn("grade", functions.when(col("salary").$greater$eq(30000), "A").
                when(col("salary").$greater$eq(20000), "B").otherwise("C"));
        withColumn.show();
        System.out.println(withColumn.schema());

        withColumn.write().mode(SaveMode.Append)
                .jdbc("jdbc:postgresql://localhost:5433/postgres", "public.employeedata", connectionProperties);
    }
}
