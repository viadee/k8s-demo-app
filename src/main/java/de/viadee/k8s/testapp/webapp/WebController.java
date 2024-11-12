package de.viadee.k8s.testapp.webapp;

import com.zaxxer.hikari.HikariDataSource;
import de.viadee.k8s.testapp.counter.CounterEntity;
import de.viadee.k8s.testapp.counter.CounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WebController {

    Logger log = LoggerFactory.getLogger(WebController.class.getName());

    @Autowired
    private CounterService counterService;

    @Autowired
    private Environment env;

    @Autowired
    private HikariDataSource dataSource;

    @Value("${welcome.text:Welcome on pod}")
    private String welcomeText;

    @Value(value = "${nav.bgcolor:}")
    private String bgcolor;

    @GetMapping("/")
    public String k8s_index(Model model) {

        initBgColor(model);

        String jdbcUrl = dataSource.getJdbcUrl();
        if(jdbcUrl.contains(";")) {
            jdbcUrl = StringUtils.split(dataSource.getJdbcUrl(), ";")[0];
        }

        String hostname = "unknown";
        if(System.getenv().containsKey("HOSTNAME")) {
            hostname = System.getenv().get("HOSTNAME");
        }

        String profiles = String.join(",", env.getDefaultProfiles());
        if(env.getActiveProfiles().length>0) {
            profiles = String.join(",", env.getActiveProfiles());
        }

        CounterEntity counter = counterService.getCounterAndIncrement();
        model.addAttribute("counter_value", counter.getCounter().toString());
        model.addAttribute("counter_created", counter.getCreated().toString());
        model.addAttribute("hostname", hostname);
        model.addAttribute("jdbc_url", jdbcUrl);
        model.addAttribute("profiles", profiles);
        model.addAttribute("welcome_text", welcomeText);

        return "k8s_index";
    }

    @GetMapping("/env")
    public String k8s_env(Model model) {
        initBgColor(model);
        Map<String, String> env = new HashMap<>(System.getenv());

        model.addAttribute("env", env);

        return "k8s_env";
    }

    private void initBgColor(Model model) {
        if(!bgcolor.isEmpty()) {
            model.addAttribute("bgcolor", bgcolor);
        }
    }
}
