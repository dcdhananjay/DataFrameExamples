package com.reactive.reactiveexample;

import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class CustomerServiceTest {
    
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    
    public CustomerServiceTest(@Autowired CustomerRepository customerRepository,@Autowired CustomerService customerService){
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }
    
    
    @Test
    public void save() {
        Mono<Customer> profileMono = customerService.create("JOSH");
        StepVerifier
            .create(profileMono)
            .expectNextMatches(saved -> StringUtils.hasText(saved.getId()))
            .verifyComplete();
    }
    
    @Test
    public void delete() {
        String test = "JOSH";
        Mono<Customer> deleted = customerService
            .create(test)
            .flatMap(saved -> customerService.delete(saved.getId()));
        StepVerifier
            .create(deleted)
            .expectNextMatches(profile -> profile.getName().equalsIgnoreCase(test))
            .verifyComplete();
    }
    
    
    @Test
    public void update() throws Exception {
        Mono<Customer> saved = customerService
            .create("test")
            .flatMap(p -> customerService.update("1", "test1"));
        
        StepVerifier
            .create(saved)
            .expectNextMatches(p -> p.getName().equalsIgnoreCase("test1"))
            .verifyComplete();
    }
    
    
}
