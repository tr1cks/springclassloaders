package spring.plugind;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import spring.core.SomeService;

@Configuration
@Import(PluginDSubSubContext.class)
public class PluginDSubContext {

    @Bean
    public SomeService pluginDSubSomeService() {
        return new PluginDSubSomeService();
    }
}