package Shlomi.Initalization;

import java.awt.Color;

public interface Settings {
	
	public int CURSOR_RANGE_RADIUS = 30;
	
	public Color ENDPOINT_FILL_COLOR = Color.LIGHT_GRAY;
	public Color ENDPOINT_OUTLINE_COLOR = Color.DARK_GRAY;
	public int ENDPOINT_RADIUS = 20;
	
	public String images_directory = "/Images/";
	
	public float LINE_STROKE = 4;

	public int EDITORFRAME_HEIGHT = 600;
	public int EDITORFRAME_WIDTH = 600;
	
	public int EDITORFRAME_STARTX = 400;
	public int EDITORFRAME_STARTY = 400;
	
	public Color NORMALPOINT_FILL_COLOR = Color.CYAN;
	public Color NORMALPOINT_OUTLINE_COLOR = Color.BLUE;
	public int NORMALPOINT_RADIUS = 12;

	public String save_directory = "Saves/";
	
	public int GRAPHICS_CONTAINER_WIDTH = 400;
	public int GRAPHICS_CONTAINER_HEIGHT = 400;

	public Color STARTPOINT_FILL_COLOR = Color.pink;
	public Color STARTPOINT_OUTLINE_COLOR = Color.RED;
	public int STARTPOINT_RADIUS = 20;
	public int STARTPOINT_STARTX = 100;
	public int STARTPOINT_STARTY = 100;

	public int TABLE_BUTTON_HEIGHT = 32;
	public int TABLE_BUTTON_WIDTH = 32;

}
