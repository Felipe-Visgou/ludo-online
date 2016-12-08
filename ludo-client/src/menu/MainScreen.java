//INF1636 - Programação orientada a objetos - LUDO GAME
//Professor: Ivan Matias
//Turma: 3WA
//Nome: Fernando Homem da Costa  - 1211971
//		Mateus Ribeiro de Castro - 1213068

package menu;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import view.Board;

@SuppressWarnings("serial")
public class MainScreen extends JFrame implements ActionListener {
	JButton iniciar = new JButton("INICIAR");
	JButton carregar = new JButton("CARREGAR");
	JButton sair = new JButton("SAIR");
	JPanel central = new JPanel();
	JFrame frame = new JFrame();

	public static void main(String[] args) {
		new MainScreen();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sair) {
			System.exit(0);
		} else if (e.getSource() == iniciar) {
			frame.setVisible(false); // Esconde a Tela Principal
			
			String nickname = JOptionPane.showInputDialog("Digite o seu nick.");
			
			if (nickname != null) {
				Board.getInstance().initializeBoard(nickname);// DEFAULT da Board.
														// -- Singleton
			}
			else{
				System.exit(0);
			}
		}
	}

	public MainScreen() {
		this.setTitle("LUDO & FRIENDS");

		iniciar.addActionListener(this);
		sair.addActionListener(this);
		JLabel image = new JLabel(makeImage());

		Toolkit tk = Toolkit.getDefaultToolkit();

		Dimension dim = tk.getScreenSize();

		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);

		central.setOpaque(false);
		central.setLocation(512, 250);
		central.setSize(300, 300);
		central.setLayout(new GridLayout(0, 1, 0, 20));
		central.add(iniciar);
		central.add(sair);

		Container c = image;

		frame.getContentPane().add(central);
		frame.getContentPane().add(c);
		frame.pack();

		frame.setLocation(xPos, yPos);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	// Cria a Imagem de Background e retorna um Icon com Background
	private ImageIcon makeImage() {

		BufferedImage result = null;
		try {

			result = ImageIO.read(new File("src/ImageMenu/LudoGameFinal.jpg"));

		} catch (IOException e) {

			System.out.println("Erro: Image Background not found!");
			System.exit(0);
		}

		return new ImageIcon(result);
	}

}
