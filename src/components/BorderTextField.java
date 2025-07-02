package src.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.BorderFactory;

public class BorderTextField extends JTextField
{
 
    protected int width;
    protected int height;
    protected Color mainColor;
    protected Color textColor;
    protected boolean opaque;

    public BorderTextField()
    {
        this.mainColor = new Color(233, 244, 255);
        this.width = 100;
        this.height = 20;
        this.opaque = false;
        this.textColor = Color.WHITE;
        this.init();
    }

  

    public BorderTextField(int w, int h, Color mainColor)
    {
        this.mainColor = mainColor;
        this.width = w;
        this.height = h;
        this.opaque = false;
        this.textColor = Color.WHITE;
        this.init();
    }

    public BorderTextField(int w, int h, Color mainColor, Color textColor)
    {
        this.mainColor = mainColor;
        this.width = w;
        this.height = h;
        this.opaque = false;
        this.textColor = textColor;
        this.init();
    }

    public BorderTextField(String text)
    {
        this.mainColor = new Color(71, 120, 230);
        this.width = 200;
        this.height = 100;
        this.opaque = true;
        this.textColor = Color.BLACK;
        this.init();
        this.setText(text);
    }
    
    public void init()
    {
        this.setOpaque( this.opaque );
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, this.mainColor));
        this.setMargin(new Insets(50, 50, 50, 50));
        this.setFont(new Font("poppins", Font.PLAIN, 13));
        this.setForeground(this.textColor);
        this.setPreferredSize(new Dimension(this.width, this.height));
    }

    
}