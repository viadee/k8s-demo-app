package de.viadee.k8s.testapp.health;

import de.viadee.k8s.testapp.counter.CounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DbHealthCheck implements HealthIndicator {

    private static final Logger LOG = LoggerFactory.getLogger(DbHealthCheck.class);

    @Autowired
    private CounterService counterService;


    @Override
    public Health health() {
        try {
            counterService.getCounter();
        } catch (Exception e) {
            LOG.debug("DbHealthCheck = DOWN");
            return Health.down(e).build();
        }

        LOG.debug("DbHealthCheck = UP");
        return Health.up().build();
    }
}
