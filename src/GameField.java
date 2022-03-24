import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Random;


public class GameField extends JPanel implements ActionListener {
    int points = 0;
    int SPEED = 176;

    private final int SIZE = 320*2;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = (SIZE/DOT_SIZE)*(SIZE/DOT_SIZE);
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;


    public GameField(){
        setBackground(Color.lightGray);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);

    }





    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48 ;
        }
        timer = new Timer(SPEED,this);
        timer.start();
        createApple();
    }

    public void createApple(){
        appleX = new Random().nextInt(SIZE/DOT_SIZE)*DOT_SIZE;
        appleY = new Random().nextInt(SIZE/DOT_SIZE)*DOT_SIZE;
    }

    public void loadImages(){
        ImageIcon iia = new ImageIcon("Apple_Bonya.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("Snake_Bys.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple,appleX,appleY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i],y[i],this);
            }
        } else{
            String str0 = "Game Over ";
            String str1 = "Points: "+ points;
            String str2 = "Press 'Space' to restart";
            Font f = new Font("Arial", Font.BOLD, 14);
            g.setColor(Color.black);
            g.setFont(f);
            g.drawString(str0,SIZE/2 - 35,SIZE/2 - 30);
            g.drawString(str1,SIZE/2 - 25,SIZE/2);
            g.drawString(str2,SIZE/2 - 75,SIZE/2 + 30);
        }
    }

    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        } if(up){
            y[0] -= DOT_SIZE;
        } if(down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            points ++;
            dots++;
            createApple();
        }
    }

    public void checkCollisions(){
        for (int i = dots; i >0 ; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }

        if(x[0]>SIZE){
            inGame = false;
        }
        if(x[0]<0){
            inGame = false;
        }
        if(y[0]>SIZE){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkCollisions();
            move();

        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }

            if(key == KeyEvent.VK_UP && !down){
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                right = false;
                down = true;
                left = false;
            }
            if(key == KeyEvent.VK_SPACE){
                if(!inGame){
                    timer.stop();
                    left = false;
                    right = true;
                    up = false;
                    down = false;
                    points = 0;
                    inGame = true;
                    initGame();
                }
            }
        }
    }


}