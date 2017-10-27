package spring.pluginb;

import spring.core.MainService;
import spring.core.SomeService;

public class PluginBSomeService implements SomeService {
    private final MainService mainService;

    public PluginBSomeService(MainService mainService) {
        this.mainService = mainService;
    }

    @Override public String getName() {
        return mainService.getName() + '#' + getClass().getSimpleName();
    }
}
