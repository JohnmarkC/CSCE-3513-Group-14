import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Splash extends JFrame
{

	public Splash()
	{
		//JFrame splash = new JFrame();

		// Set some window properties
		this.setTitle("Photon");
		this.setSize(900, 650);
		this.setFocusable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
        // Load and add the image
        ImageIcon imageIcon = new ImageIcon("logo.jpg");
        Image originalImage = imageIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH); 
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        this.add(imageLabel);

        this.setVisible(true);

        // Wait for 3 seconds
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

	}

	public static void main(String[] args)
	{
		Splash g = new Splash();
	}
	
}
