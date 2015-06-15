package botMilAnuncios;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.*;
public class View extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea = new JTextArea(30, 60);
	private TextOutputStream outputStream = new TextOutputStream(textArea, "Log");
	public View(){
		setLayout(new BorderLayout());
		add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

		System.setOut(new PrintStream(outputStream));

		int delay=200;//ms
		new Timer(delay, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {		    
				
			}
			
		}).start();
	}

	private static void createAndShowGui(){
		JFrame frame = new JFrame("Bot MilAnuncios");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new View());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	 public  void main(String[] args) {
	      SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
	            createAndShowGui();
	         }
	      });
	   }
}
