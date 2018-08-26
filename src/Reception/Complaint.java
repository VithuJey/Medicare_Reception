/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reception;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Vithu
 */
public class Complaint {
    
    private StringProperty complainee;
    private StringProperty complaineeRole;
    private StringProperty complaint;
    private StringProperty complainer;
    private StringProperty complainerRole;
    private IntegerProperty cid;
    private StringProperty date;
    
    
    
    
    public Complaint(String complainee, String complaineeRole,String complainer, String complainerRole, String complaint, int cid, String date) {
            
        this.complainee = new SimpleStringProperty(complainee);
        this.complaineeRole = new SimpleStringProperty(complaineeRole);
        this.complaint = new SimpleStringProperty(complaint);
        this.complainer = new SimpleStringProperty(complainer);
        this.complainerRole = new SimpleStringProperty(complainerRole);
        this.cid = new SimpleIntegerProperty(cid);
        this.date = new SimpleStringProperty(date);
    }

    public String getdate() {
        return date.get();
    }

    public void setdate(String value) {
        this.date.setValue(value);
    }
    
     public String getcomplainee() {
        return complainee.get();
    }
    public String getcomplaineeRole() {
        return complaineeRole.get();
    }
    public String getcomplainer() {
        return complainer.get();
    }
    public String getcomplainerRole() {
        return complainerRole.get();
    }
    public String getcomplaint() {
        return complaint.get();
    }
    public int getcid() {
        return cid.get();
    }
    
    
    public void setcomplainee(String value)
    {
    complainee.set(value);
    }
    public void setcomplaineeRole(String value)
    {
    complaineeRole.set(value);
    }
    public void setcomplainer(String value)
    {
    complainer.set(value);
    }
    public void setcomplainerRole(String value)
    {
    complainerRole.set(value);
    }
    public void setcomplaint(String value)
    {
    complaint.set(value);
    }
    public void setcid(int value)
    {
    cid.set(value);
    }
    
    public StringProperty complaineeProperty(){return complainee;}
    public StringProperty complaineeRoleProperty(){return complaineeRole;}
    public StringProperty complainerProperty(){return complainer;}
    public StringProperty complainerRoleProperty(){return complainerRole;}
    public StringProperty complaintProperty(){return complaint;}
    public IntegerProperty cidProperty(){return cid;}
    public StringProperty dateProperty(){return date;}
    
}
