package com.SimpleRead;

import com.fazecast.jSerialComm.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


// ---------- AUTOMATIC USAGE REPORT GENERATOR ------------ //

public class Main extends sampleGUI {

    public static void main(String[] args) {

        Instant start = Instant.now();
        Instant end;
        Duration timeElapsed;

        int switchedOnTime = 0;             //Number of times switched On <-> Off
        boolean updateQueryFlag = false;    // Runs the hardwareData Update query , only if connection is successful

        // Data Formatting

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startTime = LocalTime.now().format(dtf);
        String stopTime;

        // Data Base Connection Login Details
        String url = "jdbc:mysql://localhost:3306/sys";
        String uname = "Yash";
        String pass = "YaMoh@2000";

        // Getting All System Serial Ports
        SerialPort ports[] = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            System.out.println(port.getSystemPortName());
        }

        //Available Serial Ports
        System.out.println("Available Serial Ports:");
        for (int i = 0; i < ports.length; i++) {
            System.out.println(i + 1 + "." + ports[i]);
        }

        //  ------------- Main GUI -----------------

        JFrame frame        = new JFrame("Usage Report Generator");
        JMenuBar MenuBar = new JMenuBar();
        JMenu Ports     = new JMenu("Ports");
        MenuBar.add(Ports);

        JPanel mainPanel = new JPanel(new GridLayout(4,2,10,10));

        JLabel Connected_To_Port_Label  = new JLabel("Connected To :");
        JTextField Connected_To_Port    = new JTextField("", 5);
        Connected_To_Port_Label.setBounds(30, 35, 90, 30);
        Connected_To_Port.setEditable(false);
        JLabel Connection_Status_Label  = new JLabel("CURRENT STATUS :");
        JTextField Connection_Status    = new JTextField("Not Connected ");
        JLabel On_Off_Status_Label      = new JLabel("ON/OFF STATUS :");
        JTextField On_Off_Status        = new JTextField("-", 10);

        frame.add(MenuBar);

        mainPanel.add(Connected_To_Port_Label);
        mainPanel.add(Connected_To_Port);
        Connected_To_Port.setEditable(false);
        mainPanel.add(Connection_Status_Label);
        mainPanel.add(Connection_Status);
        Connection_Status.setEditable(false);
        mainPanel.add(On_Off_Status);

        JButton Report  = new JButton(" Generate Report");

        mainPanel.add(On_Off_Status_Label);
        mainPanel.add(On_Off_Status);
        On_Off_Status.setEditable(false);
        mainPanel.add(Report);

        JPanel outter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        outter.add(mainPanel);

        frame.add(outter);

        frame.setSize(450, 450);
        frame.setVisible(true);
//        frame.setLayout(new GridLayout(3,1,5,20));
        frame.setLayout(new FlowLayout(FlowLayout.CENTER) );
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

       //-------------------------------//----------------------------------//

        //Opening Reports Window when btn is clicked
        Report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() == Report) {
                    sampleGUI reportGenerator = new sampleGUI();
                    reportGenerator.sampleGUI();
                }
            }
        });

//        Listing the Ports Inside Port Menu (GUI)
        for (int i = 0; i < ports.length; i++) {
            JMenuItem portName = new JMenuItem((i + 1) + ". " + ports[i].getSystemPortName());
            Ports.add(portName);
            portName.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Connected_To_Port.setText(e.getActionCommand());
                }
            });
        }



        while (Connected_To_Port.getText() != "") {         //Checks if any port is selected

            try {
                //        Port Connection -- connecting to selected port
                int selectedPort = Integer.parseInt(Connected_To_Port.getText().substring(0, 1));
                SerialPort dataPort = ports[selectedPort - 1];

                if (dataPort.openPort()) {
                    System.out.println(dataPort + ": PORT CONNECTED");
                    Connection_Status.setText("Connected");
                    updateQueryFlag = true;
                } else {
                    Connection_Status.setText(" Not Connected");
                    System.out.println(("ERROR : PORT NOT OPEN"));
                }

                // Setting Port Setting Variables

                dataPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
                Scanner data = new Scanner(dataPort.getInputStream());

                // Connecting to Database - MYSQL

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, uname, pass);
                Statement st = con.createStatement();

                try {

                    // State Switching ON <->OFF
                    byte prev = 0, present;             // RANDOM PREV ! = (1 || 0) -- TO GET INITIAL ON/OFF STATUS
                    On_Off_Status.setText("OFF");


                    // COUNTING DATA - ON/OF TIME AND SWITCHED TIMES ----------------------------------------------//
                    do {

                        present = data.nextByte();   //next data input

                        if (prev != present && present == 1) {  // 0-->1 =>SWITCHED ON

                            //getting Date - Time data of that instance
                            start = Instant.now();
                            startTime = LocalTime.now().format(dtf);

                            System.out.println("Switched on AT : " + startTime);
                            On_Off_Status.setText("ON (AT " + startTime + ")");

                        } else if (prev != present && present == 0) {  //1-->0 => SWITCHED OFF

                            //Getting Date - Time data of that instance
                            end = Instant.now();
                            timeElapsed = Duration.between(start, end);
                            String date = LocalDate.now().format(dateFormat);
                            stopTime = LocalTime.now().format(dtf);

                            System.out.println("Time taken: " + timeElapsed.toSeconds() + " seconds");
                            System.out.println(date);
                            System.out.println(startTime);
                            System.out.println(stopTime);
                            switchedOnTime += timeElapsed.toSeconds();
                            System.out.println("Switched off AT : " + stopTime);

                            On_Off_Status.setText("OFF (At " + stopTime + ")");

                            // Entering data into data base -> Start -Stop Time DB
                            String TimeOnQuery = "INSERT INTO COM_START_STOP" +
                                                 " VALUES (\"" + dataPort.getSystemPortName() + "\"," + "\""
                                                    + date + "\"," + "\"" + startTime + "\"," + "\"" + stopTime + "\"" + ");";

                            int count = st.executeUpdate(TimeOnQuery);
                            System.out.println(count + " Rows Inserted");

                        }
                        prev = present;

                    } while (data.hasNextByte());       // While there is input (Data)


                } catch (Exception ex) {
                    // If Error while connecting to the port
                    System.out.println(ex + "   !!! PORT DISCONNECTED ");
                    Connected_To_Port.setText("");
                }finally {
                    dataPort.closePort();
                }

                //Updating Port OPEN/CLOSE Status
                if (dataPort.openPort()) {
                    System.out.println(dataPort + ": PORT CONNECTED");
                } else {
                    System.out.println("PORT CLOSED");
                    On_Off_Status.setText("-");
                    Connection_Status.setText("PORT CLOSED");

                }

                // Updating Main Table - Total Count Table

                if (updateQueryFlag) {

                    System.out.println("Total  Time : " + switchedOnTime + " secs");                        // Total Sum Update -----------------------}
                    String query = "UPDATE hardwareData " +                                                 // UPDATE hardwareData
                            "SET PoweredOnCount = PoweredOnCount +" + switchedOnTime +                      // SET PoweredOnCount = PoweredOnCount + '5'
                            " WHERE ID = " + "\"" + dataPort.getSystemPortName() + "\"" + ";";             // WHERE ID = "COM3" ;

                    int count = st.executeUpdate(query);
                    System.out.println(query);
                    System.out.println(count + " Rows Inserted");

                    updateQueryFlag = false;
                }
            } catch (Exception ignored) {} // Trying to get port connection
        }
    }
}