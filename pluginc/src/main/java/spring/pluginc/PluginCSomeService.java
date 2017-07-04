package spring.pluginc;

import spring.core.SomeService;

public class PluginCSomeService implements SomeService {

    @Override public String getName() {
        return getClass().getSimpleName();
    }
}
