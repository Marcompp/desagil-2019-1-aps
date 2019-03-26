package br.pro.hashi.ensino.desagil.aps.model;

public class AndGate extends Gate {
    private final NandGate nandi;
    private final NandGate nandn;

    public AndGate() {
        super(2);

        nandi = new NandGate();
        nandn = new NandGate();
    }


    @Override
    public boolean read() {
        return nandn.read();
    }


    @Override
    public void connect(int inputPin, SignalEmitter emitter) {
        if (inputPin < 0 || inputPin > 1) {
            throw new IndexOutOfBoundsException(inputPin);
        }

        nandi.connect(inputPin, emitter);
        nandi.connect(inputPin, emitter);

        nandn.connect(0, nandi);
        nandn.connect(1, nandi);
    }
}
