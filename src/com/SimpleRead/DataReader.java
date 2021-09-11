//package com.SimpleRead;
//
//import be.quodlibet.boxable.BaseTable;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//
//import java.sql.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.common.PDRectangle;
//import org.apache.pdfbox.pdmodel.font.PDFont;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.usermodel.XSSFCellStyle;
//import org.apache.poi.xssf.usermodel.XSSFFont;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//public class sampleGUI  implements ActionListener {
//
//    private JTextField f_text;
//    private JTextField t_text;
//    private  JTextField reportGenerateStatus;
//    private JButton show_button;
//    private JButton itext_button;
//    private JButton pdfbox_button;
//    private JButton e_button;
//    static JTable table;
//    String[] columnNames = {"COM NUM", "Date", "Start Time", "Stop Time"};
//
//    String url = "jdbc:mysql://localhost:3306/sys";
//    String uname = "Yash";
//    String pass = "YaMoh@2000";
//    String query = " select COM_NUM,Date,Start_Time,Stop_Time from COM_START_STOP where date between ? and ?";
//
//    public void sampleGUI() {
//
//        // Main GUI
//
//        JFrame frame = new JFrame();
//        frame.setVisible(true);
//        frame.setTitle("Data Fetching");
//        frame.setLayout(null);
//        frame.setSize(550, 550);
//        frame.getContentPane().setBackground(new Color(0xf3ece7));
//
//        Font fontProp = new Font("SansSerif", Font.PLAIN, 20);
//        JFrame newFrame = new JFrame();
//
//        newFrame.setTitle("Data Report Generation");
//        newFrame.setLayout(null);
//        newFrame.setSize(450, 450);
//        newFrame.getContentPane().setBackground(new Color(0xf3ece7));
//        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        JLabel f_label = new JLabel();
//        f_label.setText("FROM");
//        f_label.setBounds(30, 30, 100, 50);
//        f_label.setFont(fontProp);
//
//
//        f_text = new JTextField();
//        f_text.setBounds(30, 70, 150, 30);
//        f_text.setText("2021-02-08");
//
//        f_text.setFont(new Font("SansSerif", Font.PLAIN, 18));
//        f_text.getText();
//        JLabel t_label = new JLabel();
//        t_label.setText("TO");
//        t_label.setBounds(300, 30, 150, 50);
//        t_label.setFont(fontProp);
//
//        t_text = new JTextField();
//        t_text.setBounds(300, 70, 150, 30);
//        t_text.setText("2021-02-10");
//        t_text.setFont(fontProp);
//
//        JLabel date_format_info = new JLabel();
//        date_format_info.setBounds(30, 0, 550, 30);
//        date_format_info.setText("Date Format : YYYY-MM-DD ");
//        date_format_info.setFont(fontProp);
//
//        show_button = new JButton("SHOW DATA");
//        show_button.setBounds(160, 120, 150, 50);
//        show_button.setFocusable(false);
//        show_button.addActionListener((ActionListener) this);
//
//        JLabel s_pdf = new JLabel();
//        s_pdf.setText("Download-PDF");
//        s_pdf.setBounds(30, 170, 150, 50);
//        s_pdf.setFont(fontProp);
//
//        JLabel s_excel = new JLabel();
//        s_excel.setText("Downlaod-EXCEL");
//        s_excel.setBounds(300, 170, 250, 50);
//        s_excel.setFont(fontProp);
//
//        itext_button = new JButton("PDF (Itext)");
//        itext_button.setBounds(30, 300, 150, 50);
//        itext_button.setFocusable(false);
//        itext_button.addActionListener((ActionListener) this);
//
//        pdfbox_button = new JButton("PDF (Pdf Box)");
//        pdfbox_button.setBounds(30, 210, 150, 50);
//        pdfbox_button.setFocusable(false);
//        pdfbox_button.addActionListener((ActionListener) this);
//
//        e_button = new JButton("EXCEL");
//        e_button.setBounds(300, 210, 150, 50);
//        e_button.setFocusable(false);
//        e_button.addActionListener((ActionListener) this);
//
//        reportGenerateStatus = new JTextField("-");
//        reportGenerateStatus.setEditable(false);
//        reportGenerateStatus.setBounds(300,300,150,50);
//
//        frame.add(show_button);
//        frame.add(f_label);
//        frame.add(f_text);
//        frame.add(t_label);
//        frame.add(t_text);
//        frame.add(date_format_info);
//
//        frame.add(itext_button);
//        frame.add(pdfbox_button);
//        frame.add(e_button);
//        frame.add(s_pdf);
//        frame.add(s_excel);
//        frame.add(reportGenerateStatus);
//
//    }
//
//
////----------------BUTTON FUNCTIONS -Adding ActionListeners -------------
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        String s = e.getActionCommand();
//        if (e.getSource() == show_button) {
//            System.out.println("Showing Table Data.......");
//            showTableData();
//        }
//
//        if (e.getSource() == itext_button) {
//            System.out.println("genearting Pdf");
//
//            try {
//                generatePdfItext();
//            } catch (SQLException ex) {
//                Logger.getLogger(sampleGUI.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(sampleGUI.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            System.out.println("Out");
//        }
//        if (e.getSource() == pdfbox_button) {
//            System.out.println("genearting Pdf");
//
//            try {
//                generatePdfPdfbox();
//            } catch (SQLException ex) {
//                Logger.getLogger(sampleGUI.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(sampleGUI.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            System.out.println("Out");
//        }
//
//        if (e.getSource() == e_button) {
//            System.out.println("Generating Excel");
//            try {
//                GenerateExcel();
//            } catch (SQLException ex){
//                Logger.getLogger(sampleGUI.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ClassNotFoundException exe) {
//                Logger.getLogger(sampleGUI.class.getName()).log(Level.SEVERE, null, exe);
//            }
//            System.out.println("Out");
//        }
//
//    }
//
//    //-------------------- SHOW DATA ----------------------
//
//    public void showTableData() {
//
//        JFrame frame1 = new JFrame("Database Search Result");
//
//        frame1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//
//        frame1.setVisible(true);
//        DefaultTableModel model = new DefaultTableModel();
//
//        model.setColumnIdentifiers(columnNames);
//        table = new JTable();
//
//        table.setModel(model);
//
//        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//
//        table.setFillsViewportHeight(true);
//        table.setVisible(true);
//
//        JScrollPane scroll = new JScrollPane(table);
//
//        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//
//        String f_date = f_text.getText();
//        String t_date = t_text.getText();
//        String comm_no;
//        Date date;
//        String start_time;
//        String stop_time;
//
//        try {
//
//            try (Connection con = DriverManager.getConnection(url, uname, pass)) {
//                System.out.println("Databse Connection Successfull");
//
//                PreparedStatement pstmt = con.prepareStatement(query);
//                pstmt.setString(1, f_date);
//                pstmt.setString(2, t_date);
//                ResultSet set = pstmt.executeQuery();
//
//                int i = 0;
//
//                while (set.next()) {
//
//                    comm_no = set.getString("COM_NUM");
//                    date = set.getDate("Date");
//                    start_time = set.getString("Start_Time");
//                    stop_time = set.getString("Stop_Time");
//                    model.addRow(new Object[]{comm_no, date, start_time, stop_time});
//                    System.out.println(comm_no + "|" + date + "|" + start_time + "|  " + stop_time + "  |");
//                    i++;
//
//                }
//
//                if (i < 1) {
//                    JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//
//                if (i == 1) {
//                    System.out.println(i + " Record Found");
//                } else {
//                    System.out.println(i + " Records Found");
//                }
//                con.close();
//
//
//            }
//        } catch (Exception ex) {
//
//            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//
//        }
//
//        frame1.add(scroll);
//
//        frame1.setVisible(true);
//
//        frame1.setSize(400, 300);
//    }
//
//
//    //------------------------GENERATE PDF ------------------------
//
//    public void generatePdfItext() throws SQLException, ClassNotFoundException {
//        String f_date = f_text.getText();
//        String t_date = t_text.getText();
//        String comm_no;
//        String date;
//        String start_time;
//        String stop_time;
//
//        Class.forName("com.mysql.cj.jdbc.Driver");
//
//        try (Connection con = DriverManager.getConnection(url, uname, pass)) {
//            System.out.println("Databse Connection Successfull");
//
//
//            PreparedStatement pstmt = con.prepareStatement(query);
//            pstmt.setString(1, f_date);
//            pstmt.setString(2, t_date);
//            ResultSet set = pstmt.executeQuery();
//
//            try {
//                String file_name = "E:\\view_file(itextPDF)" + f_date + "--" + t_date + ".pdf";
//
//
//                //Creating and opening pdf
//                com.itextpdf.text.Document document = new com.itextpdf.text.Document();
//                PdfWriter.getInstance(document, new FileOutputStream(file_name));
//                document.open();
//
//                Paragraph heading = new Paragraph(" Automatic Usage Report Generator  \n\n" );
//                heading.setAlignment(5);
//                Paragraph para1 = new Paragraph(" This is the data from the user :- \n\n");
//
//                document.add(heading);
//                document.add(para1);
//
//                // Table Headers
//                PdfPTable ptable = new PdfPTable(4);
//                PdfPCell cell;
//                ptable.addCell("COM_NUM");
//                ptable.addCell("Date");
//                ptable.addCell("Start_Time");
//                ptable.addCell("Stop_Time");
//
//                //Creating Table
//                while (set.next()) {
//
//                    comm_no = set.getString("COM_NUM");
//                    cell = new PdfPCell(new Phrase(comm_no));
//                    ptable.addCell(cell);
//
//                    date = set.getString("Date");
//                    cell = new PdfPCell(new Phrase(date));
//                    ptable.addCell(cell);
//
//                    start_time = set.getString("Start_Time");
//                    cell = new PdfPCell(new Phrase(start_time));
//                    ptable.addCell(cell);
//
//                    stop_time = set.getString("Stop_Time");
//                    cell = new PdfPCell(new Phrase(stop_time));
//                    ptable.addCell(cell);
//                }
//
//                //Adding Table to pdf and closing pdf
//                document.add(ptable);
//                document.close();
//                System.out.println("PDF  created");
//                reportGenerateStatus.setText("PDF Generated");
//
//            } catch (FileNotFoundException | DocumentException e) {
//                System.out.println("Error creating PDF" + e);
//                reportGenerateStatus.setText("ERROR - File Open");
//            }
//            finally {
//                pstmt.close();
//                con.close();
//            }
//
//        }
//
//    }
//
//    //-------------------------- PDF GENERAT (PDFBOX) -------------------------
//
//    public void generatePdfPdfbox() throws SQLException, ClassNotFoundException {
//
//
//        try (Connection con = DriverManager.getConnection(url, uname, pass)) {
//
//            System.out.println("Databse Connection Successfull");
//            PreparedStatement pstmt = con.prepareStatement(query);
//            String f_date=f_text.getText();
//            String  t_date = t_text.getText();
//            pstmt.setString(1,f_text.getText());
//            pstmt.setString(2,t_text.getText());
//            ResultSet set = pstmt.executeQuery();
//
//            try {
//                String file_name = "E:\\Report (pdfbox)" + f_date + "--" + t_date + ".pdf";
//                String outputFileName = file_name;
//
//                PDFont fontBold = PDType1Font.HELVETICA_BOLD;
//
//                PDDocument document = new PDDocument();
//                PDPage page = new PDPage(PDRectangle.A4);
//
//                document.addPage(page);
//
//                PDPageContentStream cos = new PDPageContentStream(document, page);
//
//                float margin = 50;
//                float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
//                float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
//                float bottomMargin = 70;
//                float yPosition = 550;
//
//                BaseTable table = new BaseTable(yPosition, yStartNewPage,
//                        bottomMargin, tableWidth, margin, document, page, true, true);
//
//                be.quodlibet.boxable.Row<PDPage> headerRow = table.createRow(50);
//
////            be.quodlibet.boxable.Cell<PDPage> cell = headerRow.createCell(100, "Automated Usage Report generator (Aurg)");
//                be.quodlibet.boxable.Cell<PDPage> cell = headerRow.createCell(100, "Automated Usage Report generator Report :          " +
//                        " From " + f_date + " To " + t_date);
//                cell.setFont(fontBold);
//                cell.setFontSize(20);
//
//                // Setting Table Headers
//
//                be.quodlibet.boxable.Row<PDPage> row = table.createRow(20);
//                cell = row.createCell(25, "Comm Num");
//                cell.setFontSize(15);
//                cell = row.createCell(25, "Date");
//                cell.setFontSize(15);
//                cell.setFont(fontBold);
//                cell = row.createCell(25, "Start Time");
//                cell.setFontSize(15);
//                cell.setFont(fontBold);
//                cell = row.createCell(25, "Stop Time");
//                cell.setFontSize(15);
//                cell.setFont(fontBold);
//
//                //Creating Table
//                while (set.next()) {
//
//                    be.quodlibet.boxable.Row<PDPage> bodyrow = table.createRow(20);
//                    cell = bodyrow.createCell(25, set.getString("COM_NUM"));
//                    cell.setFontSize(15);
//
//                    cell = bodyrow.createCell(25, set.getString("Date"));
//                    cell.setFontSize(15);
//
//                    cell = bodyrow.createCell(25, set.getString("Start_Time"));
//                    cell.setFontSize(15);
//
//                    cell = bodyrow.createCell(25, set.getString("Stop_Time"));
//                    cell.setFontSize(15);
//
//                }
//
//                table.draw();
//
//                // close the content stream
//                cos.close();
//                document.save(outputFileName);
//                document.close();
//
//                System.out.println("Excel Generated Succesfully");
//                reportGenerateStatus.setText("PDF Generated");
//            }catch (Exception e){
//                System.out.println("Error making PDF" + e);
//                reportGenerateStatus.setText("ERROR - File Open");
//            }finally {
//                pstmt.close();
//                con.close();
//            }
//
//
//        } catch (Exception e) {
//            System.out.println("error generating execl" + e);
//        }
//
//    }
//
//    //-------------------- GENERATE EXCEL -----------------------------
//
//    public void GenerateExcel() throws SQLException, ClassNotFoundException {
//
//        XSSFWorkbook workbook = new XSSFWorkbook();                        // creating workbook
//        XSSFSheet sheet = workbook.createSheet("Report Excel");
//
//        sheet.setColumnWidth(1,5000);       //setting column width
//        sheet.setColumnWidth(2,4000);
//        sheet.setColumnWidth(3,4000);
//        sheet.setColumnWidth(4,4000);
//        String f_date = f_text.getText();
//        String t_date = t_text.getText();
//
//
//        //Connecting to Database
//
//        try (Connection con = DriverManager.getConnection(url, uname, pass)) {
//            System.out.println("Databse Connection Successfull");
//
//
//            PreparedStatement pstmt = con.prepareStatement(query);
//            pstmt.setString(1, f_date);
//            pstmt.setString(2, t_date);
//            ResultSet set = pstmt.executeQuery();       // Result Set of Fetched Data
//
//
//            String[] excelHeadings = {"Comm Number", "Date" , "Start Time", "Stop Time"};
//
//            XSSFCellStyle style = workbook.createCellStyle();
//            style.setBorderTop((short) 6); // double lines border
//            style.setBorderBottom((short) 1); // single line border
//            XSSFFont font = workbook.createFont();
//            font.setFontHeightInPoints((short) 15);
//            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
//            style.setFont(font);
//
//            Row row1 = sheet.createRow((short) 0);
//            Cell cell1 = row1.createCell((short) 1);
//            cell1.setCellValue("Automated Usage Report Generator");
//            CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 1, 4);
//            sheet.addMergedRegion(cellRangeAddress);
//            cell1.setCellStyle(style);
//
//            try {
//
//                String file_name="E:\\Report"+ f_date + "--"+ t_date + ".xlsx";
//
//                int rowCount=1;
//                Row headerRow = sheet.createRow(rowCount++);
//                int headerColumn = 0;
//
//                //Table Headers
//                for (String excelHeading : excelHeadings) {
//                    Cell Excelcell = headerRow.createCell(++headerColumn);
//                    Excelcell.setCellValue(excelHeading);
//                    Excelcell.setCellStyle(style);
//                }
//
//                rowCount++;
//                //Creating Tables
//                while (set.next()) {
//
//                    Row row = sheet.createRow(rowCount++);
//
//                    int columnCount = 1;
//                    Cell cell = row.createCell(columnCount++);
//                    cell.setCellValue(set.getString("COM_NUM"));
//
//                    cell = row.createCell(columnCount++);
//                    cell.setCellValue(set.getString("Date"));
//
//                    cell = row.createCell(columnCount++);
//                    cell.setCellValue(set.getString("Start_Time"));
//
//                    cell = row.createCell(columnCount);
//                    cell.setCellValue(set.getString("Stop_Time"));
//
//                }
//                pstmt.close();
//                con.close();
//
//                try (FileOutputStream outputStream = new FileOutputStream(file_name)) {
//                    workbook.write(outputStream);
//                    System.out.println("Excel Created");
//                    reportGenerateStatus.setText("Excel Generated");
//                }
//
//
//            } catch (Exception e) {
//                System.out.println("error generating execl" + e);
//                pstmt.close();
//                con.close();
//                reportGenerateStatus.setText("Error File Open");
//            }
//        }
//    }
//
//
//
//
//    public static void main(String args[]) {
//        sampleGUI sr = new sampleGUI();
//
//    }
//}
//
