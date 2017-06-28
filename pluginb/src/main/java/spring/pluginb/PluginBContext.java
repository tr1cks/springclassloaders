package spring.pluginb;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import spring.core.Context;

@Service
@ComponentScan(
    basePackages = {"spring.pluginb"}
)
public class PluginBContext implements Context {

}