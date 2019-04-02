import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;
import javax.swing.Timer;

public class Ball extends JComponent implements ActionListener
{
	private int dx = 8, dy = 0;
	private static int r = 7;
	
	public Ball(int x, int y)
	{
		this.setSize(7, 7);
		this.setLocation(x, y);
		
		Timer t2 = new Timer(1000/60, this);
		t2.start();
		
		this.setFocusable(true);
	}
	
	public void stop()
	{
		dx = 0;
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.fill(new Ellipse2D.Double(0, 0, 6, 6));
	}
	
	public void update()
	{
		this.setLocation(getX() + dx, getY() + dy);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		this.update();
		this.repaint();		
	}	
}
