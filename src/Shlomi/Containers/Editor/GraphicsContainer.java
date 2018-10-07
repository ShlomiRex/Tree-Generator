package Shlomi.Containers.Editor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;

import javax.swing.JPanel;

import Shlomi.Containers.Constants;
import Shlomi.Core.LoadedSave;
import Shlomi.Core.Set;
import Shlomi.Core.Basic.Point;
import Shlomi.Core.Basic.Point.STATUS;
import Shlomi.Initalization.Settings;

public class GraphicsContainer extends JPanel implements Settings, MouseMotionListener, MouseListener, Serializable, Constants {
	private static final long serialVersionUID = 1L;
	private ColorChooserButton btn_Color;
	// First point selected when line tool clicked on a point
	private Point firstPointOfLine;
	private boolean isDragging;
	private Point pointInRange;
	private Set set;
	private TOOL tool;

	public GraphicsContainer(EditorFrame frame) {
		setPreferredSize(new Dimension(Settings.GRAPHICS_CONTAINER_HEIGHT, Settings.GRAPHICS_CONTAINER_WIDTH));
		
		this.set = new Set(STARTPOINT_STARTX, STARTPOINT_STARTY);
		if(frame != null) {
			this.btn_Color = frame.btn_Color;
			addMouseMotionListener(this);
			addMouseListener(this);
		}
		reset();
	}

	public void clickedEndPoint() {
		System.out.println("Clicked endPoint");
		setCursor(cursorPoint);
		tool = TOOL.END_POINT;
	}

	public void clickedLine() {
		System.out.println("Clicked line");
		setCursor(cursorHand);
		tool = TOOL.LINE;
	}

	public void clickedRemovePoint() {
		tool = TOOL.REMOVE_POINT;
		setCursor(cursorPoint);
	}

	public void clickeNewPoint() {
		System.out.println("Clicked newPoint");
		setCursor(cursorPoint);
		tool = TOOL.NEW_POINT;
	}

	public void drawLoadedSave(LoadedSave loadedSave) {
		System.out.println("Drawing loaded save...");
		this.set = loadedSave.getSave().getSet();
		repaint();
	}

	public Set getSet() {
		return set;
	}

	public void loadSet(Set set) {
		this.set = set;
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (isDragging) {
			int dx = e.getX() - pointInRange.x - pointInRange.getRadius() / 2;
			int dy = e.getY() - pointInRange.y - pointInRange.getRadius() / 2;

			pointInRange.offset(dx, dy);
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point p = set.closestPointInRadius(e.getX(), e.getY());

		if (isDragging == false && p != null && tool == TOOL.NONE) {
			setCursor(cursorNearPoint);
			pointInRange = p;
		}

		else {
			if (tool == TOOL.NONE) {
				setCursor(cursorDefault);
			}

			pointInRange = null;
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Tool = none
		if (pointInRange != null && tool == TOOL.NONE) {
			isDragging = true;
		}

		// Tool = new point
		else if (tool == TOOL.NEW_POINT) {
			set.addNewPoint(e.getX(), e.getY(), STATUS.NORMAL_POINT);
			tool = TOOL.NONE;
			repaint();
		}

		// Toll = end point
		else if (tool == TOOL.END_POINT) {
			set.addNewPoint(e.getX(), e.getY(), STATUS.END_POINT);
			tool = TOOL.NONE;
			repaint();
		}

		// Tool = line
		else if (tool == TOOL.LINE) {
			pointInRange = set.closestPointInRadius(e.getX(), e.getY());
			if (pointInRange == null)
				System.out.println("You must select point to connect to.");
			else {
				// Point is selected, tool is line, wait for another point for
				// click
				if (firstPointOfLine == null) {
					System.out.println("Selected the 1st point of line.");
					firstPointOfLine = pointInRange;
				}
				// Point is selected, tool is line, this is the second point
				else {
					// Check if 2 points are the same
					if (pointInRange == firstPointOfLine) {
						System.out.println("Canno't do line with 2 same points.");
					}
					// Line is ok with every condition, create a new line
					else {
						System.out.println("Selected the 2nd point of line.");
						set.addLine(firstPointOfLine, pointInRange, btn_Color.getSelectedColor());
						resetCursorAndTool();
						repaint();
					}
				}
			}
		}

		else if (tool == TOOL.REMOVE_POINT) {
			pointInRange = set.closestPointInRadius(e.getX(), e.getY());
			if (pointInRange != null) {
				set.removePoint(pointInRange);
				resetCursorAndTool();
				repaint();
			}
		}
	}// mouse pressed

	@Override
	public void mouseReleased(MouseEvent e) {
		isDragging = false;
		pointInRange = null;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw set here
		set.draw(g);
	}

	public void printSet() {
		System.out.println(set);
	}

	/**
	 * Reset the graphics container, points, lines, cursor, other fields.
	 * (basically return to new Set Tree.)
	 */
	public void reset() {
		set = new Set(STARTPOINT_STARTX, STARTPOINT_STARTY);
		tool = TOOL.NONE;
		isDragging = false;
		pointInRange = null;
		firstPointOfLine = null;
		repaint();
	}

	private void resetCursorAndTool() {
		tool = TOOL.NONE;
		pointInRange = null;
		setCursor(cursorDefault);
		isDragging = false;
		firstPointOfLine = null;
	}

	public void undoLine() {
		set.undoLine();
		repaint();
	}
}
