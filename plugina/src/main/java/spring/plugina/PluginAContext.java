package spring.plugina;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.core.Context;
import spring.core.SomeService;

@Configuration
public class PluginAContext implements Context {

    @Bean
    public SomeService pluginASomeService() {
        return new PluginASomeService();
    }
}