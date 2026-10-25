public class Wire {

    private final LogicGate source;
    private final LogicGate target;
    private final int targetInputIndex;

    private boolean signal = false;

    public Wire(LogicGate source, LogicGate target, int targetInputIndex) {
        this.source = source;
        this.target = target;
        this.targetInputIndex = targetInputIndex;
    }

    public boolean getSignal() {
        return signal;
    }

    // Read source output, store it, and feed target input
    public boolean propagate() {
        boolean newSignal = source.getOutput();
        signal = newSignal;
        target.setInput(targetInputIndex, newSignal);
        return newSignal;
    }
}
