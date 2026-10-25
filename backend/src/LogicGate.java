public interface LogicGate {

    // Set an input pin by index (0, 1, ...)
    void setInput(int index, boolean value);

    // Compute the output from current inputs (pure logic)
    boolean compute();

    // Stored output value (used by wires)
    boolean getOutput();

    // Update stored output after compute()
    void setOutput(boolean o);
}
