import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import static javax.swing.JScrollPane.*;

public class BtnTest extends JFrame {
    public static final int WIDTH = 800, HEIGHT = 600;
    
    public static void main(String[] args) {
	EventQueue.invokeLater(() -> {
		new BtnTest().setVisible(true);
	    });
    }

    public BtnTest() {
	super();

	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLayout(new FlowLayout());
	setSize(WIDTH, HEIGHT);

	JTextArea tea = new JTextArea("", 5, 16);
	
	JButton button = new JButton("Click me!");
	button.addActionListener(event -> {
		tea.append("Clicked!\n");
	    });

	JScrollPane scp = new JScrollPane(tea, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER);

	add(button);
	add(scp);
    }
}
