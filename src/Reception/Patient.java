/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reception;

import java.time.LocalDate;
import static java.time.temporal.TemporalQueries.localDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Vithu
 */
public class Patient {
    
    private StringProperty name;
    private StringProperty sex;
    private StringProperty dob;
    private StringProperty address;
    private StringProperty phone;
    private StringProperty email;
    private StringProperty age;
    private StringProperty job;
    private StringProperty marital;
    
    
     public Patient() {
            
        this.name = new SimpleStringProperty("");
        this.sex = new SimpleStringProperty("");
        this.dob = new SimpleStringProperty("");
        this.address = new SimpleStringProperty("");
        this.phone = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.age = new SimpleStringProperty("");
        this.job = new SimpleStringProperty("");
        this.marital = new SimpleStringProperty("");
    }
    
    
    public Patient(String name, String sex, String dob, String address, String phone, String email, String age ,String job, String marital) {
            
        this.name = new SimpleStringProperty(name);
        this.sex = new SimpleStringProperty(sex);
        this.dob = new SimpleStringProperty(dob);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.age = new SimpleStringProperty(age);
        this.job = new SimpleStringProperty(job);
        this.marital = new SimpleStringProperty(marital);
    }
    
     public String getName() {
        return name.get();
    }
    public String getSex() {
        return sex.get();
    }
    public String getDob() {
        return dob.get();
    }
    public String getAddress() {
        return address.get();
    }
    public String getPhone() {
        return phone.get();
    }
    public String getEmail() {
        return email.get();
    }
    public String getAge() {
        return age.get();
    }
    public String getJob() {
        return job.get();
    }
    public String getMarital() {
        return marital.get();
    }
    
    
    public void setName(String value)
    {
    name.set(value);
    }
    public void setSex(String value)
    {
    sex.set(value);
    }
    public void setDob(String value)
    {
    dob.set(value);
    }
    public void setAddress(String value)
    {
    address.set(value);
    }
    public void setPhone(String value)
    {
    phone.set(value);
    }
    public void setEmail(String value)
    {
    email.set(value);
    }
    public void setAge(String value)
    {
    age.set(value);
    }
    public void setJob(String value)
    {
    job.set(value);
    }
    public void setMarital(String value)
    {
    marital.set(value);
    }
    
    public StringProperty nameProperty(){return name;}
    public StringProperty sexProperty(){return sex;}
    public StringProperty dobProperty(){return dob;}
    public StringProperty addressProperty(){return address;}
    public StringProperty phoneProperty(){return phone;}
    public StringProperty emailProperty(){return email;}
    public StringProperty ageProperty(){return age;}
    public StringProperty jobProperty(){return job;}
    public StringProperty maritalProperty(){return marital;}
    
}
