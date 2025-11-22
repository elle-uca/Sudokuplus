package org.ln.noosudoku.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 * Lightweight preferences panel offering toggles for common options.
 */
@SuppressWarnings("serial")
public class PreferencesPanel extends JPanel {

    private final JCheckBox highlightConflicts;
    private final JCheckBox enableAdvancedNotes;

    /**
     * Creates a simple preferences layout with placeholder controls.
     */
    public PreferencesPanel() {
        super(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel content = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipady = 4;

        JLabel description = new JLabel("Personalizza le opzioni di gioco:");
        gbc.gridy = 0;
        content.add(description, gbc);

        gbc.gridy = 1;
        content.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);

        highlightConflicts = new JCheckBox("Evidenzia automaticamente righe e colonne correlate", true);
        gbc.gridy = 2;
        content.add(highlightConflicts, gbc);

        enableAdvancedNotes = new JCheckBox("Suggerisci note avanzate quando disponibili", true);
        gbc.gridy = 3;
        content.add(enableAdvancedNotes, gbc);

        JLabel footer = new JLabel("Le modifiche sono solo illustrative per questa demo.");
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        gbc.gridy = 4;
        content.add(footer, gbc);

        content.setPreferredSize(new Dimension(380, 180));
        add(content, BorderLayout.CENTER);
    }

    public boolean isHighlightConflictsEnabled() {
        return highlightConflicts.isSelected();
    }

    public boolean isEnableAdvancedNotes() {
        return enableAdvancedNotes.isSelected();
    }
}

