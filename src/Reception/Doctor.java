/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reception;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Vithu
 */
public class Doctor {
    private StringProperty name;
    private StringProperty qualification;
    private StringProperty speciality;
    private StringProperty docfee;
    private StringProperty hosfee;
    private StringProperty phone;
    private StringProperty gender;
    
    
     public Doctor() {
            
        this.name = new SimpleStringProperty("");
        this.qualification = new SimpleStringProperty("");
        this.speciality = new SimpleStringProperty("");
        this.docfee = new SimpleStringProperty("");
        this.phone = new SimpleStringProperty("");
        this.hosfee = new SimpleStringProperty("");
        this.gender = new SimpleStringProperty("");
    }
    
    
    public Doctor(String name, String qualify, String special, String docfee, String hosfee, String phone, String gender) {
            
        this.name = new SimpleStringProperty(name);
        this.qualification = new SimpleStringProperty(qualify);
        this.speciality = new SimpleStringProperty(special);
        this.docfee = new SimpleStringProperty(docfee);
        this.phone = new SimpleStringProperty(phone);
        this.hosfee = new SimpleStringProperty(hosfee);
        this.gender = new SimpleStringProperty(gender);
        
    }
    
     public String getName() {
        return name.get();
    }
    public String getQualification() {
        return qualification.get();
    }
    public String getSpeciality() {
        return speciality.get();
    }
    public String getDocFee() {
        return docfee.get();
    }
    public String getHosFee() {
        return hosfee.get();
    }
    public String getPhone() {
        return phone.get();
    }
    public String getGender() {
        return gender.get();
    }
    
    
    public void setName(String value)
    {
    name.set(value);
    }
    public void setQualify(String value)
    {
    qualification.set(value);
    }
    public void setSpecial(String value)
    {
    speciality.set(value);
    }
    public void setDocFee(String value)
    {
    docfee.set(value);
    }
    public void setHosFee(String value)
    {
    hosfee.set(value);
    }
    public void setPhone(String value)
    {
    phone.set(value);
    }
    public void setGender(String value)
    {
    gender.set(value);
    }
    
    
    public StringProperty nameProperty(){return name;}
    public StringProperty qualifyProperty(){return qualification;}
    public StringProperty specialProperty(){return speciality;}
    public StringProperty docProperty(){return docfee;}
    public StringProperty hosProperty(){return hosfee;}
    public StringProperty phoneProperty(){return phone;}
    public StringProperty genderProperty(){return gender;}
    
}
