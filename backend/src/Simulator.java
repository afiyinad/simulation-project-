public class Simulator {

    // Single pass: propagate all wires once
    public void run(Circuit circuit) {
        for (Wire wire : circuit.getWires()) {
            wire.propagate();
        }
    }

    // Keep updating until circuit reaches a stable state
    public void stabilize(Circuit circuit) {
        boolean changed;

        do {
            changed = false;

            // Update gate outputs
            for (LogicGate gate : circuit.getGates()) {
                boolean oldOutput = gate.getOutput();
                boolean newOutput = gate.compute();

                if (oldOutput != newOutput) {
                    gate.setOutput(newOutput);
                    changed = true;
                }
            }

            // Propagate signals along wires
            for (Wire wire : circuit.getWires()) {
                boolean oldSignal = wire.getSignal();
                boolean newSignal = wire.propagate();

                if (oldSignal != newSignal) {
                    changed = true;
                }
            }

        } while (changed);
    }
}
