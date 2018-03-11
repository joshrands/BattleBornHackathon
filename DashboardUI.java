import java.io.*;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

public class DashboardUI extends Canvas implements Runnable, MouseListener {

	private Thread thread;
	private BufferStrategy bs;
	private Graphics g;

	boolean running;

	private int screen;

	private BufferedImage DashUI;
	private BufferedImage DashUILU;
	private BufferedImage DashUIRU;
	private BufferedImage DashUILD;
	private BufferedImage DashUIRD;

	public DashboardUI() {
		//addMouseMotionListener(this);
		addMouseListener(this);

		try {
			DashUI = ImageIO.read(new File ("UI.png"));
			DashUILU = ImageIO.read(new File ("UILU.png"));
			DashUIRU = ImageIO.read(new File ("UIRU.png"));
			DashUILD = ImageIO.read(new File ("UILD.png"));
			DashUIRD = ImageIO.read(new File ("UIRD.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		running = false;

		screen = 1;
	}

	public void render() {
		//this is some bs...
		//hahahaha get it
		//because Camden said I should name it bs
		//ya know, Buffer Strategy...
		bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0,0,getWidth(),getHeight());
		//I guess that just ran the program in the window constantly

		//cast g2 to be 2D Graphics for shapes
		Graphics2D g2 = (Graphics2D)g;

		//paint the background
		g.setColor(Color.black);
		//g2.draw(test);
		if (screen == 0) {
			//g.drawImage(DashUI, 0, 0, getWidth(), getHeight(), null);
		} else if (screen == 1) {
			g.drawImage(DashUI, 0, 0, getWidth(), getHeight(), null);
		} else if (screen == 5) {
			g.drawImage(DashUILU, 0, 0, getWidth(), getHeight(), null);
		} else if (screen == 6) {
			g.drawImage(DashUIRU, 0, 0, getWidth(), getHeight(), null);
		} else if (screen == 7) {
			g.drawImage(DashUILD, 0, 0, getWidth(), getHeight(), null);
		} else if (screen == 8) {
			g.drawImage(DashUIRD, 0, 0, getWidth(), getHeight(), null);
		}


		//cam cam said so, I guess wipe the screen?
		bs.show();
		g.dispose();
	}

	public static void main(String[] args) {
		//new JFrame
		JFrame win = new JFrame("Barricks Dashboard");
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		win.setSize(1024, 768);
		win.setResizable(false);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        win.setLocation(dim.width/2-win.getSize().width/2, dim.height/2-win.getSize().height/2);
        
		//new instance of mazeRunner
		DashboardUI ui = new DashboardUI();
		//black background
		ui.setBackground(Color.BLACK);



		//add the instance
		win.add(ui);
		//show it
		win.setVisible(true);
		//run it
		ui.start();
	}

	//this is an implemented runnable that is constantly running
	public void run() {
		while(running) {
			//constantly rended #repaint
			render();
		}
		stop();
	}

	//what to do if told to start like in the main method
	public void start() {
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	//what to do if told to stop
	public void stop() {
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX() * 2;
		int y = e.getY() * 2;
		if (screen == 1) {
			// this is the moron dashboard  
			// send workOrder.txt to raspberry pi
			if (x > 76 && x < 983) {
				if (y > 605 && y < 893) {
					screen = 5;
				}
				if (y > 967 && y < 1255) {
					screen = 7;
				}
			}
			if (x > 1105 && x < 1970) {
				if (y > 605 && y < 893) {
					screen = 6;
				}
				if (y > 967 && y < 1255) {
					screen = 8;
				}
			}
		} else if (screen == 5 || screen == 6 || screen == 7 || screen == 8) {
			if (x > 76 && x < 983) {
				if (y > 605 && y < 893) {
					screen = 5;
				}
				if (y > 967 && y < 1255) {
					screen = 7;
				}
			}
			if (x > 1105 && x < 1970) {
				if (y > 605 && y < 893) {
					screen = 6;
				}
				if (y > 967 && y < 1255) {
					screen = 8;
				}
			}
			if (x > 506 && x < 1538 && y > 1306 && y < 1487) {
				System.out.println("Sending work order to Oracle database...");

				try {
					PrintWriter out = new PrintWriter("workOrder.txt");
					out.println("request");
					System.out.println("Data written.");
					out.close();
				} catch(FileNotFoundException ex) {
					System.out.println(ex);
				}
				System.exit(0);
			}
		} else if (screen == 2) {
			// "Other" W.O. option pulls up forge app and eventually does the submit from above
		} else if (screen == 3) {
			// Maintenance guy screen
		}  
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}

