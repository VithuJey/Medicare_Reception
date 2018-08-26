/*
Class that models a doctor's schedule.
 */
package Reception;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Schedule {
    
    
    //Attributes for schedule
    private IntegerProperty sid;  //id for each schedule (1 doctor can have schedule set for more than 1 day)
    private StringProperty docId; //empId of doctor who's schedule this is.
    private ObjectProperty<LocalDate> date;  //date of schedule
    private ObjectProperty<LocalTime> startTime; //schedule start time (time doctor comes to work)
    private ObjectProperty<LocalTime> endTime; //schedule end time
    private IntegerProperty roomNo;  //room in which doctor will see patient appointments
    private IntegerProperty averageTimePerBooking;  //in minutes
    private IntegerProperty maxBookingLimit;  //derived from averageTimePerBooking and start and end times.
    
    //Constructorsg
    //Non-parametric: Used when creating new schedules etc.
    public Schedule(){
        this.sid = new SimpleIntegerProperty();
        this.docId = new SimpleStringProperty();
        this.date = new SimpleObjectProperty<LocalDate>();
        this.startTime = new SimpleObjectProperty<LocalTime>();
        this.endTime = new SimpleObjectProperty<LocalTime>();
        this.roomNo = new SimpleIntegerProperty();
        this.averageTimePerBooking = new SimpleIntegerProperty();
        this.maxBookingLimit = new SimpleIntegerProperty();
        
    }
    
    //1st Parameterized Constructor (Used for Doctors tab in DocAndScheduleOverview): Used to retrieve values from db (Note: we use java.sql classes Date , Time to get the values from db and store. Then inside the constructor, we use methods provided in these classes to 
    //convert to LocalDate and LocalTime
    public Schedule(int sid, String docId, Date date, Time startTime, Time endTime, int roomNo, int averageTimePerBooking, int maximumBookingLimit){
    
        
        this.sid = new SimpleIntegerProperty(sid);
        this.docId = new SimpleStringProperty(docId);
        this.date = new SimpleObjectProperty<LocalDate>(date.toLocalDate()); //Note: mysql(SQL) does not a LocalDate /LocalTime type. So we use Date, Time provided by java.sql
        this.startTime = new SimpleObjectProperty<LocalTime>(startTime.toLocalTime()); //Then to conver to LocalDate or LocalTime we use toLocalDate() or toLocalTime().
        this.endTime = new SimpleObjectProperty<LocalTime>(endTime.toLocalTime());                           //Note: To convert from LocalDate/LocalTime to java.sql Date or Time => use Date.valueOf(LocalDate ldobject) or Time.valueOf(LocalTime ltobject) <= needed when inserting to db .
        this.roomNo = new SimpleIntegerProperty(roomNo);
        this.averageTimePerBooking = new SimpleIntegerProperty(averageTimePerBooking);
        this.maxBookingLimit = new SimpleIntegerProperty(maximumBookingLimit);
        
       
    }
    
    //2nd Parameterized constructor(Used for Schedule tab)(When doctor, schedule and employee tables are joined and values retrieved to create a schedule object)
        public Schedule(int sid, String docId, String name, String speciality, Date date, Time startTime, Time endTime, int roomNo, int averageTimePerBooking, int maximumBookingLimit){
    
        
        this.sid = new SimpleIntegerProperty(sid);
        this.docId = new SimpleStringProperty(docId);
        this.date = new SimpleObjectProperty<LocalDate>(date.toLocalDate()); //Note: mysql(SQL) does not a LocalDate /LocalTime type. So we use Date, Time provided by java.sql
        this.startTime = new SimpleObjectProperty<LocalTime>(startTime.toLocalTime()); //Then to conver to LocalDate or LocalTime we use toLocalDate() or toLocalTime().
        this.endTime = new SimpleObjectProperty<LocalTime>(endTime.toLocalTime());                           //Note: To convert from LocalDate/LocalTime to java.sql Date or Time => use Date.valueOf(LocalDate ldobject) or Time.valueOf(LocalTime ltobject) <= needed when inserting to db .
        this.roomNo = new SimpleIntegerProperty(roomNo);
        this.averageTimePerBooking = new SimpleIntegerProperty(averageTimePerBooking);
        this.maxBookingLimit = new SimpleIntegerProperty(maximumBookingLimit);
       
    }
        
    
    //Setters and getters
    
    public int getSid(){
        return this.sid.get();
    }
    
    public void setSid(int sid) {
        this.sid.set(sid);
    }

    public IntegerProperty sidProperty() {
        return sid;
    }

    

  
    public String getdocId(){
        return this.docId.get();
    }
    
    public void setdocId(String docId) {
        this.docId.set(docId);
    }
    
    public StringProperty docIdProperty() {
        return docId;
    }
    
    
    

    public LocalDate getDate(){
        return this.date.get();
    }
    
    public void setDate(LocalDate date) {
        this.date.set(date);
    }
    
    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }
    
    
    
    
    public LocalTime getStartTime(){
        return this.startTime.get();
    }
    
    public void setStartTime(LocalTime startTime) {
        this.startTime.set(startTime);
    }
    
    public ObjectProperty<LocalTime> startTimeProperty() {
        return startTime;
    }

    
    

    public LocalTime getEndTime(){
        return this.endTime.get();
    }
    
    public void setEndTime(LocalTime endTime) {
        this.endTime.set(endTime);
    }
    
    public ObjectProperty<LocalTime> endTimeProperty() {
        return endTime;
    }
    
    
    
    public int getRoomNo(){
        return this.roomNo.get();
    }

    public void setRoomNo(int roomNo) {
        this.roomNo.set(roomNo);
    }
    
    public IntegerProperty roomNoProperty() {
        return roomNo;
    }

    
    
    public int getAverageTimePerBooking(){
        return this.averageTimePerBooking.get();
    }
    
    public void setAverageTimePerBooking(int averageTimePerBooking) {
        this.averageTimePerBooking.set(averageTimePerBooking);
    }
    
    public IntegerProperty averageTimePerBookingProperty() {
        return averageTimePerBooking;
    }
        


    public int getmaxBookingLimit(){
        return this.maxBookingLimit.get();
    }
    
    public void setmaxBookingLimit(int maxBookingLimit) {
        this.maxBookingLimit.set(maxBookingLimit);
    }
    
    public IntegerProperty maxBookingLimitProperty() {
        return maxBookingLimit;
    }
    
    
}
