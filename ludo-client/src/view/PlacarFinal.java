package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Score;

@SuppressWarnings("serial")
public class PlacarFinal extends JFrame{

	JButton closePlacar;
	ActionHandler aHandler;
	JLabel PlacarRed;
	JLabel PlacarBlue;
	JLabel PlacarYellow;
	JLabel PlacarGreen;

	private static PlacarFinal PlacarFinalfirstInstance = null;

	//Singleton da Class -- ConjuntoDePinos
	public static PlacarFinal getInstancce(){
		if(PlacarFinalfirstInstance == null){

			PlacarFinalfirstInstance = new PlacarFinal();
		}

		return PlacarFinalfirstInstance;
	}

	public PlacarFinal(){
		closePlacar = new JButton("OK");
		PlacarRed = new JLabel();
		PlacarBlue = new JLabel();
		PlacarYellow = new JLabel();
		PlacarGreen = new JLabel();

		//Criando Box para dividir o Frame em partes:Score e Botao.
		Box thebox = Box.createVerticalBox();
		Box theboxH = Box.createHorizontalBox();

		//Definindo configuracoes defaults para o JFrame
		this.setSize(200,150);
		this.setTitle("PLACAR FINAL");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		aHandler = new ActionHandler();

		//Adicionando ActionListner ao botao RollDice
		closePlacar.addActionListener(aHandler);

		//Criando os Labels do score

		if(Score.getInstancce().ScoreRed == 0){
			PlacarRed.setText("VENCEDOR Red: " + Integer.toString(Score.getInstancce().getScoreRed()));

		}

		else{
			PlacarRed.setText("Red: " + Integer.toString(Score.getInstancce().getScoreRed()));

		}

		if(Score.getInstancce().ScoreBlue == 0){
			PlacarBlue.setText("VENCEDOR Blue: " + Integer.toString(Score.getInstancce().getScoreBlue()));
		}

		else{
			PlacarBlue.setText("Blue: " + Integer.toString(Score.getInstancce().getScoreBlue()));
		}

		if(Score.getInstancce().ScoreYellow == 0){
			PlacarYellow.setText("VENCEDOR Yellow: " + Integer.toString(Score.getInstancce().getScoreYellow()));
		}

		else{
			PlacarYellow.setText("Yellow: " + Integer.toString(Score.getInstancce().getScoreYellow()));
		}

		if(Score.getInstancce().ScoreGreen == 0){
			PlacarGreen.setText("VENCEDOR Green: " + Integer.toString(Score.getInstancce().getScoreGreen()));
		}

		else{
			PlacarGreen.setText("Green: " + Integer.toString(Score.getInstancce().getScoreGreen()));
		}
		
		//Adicionando os Labels do score ao box
		thebox.add(PlacarRed);
		thebox.add(PlacarBlue);
		thebox.add(PlacarYellow);
		thebox.add(PlacarGreen);

		//Adicionando o box do Save ao box
		theboxH.add(closePlacar);

		//Adicionando o box do dado para o panel
		this.add(thebox, BorderLayout.NORTH);
		this.add(theboxH, BorderLayout.CENTER);

		//Centralizando a Janela com Tela do PC
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width/2) - (this.getWidth()/2);
		int yPos = (dim.height/2) - (this.getHeight()/2);

		//Setting a posicao de centralizacao
		this.setLocation(xPos,yPos);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		//Mostrando a frame
		this.setVisible(true);
	}

	private class ActionHandler implements ActionListener{

		public void actionPerformed(ActionEvent e){
			if(e.getSource() == closePlacar){

				System.exit(0);

			}
		}
	}
}
