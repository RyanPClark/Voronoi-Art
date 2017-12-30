package rewrite;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import voronoi.Site;
import voronoi.VoronoiGenerator;

public class Main {

	private static final float MAX_WIDTH  = 1366;
	private static final float MAX_HEIGHT = 768;
	private static final float MAX_ASPECT = MAX_WIDTH / MAX_HEIGHT;
	
	private static Site[] sites;

	public static void renderImage (BufferedImage img, String fileNameWithoutExtn, int numPolygons){

		float imageWidth  = img.getWidth();
		float imageHeight = img.getHeight();
		float aspectRatio = imageWidth / imageHeight;
		
		float stageWidth = imageWidth;
		float stageHeight = imageHeight;
		
		if (imageWidth > MAX_WIDTH && imageHeight <= MAX_HEIGHT) {
			stageWidth  = MAX_WIDTH;
			stageHeight = stageWidth / aspectRatio;
		}
		if (imageWidth <= MAX_WIDTH && imageHeight > MAX_HEIGHT) {
			stageHeight = MAX_HEIGHT;
			stageWidth = stageHeight * aspectRatio;
		}
		if (imageWidth > MAX_WIDTH && imageHeight > MAX_HEIGHT) {
			if(aspectRatio > MAX_ASPECT) {
				stageWidth  = MAX_WIDTH;
				stageHeight = stageWidth / aspectRatio;
			}
			else {
				stageHeight = MAX_HEIGHT;
				stageWidth = stageHeight * aspectRatio;
			}
		}
		
		final int finalStageWidth  = (int)stageWidth;
		final int finalStageHeight = (int)stageHeight;
		
        Stage stage = new Stage();
        Group root = new Group();
        
        stage.setScene(new Scene(root, stageWidth-20, stageHeight-20));
        stage.show();
        stage.setResizable(false);

        final Canvas canvas = new Canvas(stageWidth, stageHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        stage.setTitle(fileNameWithoutExtn);
        gc.drawImage(SwingFXUtils.toFXImage(img, null), 0, 0, finalStageWidth, finalStageHeight);
        root.getChildren().add(canvas);
		
        sites = new Site[numPolygons];
		
        Thread t = new Thread() {
            @Override
            public void run() {
            	sites = VoronoiGenerator.init(sites, img, numPolygons);
            	for(int i = 0; i < sites.length; i++) {
            		int nPoints = sites[i].getCorners().size();
            		double[] xPoints = new double[nPoints];
            		double[] yPoints = new double[nPoints];
            		for(int j = 0; j < nPoints; j++) {
            			xPoints[j] = sites[i].getCorners().get(j)[0] * finalStageWidth;
            			yPoints[j] = sites[i].getCorners().get(j)[1] * finalStageHeight;
            		}
            		
            		gc.setFill(sites[i].getColor());
                	gc.fillPolygon(xPoints, yPoints, nPoints);
            	}
            }
         };
         t.start();
	}
}
