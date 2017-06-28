package spring.core.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.core.SomeService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SomeServicesManager {
    private final List<SomeService> someServices;

    @Autowired public SomeServicesManager(List<SomeService> someServices) {
        this.someServices = someServices;
    }

    public Set<String> getServicesNames() {
        Set<String> result = new HashSet<>();
        for(SomeService someService : someServices) {
            result.add(someService.getName());
        }

        return result;
    }
}
