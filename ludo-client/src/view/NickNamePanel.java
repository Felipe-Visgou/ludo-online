package view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class NickNamePanel extends JFrame {
	private String nickname;
	
	private static NickNamePanel nickfirstInstance = null;

	// Singleton da Class -- ConjuntoDePinos
	public static NickNamePanel getInstancce() {
		if (nickfirstInstance == null) {

			nickfirstInstance = new NickNamePanel();
		}
		return nickfirstInstance;
	}
	
	public String getNickname(){
		return this.nickname;
	}
	
	private void setNickname(String newNickName){
		this.nickname = newNickName;
	}
	
	private NickNamePanel(){}
	
	public boolean initializeNickPanel(){
		/******** START: Configurações do Frame********/
		Toolkit tk = Toolkit.getDefaultToolkit();

		Dimension dim = tk.getScreenSize();

		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
		
		setSize(400,200);
		setLocation(xPos, yPos);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		/******** END: Configurações do Frame********/
		
		
		return true;
	}
	

}
