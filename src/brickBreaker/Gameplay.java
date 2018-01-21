package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
	private boolean play = false;
	private int score = 0;
	private int totalBricks = 21;
	
	private Timer eventLoop;
	private int delay = 12;
	
	private int playerX = 310;
	
	private Ball ball;
	
	private MapGenerator map;
	
	public Gameplay() {
		map = new MapGenerator(3, 7);
		
		ball = new Ball(120, 350, -2, -2);
		paddle = new Paddle();
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		eventLoop = new Timer(delay, this);
		eventLoop.start();
	}
	
	public void paint(Graphics g) {
		// background
		g.setColor(Color.white);
		g.fillRect(1, 1, 692, 592);
		
		// map
		map.draw((Graphics2D)g);
		
		// borders
		g.setColor(Color.black);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		// scores
		g.setColor(Color.black);
		g.setFont(new Font("arial", Font.BOLD, 25));
		g.drawString("" + score, 590, 30);
		
		// the paddle
		g.setColor(Color.black);
		g.fillRect(playerX, 550, 100, 8);
		paddle.draw(g);
		
		ball.draw(g);
		
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (play) {
			if (new Rectangle(ball.x, ball.y, 20, 20).intersects(playerX, 550, 100, 8)) {
				ball.yDir *= -1;
			}
			
			A: for (int i = 0; i < map.map.length; i ++) {
				for (int j = 0; j < map.map[0].length; j++) {
					if (map.map[i][j] == 1) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ball.x, ball.y, 20, 20);
						Rectangle brickRect = rect;
						
						if (ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks -= 1;
							score += 5;
							
							if (ball.x + 19 <= brickRect.x || ball.x + 1 >= brickRect.x + brickRect.width) {
								ball.xDir *= -1;
							} else {
								ball.yDir *= -1;
							}
							break A;
						}
					}
				}
			}
			
			ball.moveInFrame(670);
			
			if (ball.y > 570 || totalBricks == 0) {
				play = false;
				ball.stop();
//				g.setColor(Color.red);
//				g.setFont(new Font("arial", Font.BOLD, 30));
//				g.drawString("Enter to Restart", 230, 350);
			}
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 590) {
				playerX = 590;
			} else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!play) {
				play = true;
				ball.x = 120;
				ball.y = 350;
				ball.xDir = -1;
				ball.yDir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3, 7);
				
				repaint();
			}
		}
	}
	
	public void moveRight() {
		play = true;
		playerX += 20;
	}
	
	public void moveLeft() {
		play = true;
		playerX -= 20;
	}

}
