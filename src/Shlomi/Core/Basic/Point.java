package Shlomi.Core.Basic;

import java.io.Serializable;

import Shlomi.Initalization.Settings;

public class Point implements Settings, Serializable {
	/**
	 * Status of the point.
	 * @author Shlomi
	 *
	 */
	public enum STATUS {
		END_POINT, NORMAL_POINT, NOT_A_SET_POINT, START_POINT;

		public String getStatusName(STATUS status) {
			switch (status) {
			case START_POINT:
				return "START_POINT";
			case END_POINT:
				return "END_POINT";
			case NORMAL_POINT:
				return "NORMAL_POINT";
			default:
				return "NOT_A_SET_POINT";
			}
		}
	}// STATUS

	public STATUS status;

	public int x, y;

	public Point(int x, int y, STATUS status) {
		this.x = x;
		this.y = y;
		this.status = status;
	}

	public int getRadius() {
		if (status == STATUS.START_POINT)
			return STARTPOINT_RADIUS;

		else if (status == STATUS.NORMAL_POINT)
			return NORMALPOINT_RADIUS;

		else if (status == STATUS.END_POINT)
			return ENDPOINT_RADIUS;

		return 0;
	}

	public void offset(int dx, int dy) {
		this.x += dx;
		this.y += dy;
	}

	@Override
	public String toString() {
		return status.getStatusName(status) + "(" + x + "," + y + ") ";
	}

}
