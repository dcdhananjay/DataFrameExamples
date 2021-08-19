/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dfexamples;

import com.mycompany.bean.MovieBean;
import java.util.Arrays;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 *
 * @author Dell
 */
public class DataSet {

    public static void main(String[] args) {
        MovieBean movie1 = new MovieBean("Autograph", "4.5", "23232");
        MovieBean movie2 = new MovieBean("Mugulu Nage", "4.8", "43232");

        Encoder<MovieBean> movie_encoder = Encoders.bean(MovieBean.class);
        SparkSession session = SparkSession.builder().appName("Test").config("key", "value").master("local")
                .getOrCreate();
        Dataset<MovieBean> movieDataSet = session.createDataset(Arrays.asList(movie1, movie2), movie_encoder);
        movieDataSet.show();
        Dataset<Row> dataframe = session.read().csv("D:\\SparkExamples\\moviesdata.csv");
        dataframe = dataframe.withColumnRenamed("_c0", "movieName");
        dataframe = dataframe.withColumnRenamed("_c1", "rating");
        dataframe = dataframe.withColumnRenamed("_c2", "timestamp");
        Dataset<MovieBean> movie_set = dataframe.as(movie_encoder);
        movie_set.show();
        Dataset<Integer> intdata = session.createDataset(Arrays.asList(1, 2, 3), Encoders.INT());
        intdata.show();
    }
}
