package spring.pluginc;

import org.springframework.beans.factory.annotation.Autowired;
import spring.core.MainService;
import spring.core.SomeService;

public class PluginCSomeService implements SomeService {
    private MainService mainService;

    @Override public String getName() {
        return mainService.getName() + '#' + getClass().getSimpleName();
    }

    @Autowired public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }
}