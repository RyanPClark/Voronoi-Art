package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import rendering.Camera;

public class GameMath {

	private static final Vector3f axis1 = new Vector3f(1,0,0);
	private static final Vector3f axis2 = new Vector3f(0,1,0);


	public static Matrix4f createViewMatrix(Camera camera){
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float)Math.toRadians(camera.getPitch()), axis1, viewMatrix, viewMatrix);
		Matrix4f.rotate((float)Math.toRadians(camera.getYaw()), axis2, viewMatrix, viewMatrix);
		Vector2f cameraPosition = camera.getPosition();
		Vector2f negativeCamPosition = new Vector2f(-cameraPosition.x, -cameraPosition.y);
		Matrix4f.translate(negativeCamPosition, viewMatrix, viewMatrix);
		return viewMatrix;
	}
}
