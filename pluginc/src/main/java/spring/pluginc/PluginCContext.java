package spring.pluginc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.core.Context;
import spring.core.SomeService;

@Configuration
public class PluginCContext implements Context {

    @Bean
    public SomeService pluginCSomeService() {
        return new PluginCSomeService();
    }
}
