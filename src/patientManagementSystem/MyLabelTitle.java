package patientManagementSystem;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
public class MyLabelTitle extends JLabel {
	public MyLabelTitle(String text) {
		this.setText(text);
		this.setForeground(new Color(102, 0, 153));
		this.setFont(new Font("Monospaced",Font.BOLD,20));
	}
}
