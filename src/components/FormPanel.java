package src.components;

import java.awt.*;
import javax.swing.*;

public class FormPanel extends JPanel {
    private int gap = 10;
    private JComponent components[];

    public FormPanel(int inputsPanelsCount, JComponent components[])
    {
        this.components = components;

        setLayout(new GridLayout(inputsPanelsCount,1, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(this.gap, this.gap, this.gap, this.gap));

        for (int i = 0; i < components.length; i++) 
        {
            add(components[i]);
        }
    }
}
