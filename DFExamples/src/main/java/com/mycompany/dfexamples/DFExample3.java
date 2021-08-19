/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dfexamples;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 *
 * @author Dell
 */
public class DFExample3 {

    public static void main(String[] args) {
        SparkSession session = SparkSession.builder().appName("Test").config("key", "value").master("local")
                .getOrCreate();
        Dataset<Row> dataframe = session.read().csv("D:\\SparkExamples\\data.csv");
        System.out.println(dataframe.schema());
        dataframe.show();
        System.out.println("count = " + dataframe.count());
        dataframe = dataframe.withColumnRenamed("_c0", "ID");
        dataframe = dataframe.withColumnRenamed("_c1", "Name");
        dataframe = dataframe.withColumnRenamed("_c2", "Age");
        dataframe = dataframe.withColumnRenamed("_c3", "Nationality");
        dataframe = dataframe.withColumnRenamed("_c4", "Club");
        dataframe = dataframe.withColumnRenamed("_c5", "Volleys");
        dataframe = dataframe.withColumnRenamed("_c6", "Dribbling");
        dataframe = dataframe.withColumnRenamed("_c7", "Curve");
        dataframe = dataframe.withColumnRenamed("_c8", "Accuracy");
        dataframe = dataframe.withColumnRenamed("_c9", "LongPassing");
//        for (String c : dataframe.columns()) {
//            System.out.println(c);
//        }
        dataframe.describe("Volleys").show();
        dataframe.select("ID", "Name", "Nationality", "Club", "Age").filter("Age>30").show();
        dataframe.createOrReplaceTempView("fifa_table");
        session.sql("select club,count(*) from fifa_table group by club order by count(*) desc").show();
    }
}
