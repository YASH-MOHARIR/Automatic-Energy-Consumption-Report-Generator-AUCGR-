package com.SimpleRead.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;


////////////////////////////////////////////////////////////////////////////////////////////////
//public class appGui implements ActionListener {


    //////////////////////////////////////////////////////////////////////////////////////////
//    public static void main(String[] args) {










//        try
//        {
//            String file_name="E:\\data_creation\\view_file(itextPDF).pdf";
//            Document document = new Document();
//            PdfWriter.getInstance(document , new FileOutputStream(file_name));
//            document.open();
//            Paragraph para = new Paragraph("The Pdf generation is done succesfully\n");
//
//            Paragraph para1 = new Paragraph(" This is the data from the user :- \n\n");
//
//            document.add(para);
//            document.add(para1);
//
//            System.out.println("finished");
//
//            PdfPTable table = new PdfPTable(4);
//            PdfPCell c1 = new PdfPCell(new Phrase("COM_NUM"));
//            table.addCell(c1);
//
//            c1 = new PdfPCell(new Phrase("Date"));
//            table.addCell(c1);
//
//            c1 = new PdfPCell(new Phrase("Strat_Time"));
//            table.addCell(c1);
//
//            c1 = new PdfPCell(new Phrase("Stop_Time"));
//            table.addCell(c1);
//                while(true)
//                {
//                    table.addCell("COM3");
//                    table.addCell("12/01/2021");
//                    table.addCell("12-20-21");
//                    table.addCell("12-25-21");
//
//                    table.addCell("COM3");
//                    table.addCell("13/01/2021");
//                    table.addCell("12-30-21");
//                    table.addCell("12-35-21");
//
//                    table.addCell("COM3");
//                    table.addCell("13/01/2021");
//                    table.addCell("12-30-21");
//                    table.addCell("12-35-21");
//
//            document.add(table);
//
//            document.close();
//            System.out.println("file is created");
//                }
//        }
//        catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//        catch (DocumentException e){}

        //////////////////////////////////////////////////////////////////////////////////////////

//    }
//}


















