import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow(){

        setIconImage(new ImageIcon("src/resources/logo.png").getImage());
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(GameField.SIZE+49, GameField.SIZE+72);
        setLocationRelativeTo(null);
        add(new GameField());
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {MainWindow mw = new MainWindow();}
}
