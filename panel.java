import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

public class panel extends JPanel implements ActionListener {

    static int width = 1000;
    static int height = 600;
    static int unit = 50;
    Timer time;
    static int delay = 200;
    Random random;
    int fx, fy;
    int body = 3;
    char dir = 'R';

    int score;
    boolean flag = false;
    int[] xsnake = new int[280];
    int[] ysnake = new int[280];

    panel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new key());
        random = new Random();
        gamestart();
    }

    public void gamestart() {
        flag = true;
        spwanfood();
        time = new Timer(delay, this);
        time.start();
    }


    public void spwanfood() {

        fx = random.nextInt((int) width / unit) * unit;
        fy = random.nextInt((int) height / unit) * unit;
    }

    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        draw(graphic);
    }

    public void draw(Graphics graphic) {
        if (flag == true) {
            graphic.setColor(Color.orange);
            graphic.fillOval(fx, fy, unit, unit);
            //this is the head of the snake
            for (int i = 0; i < body; i++) {
                if (i == 0) {
                    graphic.setColor(Color.red);
                    graphic.fillOval(xsnake[i], ysnake[i], unit, unit);
                } else {
                    // the body of the snake
                    graphic.setColor(Color.blue);
                    graphic.fillOval(xsnake[i], ysnake[i], unit, unit);
                }
            }
            //spawning the score display
            graphic.setColor(Color.CYAN);
            //setting the font
            graphic.setFont(new Font("comic sans", Font.BOLD, 40));
            FontMetrics fme = getFontMetrics(graphic.getFont());
            graphic.drawString("Score :" + score, (width - fme.stringWidth("Score" + score)) / 2, graphic.getFont().getSize());

        } else {
            gameOver(graphic);

        }
    }

    public void gameOver(Graphics graphic) {
        //drawing the score
        graphic.setColor(Color.CYAN);
        //setting the font
        graphic.setFont(new Font("comic sans", Font.BOLD, 40));
        FontMetrics fme = getFontMetrics(graphic.getFont());
        graphic.drawString("Score :" + score, (width - fme.stringWidth("Score" + score)) / 2, graphic.getFont().getSize());
//                draw the gameOver text
        graphic.setColor(Color.red);
        //setting the font
        graphic.setFont(new Font("comic sans", Font.BOLD, 80));
        FontMetrics fme1 = getFontMetrics(graphic.getFont());
        graphic.drawString("Game Over :", (width - fme1.stringWidth("Game Over")) / 2, height / 2);
        //drawing the replay prompt
        graphic.setColor(Color.green);
        //setting the font
        graphic.setFont(new Font("comic sans", Font.BOLD, 40));
        FontMetrics fme2 = getFontMetrics(graphic.getFont());
        graphic.drawString("Press R to Replay", (width - fme2.stringWidth("Press R to Replay")) / 2, height / 2 - 150);


    }

    public void move() {
        for (int i = body; i > 0; i--) {
            xsnake[i] = xsnake[i - 1];
            ysnake[i] = ysnake[i - 1];
        }
        //update the head of the snake
        switch(dir){
            case 'R':
                xsnake[0] = xsnake[0] + unit;
                break;
            case 'L':
                xsnake[0] = xsnake[0] - unit;
                break;
            case 'U':
                ysnake[0] = ysnake[0] - unit;
                break;
            case 'D':
                ysnake[0] = ysnake[0] + unit;
                break;
        }

    }


    //checking out of bound
    public void check() {
        if (xsnake[0] < 0) {
            flag = false;
        } else if (xsnake[0] > width) {
            flag = false;
        } else if (ysnake[0] < 0) {
            flag = false;
        } else if (ysnake[0] > height) {
            flag = false;
        }
        //checking hit with body
        for (int i = body; i > 0; i--) {
            if ((xsnake[0] == xsnake[i]) && (ysnake[0] == ysnake[i])) {
                flag = false;
            }
        }
        if (flag == false) {
            time.stop();
        }
    }

    public void eat() {
        if ((xsnake[0] == fx) && (ysnake[0] == fy)) {
            body++;
            score++;
            spwanfood();
        }
    }


    public class key extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (dir != 'D') {
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (dir != 'u') {
                        dir = 'D';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (dir != 'L') {
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (dir != 'R') {
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_R:
                    if (!flag) {
                        score = 0;
                        body = 3;
                        dir = 'R';
                        Arrays.fill(xsnake, 0);
                        Arrays.fill(ysnake, 0);
                        gamestart();
                    }
                    break;
            }

        }
    }

        //        @Override
        public void actionPerformed(ActionEvent e) {
            if (flag) {
                move();
                eat();
                check();
            }
            repaint();

        }

    }


