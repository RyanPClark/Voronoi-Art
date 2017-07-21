package toolbox;

import org.lwjgl.input.Mouse;

public class Input {

	public static boolean mouseDown;
	private static boolean mouseClicked;

	public static void update(){

		if(mouseDown && !Mouse.isButtonDown(0) && !mouseClicked){
			mouseClicked = true;
		}
		else {
			mouseClicked = false;
		}

		if(Mouse.isButtonDown(0)){
			mouseDown = true;
		}
		else {
			mouseDown = false;
		}
	}
}
