/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dfexamples;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import static org.apache.spark.sql.functions.col;

/**
 *
 * @author Dell
 */
public class DFExample1 {

    public static void main(String[] args) {
        SparkSession session = SparkSession.builder().appName("Test").config("key", "value").master("local")
                .getOrCreate();
        Dataset<Row> dataframe = session.read().csv("D:\\SparkExamples\\moviesdata.csv");
        System.out.println(dataframe.schema());
        dataframe.show();

        dataframe = dataframe.withColumnRenamed("_c0", "movie_name");
        dataframe = dataframe.withColumnRenamed("_c1", "rating");
        dataframe = dataframe.withColumnRenamed("_c2", "timestamp");
        dataframe = dataframe.withColumn("rating", col("rating").cast("double"));
        dataframe.select("movie_name").show();
        dataframe.select(col("movie_name"), col("rating"), col("rating").plus(1)).show();
        //dataframe.show();
        dataframe.groupBy("movie_name").avg("rating").show();
        dataframe.createOrReplaceTempView("movie_table");
        session.sql("select * from movie_table").show();
    }
}
