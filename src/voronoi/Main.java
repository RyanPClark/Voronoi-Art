package voronoi;

import java.awt.image.BufferedImage;

import org.lwjgl.opengl.Display;

import rendering.Camera;
import rendering.DisplayManager;
import rendering.Loader;
import rendering.Renderer;
import toolbox.Input;

public class Main {

	public static final int WIDTH = 1440;
	public static final int HEIGHT = 720;
	public static boolean generatedAtFull;
	public static boolean showBorders = true;
	private static Site[] sites;
	private static Loader loader = new Loader();
	private static Renderer renderer;
	private static Camera camera;
	private static boolean contextOpen = false;

	public static void renderImage (BufferedImage img, int numPolygons){

		if(contextOpen){
			cleanUp();
		}

		DisplayManager.createDisplay();
		contextOpen = true;

		renderer = new Renderer(loader);
		camera = new Camera();

		sites = new Site[numPolygons];
		sites = VoronoiGenerator.init(sites, loader, img, numPolygons);

		while (!Display.isCloseRequested()){

			Input.update();
			camera.update();
			renderer.prepare(camera);
			renderer.render(camera, sites);
			DisplayManager.updateDisplay();
		}

		cleanUp();

	}

	private static void cleanUp(){
		renderer.cleanUp();
		loader.cleanUp();
		Display.destroy();
		contextOpen = false;
	}

}
