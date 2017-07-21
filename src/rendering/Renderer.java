package rendering;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import shaders.StaticShader;
import toolbox.GameMath;
import voronoi.Site;
public class Renderer {

	private StaticShader shader;

	private float zoomAmount;

	public Renderer(Loader loader){
		shader = new StaticShader();

		GL11.glCullFace(GL11.GL_BACK);
	}

	public void prepareSelection(){
		if (Display.wasResized())
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(0, 0, 0, 1);
	}

	public void prepare(Camera camera){

		if (Display.wasResized())
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(0, 0.7f, 1, 1);

		zoomAmount = camera.getZoom();

		Matrix4f viewMatrix = GameMath.createViewMatrix(camera);

		shader.start();
		shader.loadZoom(zoomAmount);
		shader.loadViewMatrix(viewMatrix);
		shader.stop();
	}

	public void render(Camera camera, Site[] sites){

		shader.start();
		for(Site site : sites){
				shader.loadMixColor(new Vector3f(site.getColor().getRed()/255.0f,site.getColor().getGreen()/255.0f,site.getColor().getBlue()/255.0f));
				renderModel(site.getModel());
		}

		shader.stop();
	}

	private static void renderModel(RawModel model){

		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	public void cleanUp(){
		shader.cleanUp();
	}
}
