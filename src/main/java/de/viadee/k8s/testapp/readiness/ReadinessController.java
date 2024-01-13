package de.viadee.k8s.testapp.readiness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReadinessController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadinessController.class);

    private boolean ready = true;

    // http://localhost:8080/ready?set=false
    @GetMapping("/ready")
    public ResponseEntity<String> checkReadiness(@RequestParam(required = false, name = "set") Boolean newValue) {

        if(newValue!=null) {
            ready = newValue;
            LOGGER.debug("set ready = " + ready);
            return ResponseEntity.ok().body("set ready = " + ready);
        }

        LOGGER.debug("ready = " + ready);
        if(ready) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
}
