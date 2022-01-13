/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cars;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Ojomo Oreofe
 */
public class work extends JPanel implements ActionListener, KeyListener {

    private int space;
    private int width = 80;
    private int height = 70;
    private int speed;
    private int WIDTH = 500;
    private int HEIGHT = 700;
    private int move = 20;
    private int count = 1;
    private ArrayList<Rectangle> ocars;
    private ArrayList<Rectangle> line;
    private Random rand;
    private Rectangle car;
    BufferedImage bg;
    BufferedImage user;
    BufferedImage op1;
    BufferedImage op2;
    BufferedImage road;
    Boolean linef = true;
    Timer t;

    public work() {
        try {
            user = ImageIO.read(new File("C:\\Users\\HP\\Desktop\\java\\cars\\image\\user.png"));
            op1 = ImageIO.read(new File("C:\\Users\\HP\\Desktop\\java\\cars\\image\\op1.png"));
            op2 = ImageIO.read(new File("C:\\Users\\HP\\Desktop\\java\\cars\\image\\op2_.png"));
            bg = ImageIO.read(new File("C:\\Users\\HP\\Desktop\\java\\cars\\image\\bg.png"));
            road = ImageIO.read(new File("C:\\Users\\HP\\Desktop\\java\\cars\\image\\road.png"));
        } catch (IOException ex) {
            Logger.getLogger(work.class.getName()).log(Level.SEVERE, null, ex);
        }
        t = new Timer(20, this);
        rand = new Random();
        ocars = new ArrayList<Rectangle>();
        line = new ArrayList<Rectangle>();
        car = new Rectangle(WIDTH / 2 - 90, HEIGHT - 100, width, height);
        space = 300;
        speed = 4;
        addKeyListener(this);
        setFocusable(true);
        addocars(true);
        addocars(true);

//        addocars(true);
//        addocars(true);
//        addocars(true);
//        addocars(true);
//        addocars(true);
//        for(int i =0;i<4;i++){
//           addocars(true); 
//        }
        addlines(true);
        addlines(true);
        addlines(true);
        addlines(true);
        addlines(true);
        addlines(true);
        addlines(true);
        addlines(true);
        t.start();

    }

    public void addlines(Boolean first) {
        int x = WIDTH / 2 - 2;
        int y = 700;
        int width = 4;
        int height = 100;
        int space = 130;
        if (first) {
            line.add(new Rectangle(x, y - (line.size() * space), width, height));
        } else {
        line.add(new Rectangle(x,y-(line.size()-1)*space,width,height));
            line.add(new Rectangle(x, line.get(line.size() - 1).y - space, width, height));
        }
    }

    public void addocars(boolean first) {
        int positionx = rand.nextInt() % 2;
        int x = 0;
        int y = 0;
        int Width = width;
        int Height = height;
        if (positionx == 0) {
            x = WIDTH / 2 - 90;
        } else {
            x = WIDTH / 2 + 10;
        }
        if (first) {

            ocars.add(new Rectangle(x, y - 100 - (ocars.size() * space), Width, Height));
        } else {
            ocars.add(new Rectangle(x, ocars.get(ocars.size() - 1).y - 500, Width, Height));

        }
    }

    public void paintComponent(Graphics g) {
        int counter = 0;
        super.paintComponents(g);
//        Graphics2D g2=(Graphics2D)g;
        g.drawImage(bg, 0, 0, null);
        g.drawImage(road, WIDTH / 2 - 125, 0, null);
        g.setColor(Color.WHITE);
//       g2.setStroke(new BasicStroke(10));
//        for (Rectangle rect : line) {
//            g.fillRect(rect.x, rect.y, rect.width, rect.height);
//        }
        g.drawImage(user, car.x, car.y, null);
//        g.setColor(Color.MAGENTA);
        for (Rectangle rect : ocars) {
            counter++;
            if (rand.nextInt() % 100 == 10) {
                g.drawImage(op1, rect.x, rect.y, null);

            } else if (rand.nextInt() % 2 != 50) {
                g.drawImage(op2, rect.x, rect.y, null);
            }

        }
//        g.drawString("Game Over",400,200);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Rectangle rect;
        count++;
        for (int i = 0; i < ocars.size(); i++) {
            rect = ocars.get(i);
            if (count % 1000 == 0) {
                if (move < 10) {
                    speed++;
                    move += 5;
                }
            }
            rect.y += speed;
        }
        //cars crashing with oponents
        for (Rectangle r : ocars) {
            if (r.intersects(car)) {
                car.y = r.y + height;
            }
        }
        for (int i = 0; i < ocars.size(); i++) {
            rect = ocars.get(i);
            if (rect.y + rect.height > HEIGHT) {
                ocars.remove(rect);
                addocars(false);
            }
        }
        for (int i = 0; i < line.size(); i++) {
            rect = line.get(i);
            if (count % 1000 == 0) {
                speed++;
            }
            rect.y += speed;
        }

        for (int i = 0; i < line.size(); i++) {
            rect = line.get(i);
            if (rect.y > HEIGHT) {
                line.remove(rect);

                addlines(false);
            }
        }
        repaint();
    }
//    movingup

    public void moveup() {
        if (car.y - move < 0) {
            System.out.println("\b");
        } else {
            car.y -= move;
        }
    }

    public void movedown() {
        if (car.y + move + car.height > HEIGHT - 1) {
            System.out.println("\b");
        } else {
            car.y += move;
        }
    }

    public void moveleft() {
        if (car.x - move < WIDTH / 2 - 90) {
            System.out.println("\b");
        } else {
            car.x -= move;
        }
    }

    public void moveright() {
        if (car.x + move > WIDTH / 2 + 10) {
            System.out.println("\b");
        } else {
            car.x += move;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                moveup();
                break;
            case KeyEvent.VK_DOWN:
                movedown();
                break;
            case KeyEvent.VK_LEFT:
                moveleft();
                break;
            case KeyEvent.VK_RIGHT:
                moveright();
                break;
            default:
                break;
        }
//        throw new UnsupportedOperationException("Not supported yet."); 
//To change body of generated methods, choose Tools | Templates.

    }

}
