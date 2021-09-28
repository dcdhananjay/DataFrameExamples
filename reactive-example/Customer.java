package com.reactive.reactiveexample;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@ToString
public class Customer {
    @Id
    private int id;
    private String name;
}
