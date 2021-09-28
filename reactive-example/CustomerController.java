package com.reactive.reactiveexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    CustomerService service;
    
    @GetMapping("all")
    public Flux<Customer> getAll(){
        return this.service.getAllCustomers();
    }

//    @GetMapping("{id}")
//    public Mono<ResponseEntity<Customer>> getCustomerById(@PathVariable int customerId){
//        return this.service.getCustomerbyId(customerId)
//                                .map(ResponseEntity::ok)
//                                .defaultIfEmpty(ResponseEntity.notFound().build());
//    }
    
    @GetMapping("{id}")
    public Mono<Customer> getCustomerById(@PathVariable int customerId){
        return this.service.getCustomerbyId(customerId);
    }

    @PostMapping
    public Mono<Customer> createCustomer(@RequestBody Mono<Customer> customerMono){
        return customerMono.flatMap(this.service::createCustomer);
    }

    @PutMapping("{id}")
    public Mono<Customer> updateCustomer(@PathVariable int customerId,
                                       @RequestBody Mono<Customer> customerMono){
        return this.service.updateCustomer(customerId, customerMono);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCustomer(@PathVariable int id){
        return this.service.deleteCustomer(id);
    }

}
