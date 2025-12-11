package de.viadee.k8s.testapp.testapp;

import de.viadee.k8s.testapp.K8sDemoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {K8sDemoApplication.class})
@AutoConfigureTestRestTemplate
public class SpringNativeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void mainPageAndAllResourcesAvailable() {
        // Load main page
        String baseUrl = "http://localhost:" + port + "/";
        ResponseEntity<String> mainPage = restTemplate.getForEntity(baseUrl, String.class);
        assertThat(mainPage.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(mainPage.getBody()).isNotNull();

        String html = mainPage.getBody();
        Set<String> resourcePaths = new HashSet<>();

        // Extract CSS links
        Pattern cssPattern = Pattern.compile("<link[^>]*rel=[\"']stylesheet[\"'][^>]*href=[\"']([^\"']+)[\"']", Pattern.CASE_INSENSITIVE);
        Matcher cssMatcher = cssPattern.matcher(html);
        while (cssMatcher.find()) {
            resourcePaths.add(cssMatcher.group(1));
        }

        // Extract JS scripts
        Pattern jsPattern = Pattern.compile("<script[^>]*src=[\"']([^\"']+\\.js)[\"'][^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher jsMatcher = jsPattern.matcher(html);
        while (jsMatcher.find()) {
            resourcePaths.add(jsMatcher.group(1));
        }

        assertThat(resourcePaths).isNotEmpty()
                .as("There should be at least one resource linked in the main page");

        assertThat(resourcePaths).anyMatch(resourcePath -> resourcePath.endsWith("/css/bootstrap.min.css"));
        assertThat(resourcePaths).anyMatch(resourcePath -> resourcePath.endsWith("/js/bootstrap.min.js"));
        // Print resource paths to the console
        System.out.println("Found resource paths:");
        for (String path : resourcePaths) {
            System.out.println(" - " + path);
        }

        // Check resources
        for (String path : resourcePaths) {
            ResponseEntity<String> res = restTemplate.getForEntity(baseUrl + path, String.class);
            assertThat(res.getStatusCode().is2xxSuccessful())
                    .as("Resource " + path + " should be available")
                    .isTrue();

        }
    }

    @Test
    void envPageAvailable() {
        // Load main page
        String baseUrl = "http://localhost:" + port + "/env";
        ResponseEntity<String> mainPage = restTemplate.getForEntity(baseUrl, String.class);
        assertThat(mainPage.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(mainPage.getBody()).isNotNull();
    }
}