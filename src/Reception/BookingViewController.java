/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reception;


import com.itextpdf.text.DocumentException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.FileNotFoundException;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

import com.itextpdf.text.*;

import java.io.IOException;


/**
 * FXML Controller class
 *
 * @author Vithu
 */
public class BookingViewController implements Initializable {

    @FXML
    private JFXButton bookAdd;
    @FXML
    private JFXButton bookEdit;
    @FXML
    private TableView<Booking> bookTable;
    @FXML
    private TableColumn<Booking, String> referCol;
    @FXML
    private TableColumn<Booking, String> docCol;
    @FXML
    private TableColumn<Booking, String> patCol;
    @FXML
    private ObservableList<Booking>data;
    @FXML
    private TableColumn<?, ?> appNo;
    @FXML
    private Label labName;
    @FXML
    private Label labGender;
    @FXML
    private Label labDOB;
    @FXML
    private Label labAddress;
    @FXML
    private Label labAge;
    @FXML
    private Label labJob;
    @FXML
    private Label labMarital;
    @FXML
    private Label labPhone;
    @FXML
    private Label labEmail;
    @FXML
    private JFXComboBox search;
    @FXML
    private JFXComboBox category;
    @FXML
    private JFXButton print;
    
    String cate;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.loadDataFromDatabase();
            
            this.viewBookingDetails(null);
            
            bookTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {  
                
              viewBookingDetails(newValue); 
                
            });
        } catch (SQLException ex) {
            Logger.getLogger(PatientViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        category.getItems().removeAll(category.getItems());
        category.getItems().addAll("Doctor Name", "Appointment Date", "Reference Number");
        category.editableProperty().setValue(Boolean.FALSE);
        
        
        search.getItems().removeAll(search.getItems());
        search.editableProperty().setValue(Boolean.TRUE);
        
    }    
    
    Stage stage;
    
    //SELECT b.referNo,b.appNo,b.appDate,b.appTime,b.payAmount,b.payStatus,b.arriveStatus,e.name,p.name FROM employee e, booking b, patient p where e.empId=b.docId AND p.patID=b.patID
    
    
    
         //Connect to db
        DBConnector db = new DBConnector();
        Connection conn;
        
        DBControl dbc = new DBControl();
    
    
    @FXML
    public void loadDataFromDatabase() throws SQLException {
        
     
        data=FXCollections.observableArrayList();
        
         try{
            conn = db.getDBConnection();
            
            
            //Execute query and store results
            ResultSet rs = dbc.selectBookingTable();
          
          
              while(rs.next()) {
                data.add(new Booking(rs.getString(1).toString(),rs.getString(2), rs.getString(3), rs.getString(4).toString(), rs.getString(5), rs.getString(6), rs.getString(7).toString(), rs.getString(8), rs.getString(9)));
                }
    
        }
         
        catch(SQLException e){
            System.err.println("SQLException!");
            e.printStackTrace();
        }
        
              
              
        referCol.setCellValueFactory(new PropertyValueFactory<>("referNo"));
        appNo.setCellValueFactory(new PropertyValueFactory<>("appNo"));
        docCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        patCol.setCellValueFactory(new PropertyValueFactory<>("pName"));
        
        
        bookTable.setItems(null);
        bookTable.setItems(data);
          
    }
    
    
    private void viewBookingDetails(Booking b){
            
        if(b != null){
            //Fill the labels with info from Patient object.
            labName.setText(b.getreferNo());
            labGender.setText(b.getappNo());
            labDOB.setText(b.getName());
            labAddress.setText(b.getpayAmount());
            labPhone.setText(b.getpayStatus());
            labEmail.setText(b.getarrStatus());
            labAge.setText(b.getPName());
            labJob.setText(b.getappDate());
            labMarital.setText(b.getappTime());
         
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
        
        
        try {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("BookingAdd.fxml"));
        /* 
         * if "fx:controller" is not set in fxml
         * fxmlLoader.setController(NewWindowController);
         */
        Scene scene = new Scene(fxmlLoader.load(), 600, 800);
        stage = new Stage();
        stage.setTitle("Add New Appoitment");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
        
        
        
        BookingAddController ba = fxmlLoader.getController();
        
        
        ba.setbookingViewController(this);
        ba.setStage(stage);
        
        
        } 
        
        catch (IOException e) {
        Logger logger = Logger.getLogger(getClass().getName());
        logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }
    
    
    
    @FXML
    public void handleUPDATE(ActionEvent event) throws IOException, InterruptedException {
        
        
        Booking selectedBooking = bookTable.getSelectionModel().getSelectedItem(); 
        
         if(selectedBooking != null){
            
            //loader.setController(patientUpdateController);
            try{
                this.showEditDialog(selectedBooking);
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
    
    
    private void showEditDialog(Booking selectedBooking) throws IOException, SQLException, InterruptedException{
        
        
         try {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("BookingUpdate.fxml"));
        
        
      
        //patientUpdateController pu = fxmlLoader.getController();
        //pu.setPatient(selectedPatient);
        AnchorPane root = fxmlLoader.load();
        
        //patient PatientS = tableUser.getSelectionModel().getSelectedItem();
        //System.out.println(PatientS);
             
        BookingUpdateController bu = fxmlLoader.getController();
        bu.setBooking(selectedBooking);
        
        Scene scene = new Scene(root, 600, 800);
        Stage stage = new Stage();
        stage.setTitle("Update Appointment");
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
        
        
        
        bu.setbookingViewController(this);
        bu.setStage(stage);
        
        }
        
        
        catch (IOException e) {
        Logger logger = Logger.getLogger(getClass().getName());
        logger.log(Level.SEVERE, "Failed to create new Window.", e);
        e.printStackTrace();
        }
    }
 
    
    
    @FXML
    public boolean handleDelete(ActionEvent event)throws IOException, InterruptedException 
    {
        
            Booking selectedBooking = bookTable.getSelectionModel().getSelectedItem(); 
        
            if(selectedBooking != null){
            
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure" + " ?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) 
            {
                
            //loader.setController(patientUpdateController);
            try{
                
                int referNO = Integer.parseInt(selectedBooking.getreferNo());
                System.out.println(referNO);
               
      
                // execute the query, and get a java resultset
                int rs = dbc.deleteBooking(referNO);
            
            this.loadDataFromDatabase();
                
            }
            catch(Exception exe){
                System.out.println(exe);
                exe.printStackTrace();
                }
            }
           
         }
             else{
              Alert alert2 = new Alert(Alert.AlertType.WARNING, "Record to delete is not selected" + "!!!", ButtonType.OK);
              alert2.showAndWait();
            
        }
        return false;
    }

    
    String appDE = "s";
    
    
    @FXML
    private void listSearchItems(MouseEvent event) throws SQLException {
        
         try
        {
           search.getItems().removeAll(search.getItems());
           
           cate = category.getValue().toString();
           
           
           System.out.println(cate);
            
          
           
           
          
              
               if(cate.equals("Doctor Name"))
               {
                    String sql=" SELECT DISTINCT docId from booking ";
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                   
                   
                   while (rs.next())
                    {
                    String empId = rs.getString("docId");
                    String sql2=" SELECT name from doctor d, employee e where d.docId=e.empId AND e.empId='"+empId+"' ";
                    Statement st2 = conn.createStatement();
                    ResultSet rs2 = st2.executeQuery(sql2);
                    
                    while (rs2.next()) 
                    {
                        search.getItems().add(rs2.getString("name"));
                    }
                    
                   new AutoCompleteComboBoxListener<>(search);
                   }
               }
               
               else if (cate.equals("Reference Number"))
               {
                   String sql=" SELECT referNo,appDate,docId from booking ";
                   Statement st = conn.createStatement();
                   ResultSet rs = st.executeQuery(sql);
                   
                   
                   while (rs.next())
                    {
                        search.getItems().add(rs.getString("referNo"));
                    }
                   
                   new AutoCompleteComboBoxListener<>(search);
               }
           
           
              if (cate.equals("Appointment Date"))
              {
               
                String sql5=" SELECT DISTINCT appDate from booking ";
                Statement st5 = conn.createStatement();
                ResultSet rs5 = st5.executeQuery(sql5);
                   
               while (rs5.next())
                {
               
                    search.getItems().add(rs5.getString("appDate"));
                }
               
                   new AutoCompleteComboBoxListener<>(search);
              }
           
             new AutoCompleteComboBoxListener<>(search);
        }  
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    }

    @FXML
    private void searchDataFromDatabase(MouseEvent event) {
    
        try{
        
        if(cate.equals("Doctor Name"))
        {
                   
            cate = search.getValue().toString();
                   
            data=FXCollections.observableArrayList();
        
         try{
            conn = db.getDBConnection();
            
            //Create a statement
            Statement stmt = conn.createStatement();
            
            //SQL Query
            String sql = " SELECT b.referNo,e.name,p.name as pName,b.appNo,b.appDate,b.appTime,b.payAmount,b.payStatus,b.arriveStatus FROM employee e, booking b, patient p where e.empId=b.docId AND p.patID=b.patID AND e.name='"+cate+"' ORDER BY b.referNo DESC ";
            //String sql = "Select";
            
            //Execute query and store results
            ResultSet rs = stmt.executeQuery(sql);
          
          
            while(rs.next()) {
            data.add(new Booking(rs.getString(1).toString(),rs.getString(2), rs.getString(3), rs.getString(4).toString(), rs.getString(5), rs.getString(6), rs.getString(7).toString(), rs.getString(8), rs.getString(9)));
            }
    
        }
         
        catch(SQLException e){
            System.err.println("SQLException!");
            e.printStackTrace();
        }
        
              
              
        referCol.setCellValueFactory(new PropertyValueFactory<>("referNo"));
        appNo.setCellValueFactory(new PropertyValueFactory<>("appNo"));
        docCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        patCol.setCellValueFactory(new PropertyValueFactory<>("pName"));
        
        
        bookTable.setItems(null);
        bookTable.setItems(data);
        
        }
        
        else if (cate.equals("Reference Number"))
        {
                   
                   
            int cate = Integer.parseInt(search.getValue().toString());
                   
            data=FXCollections.observableArrayList();
        
         try{
            conn = db.getDBConnection();
            
            //Create a statement
            Statement stmt = conn.createStatement();
            
            //SQL Query
            String sql = " SELECT b.referNo,e.name,p.name as pName,b.appNo,b.appDate,b.appTime,b.payAmount,b.payStatus,b.arriveStatus FROM employee e, booking b, patient p where e.empId=b.docId AND p.patID=b.patID AND b.referNo='"+cate+"' ORDER BY b.referNo DESC ";
            //String sql = "Select";
            
            //Execute query and store results
            ResultSet rs = stmt.executeQuery(sql);
          
          
              while(rs.next()) {
                data.add(new Booking(rs.getString(1).toString(),rs.getString(2), rs.getString(3), rs.getString(4).toString(), rs.getString(5), rs.getString(6), rs.getString(7).toString(), rs.getString(8), rs.getString(9)));
                }
    
        }
         
        catch(SQLException e){
            System.err.println("SQLException!");
            e.printStackTrace();
        }
        
              
              
        referCol.setCellValueFactory(new PropertyValueFactory<>("referNo"));
        appNo.setCellValueFactory(new PropertyValueFactory<>("appNo"));
        docCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        patCol.setCellValueFactory(new PropertyValueFactory<>("pName"));
        
        
        bookTable.setItems(null);
        bookTable.setItems(data);
                   
        }
        
        else if (cate.equals("Appointment Date"))
        {
                  
             cate = search.getValue().toString();
                   
            data=FXCollections.observableArrayList();
        
         try{
            conn = db.getDBConnection();
            
            //Create a statement
            Statement stmt = conn.createStatement();
            
            //SQL Query
            String sql = " SELECT b.referNo,e.name,p.name as pName,b.appNo,b.appDate,b.appTime,b.payAmount,b.payStatus,b.arriveStatus FROM employee e, booking b, patient p where e.empId=b.docId AND p.patID=b.patID AND b.appDate='"+cate+"' ORDER BY b.referNo DESC ";
            //String sql = "Select";
            
            //Execute query and store results
            ResultSet rs = stmt.executeQuery(sql);
          
          
              while(rs.next()) {
                data.add(new Booking(rs.getString(1).toString(),rs.getString(2), rs.getString(3), rs.getString(4).toString(), rs.getString(5), rs.getString(6), rs.getString(7).toString(), rs.getString(8), rs.getString(9)));
                }
    
        }
         
        catch(SQLException e){
            System.err.println("SQLException!");
            e.printStackTrace();
        }
        
              
              
        referCol.setCellValueFactory(new PropertyValueFactory<>("referNo"));
        appNo.setCellValueFactory(new PropertyValueFactory<>("appNo"));
        docCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        patCol.setCellValueFactory(new PropertyValueFactory<>("pName"));
        
        
        bookTable.setItems(null);
        bookTable.setItems(data);
               }
        }
        catch(Exception exe){
        System.out.println(exe);
        }
        
        }
    
    
    //To Print Invoice
    @FXML
    private void handlePrint() throws FileNotFoundException, DocumentException, InterruptedException, BadElementException, IOException {
    
        //SELECT A ROW WHICH WANT TO BE PRINTED
        Booking selectedBooking = bookTable.getSelectionModel().getSelectedItem();
        
        //instantiate PrintInvoice class to access print method
        PrintInvoice pi = new PrintInvoice();
        pi.print(selectedBooking);
        
    }
    
    
    
     @FXML
    public void rules(ActionEvent event) throws IOException, InterruptedException {
        
        
        try {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("BookingRules.fxml"));
        /* 
         * if "fx:controller" is not set in fxml
         * fxmlLoader.setController(NewWindowController);
         */
        Scene scene = new Scene(fxmlLoader.load(), 600, 439);
        stage = new Stage();
        stage.setTitle("Appointment Booking Rules");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
        }
        
        catch(Exception e) {
            System.out.println(e);
        }
    }

}