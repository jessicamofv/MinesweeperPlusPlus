package minesweeperplusplus.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import minesweeperplusplus.MinesweeperPlusPlusApplication;

public class LogInHandler implements ActionListener
{ 
	private MinesweeperPlusPlusApplication application;
	private JTextField newUserField;
	private JComboBox existingUsersComboBox;
	
	public LogInHandler(MinesweeperPlusPlusApplication initApplication, JTextField initNewUserField, JComboBox initExistingUsersComboBox)
	{
		application = initApplication;
		newUserField = initNewUserField;
		existingUsersComboBox = initExistingUsersComboBox;
	}

	public void actionPerformed(ActionEvent ae)
	{
		String newUser = newUserField.getText();
		if (!(newUser == null) && !newUser.replaceAll("\\s", "").equals(""))
		{
			application.setCurrentUser(newUser);
			application.addCurrentUserToExistingUsers();
			application.getLogInWindow().setVisible(false);
			application.initLevelEditorWindow();
		}
		else
		{
			String existingUser = (String)existingUsersComboBox.getSelectedItem();
			if (!(existingUser == null) && !existingUser.equals(""))
			{
				application.setCurrentUser(existingUser);
				application.getLogInWindow().setVisible(false);
				application.initLevelEditorWindow();
			}
			else
				application.showUserErrorMessage();
		}
	}
}
