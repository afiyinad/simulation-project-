import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import java.util.*;

public class Server {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/simulate", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {

                String body = new String(exchange.getRequestBody().readAllBytes());

                // Use your SimpleJson parser
                Map<String, Object> parsed = SimpleJson.parse(body);

                List<Map<String, Object>> gatesJson =
                        (List<Map<String, Object>>) parsed.get("gates");

                List<Map<String, Object>> wiresJson =
                        (List<Map<String, Object>>) parsed.get("wires");

                Circuit circuit = new Circuit();
                Map<String, LogicGate> gateMap = new HashMap<>();

                // Build gates
                for (Map<String, Object> g : gatesJson) {
                    String id = (String) g.get("id");
                    String type = (String) g.get("type");

                    LogicGate gate = GateFactory.createGate(type);

                    List<Boolean> inputs = (List<Boolean>) g.get("inputs");
                    for (int i = 0; i < inputs.size(); i++) {
                        gate.setInput(i, inputs.get(i));
                    }

                    gateMap.put(id, gate);
                    circuit.addGate(gate);
                }

                // Build wires
                for (Map<String, Object> w : wiresJson) {
                    String from = (String) w.get("from");
                    String to = (String) w.get("to");
                    int inputIndex = (int) w.get("inputIndex");

                    Wire wire = new Wire(gateMap.get(from), gateMap.get(to), inputIndex);
                    circuit.addWire(wire);
                }

                // Run simulation
                Simulator sim = new Simulator();
                sim.stabilize(circuit);

                // Build JSON response manually
                StringBuilder response = new StringBuilder();
                response.append("{\"outputs\":{");

                boolean first = true;
                for (String id : gateMap.keySet()) {
                    if (!first) response.append(",");
                    first = false;

                    response.append("\"")
                            .append(id)
                            .append("\":")
                            .append(gateMap.get(id).getOutput());
                }

                response.append("}}");

                byte[] bytes = response.toString().getBytes();
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            }
        }));

        server.start();
        System.out.println("Backend running on http://localhost:8080");
    }
}
