package voronoi;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;
import main.java.be.humphreys.simplevoronoi.GraphEdge;
import main.java.be.humphreys.simplevoronoi.Voronoi;

public class VoronoiGenerator {

	private static double[] latValues;
	private static double[] lngValues;

	public static Site[] init(Site[] sites, BufferedImage image, int number_of_sites){

		Random r = new Random();

		latValues = new double[number_of_sites];
		lngValues = new double[number_of_sites];

		for(int i = 0; i < number_of_sites; i++){
			latValues[i] = r.nextDouble();
			lngValues[i] = r.nextDouble();
			sites[i] = new Site();
			sites[i].setX((float)latValues[i]);
			sites[i].setY((float)lngValues[i]);
		}

		Voronoi v = new Voronoi(0);
		List<GraphEdge> allEdges = v.generateVoronoi(latValues, lngValues, 0, 1, 0, 1);
		return generateSites(allEdges, sites, image);
	}
	
	private static Site[] generateSites(List<GraphEdge> allEdges, Site[] sites, BufferedImage image){

		for(int i = 0; i < allEdges.size(); i++){

			int site1 = allEdges.get(i).site1;
			int site2 = allEdges.get(i).site2;

			sites[site1].edges.add(allEdges.get(i));
			sites[site2].edges.add(allEdges.get(i));
		}

		for(Site site : sites){
			int rgbInt = image.getRGB((int)(image.getWidth()*site.getX()), (int)(image.getHeight() * site.getY()));
			site.setColor(Color.rgb(rgbInt>>16&255, rgbInt>>8&255, rgbInt&255));
		}

		for(int i = 0; i < sites.length; i++){

			List<Float[]> corners = generateCornersInOrder(sites[i]);
			sites[i].setCorners(corners);
		}

		return sites;
	}

	private static List<Float[]> generateCornersInOrder(Site site){

		List<Float[]> corners = new ArrayList<Float[]>();

		int not_allowed = 0;
		float nextX;

		corners.add(new Float[2]);
		corners.add(new Float[2]);
		corners.get(0)[0] = (float) site.edges.get(0).x1;
		corners.get(0)[1] = (float) site.edges.get(0).y1;
		corners.get(1)[0] = (float) site.edges.get(0).x2; nextX = corners.get(1)[0];
		corners.get(1)[1] = (float) site.edges.get(0).y2;

		int iteration = 0;

		while(corners.size() < site.edges.size() && iteration <= 100){
			
			iteration ++;

			for(int i = 1; i < site.edges.size(); i++){

				if((float)site.edges.get(i).x1 == nextX && i != not_allowed){
					not_allowed = i;
					Float[] vec = new Float[2];
					vec[0] = (float) site.edges.get(i).x2; nextX = vec[0];
					vec[1] = (float) site.edges.get(i).y2;
					corners.add(vec);
				}
				else if ((float)site.edges.get(i).x2 == nextX && i != not_allowed){
					not_allowed = i;
					Float[] vec = new Float[2];
					vec[0] = (float) site.edges.get(i).x1; nextX = vec[0];
					vec[1] = (float) site.edges.get(i).y1;
					corners.add(vec);
				}
			}
		}

		return corners;
	}
}
