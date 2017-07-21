package rendering;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.opengl.ImageIOImageData;

import toolbox.MyPaths;

public class DisplayManager {

	private static final String TITLE = "Voronoi Art";

	private static final int WIDTH = 1080;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 30;

	public static int FPS;

	private static long startTime = System.currentTimeMillis();

	public static void createDisplay(){

	ContextAttribs attribs = new ContextAttribs(3,2)
	//.withForwardCompatible(true); Omitted because it caused a bug with glLineWidth()
	.withProfileCore(true);

		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle(TITLE);
			Display.setResizable(true);

		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		try {
			Display.setIcon(new ByteBuffer[] {
			        new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File(MyPaths.makeTexturePath("misc/gameIcon"))), false, false, null),
			        new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File(MyPaths.makeTexturePath("misc/gameIcon"))), false, false, null)
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void updateDisplay(){

		Display.sync(FPS_CAP);
		Display.update();

		long currentTime = System.currentTimeMillis();
		FPS = (int) (1000f / (float)(currentTime - startTime));
		startTime = currentTime;
	}
}