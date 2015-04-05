package minesweeperplusplus;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Vector;

import javax.swing.*;

import minesweeperplusplus.events.LogInHandler;
import minesweeperplusplus.events.LogInWindowHandler;

public class MinesweeperPlusPlusApplication
{
	private static final String USERS_FILE = "./setup/Minesweeper++Users.txt";
	private JFrame logInWindow;
	private JTextField newUserField;
	private Vector<String> existingUsersList;
	private JComboBox existingUsersComboBox;
	String currentUser;
	private JButton logInButton;
	private MinesweeperPlusPlusLevelEditor editor;
	
	// INITIALIZE THE OPENING WINDOW OF APPLICATION AND ITS COMPONENTS
	public MinesweeperPlusPlusApplication()
	{
		initWindow();
		layoutGUI();
		initHandlers();
	}
	
	public JFrame getLogInWindow()
	{
		return logInWindow;
	}
	
	public String getCurrentUser() { return currentUser; }
	public void setCurrentUser(String initCurrentUser) { currentUser = initCurrentUser; }
	public void clearCurrentUser() { currentUser = null; }
	
	// INITIALIZE LOG-IN WINDOW, THE OPENING WINDOW OF APPLICATION
	public void initWindow()
	{
		logInWindow = new JFrame("Minesweeper++");
		logInWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
		logInWindow.setResizable(false);
		logInWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		int screenWidth = (int) screen.getWidth();
		int screenHeight = (int) screen.getHeight();
		int windowWidth = logInWindow.getWidth();
		int windowHeight = logInWindow.getHeight();
		
	
		logInWindow.setLocation((screenWidth - windowWidth)/3, (screenHeight - windowHeight)/6);
	}
	
	// INITIALIZE AND LAY OUT COMPONENTS OF LOG-IN WINDOW
	public void layoutGUI()
	{
		JPanel usersPanel = new JPanel();
		usersPanel.setLayout(new GridBagLayout());
		
		JPanel newUserPanel = new JPanel();
		JLabel newUser = new JLabel("New User:        ");
		newUserField = new JTextField(15);
		((FlowLayout)newUserPanel.getLayout()).setAlignment(FlowLayout.LEFT);
		newUserPanel.add(newUser);
		newUserPanel.add(newUserField);
		placeComponentInContainer(usersPanel, newUserPanel, 0, 0, 1, 1);
		
		JPanel existingUsersPanel = new JPanel();
		JLabel existingUser = new JLabel("Existing User: ");
		existingUsersList = new Vector<String>();
		loadExistingUsers();
		existingUsersComboBox = new JComboBox(existingUsersList);
		((FlowLayout)existingUsersPanel.getLayout()).setAlignment(FlowLayout.LEFT);
		existingUsersPanel.add(existingUser);
		existingUsersPanel.add(existingUsersComboBox);
		placeComponentInContainer(usersPanel, existingUsersPanel, 0, 1, 1, 1);
		
		logInWindow.add(usersPanel, BorderLayout.CENTER);
		
		JPanel logInPanel = new JPanel();
		logInButton = new JButton("LOG IN");
		((FlowLayout)logInPanel.getLayout()).setAlignment(FlowLayout.RIGHT);
		logInPanel.add(logInButton);
		
		logInWindow.add(logInPanel, BorderLayout.SOUTH);
		
		logInWindow.setSize(500, 500);
	}
	
	// HELPER METHOD FOR ARRANGING COMPONENTS IN A CONTAINER THAT UTILIZES GridBagLayout
	void placeComponentInContainer(Container c, JComponent jc, int col, int row, int colSpan, 
			int rowSpan)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = col;
		gbc.gridy = row;
		gbc.gridwidth = colSpan;
		gbc.gridheight = rowSpan;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		c.add(jc, gbc);
	}
	
	void loadExistingUsers()
	{
		existingUsersList.add("");
		
		try
		{
			Reader reader = new FileReader(USERS_FILE);
			BufferedReader in = new BufferedReader(reader);
			
			String line = in.readLine();
			
			while (!(line == null) && !line.equals(""))
			{
				existingUsersList.add(line);
				line = in.readLine();
			}
			
			in.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(logInWindow, "Error loading existing users");
			System.exit(0);
        }
	}
	
	// INITIALIZE HANDLERS FOR PRESSING LOG-IN BUTTON AND EXING-OUT OF WINDOW
	public void initHandlers()
	{
		LogInWindowHandler mainWH = new LogInWindowHandler(this);
		logInWindow.addWindowListener(mainWH);
		
		LogInHandler logInH = new LogInHandler(this, newUserField, existingUsersComboBox);
		logInButton.addActionListener(logInH);
	}
	
	public void addCurrentUserToExistingUsers()
	{
		try
		{
			existingUsersList.add(currentUser);
			
			Writer writer = new FileWriter(USERS_FILE);
			PrintWriter out = new PrintWriter(writer);
			
			for (int i = 0; i < existingUsersList.size(); i++)
				out.println(existingUsersList.elementAt(i));
			
			out.flush();
			out.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(logInWindow, "Error adding user to existing users");
			System.exit(0);
        }
	}
	
	public boolean isExistingUsername(String usernameToCheck)
	{
		for (String existingUsername : existingUsersList)
		{
			if (usernameToCheck.equalsIgnoreCase(existingUsername))
			{
				return true;
			}
		}
		
		return false;
	}
	
	// INITIALIZE LEVEL EDITOR WINDOW, WHICH OPENS UPON PRESSING OF LOG-IN BUTTON
	public void initLevelEditorWindow()
	{
		editor = new MinesweeperPlusPlusLevelEditor(this);
		editor.startLevelEditor();
	}
	
	public void showLogInErrorMessage(String message)
	{
		JOptionPane.showMessageDialog(logInWindow, message);
	}
	
	// CLOSE APPLICATION UPON EXING-OUT OF LOG-IN WINDOW
	public void killApplication()
	{
		logInWindow.setVisible(false);
		System.exit(0);
	}
	
	// RUN APPLICATION BY INITIALIZING AND OPENING LOG-IN WINDOW
	public static void main(String[] args)
	{
		MinesweeperPlusPlusApplication mppApp = new MinesweeperPlusPlusApplication();
		mppApp.logInWindow.setVisible(true);
	}
}
