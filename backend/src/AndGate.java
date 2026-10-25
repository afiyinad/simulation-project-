public class AndGate implements LogicGate {

    private final boolean[] inputs = new boolean[2];
    private boolean output = false;

    @Override
    public void setInput(int index, boolean value) {
        if (index >= 0 && index < inputs.length) {
            inputs[index] = value;
        }
    }

    @Override
    public boolean compute() {
        return inputs[0] && inputs[1];
    }

    @Override
    public boolean getOutput() {
        return output;
    }

    @Override
    public void setOutput(boolean o) {
        output = o;
    }
}


