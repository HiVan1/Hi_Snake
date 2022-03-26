import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class GameField extends JPanel implements ActionListener {
    int SPEED = 125;//скорсть игры
    private static final int UNIT = 40;//Кол-во клеток(wight, height)
    public static final int ONE_UNIT = 16;//Размер клеток
    public static int SIZE = UNIT*ONE_UNIT;//Максимальный размер карты

    private Image snake;//Объект для картинки змеи
    private Image snakeHead;//Объект для картинки головы змеи

    public Image apple;//Объект для картинки яблока
    private static int appleX;//Положение яблока по Х
    private static int appleY;//Положение яблока по У

    private Image BigApple;//Объект для картинки большого яблока
    private int BigAppleX;//Положение большого яблока по Х
    private int BigAppleY;//Положение большого яблока по У

    public static int[] x = new int[SIZE];//Массив для змеи по Х
    public static int[] y = new int[SIZE];//Массив для змеи по У
    public static int sizeSnake;//Размер змеи
    private Timer timer;//Объект таймера
    //Направление движения змеи
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private boolean alive = false;//Жива змея true/false
    private boolean died = false;//Мертва змея true/false
    private boolean pause = false;//Игра на пузе true/false
    private boolean helloMenu = true;//Открыто главное меню true/false


//    Apple app = new Apple();

    //Конструкор класса
    public GameField(){
        loadImages();//Закгрузка изображений
        initGame();//Инициализация игры
        addKeyListener(new FieldKeyListener());//Прослушивание нажатий
        setFocusable(true);
    }

    public void initGame(){
        sizeSnake = 2;//Начальный размер змеи
        //Начальное положение змеи
        for (int i = 0; i < sizeSnake; i++) {
            x[i] = 48 - i*ONE_UNIT;
            y[i] = 48 ;
        }
        timer = new Timer(SPEED,this);
        timer.start();//Запуск игры
        createApple();//Создание яблока
        createBigApple();//Создание большого яблока
    }
    //Рандомное положение яблока по Х и У
    public void createApple(){
        appleX = new Random().nextInt(SIZE/ONE_UNIT)*ONE_UNIT;
        appleY = new Random().nextInt(SIZE/ONE_UNIT)*ONE_UNIT;
    }
    //Проверка на сталкновение с яоблоком
    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            sizeSnake++;
            createApple();
        }
    }
    //Рандомное положение большого яблока по Х и У
    public void createBigApple(){
        BigAppleX = new Random().nextInt(SIZE/ONE_UNIT)*ONE_UNIT;
        BigAppleY = new Random().nextInt(SIZE/ONE_UNIT)*ONE_UNIT;
    }
    //Проверка на сталкновение с большим яоблоком
    public void checkBigApple(){
        if(x[0] == BigAppleX && y[0] == BigAppleY){
            sizeSnake+=2;
            createBigApple();
        }
    }

    public void loadImages(){
        //Картинка большого яблока
        ImageIcon iiab = new ImageIcon("src/resources/Big_Apple_Nick.png");
        BigApple = iiab.getImage();
        //Картинка яблока
        ImageIcon iia = new ImageIcon("src/resources/Apple_Bonya.png");
        apple = iia.getImage();
        //Картинка тела змеи
        ImageIcon iid = new ImageIcon("src/resources/Snake_Bys_Right.png");
        snake = iid.getImage();
        //Картинка головы змеи
        ImageIcon iid0 = new ImageIcon("src/resources/Snake_Bys_Right.png");
        snakeHead = iid0.getImage();
    }

    //Отрисовка игры
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Отрисовка главного меню
        if(helloMenu){
            setBackground(Color.pink);//Задний фон главного меню
            //Вывод главного меню
            String menu_text_1 = "Rules:";
            String menu_text_2 = "-Press 'S' to pause";
            String menu_text_3 = "-Use 'Up', 'Down', 'Left', 'Right' to control ";
            String menu_text_4 = "Press 'M' to start...";

            Font f1 = new Font("Arial", Font.BOLD, 25);
            g.setFont(f1);
            g.drawString(menu_text_1,80,SIZE/2 - 30);
            g.drawString(menu_text_2,90,SIZE/2);
            g.drawString(menu_text_3,90,SIZE/2 + 30);
            g.drawString(menu_text_4,SIZE/2 - 80,SIZE/2 + 200);
        }
        //Отрисовка игрового поля
        if(alive){
            setBackground(Color.lightGray);//Задний фон игрового поля
            //Отрисовка горизонтальной линии
            for (int x = 0; x < UNIT*ONE_UNIT+UNIT; x+=ONE_UNIT) {
                g.setColor(Color.gray);
                g.drawLine(x, 0, x, UNIT*ONE_UNIT+(3*UNIT));
            }
            //Отрисовка вертикальной линии
            for (int y = 0; y < UNIT*ONE_UNIT+(3*UNIT); y+=ONE_UNIT) {
                g.setColor(Color.gray);
                g.drawLine(0, y, UNIT*ONE_UNIT+(3*UNIT), y);
            }
            //Отрисовка яблок
            g.drawImage(apple, appleX, appleY,this);
            g.drawImage(BigApple, BigAppleX, BigAppleY,this);

            for (int i = 0; i < sizeSnake; i++) {
                if(i == 0){
                    //Отрисовка головы змеи
                    g.drawImage(snakeHead,x[i],y[i],this);
                }else{
                    //Отрисовка тела змеи
                    g.drawImage(snake,x[i],y[i],this);
                }
            }
        }
        //Отрисовка экрана после смерти
        if(died){
            setBackground(Color.cyan);//Задний фон экрана после смерти
            alive = false;
            String str0 = "Game Over ";
            String str1 = "Points: "+ (sizeSnake-2);
            String str2 = "Press 'Space' to restart";
            Font f = new Font("Arial", Font.BOLD, 14);
            g.setColor(Color.black);
            g.setFont(f);
            g.drawString(str0,SIZE/2 - 35,SIZE/2 - 30);
            g.drawString(str1,SIZE/2 - 25,SIZE/2);
            g.drawString(str2,SIZE/2 - 75,SIZE/2 + 30);
        }
    }

    //Движение змейки
    public void move(){
        for (int i = sizeSnake; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= ONE_UNIT;
        }
        if(right){
            x[0] += ONE_UNIT;
        } if(up){
            y[0] -= ONE_UNIT;
        } if(down){
            y[0] += ONE_UNIT;
        }
    }

    //Проверка столкновения с своим телом и с краями карты
    public void checkCollisions(){
        for (int i = sizeSnake; i >0 ; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                died = true;
            }
        }

        if(x[0]>SIZE){
            died = true;
        }
        if(x[0]<0){
            died = true;
        }
        if(y[0]>SIZE){
            died = true;
        }
        if(y[0]<0){
            died = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(alive){
            checkApple();
            checkBigApple();
            checkCollisions();
            move();

        }
        repaint();
    }
    //Слушание нажатий клавиш
    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                ImageIcon iid0 = new ImageIcon("src/resources/Snake_Bys_Left.png");
                snakeHead = iid0.getImage();
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                ImageIcon iid0 = new ImageIcon("src/resources/Snake_Bys_Right.png");
                snakeHead = iid0.getImage();
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && !down){
                ImageIcon iid0 = new ImageIcon("src/resources/Snake_Bys_Up.png");
                snakeHead = iid0.getImage();
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                ImageIcon iid0 = new ImageIcon("src/resources/Snake_Bys_Down.png");
                snakeHead = iid0.getImage();
                right = false;
                down = true;
                left = false;
            }
            if(key == KeyEvent.VK_SPACE){
                if(died){
                    timer.stop();

                    ImageIcon iid0 = new ImageIcon("src/resources/Apple_Bonya.png");
                    snakeHead = iid0.getImage();

                    left = false;
                    right = true;
                    up = false;
                    down = false;

                    sizeSnake = 2;

                    alive = true;
                    died = false;
                    initGame();
                }
            }
            if(key == KeyEvent.VK_ENTER){
                if(!pause){
                    timer.stop();
                    pause = true;
                }else{
                    timer.start();
                    pause = false;
                }
            }
            if(key == KeyEvent.VK_M){
                if(!alive){
                    helloMenu = false;
                    alive = true;
                }
            }
        }
    }


}