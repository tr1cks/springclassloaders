package spring.pluginb;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spring.core.Context;

@Configuration
@ComponentScan(
    basePackages = {"spring.pluginb"}
)
public class PluginBContext implements Context {

}