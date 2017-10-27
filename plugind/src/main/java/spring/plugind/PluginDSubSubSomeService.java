package spring.plugind;

import org.springframework.beans.factory.annotation.Autowired;
import spring.core.MainService;
import spring.core.SomeService;

public class PluginDSubSubSomeService implements SomeService {
    @Autowired private MainService mainService;

    @Override public String getName() {
        return mainService.getName() + '#' + getClass().getSimpleName();
    }
}