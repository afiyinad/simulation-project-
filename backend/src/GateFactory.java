public class GateFactory {

    public static LogicGate createGate(String type) {
        switch (type) {
            case "AND": return new AndGate();
            case "OR":  return new OrGate();
            case "NOT": return new NotGate();
            case "XOR": return new XorGate();

            case "NAND": return new NandGate();
            case "NOR": return new NorGate();
            case "XNOR": return new XnorGate();
            default:
                throw new IllegalArgumentException("Unknown gate type: " + type);
        }
    }
}
