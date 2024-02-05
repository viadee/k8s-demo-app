package de.viadee.k8s.testapp.counter;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;

@Component
public class CounterService {

    private static final Logger log = LoggerFactory.getLogger(CounterService.class);

    private static final String ID = "k8s-demo";

    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private HikariDataSource dataSource;

    public CounterEntity getCounterAndIncrement() {
        Optional<CounterEntity> counter = getCounter();

        if(counter.isPresent()) {
            CounterEntity counterEntity = counter.get();

            counterEntity.setCounter(counterEntity.getCounter()+1);
            counterRepository.save(counterEntity);

            log.debug("New counter value = {}", counterEntity.getCounter());

            return counterEntity;
        } else {
            log.error("Could not find counter in datasource {}", dataSource.getJdbcUrl());
        }

        return null;
    }

    public Optional<CounterEntity> getCounter() {
        return counterRepository.findById(ID);
    }

    @PostConstruct
    private void initCounter() {

        Optional<CounterEntity> counter = counterRepository.findById(ID);

        if(!counter.isPresent()) {
            CounterEntity newEntity = new CounterEntity();
            newEntity.setId(ID);
            newEntity.setCounter(0L);
            newEntity.setCreated(new Timestamp(System.currentTimeMillis()));
            counterRepository.save(newEntity);
        }
    }

}
