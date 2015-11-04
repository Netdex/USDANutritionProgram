package parser.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BTreeVisualizer<E extends Comparable<E>> extends JFrame {

	private static final int WINDOW_WIDTH = 1024;
	private static final int WINDOW_HEIGHT = 768;

	private BinaryTree<E> bt;
	protected LinkedList<Operation<E>> OPS = new LinkedList<>();

	public BTreeVisualizer(BinaryTree<E> bt) {
		super("Binary Tree Visualizer");
		// this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new BTreePanel<E>(bt));
		this.bt = bt;
	}

	public void addOperation(Operation<E> op) {
		op.setTree(bt);
		OPS.add(op);
	}

	static class Operation<A extends Comparable<A>> {
		private BinaryTree<A> bt;
		private String debug;

		public Operation(String debug) {
			this.debug = debug;
		}

		public void exec() {

		}

		protected void setTree(BinaryTree<A> tree) {
			this.bt = tree;
		}

		public BinaryTree<A> getTree() {
			return bt;
		}

		@Override
		public String toString() {
			return debug;
		}
	}

	class BTreePanel<E extends Comparable<E>> extends JPanel {

		private int DELTA_X = 20;
		private final int DELTA_Y = 2400;

		private static final double SINE_DELTA = 0.001;
		private double sineValue = 0;

		private long lastTick = System.currentTimeMillis();
		private double offsetX = 0;
		private double offsetY = 0;
		private Point lastDrag = null;

		private double SCALE_FACTOR = 100;
		private static final double ZOOM_DELTA = 1.5;
		private double ZOOM = 0.125;
		private static final double BASE_FONT_SIZE = 1200.0;
		private static final double FONT_DECREMENT_COEFFICIENT = 2;
		private static final double HEIGHT_DECREMENT_COEFFICIENT = 2;
		private static final double RENDER_STOP_POINT = 25.0;

		private boolean DRAFT_MODE = false;
		private BinaryTree<E> bt;

		public BTreePanel(BinaryTree<E> bt) {
			this.setBackground(Color.WHITE);
			this.setFocusable(true);
			this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

			this.bt = bt;
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						try {
							sineValue += SINE_DELTA;
							long currentTime = System.currentTimeMillis();
							long deltaTime = currentTime - lastTick;
							lastTick = currentTime;
							Thread.sleep(10 - deltaTime);
							repaint();
						} catch (Exception e) {

						}
					}

				}
			}).start();
			this.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent event) {
					int keycode = event.getKeyCode();
					switch (keycode) {
					case KeyEvent.VK_SPACE:
						if (!OPS.isEmpty()) {
							OPS.remove().exec();
						}
						repaint();
						break;
					case KeyEvent.VK_A:
						DRAFT_MODE = !DRAFT_MODE;
						repaint();
						break;
					case KeyEvent.VK_UP:
						SCALE_FACTOR++;
						repaint();
						break;
					case KeyEvent.VK_DOWN:
						SCALE_FACTOR--;
						repaint();
						break;
					}
				}
			});

			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent event) {
					Point p = event.getPoint();
					lastDrag = p;
				}

				@Override
				public void mouseReleased(MouseEvent event) {
					lastDrag = null;
				}
			});

			this.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent event) {
					Point p = event.getPoint();
					if (lastDrag != null) {
						offsetX -= (double) (p.x - lastDrag.x) / ZOOM;
						offsetY -= (double) (p.y - lastDrag.y) / ZOOM;
					}
					lastDrag = p;
					repaint();
				}

			});

			this.addMouseWheelListener(new MouseWheelListener() {
				@Override
				public void mouseWheelMoved(MouseWheelEvent event) {
					int notches = event.getWheelRotation();
					if (notches < 0) {
						ZOOM *= ZOOM_DELTA;
					} else if (notches > 0) {
						ZOOM /= ZOOM_DELTA;
					}
					repaint();
				}
			});
		}

		public void render(Graphics2D g) {
			if (DRAFT_MODE) {
				g.setRenderingHint(RenderingHints.KEY_RENDERING,
						RenderingHints.VALUE_RENDER_SPEED);
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_OFF);
				g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
						RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
				g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
						RenderingHints.VALUE_COLOR_RENDER_SPEED);
				g.setRenderingHint(RenderingHints.KEY_DITHERING,
						RenderingHints.VALUE_DITHER_DISABLE);
			} else {
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
			}

			String[] debug = new String[] {
					"TREE SIZE: " + bt.size(),
					OPS.isEmpty() ? "NO OPS REMAINING" : "NEXT OP: "
							+ OPS.peek().toString(),
					String.format("DELTA_X: %d; DELTA_Y: %d", DELTA_X, DELTA_Y),
					"SINE_VALUE: " + sineValue,
					"SCALE_FACTOR: " + SCALE_FACTOR,
					"DRAFT_MODE: " + DRAFT_MODE };
			for (int i = 0; i < debug.length; i++) {
				g.drawString(debug[i], 10, 10 + i * 15);
			}
			g.translate(this.getWidth() / 2, this.getHeight() / 2);
			g.scale(ZOOM, ZOOM);
			g.translate(-this.getWidth() / 2, -this.getHeight() / 2);

			g.translate((int) (-offsetX), (int) (-offsetY));

			drawTreeBinary(g, bt.getRootNode(),
					(int) (-this.getWidth() * SCALE_FACTOR),
					(int) (this.getWidth() * SCALE_FACTOR), 0, 1);
		}

		final Font TYPE_FACE = new Font("Calibri", Font.PLAIN, 10);

		private void drawTreeBinary(Graphics2D g,
				BinaryTree.BinaryTreeNode<E> node, double minX, double maxX,
				double y, int depth) {
			if (node == null)
				return;
			if (Math.abs(maxX - minX) < RENDER_STOP_POINT / ZOOM)
				return;
			double x = (minX + maxX) / 2;

			// Math.abs(Math.sin(sineValue) / 5 + 1)
			Font f = TYPE_FACE.deriveFont((float) (1 * Math.max(
					BASE_FONT_SIZE * SCALE_FACTOR
							/ Math.pow(FONT_DECREMENT_COEFFICIENT, depth), 0)));

			g.setFont(f);

			String desc = node.toString();
			g.drawString(desc,
					(int) (x - g.getFontMetrics().stringWidth(desc) / 2),
					(int) (y + g.getFontMetrics().getAscent() / 2));

			g.setColor(Color.LIGHT_GRAY);
			g.setStroke(new BasicStroke((float) (BASE_FONT_SIZE / Math.pow(2,
					depth))));
			// g.drawLine((int)x, 0, (int)x, (int)(y));
			g.setColor(Color.BLACK);
			if (node.getLeft() != null) {
				double newMinX = minX;
				double newMaxX = maxX - (maxX - minX) / 2.0;
				double newX = (newMinX + newMaxX) / 2.0;
				double newY = y + 1.0 * (DELTA_Y * SCALE_FACTOR)
						/ Math.pow(HEIGHT_DECREMENT_COEFFICIENT, depth);

				g.setColor(new Color(255, 200, 200));
				g.setStroke(new BasicStroke((float) (BASE_FONT_SIZE * 2 / Math
						.pow(2, depth))));
				g.drawLine((int) x, (int) y, (int) newX, (int) newY);
				g.setColor(new Color(120, 40, 40));

				drawTreeBinary(g, node.getLeft(), newMinX, newMaxX, newY,
						depth + 1);
			}
			if (node.getRight() != null) {
				double newMinX = minX + (maxX - minX) / 2.0;
				double newMaxX = maxX;
				double newX = (newMinX + newMaxX) / 2.0;
				double newY = y + 1.0 * (DELTA_Y * SCALE_FACTOR)
						/ Math.pow(HEIGHT_DECREMENT_COEFFICIENT, depth);

				g.setColor(new Color(200, 200, 255));
				g.setStroke(new BasicStroke((float) (BASE_FONT_SIZE * 2 / Math
						.pow(2, depth))));
				g.drawLine((int) x, (int) y, (int) newX, (int) newY);
				g.setColor(new Color(40, 40, 120));

				drawTreeBinary(g, node.getRight(), newMinX, newMaxX, newY,
						depth + 1);
			}
		}

		@Override
		public void paintComponent(Graphics gr) {
			super.paintComponent(gr);

			Graphics2D g = (Graphics2D) gr;
			render(g);
		}
	}
}
