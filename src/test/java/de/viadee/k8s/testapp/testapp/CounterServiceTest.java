package de.viadee.k8s.testapp.testapp;

import de.viadee.k8s.testapp.counter.CounterEntity;
import de.viadee.k8s.testapp.counter.CounterRepository;
import de.viadee.k8s.testapp.counter.CounterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * This test class demonstrates how to disable specific tests in Native images.
 * The test is disabled in Native image builds because Mockito support is not yet
 * available in Spring Boot's Native image support as of version 3.4 (see issue #32195).
 *
 * @see <a href="https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-with-GraalVM">Native Limitations</a>
 */
@SpringBootTest
@DisabledInNativeImage
public class CounterServiceTest {

    @Autowired
    private CounterService counterService;

    @MockitoBean
    private CounterRepository counterRepository;

    @Test
    void testGetCounterAndIncrement() {
        // Arrange
        CounterEntity entity = new CounterEntity();
        entity.setId("k8s-demo");
        entity.setCounter(5L);
        entity.setCreated(new Timestamp(System.currentTimeMillis()));

        reset(counterRepository);
        when(counterRepository.findById("k8s-demo")).thenReturn(Optional.of(entity));
        when(counterRepository.save(any(CounterEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        CounterEntity result = counterService.getCounterAndIncrement();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCounter()).isEqualTo(6L);
        verify(counterRepository).findById("k8s-demo");
        verify(counterRepository).save(any(CounterEntity.class));
    }
}
