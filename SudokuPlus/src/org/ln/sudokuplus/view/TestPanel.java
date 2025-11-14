package org.ln.sudokuplus.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.ln.sudokuplus.model.SudokuConstants;
import org.ln.sudokuplus.view.cell.AbstractCell;
import org.ln.sudokuplus.view.cell.CardCell;
@SuppressWarnings("serial")
public class TestPanel extends JPanel {

    JButton jButton1;
    JButton jButton2;
    JButton jButton3;
    JButton[] numbers = new JButton[SudokuConstants.GRID_SIZE];
    JPanel buttonPanel = new JPanel(new GridLayout());
    JPanel numberPanel = new JPanel(new GridLayout(1, SudokuConstants.GRID_SIZE));
    JPanel cellPanel = new JPanel(new GridLayout(1, SudokuConstants.GRID_SIZE));
    JPanel container = new JPanel(new BorderLayout());
    AbstractCell selected;
    CardCell[] cells = new CardCell[SudokuConstants.GRID_SIZE];

    public TestPanel() {
		super();
		initButtonPanel();
		initNumberPanel();
		initCellPanel();
		container.add(buttonPanel, BorderLayout.NORTH);
		container.add(cellPanel, BorderLayout.CENTER);
		container.add(numberPanel, BorderLayout.SOUTH);
    }

    
    
	private void initCellPanel() {
                for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
			cells[i] = new CardCell(1, i);
			cells[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("clicked   "+e);
					AbstractCell pc = (AbstractCell) e.getSource();
					System.out.println("is focusable   "+pc.isFocusable());
					pc.requestFocusInWindow();
					selected = pc;
					System.out.println("has focus  "+pc.hasFocus());
				}
			});

			cells[i].addFocusListener(new FocusAdapter() {

				@Override
				public void focusLost(FocusEvent e) {
//					System.out.println("focus lost   "+e);
					AbstractCell pc = (AbstractCell) e.getSource();
					pc.setBackground(Color.white);
				}

				@Override
				public void focusGained(FocusEvent e) {
//					System.out.println("focus gained   "+e);
					AbstractCell pc = (AbstractCell) e.getSource();
					pc.setBackground(Color.yellow);
				}
			});
			cellPanel.add(cells[i]);
		}
	}



	private void initButtonPanel() {
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

       jButton1.setText("Note Off");
       jButton1.addActionListener(e -> {
		System.out.println("action  "+e.getActionCommand());
		JButton button = (JButton) e.getSource();
		if(e.getActionCommand().equals("Note Off")) {
			button.setText("Note On");
		}else {
			button.setText("Note Off");
		}
		System.out.println("selected  "+selected);
		
		for (int i = 0; i < cells.length; i++) {
			//if(cells[i].getSudokuCell().getLabel().getText().equals("")) {
				//cells[i].setMode(CardCell.NOTEPANEL);
//				}
		}
	});
        buttonPanel.add(jButton1);

        jButton2.setText("jButton2");
        buttonPanel.add(jButton2);

        jButton3.setText("jButton3");
        buttonPanel.add(jButton3);
	}

	private void initNumberPanel() {
		for (int i = 0; i < numbers.length; i++) {
                        numbers[i] = new JButton((i+1)+"");
                        numbers[i].setPreferredSize(new Dimension(SudokuConstants.CELL_SIZE, SudokuConstants.CELL_SIZE));
			numbers[i].setFocusable(false);
			numbers[i].addActionListener(new BtNumberListener());
			numberPanel.add(numbers[i]);
		}
                numberPanel.setPreferredSize(new Dimension(SudokuConstants.BOARD_WIDTH, SudokuConstants.CELL_SIZE));
	}
	class BtNumberListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("action  "+e.getActionCommand());
			if(selected == null) {
				return;
			}
			selected.getCardCell().setNumber(e.getActionCommand());
		}
		
	}


	private void display() {
        JFrame f = new JFrame("Test");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.getContentPane().add(container);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new TestPanel().display();
        });
    }
}
