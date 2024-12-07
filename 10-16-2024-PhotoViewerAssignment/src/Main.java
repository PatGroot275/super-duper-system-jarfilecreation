import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main {
    public static int current = 0;
    public static void main(String[] args) {
        ImageIcon[] images = {
            /*  
            WHAT YOUR CODE SHOULD LOOK LIKE BEFORE YOU EDIT IT FOR THE JAR FILE CREATION
            new ImageIcon("src\\images\\image1.png")
            new ImageIcon("src\\images\\image2.png")
            new ImageIcon("src\\images\\image3.png")
            */

            //WHAT YOUR CODE SHOULD LOOK LIKE NOW FOR THE JAR FILE CREATION
            new ImageIcon(Main.class.getResource("/src/images/image1.png")),
            new ImageIcon(Main.class.getResource("/src/images/image2.png")),
            new ImageIcon(Main.class.getResource("/src/images/image3.png"))
        };
        
        JFrame frame = new JFrame("Photo Viewer");
        frame.setLayout(null);
        frame.setSize(960, 540);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panelTop = new JPanel();
        panelTop.setLayout(null);
        panelTop.setBounds(0, 5, 960, 60);

        JLabel title = new JLabel("Photo Viewer");
        title.setBounds(430, 10, 100, 30);

        
        //Go back, negative, 0
        JButton leftButton = new JButton("Back");
        leftButton.setBounds(10,10,100,50);

        //Go forward, positive, 1
        JButton rightButton = new JButton("Next");
        rightButton.setBounds(820,10,100,50);

        JPanel panelImage = new JPanel();
        panelImage.setLayout(null);
        panelImage.setBounds(0, 100, 960, 470);

        JLabel image = new JLabel();
        image.setIcon(images[current] );
        image.setBounds(390,10,920,410);

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (current == 0) {
                    current = 2;
                }
                else {
                    current = current - 1;
                }
                image.setIcon(images[current]);
                panelImage.add(image);
                panelImage.setVisible(true);
            }
        });


        rightButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (current == 2) {
                    current = 0;
                }
                else {
                    current = current + 1;
                }
                image.setIcon(images[current]);
                panelImage.add(image);
                panelImage.setVisible(true);
            }
        });
        

        panelTop.add(title);
        panelTop.add(leftButton);
        panelTop.add(rightButton);
        panelTop.setVisible(true);
        panelImage.add(image);
        panelImage.setVisible(true);

        frame.add(panelTop);
        frame.add(panelImage);
        frame.setVisible(true);
    }
}
