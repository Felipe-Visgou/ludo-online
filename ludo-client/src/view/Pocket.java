package view;

import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Pocket extends JComponent{

	private static Pocket POCKETfirstInstance = null;

	//Singleton da Class -- Pocket
	public static Pocket getInstance(){
		if(POCKETfirstInstance == null){

			POCKETfirstInstance = new Pocket();
		}

		return POCKETfirstInstance;
	}

	public Pocket(){

	}

	public void graphPocket(Graphics2D graphSettings){
		final int []xgreen = {240, 240, 300};
		final int []ygreen = {360, 240, 300};
		final int []xyellow = {360, 240, 300};
		final int []yyellow = {360, 360, 300};
		final int []xred = {240, 360, 300};
		final int []yred = {240, 240, 300};
		final int []xblue = {360, 360, 300};
		final int []yblue = {360, 240, 300};

		graphSettings.setPaint(Color.green);
		graphSettings.fillPolygon(xgreen, ygreen, 3);
		graphSettings.setPaint(Color.yellow);
		graphSettings.fillPolygon(xyellow, yyellow, 3);
		graphSettings.setPaint(Color.red);
		graphSettings.fillPolygon(xred, yred, 3);
		graphSettings.setPaint(Color.blue);
		graphSettings.fillPolygon(xblue, yblue, 3);

		graphSettings.setPaint(Color.black);
		graphSettings.drawPolygon(xgreen, ygreen, 3);
		graphSettings.drawPolygon(xyellow, yyellow, 3);
		graphSettings.drawPolygon(xred, yred, 3);	
		graphSettings.drawPolygon(xblue, yblue, 3);


	}

}
