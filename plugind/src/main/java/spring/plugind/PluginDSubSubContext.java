package spring.plugind;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.core.SomeService;

@Configuration
public class PluginDSubSubContext {

    @Bean
    public SomeService pluginDSubSubSomeService() {
        return new PluginDSubSubSomeService();
    }
}
