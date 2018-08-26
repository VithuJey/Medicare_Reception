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
public class Booking {
    
    private StringProperty referNo;
    private StringProperty appNo;
    private StringProperty appDate;
    private StringProperty appTime;
    private StringProperty payAmount;
    private StringProperty payStatus;
    private StringProperty arrStatus;
    private StringProperty docName;
    private StringProperty patName;
    
    //SELECT b.referNo,b.appNo,b.appDate,b.appTime,b.payAmount,b.payStatus,b.arriveStatus,e.name,p.name FROM employee e, booking b, patient p where e.empId=b.docId AND p.patID=b.patID
    
     public Booking() {
            
        this.referNo = new SimpleStringProperty("");
        this.appNo = new SimpleStringProperty("");
        this.appDate = new SimpleStringProperty("");
        this.appTime = new SimpleStringProperty("");
        this.payAmount = new SimpleStringProperty("");
        this.payStatus = new SimpleStringProperty("");
        this.arrStatus = new SimpleStringProperty("");
        this.docName = new SimpleStringProperty("");
        this.patName = new SimpleStringProperty("");
    }
    
    
    public Booking(String refer, String docNa, String patNa,String appNo, String appDate, String appTime, String payAmo, String paySta, String arrSta) {
            
        this.referNo = new SimpleStringProperty(refer);
        this.appNo = new SimpleStringProperty(appNo);
        this.appDate = new SimpleStringProperty(appDate);
        this.appTime = new SimpleStringProperty(appTime);
        this.payAmount = new SimpleStringProperty(payAmo);
        this.payStatus = new SimpleStringProperty(paySta);
        this.arrStatus = new SimpleStringProperty(arrSta);
        this.docName = new SimpleStringProperty(docNa);
        this.patName = new SimpleStringProperty(patNa);
    }
    
     public String getreferNo() {
        return referNo.get();
    }
    public String getappNo() {
        return appNo.get();
    }
    public String getappDate() {
        return appDate.get();
    }
    public String getappTime() {
        return appTime.get();
    }
    public String getpayAmount() {
        return payAmount.get();
    }
    public String getpayStatus() {
        return payStatus.get();
    }
    public String getarrStatus() {
        return arrStatus.get();
    }
    public String getName() {
        return docName.get();
    }
    public String getPName() {
        return patName.get();
    }
    
    
    public void setreferNo(String value)
    {
    referNo.set(value);
    }
    public void setappNo(String value)
    {
    appNo.set(value);
    }
    public void setappDate(String value)
    {
    appDate.set(value);
    }
    public void setappTime(String value)
    {
    appTime.set(value);
    }
    public void setpayAmount(String value)
    {
    payAmount.set(value);
    }
    public void setEmail(String value)
    {
    payStatus.set(value);
    }
    public void setarrStatus(String value)
    {
    arrStatus.set(value);
    }
    public void setdocName(String value)
    {
    docName.set(value);
    }
    public void setpatName(String value)
    {
    patName.set(value);
    }
    
    public StringProperty referNoProperty(){return referNo;}
    public StringProperty appNoProperty(){return appNo;}
    public StringProperty appDateProperty(){return appDate;}
    public StringProperty appTimeProperty(){return appTime;}
    public StringProperty payAmountProperty(){return payAmount;}
    public StringProperty payStatusProperty(){return payStatus;}
    public StringProperty arrStatusProperty(){return arrStatus;}
    public StringProperty NameProperty(){return docName;}
    public StringProperty PNameProperty(){return patName;}
    
    
}
