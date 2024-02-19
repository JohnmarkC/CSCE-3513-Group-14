import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Splash extends JFrame
{

	public Splash(){
		// Set some window properties
		this.setTitle("Photon");
		this.setSize(900, 650);
		this.setFocusable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
    	}

    public void create(){
        // Load and add the image
        ImageIcon imageIcon = new ImageIcon("logo.jpg");
        Image originalImage = imageIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH); 
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        this.add(imageLabel);

        this.setVisible(true);
    }
    
	public static void main(String[] args){
		Splash g = new Splash();
	}
	
}
