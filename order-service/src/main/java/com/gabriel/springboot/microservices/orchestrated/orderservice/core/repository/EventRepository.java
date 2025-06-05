package com.gabriel.springboot.microservices.orchestrated.orderservice.core.repository;

import com.gabriel.springboot.microservices.orchestrated.orderservice.core.document.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
}
