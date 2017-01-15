package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FileDialog;
import java.awt.Frame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;

import de.umass.util.StringUtilities;
import main.ScrobblerThread;
import main.Main;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.JPasswordField;

public class LastFMImportGUI {

	private JFrame frmLastfmImport;
	private File fSelectedFile;
	private JTextField txtLastfmUsername;
	private JTextField txtSelectFile;
	private JLabel lblSendScrobbles;
	private JCheckBox chckbxSendscrobbles;
	private JTextPane txtpnOutput;
	private JLabel lblOutput;
	private JButton btnStart;
	private JScrollPane scrollPane;
	private JPasswordField pfLastfmPassword;


	/**
	 * Create the application.
	 */
	public LastFMImportGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLastfmImport = new JFrame();
		frmLastfmImport.setTitle("Last.FM Import");
		frmLastfmImport.setBounds(100, 100, 1000, 581);
		frmLastfmImport.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{145, 354, 0};
		gridBagLayout.rowHeights = new int[]{50, 25, 0, 35, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		frmLastfmImport.getContentPane().setLayout(gridBagLayout);
		
		JButton btnSelectFile = new JButton("Select File");
		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				//If the user successfully selected a file, then set the reference
				if(fileChooser.showOpenDialog(frmLastfmImport) == JFileChooser.APPROVE_OPTION)
				{
					fSelectedFile = fileChooser.getSelectedFile();
					txtSelectFile.setText(getSelectedFile().getAbsolutePath());
				}
			}
		});
		GridBagConstraints gbc_btnSelectFile = new GridBagConstraints();
		gbc_btnSelectFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelectFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSelectFile.gridx = 0;
		gbc_btnSelectFile.gridy = 0;
		frmLastfmImport.getContentPane().add(btnSelectFile, gbc_btnSelectFile);
		
		txtSelectFile = new JTextField();
		txtSelectFile.setEditable(false);
		GridBagConstraints gbc_txtSelectFile = new GridBagConstraints();
		gbc_txtSelectFile.insets = new Insets(0, 0, 5, 0);
		gbc_txtSelectFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSelectFile.gridx = 1;
		gbc_txtSelectFile.gridy = 0;
		frmLastfmImport.getContentPane().add(txtSelectFile, gbc_txtSelectFile);
		txtSelectFile.setColumns(10);
		
		JLabel lblLastfmUsername = new JLabel("Last.FM Username");
		GridBagConstraints gbc_lblLastfmUsername = new GridBagConstraints();
		gbc_lblLastfmUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastfmUsername.anchor = GridBagConstraints.WEST;
		gbc_lblLastfmUsername.gridx = 0;
		gbc_lblLastfmUsername.gridy = 1;
		frmLastfmImport.getContentPane().add(lblLastfmUsername, gbc_lblLastfmUsername);
		
		txtLastfmUsername = new JTextField();
		GridBagConstraints gbc_txtLastfmUsername = new GridBagConstraints();
		gbc_txtLastfmUsername.insets = new Insets(0, 0, 5, 0);
		gbc_txtLastfmUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLastfmUsername.gridx = 1;
		gbc_txtLastfmUsername.gridy = 1;
		frmLastfmImport.getContentPane().add(txtLastfmUsername, gbc_txtLastfmUsername);
		txtLastfmUsername.setColumns(10);
		
		JLabel lblLastfmPassword = new JLabel("Last.FM Password");
		GridBagConstraints gbc_lblLastfmPassword = new GridBagConstraints();
		gbc_lblLastfmPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastfmPassword.anchor = GridBagConstraints.EAST;
		gbc_lblLastfmPassword.gridx = 0;
		gbc_lblLastfmPassword.gridy = 2;
		frmLastfmImport.getContentPane().add(lblLastfmPassword, gbc_lblLastfmPassword);
		
		pfLastfmPassword = new JPasswordField();
		GridBagConstraints gbc_pfLastfmPassword = new GridBagConstraints();
		gbc_pfLastfmPassword.insets = new Insets(0, 0, 5, 0);
		gbc_pfLastfmPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_pfLastfmPassword.gridx = 1;
		gbc_pfLastfmPassword.gridy = 2;
		frmLastfmImport.getContentPane().add(pfLastfmPassword, gbc_pfLastfmPassword);
		
		lblSendScrobbles = new JLabel("Send Scrobbles");
		lblSendScrobbles.setToolTipText("Tells whether to actually scrobble to Last.FM while reading the file.  It's wise to go through the file once first with this off in order to ensure that everything looks good.");
		GridBagConstraints gbc_lblSendScrobbles = new GridBagConstraints();
		gbc_lblSendScrobbles.anchor = GridBagConstraints.WEST;
		gbc_lblSendScrobbles.insets = new Insets(0, 0, 5, 5);
		gbc_lblSendScrobbles.gridx = 0;
		gbc_lblSendScrobbles.gridy = 3;
		frmLastfmImport.getContentPane().add(lblSendScrobbles, gbc_lblSendScrobbles);
		
		chckbxSendscrobbles = new JCheckBox("");
		GridBagConstraints gbc_chckbxSendscrobbles = new GridBagConstraints();
		gbc_chckbxSendscrobbles.anchor = GridBagConstraints.WEST;
		gbc_chckbxSendscrobbles.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxSendscrobbles.gridx = 1;
		gbc_chckbxSendscrobbles.gridy = 3;
		frmLastfmImport.getContentPane().add(chckbxSendscrobbles, gbc_chckbxSendscrobbles);
		
		lblOutput = new JLabel("Output");
		GridBagConstraints gbc_lblOutput = new GridBagConstraints();
		gbc_lblOutput.anchor = GridBagConstraints.NORTH;
		gbc_lblOutput.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutput.gridx = 0;
		gbc_lblOutput.gridy = 4;
		frmLastfmImport.getContentPane().add(lblOutput, gbc_lblOutput);
		
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		frmLastfmImport.getContentPane().add(scrollPane, gbc_scrollPane);
		
		txtpnOutput = new JTextPane();
		scrollPane.setViewportView(txtpnOutput);
		txtpnOutput.setEditable(false);
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.gridwidth = 2;
		gbc_btnStart.gridx = 0;
		gbc_btnStart.gridy = 5;
		
		
		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//If the user provided valid input, then start the main scrobbler thread
				if(validateInput())
				{
					ScrobblerThread scrobbler = new ScrobblerThread(Main.getMainGUI(), fSelectedFile, getUsername(), getPassword(), getSendScrobbles());
					Thread scrobbleThread = new Thread(scrobbler);
					scrobbleThread.start();
				}
			}
		});
		
		frmLastfmImport.getContentPane().add(btnStart, gbc_btnStart);
		
		//Set the text panel to automatically scroll as text is appended
		DefaultCaret caret = (DefaultCaret)txtpnOutput.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}
	
	private File getSelectedFile()
	{
		return fSelectedFile;
	}
	
	private String getUsername()
	{
		return txtLastfmUsername.getText();
	}
	
	private String getPassword()
	{
		return new String(pfLastfmPassword.getPassword());
	}
	
	private boolean getSendScrobbles()
	{
		return chckbxSendscrobbles.isSelected();
	}
	
	//Append a line to the end of the text pane and scroll accordingly
	public void appendLine(String appendText) throws BadLocationException
	{
		Document doc = txtpnOutput.getStyledDocument();
		JScrollBar vertical = scrollPane.getVerticalScrollBar();
		
		//Append the provided string to the end of the text pane
		doc.insertString(doc.getLength(), String.format("%s\r\n", appendText), null);
	}
	
	private boolean validateInput()
	{
		//TODO Validate the file itself
		
		if(fSelectedFile == null){
			JOptionPane.showMessageDialog(frmLastfmImport, "You must select a file.");
			return false;
		}
		
		if(this.getSendScrobbles() && this.getUsername().isEmpty()){
			JOptionPane.showMessageDialog(frmLastfmImport, "When specifying to send scrobbles, you must enter a username.");
			return false;
		}
		
		if(this.getSendScrobbles() && this.getPassword().isEmpty()){
			JOptionPane.showMessageDialog(frmLastfmImport, "When specifying to send scrobbles, you must enter a password.");
			return false;
		}
		
		return true;
	}
	
	public void setVisible(boolean visible)
	{
		frmLastfmImport.setVisible(true);
	}

}
