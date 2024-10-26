package patientManagementSystem;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
public class MyLabel extends JLabel {
	public MyLabel(String text) {
		this.setText(text);
		this.setForeground(new Color(51, 51, 51));
		this.setFont(new Font("Monospaced",Font.BOLD,20));
	}
}
