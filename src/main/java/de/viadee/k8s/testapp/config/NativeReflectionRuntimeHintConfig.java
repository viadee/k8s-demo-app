package de.viadee.k8s.testapp.config;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;


@Configuration
@RegisterReflectionForBinding(classes = {HashMap.class})
@ImportRuntimeHints(NativeReflectionRuntimeHintConfig.NativeRuntimeHints.class)
@EnableWebMvc
public class NativeReflectionRuntimeHintConfig {

    static class NativeRuntimeHints implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {

            // especially for rendering k8s_env.html
            hints.reflection().registerType(Map.Entry.class, MemberCategory.values());
            // hints.reflection().registerType(HashMap.class, MemberCategory.values());
            // Innere Klassen
           hints.reflection().registerTypeIfPresent(classLoader, "java.util.HashMap$Node"
                    , MemberCategory.values());

            hints.resources().registerPattern("META-INF/resources/webjars/bootstrap/5.3.3/css/bootstrap.min.css");
            hints.resources().registerPattern("META-INF/resources/webjars/bootstrap/5.3.3/js/bootstrap.min.js");
        }
    }
}
