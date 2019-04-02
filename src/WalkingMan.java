import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

public class WalkingMan extends JComponent implements ActionListener
{
	private ArrayList<Ball> bullets = new ArrayList<>();
	private Ellipse2D head = new Ellipse2D.Double(10, 0, 10, 10);
	private Rectangle2D body = new Rectangle2D.Double(11, 10, 8, 40);
	private Polygon leftArm = new Polygon(new int[] {0, 11, 6}, new int[] {40, 11, 40}, 3), 
			rightArm = new Polygon(new int[] {30, 19, 24}, new int[] {40, 11, 40}, 3);
	private int dx = 0, dy = 0;
	
	public WalkingMan(int x, int y)
	{
		JFrame myFrame = new JFrame();
		myFrame.setBounds(100, 100, 900, 600);
		
		this.setSize(51, 31);
		this.setLocation(x,y);
		
		myFrame.add(this);
		
		this.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == e.VK_UP)
				{
					setDy(-10);
				}
				else if(e.getKeyCode() == e.VK_DOWN)
				{
					setDy(10);
				}
				else if (e.getKeyCode() == e.VK_RIGHT)
				{
					setDx(10);
				}
				else if(e.getKeyCode() == e.VK_LEFT)
				{
					setDx(-10);
				}
				if(e.getKeyCode() == e.VK_SPACE)
				{
					Ball bulletA = new Ball(getX() + getWidth(), getY() + getHeight()/4);
					bullets.add(bulletA);
					myFrame.add(bulletA);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) 
			{
				if(e.getKeyCode() == e.VK_UP)
				{
					setDy(0);
				}
				else if(e.getKeyCode() == e.VK_DOWN)
				{
					setDy(0);
				}
				else if (e.getKeyCode() == e.VK_RIGHT)
				{
					setDx(0);
				}
				else if(e.getKeyCode() == e.VK_LEFT)
				{
					setDx(0);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) 
			{
				if(e.getKeyCode() == e.VK_UP)
				{
					setDy(-5);
				}
				else if(e.getKeyCode() == e.VK_DOWN)
				{
					setDy(5);
				}
				else if(e.getKeyCode() == e.VK_RIGHT)
				{
					setDx(5);
				}
				else if(e.getKeyCode() == e.VK_LEFT)
				{
					setDx(-5);
				}
				if(e.getKeyCode() == e.VK_SPACE)
				{	
					Ball bulletA = new Ball(getX() + 31, getY() + 15);
					bullets.add(bulletA);
					myFrame.add(bulletA);
				}
			}
			
		});
		
		Timer t1 = new Timer(1000/60, this);
		t1.start();
		
		this.setFocusable(true);
				
		myFrame.setDefaultCloseOperation(myFrame.EXIT_ON_CLOSE);
		myFrame.setVisible(true);
		
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.fill(head);
		g2d.fill(body);
		g2d.fill(leftArm);
		g2d.fill(rightArm);
	}
	
	public void setDx(int x)
	{
		dx = x;
	}
	
	public void setDy(int y)
	{
		dy = y;
	}
	
	public void update()
	{
		this.setLocation(this.getX() + dx, this.getY() + dy);
		if((this.getX() <= 0 && dx < 0))
		{
			this.setLocation(0, this.getY());
		}
		if((this.getX() >= 854 && dx > 0))
		{
			this.setLocation(854, this.getY());
		}
		if((this.getY() <= 0 && dy < 0))
		{
			this.setLocation(this.getX(), 0);
		}
		if((this.getY() >= 510 && dy > 0))
		{
			this.setLocation(this.getX(), 510);
		}
		for (int i = bullets.size(); i >0; i--)
		{
			int count = 0;
			if (bullets.get(bullets.size() - i).getX() < 0)
			{
				bullets.get(bullets.size() - i).stop();
				bullets.get(bullets.size() - i).repaint();
			}
			else if (bullets.get(bullets.size() - i).getX() > 200)
			{
				bullets.remove(bullets.get(bullets.size() - i));
				bullets.get(bullets.size() - i).repaint();
			}
			System.out.println(bullets.get(bullets.size() - i).getX());
		}
	}
	
	public static void main(String[] args) 
	{
		new WalkingMan(100, 100);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		for(Ball bullet : bullets)
		{
			bullet.update();
			bullet.repaint();
		}
		this.update();
		this.repaint();
	}
}
