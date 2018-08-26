/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reception;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Vithu
 */
public class DBControl {
    
    
    //Connect to db
    DBConnector db = new DBConnector();
    Connection conn = db.getDBConnection();
    
    
    public ResultSet findPatientId(String patName) throws SQLException
    {   
        String query1 = "SELECT patID FROM patient WHERE name='"+patName+"' ";
        // create the java statement
        Statement st1 = conn.createStatement();
        // execute the query, and get a java resultset
        ResultSet rs1 = st1.executeQuery(query1);
        
        return rs1;
    }
    
    
    public boolean insertBooking(int appNo, Date appDa, String AppTime, Double payAmount, String paySta, String arrSta, int patId, String docID) throws SQLException
    {   
        try{
            //SQL insert statement (NOTE: Photo NOT included)
            String sql="INSERT INTO `booking`(`referNo`, `appNo`, `appDate`, `appTime`, `payAmount`, `payStatus`, `arriveStatus`, `patID`, `docId`) VALUES (?,?,?,?,?,?,?,?,?)";
                
            //Create prerpared statement
            PreparedStatement pst = conn.prepareStatement(sql);
                
            pst.setString(1,"0");
            pst.setInt(2, appNo);
            pst.setDate(3, appDa);
            pst.setString(4, AppTime);
            pst.setDouble(5, payAmount);
            pst.setString(6, paySta);
            pst.setString(7, arrSta);
            pst.setInt(8, patId);
            pst.setString(9, docID);
                
            pst.executeUpdate();//execute the sql query
        
            return true;
        }
        catch(Exception exe){
            
            return false; 
        }
    }
    
    
    public ResultSet findAllDoctorName() throws SQLException
    {   
        String sql=" SELECT e.name from employee e, doctor d WHERE d.docId=e.empId ";
        Statement st2 = conn.createStatement();
        ResultSet rs2 = st2.executeQuery(sql);
        
        return rs2;
    }
    
    public ResultSet findAllPatientName() throws SQLException
    {   
        String sql=" SELECT name from patient ";
        Statement st = conn.createStatement();
        ResultSet rs3 = st.executeQuery(sql);
        
        return rs3;
    }
    
     
    public ResultSet findDocId(String doc) throws SQLException
    {   
        String sql=" SELECT e.empId from employee e, doctor d WHERE d.docId=e.empId AND e.name='"+doc+"' ";
        Statement st = conn.createStatement();
        ResultSet rs4 = st.executeQuery(sql);
        
        return rs4;
    }
    
    public ResultSet findDocScheduleDate(String docID) throws SQLException
    {   
        String sql2=" SELECT s.date FROM schedule s where s.docId='"+docID+"' ";
        Statement st2 = conn.createStatement();
        ResultSet rs5 = st2.executeQuery(sql2);
        
        return rs5;
    }
    
    public ResultSet findDocMaxBookLimit(String docID, String date) throws SQLException
    {   
        String sql3=" SELECT s.maxBookingLimit FROM schedule s WHERE s.docId='"+docID+"' AND s.date='"+date+"' ";
        Statement st3 = conn.createStatement();
        ResultSet rs6 = st3.executeQuery(sql3);
        
        return rs6;
    }
    
    public ResultSet findAppNo(String docID, String date) throws SQLException
    {   
        String sql4=" SELECT b.appNo FROM booking b WHERE b.docId='"+docID+"' AND b.appDate='"+date+"' ORDER BY b.appNo ASC ";
        Statement st4 = conn.createStatement();
        ResultSet rs6 = st4.executeQuery(sql4);
        
        return rs6;
    }
    
    public ResultSet findStartTime(String docID, String date) throws SQLException
    {   
        String sql3=" SELECT s.startTime FROM schedule s WHERE s.docId='"+docID+"' AND s.date='"+date+"' ";
        Statement st3 = conn.createStatement();
        ResultSet rs7 = st3.executeQuery(sql3);
        
        return rs7;
    }
    
    public ResultSet findAvgTimePerBook(String docID, String date) throws SQLException
    {   
        String sql4=" SELECT s.avgTimePerBooking FROM schedule s WHERE s.docId='"+docID+"' AND s.date='"+date+"' ";
        Statement st4 = conn.createStatement();
        ResultSet rs8 = st4.executeQuery(sql4);
        
        return rs8;
    }
    
    public ResultSet findDocFeeAndClinicCharge(String docID) throws SQLException
    {   
           String sql=" SELECT d.doctorFee,d.clinicCharge FROM doctor d WHERE d.docId='"+docID+"' ";
           Statement st = conn.createStatement();
           ResultSet rs9 = st.executeQuery(sql);
        
        return rs9;
    }
    
    
    public ResultSet findEmpId(String docName) throws SQLException
    {   
        String query = "SELECT empId FROM employee WHERE name='"+docName+"' ";
            // create the java statement
        Statement st1 = conn.createStatement();
            // execute the query, and get a java resultset
        ResultSet rs10 = st1.executeQuery(query);
            // iterate through the java resultset
        
        return rs10;
    }
    
    
    public boolean updateBooking(int appNo, String appDa, String appTime, Double payAmo, String paySta, String arrSta, int patId, String docID, int refer) throws SQLException
    {   
        try{
           //SQL insert statement (NOTE: Photo NOT included) SELECT `referNo`FROM booking b WHERE b.appNo='1' AND b.docId="DR1000" AND b.patID=8
                String sql="UPDATE `booking` SET `appNo`= ?,`appDate`= ?,`appTime`= ?,`payAmount`= ?,`payStatus`= ?,`arriveStatus`= ?,`patID`= ?,`docId`= ? WHERE referNo= ?";
                
                //Create prerpared statement
                PreparedStatement pst = conn.prepareStatement(sql);
                
                System.out.println("update check");
                
                pst.setInt(1,  appNo);
                pst.setString(2, appDa);
                pst.setString(3, appTime);
                pst.setDouble(4, payAmo);
                pst.setString(5, paySta);
                pst.setString(6, arrSta);
                pst.setInt(7, patId);
                pst.setString(8, docID);
                pst.setInt(9, refer);
                
                pst.executeUpdate();//execute the sql query
        
            return true;
        }
        catch(Exception exe){
            System.out.println(exe);
            return false; 
        }
    }
    
    
    public ResultSet selectBookingTable() throws SQLException
    {   
       //Create a statement
            Statement stmt = conn.createStatement();
            
            //SQL Query
            String sql = " SELECT b.referNo,e.name,p.name as pName,b.appNo,b.appDate,b.appTime,b.payAmount,b.payStatus,b.arriveStatus FROM employee e, booking b, patient p where e.empId=b.docId AND p.patID=b.patID ORDER BY b.referNo DESC ";
            //String sql = "Select";
            
            //Execute query and store results
            ResultSet rs11 = stmt.executeQuery(sql);
        
        return rs11;
    }
    
    
    public int deleteBooking(int referNO) throws SQLException
    {   
        String query = "DELETE FROM `booking` WHERE `booking`.`referNo` = '"+referNO+"'";
      
                 // create the java statement
                PreparedStatement pst = conn.prepareStatement(query);
      
      
                // execute the query, and get a java resultset
                int result = pst.executeUpdate(query);
                pst.close();
                
        return result;
    }
    
    public ResultSet findRefernceNo(int appNo,int PatId, String docId) throws SQLException
    {   
           String query3 = "SELECT `referNo`FROM booking b WHERE b.appNo='"+appNo+"' AND b.docId='"+docId+"' AND b.patID='"+PatId+"' ";
            // create the java statement
            Statement st3 = conn.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs12 = st3.executeQuery(query3);
        
        return rs12;
    }
    
    public boolean insertComplaint(String complainee, String complaineeRole, String complaint, String complainer, String complainerRole) throws SQLException
    {  
        
        try{
                //Create connection to db
                conn = db.getDBConnection();
                
                //SQL insert statement (NOTE: Photo NOT included)
                String sql="INSERT INTO `complaint`(`complainee`, `complaineeRole`, `ctid`, `complaint`, `complainer`, `complainerRole`, `date`) VALUES (?,?,?,?,?,?,?)";
                
                //Create prerpared statement
                PreparedStatement pst = conn.prepareStatement(sql);
                
                System.out.println("Hello123");
                
                pst.setString(1,complainee);
                pst.setString(2,complaineeRole);
                pst.setString(3,"0");
                pst.setString(4,complaint);
                pst.setString(5,complainer);
                pst.setString(6,complainerRole);
                
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate localDate = LocalDate.now();
                
                pst.setString(7,dtf.format(localDate));
                
                pst.executeUpdate();//execute the sql query
                
                return true;
               }
             
            catch(Exception e){
                System.out.println(e);
                
                    return false;
                }
        
    }
    
    public ResultSet selectComplaintTable() throws SQLException
    {   
       //Create a statement
        Statement stmt = conn.createStatement();
            
        //SQL Query
        String sql = "SELECT * FROM `complaint`";
            
        //Execute query and store results
        ResultSet rs12 = stmt.executeQuery(sql);
        
        return rs12;
    }
    
    public ResultSet selectBookingTableByDate(String date) throws SQLException
    {   
        //Create a statement
        Statement stmt = conn.createStatement();
            
        //SQL Query
        String sql = " SELECT b.referNo,e.name,p.name as pName,b.appNo,b.appDate,b.appTime,b.payAmount,b.payStatus,b.arriveStatus AS arrStatus FROM employee e, booking b, patient p where e.empId=b.docId AND p.patID=b.patID AND b.appDate='"+date+"' ORDER BY b.referNo ASC ";
            
        //Execute query and store results
         ResultSet rs13 = stmt.executeQuery(sql);
        
        return rs13;
    }
    
    
    public ResultSet findAttendance(String date) throws SQLException
    {   
        String sql2 = " SELECT `empId` FROM `attendance` WHERE date='"+date+"' ";
        Statement stmt2 = conn.createStatement();
        ResultSet rs14 = stmt2.executeQuery(sql2);
        
        return rs14;
    }
    
    public void insertMemo(Date date, String description)
    {  
         try{
        
            String sql = "INSERT INTO `memo`(`memoID`, `date`, `description`) VALUES (?,?,?)";
            PreparedStatement pst = db.getDBConnection().prepareStatement(sql);
            
            pst.setString(1, "0");
            pst.setDate(2, date);
            pst.setString(3, description);
        
            pst.executeUpdate();
        
        }
        catch(Exception exe){
            System.out.println(exe);
            }
        
    }
    
    
    public ResultSet selectMemoTable(LocalDate b) throws SQLException
    {
         //Create a statement
            Statement stmt = conn.createStatement();
            
            //SQL Query
            String sql = " SELECT * FROM `memo` WHERE date >= '"+Date.valueOf(b)+"'";
            //String sql = "Select";
            
            //Execute qu5ery and store results
            ResultSet rs15 = stmt.executeQuery(sql);
            
            return rs15;
    
    }
    
    
    public ResultSet selectDocSchedule(String docName) throws SQLException
    {
        String sql = "SELECT s.sid, s.docId, s.date, s.startTime, s.endTime, s.roomNo, s.avgTimePerBooking, s.maxBookingLimit"
                    + " FROM schedule s, employee e "
                    + "WHERE s.docId=e.empId AND  e.name='"+docName+"' ";
        
        Statement st = conn.createStatement();
        ResultSet rs16 = st.executeQuery(sql);
        
        
        return rs16;
    }
    
    public ResultSet selectDoctor() throws SQLException
    {
            //Create a statement
            Statement stmt = conn.createStatement();
            
            //SQL Query
            String sql = "SELECT * FROM doctor d, employee e WHERE d.docId=e.empId";
            
            //Execute query and store results
            ResultSet rs17 = stmt.executeQuery(sql);
            
            return rs17;
    }
    
    public ResultSet searchDoctorNameNSpeciality() throws SQLException
    {
           String sql=" SELECT speciality, name from doctor d, employee e where d.docId=e.empId ";
           Statement st = conn.createStatement();
           ResultSet rs18 = st.executeQuery(sql);
            
            return rs18;
    }
    
    public ResultSet searchDoctorName(String searchKeyword) throws SQLException
    {
           //Create a statement
            Statement stmt = conn.createStatement();
            
            //SQL Query
            String sql = "SELECT * FROM doctor d, employee e WHERE d.docId=e.empId AND e.name='"+searchKeyword+"'";
            
            //Execute query and store results
            ResultSet rs19 = stmt.executeQuery(sql);
            
           return rs19;
    }
    
    public ResultSet searchDoctorSpeciality(String searchKeyword) throws SQLException
    {
           //Create a statement
            Statement stmt = conn.createStatement();
            
            //SQL Query
            String sql = "SELECT * FROM doctor d, employee e WHERE d.docId=e.empId AND d.speciality='"+searchKeyword+"'";
            
            //Execute query and store results
            ResultSet rs20 = stmt.executeQuery(sql);

           return rs20;
    }
    
    
    public boolean insertPatient(String Name,String Sex,String Dob,String Address,String Phone,String Email,String Age, String Job, String Marital)
    {  
         try{
                //Create connection to db
                conn = db.getDBConnection();
                
                //SQL insert statement (NOTE: Photo NOT included)
                String sql="INSERT INTO `patient`(`patID`, `name`, `sex`, `dob`, `address`, `phone`, `email`, `age`, `job`, `marital`) VALUES (?,?,?,?,?,?,?,?,?,?)";
                
                //Create prerpared statement
                PreparedStatement pst = conn.prepareStatement(sql);
                
                System.out.println("Hello123");
                
                pst.setString(1,"0");
                pst.setString(2,Name);
                pst.setString(3,Sex);
                pst.setDate(4,java.sql.Date.valueOf(Dob));
                pst.setString(5,Address);
                pst.setString(6,Phone);
                pst.setString(7,Email);
                pst.setString(8,Age);
                pst.setString(9,Job);
                pst.setString(10,Marital);
                
                pst.executeUpdate();//execute the sql query
                
               
                return true;
               }
             
            catch(Exception e){
                System.out.println(e);
            
                return false;
                }
    }
    
     
    public ResultSet selectPatientID(String name) throws SQLException
    {
            
                // if you only need a few columns, specify them by name instead of using "*"
                String query = "SELECT patID FROM patient where name= '"+name+"' ";
      
                 // create the java statement
                Statement st = conn.createStatement();
      
      
                // execute the query, and get a java resultset
                ResultSet rs21 = st.executeQuery(query);
                
                return rs21;
    }
    
    
    public boolean updatePatient(String Name,String Sex,String Dob,String Address,String Phone,String Email,String Age, String Job, String Marital, String patID) throws SQLException
    {  
        
        try{
                String sql="UPDATE `patient` SET `name`= ?,`sex`= ?,`dob`= ?,`address`= ?,`phone`= ?,`email`= ? ,`age`= ?, `job`= ?, `marital`= ? WHERE `patID`= ?";
                //Create prerpared statement
                PreparedStatement pst = conn.prepareStatement(sql);
                
                
                
                
                pst.setString(1,Name);
                pst.setString(2,Sex);
                pst.setDate(3,java.sql.Date.valueOf(Dob));
                pst.setString(4,Address);
                pst.setString(5,Phone);
                pst.setString(6,Email);
                pst.setString(7,Age);
                pst.setString(8,Job);
                pst.setString(9,Marital);
                pst.setInt(10, Integer.parseInt(patID));
                
                int status = pst.executeUpdate();
                
                if(status > 0) {   //If row inserted, status will be 1 (1 row inserted), else 0
                pst.close();
                conn.close();
            }
                return true;
               }
         catch(Exception e){
                System.out.println(e);
            
                return false;
                }
    }
    
    
    public ResultSet selectPatientTable() throws SQLException
    {           
            //Create a statement
            Statement stmt = conn.createStatement();
            
            //SQL Query
            String sql = "SELECT * FROM `patient`";
            
            //Execute query and store results
            ResultSet rs22 = stmt.executeQuery(sql);
                
            return rs22;
    }
    
    
    public boolean deletePatient(String name) throws SQLException
    {   
       try{
                String query = "DELETE FROM `patient` WHERE `patient`.`name` = '"+name+"'";
      
                 // create the java statement
                PreparedStatement pst = conn.prepareStatement(query);
      
      
                // execute the query, and get a java resultset
                int rs = pst.executeUpdate(query);
                
                
                int status = pst.executeUpdate();

            if(status > 0) {   //If row inserted, status will be 1 (1 row inserted), else 0
                pst.close();
                conn.close();
                }
            return true;
            }
            catch(Exception exe){
                System.out.println(exe);
                exe.printStackTrace();
                return false;
                }
    }
    
    
    public ResultSet searchPatientName(String patName) throws SQLException
    {   
        //Create a statement
        Statement stmt = conn.createStatement();
            
        //SQL Query
        String sql = "SELECT * FROM `patient` where name='"+patName+"' ";
            
        //Execute query and store results
        ResultSet rs23 = stmt.executeQuery(sql);
        return rs23;
    }
    
}
