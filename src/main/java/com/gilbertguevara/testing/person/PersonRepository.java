package com.gilbertguevara.testing.person;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, UUID> {
    Optional<Person> findByLastName(String lastName);
}
