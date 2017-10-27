package spring.core.impl;

import org.springframework.stereotype.Service;
import spring.core.MainService;

@Service
public class MainServiceImpl implements MainService {

    @Override public String getName() {
        return "MainService";
    }
}
