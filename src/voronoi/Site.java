package voronoi;


import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import main.java.be.humphreys.simplevoronoi.GraphEdge;

public class Site {

	private float x, y;
	public List<GraphEdge> edges = new ArrayList<GraphEdge>();
	private List<Float[]> corners = new ArrayList<Float[]>();
	private Color color;

	public List<Float[]> getCorners() {
		return corners;
	}
	
	public void setCorners(List<Float[]> corners) {
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
