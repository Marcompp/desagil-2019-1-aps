package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// A classe JPanel representa uma das componentes mais
// simples da Swing. A função dela é simplesmente ser
// um contêiner para colocar outras componentes dentro.
// A razão de implementar ActionListener está mais abaixo.
public class GateView extends JPanel implements ActionListener {

    // A ideia é que essa componente gráfica represente
    // uma calculadora específica. Essa calculadora que
    // está sendo representada é guardada como atributo.
    private final Gate gate;

    // A classe JCheckBox representa um campo de texto.
    private final JCheckBox port1Field;
    private final JCheckBox port2Field;
    private final JCheckBox resultField;

    public GateView(Gate gate) {
        this.gate = gate;

        // Nada de especial na construção dos campos.
        port1Field = new JCheckBox();
        port2Field = new JCheckBox();
        resultField = new JCheckBox();

        // A classe JLabel representa um rótulo, ou seja,
        // um texto não-editável que queremos colocar na
        // interface para identificar alguma coisa. Não
        // precisa ser atributo, pois não precisamos mais
        // mexer nesses objetos depois de criar e adicionar.
        JLabel port1Label = new JLabel("Entrada");
        //JLabel port2Label = new JLabel("Porta B");
        JLabel resultLabel = new JLabel("Resultado");

        // Um JPanel tem um layout, ou seja, um padrão para
        // organizar as componentes dentro dele. A linha abaixo
        // estabelece um dos padrões mais simples: simplesmente
        // colocar uma componente debaixo da outra, alinhadas.
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Colocamos todas componentes aqui no contêiner.
        add(port1Label);
        add(port1Field);
        //add(port2Label);
        if (gate.getInputSize() > 1) {
            add(port2Field);
        }
        add(resultLabel);
        add(resultField);

        // Uma campo de texto tem uma lista de observadores que
        // reagem quando o usuário dá Enter depois de digitar.
        // Usamos o método addActionListener para adicionar esta
        // instância de CalculatorView, ou seja "this", nessa
        // lista. Só que addActionListener espera receber um objeto
        // do tipo ActionListener como parâmetro. É por isso que
        // adicionamos o "implements ActionListener" lá em cima.
        port1Field.addActionListener(this);
        port2Field.addActionListener(this);

        // O último campo de texto não pode ser editável, pois é
        // só para exibição. Logo, configuramos como desabilitado.
        resultField.setEnabled(false);

        // Update é o método que definimos abaixo para atualizar o
        // último campo de acordo com os valores dos primeiros.
        // Precisamos chamar esse método no final da construção
        // para garantir que a interface não nasce inconsistente.
        update();
    }

    private void update() {
        Switch port1 = new Switch();
        Switch port2 = new Switch();

        try {
            // O conteúdo de um campo é uma String, não um double.
            // Se queremos interpretar como double, precisamos fazer
            // uma conversão. Esse Double.parseDouble faz isso...
            if (port1Field.isSelected()) {
                port1.turnOn();
            } else {
                port1.turnOff();
            }
            if (port2Field.isSelected()) {
                port2.turnOn();
            } else {
                port2.turnOff();
            }

        } catch (NumberFormatException exception) {
            // ...e se a string não representar um double válido,
            // esse parseDouble lança um NumberFormatException.
            resultField.setText("?");
            return;
        }


        gate.connect(0, port1);
        if (gate.getInputSize() > 1) {
            gate.connect(1, port2);
        }
        boolean result = gate.read();

        // O contrário também vale! Para colocar um double no
        // campo, precisamos antes converter ele para String.
        resultField.setSelected(result);
    }

    // O que esta componente deve fazer quando o usuário der um
    // Enter depois de digitar? Apenas chamar o update, é claro!
    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }
}
