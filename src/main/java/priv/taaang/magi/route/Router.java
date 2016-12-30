package priv.taaang.magi.route;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by TdaQ on 16/12/12.
 */
public class Router {

    private String mBaseRoute;
    private Map<String, Entry> mEntryMapping = new HashMap<>();


    public Router(String baseRoute, Class clazz) {
        buildFromClass(baseRoute, clazz);
    }

    private void buildFromClass(String baseRoute, Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method each : methods) {
            Entry entry = new Entry(each);
            mEntryMapping.put(entry.getEntryRoute(), entry);
        }
        mBaseRoute = baseRoute;
    }

    public Optional<Entry> matchEntry(String requestRoute) {
        String entryRoute = requestRoute.replace(mBaseRoute, "");
        Optional<String> entryMapping = mEntryMapping.keySet().stream()
                .filter(each -> Pattern.compile(each).matcher(entryRoute).find())
                .findFirst();
        return entryMapping.isPresent() ? Optional.of(mEntryMapping.get(entryMapping.get())) : Optional.empty();
    }
}
