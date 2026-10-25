public class XnorGate implements LogicGate {

    private boolean[] inputs = new boolean[2];
    private boolean output;

    @Override
    public void setInput(int index, boolean value) {
        inputs[index] = value;
    }


    @Override
    public void setOutput(boolean value) {
        this.output = value;
    }

    @Override
    public boolean getOutput() {
        return output;
    }

    @Override
    public boolean compute() {
        output = !(inputs[0] ^ inputs[1]);
        return output;
    }
}
