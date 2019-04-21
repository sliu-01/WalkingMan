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
	private Ellipse2D head;
	private Rectangle2D body;
	private Polygon leftArm, rightArm;
	private int dx = 0, dy = 0;
	private int xLocation = 0;
	private int yLocation = 0;
	
	private int manWidth = 31;
	private int manHeight = 51;
	
	JFrame myFrame = new JFrame();
	
	public WalkingMan(int x, int y)
	{
		myFrame.setBounds(100, 100, 900, 600);
		
		this.setSize(manWidth, manHeight);
		this.setLocation(x, y);
		
		myFrame.add(this);
		
		head = new Ellipse2D.Double(10 + this.getX(), 0 + this.getY(), 10, 10);
		body = new Rectangle2D.Double(11 + this.getX(), 10 + this.getY(), 8, 40);
		leftArm = new Polygon(new int[] {0 + this.getX(), 11 + this.getX(), 6 + this.getX()}, new int[] {40 + this.getY(), 11 + this.getY(), 40 + this.getY()}, 3);
		rightArm = new Polygon(new int[] {30 + this.getX(), 19 + this.getX(), 24 + this.getX()}, new int[] {40 + this.getY(), 11 + this.getY(), 40 + this.getY()}, 3);
		
		this.addKeyListener(new KeyListener()
		{
			@Override
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
				else if(e.getKeyCode() == e.VK_SPACE)
				{
					Ball bulletA = new Ball(xLocation + manWidth, yLocation + manHeight/4);
					myFrame.add(bulletA);
					bullets.add(bulletA);
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
		if ((this.getX() < 0) && (dx < 0))
		{
			this.setDx(0);
			xLocation = 0;
		}
		else if (((this.getX() + manWidth) >= myFrame.getWidth()) && (dx > 0))
		{
			this.setDx(0);
			xLocation = myFrame.getWidth() - manWidth;
		}
		else
		{
			xLocation += dx;
		}
		
		if ((this.getY() < 0) && (dy < 0))
		{
			this.setDy(0);
			yLocation = 0;
		}
		else if (((this.getY() + manHeight) >= myFrame.getHeight()) && (dy > 0))
		{
			this.setDy(0);
			yLocation = (myFrame.getHeight() - manHeight);
		}
		else
		{
			yLocation += dy;
		}
		for(int i = bullets.size(); i > 0; i--)
		{
			if(bullets.get(i-1).getX() > myFrame.getWidth())
			{
				myFrame.remove(bullets.get(i-1));
				bullets.remove(i-1);
			}
		}
	}
	
	public static void main(String[] args) 
	{
		new WalkingMan(0, 0);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		this.update();
		this.setLocation(xLocation, yLocation);
		this.repaint();
		for(Ball bullet : bullets)
		{
			bullet.update();
		}
	}
}
