package spring.pluginb;

import org.springframework.stereotype.Service;
import spring.core.SomeService;

@Service
public class PluginBSomeService implements SomeService {

    public String getName() {
        return getClass().getSimpleName();
    }
}
