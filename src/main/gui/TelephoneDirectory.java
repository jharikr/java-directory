package main.gui;


import main.core.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

public class TelephoneDirectory {
	private ListDirectory directory;

	private JTextField surnameField;
	private JTextField initialsField;
	private JTextField telExtensionField;
	
	private JTextArea output;
	
	/*
	 * Default constructor that instantiates the ListDirectory object 
	 */
	public TelephoneDirectory() {
		directory = new ListDirectory();
	}
	
	public static void main(String[] args) throws Exception  {
        new TelephoneDirectory().launch();
    }
	
	/**
	 * Launches the telephone directory.
	 */
	public void launch() throws Exception {
		final JFrame frame = new JFrame("Newcastle University Internal Telephone Directroy");
       
		frame.setLayout(new BorderLayout());
		
		//frame.add(getFieldLabels(), BorderLayout.WEST);
		frame.add(getEntryFields(), BorderLayout.NORTH);
		frame.add(getDirectoryButton(), BorderLayout.CENTER);
		frame.add(getOutPutPanel(),  BorderLayout.SOUTH);
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
	
	private JPanel getFieldLabels() {
		final JPanel fieldLabels = new JPanel(new GridLayout (1,3));
		
		JLabel surnameLabel = new JLabel("Surname");
		JLabel initalsLabel = new JLabel("Initials");
		JLabel telExtensionLabel = new JLabel("Telephone Extension");
		
		fieldLabels.add(surnameLabel);
		fieldLabels.add(initalsLabel);
		fieldLabels.add(telExtensionLabel);
		
		return fieldLabels;
	}
	
	/**
	 * Creates input text fields for the telephone directory.
	 * 
	 * @return the input text fields
	 */
	private JPanel getEntryFields() {
		final JPanel entryFields = new JPanel(new GridLayout (1,6));
		

		surnameField = new JTextField(20);
		initialsField = new JTextField(10);
		telExtensionField = new JTextField(10);
		
		JLabel surnameLabel = new JLabel("Surname");
		JLabel initalsLabel = new JLabel("Initials");
		JLabel telExtensionLabel = new JLabel("Telephone Extension");
		
		entryFields.add(surnameLabel);
		entryFields.add(surnameField);
		entryFields.add(initalsLabel);
		entryFields.add(initialsField);
		entryFields.add(telExtensionLabel);
		entryFields.add(telExtensionField);

		return entryFields;
	}
	
	/**
	 * Creates buttons for the telephone directory. Each button performs a corresponding method 
	 * from the the Directory interface using the ListDirectory class implementation. 
	 * 
	 * @return the action buttons
	 */
	private JPanel getDirectoryButton() {
		final JPanel buttons = new JPanel(new GridLayout (2,3));
		
		final JButton insertEntryButton = new JButton("Insert");
		final JButton deleteByNameButton = new JButton("Delete by Name");
		final JButton deleteByNumButton = new JButton("Delete by Number");
		final JButton lookUpButton = new JButton("Look Up");
		final JButton changeTelExtButton = new JButton("Change Telephone Extension");
		final JButton displayDirectoryButton = new JButton("Print Directory");
		
		
		//Action Listener - Insert Entry
		insertEntryButton.addActionListener( (ae) -> {
			
			try {
				String surname = surnameField.getText();
				String initials = initialsField.getText();
				int telExtension = Integer.parseInt(telExtensionField.getText());
				
				directory.insertEntry(new Entry (surname, initials, telExtension));
				displayDirectory();
			} 
			
			catch (NumberFormatException e){
				output.setText("Telephone Extension Field is either Empty or Not A Number!");
				refresh();
			}
			
			catch (InvalidTelephoneExtensionException e) {
				output.setText( e.getMessage() );
				refresh();
			}
			
		});

		//Action Listener - Delete Entry by Name
		deleteByNameButton.addActionListener( (ae) -> {
			
			try {
				String surname = surnameField.getText();
				
				directory.deleteEntry(surname);
				displayDirectory();
			} 

			catch (ElementNotFoundException e) {
				output.setText( e.getMessage() );
				refresh();
			}
		});


		//Action Listener - Delete Entry by Number
		deleteByNumButton.addActionListener( (ae) -> {	
			try {
				String telExtension = surnameField.getText();

				directory.deleteEntry(telExtension);
				displayDirectory();
			} 

			catch (ElementNotFoundException e) {
				output.setText( e.getMessage() );
				refresh();
			}
		});

		//Action Listener - Look Up Extension Number
		lookUpButton.addActionListener( (ae) -> {
				
			try {
				String surname = surnameField.getText();
				
				output.setText( Integer.toString( directory.lookUpEntry(surname) ) );
				//refresh();
			} 

			catch (ElementNotFoundException e) {
				output.setText( e.getMessage() );
				refresh();
			}
		});

		//Action Listener - Change Extension Number
		changeTelExtButton.addActionListener( (ae) -> {
			
			try {
				String surname = surnameField.getText();
				int telExtension = Integer.parseInt(telExtensionField.getText());

				directory.changeTelExtension(surname, telExtension);
				displayDirectory();
			} 

			catch (NumberFormatException e){
				output.setText("Telephone Extension Field is either Empty or Not A Number!");
				refresh();
			}

			catch (ElementNotFoundException | InvalidTelephoneExtensionException e) {
				output.setText( e.getMessage() );
				refresh();
			}
		});
		
		//Action Listener - Display Directory
		displayDirectoryButton.addActionListener( (ae) -> displayDirectory() );

		buttons.add(insertEntryButton);
		buttons.add(deleteByNameButton);
		buttons.add(deleteByNumButton);
		buttons.add(lookUpButton);
		buttons.add(changeTelExtButton);
		buttons.add(displayDirectoryButton);
		
		return buttons;
	}

	/**
	 * Creates a scrollable text area that displays the contents of the directory, user requested 
	 * output and error messages.
	 * 
	 * @return the output text area
	 */ 
	private JScrollPane getOutPutPanel() {
		
		output = new JTextArea();
		final JScrollPane scrollPane = new JScrollPane(output);
		
		output.setEditable(false);
		
		scrollPane.setPreferredSize(new Dimension(50, 400));
        scrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        
        return scrollPane;
	}
	
	
	/**
	 * Displays the contents of the directory in the JTextArea
	 */
	private void displayDirectory() {
		output.setText( directory.toString() );
	}
	
	/**
	 * Refreshes the JTextArea with the current contents of the of the directory after a set
	 * amount of time.
	 */
	private void refresh() {
		final int delayTime = 2500;	
		
		Timer timer = new Timer (delayTime, (ae) -> displayDirectory() );
		
		timer.setRepeats(false);
		timer.start();
	}
}
