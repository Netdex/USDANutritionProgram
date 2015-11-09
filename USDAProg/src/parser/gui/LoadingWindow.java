package parser.gui;

import gui.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;

public class LoadingWindow extends JFrame {

	private JProgressBar progressBar;
	private final static Color LOADING_BAR_COLOR = new Color(6012382);

	public LoadingWindow() {
		super("Loading..");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.getContentPane().setBackground(GUI.BACKGROUND_COLOUR);
		Container content = this.getContentPane();

		progressBar = new JProgressBar();
		progressBar.setForeground(LOADING_BAR_COLOR);
		progressBar.setBackground(GUI.BACKGROUND_COLOUR);
		progressBar.setStringPainted(true);
		TitledBorder border = BorderFactory.createTitledBorder("Reading...");
		progressBar.setBorder(border);
		content.add(progressBar, BorderLayout.NORTH);
		this.setSize(300, 100);
	}

	public void setPercent(int percent) {
		this.progressBar.setValue(percent);
	}
}
