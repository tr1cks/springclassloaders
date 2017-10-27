package spring.plugina;

import org.springframework.beans.factory.annotation.Autowired;
import spring.core.MainService;
import spring.core.SomeService;

public class PluginASomeService implements SomeService {
    @Autowired private MainService mainService;

    @Override public String getName() {
        return mainService.getName() + '#' + getClass().getSimpleName();
    }
}
