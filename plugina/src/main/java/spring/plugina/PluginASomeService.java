package spring.plugina;

import org.springframework.stereotype.Service;
import spring.core.SomeService;

@Service
public class PluginASomeService implements SomeService {

    @Override public String getName() {
        return getClass().getSimpleName();
    }
}
