/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reception;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Vithu
 */
public class PrintInvoice {
    
    
    public void print(Booking selectedBooking) throws FileNotFoundException, DocumentException, InterruptedException, BadElementException, IOException {
    
        
        
        
      if(selectedBooking != null)
      {
        
        //create new document
        Document doc = new Document();
        Rectangle page = doc.getPageSize();
        
        
        //set pdf name
        PdfWriter.getInstance(doc, new FileOutputStream("Invoice "+selectedBooking.getreferNo()+".pdf"));
        
        doc.open();
        
        //set page size
        doc.setPageSize(PageSize.A5.rotate());
        doc.newPage();
        
        
        /*
        //PrintDate
        PdfPTable tableDate = new PdfPTable(1);
        tableDate.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tableDate.setWidthPercentage(160 / 5.23f);
        PdfPCell cell = new PdfPCell(new Paragraph("Printed Date"));
        cell.setBackgroundColor(BaseColor.CYAN);
        cell.setBorderWidth(2f);
        tableDate.addCell(cell);
        String date = LocalDate.now().toString();
        PdfPCell cellTwo = new PdfPCell(new Phrase(date));
        cellTwo.setBorderWidth(2f);
        tableDate.addCell(cellTwo);
        doc.add(tableDate);
        */
        


        //Add header
        PdfPTable header = new PdfPTable(1);
        
        // set defaults
        header.setWidths(new int[]{24});
        header.setTotalWidth(527);
        header.setLockedWidth(true);
        header.getDefaultCell().setFixedHeight(40);
        header.getDefaultCell().setBorder(Rectangle.BOTTOM);
        header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

        // add image
        Image logo = Image.getInstance(BookingViewController.class.getResource("images/medi.png"));
        logo.scaleAbsoluteWidth(100.0f);
        //System.out.println(logo.getWidth());
            
        //header.addCell(logo);

        // add text
        PdfPCell text = new PdfPCell();
        text.addElement(logo);
        text.setPaddingBottom(15);
        text.setPaddingLeft(10);
        text.setBorder(Rectangle.BOTTOM);
        text.setBorderColor(BaseColor.LIGHT_GRAY);
        text.addElement(new Phrase("MEDICARE CLINIC", new Font(Font.FontFamily.COURIER, 24)));
        text.addElement(new Phrase("We Care You", new Font(Font.FontFamily.HELVETICA, 16)));
        text.addElement(new Phrase("Invoice No: "+selectedBooking.getreferNo(), new Font(Font.FontFamily.HELVETICA, 12)));
            
        header.addCell(text);

        doc.add(header);
        
        
        
        
        
        //create new table
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(11f);
        table.setSpacingAfter(11f);
        
        float [] colWidth = {2f,2f,2f,2f,2f,2f,2f};
        table.setWidths(colWidth);
        
        PdfPCell c1 = new PdfPCell(new Paragraph("Reference No"));
        PdfPCell c2 = new PdfPCell(new Paragraph("Doctor Name"));
        PdfPCell c3 = new PdfPCell(new Paragraph("Patient Name"));
        PdfPCell c4 = new PdfPCell(new Paragraph("Appointment No"));
        PdfPCell c5 = new PdfPCell(new Paragraph("Appointment Date"));
        PdfPCell c6 = new PdfPCell(new Paragraph("Appointment Time"));
        PdfPCell c7 = new PdfPCell(new Paragraph("Total Amount"));
        
        PdfPCell c1a = new PdfPCell(new Paragraph(selectedBooking.getreferNo()));
        PdfPCell c2a = new PdfPCell(new Paragraph(selectedBooking.getName()));
        PdfPCell c3a = new PdfPCell(new Paragraph(selectedBooking.getPName()));
        PdfPCell c4a = new PdfPCell(new Paragraph(selectedBooking.getappNo()));
        PdfPCell c5a = new PdfPCell(new Paragraph(selectedBooking.getappDate()));
        PdfPCell c6a = new PdfPCell(new Paragraph(selectedBooking.getappTime()));
        PdfPCell c7a = new PdfPCell(new Paragraph(selectedBooking.getpayAmount()));
        
        
        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
        table.addCell(c4);
        table.addCell(c5);
        table.addCell(c6);
        table.addCell(c7);
        
        table.addCell(c1a);
        table.addCell(c2a);
        table.addCell(c3a);
        table.addCell(c4a);
        table.addCell(c5a);
        table.addCell(c6a);
        table.addCell(c7a);
        
        doc.add(table);
        
        
        List orderList = new List(List.ORDERED);
        
        List unorderList = new List(List.UNORDERED);
        unorderList.add(new ListItem("Claim requests are accepted on the following basis;"));
        doc.add(unorderList);
        
        orderList.add(new ListItem("Claim requests for direct booking, need to come before the session start time and the refund will be made in two (2) working days."));
        orderList.add(new ListItem("The Claim requests for direct booking will not be accepted after the session start time and the Company is not liable for any refund pertaining to patients’ appointment after this time."));
        doc.add(orderList);
        
        List unorderList1 = new List(List.UNORDERED);
        unorderList1.add(new ListItem("                                  "));
        unorderList1.add(new ListItem("--Terms & Conditions Applied---"));
        doc.add(unorderList1);
        
        
        doc.close();
        
        //To Open The Pdf File
        Desktop.getDesktop().open(new File("Invoice "+selectedBooking.getreferNo()+".pdf"));
        
      }
        
      else
      {
              Alert alert2 = new Alert(Alert.AlertType.WARNING, "Record to Print is not selected" + "!!!", ButtonType.OK);
              alert2.showAndWait();
      }
        
    }
    
}
