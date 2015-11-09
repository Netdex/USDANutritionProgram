package parser.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;

public class LoadingWindow extends JFrame {

	private JProgressBar progressBar;

	public LoadingWindow() {
		super("Loading..");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		Container content = this.getContentPane();

		progressBar = new JProgressBar();
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
