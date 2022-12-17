package com.company.gui;

import javax.swing.*;
import java.awt.*;

public class Terminal extends JPanel {
    JPanel terminal;
    JLabel jLabel;
    JTextField jTextField;
    PanelManager panel;

    public Terminal() {
        armarPanel();
    }
    public void armarPanel(){
        terminal = new JPanel();
        jLabel = new JLabel("Testtttttttttttttt");
        jTextField = new JTextField(20);

        terminal.add(jTextField);
        terminal.add(jLabel);

        add(terminal, BorderLayout.CENTER);
    }

}

