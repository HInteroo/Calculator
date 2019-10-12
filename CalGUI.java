package calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.ScriptEngine;

public class CalGUI extends JFrame{

	public CalGUI(){
		Panel chatPanel = new Panel();
		add(chatPanel);
	}
	
	private class Panel extends JPanel implements ActionListener{	
		private JPanel displayArea;				
		private String[] numberBtn = {"1","2", "3", "4","5","6","7","8","9","0",".","="};
		private String[] symbolBtn = {"+","-","/","*"};
		private JButton [] Buttons = new JButton[numberBtn.length+symbolBtn.length];
		private String Equation = "";
		private JLabel label = new JLabel();
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		
		public Panel(){
			setLayout(new BorderLayout());
			displayArea = new JPanel();
			displayArea.setBackground(Color.white);
			
			label.setText("");
	    	Font labelFont = new Font("Arial", Font.BOLD, 20);
			label.setFont(labelFont);
			add(displayArea, BorderLayout.NORTH);
			displayArea.add(label);
			label.setPreferredSize(new Dimension(330, 60));
			displayArea.setPreferredSize(new Dimension(370, 80));
			
			JPanel numberPanel = new JPanel();
			numberPanel.setLayout(new GridLayout(4,4));
			
			int SymbolIndex = 0, numIndex = 0;
	    	Font bBold = new Font("Arial", Font.BOLD, 14);
	    	
			for(int i=0; i<Buttons.length; i++){
				if((i+1)%4 == 0) {
					Buttons[i] = new JButton(symbolBtn[SymbolIndex]);
					Buttons[i].addActionListener(this);
					Buttons[i].setEnabled(true);
					Buttons[i].setBackground(Color.decode("#a5c2d6"));
					SymbolIndex++;
				}
				else {
					Buttons[i] = new JButton(numberBtn[numIndex]);
					Buttons[i].addActionListener(this);
					Buttons[i].setEnabled(true);
					if(numberBtn[numIndex] == "=") {
						Buttons[i].setBackground(Color.decode("#0086e6"));
						Buttons[i].setForeground(Color.WHITE);
					}
					else {
						Buttons[i].setBackground(Color.decode("#e0f2ff"));
					}
					numIndex++;
				}
				Buttons[i].setFont(bBold);
				Buttons[i].setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
				numberPanel.add(Buttons[i]);
			}
			add(numberPanel, BorderLayout.CENTER);
			
		}
		
		public void SolveEquation() {
			try {
				Equation = String.valueOf(engine.eval(Equation));
				System.out.println(Equation);
			} catch (ScriptException e1) {
				e1.printStackTrace();
			}
		}
		@SuppressWarnings("unlikely-arg-type")
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btnClicked = (JButton) e.getSource();
			String buttonText = btnClicked.getText();
			
			if (Arrays.asList(symbolBtn).contains(buttonText)){
				String lastChar = Character.toString(Equation.charAt(Equation.length()-1));
				if(Arrays.asList(symbolBtn).contains(lastChar)) {
					Equation = Equation.substring(0,Equation.length() - 1)+buttonText;
					this.label.setText(Equation);
//					System.out.println(Equation);
				}
				else {
					Equation +=buttonText;
					this.label.setText(Equation);
				}
			}
			else{
				if(buttonText == "=") {
					SolveEquation();
					this.label.setText(Equation);
					Equation ="";
				}
				else {
					Equation +=buttonText;
					this.label.setText(Equation);
				}
			}
		}
	}
	public static void main(String[] args) throws ScriptException {
//		System.out.println(a[0]);
		CalGUI gui = new CalGUI();
		gui.setSize(370, 500);
		gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
		gui.setVisible(true);	
	}

}