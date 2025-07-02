package src.components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FadeButton extends JButton {

    private final Color foreground;
    private final Color clickBackground;
    private final Color unClickBackground;

    int paddingWidth = 10, paddingHeight = 3;

    public FadeButton(String text, Color unClickBackground, Color clickBackground, Color foreground) {
        this.unClickBackground = unClickBackground;
        this.clickBackground = clickBackground;
        this.foreground = foreground;

        setText(text);

        Dimension dimension = getPreferredSize();
        int w = (int) dimension.getWidth() + paddingWidth * 3;
        int h = (int) dimension.getHeight() + paddingHeight * 3;

        setPreferredSize(new Dimension(w, h));
        setOpaque(false);
        setBorder(null);
        setBackground(unClickBackground);
        setForeground(foreground);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(clickBackground);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(unClickBackground);
            }
        });
    }

    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Dimension dimension = getPreferredSize();
        int w = (int) dimension.getWidth();
        int h = (int) dimension.getHeight();

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, w, h, 10, 10);

        g2.setColor(getForeground());
        g2.setFont(new Font("poppins", Font.PLAIN, 13));

        FontMetrics fontMetrics = g2.getFontMetrics();
        Rectangle rectangle = fontMetrics.getStringBounds(getText(), g2).getBounds();

        g2.drawString(getText(), (w - rectangle.width) / 2, (h - rectangle.height) / 2 + fontMetrics.getAscent());
    }
}