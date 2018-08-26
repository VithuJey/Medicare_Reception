/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reception;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Vithu
 */
public class ComplaintController implements Initializable {

    @FXML
    private TableView<Complaint> ComTable;
    @FXML
    private TableColumn<Complaint, String> columnComplainee;
    @FXML
    private TableColumn<Complaint, String> columnComplaineeRole;
    @FXML
    private TableColumn<Complaint, String> columnComplainer;
    @FXML
    private TableColumn<Complaint, String> columnComplainerRole;
    @FXML
    private TableColumn<Complaint, Integer> cid;
    @FXML
    private TableColumn<Complaint, Date> date;
    @FXML
    private TableColumn<Complaint, String> complaint;

    private ObservableList<Complaint>data;
    
    
    
    
    
    
    @FXML
    private Label emptyAlert;
    
    private JFXTextField crro;
    @FXML
    private JFXTextArea com;
    private JFXTextField cero;
    @FXML
    private JFXTextField crna;
    @FXML
    private JFXTextField cena;
    @FXML
    private JFXComboBox cb1;
    @FXML
    private JFXComboBox cb2;

    /**
     * Initializes the controller class.
     */
    
     //Connect to db
            DBConnector db = new DBConnector();
            Connection conn;
        
    DBControl dbc = new DBControl();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cb1.getItems().removeAll(cb1.getItems());
        cb1.getItems().addAll("Patient","Accountant", "Admin", "Doctor", "Lab Technician", "Nurse", "Pharmacist", "Receptionist", "Supplier", "Other");    
        
        cb2.getItems().removeAll(cb1.getItems());
        cb2.getItems().addAll("Patient","Accountant", "Admin", "Doctor", "Lab Technician", "Nurse", "Pharmacist", "Receptionist", "Supplier", "Other"); 
    
        try {
            this.loadDataFromDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(ComplaintController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
 
    
    @FXML
    private void handleInsertButton(ActionEvent event) throws FileNotFoundException, SQLException{
        
        
        
         emptyAlert.setText("");
    
            if(!cena.getText().isEmpty() && !crna.getText().isEmpty() && !com.getText().isEmpty() && cb1.getValue() != null && cb2.getValue() != null)
            {   
                Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want insert the complaint" + " ?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) 
        {
                
                boolean bool = insertData();
            
                if(bool==false)
                {
                    emptyAlert.setText("*DB Violation");
                }
                
                else 
                 {
                    cena.clear();
                    crna.clear();
                    cb1.setValue(null);
                    cb2.setValue(null);
                    com.clear();
                 }
             }
         else if (alert.getResult() == ButtonType.NO)
        {
            alert.close();
        }
          }   
            else 
            {
                emptyAlert.setText("Fill All Fields");
            }
         
    }
    
     public boolean insertData() throws SQLException
    {   
        
            Complaint ct =new Complaint(cena.getText().toString(), cb1.getValue().toString(), crna.getText().toString(), cb2.getValue().toString(), com.getText().toString(), 5,"".toString());
             
            
            boolean bool = dbc.insertComplaint(ct.getcomplainee(),ct.getcomplaineeRole(),ct.getcomplaint(),ct.getcomplainer(),ct.getcomplainerRole());
          
            return bool;
        }
     
     @FXML
    public void loadDataFromDatabase() throws SQLException {
        
     
        data=FXCollections.observableArrayList();
        
        
         try{
            conn = db.getDBConnection();
            
           
            
            //Execute query and store results
            ResultSet rs = dbc.selectComplaintTable();
          
          
              while(rs.next()) {
                data.add(new Complaint(rs.getString(1),rs.getString(2),rs.getString(4),rs.getString(5),rs.getString(6),rs.getInt(3),rs.getDate(7).toString()));
                }
    
        }
         
        catch(SQLException e){
            System.err.println("SQLException!");
            e.printStackTrace();
        }
        
              
              
        cid.setCellValueFactory(new PropertyValueFactory<>("cid"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnComplainer.setCellValueFactory(new PropertyValueFactory<>("complainerRole"));
        columnComplainerRole.setCellValueFactory(new PropertyValueFactory<>("complaint"));
        columnComplainee.setCellValueFactory(new PropertyValueFactory<>("complainee"));
        columnComplaineeRole.setCellValueFactory(new PropertyValueFactory<>("complaineeRole"));
        complaint.setCellValueFactory(new PropertyValueFactory<>("complainer"));
        
        ComTable.setItems(null);
        ComTable.setItems(data);
          
    }
    
}
  
