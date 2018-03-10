
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
    
    private int moveRectStartX;
    private int moveRectStartY;
    
    private int moveRectLengthX;
    private int moveRectLengthY;
    
    boolean running;
    
    private int screen;
    
    private BufferedImage DashUI;
    
    public DashboardUI() {
        //addMouseMotionListener(this);
        addMouseListener(this);
        
        try {
            DashUI = ImageIO.read(new File ("Dashboard.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        running = false;
        
        screen = 0;
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
            
        }
        
        
        //cam cam said so, I guess wipe the screen?
        bs.show();
        g.dispose();
    }
    
    public static void main(String[] args) {
        //new JFrame
        JFrame win = new JFrame("Barricks Dashboard");
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        win.setSize(768, 1024);
        
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

