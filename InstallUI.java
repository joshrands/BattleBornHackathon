import java.io.*;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JTextField;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import javax.swing.*;

public class InstallUI extends Canvas implements Runnable, MouseListener {
    
    private Thread thread;
    private BufferStrategy bs;
    private Graphics g;
    
    boolean running;
    
    private int screen;
    
    private BufferedImage SetupUI;
    
    public InstallUI() {
        //addMouseMotionListener(this);
        addMouseListener(this);
        
        try {
            SetupUI = ImageIO.read(new File ("UI.png"));
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
            g.drawImage(SetupUI, 0, 0, getWidth(), getHeight(), null);
        }
        
        //cam cam said so, I guess wipe the screen?
        bs.show();
        g.dispose();
    }
    
    public static void main(String[] args) {
        //new JFrame
        JFrame win = new JFrame("Barricks Setup");
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        win.setSize(1024, 768);
        win.setResizable(false);
        
        //new instance of mazeRunner
        DashboardUI ui = new DashboardUI();
        //black background
        ui.setBackground(Color.BLACK);
        
        JTextField id = new JTextField(1);
        
        id.setBounds(68 / 2, 719 / 2, 1891 / 2, 115 / 2);
        
        win.add(id);
        
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
        if (screen == 0) {
            if (x > 1828 && x < 1967 && y > 717 && y < 837) {
                System.out.println("Finishing Bridge Box Setup...");
                
                try {
                    PrintWriter out = new PrintWriter("oracle.txt");
                    out.println("installed");
                    System.out.println("New Asset Setup");
                    out.close();
                } catch(FileNotFoundException ex) {
                    System.out.println(ex);
                }
                
                //quit
                System.exit(0);
            }
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

