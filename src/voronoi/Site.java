package voronoi;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import rendering.Loader;
import rendering.RawModel;
import main.java.be.humphreys.simplevoronoi.GraphEdge;

public class Site {

	private float x, y;
	public List<Site> neighbors = new ArrayList<Site>();
	public List<GraphEdge> edges = new ArrayList<GraphEdge>();
	public List<Integer> edgeIDs = new ArrayList<Integer>();
	private List<Vector2f> corners = new ArrayList<Vector2f>();
	private RawModel model;
	private Color color;

	public RawModel getModel() {
		return model;
	}
	public void setModel(RawModel model) {
		this.model = model;
	}
	public List<Vector2f> getCorners() {
		return corners;
	}

	public void setModel(Loader loader, List<Vector2f> vertices){
		float[] positions = new float[vertices.size()*2];
		for(int i = 0; i < vertices.size(); i++){
			positions[2*i] = vertices.get(i).x / Main.WIDTH * 2 - 1;
			positions[2*i+1] = vertices.get(i).y / Main.HEIGHT * 2 - 1;
		}
		int[] indices = new int[(vertices.size()-2)*3];

		for(int j = 0; j < indices.length/3; j++){

			indices[3*j] = 0;
			indices[3*j+1] = j+1;
			indices[3*j+2] = j+2;
		}

		model = loader.loadToVAO(positions,indices, 2);

		int[] newIndices = new int[positions.length/2];

		for(int j = 0; j < newIndices.length; j++){

			newIndices[j] = j;
		}

		/**
		 * May be useful later. Do not delete.
		 */
		//lineModel = loader.loadToVAO(positions, newIndices, 2);
	}

	public void setCorners(List<Vector2f> corners) {
		this.corners = corners;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public List<Site> getNeighbors() {
		return neighbors;
	}
	public void setNeighbors(List<Site> neighbors) {
		this.neighbors = neighbors;
	}
	public List<GraphEdge> getEdges() {
		return edges;
	}
	public void setEdges(List<GraphEdge> edges) {
		this.edges = edges;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
