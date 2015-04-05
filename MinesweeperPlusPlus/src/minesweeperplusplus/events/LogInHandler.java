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
		String existingUser = (String)existingUsersComboBox.getSelectedItem();
		// fields can be neither both blank nor both filled
		if (((newUser == null || newUser.trim().equals(""))
			&& (existingUser == null || existingUser.trim().equals("")))
			|| (!(newUser == null) && !newUser.trim().equals("")
			&& !(existingUser == null) && !existingUser.equals("")))
		{
			application.showLogInErrorMessage("Please enter a new username OR select an existing "
					+ "username.");
			newUserField.requestFocus();
		}
		else if (!(newUser == null) && !newUser.trim().equals(""))
		{
			if (application.isExistingUsername(newUser))
			{
				application.showLogInErrorMessage("There is already a user with that username.");
				newUserField.requestFocus();
			}
			else
			{
				application.setCurrentUser(newUser);
				application.addCurrentUserToExistingUsers();
				application.getLogInWindow().setVisible(false);
				application.initLevelEditorWindow();
			}
		}
		else
		{
			application.setCurrentUser(existingUser);
			application.getLogInWindow().setVisible(false);
			application.initLevelEditorWindow();
		}
	}
}
