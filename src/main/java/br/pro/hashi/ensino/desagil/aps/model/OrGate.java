package br.pro.hashi.ensino.desagil.aps.model;

public class OrGate extends Gate {
    private final NandGate nanda;
    private final NandGate nandb;
    private final NandGate nandl;


    public OrGate() {
        super(2);

        nanda = new NandGate();
        nandb = new NandGate();
        nandl = new NandGate();
    }


    @Override
    public boolean read() {

        return nandl.read();
    }


    @Override
    public void connect(int inputPin, SignalEmitter emitter) {
        if (inputPin < 0 || inputPin > 1) {
            throw new IndexOutOfBoundsException(inputPin);
        }
        if (inputPin == 0) {
            nanda.connect(0, emitter);
            nanda.connect(1, emitter);
        }
        if (inputPin == 1) {
            nandb.connect(0, emitter);
            nandb.connect(1, emitter);
        }
        nandl.connect(0, nanda);
        nandl.connect(1, nandb);
    }
}
