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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Vithu
 */
public class BookingAddController implements Initializable {

    String docID = null;
    String date = null;
    int AppNo=0, AvgTime=0, patId=0, remNo=0,count;
    LocalTime startTime, AppTime;
    double avgTime, payAmount, payDoc, payCli;
    
    
    private BookingViewController bvc;
    private Stage stage;
    
    
    
    DBControl dbc = new DBControl();
    
    
    @FXML
    private JFXComboBox doctor;
    @FXML
    private JFXComboBox patient;
    @FXML
    private JFXComboBox appNo;
    @FXML
    private Label appTime;
    @FXML
    private JFXComboBox appDa;
    @FXML
    private JFXComboBox paySta;
    @FXML
    private JFXComboBox arrSta;
    @FXML
    private Label payAmo;
    
    
    
    /**
     * Initializes the controller class./// INSERT INTO `booking`(`referNo`, `appNo`, `appDate`, `appTime`, `payAmount`, `payStatus`, `arriveStatus`, `patID`, `docId`) VALUES ()
     */
    
    @FXML
    private JFXButton book;
    @FXML
    private Label emptyAlert;
    @FXML
    private Label DocNameAlert;
    @FXML
    private Label PatNameAlert;
    @FXML
    private Label AppDateAlert;
    @FXML
    private Label AppNoAlert;
    @FXML
    private Label AppTimeAlert;
    @FXML
    private Label PayAmoAlert;
    @FXML
    private Label PayStaAlert;
    @FXML
    private Label AppStaAlert;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          // addDoctor();
           addPatient();
           addPayStatus();
           addArriveStatus();
           
    }
    
   
    @FXML
    private void handleInsertButton(ActionEvent event) throws SQLException, IOException, InterruptedException {
     
        
        if(appTime.getText().isEmpty())
        {
            AppTimeAlert.setText("Click It!!!"); 
        }
        else
        {
            AppTimeAlert.setText("");
        }    
        
        if(payAmo.getText().isEmpty())
        {
            PayAmoAlert.setText("Click It!!!"); 
        }
        else
        {
            PayAmoAlert.setText("");
        }    
        
        
        if(doctor.getValue()== null)
        {
            DocNameAlert.setText("*Select a Doctor!!!"); 
        }
        else
        {
            DocNameAlert.setText("");
        }  
        
        if(patient.getValue()== null)
        {
            PatNameAlert.setText("*Select a Patient!!!"); 
        }
        else
        {
            PatNameAlert.setText("");
        }
        
        if(appDa.getValue()== null)
        {
            AppDateAlert.setText("*Select a Date!!!"); 
        }
        else
        {
            AppDateAlert.setText("");
        }
        
        if(appNo.getValue()== null)
        {
            AppNoAlert.setText("*Select a Number!!!"); 
        }
        else
        {
            AppNoAlert.setText("");
        }
        
        if(paySta.getValue()== null)
        {
            PayStaAlert.setText("*Select a Status!!!"); 
        }
        else
        {
            PayStaAlert.setText("");
        }
        
        if(arrSta.getValue()== null)
        {
            AppStaAlert.setText("*Select a Status!!!"); 
        }
        else
        {
            AppStaAlert.setText("");
        }
        
        
        
        if(AppStaAlert.getText().equals("") && PayStaAlert.getText().equals("") && AppNoAlert.getText().equals("") && AppDateAlert.getText().equals("") && PatNameAlert.getText().equals("") && DocNameAlert.getText().equals("") && AppTimeAlert.getText().equals("") && PayAmoAlert.getText().equals("")   )
        {
            System.out.println("Hello");
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to book" + " ?", ButtonType.YES, ButtonType.NO);
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
                
                doctor.setValue(null);
                patient.setValue(null);
                appDa.setValue(null);
                appNo.setValue(null);
                paySta.setValue(null);
                arrSta.setValue(null);
                appTime.setText("");
                payAmo.setText("");
                
                
                bvc.loadDataFromDatabase();
                stage.close();
                
                
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Information Dialog");
                alert2.setHeaderText(null);
                alert2.setContentText("Details Inserted!");

                alert2.show();
                
            }
            }
            else{
                alert.close();
            }
        }
    }
    
    
    public void setbookingViewController(BookingViewController eoc){
        this.bvc = eoc;
    }
    
    public void setStage(Stage Stage){
        this.stage=Stage;
    }
    
    


    public boolean insertData() throws SQLException
    {   
            
                String patName = patient.getValue().toString();
                
                ResultSet rs1 = dbc.findPatientId(patName);
                
                // iterate through the java resultset
                patId = 0;
                while (rs1.next())
                {
                    patId = rs1.getInt("patID");
                }
                System.out.println(patId);
                
                boolean bool = dbc.insertBooking(Integer.parseInt(appNo.getValue().toString()), Date.valueOf(appDa.getValue().toString()), AppTime.toString(), payAmount, (String) paySta.getValue(), (String) arrSta.getValue(), patId, docID);
                
                return bool;
    }
    
    
    public void addDoctor() 
    {
           appDa.getItems().removeAll(appDa.getItems());
           appDa.setValue("Choose a number");
           appNo.getItems().removeAll(appNo.getItems());
           appNo.setValue("Choose a number");
           
           
           paySta.getItems().removeAll(paySta.getItems());
          
           arrSta.getItems().removeAll(arrSta.getItems());
           
           
           appTime.setText("Time (24 Hrs Clock)");
           payAmo.setText("DocFee + HosFee = TotFee");
           
           appDa.getItems().removeAll(appDa.getItems());
        
        
        try
        {
           doctor.getItems().removeAll(doctor.getItems());
           doctor.editableProperty().setValue(Boolean.TRUE);
            
           ResultSet rs2 = dbc.findAllDoctorName();
                
           while (rs2.next())
           {
                doctor.getItems().add(rs2.getString("e.name"));
           }
           
           new AutoCompleteComboBoxListener<>(doctor);
        }  
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void addPatient() 
    {
        try
        {
            
           patient.getItems().removeAll(patient.getItems());
           patient.editableProperty().setValue(Boolean.TRUE);
           
           ResultSet rs3 = dbc.findAllPatientName();
                
           while (rs3.next())
           {
                patient.getItems().add(rs3.getString("name"));
           }
             new AutoCompleteComboBoxListener<>(patient);
        }  
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    
    @FXML
    public void addAppDate() 
    {
        
         appNo.getItems().removeAll(appNo.getItems());
           appNo.setValue("Choose a number");
           //AppNoAlert.setText("*Select a Number!!!"); 
           
           paySta.setValue("Choose a status");
           //PayStaAlert.setText("Choose a status");
           arrSta.setValue("Choose a status");
          // AppStaAlert.setText("Choose a status");
           
           appTime.setText("Click me");
           payAmo.setText("Click me");
           
          // AppTimeAlert.setText("*Select a Time!!!");
          // PayAmoAlert.setText("*Select a Amount!!!");
           
            
           appDa.getItems().removeAll(appDa.getItems());
        
        if(doctor.getValue() != null && patient.getValue() != null) {
        
        try
        {
           appDa.getItems().removeAll(appDa.getItems());
           
           String doc = doctor.getValue().toString();
           
           ResultSet rs = dbc.findDocId(doc);
           
           while (rs.next())
           {
                docID = rs.getString("e.empId");
                System.out.println(docID);
           }
           
           
           
           ResultSet rs2 = dbc.findDocScheduleDate(docID);
           
           while (rs2.next())
           {
               appDa.getItems().add(rs2.getDate("s.date").toString());
//               appDate.setValue(java.time.LocalDate.parse(rs2.getDate("s.date").toString()));
           }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }  
        }
        
        else if (patient.getValue() == null || doctor.getValue() == null) {
             Alert alert2 = new Alert(Alert.AlertType.WARNING, "Previous Field Isn't Filled" + "!!!", ButtonType.OK);
             alert2.showAndWait();
        }
    }
    
    
    @FXML
    public void addAppNo() 
    {
        int num = 0, n=0, count=1;
        
        if(doctor.getValue() != null && patient.getValue() != null && appDa.getValue() != null) {
        try
        {
           
           appNo.getItems().removeAll(appNo.getItems());
           appNo.editableProperty().setValue(Boolean.FALSE);
        
           date = appDa.getValue().toString();
           
           ResultSet rs3 = dbc.findDocMaxBookLimit(docID, date);
           while (rs3.next())
           {
                num = rs3.getInt("s.maxBookingLimit");
           }
           
           System.out.println(num);
           System.out.println(n);
           
           while (n != num)
           {
               appNo.getItems().add(++n);
           }
           
           System.out.println("start removing");
           
           
           ResultSet rs4 = dbc.findAppNo(docID, date);
           while (rs4.next())
           {
                remNo = rs4.getInt("b.appNo");
                
                appNo.getItems().remove(remNo-count);
                System.out.println(remNo-count);
                count++;
           }
           
           
           while (n != num)
           {
               appNo.getItems().add(++n);
           }
           
        }
        catch(Exception e)
        {
            System.out.println(e);
        }  
        }
        
        else if (patient.getValue() == null || doctor.getValue() == null || appDa.getValue() == null) {
             Alert alert2 = new Alert(Alert.AlertType.WARNING, "Previous Field Isn't Filled" + "!!!", ButtonType.OK);
             alert2.showAndWait();
        }
    }
    
    @FXML
    public void addAppTime() 
    {
        
        if(doctor.getValue() != null && patient.getValue() != null && appDa.getValue() != null && appNo.getValue() != null) {
            
        try
        {
           
           ResultSet rs3 = dbc.findStartTime(docID, date);
           while (rs3.next())
           {
                startTime = LocalTime.parse(rs3.getTime("s.startTime").toString());
           }   
           System.out.println(startTime);
           
           
           ResultSet rs4 = dbc.findAvgTimePerBook(docID, date);
           while (rs4.next())
           {
                avgTime = rs4.getDouble("s.avgTimePerBooking");
           }
                AvgTime = (int)avgTime;
           
           System.out.println(AvgTime);
           
           AppNo = Integer.parseInt(appNo.getValue().toString());   //selected appNo
           
           
           AppTime = startTime.plusMinutes((AppNo-1)*AvgTime);// Next Appointment Time
           System.out.println(AppTime);
           
           appTime.setText(AppTime.toString());
           
           
        }
        catch(Exception e)
        {
            System.out.println(e);
        }  
        }
        
        else if (patient.getValue() == null || doctor.getValue() == null || appDa.getValue() == null || appNo.getValue() == null) {
             Alert alert2 = new Alert(Alert.AlertType.WARNING, "Previous Field Isn't Filled" + "!!!", ButtonType.OK);
             alert2.showAndWait();
        }
    }
    
    @FXML
    public void addPayAmo() 
    {
        if(doctor.getValue() != null && patient.getValue() != null && appDa.getValue() != null && appNo.getValue() != null && appTime.getText() != "") {
            
        try
        {
           
           ResultSet rs = dbc.findDocFeeAndClinicCharge(docID);
                
           while (rs.next())
           {
               payDoc = Double.parseDouble(rs.getString("d.doctorFee"));
               payCli = Double.parseDouble(rs.getString("d.clinicCharge"));
           }
           
           payAmount = payDoc + payCli;
           payAmo.setText(payDoc + "+" + payCli + " = " + payAmount);
           
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        }
        
        else if (patient.getValue() == null || doctor.getValue() == null || appDa.getValue() == null || appNo.getValue() == null || appTime.getText() == "") {
             Alert alert2 = new Alert(Alert.AlertType.WARNING, "Previous Field Isn't Filled" + "!!!", ButtonType.OK);
             alert2.showAndWait();
        }
    }
    
    @FXML
    public void addPayStatus() 
    {
        
           paySta.getItems().removeAll(paySta.getItems());
           paySta.editableProperty().setValue(Boolean.FALSE);
           paySta.getItems().addAll("PAID", "UNPAID");

    }
    
    @FXML
    public void addArriveStatus() 
    {
        
           arrSta.getItems().removeAll(arrSta.getItems());
           arrSta.editableProperty().setValue(Boolean.FALSE);
           arrSta.getItems().addAll("ARRIVED", "NOT-ARRIVED", "CANCELLED");

    }
}