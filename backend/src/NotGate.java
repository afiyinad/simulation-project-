public class NotGate implements LogicGate {

    private boolean input = false;
    private boolean output = false;

    @Override
    public void setInput(int index, boolean value) {
        if (index == 0) {
            input = value;
        }
    }

    @Override
    public boolean compute() {
        return !input;
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
