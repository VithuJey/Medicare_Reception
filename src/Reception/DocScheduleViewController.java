/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reception;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Vithu
 */
public class DocScheduleViewController implements Initializable {

    @FXML
    private TableView<Schedule> dailyScheduleTable;
    @FXML
    private TableColumn<Schedule, Integer> sidColumn;
    @FXML
    private TableColumn<Schedule, Integer> roomNoColumn;
    @FXML
    private TableColumn<Schedule, String> docIdColumn;
    @FXML
    private TableColumn<Schedule, String> doctorNameColumn;
    @FXML
    private TableColumn<Schedule, LocalTime> startTimeColumn;
    @FXML
    private TableColumn<Schedule, LocalTime> endTimeColumn;
    @FXML
    private TableColumn<Schedule, Integer> maxBookingLimitColumn;
     private ObservableList<Schedule> scheduleData = FXCollections.observableArrayList();
    
    private Schedule schedule;

    
    DBControl dbc = new DBControl();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    DoctorViewController pvc = new DoctorViewController();
    public void setdoctorViewController(DoctorViewController eoc){
        this.pvc = eoc;
    }
    
    Stage stage = new Stage();
    public void setStage(Stage Stage){
        this.stage=Stage;
    }
    
    
    public void docScheduleView(String docName) {
        
        
        try {
         
        ResultSet rs = dbc.selectDocSchedule(docName);
                
        while(rs.next()){
                
                //Create new schedule object to add to observable list
                Schedule schedule = new  Schedule(rs.getInt("sid"), rs.getString("docId"), rs.getDate("date"),   rs.getTime("startTime"),
                        rs.getTime("endTime"), rs.getInt("roomNo"), rs.getInt("avgTimePerBooking"), rs.getInt("maxBookingLimit"));
                //Get the values from result set and add it observable list as Doctor objects (use either rs.get..(colName) <=colName in mysql or rs.get..(colIndex)
                scheduleData.add(schedule);
                
            }
            
        }
        catch(SQLException e){
            System.err.println("SQLException: load schedule");
            e.printStackTrace();
        }
        
        //Initialize table columns
        sidColumn.setCellValueFactory(new PropertyValueFactory<>("sid"));
        roomNoColumn.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        docIdColumn.setCellValueFactory(new PropertyValueFactory<>("docId"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        maxBookingLimitColumn.setCellValueFactory(new PropertyValueFactory<>("maxBookingLimit"));
        
        
       
        
        //set the items onto the table
        dailyScheduleTable.setItems(null);
        dailyScheduleTable.setItems(scheduleData);
        
    }
        
    }
    

