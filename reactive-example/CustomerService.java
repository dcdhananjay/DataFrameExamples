package com.reactive.reactiveexample;

import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    
    public Flux<Customer> all(){
        Flux<Customer> allCustomers = customerRepository.findAll();
        
        
        allCustomers.log().subscribe(
            System.out::println,
            err -> err.printStackTrace(),
            () -> System.out.println("Finished"),
            subscription -> {
                        for (int i = 0; i < 5; i++) {
                            System.out.println("Requesting the next 2 elements!!!");
                            subscription.request(2);
                        }
                }
        );
        
        allCustomers.log().subscribe(new BaseSubscriber<Customer>(){
            int consumed = 0;
            int limit = 2;
            
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(limit);
            }      
    
            @Override
            protected void hookOnNext(Customer value) {
                System.out.println("Inside hookOnNext method");
                consumed++;
        
                if (consumed == limit) {
                    consumed = 0;
                    request(limit);
                }
            }
        });
        
        StepVerifier.create(allCustomers)
      .expectSubscription()
      .expectNext(new Customer("1","A"))
      .expectNext(new Customer("2","B"))
      .expectNext(new Customer("3","C"))
      .expectNext(new Customer("4","D"))
      .expectNext(new Customer("5","E"))
      .verifyComplete();
        
        return allCustomers;
    }
    
    public Mono<Customer> get(String id){
        Mono<Customer> customer = customerRepository.findById(Integer.parseInt(id)).log();
        return customer;
    }
    
    
    public Mono<Customer> update(String id,String name){
        return customerRepository
            .findById(Integer.parseInt(id))
            .map(p -> new Customer(p.getId(), name))
            .flatMap(customerRepository::save).log();
    }
    
    public Mono<Customer> delete(String id) { 
        return customerRepository
            .findById(Integer.parseInt(id))
            .flatMap(p -> customerRepository.deleteById(Integer.parseInt(p.getId())).thenReturn(p)).log();
    }
    
}
