package spring.core.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.core.MainService;
import spring.core.SomeService;

@Service
public class CoreSomeService implements SomeService {
    private final MainService mainService;

    @Autowired public CoreSomeService(MainService mainService) {
        this.mainService = mainService;
    }

    public String getName() {
        return mainService.getName() + '#' + getClass().getSimpleName();
    }
}
