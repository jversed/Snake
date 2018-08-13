package snake;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class Snake implements ActionListener, KeyListener{
	
	public JFrame jframe;
	
	public RenderPanel renderPanel;
	
	public javax.swing.Timer timer = new javax.swing.Timer(20, this);
	
	public static Snake snake;
	//----------------------------------------------------------------------\\
	public ArrayList <Point> snakeParts = new ArrayList<Point>();
	
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;
	
	public int ticks = 0, direction = DOWN, score, tailLength = 10, time;
	
	public Point head, cherry;
	
	public Random random;
	
	public boolean over = false, paused;
	
	public Dimension dim;
	
	public Snake() {
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		jframe = new JFrame("TheSnakeGame");
		jframe.setVisible(true);
		jframe.setSize(805, 700);
		jframe.setResizable(false);
		jframe.setLocation(dim.width/2 - jframe.getWidth()/2, dim.height/2 - jframe.getHeight()/2);
		jframe.add(renderPanel=new RenderPanel());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addKeyListener(this);
		startGame();
	}
	public void startGame() {
		over = false;
		paused = false;
		time = 0;
		score = 0;
		tailLength = 15;
		direction = DOWN;
		head = new Point(0, -1);
		random = new Random();
		snakeParts.clear();
		cherry = new Point(random.nextInt(79), random.nextInt(65));
		for (int i = 0; i < tailLength; i++) {
			snakeParts.add(new Point(head.x, head.y));
		}
		timer.start();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println(head.x + ", " + head.y + " score:" + score);
		renderPanel.repaint();
		ticks++;
		
		if(ticks % 2 == 0 && head!=null && !over && !paused) {
			time++;
			snakeParts.add(new Point(head.x,head.y));
			if(direction == UP) 
				if(head.y-1 >= 0 && noTailAt(head.x + 1, head.y	- 1))
					head = new Point(head.x, head.y-1);
				else
					over = true;
			
			if(direction == DOWN) 
				if(head.y+1 < 66 && noTailAt(head.x, head.y + 1))
					head = new Point(head.x, head.y+1);
				else
					over = true;
			
			if(direction == LEFT) 
				if(head.x-1 >= 0 && noTailAt(head.x - 1, head.y))
					head = new Point(head.x-1, head.y);
				else
					over = true;
			
			if(direction == RIGHT) 
				if(head.x+1 < 79 && noTailAt(head.x + 1, head.y))
					head = new Point(head.x+1, head.y);
				else
					over = true;
			
			if (snakeParts.size()>tailLength) {
				snakeParts.remove(0);
			}
			
			if(cherry != null) {
				if (head.equals(cherry)) {
					score += 10;
					for (int i = 0; i < 10; i++) {
						tailLength = tailLength+i;
					}; 
					cherry.setLocation(random.nextInt(79), random.nextInt(65));
				}
			}
		}
	}
	
	public boolean noTailAt(int x, int y) {
		for(Point point : snakeParts) {
			if(point.equals(new Point(x, y)))
				return false;
		}
		return true;
	}
	public static void main(String[] args) {
		snake = new Snake();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		if ( i == KeyEvent.VK_A && direction != RIGHT)
			direction = LEFT;
		if ( i == KeyEvent.VK_D && direction != LEFT)
			direction = RIGHT;
		if ( i == KeyEvent.VK_W && direction != DOWN)
			direction = UP;
		if ( i == KeyEvent.VK_S && direction != UP)
			direction = DOWN;
		if ( i == KeyEvent.VK_SPACE)
			if (over)
			startGame();
			else
				paused = !paused;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	

}
