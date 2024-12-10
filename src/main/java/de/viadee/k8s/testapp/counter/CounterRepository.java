package de.viadee.k8s.testapp.counter;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CounterRepository extends CrudRepository<CounterEntity, String> {

    Optional<CounterEntity> findById(String id);


}
