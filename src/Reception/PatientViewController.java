/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reception;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Stack;
import static java.util.jar.Pack200.Packer.TRUE;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import Reception.PatientUpdateController;


/**
 * FXML Controller class
 *
 * @author Vithu
 */
public class PatientViewController implements Initializable {

    @FXML
    private JFXButton patientAdd;
    
    @FXML
    private TableView<Patient> tableUser;
    @FXML
    private TableColumn<Patient, String> columnName;
    @FXML
    private TableColumn<Patient, String> columnGender;
    @FXML
    private TableColumn<Patient, String> columnPhone;

    private ObservableList<Patient>data;
    
    @FXML
    private Label labName;
    @FXML
    private Label labGender;
    @FXML
    private Label labDOB;
    @FXML
    private Label labAddress;
    @FXML
    private Label labPhone;
    @FXML
    private Label labEmail;

    
    @FXML
    private JFXButton patientEdit;
    @FXML
    private Label labAge;
    @FXML
    private Label labJob;
    @FXML
    private Label labMarital;
    
    private PatientUpdateController puc;
    
    Stage stage;
    
    @FXML
    private JFXComboBox patient;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            this.loadDataFromDatabase();
            
            this.viewPatientDetails(null);
            
            tableUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {  
                
              viewPatientDetails(newValue); 
                
            });
        } catch (SQLException ex) {
            Logger.getLogger(PatientViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        addPatient();
    }

    DBControl dbc = new DBControl();
    
    @FXML
    public void loadDataFromDatabase() throws SQLException {
        
        data=FXCollections.observableArrayList();
        
         try{
            
            //Execute query and store results
            ResultSet rs = dbc.selectPatientTable();
          
          
              while(rs.next()) {
                data.add(new Patient(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)));
                }
        }
         
        catch(SQLException e){
            System.err.println("SQLException!");
            e.printStackTrace();
        }
        
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnGender.setCellValueFactory(new PropertyValueFactory<>("sex"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("age"));
        
        tableUser.setItems(null);
        tableUser.setItems(data);
    }
    
    
     //Create method for load table button
    public void loadReload() throws SQLException{
       loadDataFromDatabase();
       
    }
    
    
    private void viewPatientDetails(Patient pat){
        
        if(pat != null){
            //Fill the labels with info from Patient object.
            labName.setText(pat.getName());
            labGender.setText(pat.getSex());
            labDOB.setText(pat.getDob());
            labAddress.setText(pat.getAddress());
            labPhone.setText(pat.getPhone());
            labEmail.setText(pat.getEmail());
            labAge.setText(pat.getAge());
            labJob.setText(pat.getJob());
            labMarital.setText(pat.getMarital());
         
        }
        
        else{
            labName.setText("");
            labGender.setText("");
            labDOB.setText("");
            labAddress.setText("");
            labPhone.setText("");
            labEmail.setText("");
            labAge.setText("");
            labJob.setText("");
            labMarital.setText("");
        }
    }
    
    
 
    
   
    @FXML
    public void handleADD(ActionEvent event) throws IOException, InterruptedException {
        System.out.println("success");
        
        try {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("PatientAdd.fxml"));
        /* 
         * if "fx:controller" is not set in fxml
         * fxmlLoader.setController(NewWindowController);
         */
        Scene scene = new Scene(fxmlLoader.load(), 600, 800);
        stage = new Stage();
        stage.setTitle("Insert Patient Details");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
        
        
        
        PatientAddController pa = fxmlLoader.getController();
        
        
        pa.setpatientViewController(this);
        pa.setStage(stage);
        
        
        } 
        
        catch (IOException e) {
        Logger logger = Logger.getLogger(getClass().getName());
        logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }
    
    
    
    @FXML
    public void handleUPDATE(ActionEvent event) throws IOException, InterruptedException {
        
        
        Patient selectedPatient = tableUser.getSelectionModel().getSelectedItem(); 
        
         if(selectedPatient != null){
            
            //loader.setController(patientUpdateController);
            try{
                this.showEditDialog(selectedPatient);
            }
            catch(Exception exe){
            System.out.println(exe);
            exe.printStackTrace();
            }
        }
         
         else{
              Alert alert = new Alert(Alert.AlertType.WARNING, "Record to update is not selected" + "!!!", ButtonType.OK);
              alert.showAndWait();
         }
             
    }
    
    
    private void showEditDialog(Patient selectedPatient) throws IOException, SQLException, InterruptedException{
        
        
         try {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("PatientUpdate.fxml"));
        
        
      
        //patientUpdateController pu = fxmlLoader.getController();
        //pu.setPatient(selectedPatient);
        AnchorPane root = fxmlLoader.load();
        
        //patient PatientS = tableUser.getSelectionModel().getSelectedItem();
        //System.out.println(PatientS);
             
        PatientUpdateController pu = fxmlLoader.getController();
        pu.setPatient(selectedPatient);
        
        Scene scene = new Scene(root, 600, 800);
        Stage stage = new Stage();
        stage.setTitle("Update Patient Details");
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
        
        
        
        pu.setpatientViewController(this);
        pu.setStage(stage);
        
        }
        
        
        catch (IOException e) {
        Logger logger = Logger.getLogger(getClass().getName());
        logger.log(Level.SEVERE, "Failed to create new Window.", e);
        e.printStackTrace();
        }
         
         /*
         if(selectedPatient != null){
            
            patientUpdateController pu = new patientUpdateController();
            //patientUpdateController pu = fxmlLoader.
            pu.setPatient(selectedPatient);
           
         }*/
    } 
    
    @FXML
    public boolean handleDelete(ActionEvent event)throws IOException, InterruptedException, SQLException 
    {
        
            Patient selectedPatient = tableUser.getSelectionModel().getSelectedItem(); 
        
            if(selectedPatient != null){
            
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure" + " ?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) 
            {
                
            //loader.setController(patientUpdateController);
            
                String name=selectedPatient.getName();
                System.out.println(name);
                
                boolean bool = dbc.deletePatient(name);
                
                try {
                    this.loadDataFromDatabase();
                } catch (SQLException ex) {
                    Logger.getLogger(PatientViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
              
                return bool;
            }
           
         }
             else{
              Alert alert2 = new Alert(Alert.AlertType.WARNING, "Record to delete is not selected" + "!!!", ButtonType.OK);
              alert2.showAndWait();
            
        }
        return false;
    }
  
    
    public void addPatient() 
    {
        try
        {
            
           patient.getItems().removeAll(patient.getItems());
           patient.editableProperty().setValue(Boolean.TRUE);
            
           
           ResultSet rs = dbc.findAllPatientName();
                
           while (rs.next())
           {
                patient.getItems().add(rs.getString("name"));
           }
             new AutoCompleteComboBoxListener<>(patient);
        }  
        catch(Exception e)
        {
            System.out.println(e);
        }
    }


     @FXML
    public void searchDataFromDatabase() throws SQLException {
        
     
        data=FXCollections.observableArrayList();
        
        if ( patient.getValue() != null)
        {
        
         try{
             
             String patName = patient.getValue().toString();
             
            
            //Execute query and store results
            ResultSet rs = dbc.searchPatientName(patName);
          
          
              while(rs.next()) {
                data.add(new Patient(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)));
                }
    
        }
         
        catch(SQLException e){
            System.err.println("SQLException!");
            e.printStackTrace();
        }
        
              
              
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnGender.setCellValueFactory(new PropertyValueFactory<>("sex"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("age"));
        
        
        tableUser.setItems(null);
        tableUser.setItems(data);
          
     }
    
    }
}