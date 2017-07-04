package spring.plugina;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spring.core.Context;

@Configuration
@ComponentScan(
    basePackages = {"spring.plugina"}
)
public class PluginAContext implements Context {

}