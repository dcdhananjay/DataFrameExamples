package com.mycompany.jdbcexample;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverAction;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JdbcExample implements DriverAction {

    @Override
    public void deregister() {
        System.out.println("Driver deregistered");
    }

    public static void main(String[] args) {

        try {
            Driver driver = new org.postgresql.Driver();
            DriverAction da = new JdbcExample();
            DriverManager.registerDriver(driver, da);
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres", "postgres", "root");
            if (conn != null) {
                System.out.println("Connected to the database!");

                String query = "Select * from person";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Person> result = getList(resultSet);

                List<Person> result2 = new ArrayList<>();

                for (Person p : result) {
                    if (p.getAge() > 20) {
                        result2.add(p);
                    }
                }

                System.out.println("Records with age more than 20 years : ");
                result2.forEach(x -> System.out.println(x));

                Map<String, Long> countByLname = result.stream()
                        .map(Person::getLname)
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

                System.out.println("Group by Last name Result : " + countByLname);

                Long maxCount = 0L;
                String res = "";
//
//                for (Map.Entry<String, Long> entry : countByLname.entrySet()) {
//                    if (entry.getValue() > maxCount) {
//                        maxCount = (Long) entry.getValue();
//                        res = (String) entry.getKey();
//                    }
//                }
                maxCount = (Collections.max(countByLname.values()));

                for (Map.Entry<String, Long> entry : countByLname.entrySet()) {
                    if (entry.getValue() == maxCount) {
                        res = entry.getKey();
                    }
                }

                System.out.println("Max Group Size : ");
                System.out.println("Last Name : " + res);
                System.out.println("Total Count : " + maxCount);

            } else {
                System.out.println("Failed to make connection!");
            }
            conn.close();
            DriverManager.deregisterDriver(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Person> getList(ResultSet resultSet) throws SQLException {
        List<Person> result = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String fname = resultSet.getString("fname");
            String lname = resultSet.getString("lname");
            int age = resultSet.getInt("age");
            Date dob = resultSet.getDate("dob");

            Person obj = new Person();
            obj.setId(id);
            obj.setFname(fname);
            obj.setLname(lname);
            obj.setAge(age);
            obj.setDob(dob);

            result.add(obj);

        }
        return result;
    }

}
