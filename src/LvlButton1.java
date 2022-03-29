import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LvlButton1 {

    private int sizeButton = 50;
    private JButton lightButton;
    private JButton simpleButton;
    private JButton hardButton;

    JPanel panel = new JPanel();
    GridBagConstraints c = new GridBagConstraints();


    public void LvlButton1() {
        setupView();
        setupButtons();
        panel.setVisible(true);
    }

    private void setupView() {
        panel.setLayout(new GridBagLayout());
    }

    private void setupButtons() {
//        lightButton
        this.lightButton = new JButton();
        lightButton.setIcon(new ImageIcon("src/Apple_Bonya.png"));
        lightButton.setPreferredSize(new Dimension(sizeButton, sizeButton));
        lightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button_1");
                GameField.SPEED = 200;
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(0,0,50,0);

        panel.add(lightButton, c);

        // simpleButton
        this.simpleButton = new JButton();
        simpleButton.setIcon(new ImageIcon("src/Big_Apple_Nick.png"));
        simpleButton.setPreferredSize(new Dimension(sizeButton, sizeButton));
//        simple.setEnabled(false);//Делает кнопку не кликабельной
        simpleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button_2");
                GameField.SPEED = 150;
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(0,25,50,25);

        panel.add(simpleButton, c);

//        hardButton
        this.hardButton = new JButton();
        hardButton.setIcon(new ImageIcon("src/logo.png"));
        hardButton.setPreferredSize(new Dimension(sizeButton, sizeButton));
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button_3");
                GameField.SPEED = 100;
            }
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(0,0,50,0);

        panel.add(hardButton, c);
    }
}