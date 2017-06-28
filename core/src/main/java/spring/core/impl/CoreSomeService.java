package spring.core.impl;

import org.springframework.stereotype.Service;
import spring.core.SomeService;

@Service
public class CoreSomeService implements SomeService {

    public String getName() {
        return getClass().getSimpleName();
    }
}
