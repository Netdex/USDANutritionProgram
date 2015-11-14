//package gui;
//
//import java.awt.BorderLayout;
//import java.awt.FlowLayout;
//import java.awt.Image;
//import java.io.File;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JTabbedPane;
//
//public class HelpPanel extends JPanel {
//
//	private PanelManager manager;
//
//	public HelpPanel(PanelManager manager) throws IOException {
//		super();
//		this.manager = manager;
//		this.setLayout(new BorderLayout());
//		this.setBackground(GUI.BACKGROUND_COLOUR);
//
//		JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		header.setBackground(GUI.HEADER_COLOUR);
//		header.add(new HomeButton(this.manager));
//
//		JLabel title = new JLabel("HELP");
//		title.setFont(GUI.TITLE_FONT);
//		title.setOpaque(false);
//		header.add(title);
//		this.add(header, BorderLayout.NORTH);
//
//		JTabbedPane contentPanels = new JTabbedPane(JTabbedPane.TOP,
//				JTabbedPane.WRAP_TAB_LAYOUT);
//		contentPanels.setBorder(GUI.EMPTY_BORDER);
//		contentPanels.setBackground(GUI.BACKGROUND_COLOUR);
//		contentPanels.setFocusable(false);
//		contentPanels.setFont(GUI.CONTENT_FONT);
//
//		contentPanels.add(
//				"Welcome",
//				new JLabel(new ImageIcon(ImageIO.read(
//						new File("images/helpPics/welcome.png"))
//						.getScaledInstance(450, 650, Image.SCALE_SMOOTH))));
//		contentPanels.add(
//				"Search (1)",
//				new JLabel(new ImageIcon(ImageIO.read(
//						new File("images/helpPics/search1.png"))
//						.getScaledInstance(450, 650, Image.SCALE_SMOOTH))));
//		contentPanels.add(
//				"Search (2)",
//				new JLabel(new ImageIcon(ImageIO.read(
//						new File("images/helpPics/search2.png"))
//						.getScaledInstance(450, 650, Image.SCALE_SMOOTH))));
//		contentPanels.add(
//				"Groups",
//				new JLabel(new ImageIcon(ImageIO.read(
//						new File("images/helpPics/group.png"))
//						.getScaledInstance(450, 650, Image.SCALE_SMOOTH))));
//		contentPanels.add(
//				"Adding (1)",
//				new JLabel(new ImageIcon(ImageIO.read(
//						new File("images/helpPics/add1.png"))
//						.getScaledInstance(450, 650, Image.SCALE_SMOOTH))));
//		contentPanels.add(
//				"Adding (2)",
//				new JLabel(new ImageIcon(ImageIO.read(
//						new File("images/helpPics/add2.png"))
//						.getScaledInstance(450, 650, Image.SCALE_SMOOTH))));
//
//		// 450 * 650
//		this.add(contentPanels, BorderLayout.CENTER);
//	}
//}
