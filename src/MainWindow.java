import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow(){
        setIconImage(new ImageIcon("logo.png").getImage());
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(336*2, 359*2);
        setLocation(500,5);
        add(new GameField());
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {MainWindow mw = new MainWindow();}
}
