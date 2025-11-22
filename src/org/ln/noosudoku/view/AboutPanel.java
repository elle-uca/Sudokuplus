package org.ln.noosudoku.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Simple panel describing the application and authorship details.
 */
@SuppressWarnings("serial")
public class AboutPanel extends JPanel {

    /**
     * Builds the about content with a title and description.
     */
    public AboutPanel() {
        super(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        JLabel logoLabel = new JLabel(new ImageIcon(AboutPanel.class.getResource("/img/noos-logo.png")));

        JLabel title = new JLabel("NooSudoku");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));

        JTextArea description = new JTextArea();
        description.setEditable(false);
        description.setOpaque(false);
        description.setWrapStyleWord(true);
        description.setLineWrap(true);
        description.setFont(description.getFont().deriveFont(14f));
        description.setText("Gioco Sudoku realizzato con Swing. "
                + "Scegli un livello di difficolt\u00e0, applica un tema e utilizza le note "
                + "per risolvere gli schemi.");
        description.setPreferredSize(new Dimension(320, 120));

        add(title, BorderLayout.NORTH);
        add(description, BorderLayout.CENTER);
        add(logoLabel, BorderLayout.WEST);
    }
}

