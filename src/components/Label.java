package src.components;

import java.awt.*;
import javax.swing.*;

public class Label extends JLabel {

    public Label(String text) {
        super(text);
        setFont(new Font("poppins", Font.PLAIN, 16));
        setForeground(Color.WHITE);
    }

    public Label(String text, Color fontColor, int fontSize) {
        setFont(new Font("poppins", Font.PLAIN, fontSize));
        setForeground(fontColor);
        setText(text);
    }

    public Label(String text, Color fontColor, String style, int fontSize) {
        if (style.equals("bold")) {
            setFont(new Font("poppins", Font.BOLD, fontSize));
        } else if (style.equals("plain")) {
            setFont(new Font("poppins", Font.PLAIN, fontSize));
        }
        setForeground(fontColor);
        setText(text);
    }

}
