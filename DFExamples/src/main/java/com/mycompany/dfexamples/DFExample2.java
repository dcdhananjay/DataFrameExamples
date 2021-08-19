/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dfexamples;

import com.mycompany.bean.Employee;
import java.util.Arrays;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

/**
 *
 * @author Dell
 */
public class DFExample2 {

    public static void main(String[] args) {
        Employee e1 = new Employee("Mike", "Robert", "Mike09@gmail.com", 10000);
        Employee e2 = new Employee("John", "Milers", "John09@gmail.com", 20000);
        Employee e3 = new Employee("Brett", "Lee", "Brett09@gmail.com", 30000);
        Employee e4 = new Employee("Letty", "Brown", "Letty09@gmail.com", 40000);

        Encoder<Employee> employee_encoder = Encoders.bean(Employee.class);
        SparkSession session = SparkSession.builder().appName("Test").config("key", "value").master("local")
                .getOrCreate();
        Dataset<Employee> employeeDataSet = session.createDataset(Arrays.asList(e1, e2, e3, e4), employee_encoder);
        employeeDataSet.show();
    }
}
