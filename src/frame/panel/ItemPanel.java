package frame.panel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ItemPanel extends JPanel {
	
	private String name;
	private String price;

	public ItemPanel(String _name, String _price) {
		name = _name;
		price = _price;
		
		JLabel lName = new JLabel(_name);
		JLabel lPrice = new JLabel(_price);
		
		add(lName);
		add(lPrice);
		
		setPreferredSize(new Dimension(400, 200));
		setBorder(BorderFactory.createEmptyBorder());
		setBackground(Color.WHITE);
	}
}
