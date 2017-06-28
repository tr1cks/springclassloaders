package spring.core.impl;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spring.core.Context;

@Configuration
@ComponentScan(
    basePackages = {"spring.core"}
)
public class CoreContext implements Context {

}