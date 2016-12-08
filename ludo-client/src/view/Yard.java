package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;


@SuppressWarnings("serial")
public class Yard extends JComponent{

	private static Yard YARDfirstInstance = null;

	//Singleton da Class -- Yard
	public static Yard getInstance(){
		if(YARDfirstInstance == null){

			YARDfirstInstance = new Yard();
		}

		return YARDfirstInstance;
	}

	private Yard(){

	}

	public void graphYard(Graphics2D graphSettings){
		graphSettings.setPaint(Color.green);
		graphSettings.fill(new Rectangle2D.Double(0, 0, 240, 240));
		graphSettings.setPaint(Color.yellow);
		graphSettings.fill(new Rectangle2D.Double(0, 360, 240, 240));
		graphSettings.setPaint(Color.red);
		graphSettings.fill(new Rectangle2D.Double(360, 0, 240, 240));
		graphSettings.setPaint(Color.blue);
		graphSettings.fill(new Rectangle2D.Double(360, 360, 240, 240));

		graphSettings.setPaint(Color.black);
		graphSettings.draw(new Rectangle2D.Double(0, 0, 240, 240));
		graphSettings.draw(new Rectangle2D.Double(0, 360, 240, 240));
		graphSettings.draw(new Rectangle2D.Double(360, 0, 240, 240));
		graphSettings.draw(new Rectangle2D.Double(360, 360, 240, 240));

		for(int i = 0; i < 15; i++){
			for(int j = 0; j < 15; j++){
				graphSettings.setPaint(Color.white);
				if(((i == 1) || (i == 4) || (i == 10) || (i == 13)) && ((j == 1) || (j == 4) || (j == 10) || (j == 13))){
					graphSettings.fill(new Ellipse2D.Double(40*i, 40*j, 40, 40));
					graphSettings.setPaint(Color.black);
					graphSettings.draw(new Ellipse2D.Double(40*i, 40*j, 40, 40));
				}

			}
		}

	}

}
