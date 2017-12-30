package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import rewrite.Main;

public class GUI extends Application {

   @Override
   public void start(Stage primaryStage) {

       GridPane grid = new GridPane();
       grid.setAlignment(Pos.CENTER);
       grid.setHgap(10);
       grid.setVgap(10);
       grid.setPadding(new Insets(25, 25, 25, 25));

       Text scenetitle = new Text("Welcome");
       scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
       grid.add(scenetitle, 0, 0, 2, 1);
       scenetitle.setId("welcome-text");

       Label userName = new Label("Address or URL:");
       grid.add(userName, 0, 1);

       TextField userTextField = new TextField();
       grid.add(userTextField, 1, 1);
       userTextField.setText("http://buyersguide.caranddriver.com/media/assets/submodel/6873.jpg");

       Label pw = new Label("Polygons:");
       grid.add(pw, 0, 2);

       final TextField precisionTextField = new TextField("5000");
       precisionTextField.setVisible(true);
       grid.add(precisionTextField, 1, 2);

       Button btnE = new Button("Stylize");

       HBox hbBtnE = new HBox(10);
       hbBtnE.setAlignment(Pos.BOTTOM_LEFT);
       hbBtnE.getChildren().add(btnE);
       grid.add(hbBtnE, 0, 4);
       GridPane.setColumnSpan(hbBtnE, 10);

       HBox hbBtn = new HBox(10);
       hbBtn.setAlignment(Pos.BOTTOM_LEFT);
       grid.add(hbBtn, 1, 4);

       Button btnCF = new Button("ChooseFile");

       HBox hbBtnCF = new HBox(10);
       hbBtn.getChildren().add(btnCF);
       grid.add(hbBtnCF, 1, 0);
       GridPane.setRowSpan(btnCF, 10);


       final Text actiontarget = new Text();
       actiontarget.setId("actiontarget");
       grid.add(actiontarget, 0, 6);

       final FileChooser fileChooser = new FileChooser();

       btnCF.setOnAction(
               new EventHandler<ActionEvent>() {
                   @Override
                   public void handle(final ActionEvent e) {
                       File file = fileChooser.showOpenDialog(primaryStage);
                       if (file != null) {
                           userTextField.setText(file.getAbsolutePath());
                       }
                   }
               });

       btnE.setOnAction(new EventHandler<ActionEvent>() {

   	    @Override
   	    public void handle(ActionEvent e) {
   	        actiontarget.setFill(Color.FIREBRICK);
   	        int polygons = 5000;
   	        try {
   	        	polygons = Integer.parseInt(precisionTextField.getText());
   	        	if(polygons <= 0){
   	        		actiontarget.setText("Must be positive");
   	        		return;
   	        	}
   	        	else if (polygons > 20000){
   	        		actiontarget.setText("Must be <= 20k");
   	        		return;
   	        	}
   	   	        stylize(userTextField.getText(), actiontarget, polygons);
   	        }
   	        catch (NumberFormatException ex){
   	        	actiontarget.setText("Enter an integer");
   	        }
   	    }
       });

       Scene scene = new Scene(grid, 450, 250);
       scene.getStylesheets().add
       (GUI.class.getResource("gui.css").toExternalForm());

       primaryStage.setTitle("Voronoi Art");
       primaryStage.setResizable(false);
       try {
           primaryStage.getIcons().add(
        		   new Image(
        		      GUI.class.getResourceAsStream( "voronoi-icon.png" )));
       }
       catch(NullPointerException e) {
    	   System.err.println("voronoi-icon.png not found");
       }
       primaryStage.setScene(scene);
       primaryStage.show();
   }


private static void stylize(String fileAddress, Text actionTarget, int numPolygons)
{
	BufferedImage image = null;

	String fileName = fileAddress.substring( fileAddress.lastIndexOf('/')+1, fileAddress.length() );
	String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
	
	try {
		if (fileAddress.startsWith("http")){
			URL url = new URL(fileAddress);
		    image = ImageIO.read(url);
		}
		else {
			image = ImageIO.read(new File(fileAddress));
		}
	} catch (IOException e) {
		e.printStackTrace();
		actionTarget.setText("Error reading file");
		return;
	}

	Main.renderImage(image, fileNameWithoutExtn, numPolygons);
}

public static void main(String[] args) {
       launch(args);
   }
}