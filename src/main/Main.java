package main;

import java.awt.EventQueue;

import gui.LastFMImportGUI;

public class Main {
	
	private static LastFMImportGUI MainGUI;
	
	public static LastFMImportGUI getMainGUI()
	{
		return MainGUI;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI = new LastFMImportGUI();
					MainGUI.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
