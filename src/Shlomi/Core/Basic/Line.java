package Shlomi.Core.Basic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

import Shlomi.Initalization.Settings;

public class Line implements Settings, Serializable {
	private static final long serialVersionUID = 1L;

	public Color color;
	private boolean isConnected;
	public Point p1, p2;

	public Line(Point p1, Point p2, Color color) {
		this.p1 = p1;
		this.p2 = p2;
		isConnected = true;
		this.color = color;
	}

	public void connect() {
		isConnected = true;
	}

	public void draw(Graphics g) {
		// Do not draw if not connected.
		if (isConnected == false)
			return;

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.setStroke(new BasicStroke(LINE_STROKE));
		g.drawLine(p1.x + p1.getRadius() / 2, p1.y + p1.getRadius() / 2, p2.x + p2.getRadius() / 2,
				p2.y + p2.getRadius() / 2);
	}

	/**
	 * Compare 2 lines to check if they are the same. 2 Lines are the same, if
	 * their points are the same (p1,p2). Note, p1 and p2 can be switched, so p2
	 * can be p1...ect.
	 * 
	 * @param Line
	 *            to compare
	 * @return True, if same line.
	 */
	public boolean isSameLine(Line l) {
		if ((l.p1 == p1 && l.p2 == p2) || (l.p1 == p2 && l.p2 == p1))
			return true;
		return false;
	}

	public void removeLink() {
		isConnected = false;
	}

	@Override
	public String toString() {
		return "[Line] ,{" + p1 + "," + p2 + "} ";
	}
	
	/**
	 * If this line connected to point, return true. If not, return false.
	 * @param p Point to check.
	 * @return If p is connected to line.
	 */
	public boolean contains(Point p) {
		if(p1 == p || p2 == p)
			return true;
		
		return false;
	}

	/**
	 * Offset the line.
	 * @param dx
	 * @param dy
	 */
	public void offset(int dx, int dy) {
		p1.offset(dx, dy);
		p2.offset(dx, dy);
	}

	/**
	 * Offset the point in the param, if the line contains it.
	 * @param p Point to offset.
	 * @param dx
	 * @param dy
	 */
	public void offsetIfContains(Point p, int dx, int dy) {
		if(p1 == p) {
			System.out.println("Offsetting " + p1 + " to ");
			p1.offset(dx, dy);
			System.out.print(p1);
		}
		else if(p2 == p){
			System.out.println("Offsetting " + p2 + " to ");
			p2.offset(dx, dy);
			System.out.print(p2);
		}
		
		else
			System.out.println("No such point: " + p + " found in line: " + this.toString());
	}
	
}
