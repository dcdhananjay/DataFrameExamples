package com.reactive.reactiveexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository repository;
    
    
    public Flux<Customer> getAllCustomers(){
        return this.repository.findAll();
    }

    public Mono<Customer> getCustomerbyId(int Id){
        return this.repository.findById(Id);
    }

    public Mono<Customer> createCustomer(final Customer customer){
        return this.repository.save(customer);
    }

    public Mono<Customer> updateCustomer(int customerId, final Mono<Customer> customerMono){
        return this.repository.findById(customerId)
                .flatMap(p -> customerMono.map(u -> {
                    p.setName(u.getName());
                    return p;
                }))
                .flatMap(p -> this.repository.save(p));
    }

    public Mono<Void> deleteCustomer(final int id){
        return this.repository.deleteById(id);
    }
}
