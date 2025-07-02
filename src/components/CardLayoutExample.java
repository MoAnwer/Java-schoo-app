package src.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardLayoutExample {

    public static void createAndShowGUI() {
        // إعداد النافذة الرئيسية
        JFrame frame = new JFrame("Components Cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 400);
        frame.setLocationRelativeTo(null);

        // لوحة رئيسية باستخدام GridLayout مع 3 أعمدة ومسافات
        JPanel mainPanel = new JPanel(new GridLayout(1, 3, 30, 0)); // 30px مسافة أفقية
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30)); // هوامش خارجية
        mainPanel.setBackground(new Color(240, 240, 240)); // لون خلفية فاتح

        // إنشاء 3 بطاقات متطابقة
        for (int i = 0; i < 3; i++) {
            mainPanel.add(createCard());
        }

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createCard() {
        // إنشاء البطاقة
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(15, new Color(200, 200, 200)), // حدود دائرية رمادية
                new EmptyBorder(25, 25, 25, 25) // هوامش داخلية
        ));

        // رقم البطاقة
        JLabel numberLabel = new JLabel("64+");
        numberLabel.setFont(new Font("Arial", Font.BOLD, 36));
        numberLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        numberLabel.setForeground(new Color(50, 50, 50));

        // عنوان البطاقة
        JLabel titleLabel = new JLabel("Components Created");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setForeground(new Color(70, 70, 70));

        // وصف البطاقة
        JTextArea description = new JTextArea("Every component is made in house and battle tested. Collect them all!");
        description.setFont(new Font("Arial", Font.PLAIN, 14));
        description.setAlignmentX(Component.LEFT_ALIGNMENT);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setBackground(Color.WHITE);
        description.setBorder(new EmptyBorder(10, 0, 15, 0)); // هوامش فوق وتحت النص

        // زر "Learn more"
        JButton button = new JButton("Learn more");
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 120, 215)); // أزرق
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15)); // هوامش داخل الزر
        button.addActionListener(e -> JOptionPane.showMessageDialog(null, "Learn more clicked!"));

        card.add(numberLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10))); // مسافة رأسية
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 15))); // مسافة رأسية
        card.add(description);
        card.add(Box.createRigidArea(new Dimension(0, 10))); // مسافة رأسية
        card.add(button);

        return card;
    }

    static class RoundBorder implements javax.swing.border.Border {
        private final int radius;
        private final Color color;

        public RoundBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(radius, radius, radius, radius);
        }

        public boolean isBorderOpaque() {
            return false;
        }
    }
}