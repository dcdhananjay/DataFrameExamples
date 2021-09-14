package com.mycompany.jdbcexample;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverAction;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.OptionalInt;
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

            String URL = "";
            String username = "";
            String password = "";
            String tablename = "";

            try {
                String inputCredentials = args[0];
                String[] inputCredArray = inputCredentials.split("#");
                URL = inputCredArray[0];
                username = inputCredArray[1];
                password = inputCredArray[2];
                tablename = inputCredArray[3];

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Inputs not given properly.");
                System.out.println("Required Format :: DBURL#DBUsername#DBPassword#DBTablename");
                return;
            }

            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + URL, username, password);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String query = "Select * from " + tablename;

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Person> result = getList(resultSet);

                OptionalInt size = result.stream()
                        .filter((p) -> p.getAge() > 20)
                        .collect(Collectors.groupingBy(Person::getLname))
                        .values().stream().mapToInt(Collection::size).max();

                System.out.println("Answer is : " + size.getAsInt());

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

    public static int getMaxSize(List<Person> result) {
        try {
            OptionalInt size = result.stream()
                    .filter((p) -> p.getAge() > 20)
                    .collect(Collectors.groupingBy(Person::getLname))
                    .values().stream().mapToInt(Collection::size).max();

            System.out.println("Answer is : " + size.getAsInt());
            return size.getAsInt();
        } catch (Exception e) {
            System.out.println("Answer is : 0");
            return 0;
        }
    }

}
