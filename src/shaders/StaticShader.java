package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_FILE = "vertex.txt";
	private static final String FRAGMENT_FILE = "fragment.txt";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;

	private int location_mixColor;
	private int location_zoomAmount;

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	protected void bindAttributes(){
		super.bindAttributes(0, "position");
	}

	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_mixColor = super.getUniformLocation("mixColor");
		location_zoomAmount = super.getUniformLocation("zoomAmount");
	}

	public void loadMixColor(Vector3f vector){
		super.loadVector(location_mixColor, vector);
	}

	public void loadZoom(float zoom){
		super.loadFloat(location_zoomAmount, zoom);
	}

	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionMatrix, matrix);
	}

	public void loadViewMatrix(Matrix4f matrix){
		super.loadMatrix(location_viewMatrix, matrix);
	}
}
