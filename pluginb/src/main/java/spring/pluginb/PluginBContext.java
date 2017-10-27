package spring.pluginb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.core.Context;
import spring.core.MainService;
import spring.core.SomeService;

@Configuration
public class PluginBContext implements Context {

    @Bean
    public SomeService pluginBSomeService(MainService mainService) {
        return new PluginBSomeService(mainService);
    }
}