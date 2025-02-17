//package org.ln.sudokuplus.controller;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import org.ln.sudokuplus.model.SudokuConstants.CellMode;
//import org.ln.sudokuplus.view.cell.CardCell;
////
////public class ButtonNumberListener implements ActionListener {
////
////
////	private SudokuController controller;
////
////
////
////	public ButtonNumberListener(SudokuController controller) {
////		super();
////		this.controller = controller;
////	}
////
////
////	@Override
////	public void actionPerformed(ActionEvent e) {
////		//System.out.println("action  "+e.getActionCommand());
////		CardCell cc = controller.getView().getBoard().getSelected();
////		if(cc == null)
////			return;
////		
////		// due possibilità
////		System.out.println("mode  "+controller.isModeNote());
////		if(controller.isModeNote()) {
////			cc.setMode(CellMode.NOTEPANEL);
////			cc.getNoteCell().setNoteText(e.getActionCommand(), Integer.parseInt(e.getActionCommand())-1);
////		}else {
////			cc.setMode(CellMode.CELLPANEL);
////			cc.setNumber(e.getActionCommand());
////			
////			controller.checkDisableButton(Integer.parseInt(e.getActionCommand()));
////			controller.getView().getBoard().checkCancellNote(cc);
////			controller.checkSolved();
////		}
////	}
////}
