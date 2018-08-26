/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reception;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Vithu
 */
public class PatientUpdateController implements Initializable {

    @FXML
    public JFXTextField name;
    
    @FXML
    private JFXDatePicker dob;
    @FXML
    private JFXTextField address;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXTextField email;
    @FXML
    private Label nameAlert;
    @FXML
    private Label genderAlert;
    @FXML
    private Label dobAlert;
    @FXML
    private Label emailAlert;
    @FXML
    private Label phoneAlert;
    @FXML
    private Label addressAlert;
    @FXML
    private Label emptyAlert;
    
    @FXML
    private JFXComboBox cb;
    public JFXTextField text;

    Patient selectedPatient;
    
    @FXML
    private JFXTextField job;
    @FXML
    private JFXComboBox cb2;
    @FXML
    private JFXButton insert;
    @FXML
    private Label jobAlert;
    @FXML
    private Label maritalAlert;
    /**
     * Initializes the controller class.
     */
    
    private PatientViewController pvc;
    private Stage stage;
    
    DBControl dbc = new DBControl();
    
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         cb.getItems().removeAll(cb.getItems());
         cb.getItems().addAll("Male", "Female", "Intersex");
         
         cb2.getItems().removeAll(cb2.getItems());
         cb2.getItems().addAll("Married", "Unmarried", "Divorced", "Widowed");
        
         cb.editableProperty().setValue(Boolean.FALSE);
         cb2.editableProperty().setValue(Boolean.FALSE);
   
    }    
    
    
    public void setPatient(Patient pati){
         
        //System.out.println(pati.getName());
        this.selectedPatient = pati;
  
       
        
        
        //Set the values in all other fields.
        
        name.setText(selectedPatient.getName());
        address.setText(selectedPatient.getAddress());
        phone.setText(selectedPatient.getPhone());
        email.setText(selectedPatient.getEmail());
        dob.setValue(LocalDate.parse(selectedPatient.getDob()));
        cb.setId(selectedPatient.getSex());
        cb.setValue(selectedPatient.getSex().substring(0, 1).toUpperCase().concat(selectedPatient.getSex().substring(1)));
        cb2.setId(selectedPatient.getMarital());
        cb2.setValue(selectedPatient.getMarital().substring(0, 1).toUpperCase().concat(selectedPatient.getMarital().substring(1)));
        job.setText(selectedPatient.getJob());
        
      
     }
    
    @FXML
    private void handleUpdateButton(ActionEvent event) throws SQLException, IOException, InterruptedException {
     
    
        if(name.getText().isEmpty())
        {
            nameAlert.setText("*Enter a name!!!"); 
        }
        else
        {
            nameAlert.setText("");
        }    
        
        if(cb.getValue()==null)
        {
            genderAlert.setText("*Select a gender!!!"); 
        }
        else
        {
            genderAlert.setText("");
        }  
        
        if(address.getText().isEmpty())
        {
            addressAlert.setText("*Enter an address!!!"); 
        }
        else
        {
            addressAlert.setText("");
        }    
        
        if(dob.getValue()==null)
        {
            dobAlert.setText("*Enter a Date Of Birth!!!"); 
        }
        else
        {
            dobAlert.setText("");
        }   
        
        
        if(cb2.getValue()== null)
        {
            maritalAlert.setText("*Select a marital status!!!"); 
        }
        else
        {
            maritalAlert.setText("");
        }
        
        if(job.getText().isEmpty())
        {
            jobAlert.setText("*Enter an Occupation!!!"); 
        }
        else
        {
            jobAlert.setText("");
        }    
        
        if(isDigit(phone.getText()) && !phone.getText().isEmpty())      
        {
            phoneAlert.setText("");
            isPhone(phone.getText());
        }
        else
        {
            phoneAlert.setText("*Enter a phone number!!!");
        }
        
        if(isEmail(email.getText()) && !email.getText().isEmpty())
        {
            emailAlert.setText(""); 
        }
        else
        {
            emailAlert.setText("*Enter an e-mail!!!");
        }    
        
        if(nameAlert.getText().equals("") && genderAlert.getText().equals("") && dobAlert.getText().equals("") && addressAlert.getText().equals("") && phoneAlert.getText().equals("") && emailAlert.getText().equals("") && jobAlert.getText().equals("") && maritalAlert.getText().equals("") )
        {
            
        
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to update" + " ?", ButtonType.YES, ButtonType.NO);
                 alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) 
            {
                
            
            boolean bool = updateData();
            
            if(bool==false)
            {
                emptyAlert.setText("*DB Violation");
            }
            else 
            {
                
                name.clear();
                address.clear();
                cb.setValue(null);
                dob.setValue(null);
                email.clear();
                phone.clear();
                phone.clear();
                job.clear();
                
                
                pvc.loadReload();
                stage.close();
                
                
                Alert alert2 = new Alert(AlertType.INFORMATION);
                alert2.setTitle("Information Dialog");
                alert2.setHeaderText(null);
                alert2.setContentText("Details Updated!");

                alert2.show();
                 
            }
            }
            else{
                alert.close();
            }
            
        }
    }
    
    public void setpatientViewController(PatientViewController eoc){
        this.pvc = eoc;
    }
    
    public void setStage(Stage Stage){
        this.stage=Stage;
    }
    
    public boolean updateData() throws SQLException
    {   
            dob.getValue().toString();
            int a = LocalDate.parse(dob.getValue().toString()).getYear();
            int b = LocalDate.now().getYear();
            
            
            int age = b-a;
            
            
            Patient pat =new Patient(name.getText().toString(), cb.getValue().toString(), dob.getValue().toString(), address.getText().toString(), phone.getText().toString(), email.getText().toString(), Integer.toString(age)  ,  job.getText().toString(), cb2.getValue().toString());
          
            
            String name=selectedPatient.getName();
            System.out.println(name);
                
                
            // execute the query, and get a java resultset
            ResultSet rs = dbc.selectPatientID(name);
      
            // iterate through the java resultset
            int id = 0;
            while (rs.next())
            {
                id = rs.getInt("patID");
            }
                  
                
            String paID = id + "";
            System.out.println(paID);
                
            boolean bool = dbc.updatePatient(pat.getName(),pat.getSex(),pat.getDob(),pat.getAddress(),pat.getPhone(),pat.getEmail(),pat.getAge(),pat.getJob(),pat.getMarital(),paID);
                
            return bool;
    }
    
    /*
    public void setPatient(String nam, String add, String pho, String emai, String Dob, String sex){
        
        
        System.out.println(add);
       
        //Set the values in all other fields.
        name.setText(nam);
        address.setText(add);
        phone.setText(pho);
        email.setText(emai);
        dob.setValue(LocalDate.parse(Dob));
        cb.setId(sex);
        
     }
   */
    
    public void isPhone(String str)
    {
       if(str.length()==10 || str.length()==7)
           phoneAlert.setText("");
       else
           phoneAlert.setText("Error!!!");
    }
    
    
    
    
    public boolean isDigit(String str1)
    {
        try
        {
            double d = Double.parseDouble(str1);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
       
    
    
    public boolean isEmail(String str1)
    {   
        int num1=0, num2=0, num3=0;
        
        StringTokenizer st1 = new StringTokenizer(str1,"@");
        
        while(st1.hasMoreTokens())
        {   
            num1++;
            st1.nextToken();
        }
        
        StringTokenizer st2 = new StringTokenizer(str1,".");
        
        while(st2.hasMoreTokens())
        {   
            num2++;
            st2.nextToken();
        }
        
        
        
        //System.out.println(num1+"   "+num2);
        
        if(num1 == 2 && num2 == 2)
            return true;
        else
            return false;
    }

}
