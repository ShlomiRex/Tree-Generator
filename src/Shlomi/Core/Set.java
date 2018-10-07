package Shlomi.Core;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

import Shlomi.Core.Basic.Line;
import Shlomi.Core.Basic.Point;
import Shlomi.Core.Basic.Point.STATUS;
import Shlomi.Initalization.Settings;

public class Set implements Settings, Serializable {

	/**
	 * All lines in the set.
	 */
	public Stack<Line> lines;

	/**
	 * All points in the set.
	 */
	public ArrayList<Point> points;

	public Set(int xStart, int yStart) {
		points = new ArrayList<Point>();
		Point start = new Point(xStart, yStart, STATUS.START_POINT);
		points.add(start);

		lines = new Stack<Line>();
	}
	
	public Set(Set other) {
		points = new ArrayList<Point>();
		lines = new Stack<Line>();
		
		ArrayList<Point> otherPoints = other.points;
		Stack<Line> otherLines = other.lines;
		
		for(Point p : otherPoints)
			points.add(new Point(p.x,p.y,p.status));
		
		for(Line l : otherLines)
			lines.add(new Line(l.p1,l.p2,l.color));
	}

	/**
	 * Add a line to the set.
	 * @param p1 Point 1 to add line.
	 * @param p2 Point 2 to finish line.
	 * @param lineColor Color of the line.
	 */
	public void addLine(Point p1, Point p2, Color lineColor) {
		Line tmpLine = new Line(p1, p2, lineColor);

		for (Line l : lines)
			if (tmpLine.isSameLine(l)) {
				System.out.println("Line is already connected.\n-Line " + tmpLine + " is same as " + l);
				return;
			}
		System.out.println("Point " + p1 + " has connected to " + p2);
		lines.add(tmpLine);
	}

	/**
	 * Add new point to the set.
	 * @param x
	 * @param y
	 * @param pointStatus STATUS of the point.
	 */
	public void addNewPoint(int x, int y, STATUS pointStatus) {
		Point newPoint = new Point(x, y, pointStatus);
		System.out.println("Added: " + newPoint);
		points.add(newPoint);
	}

	/**
	 * 
	 * @param mouseX
	 * @param mouseY
	 * @return Closest point to cursor by range radius. Null if not point in
	 *         radius.
	 */
	public Point closestPointInRadius(int mouseX, int mouseY) {
		Point cursorPoint = new Point(mouseX, mouseY, STATUS.NOT_A_SET_POINT);
		Point minPointToCursor = null;
		double tmpDist;
		distance(cursorPoint, getStartingPoint());

		for (Point p : points) {
			tmpDist = distance(p, cursorPoint);
			if (tmpDist < CURSOR_RANGE_RADIUS) {
				minPointToCursor = p;
			}
		}

		return minPointToCursor;
	}

	/**
	 * 
	 * @param p1 Point 1.
	 * @param p2 Point 2.
	 * @return Distance between the 2 points.
	 */
	private double distance(Point p1, Point p2) {
		return Math.sqrt(Math.pow((p2.x - p1.x - CURSOR_RANGE_RADIUS / 2), 2)
				+ Math.pow((p2.y - p1.y - CURSOR_RANGE_RADIUS / 2), 2));
	}

	/**
	 * Draw the set.
	 * @param g Graphics
	 */
	public void draw(Graphics g) {
		// Draw points
		for (Point p : points) {
			if (p.status == STATUS.START_POINT) {
				g.setColor(STARTPOINT_FILL_COLOR);
				g.fillOval(p.x, p.y, STARTPOINT_RADIUS, STARTPOINT_RADIUS);
				g.setColor(STARTPOINT_OUTLINE_COLOR);
				g.drawOval(p.x, p.y, STARTPOINT_RADIUS, STARTPOINT_RADIUS);
			}

			else if (p.status == STATUS.NORMAL_POINT) {
				g.setColor(NORMALPOINT_FILL_COLOR);
				g.fillOval(p.x, p.y, NORMALPOINT_RADIUS, NORMALPOINT_RADIUS);
				g.setColor(NORMALPOINT_OUTLINE_COLOR);
				g.drawOval(p.x, p.y, NORMALPOINT_RADIUS, NORMALPOINT_RADIUS);
			}

			else if (p.status == STATUS.END_POINT) {
				g.setColor(ENDPOINT_FILL_COLOR);
				g.fillOval(p.x, p.y, ENDPOINT_RADIUS, ENDPOINT_RADIUS);
				g.setColor(ENDPOINT_OUTLINE_COLOR);
				g.drawOval(p.x, p.y, ENDPOINT_RADIUS, ENDPOINT_RADIUS);
			}

		}
		// Draw lines
		for (Line l : lines) {
			l.draw(g);
		}
	}

	/**
	 * 
	 * @return The starting point.
	 */
	public Point getStartingPoint() {
		//Starting point index in the array is always 0
		return points.get(0);
	}

	/**
	 * Remove point.
	 * @param p Point to remove from the set.
	 */
	public void removePoint(Point p) {
		if (p == getStartingPoint()) {
			System.out.println("Can't remove starting point.");
			return;
		}

		// Next logic removes points and lines
		// Index and line to remove logic is to prevent crash of
		// java.util.concurrentmodificationexception
		// Because you canno't run on loop for each if the object is modified.

		ArrayList<Line> linesToRemove = new ArrayList<Line>();
		// If point is connected in line, remove the line!
		for (Line l : lines)
			if (l.contains(p))
				linesToRemove.add(l);

		for (Line l : linesToRemove)
			lines.remove(l);

		// Remove point
		points.remove(p);
	}
	
	/**
	 * @return String which describes the set.
	 */
	@Override
	public String toString() {
		String result = "";
		result += "~~~~~Printing set..\n";
		result += "-Lines...\n";
		for (Line l : lines)
			result += l + "\n";
		result += "\n-Points...\n";
		for (Point p : points) {
			result += p + "\n";
		}
		result += "\n~~~~~Done!";

		return result;
	}

	/**
	 * Remove last line in queue.
	 */
	public void undoLine() {
		if (lines.size() > 0) {
			System.out.println("Undoing line: " + lines.peek());
			lines.pop();
		}
	}
	
	/**
	 * 
	 * @return All the end points.
	 */
	public ArrayList<Point> getEndPoints() {
		ArrayList<Point> ep = new ArrayList<Point>();
		
		for(Point p : points)
			if(p.status == STATUS.END_POINT)
				ep.add(p);
		
		return ep;
	}

	/**
	 * Offset all points in the set, including lines
	 * @param dx
	 * @param dy
	 */
	public void offset(int dx, int dy) {
		
		//Offset the point in the set.
		for(Point p : points)
			p.offset(dx, dy);
		
		//Offset the point in the line (because the point object is passed by value and NOT by reference)
		ArrayList<Point> offsetedPoints = new ArrayList<Point>();
		//We need to offset only once every point in lines. That means if a point has more than 1 connection, it will only offset once.
		for(Line l : lines) {
			if(offsetedPoints.contains(l.p1) == false) {
				offsetedPoints.add(l.p1);
				l.p1.offset(dx, dy);
			}
			
			if(offsetedPoints.contains(l.p2) == false) {
				offsetedPoints.add(l.p2);
				l.p2.offset(dx, dy);
			}
		}
		

	}

	/**
	 * Position all the set to this x,y location
	 * @param x
	 * @param y
	 */
	public void offsetToThisLocation(int x, int y) {
		offset(-getStartingPoint().x, -getStartingPoint().y);
		offset(x,y);
	}
	
	/**
	 * Check if the Set is connected, which means all points are connected with lines.
	 * @return If all points connected to lines, return true.
	 */
	public boolean isConnected() {
		//VVVVVV Good
		if(lines.size() == 0) 
			return false;
		
		//For each point in the set, check if it is in the array or lines. If not, return false.
		boolean isPointConnected = false;
		//For each point
		for(Point p : points) {
			//Check ALL lines for this point
			for(Line l : lines) {
				if(l.contains(p) == true) { 
					isPointConnected = true;
					break;
				}
			}
			
			if(isPointConnected == false)
				return false;
		}
		
		return true;
		
	}
	
	/**
	 * 
	 * @return New set that is duplicated from this set. (No reference whatsoever.)
	 */
	public Set duplicate() {
		Set set = new Set(getStartingPoint().x , getStartingPoint().y);
		
		ArrayList<Point> otherPoints = new ArrayList<Point>();
		Stack<Line> otherLines = new Stack<Line>();
		
		for(Point p : points)
			otherPoints.add(new Point(p.x,p.y,p.status));
		
		for(Line l : lines)
			otherLines.add(new Line(l.p1,l.p2,l.color));
		
		set.points = otherPoints;
		set.lines = otherLines;
		
		return set;
	}

	
}
