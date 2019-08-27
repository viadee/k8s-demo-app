package de.viadee.k8s.testapp.counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CounterController.class);

	@Autowired
	private CounterService counterService;

	@GetMapping("/counter")
	public ResponseEntity<CounterEntity> checkCounter() {

		CounterEntity counter = counterService.getCounterAndIncrement();

		LOGGER.debug("counter = " + counter);
		return ResponseEntity.ok().body(counter);
	}
}
