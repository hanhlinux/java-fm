 import java.awt.*;
 import javax.swing.*;
 import java.awt.event.*;
 import java.io.*;
 import java.util.List;
 import java.util.ArrayList;


public class fm extends JFrame{
	// Align some variables that need to be used in multiple functions
		File current ;
		File[] f; 
		JButton btn;
		String dir, name;
		JPanel panel; 
		JTextField txt;
	public static void main (String[] args) {
		fm FM = new fm();
	}
	
	public fm(){		
		// Start graphical interface 
		JFrame main = new JFrame();
		panel = new JPanel();
		BoxLayout box = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
		JScrollPane pane = new JScrollPane();
		txt = new JTextField(); 
		
		// Properties for `txt`
		txt.setMaximumSize(new Dimension(3840, 20));
	    txt.addKeyListener(new KeyListener(){
			// KeyListener for task
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				// Do if `Enter` key is pressed
				if (key == 10) {
					// Check if it is file, directory or null
					File inserted = new File(txt.getText());
					if (inserted.isFile()){
						try {
							Desktop.getDesktop().open(inserted);
							} catch (Throwable e1) {
								JOptionPane.showMessageDialog(null, e.toString());
							}
						} 
					else if (inserted.isDirectory()) {
						dir="";
						panel.removeAll();
						panel.updateUI();
						getDir(inserted.getAbsolutePath());
						} 
					else {
								JOptionPane.showMessageDialog(null, "Not found!");
						}
					} 
					// Do if left arrow key is pressed
					else if (key == 37) {
						panel.removeAll();
						panel.updateUI();
						getDir("..");
					} 
					// Do if top arrow key is pressed
					else if (key == 38) {
						dir="";
						panel.removeAll();
						panel.updateUI();
						getDir(System.getProperty("user.home"));
					}
				}
				
			// Ignore the other one
			public void keyTyped(KeyEvent e) {}			
			public void keyReleased(KeyEvent e) {}
			
			});
		
		// Properties for `pane`
		pane.setViewportView(panel);
		// Properties for `panel`
		panel.setLayout(box);
		panel.setBackground(Color.white);
	
		// Run task
		getDir(System.getProperty("user.home"));
		
		// Properties for `main` - this should be set when everything is ready
		main.setBackground(Color.white);
		main.setSize(800,600);
		main.setDefaultCloseOperation(EXIT_ON_CLOSE);
		main.setContentPane(pane);
		main.setTitle("File Manager");
		main.setVisible(true);
		}
		
		void getDir(String x) {
			// Check if the `dir` variable is null
			if (dir == null) {
				dir = "/" + x;
			} 
			else {
				dir = dir + "/" + x;
			}
			current = new File(dir);
			// Do some tasks
			txt.setText(dir.trim());
			panel.add(txt);
			f = current.listFiles();
			// Provide a list of buttons
			for(File f1 : f) {
				// Properties for the buttons
				btn = new JButton(); 
				if (f1.isFile()){
					btn.setText( "<file>" + f1.getName());
				}
				else {
					btn.setText("<dir> " + f1.getName() );
				}
				btn.setBackground(Color.white);
				btn.setForeground(Color.black);
				btn.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
				btn.setHorizontalAlignment(SwingConstants.LEFT);
				
				btn.addActionListener(new ActionListener(){
					// Function for the clicked button 
					public void actionPerformed(ActionEvent e) {
						// Set some variables
						String test = dir + "/" + f1.getName(); 
						File testfile = new File(test);
						// Check if it is a file or directory
						if (testfile.isDirectory()){
							// If it is a directory,  get the directory structure
							panel.removeAll();
							panel.updateUI();
							panel.add(txt);
							getDir(f1.getName());
							panel.repaint();
						} 
						else {
							// If it is a file, open it.
							try {
								Desktop.getDesktop().open(testfile);
							} catch (Throwable a) {
								JOptionPane.showMessageDialog(null, a.toString());
							}
					}
				}});
			panel.add(btn);
			}
		}
}


