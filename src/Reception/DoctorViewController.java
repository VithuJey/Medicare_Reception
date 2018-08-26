/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reception;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Vithu
 */
public class DoctorViewController implements Initializable {

    
   
    
    @FXML
    private TableView<Doctor> tableDoc;
    @FXML
    private TableColumn<Doctor, String> columnName;
    @FXML
    private TableColumn<Doctor, String> columnQualify;
    @FXML
    private TableColumn<Doctor, String> columnSpecial;
    
    private ObservableList<Doctor>data;
     
    @FXML
    private Label labName;
    @FXML
    private Label labQualify;
    @FXML
    private Label labSpecial;
    @FXML
    private Label labDocFee;
    @FXML
    private Label labHosFee;
    @FXML
    private Label labPhone;
    @FXML
    private Label labGender;
    @FXML
    private JFXComboBox search;
    @FXML
    private JFXComboBox category;
    
    String cate;
    @FXML
    private AnchorPane docPane;
    @FXML
    private JFXButton scheduleBtn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.loadDataFromDatabase();
            
            this.viewDocDetails(null);
            
            tableDoc.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {  
                
              viewDocDetails(newValue); 
                
            });
        } catch (SQLException ex) {
            Logger.getLogger(PatientViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        category.getItems().removeAll(category.getItems());
        category.getItems().addAll("Name", "Speciality");
        category.editableProperty().setValue(Boolean.FALSE);
        
        
        search.getItems().removeAll(search.getItems());
        search.editableProperty().setValue(Boolean.TRUE);
        
    }
    
    
         //Connect to DBControl
        DBControl dbc = new DBControl();
    
     @FXML
    private void loadDataFromDatabase() throws SQLException {
        
     
        data=FXCollections.observableArrayList();
        
        
         try{
            //Execute query and store results
            ResultSet rs = dbc.selectDoctor();
          
          
              while(rs.next()) {
                data.add(new Doctor(rs.getString(7), rs.getString(14), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(13), rs.getString(9)));
                }
        }
         
        catch(SQLException e){
            System.err.println("SQLException!");
            e.printStackTrace();
        }
         
              
              
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnQualify.setCellValueFactory(new PropertyValueFactory<>("qualification"));
        columnSpecial.setCellValueFactory(new PropertyValueFactory<>("speciality"));
        
        
        tableDoc.setItems(null);
        tableDoc.setItems(data);
          
    }
    
    
     //Create method for load table button
    public void loadReload() throws SQLException{
       loadDataFromDatabase();
    }
     private void viewDocDetails(Doctor doc){
        
        if(doc != null){
            //Fill the labels with info from Patient object.
            labName.setText(doc.getName());
            labGender.setText(doc.getGender());
            labQualify.setText(doc.getQualification());
            labDocFee.setText(doc.getDocFee());
            labPhone.setText(doc.getPhone());
            labHosFee.setText(doc.getHosFee());
            labSpecial.setText(doc.getSpeciality());
            
            scheduleBtn.setDisable(false);
        }
        
        else{
            labName.setText("");
            labGender.setText("");
            labQualify.setText("");
            labDocFee.setText("");
            labPhone.setText("");
            labHosFee.setText("");
            labSpecial.setText("");
            
            scheduleBtn.setDisable(true);
            
        }
    }
     
     @FXML
     private void listSearchItems() {
         
         try
        {
           search.getItems().removeAll(search.getItems());
           
           cate = category.getValue().toString().toLowerCase();
           System.out.println(cate);
            
           
           ResultSet rs = dbc.searchDoctorNameNSpeciality();
                
           while (rs.next())
           {
              
               
               if(cate.equals("name"))
               {
                    search.getItems().add(rs.getString("name"));
               }
               else if (cate.equals("speciality"))
               {
                   search.getItems().add(rs.getString("speciality"));
               }
           }
             new AutoCompleteComboBoxListener<>(search);
        }  
        catch(Exception e)
        {
            System.out.println(e);
        }
     
     }
     
    @FXML
    private void searchDataFromDatabase() {
        
        try{
        data=FXCollections.observableArrayList();
        
        if(cate.equals("name") && search.getValue() != null && category.getValue() != null)
        {
            
            String sear = search.getValue().toString();
            
            
         try{
           
            //Execute query and store results
            ResultSet rs = dbc.searchDoctorName(sear);
          
          
              while(rs.next()) {
                data.add(new Doctor(rs.getString(7), rs.getString(14), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(13), rs.getString(9)));
                }
        }
         
        catch(SQLException e){
            System.err.println("SQLException!");
            e.printStackTrace();
        }
         
              
              
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnQualify.setCellValueFactory(new PropertyValueFactory<>("qualification"));
        columnSpecial.setCellValueFactory(new PropertyValueFactory<>("speciality"));
        
        
        tableDoc.setItems(null);
        tableDoc.setItems(data);
        }
        
        
        
        else if(cate.equals("speciality") && search.getValue() != null && category.getValue() != null)
        {
            
             
            String sear = search.getValue().toString();
            
            
         try{
            
            //Execute query and store results
            ResultSet rs = dbc.searchDoctorSpeciality(sear);
          
          
              while(rs.next()) {
                data.add(new Doctor(rs.getString(7), rs.getString(14), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(13), rs.getString(9)));
                }
        }
         
        catch(SQLException e){
            System.err.println("SQLException!");
            e.printStackTrace();
        }
         
              
              
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnQualify.setCellValueFactory(new PropertyValueFactory<>("qualification"));
        columnSpecial.setCellValueFactory(new PropertyValueFactory<>("speciality"));
        
        
        tableDoc.setItems(null);
        tableDoc.setItems(data);
        
        }
        }
        
        catch(Exception exe){
            System.out.println(exe);
        }
        
    }
    
    @FXML
    private void viewSchedule() throws IOException {
        
        String docName =  this.labName.getText(); System.out.println(docName);
        
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("DocScheduleView.fxml"));
        /* 
         * if "fx:controller" is not set in fxml
         * fxmlLoader.setController(NewWindowController);
         */
        Scene scene = new Scene(fxmlLoader.load(), 1200, 500);
        Stage stage = new Stage();
        stage.setTitle("View Schedule");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
        
        
        
        DocScheduleViewController ba = fxmlLoader.getController();
        
        ba.docScheduleView(docName);
        ba.setdoctorViewController(this);
        ba.setStage(stage);
    
    }
}

    
    
