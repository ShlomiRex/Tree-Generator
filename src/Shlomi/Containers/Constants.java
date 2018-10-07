package Shlomi.Containers;

import java.awt.Cursor;

public interface Constants {
	enum TOOL {
		END_POINT, LINE, NEW_POINT, NONE, REMOVE_POINT;
	}
	public final Cursor cursorDefault = new Cursor(Cursor.DEFAULT_CURSOR);
	public final Cursor cursorHand = new Cursor(Cursor.HAND_CURSOR);
	public final Cursor cursorNearPoint = new Cursor(Cursor.MOVE_CURSOR);

	public final Cursor cursorPoint = new Cursor(Cursor.CROSSHAIR_CURSOR);
}
