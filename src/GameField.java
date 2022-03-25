import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class GameField extends JPanel implements ActionListener {
    int SPEED = 150;
    private static final int UNIT = 40;
    private static final int DOT_SIZE = 16;
    public static int SIZE = UNIT*DOT_SIZE;

    private Image dot;
    private Image dot0;

    private Image apple;
    private int appleX;
    private int appleY;

    private Image Bigapple;
    private int BigappleX;
    private int BigappleY;

    private int[] x = new int[SIZE];
    private int[] y = new int[SIZE];
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
        dots = 2;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48 ;
        }
        timer = new Timer(SPEED,this);
        timer.start();
        createApple();
        createBigApple();
    }

    public void createApple(){
        appleX = new Random().nextInt(SIZE/DOT_SIZE)*DOT_SIZE;
        appleY = new Random().nextInt(SIZE/DOT_SIZE)*DOT_SIZE;
    }

    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
    }

    public void createBigApple(){
        BigappleX = new Random().nextInt(SIZE/DOT_SIZE)*DOT_SIZE;
        BigappleY = new Random().nextInt(SIZE/DOT_SIZE)*DOT_SIZE;
    }

    public void checkBigApple(){
        if(x[0] == BigappleX && y[0] == BigappleY){
            dots+=3;
            createBigApple();
        }
    }

    public void loadImages(){
        ImageIcon iiab = new ImageIcon("src/resources/Big_Apple_Nick.png");
        Bigapple = iiab.getImage();

        ImageIcon iia = new ImageIcon("src/resources/Apple_Bonya.png");
        apple = iia.getImage();

        ImageIcon iid = new ImageIcon("src/resources/Snake_Bys_Right.png");
        dot = iid.getImage();

        ImageIcon iid0 = new ImageIcon("src/resources/Snake_Bys_Right.png");
        dot0 = iid0.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(inGame){
            //Отрисовка горизонтальной линии
            for (int x = 0; x < UNIT*DOT_SIZE+UNIT; x+=DOT_SIZE) {
                g.setColor(Color.gray);
                g.drawLine(x, 0, x, UNIT*DOT_SIZE+(3*UNIT));
            }
            //Отрисовка вертикальной линии
            for (int y = 0; y < UNIT*DOT_SIZE+(3*UNIT); y+=DOT_SIZE) {
                g.setColor(Color.gray);
                g.drawLine(0, y, UNIT*DOT_SIZE+(3*UNIT), y);
            }

            g.drawImage(apple, appleX, appleY,this);
            g.drawImage(Bigapple, BigappleX, BigappleY,this);
            for (int i = 0; i < dots; i++) {
                if(i == 0){
                    g.drawImage(dot0,x[i],y[i],this);
                }else{
                    g.drawImage(dot,x[i],y[i],this);
                }
            }
        } else{
            String str0 = "Game Over ";
            String str1 = "Points: "+ (dots-2);
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
            checkBigApple();
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
                ImageIcon iid0 = new ImageIcon("src/resources/Snake_Bys_Left.png");
                dot0 = iid0.getImage();
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                ImageIcon iid0 = new ImageIcon("src/resources/Snake_Bys_Right.png");
                dot0 = iid0.getImage();
                right = true;
                up = false;
                down = false;
            }

            if(key == KeyEvent.VK_UP && !down){
                ImageIcon iid0 = new ImageIcon("src/resources/Snake_Bys_Up.png");
                dot0 = iid0.getImage();
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                ImageIcon iid0 = new ImageIcon("src/resources/Snake_Bys_Down.png");
                dot0 = iid0.getImage();
                right = false;
                down = true;
                left = false;
            }
            if(key == KeyEvent.VK_SPACE){
                if(!inGame){
                    timer.stop();
                    ImageIcon iid0 = new ImageIcon("src/resources/Snake_Bys_Right.png");
                    dot0 = iid0.getImage();
                    left = false;
                    right = true;
                    up = false;
                    down = false;
                    dots = 2;
                    inGame = true;
                    initGame();
                }
            }
        }
    }


}