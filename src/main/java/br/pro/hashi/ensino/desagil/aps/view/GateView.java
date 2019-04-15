package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Light;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class GateView extends FixedPanel implements ItemListener, MouseListener {
    private final Switch[] switches;
    private final Gate gate;
    private final Light light;

    private final JCheckBox[] inputBoxes;
    //private final JCheckBox outputBox;

    // Novos atributos necessários para esta versão da interface.
    private final Image image;
    private Color color;


    public GateView(Gate gate) {
        super(255, 170);

        this.light = new Light();
        this.gate = gate;

        int inputSize = gate.getInputSize();

        switches = new Switch[inputSize];
        inputBoxes = new JCheckBox[inputSize];


        for (int i = 0; i < inputSize; i++) {
            switches[i] = new Switch();
            inputBoxes[i] = new JCheckBox();

            gate.connect(i, switches[i]);
        }

        light.setR(250);
        light.setG(150);
        light.setB(150);
        //light.connect(0, gate);

        //outputBox = new JCheckBox();

        JLabel inputLabel = new JLabel("Input");
        JLabel outputLabel = new JLabel("Output");

        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        int y = 0;
        add(inputLabel, 10, 10, 75, 25);
        for (JCheckBox inputBox : inputBoxes) {
            if (inputSize == 1) {
                y += 34;
            }
            add(inputBox, 15, 43 + y, 20, 15);
            y += 70;
        }
        add(outputLabel, 205, 10, 75, 25);
        //add(outputBox,220, 43+34, 18, 15);

        // Inicializamos o atributo de cor simplesmente como preto.
        //color = new Color(light.getR(),light.getG(),light.getB());

        for (JCheckBox inputBox : inputBoxes) {
            inputBox.addItemListener(this);
        }

        // Usamos esse carregamento nos Desafios, vocês lembram?
        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);


        addMouseListener(this);

        //outputBox.setEnabled(false);

        update();
    }

    private void update() {
        for (int i = 0; i < gate.getInputSize(); i++) {
            if (inputBoxes[i].isSelected()) {
                switches[i].turnOn();
            } else {
                switches[i].turnOff();
            }
        }

        light.connect(0, gate);
        //boolean result = gate.read();
        //outputBox.setSelected(result);
        repaint();
    }

    //@Override
    public void actionPerformed(ActionEvent event) {
        update();
    }

    @Override
    public void mouseClicked(MouseEvent event) {

        // Descobre em qual posição o clique ocorreu.
        int x = event.getX();
        int y = event.getY();

        // Se o clique foi dentro do quadrado colorido...
        if (sqrt(pow(x - 231, 2) + pow(y - 85, 2)) <= 12) {
            //Color color = new Color(0,0,0);
            // ...então abrimos a janela seletora de cor...
            color = JColorChooser.showDialog(this, null, color);
            light.setR(color.getRed());
            light.setG(color.getGreen());
            light.setB(color.getBlue());

            // ...e chamamos repaint para atualizar a tela.
            repaint();
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de pressionar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de soltar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // entrar no painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseExited(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // sair do painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void paintComponent(Graphics g) {

        // Não podemos esquecer desta linha, pois não somos os
        // únicos responsáveis por desenhar o painel, como era
        // o caso nos Desafios. Agora é preciso desenhar também
        // componentes internas, e isso é feito pela superclasse.
        super.paintComponent(g);

        // Desenha a imagem, passando sua posição e seu tamanho.
        g.drawImage(image, 30, 10, 200, 150, this);

        // Desenha um quadrado cheio.


        g.setColor(new Color(light.getR(), light.getG(), light.getB()));


        g.fillOval(220, 39 + 34, 23, 23);

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        update();
    }
}
