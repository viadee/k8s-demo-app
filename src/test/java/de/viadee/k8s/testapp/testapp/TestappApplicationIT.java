package de.viadee.k8s.testapp.testapp;

import de.viadee.k8s.testapp.counter.CounterEntity;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class TestappApplicationIT {

    @Test
    public void testCounterController() throws InterruptedException {
        String docker_host = "localhost";
        RestTemplate restTemplate = new RestTemplate();

        // Wait for Container to come up
        boolean ready = false;
        int count = 240;
        while (!ready && count > 0) {
            count--;
            try {
                Thread.sleep(1000);
                restTemplate.getForObject("http://" + docker_host + ":8080/ready", String.class);
                ready = true;
            } catch (Exception e) {
                System.out.println("Service not ready");
            }
        }

        // Test Counter
        CounterEntity counter = restTemplate.getForObject("http://" + docker_host + ":8080/counter", CounterEntity.class);
        System.out.println("counter was " + counter.getCounter());
        Assert.assertTrue(counter.getCounter() > 1);
    }
}
