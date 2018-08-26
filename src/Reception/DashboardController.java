/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reception;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Vithu
 */
public class DashboardController implements Initializable {

    @FXML
    private TableView<Booking> bookTable;
    @FXML
    private TableColumn<Booking, String> referCol;
    @FXML
    private TableColumn<Booking, String> docCol;
    @FXML
    private TableColumn<Booking, String> patCol;
    @FXML
    private TableColumn<Booking, String> payCol;
    @FXML
    private TableColumn<Booking, String> appNo;
    private ObservableList<Booking>data;
    @FXML
    private TableColumn<?, ?> arrCol;
    
    @FXML
    private Label docLab;
    @FXML
    private Label nurLab;
    @FXML
    private Label staffLab;
    @FXML
    private Label pharmLab;
    @FXML
    private Label labLab;
    @FXML
    private Label manageLab;
    
    
    @FXML
    private JFXButton add;
    @FXML
    private AnchorPane memoRoot;
    @FXML
    private JFXDatePicker dateP;
    @FXML
    private JFXTextField text;
    @FXML
    private ListView<LocalEvent> eventList;
    
    ObservableList<LocalEvent> list ;
    
    
    DBControl dbc = new DBControl();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            this.loadDataFromDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        employeeOverview();
        loadMemo();
        
        try {
            loadDataFromDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    Stage stage;
    
    //SELECT b.referNo,b.appNo,b.appDate,b.appTime,b.payAmount,b.payStatus,b.arriveStatus,e.name,p.name FROM employee e, booking b, patient p where e.empId=b.docId AND p.patID=b.patID
    
     
        
        String date = LocalDate.now().toString();
        
    
    
    @FXML
    public void loadDataFromDatabase() throws SQLException {
        
     
        data=FXCollections.observableArrayList();
        
        
        
         try{
             
            System.out.println(date);
            //date="2018-04-25";
             
            //Execute query and store results
            ResultSet rs = dbc.selectBookingTableByDate(date);
          
          
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
        payCol.setCellValueFactory(new PropertyValueFactory<>("payStatus"));
        arrCol.setCellValueFactory(new PropertyValueFactory<>("arrStatus"));
        
        
        bookTable.setItems(null);
        bookTable.setItems(data);
          
    }
    
    
    private void employeeOverview() {
        
        String empID;
        int dr = 0, ns = 0, pc = 0, ac = 0, ad = 0, lt = 0, rc = 0, sp = 0;
        
        try{
            
            
            ResultSet rs2 = dbc.findAttendance(date);
            
            while(rs2.next()) {
                
                empID = rs2.getString("empId");
                
                if(empID.contains("DR"))
                {
                    dr++;
                }
                else if(empID.contains("NS"))
                {
                    ns++;
                }
                else if(empID.contains("PC"))
                {
                    pc++;
                }
                else if(empID.contains("AC"))
                {
                    ac++;
                }
                else if(empID.contains("AD"))
                {
                    ad++;
                }
                else if(empID.contains("LT"))
                {
                    lt++;
                }
                else if(empID.contains("RC"))
                {
                    rc++;
                }
                else if(empID.contains("SP"))
                {
                    sp++;
                }
            }
            
            docLab.setText(dr+"");
            nurLab.setText(ns+"");
            pharmLab.setText(pc+"");
            staffLab.setText(rc+"");
            labLab.setText(lt+"");
            manageLab.setText(ad+ac+sp+"");
            
        }
        catch(Exception exe) {
            
            
        }
    }
    
    
    @FXML
    private void handleAddNote(ActionEvent e) {
   
        
       if(text.getText() != null && dateP.getValue() != null ) {
           
//        list.add(new LocalEvent(date.getValue(),text.getText()));
//        eventList.setItems(list);
       
        dbc.insertMemo(Date.valueOf(dateP.getValue()), text.getText());
       
       }
       
       
       loadMemo();
       refresh();
    }
    
    
    private void refresh() {
        
        dateP.setValue(null);
        text.setText(null);
    }
    
     public void loadMemo() {
        
        list = FXCollections.observableArrayList();
        
        LocalDate b = LocalDate.now();
        
         
         try{
            
             //Execute query and store results
             ResultSet rs = dbc.selectMemoTable(b);
          
          
              while(rs.next()) {
                list.add(new LocalEvent(rs.getDate(2).toLocalDate(), rs.getString(3).toString()));
                }
              
              eventList.setItems(list);
    
        }
         
        catch(SQLException e){
            System.err.println("SQLException!");
            e.printStackTrace();
        }
     }
     
    
    
}
