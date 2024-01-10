package plugin;

import java.util.List;
import java.util.Map;
import java.nio.file.Path;

public interface Plugin {
  List<Path> generate(Map<String, String> data);
}
