package com.mycompany.jdbcexample;

import java.text.ParseException;
import java.util.Arrays;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class AppTest {

    @BeforeAll
    public static void setup() {
        System.out.println("@BeforeAll executed");
    }

    @BeforeEach
    public void setupThis() {
        System.out.println("@BeforeEach executed");
    }

    @Test
    public void testCalcOne() throws ParseException {

        Assertions.assertEquals(2, JdbcExample.getMaxSize(Arrays.asList(
                new Person(1, "A", "Z", 30),
                new Person(2, "B", "Z", 30))));
        Assertions.assertEquals(1, JdbcExample.getMaxSize(Arrays.asList(
                new Person(1, "A", "Z", 30),
                new Person(2, "B", "Y", 20))));
        Assertions.assertEquals(0, JdbcExample.getMaxSize(Arrays.asList(
                new Person(1, "A", "Z", 15),
                new Person(2, "B", "Y", 15))));
        Assertions.assertEquals(3, JdbcExample.getMaxSize(Arrays.asList(
                new Person(1, "A", "Z", 30),
                new Person(2, "B", "Y", 42),
                new Person(3, "B", "Y", 21),
                new Person(4, "B", "Y", 23))));
        Assertions.assertEquals(1, JdbcExample.getMaxSize(Arrays.asList(
                new Person(1, "A", "Z", 30),
                new Person(2, "B", "Y", 12),
                new Person(3, "B", "Y", 16),
                new Person(4, "B", "Y", 13))));
        Assertions.assertEquals(3, JdbcExample.getMaxSize(Arrays.asList(
                new Person(1, "A", "Z", 10),
                new Person(2, "B", "Y", 42),
                new Person(3, "B", "Y", 21),
                new Person(4, "B", "Y", 23))));
        Assertions.assertEquals(3, JdbcExample.getMaxSize(Arrays.asList(
                new Person(1, "A", "Z", 30),
                new Person(2, "B", "Y", 42),
                new Person(3, "C", "X", 21),
                new Person(4, "D", "Z", 23),
                new Person(5, "E", "Z", 13),
                new Person(6, "F", "Z", 13),
                new Person(7, "G", "X", 23),
                new Person(8, "H", "Y", 43),
                new Person(9, "I", "Z", 23))));
        Assertions.assertEquals(0, JdbcExample.getMaxSize(Arrays.asList(
                new Person(1, "A", "X", 10),
                new Person(2, "B", "Y", 12),
                new Person(3, "C", "Z", 13),
                new Person(4, "D", "U", 14))));
        Assertions.assertEquals(1, JdbcExample.getMaxSize(Arrays.asList(
                new Person(1, "A", "P", 21),
                new Person(2, "B", "Q", 42),
                new Person(3, "C", "R", 21),
                new Person(4, "D", "S", 23))));
        Assertions.assertEquals(1, JdbcExample.getMaxSize(Arrays.asList(
                new Person(1, "A", "P", 15),
                new Person(2, "B", "P", 12),
                new Person(3, "C", "P", 20),
                new Person(4, "D", "P", 23))));

        System.out.println("======TEST CASES EXECUTED=======");
    }

    @AfterEach
    public void tearThis() {
        System.out.println("@AfterEach executed");
    }

    @AfterAll
    public static void tear() {
        System.out.println("@AfterAll executed");
    }

}
