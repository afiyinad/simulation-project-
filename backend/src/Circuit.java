import java.util.ArrayList;
import java.util.List;

public class Circuit {

    private final List<LogicGate> gates = new ArrayList<>();
    private final List<Wire> wires = new ArrayList<>();

    public void addGate(LogicGate gate) {
        gates.add(gate);
    }

    public void addWire(Wire wire) {
        wires.add(wire);
    }

    public List<LogicGate> getGates() {
        return gates;
    }

    public List<Wire> getWires() {
        return wires;
    }
}
