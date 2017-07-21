package voronoi;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import rendering.Loader;
import main.java.be.humphreys.simplevoronoi.GraphEdge;
import main.java.be.humphreys.simplevoronoi.Voronoi;

public class VoronoiGenerator {

	private static double[] latValues;
	private static double[] lngValues;

	public static Site[] init(Site[] sites, Loader loader, BufferedImage image, int number_of_sites){

		Random r = new Random();

		latValues = new double[number_of_sites];
		lngValues = new double[number_of_sites];

		for(int i = 0; i < number_of_sites; i++){
			latValues[i] = r.nextDouble() * Main.WIDTH;
			lngValues[i] = r.nextDouble() * Main.HEIGHT;
			sites[i] = new Site();
			sites[i].setX((float)latValues[i]);
			sites[i].setY((float)lngValues[i]);
		}

		Voronoi v = new Voronoi(0);
		List<GraphEdge> allEdges = v.generateVoronoi(latValues, lngValues, 0, Main.WIDTH, 0, Main.HEIGHT);
		return generateSites(allEdges, sites, loader, image);
	}

	private static Site[] generateSites(List<GraphEdge> allEdges, Site[] sites,
			Loader loader, BufferedImage image){

		for(int i = 0; i < allEdges.size(); i++){

			int site1 = allEdges.get(i).site1;
			int site2 = allEdges.get(i).site2;

			sites[site1].edges.add(allEdges.get(i));
			sites[site2].edges.add(allEdges.get(i));

			sites[site1].edgeIDs.add(i);
			sites[site2].edgeIDs.add(i);

			sites[site1].neighbors.add(sites[site2]);
			sites[site2].neighbors.add(sites[site1]);
		}

		for(Site site : sites){
			site.setColor(new Color(image.getRGB((int)(image.getWidth() * (site.getX()/Main.WIDTH)), (int)(image.getHeight() * (1 - site.getY()/Main.HEIGHT)))));
		}

		for(int i = 0; i < sites.length; i++){

			List<Vector2f> corners = generateCornersInOrder(sites[i]);
			sites[i].setCorners(corners);
			sites[i].setModel(loader, corners);
		}

		return sites;
	}

	private static List<Vector2f> generateCornersInOrder(Site site){

		List<Vector2f> corners = new ArrayList<Vector2f>();

		int not_allowed = 0;
		float nextX;

		corners.add(new Vector2f());
		corners.add(new Vector2f());
		corners.get(0).x = (float) site.edges.get(0).x1;
		corners.get(0).y = (float) site.edges.get(0).y1;
		corners.get(1).x = (float) site.edges.get(0).x2; nextX = corners.get(1).x;
		corners.get(1).y = (float) site.edges.get(0).y2;

		int iteration = 0;

		while(corners.size() < site.edges.size()){

			if(iteration > 100){
				break;
			}

			iteration ++;

			for(int i = 1; i < site.edges.size(); i++){

				if((float)site.edges.get(i).x1 == nextX && i != not_allowed){
					not_allowed = i;
					Vector2f vec = new Vector2f();
					vec.x = (float) site.edges.get(i).x2; nextX = vec.x;
					vec.y = (float) site.edges.get(i).y2;
					corners.add(vec);
				}
				else if ((float)site.edges.get(i).x2 == nextX && i != not_allowed){
					not_allowed = i;
					Vector2f vec = new Vector2f();
					vec.x = (float) site.edges.get(i).x1; nextX = vec.x;
					vec.y = (float) site.edges.get(i).y1;
					corners.add(vec);
				}
			}
		}

		return corners;
	}

	@SuppressWarnings("unused")
	private static void LloydRelaxation(Site[] sites){

		Vector2f[] centroids = new Vector2f[sites.length];

		int j = 0;
		for(Site site : sites){
			centroids[j] = centroid(site);
			j++;
		}

		for(int i = 0; i < sites.length; i++){

			sites[i].setX(centroids[i].x);
			sites[i].setY(centroids[i].y);
			latValues[i] = centroids[i].x;
			lngValues[i] = centroids[i].y;
		}
	}

	public static Vector2f centroid (Site site){

		float cx = 0;
		float cy = 0;

		for(Vector2f corner : site.getCorners()){
			cx += corner.x;
			cy += corner.y;
		}
		cx /= site.getCorners().size();
		cy /= site.getCorners().size();

		return new Vector2f(cx, cy);
	}
}
