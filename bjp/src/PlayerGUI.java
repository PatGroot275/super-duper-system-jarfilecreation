import javax.swing.*;
import java.awt.*;

//gui only uses playerGUIs because if the playerGUI was put in a normal JPanel no rotation would occur, made code alot messier
public class PlayerGUI extends JPanel {
    int rotation;

    //sets the Panel's rotaiton
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }


    @Override
    public void paintComponent(Graphics g) {
        //rotates the panel about its center
        Graphics2D g2d = (Graphics2D) g;
        int midLength = getWidth() / 2;
        int midHeight = getHeight() / 2;
        g2d.rotate(Math.toRadians(rotation), midLength, midHeight);
        super.paintComponent(g);

    }
}


