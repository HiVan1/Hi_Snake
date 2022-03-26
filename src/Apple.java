//import javax.swing.*;
//import java.awt.*;
//import java.util.Random;
//
//public class Apple extends JPanel {
//
//    private static int appleX;
//    private static int appleY;
//
////    GameField game = new GameField();
//
//    public static void createApple(){
//        appleX = new Random().nextInt(GameField.SIZE/GameField.ONE_UNIT)*GameField.ONE_UNIT;
//        appleY = new Random().nextInt(GameField.SIZE/GameField.ONE_UNIT)*GameField.ONE_UNIT;
//    }
//
//    public static void checkApple(){
//        if(GameField.x[0] == appleX && GameField.y[0] == appleY){
//            GameField.sizeSnake++;
//            createApple();
//        }
//    }
//
//
//    @Override
//    protected static void paintComponent(Graphics g){
//        super.paintComponent(g);
//        g.drawImage(GameField.apple, appleX, appleY, this);
//    }
//
//}
