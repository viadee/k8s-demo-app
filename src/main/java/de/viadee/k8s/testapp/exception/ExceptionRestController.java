package de.viadee.k8s.testapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionRestController.class);

    @GetMapping("/throw")
    public String throwException() {
        RuntimeException runtimeException = new RuntimeException("test!");
        LOG.error("log exception on error level", runtimeException);
        throw runtimeException;
    }

}
