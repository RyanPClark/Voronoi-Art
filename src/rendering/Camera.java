package rendering;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import toolbox.Input;

public class Camera {

	private Vector2f position = new Vector2f(0,0);
	private float pitch;
	private float yaw;
	private float roll;
	private float zoom = 1.1f;

	private float lastMX;
	private float lastMY;

	public void update(){

		if(Input.mouseDown){

			float differentialX = - 0.001f/zoom * (Mouse.getX() - lastMX);
			float differentialY = - 0.001f/zoom * (Mouse.getY() - lastMY);

			position.x += differentialX;
			position.y += differentialY;
		}
		zoom();

		lastMX = Mouse.getX();
		lastMY = Mouse.getY();
	}

	private void zoom(){

		float DWheel = Mouse.getDWheel();

		if(DWheel > 0 && zoom < 6){
			zoom *= 1.13f;
		}
		else if (DWheel < 0 && zoom > 0.1f){
			zoom /= 1.13f;
		}
	}

	public Vector2f getPosition() {
		return position;
	}
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	public float getPitch() {
		return pitch;
	}
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	public float getYaw() {
		return yaw;
	}
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	public float getRoll() {
		return roll;
	}
	public void setRoll(float roll) {
		this.roll = roll;
	}

	public float getZoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}


}
