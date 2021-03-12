package iqarrows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

public class Canvas extends JFrame {
	JButton solveButton = new JButton("Solve");
	JButton resetButton = new JButton("Reset");
	JPanel buttonPanel = new JPanel();
	BoardPanel boardPanel = new BoardPanel();

	public Canvas(String title, int w, int h) {
		super(title);
		setSize(w, h);
		setMinimumSize(new Dimension(500, 350));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		solveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!PanelListener.isShowingSolution()) {
					Board input = boardPanel.getBoard();
					Solver.solve(input);
				}
			}
		});

		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Board emptyBoard = new Board();
				boardPanel.setState(emptyBoard);
				boardPanel.repaint();
				PanelListener.setShowingSolution(false);
			}
		});

		buttonPanel.add(solveButton);
		buttonPanel.add(resetButton);
		add(buttonPanel, BorderLayout.PAGE_END);

		boardPanel.setMaximumSize(new Dimension(1000, 500));
		JPanel wrapper = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D g2 = (Graphics2D) g;

				BoardPanel board = (BoardPanel) getComponent(0);
				int cellSize = board.getComponent(0).getSize().width;
				String coordinates = "ABCDEFGHIONMLKJRQP";

				for (int i=0; i<coordinates.length(); i++) {
					if (i < Board.COLS) {
						int x = cellSize * i + board.getX() + cellSize/2;
						g2.drawString(String.valueOf(coordinates.charAt(i)), x, board.getY());
					} else if (i < Board.COLS + Board.ROWS) {
						int x = board.getX() + board.getWidth();
						int y = cellSize * (i - Board.COLS) + board.getY() + cellSize/2;
						g2.drawString(String.valueOf(coordinates.charAt(i)), x, y);
					} else if (i < Board.COLS * 2 + Board.ROWS) {
						int x = cellSize * (i - Board.ROWS - Board.COLS) + board.getX() + cellSize/2;
						int y = board.getY() + board.getHeight() + 10;
						g2.drawString(String.valueOf(coordinates.charAt(i)), x, y);
					} else {
						int x = board.getX() - 10;
						int y = cellSize * (i - Board.COLS * 2 - Board.ROWS) + board.getY() + cellSize/2;
						g2.drawString(String.valueOf(coordinates.charAt(i)), x, y);
					}
				}
			}
		};
		wrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
		wrapper.add(boardPanel);
		add(wrapper);
	}

	public void displayBoard(Board board) {
		boardPanel.setState(board);
		boardPanel.repaint();
	}

	public void displayError(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Input error", JOptionPane.ERROR_MESSAGE);
	}
}

@SuppressWarnings("serial")
class BoardPanel extends JPanel {
	//the snapshot is used to determine how to draw borders (where not to draw them)
	private CellPanel[][] currentState = new CellPanel[Board.ROWS][Board.COLS];

	class CellPanel extends JPanel {
		private Color color;
		private int orientation;

		public Color getColor() {
			return color;
		}
		public void setColor(Color color) {
			this.color = color;
		}

		public int getOrientation() {
			return orientation;
		}
		public void setOrientation(int orientation) {
			this.orientation = orientation;
		}

		CellPanel(Board.Cell cell) {
			super();
			color = cell.getColor();
			orientation = cell.getOrientation();
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;

			int s = getSize().height;
			g2.setPaint(color);
			g2.fill(new Rectangle2D.Double(0, 0, s, s));

			//draw an arrow if necessary
			AffineTransform originalState = g2.getTransform();
			AffineTransform at = new AffineTransform();
			at.translate(s/2, s/2);
			at.scale(0.8, 0.8);

			switch(orientation){
				case 0: return;
				case 1: at.rotate(Math.toRadians(-180)); break;
				case 2: at.rotate(Math.toRadians( -90)); break;
				case 3: break; //the original is already rotated this way
				case 4: at.rotate(Math.toRadians(  90)); break;
			}
			g2.setPaint(Color.WHITE);
			g2.setStroke(new BasicStroke(s/10/2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

			GeneralPath arrow = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
			int xPoints[] = {-s/4, s/4, s/4, s/2,   0, -s/2, -s/4};
			int yPoints[] = {-s/2,-s/2,   0,   0, s/2,    0,    0};
			arrow.moveTo(xPoints[0], yPoints[0]);
			for (int i = 1; i < xPoints.length; i++) {
				arrow.lineTo(xPoints[i], yPoints[i]);
			}
			arrow.closePath();

			g2.transform(at);
			g2.draw(arrow);
			g2.setTransform(originalState);
		}
	}

	BoardPanel(){
		super();
		setLayout(new GridLayout(Board.ROWS, Board.COLS));

		Board emptyBoard = new Board();

		PanelListener listener = new PanelListener();
		for (int i=0; i<Board.ROWS; i++) {
			for (int j=0; j<Board.COLS; j++) {
				Board.Cell cell = emptyBoard.getState()[i][j];
				CellPanel cellPanel = new CellPanel(cell);
				currentState[i][j] = cellPanel;
				cellPanel.addMouseListener(listener);
				add(cellPanel);
			}
		}
	}

	public void setState(Board board){
		for(int i=0; i<Board.ROWS; i++) {
			for (int j=0; j<Board.COLS; j++) {
				Board.Cell cell = board.getState()[i][j];
				CellPanel cellPanel = currentState[i][j];
				cellPanel.setColor(cell.getColor());
				cellPanel.setOrientation(cell.getOrientation());
			}
		}
	}

	public Board getBoard(){
		return new Board(currentState);
	}

	@Override
	public Dimension getPreferredSize(){
		Dimension d = getParent().getSize();
		Dimension max = getMaximumSize();

		int pad = 100;
		int h = d.height - pad;
		int w = d.width - pad;

		if (w / h  > 6 / 2 / 3) { //no clue why this needs to be 2/3 instead of just 3
			int newWidth = 6 * h / 3;
			int newHeight = h;
			return new Dimension(newWidth, newHeight);
		} else {
			int newHeight = 3 * w / 6;
			int newWidth = w;
			return new Dimension(newWidth, newHeight);
		}
	}

	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;

		Dimension size = getSize();
		int cellSize = getComponent(0).getSize().height; //stands for cell size
		int borderSize = cellSize / 10;

		g2.setPaint(Color.WHITE);
		g2.setStroke(new BasicStroke(borderSize));
		g2.draw(new Rectangle2D.Double(0, 0, size.width, size.height));

		g2.setStroke(new BasicStroke(borderSize/2));
		for(int i=0; i < Board.ROWS; i++){
			for (int j=0; j < Board.COLS; j++) {
				Color curr = currentState[i][j].getColor();

				AffineTransform originalState = g2.getTransform();
				AffineTransform offset = new AffineTransform();
				offset.translate(j*cellSize, i*cellSize);
				g2.transform(offset);

				if(j + 1 < Board.COLS && curr != currentState[i][j+1].getColor())
					g2.draw(new Line2D.Double(cellSize, 0, cellSize, cellSize));
				if(i + 1 < Board.ROWS && curr != currentState[i+1][j].getColor())
					g2.draw(new Line2D.Double(0, cellSize, cellSize, cellSize));
				g2.setTransform(originalState);
			}
		}
	}
}