import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;

public class ChipGui extends JPanel {

    public Image image;
    public String filePath;

    public void setImageFilePath(String imageFilePath) {
        this.filePath = imageFilePath;
    }

    @Override
    public void paintComponent(Graphics g) {
        //atx transformation to enable scaling
        AffineTransform at = new AffineTransform();
        Graphics2D gd2 = (Graphics2D) g;
        //scales image down
        gd2.scale(.35,.35);
       //works with Image class rather than ImageIcon as its JPanel not Label
        try {
            image = ImageIO.read(new File(filePath));
        } catch (Exception e){

        }

        gd2.drawImage(image,at,null);
        super.paintComponents(g);
    }

}




