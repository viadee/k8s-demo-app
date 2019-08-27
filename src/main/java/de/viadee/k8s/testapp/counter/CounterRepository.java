package de.viadee.k8s.testapp.counter;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CounterRepository extends CrudRepository<CounterEntity, String> {

    Optional<CounterEntity> findById(String id);


}
