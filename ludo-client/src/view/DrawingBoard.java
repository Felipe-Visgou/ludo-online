package view;

import java.awt.geom.*;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import model.Observer;
import model.Path;
import model.Subject;

@SuppressWarnings("serial")
public class DrawingBoard extends JPanel implements Observer {
	// Variavel do Tipo Graphics2D para conseguir desenhar o tabuleiro
	Graphics2D graphSettings;

	// Static used as a counter
	private static int ObserverIDTracker = 0;

	// Used to track the observer
	private int ObserverID;

	// Arraylist do tipo PinoEstruturado para guardar todas as alteracoes feitas
	// no metodo update -- Observer
	private ArrayList<Integer> RedPaintPino = new ArrayList<Integer>();
	private ArrayList<Integer> BluePaintPino = new ArrayList<Integer>();
	private ArrayList<Integer> YellowPaintPino = new ArrayList<Integer>();
	private ArrayList<Integer> GreenPaintPino = new ArrayList<Integer>();

	private static DrawingBoard dbfirstInstance = null;

	// Singleton da Class -- ConjuntoDePinos
	public static DrawingBoard getInstancce() {
		if (dbfirstInstance == null) {

			dbfirstInstance = new DrawingBoard();
		}

		return dbfirstInstance;
	}

	private DrawingBoard() {
		// Configuracao do tamnanho do tabuleiro.
		this.setSize(768, 640);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		graphSettings = (Graphics2D) g;

		paintBoard();

		for (int i = 0; i < 8; i++) {
			if (i % 2 == 0) {
				paintPino(RedPaintPino.get(i), Color.red, RedPaintPino.get(i + 1));
				paintPino(BluePaintPino.get(i), Color.blue, BluePaintPino.get(i + 1));
				paintPino(YellowPaintPino.get(i), Color.yellow, YellowPaintPino.get(i + 1));
				paintPino(GreenPaintPino.get(i), Color.green, GreenPaintPino.get(i + 1));
			}
		}
		if (RedPaintPino.size() != 0 && BluePaintPino.size() != 0 && YellowPaintPino.size() != 0
				&& GreenPaintPino.size() != 0) {
			PaintBarreira(RedPaintPino, BluePaintPino, YellowPaintPino, GreenPaintPino);
		}
	}

	public void paintBoard() {
		// Criando objeto do tipo Yard -- Singleton
		Yard.getInstance();

		// Criando objeto do tipo Pocket -- Singleton
		new Pocket();

		// Para limpar os borroes das lines e definar regras de renderizacao
		graphSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Definindo a cor do plano de fundo: Branco
		graphSettings.setBackground(Color.WHITE);

		// Chama metodo que ira quadricular a Tela.
		GridLines(graphSettings);

		// Chama o metodo graphPocket da class Pocket para criar os triangulos
		// centrais(casas finais)
		Pocket.getInstance().graphPocket(graphSettings);

		// Chama o metodo graphYard da Yard para criar os retangulos
		Yard.getInstance().graphYard(graphSettings);

		// Chamando o metodo Shelter que ira desenhar as casas de abrigo
		// conforme as coordenadas passadas
		Shelter(graphSettings, 240, 40);
		Shelter(graphSettings, 40, 320);
		Shelter(graphSettings, 520, 240);
		Shelter(graphSettings, 320, 520);

		// Chamando o metodo FinalPath que ira pintar as casas finais conforme
		// cada cor do pino passada como parametro
		for (int column = 0; column < 15; column++) {
			for (int line = 0; line < 15; line++) {
				if ((column == 6 && line == 1) || ((column == 7) && (line > 0 && line < 6))) {
					FinalPath(graphSettings, Color.GREEN, 40 * line, 40 * column);
				}

				// Vermelho: Pinta o caminho final de vermelho.
				else if ((line == 8 && column == 1) || ((line == 7) && (column > 0 && column < 6))) {
					FinalPath(graphSettings, Color.RED, 40 * line, 40 * column);
				}

				// Azul: Pinta o caminho final de Azul.
				else if ((column == 8 && line == 13) || ((column == 7) && (line > 8 && line < 14))) {
					FinalPath(graphSettings, Color.BLUE, 40 * line, 40 * column);
				}

				// Amarelo: Pinta o caminho final de Amarelo.
				else if ((line == 6 && column == 13) || ((line == 7) && (column > 8 && column < 14))) {
					FinalPath(graphSettings, Color.YELLOW, 40 * line, 40 * column);
				}
			}
		}

	}

	private void GridLines(Graphics2D graphSettings) {

		// Vai dividir em quadradinhos
		for (int column = 0; column < 15; column++) {
			for (int line = 0; line < 15; line++) {

				// Ira dividir o tabuleiro em varios quadradadinhos (GridLines)
				graphSettings.setPaint(Color.black);
				graphSettings.draw(new Rectangle2D.Double(40 * line, 40 * column, 40, 40));
			}
		}

	}

	public void Shelter(Graphics2D graphSettings, int x, int y) {
		// Selecionando a cor do pincel como preta
		graphSettings.setPaint(Color.BLACK);

		// Preenchendo retangulos de coordenadas x e y com cor preta.
		graphSettings.fill(new Rectangle2D.Double(x, y, 40, 40));

	}

	public void FinalPath(Graphics2D graphSettings, Color color, int xCoordinate, int yCoordinate) {
		// Selecionando a cor do pincel conforme a passada como parametro para o
		// metodo
		graphSettings.setPaint(color);

		// Preenchendo um dos quadradinho do caminho final com a cor passada
		// como parametro para o metodo
		graphSettings.fill(new Rectangle2D.Double(xCoordinate, yCoordinate, 40, 40));

		// Redefinindo a cor do pincel como preta
		graphSettings.setPaint(Color.black);

		// Redefinindo a borda do quadrado do caminho final pintado
		// anteriormente.
		graphSettings.draw(new Rectangle2D.Double(xCoordinate, yCoordinate, 40, 40));

	}

	public void paintPino(int numeroPino, Color cor, int CasaCorPino) {
		Ellipse2D pin = new Ellipse2D.Double();
		if (CasaCorPino == 0) {
			if (cor == Color.green) {
				graphSettings.setPaint(Color.green);
				if (numeroPino == 1) {
					pin.setFrame(new Rectangle(45, 45, 30, 30));
					graphSettings.fill(pin);
					graphSettings.setColor(Color.black);
					graphSettings.draw(pin);
				} else if (numeroPino == 2) {
					pin.setFrame(new Rectangle(165, 45, 30, 30));
					graphSettings.fill(pin);
					graphSettings.setColor(Color.black);
					graphSettings.draw(pin);
				} else if (numeroPino == 3) {
					pin.setFrame(new Rectangle(45, 165, 30, 30));
					graphSettings.fill(pin);
					graphSettings.setColor(Color.black);
					graphSettings.draw(pin);
				} else if (numeroPino == 4) {
					pin.setFrame(new Rectangle(165, 165, 30, 30));
					graphSettings.fill(pin);
					graphSettings.setColor(Color.black);
					graphSettings.draw(pin);
				}
			} else if (cor == Color.red) {
				graphSettings.setPaint(Color.red);
				if (numeroPino == 1) {
					pin.setFrame(new Rectangle(405, 45, 30, 30));
					graphSettings.fill(pin);
					graphSettings.setColor(Color.black);
					graphSettings.draw(pin);
				} else if (numeroPino == 2) {
					pin.setFrame(new Rectangle(525, 45, 30, 30));
					graphSettings.fill(pin);
					graphSettings.setColor(Color.black);
					graphSettings.draw(pin);
				} else if (numeroPino == 3) {
					pin.setFrame(new Rectangle(405, 165, 30, 30));
					graphSettings.fill(pin);
					graphSettings.setColor(Color.black);
					graphSettings.draw(pin);
				} else if (numeroPino == 4) {
					pin.setFrame(new Rectangle(525, 165, 30, 30));
					graphSettings.fill(pin);
					graphSettings.setColor(Color.black);
					graphSettings.draw(pin);
				}
			} else if (cor == Color.yellow) {
				graphSettings.setPaint(Color.yellow);
				if (numeroPino == 1) {
					pin.setFrame(new Rectangle(45, 405, 30, 30));
					graphSettings.fill(pin);
					graphSettings.setColor(Color.black);
					graphSettings.draw(pin);
				} else if (numeroPino == 2) {
					pin.setFrame(new Rectangle(165, 405, 30, 30));
					graphSettings.fill(pin);
					graphSettings.setColor(Color.black);
					graphSettings.draw(pin);
				} else if (numeroPino == 3) {
					pin.setFrame(new Rectangle(45, 525, 30, 30));
					graphSettings.fill(pin);
					graphSettings.setColor(Color.black);
					graphSettings.draw(pin);
				} else if (numeroPino == 4) {
					pin.setFrame(new Rectangle(165, 525, 30, 30));
					graphSettings.fill(pin);
					graphSettings.setColor(Color.black);
					graphSettings.draw(pin);
				}
			} else if (cor == Color.blue) {
				graphSettings.setPaint(Color.blue);
				if (numeroPino == 1) {
					pin.setFrame(new Rectangle(405, 405, 30, 30));
					graphSettings.fill(pin);
					graphSettings.setColor(Color.black);
					graphSettings.draw(pin);
				} else if (numeroPino == 2) {
					pin.setFrame(new Rectangle(525, 405, 30, 30));
					graphSettings.fill(pin);
					graphSettings.setColor(Color.black);
					graphSettings.draw(pin);
				} else if (numeroPino == 3) {
					pin.setFrame(new Rectangle(405, 525, 30, 30));
					graphSettings.fill(pin);
					graphSettings.setColor(Color.black);
					graphSettings.draw(pin);
				} else if (numeroPino == 4) {
					pin.setFrame(new Rectangle(525, 525, 30, 30));
					graphSettings.fill(pin);
					graphSettings.setColor(Color.black);
					graphSettings.draw(pin);
				}
			}
		}

		else if (CasaCorPino == 59) {
			graphSettings.setPaint(cor);
			if (cor == Color.red) {
				pin.setFrame(new Rectangle(285, 245, 30, 30));
			}

			else if (cor == Color.blue) {
				pin.setFrame(new Rectangle(325, 285, 30, 30));
			}

			else if (cor == Color.yellow) {
				pin.setFrame(new Rectangle(285, 325, 30, 30));
			}

			else {// cor == verde
				pin.setFrame(new Rectangle(245, 285, 30, 30));
			}
			graphSettings.fill(pin);
			graphSettings.setColor(Color.black);
			graphSettings.draw(pin);

		} else {
			graphSettings.setPaint(cor);
			if (cor == Color.red) {
				pin.setFrame(new Rectangle(Path.getInstance().getRed()[CasaCorPino - 1].getline() + 5,
						Path.getInstance().getRed()[CasaCorPino - 1].getcolumn() + 5, 30, 30));
			}

			else if (cor == Color.blue) {
				pin.setFrame(new Rectangle(Path.getInstance().getBlue()[CasaCorPino - 1].getline() + 5,
						Path.getInstance().getBlue()[CasaCorPino - 1].getcolumn() + 5, 30, 30));
			}

			else if (cor == Color.yellow) {
				pin.setFrame(new Rectangle(Path.getInstance().getYellow()[CasaCorPino - 1].getline() + 5,
						Path.getInstance().getYellow()[CasaCorPino - 1].getcolumn() + 5, 30, 30));
			}

			else {// cor == verde
				pin.setFrame(new Rectangle(Path.getInstance().getGreen()[CasaCorPino - 1].getline() + 5,
						Path.getInstance().getGreen()[CasaCorPino - 1].getcolumn() + 5, 30, 30));
			}
			graphSettings.fill(pin);
			graphSettings.setColor(Color.black);
			graphSettings.draw(pin);
		}

	}

	private void PaintBarreira(ArrayList<Integer> RedPaintPino, ArrayList<Integer> BluePaintPino,
			ArrayList<Integer> YellowPaintPino, ArrayList<Integer> GreenPaintPino) {
		// Criando pin que ira simbolizar a barreira
		Ellipse2D barrier = new Ellipse2D.Double();

		// Settando a cor do simbolo da barreira como preto
		graphSettings.setPaint(Color.black);

		// Cor Vermelha
		if (RedPaintPino.get(1) == RedPaintPino.get(3)) {
			if (RedPaintPino.get(1) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getRed()[RedPaintPino.get(1) - 1].getline() + 10,
						Path.getInstance().getRed()[RedPaintPino.get(1) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		if (RedPaintPino.get(1) == RedPaintPino.get(5)) {
			if (RedPaintPino.get(1) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getRed()[RedPaintPino.get(1) - 1].getline() + 10,
						Path.getInstance().getRed()[RedPaintPino.get(1) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		if (RedPaintPino.get(1) == RedPaintPino.get(7)) {
			if (RedPaintPino.get(1) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getRed()[RedPaintPino.get(1) - 1].getline() + 10,
						Path.getInstance().getRed()[RedPaintPino.get(1) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		if (RedPaintPino.get(3) == RedPaintPino.get(5)) {
			if (RedPaintPino.get(3) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getRed()[RedPaintPino.get(3) - 1].getline() + 10,
						Path.getInstance().getRed()[RedPaintPino.get(3) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		if (RedPaintPino.get(3) == RedPaintPino.get(7)) {
			if (RedPaintPino.get(3) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getRed()[RedPaintPino.get(3) - 1].getline() + 10,
						Path.getInstance().getRed()[RedPaintPino.get(3) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}
		if (RedPaintPino.get(5) == RedPaintPino.get(7)) {
			if (RedPaintPino.get(5) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getRed()[RedPaintPino.get(5) - 1].getline() + 10,
						Path.getInstance().getRed()[RedPaintPino.get(5) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		// Cor Azul
		if (BluePaintPino.get(1) == BluePaintPino.get(3)) {
			if (BluePaintPino.get(1) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getBlue()[BluePaintPino.get(1) - 1].getline() + 10,
						Path.getInstance().getBlue()[BluePaintPino.get(1) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		if (BluePaintPino.get(1) == BluePaintPino.get(5)) {
			if (BluePaintPino.get(1) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getBlue()[BluePaintPino.get(1) - 1].getline() + 10,
						Path.getInstance().getBlue()[BluePaintPino.get(1) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		if (BluePaintPino.get(1) == BluePaintPino.get(7)) {
			if (BluePaintPino.get(1) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getBlue()[BluePaintPino.get(1) - 1].getline() + 10,
						Path.getInstance().getBlue()[BluePaintPino.get(1) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		if (BluePaintPino.get(3) == BluePaintPino.get(5)) {
			if (BluePaintPino.get(3) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getBlue()[BluePaintPino.get(3) - 1].getline() + 10,
						Path.getInstance().getBlue()[BluePaintPino.get(3) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		if (BluePaintPino.get(3) == BluePaintPino.get(7)) {
			if (BluePaintPino.get(3) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getBlue()[BluePaintPino.get(3) - 1].getline() + 10,
						Path.getInstance().getBlue()[BluePaintPino.get(3) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}
		if (BluePaintPino.get(5) == BluePaintPino.get(7)) {
			if (BluePaintPino.get(5) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getBlue()[BluePaintPino.get(5) - 1].getline() + 10,
						Path.getInstance().getBlue()[BluePaintPino.get(5) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		// Cor Amarelo
		if (YellowPaintPino.get(1) == YellowPaintPino.get(3)) {
			if (YellowPaintPino.get(1) != 0) {
				barrier.setFrame(
						new Rectangle(Path.getInstance().getYellow()[YellowPaintPino.get(1) - 1].getline() + 10,
								Path.getInstance().getYellow()[YellowPaintPino.get(1) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		if (YellowPaintPino.get(1) == YellowPaintPino.get(5)) {
			if (YellowPaintPino.get(1) != 0) {
				barrier.setFrame(
						new Rectangle(Path.getInstance().getYellow()[YellowPaintPino.get(1) - 1].getline() + 10,
								Path.getInstance().getYellow()[YellowPaintPino.get(1) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		if (YellowPaintPino.get(1) == YellowPaintPino.get(7)) {
			if (YellowPaintPino.get(1) != 0) {
				barrier.setFrame(
						new Rectangle(Path.getInstance().getYellow()[YellowPaintPino.get(1) - 1].getline() + 10,
								Path.getInstance().getYellow()[YellowPaintPino.get(1) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		if (YellowPaintPino.get(3) == YellowPaintPino.get(5)) {
			if (YellowPaintPino.get(3) != 0) {
				barrier.setFrame(
						new Rectangle(Path.getInstance().getYellow()[YellowPaintPino.get(3) - 1].getline() + 10,
								Path.getInstance().getYellow()[YellowPaintPino.get(3) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		if (YellowPaintPino.get(3) == YellowPaintPino.get(7)) {
			if (YellowPaintPino.get(3) != 0) {
				barrier.setFrame(
						new Rectangle(Path.getInstance().getYellow()[YellowPaintPino.get(3) - 1].getline() + 10,
								Path.getInstance().getYellow()[YellowPaintPino.get(3) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}
		if (YellowPaintPino.get(5) == YellowPaintPino.get(7)) {
			if (YellowPaintPino.get(5) != 0) {
				barrier.setFrame(
						new Rectangle(Path.getInstance().getYellow()[YellowPaintPino.get(5) - 1].getline() + 10,
								Path.getInstance().getYellow()[YellowPaintPino.get(5) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		// Cor Verde
		if (GreenPaintPino.get(1) == GreenPaintPino.get(3)) {
			if (GreenPaintPino.get(1) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getGreen()[GreenPaintPino.get(1) - 1].getline() + 10,
						Path.getInstance().getGreen()[GreenPaintPino.get(1) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		if (GreenPaintPino.get(1) == GreenPaintPino.get(5)) {
			if (GreenPaintPino.get(1) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getGreen()[GreenPaintPino.get(1) - 1].getline() + 10,
						Path.getInstance().getGreen()[GreenPaintPino.get(1) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		if (GreenPaintPino.get(1) == GreenPaintPino.get(7)) {
			if (GreenPaintPino.get(1) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getGreen()[GreenPaintPino.get(1) - 1].getline() + 10,
						Path.getInstance().getGreen()[GreenPaintPino.get(1) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		if (GreenPaintPino.get(3) == GreenPaintPino.get(5)) {
			if (GreenPaintPino.get(3) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getGreen()[GreenPaintPino.get(3) - 1].getline() + 10,
						Path.getInstance().getGreen()[GreenPaintPino.get(3) - 1].getcolumn() + 10, 20, 20));
				;
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

		if (GreenPaintPino.get(3) == GreenPaintPino.get(7)) {
			if (GreenPaintPino.get(3) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getGreen()[GreenPaintPino.get(3) - 1].getline() + 10,
						Path.getInstance().getGreen()[GreenPaintPino.get(3) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}
		if (GreenPaintPino.get(5) == GreenPaintPino.get(7)) {
			if (GreenPaintPino.get(5) != 0) {
				barrier.setFrame(new Rectangle(Path.getInstance().getGreen()[GreenPaintPino.get(5) - 1].getline() + 10,
						Path.getInstance().getGreen()[GreenPaintPino.get(5) - 1].getcolumn() + 10, 20, 20));
				graphSettings.setColor(Color.black);
				graphSettings.draw(barrier);
			}
		}

	}

	public void popUp() {
		int input = JOptionPane.showOptionDialog(null, "Nao houve jogadores suficiente", "Time Out",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
		if (input == JOptionPane.OK_OPTION || input == JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		}
	}

	@Override
	public void update(int nCasa, Color Cor, int nPino) {

		if (Cor == Color.red) {
			if (RedPaintPino.size() < 8) {
				// System.out.print("RED ----- "+"Numero: " + nPino + "Casa: " +
				// nCasa + "\n");
				this.RedPaintPino.add(nPino);
				this.RedPaintPino.add(nCasa);
			} else {
				for (int i = 0; i < RedPaintPino.size(); i++) {
					if (i % 2 == 0) {
						if (RedPaintPino.get(i) == nPino) {// Busca se o
															// elemento já
															// existe na lista
							RedPaintPino.set(i + 1, nCasa);// Se existir muda só
															// a casa dele
							// System.out.print("RED ----- "+"Numero: " + nPino
							// + "Casa: " + nCasa + "\n");
						}
					}
				}
			}
		}

		else if (Cor == Color.blue) {
			if (BluePaintPino.size() < 8) {
				// System.out.print("Blue ----- "+"Numero: " + nPino + "Casa: "
				// + nCasa + "\n");
				this.BluePaintPino.add(nPino);
				this.BluePaintPino.add(nCasa);
			} else {
				for (int i = 0; i < BluePaintPino.size(); i++) {
					if (i % 2 == 0) {
						if (BluePaintPino.get(i) == nPino) {// Busca se o
															// elemento já
															// existe na lista
							BluePaintPino.set(i + 1, nCasa);// Se existir muda
															// só a casa dele
							// System.out.print("Blue ----- "+"Numero: " + nPino
							// + "Casa: " + nCasa + "\n");
						}
					}
				}
			}
		}

		else if (Cor == Color.yellow) {
			if (YellowPaintPino.size() < 8) {
				// System.out.print("Yellow ----- "+"Numero: " + nPino + "Casa:
				// " + nCasa + "\n");
				this.YellowPaintPino.add(nPino);
				this.YellowPaintPino.add(nCasa);
			} else {
				for (int i = 0; i < YellowPaintPino.size(); i++) {
					if (i % 2 == 0) {
						if (YellowPaintPino.get(i) == nPino) {// Busca se o
																// elemento já
																// existe na
																// lista
							YellowPaintPino.set(i + 1, nCasa);// Se existir muda
																// só a casa
																// dele
							// System.out.print("Yellow ----- "+"Numero: " +
							// nPino + "Casa: " + nCasa + "\n");
						}
					}
				}
			}
		}

		else if (Cor == Color.green) {
			if (GreenPaintPino.size() < 8) {
				// System.out.print("Green ----- "+"Numero: " + nPino + "Casa: "
				// + nCasa + "\n");
				this.GreenPaintPino.add(nPino);
				this.GreenPaintPino.add(nCasa);
			} else {
				for (int i = 0; i < GreenPaintPino.size(); i++) {
					if (i % 2 == 0) {
						if (GreenPaintPino.get(i) == nPino) {// Busca se o
																// elemento já
																// existe na
																// lista
							GreenPaintPino.set(i + 1, nCasa);// Se existir muda
																// só a casa
																// dele
							// System.out.print("Green ----- "+"Numero: " +
							// nPino + "Casa: " + nCasa + "\n");
						}
					}
				}
			}
		}

		else {
			System.out.println("Cor nula!");
		}
		revalidate();
		repaint();
	}

	public void addTabuleiro(Subject PinoEstruturado) {

		// Incrementando o contado statico
		this.ObserverID = ++ObserverIDTracker;

		// Mensagem que notifica o usuario de um novo observador
		System.out.println("Pino: New Observer" + this.ObserverID);

		// Add o observer para a Arralist
		PinoEstruturado.register(this);
	}
}