package br.pro.hashi.ensino.desagil.aps.model;

public class XorGate extends Gate {
    private final NandGate nandu;
    private final NandGate nandd;
    private final NandGate nandt;
    private final NandGate nandq;

    public XorGate() {
        super(2);

        nandu = new NandGate();
        nandd = new NandGate();
        nandt = new NandGate();
        nandq = new NandGate();
    }


    @Override
    public boolean read() {
        return nandq.read();
    }


    @Override
    public void connect(int inputPin, SignalEmitter emitter) {
        if (inputPin < 0 || inputPin > 1) {
            throw new IndexOutOfBoundsException(inputPin);
        }

        nandu.connect(inputPin, emitter);
        nandu.connect(inputPin, emitter);
        if (inputPin == 0) {
            nandd.connect(0, emitter);
        }
        nandd.connect(1, nandu);

        if (inputPin == 1) {
            nandt.connect(1, emitter);
        }
        nandt.connect(0, nandu);

        nandq.connect(0, nandd);
        nandq.connect(1, nandt);
    }
}
