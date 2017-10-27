package spring.plugind;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import spring.core.Context;
import spring.core.SomeService;

@Configuration
@Import(PluginDSubContext.class)
public class PluginDContext implements Context {

    @Bean
    public SomeService pluginDSomeService() {
        return new PluginDSomeService();
    }
}
