package de.viadee.k8s.testapp.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class FakedHealthCheck implements HealthIndicator {

    private static final Logger LOG = LoggerFactory.getLogger(FakedHealthCheck.class);

    @Value("${healthcheck.fail:false}")
    private String fail;

    private AtomicInteger failedCounter = new AtomicInteger(0);

    @Override
    public Health health() {
        Health health = checkHealth();
        LOG.info("FakedHealthCheck = {}: environment variable healthcheck.fail = {}, failedCounter = {}",
                health.getStatus(), fail, failedCounter);
        return health;
    }

    private Health checkHealth() {
        if (Boolean.valueOf(fail)) {
            failedCounter.getAndIncrement();
            return Health.down().build();
        }
        return Health.up().build();
    }
}
