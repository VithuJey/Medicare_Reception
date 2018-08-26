/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reception;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.BorderPane;


/**
 *
 * @author Vithu
 */
public class ReceptionController implements Initializable {
    
    @FXML
    private JFXButton btnDash;
    @FXML
    private JFXButton btnBook;
    @FXML
    private JFXButton btnPat;
    @FXML
    private JFXButton btnDoc;
    @FXML
    private JFXButton btnCom;
    @FXML
    private BorderPane borderPane;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadUI("Dashboard");
    }    
    
    @FXML
    private void dashboard(MouseEvent event) {
        loadUI("Dashboard");
    }

    @FXML
    private void book(MouseEvent event) {
        loadUI("BookingView");
    }

    @FXML
    private void patient(MouseEvent event) {
        loadUI("PatientView");
    }

    @FXML
    private void doctor(MouseEvent event) {
        loadUI("DoctorView");
    }

    @FXML
    private void complaint(MouseEvent event) {
        loadUI("Complaint");
    }

    
    /*
    private void patientAdd(int name) {
        
        if(11 == 11)
        {
            System.out.println(name);
            loadUI("patientAdd");
        }
    }
    */
    
    public void loadUI(String ui){
        Parent root = null;
        try {
            System.out.println("success");
            root = FXMLLoader.load(getClass().getResource(ui+".fxml"));
        } catch (IOException ex) {
            System.out.println("GO");
            Logger.getLogger(ReceptionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(ui);
        borderPane.setCenter(root);
    }

  
      @FXML
    private void handleButtonAction(ActionEvent event) {
        
        
    }
    
}

 