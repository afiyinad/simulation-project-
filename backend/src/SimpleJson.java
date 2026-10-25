import java.util.*;

public class SimpleJson {

    // VERY basic JSON parser for your simple circuit format
    public static Map<String, Object> parse(String json) {
        json = json.trim();

        Map<String, Object> result = new HashMap<>();

        // Extract gates array
        String gatesSection = extractArray(json, "gates");
        List<Map<String, Object>> gates = parseGateArray(gatesSection);
        result.put("gates", gates);

        // Extract wires array
        String wiresSection = extractArray(json, "wires");
        List<Map<String, Object>> wires = parseWireArray(wiresSection);
        result.put("wires", wires);

        return result;
    }

    private static String extractArray(String json, String key) {
        int start = json.indexOf("\"" + key + "\"");
        start = json.indexOf("[", start);
        int end = json.indexOf("]", start);
        return json.substring(start + 1, end);
    }

    private static List<Map<String, Object>> parseGateArray(String arr) {
        List<Map<String, Object>> list = new ArrayList<>();

        String[] objects = arr.split("\\},\\{");
        for (String obj : objects) {
            Map<String, Object> map = new HashMap<>();

            obj = obj.replace("{", "").replace("}", "");

            for (String part : obj.split(",")) {
                String[] kv = part.split(":");
                String key = kv[0].replace("\"", "").trim();
                String val = kv[1].replace("\"", "").trim();

                if (key.equals("id") || key.equals("type")) {
                    map.put(key, val);
                } else if (key.equals("inputs")) {
                    List<Boolean> inputs = new ArrayList<>();
                    for (String b : val.replace("[", "").replace("]", "").split(" ")) {
                        if (b.equals("true") || b.equals("false")) {
                            inputs.add(Boolean.parseBoolean(b));
                        }
                    }
                    map.put(key, inputs);
                }
            }

            list.add(map);
        }

        return list;
    }

    private static List<Map<String, Object>> parseWireArray(String arr) {
        List<Map<String, Object>> list = new ArrayList<>();

        String[] objects = arr.split("\\},\\{");
        for (String obj : objects) {
            Map<String, Object> map = new HashMap<>();

            obj = obj.replace("{", "").replace("}", "");

            for (String part : obj.split(",")) {
                String[] kv = part.split(":");
                String key = kv[0].replace("\"", "").trim();
                String val = kv[1].replace("\"", "").trim();

                if (key.equals("from") || key.equals("to")) {
                    map.put(key, val);
                } else if (key.equals("inputIndex")) {
                    map.put(key, Integer.parseInt(val));
                }
            }

            list.add(map);
        }

        return list;
    }
}
