package spring.plugina;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import spring.core.Context;

@Service
@ComponentScan(
    basePackages = {"spring.plugina"}
)
public class PluginAContext implements Context {

}