/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reception;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author PC-1
 */
public class NewScene {
                                                                //load() method throws IOException
     public void changeScene(String newFxml, ActionEvent event) throws IOException{ //Pass the new scenes fxml file, and the action event object of the button which will change scenes
         //Get fxml info of new scene (scene to change to)
         Parent root = FXMLLoader.load(getClass().getResource(newFxml));
         //Initialize scene
         Scene scene = new Scene(root);
         //Get stage info
         Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
         
         //Set the scene
         window.setScene(scene);
         //Display the scene
         window.show();
     }
    
}
