package patientManagementSystem;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import java.sql.*;
public class PatientManagementSystem extends JFrame implements ActionListener {

        private JPanel mainPanel;
        private CardLayout card;
        private JLabel hospname;
        private JButton appointmentButton, loginAsDocButton, loginAsRecepButton, fixButton, goBackDocButton, goBackRecepButton;
        private JButton continueAsDocButton, continueAsRecepButton, addPatientButton, viewPatientsButton, contactHospButton;
        private Font buttonFont=new Font("Monospaced",Font.BOLD,20);
        private Font labelFont=new Font("Monospaced",Font.BOLD,30);
        private JTextField nameField, ageField, problemField, contactField,nameFieldDoc,nameFieldRecep;
        private JPasswordField passFieldDoc,passFieldRecep;
        private JTextField dateField, timeField;
        private DataBase db;
        private JTextArea text;
        public PatientManagementSystem() {
                try {
                        db=new DataBase();
                } catch (Exception e) {

                }
                this.setTitle("Patient Management System");
                this.setSize(700,700);
                this.setLocationRelativeTo(null);
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setVisible(true);
                this.setLayout(new BorderLayout());
                ImageIcon icon=new ImageIcon("Logo.png");
                hospname=new JLabel("Illuminati Health Care",icon,JLabel.CENTER);
                hospname.setFont(new Font("Monospaced",Font.BOLD | Font.ITALIC,50));
                hospname.setForeground(new Color(255,204,51));
                hospname.setHorizontalTextPosition(JLabel.RIGHT);
                this.add(hospname,BorderLayout.NORTH);
                card=new CardLayout();
                mainPanel=new JPanel(card);
                mainPanel.add(this.homePage(),"Home");
                mainPanel.add(this.appointmentPage(),"Appointment");
                mainPanel.add(this.loginAsDoctorPage(),"LoginDoc");
                mainPanel.add(this.loginAsReceptionistPage(),"LoginRecep");
                mainPanel.add(this.recepMainPage(),"MainRecep");
                mainPanel.add(this.docMainPage(),"MainDoc");
                mainPanel.add(this.contactPage(),"Contact");
                mainPanel.add(this.feedbackPage(),"Feedback");
                this.add(mainPanel,BorderLayout.CENTER);
                card.show(mainPanel, "Home");
        }
        public void actionPerformed(ActionEvent e) {
                if(e.getSource()==appointmentButton || e.getActionCommand().equals("Add Patient")) {
                        card.show(mainPanel, "Appointment");
                }
                else if(e.getActionCommand().equals("CallOut")) {
                        try {
                                db.callOutPatient();
                        } catch (SQLException e1) {
                        } 
                }
                else if(this.goBackDocButton==e.getSource()) {
                        card.show(mainPanel, "MainDoc");
                }
                else if(this.goBackRecepButton==e.getSource()) {
                        card.show(mainPanel, "MainRecep");
                }
                else if(e.getActionCommand().equals("Send Feedback")) {
                        card.show(mainPanel, "Feedback");
                }
                else if(e.getActionCommand().equals("Submit")) {
                        this.getFeedback();
                }
                else if(e.getSource()==loginAsDocButton) {
                        card.show(mainPanel, "LoginDoc");
                }
                else if(e.getActionCommand().equals("View FeedBacks")) {
                        mainPanel.add(this.viewFeedPage(),"ViewFeeds");
                        card.show(mainPanel, "ViewFeeds");
                }
                else if(e.getSource()==loginAsRecepButton) {
                        card.show(mainPanel, "LoginRecep");
                }
                else if(e.getSource()==fixButton ) {
                        this.fixAppointment();
                }
                else if(e.getActionCommand().equals("Go to Home")) {
                        nameField.setText("");
                        ageField.setText("");
                        problemField.setText("");
                        contactField.setText("");
                        nameFieldRecep.setText("");
                        passFieldRecep.setText("");
                        nameFieldDoc.setText("");
                        passFieldDoc.setText("");
                        dateField.setText("");
                        timeField.setText("");
                        text.setText("");
                        card.show(mainPanel, "Home");
                }
                else if(e.getActionCommand().equals("Consulted")) {
                        try {
                                db.deletePatient();
                        } catch (Exception e1) {

                        }
                        mainPanel.add(this.patientQueuePage(),"Queue");
                        card.show(mainPanel, "Queue");
                }
                else if(e.getActionCommand().equals("Absent")) {
                        try {
                                db.deletePatient();
                        } catch (Exception e1) {

                        }
                        mainPanel.add(this.patientListPage(),"PatientList");
                        card.show(mainPanel, "PatientList");
                }
                else if(e.getSource()==continueAsRecepButton) {
                        this.recepLogIn();
                }
                else if(e.getSource()==continueAsDocButton) {
                        this.docLogIn();
                }
                else if(e.getSource()==addPatientButton) {
                        card.show(mainPanel, "Appointment");
                }
                else if(e.getSource()==viewPatientsButton) {
                        card.show(mainPanel, "Records");
                }
                else if(e.getSource()==contactHospButton) {
                        card.show(mainPanel, "Contact");
                }
                else if(e.getActionCommand().equals("Patient List")) {
                        mainPanel.add(this.patientListPage(),"PatientList");
                        card.show(mainPanel,"PatientList");
                }
                else if(e.getActionCommand().equals("Patient Queue")) {
                        mainPanel.add(this.patientQueuePage(),"Queue");
                        card.show(mainPanel, "Queue");
                }

        }
        private void fixAppointment()  {
                String name=nameField.getText();
                String age=ageField.getText();
                String problem=problemField.getText();
                String contact=contactField.getText();
                String date=dateField.getText();
                String time=timeField.getText();
                String dnt=date+" "+time;
                int id=999;
                try {
                        id=db.getPId();
                        id++;
                } catch (Exception e) {

                }
                try {
                        dnt=db.checkDateAndTime(date,time);
                } catch (Exception e) {

                }
                try {
                        db.appointmentFixure("insert into appointments values("+id+",'"+name+"',"+age+",'"+problem+"','"+contact+"','"+dnt+"')");
                        JOptionPane.showMessageDialog(new JFrame(), "on "+dnt+"\nYour ID:"+id,"Appointment Fixed",JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(new JFrame(), "Please contact the Illuminatis, Hospital server is busy","Appointment Not Fixed",JOptionPane.ERROR_MESSAGE);
                }
        }
        private JPanel homePage() {
                JPanel mainPanel=new JPanel(new GridBagLayout());
                appointmentButton=new JButton("Get Appointment");
                appointmentButton.setFont(buttonFont);
                appointmentButton.setBackground(Color.MAGENTA);
                appointmentButton.addActionListener(this);
                loginAsDocButton=new JButton("Login As Doctor");
                loginAsDocButton.setFont(buttonFont);
                loginAsDocButton.setBackground(Color.MAGENTA);
                loginAsDocButton.addActionListener(this);
                loginAsRecepButton=new JButton("Login As Receptionist");
                loginAsRecepButton.setFont(buttonFont);
                loginAsRecepButton.setBackground(Color.MAGENTA);
                loginAsRecepButton.addActionListener(this);
                contactHospButton=new JButton("Contact Hospital");
                contactHospButton.setFont(buttonFont);
                contactHospButton.setBackground(Color.MAGENTA);
                contactHospButton.addActionListener(this);
                JButton feedbackButton=new JButton("Send Feedback");
                feedbackButton.setFont(buttonFont);
                feedbackButton.setBackground(Color.MAGENTA);
                feedbackButton.addActionListener(this);
                GridBagConstraints gbc=new GridBagConstraints();
                gbc.insets=new Insets(10,10,10,10);
                gbc.gridx=0;
                gbc.gridy=0;
                mainPanel.add(appointmentButton,gbc);
                gbc.gridy=1;
                mainPanel.add(loginAsDocButton,gbc);
                gbc.gridy=2;
                mainPanel.add(loginAsRecepButton,gbc);
                gbc.gridy=3;
                mainPanel.add(contactHospButton,gbc);
                gbc.gridy=4;
                mainPanel.add(feedbackButton,gbc);
                mainPanel.setBackground(Color.GRAY);
                return mainPanel;
        }
        private void getFeedback() {
                String feed=text.getText();
                try {
                        db.putFeeds(feed);
                        JOptionPane.showMessageDialog(new JFrame(),"Thanks for submitting your feedback","FeedBack Submission",JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException e) {
                        JOptionPane.showMessageDialog(new JFrame(),"Sorry,Hospital Server is Busy","FeedBack Submission",JOptionPane.ERROR_MESSAGE);
                }
        }
        private JPanel appointmentPage() {
                JPanel mainPanel=new JPanel(new GridBagLayout());
                GridBagConstraints gbc=new GridBagConstraints();
                gbc.insets=new Insets(10,10,10,10);
                JLabel nameLabel=new JLabel();
                nameLabel.setFont(labelFont);
                nameLabel.setText("Name");
                nameLabel.setForeground(new Color(102, 0, 153));
                gbc.gridx=0;
                gbc.gridy=0;
                mainPanel.add(nameLabel,gbc);
                nameField=new JTextField(20);
                gbc.gridx=1;
                gbc.gridy=0;
                mainPanel.add(nameField,gbc);
                JLabel ageLabel=new JLabel();
                ageLabel.setFont(labelFont);
                ageLabel.setText("Age");
                ageLabel.setForeground(new Color(102, 0, 153));
                gbc.gridx=0;
                gbc.gridy=1;
                mainPanel.add(ageLabel,gbc);
                ageField=new JTextField(20);
                gbc.gridx=1;
                gbc.gridy=1;
                mainPanel.add(ageField,gbc);
                JLabel problemLabel=new JLabel();
                problemLabel.setFont(labelFont);
                problemLabel.setText("Problem");
                problemLabel.setForeground(new Color(102, 0, 153));
                gbc.gridx=0;
                gbc.gridy=2;
                mainPanel.add(problemLabel,gbc);
                problemField=new JTextField(20);
                gbc.gridx=1;
                gbc.gridy=2;
                mainPanel.add(problemField,gbc);
                JLabel contactLabel=new JLabel();
                contactLabel.setFont(labelFont);
                contactLabel.setText("Phone Number");
                contactLabel.setForeground(new Color(102, 0, 153));
                gbc.gridx=0;
                gbc.gridy=3;
                mainPanel.add(contactLabel,gbc);
                contactField=new JTextField(20);
                gbc.gridx=1;
                gbc.gridy=3;
                mainPanel.add(contactField,gbc);
                JLabel dateLabel=new JLabel();
                dateLabel.setFont(labelFont);
                dateLabel.setText("Date(dd/mm/yyyy)");
                dateLabel.setForeground(new Color(102, 0, 153));
                gbc.gridx=0;
                gbc.gridy=4;
                mainPanel.add(dateLabel,gbc);
                dateField=new JTextField(20);
                gbc.gridx=1;
                gbc.gridy=4;
                mainPanel.add(dateField,gbc);
                JLabel timeLabel=new JLabel();
                timeLabel.setFont(labelFont);
                timeLabel.setText("Time(hh:mm)");
                timeLabel.setForeground(new Color(102, 0, 153));
                gbc.gridx=0;
                gbc.gridy=5;
                mainPanel.add(timeLabel,gbc);
                timeField=new JTextField(20);
                gbc.gridx=1;
                gbc.gridy=5;
                mainPanel.add(timeField,gbc);
                fixButton=new JButton("Fix Appointment");
                fixButton.setFont(buttonFont);
                fixButton.setBackground(Color.MAGENTA);
                fixButton.addActionListener(this);
                gbc.gridx=1;
                gbc.gridy=6;
                mainPanel.add(fixButton,gbc);
                JButton homeButton=new JButton("Go to Home");
                homeButton.setFont(buttonFont);
                homeButton.setBackground(Color.MAGENTA);
                homeButton.addActionListener(this);
                gbc.gridx=1;
                gbc.gridy=7;
                mainPanel.add(homeButton,gbc);
                mainPanel.setBackground(Color.GRAY);
                return mainPanel;
        }
        private JPanel viewFeedPage() { //back
                JPanel mainPanel=new JPanel(new GridBagLayout());
                int x=0;
                GridBagConstraints gbc=new GridBagConstraints();
                gbc.insets=new Insets(10,10,10,10);
                try {
                        x=db.getFeed(mainPanel,gbc);
                } catch (SQLException e) {

                }
                gbc.gridy=x;
                JButton homeButton=new JButton("Go to Home");
                homeButton.setFont(buttonFont);
                homeButton.setBackground(Color.MAGENTA);
                homeButton.addActionListener(this);
                mainPanel.add(homeButton,gbc);
                mainPanel.setBackground(Color.GRAY);
                return mainPanel;
        }
        private JPanel loginAsDoctorPage() {
                JPanel mainPanel=new JPanel(new GridBagLayout());
                GridBagConstraints gbc=new GridBagConstraints();
                gbc.insets=new Insets(10,10,10,10);
                JLabel userNameLabel=new JLabel();
                userNameLabel.setFont(labelFont);
                userNameLabel.setText("UserName");
                userNameLabel.setForeground(new Color(102, 0, 153));
                gbc.gridx=0;
                gbc.gridy=0;
                mainPanel.add(userNameLabel,gbc);
                nameFieldDoc=new JTextField(20);
                gbc.gridx=1;
                gbc.gridy=0;
                mainPanel.add(nameFieldDoc,gbc);
                JLabel passLabel=new JLabel();
                passLabel.setFont(labelFont);
                passLabel.setText("Password");
                passLabel.setForeground(new Color(102, 0, 153));
                gbc.gridx=0;
                gbc.gridy=1;
                mainPanel.add(passLabel,gbc);
                passFieldDoc=new JPasswordField(20);
                gbc.gridx=1;
                gbc.gridy=1;
                mainPanel.add(passFieldDoc,gbc);
                continueAsDocButton=new JButton("Continue As Doctor");
                continueAsDocButton.setFont(buttonFont);
                continueAsDocButton.setBackground(Color.MAGENTA);
                continueAsDocButton.addActionListener(this);
                gbc.gridx=1;
                gbc.gridy=2;
                mainPanel.add(continueAsDocButton,gbc);
                JButton homeButton=new JButton("Go to Home");
                homeButton.setFont(buttonFont);
                homeButton.setBackground(Color.MAGENTA);
                homeButton.addActionListener(this);
                gbc.gridx=1;
                gbc.gridy=4;
                mainPanel.add(homeButton,gbc);
                mainPanel.setBackground(Color.GRAY);
                return mainPanel;
        }
        private JPanel loginAsReceptionistPage() {
                JPanel mainPanel=new JPanel(new GridBagLayout());
                GridBagConstraints gbc=new GridBagConstraints();
                gbc.insets=new Insets(10,10,10,10);
                JLabel userNameLabel=new JLabel();
                userNameLabel.setFont(labelFont);
                userNameLabel.setText("UserName");
                userNameLabel.setForeground(new Color(102, 0, 153));
                gbc.gridx=0;
                gbc.gridy=0;
                mainPanel.add(userNameLabel,gbc);
                nameFieldRecep=new JTextField(20);
                gbc.gridx=1;
                gbc.gridy=0;
                mainPanel.add(nameFieldRecep,gbc);
                JLabel passLabel=new JLabel();
                passLabel.setFont(labelFont);
                passLabel.setText("Password");
                passLabel.setForeground(new Color(102, 0, 153));
                gbc.gridx=0;
                gbc.gridy=1;
                mainPanel.add(passLabel,gbc);
                passFieldRecep=new JPasswordField(20);
                gbc.gridx=1;
                gbc.gridy=1;
                mainPanel.add(passFieldRecep,gbc);
                continueAsRecepButton=new JButton("Continue as Receptionist");
                continueAsRecepButton.setFont(buttonFont);
                continueAsRecepButton.setBackground(Color.MAGENTA);
                continueAsRecepButton.addActionListener(this);
                gbc.gridx=1;
                gbc.gridy=2;
                mainPanel.add(continueAsRecepButton,gbc);
                JButton homeButton=new JButton("Go to Home");
                homeButton.setFont(buttonFont);
                homeButton.setBackground(Color.MAGENTA);
                homeButton.addActionListener(this);
                gbc.gridx=1;
                gbc.gridy=4;
                mainPanel.add(homeButton,gbc);
                mainPanel.setBackground(Color.GRAY);
                return mainPanel;
        }
        private JPanel recepMainPage() {
                JPanel mainPanel=new JPanel(new GridBagLayout());
                GridBagConstraints gbc=new GridBagConstraints();
                gbc.insets=new Insets(10,10,10,10);
                JButton addPatientButton=new JButton("Add Patient");
                addPatientButton.setFont(buttonFont);
                addPatientButton.setBackground(Color.MAGENTA);
                addPatientButton.addActionListener(this);
                gbc.gridx=0;
                gbc.gridy=0;
                mainPanel.add(addPatientButton,gbc);
                JButton patientListButton=new JButton("Patient List");
                patientListButton.setFont(buttonFont);
                patientListButton.setBackground(Color.MAGENTA);
                patientListButton.addActionListener(this);
                gbc.gridy=1;
                mainPanel.add(patientListButton,gbc);
                JButton viewFeedButton=new JButton("View FeedBacks");
                viewFeedButton.setFont(buttonFont);
                viewFeedButton.setBackground(Color.MAGENTA);
                viewFeedButton.addActionListener(this);
                gbc.gridy=2;
                mainPanel.add(viewFeedButton,gbc);
                JButton homeButton=new JButton("Go to Home");
                homeButton.setFont(buttonFont);
                homeButton.setBackground(Color.MAGENTA);
                homeButton.addActionListener(this);
                gbc.gridy=3;
                mainPanel.add(homeButton,gbc);
                mainPanel.setBackground(Color.GRAY);
                return mainPanel;
        }
        private JPanel docMainPage() {
                JPanel mainPanel=new JPanel(new GridBagLayout());
                GridBagConstraints gbc=new GridBagConstraints();
                gbc.insets=new Insets(10,10,10,10);
                JButton patientQueueButton=new JButton("Patient Queue");
                patientQueueButton.setFont(buttonFont);
                patientQueueButton.setBackground(Color.MAGENTA);
                patientQueueButton.addActionListener(this);
                gbc.gridx=0;
                gbc.gridy=0;
                mainPanel.add(patientQueueButton,gbc);
                JButton viewFeedButton=new JButton("View FeedBacks");
                viewFeedButton.setFont(buttonFont);
                viewFeedButton.setBackground(Color.MAGENTA);
                viewFeedButton.addActionListener(this);
                gbc.gridy=1;
                mainPanel.add(viewFeedButton,gbc);
                JButton homeButton=new JButton("Go to Home");
                homeButton.setFont(buttonFont);
                homeButton.setBackground(Color.MAGENTA);
                homeButton.addActionListener(this);
                gbc.gridy=2;
                mainPanel.add(homeButton,gbc);
                mainPanel.setBackground(Color.GRAY);
                return mainPanel;
        }
        private JPanel patientQueuePage() { //back
                JPanel mainPanel=new JPanel(new GridBagLayout());
                GridBagConstraints gbc=new GridBagConstraints();
                gbc.insets=new Insets(10,10,10,10);
                int x=0;
                try {
                        x=db.addPatients(mainPanel,gbc);
                } catch (Exception e) {
                }
                gbc.gridx=3;
                gbc.gridy=x++;
                JButton calloutButton=new JButton("CallOut");
                calloutButton.setFont(buttonFont);
                calloutButton.setBackground(Color.MAGENTA);
                calloutButton.addActionListener(this);
                mainPanel.add(calloutButton,gbc);
                gbc.gridy=x++;
                JButton consultButton=new JButton("Consulted");
                consultButton.setFont(buttonFont);
                consultButton.setBackground(Color.MAGENTA);
                consultButton.addActionListener(this);
                mainPanel.add(consultButton,gbc);
                gbc.gridy=x++;
                goBackDocButton=new JButton("Back");
                goBackDocButton.setFont(buttonFont);
                goBackDocButton.setBackground(Color.MAGENTA);
                goBackDocButton.addActionListener(this);
                mainPanel.add(goBackDocButton,gbc);
                gbc.gridy=x++;
                JButton homeButton=new JButton("Go to Home");
                homeButton.setFont(buttonFont);
                homeButton.setBackground(Color.MAGENTA);
                homeButton.addActionListener(this);
                mainPanel.add(homeButton,gbc);
                mainPanel.setBackground(Color.GRAY);
                return mainPanel;
        }
        private JPanel patientListPage() { //back
                JPanel mainPanel=new JPanel(new GridBagLayout());
                GridBagConstraints gbc=new GridBagConstraints();
                gbc.insets=new Insets(10,10,10,10);
                int x=0;
                try {
                        x=db.addPatients(mainPanel,gbc);
                } catch (Exception e) {
                }
                gbc.gridy=x;
                JButton absentButton=new JButton("Absent");
                absentButton.setFont(buttonFont);
                absentButton.setBackground(Color.MAGENTA);
                absentButton.addActionListener(this);
                mainPanel.add(absentButton,gbc);
                gbc.gridy=++x;
                goBackRecepButton=new JButton("Back");
                goBackRecepButton.setFont(buttonFont);
                goBackRecepButton.setBackground(Color.MAGENTA);
                goBackRecepButton.addActionListener(this);
                mainPanel.add(goBackRecepButton,gbc);
                gbc.gridy=++x;
                JButton homeButton=new JButton("Go to Home");
                homeButton.setFont(buttonFont);
                homeButton.setBackground(Color.MAGENTA);
                homeButton.addActionListener(this);
                mainPanel.add(homeButton,gbc);
                mainPanel.setBackground(Color.GRAY);
                return mainPanel;
        }
        private JPanel contactPage() {
                JPanel mainPanel=new JPanel(new GridBagLayout());
                GridBagConstraints gbc=new GridBagConstraints();
                gbc.insets=new Insets(10,10,10,10);
                JLabel phoneLabel=new JLabel();
                phoneLabel.setText("Phone");
                phoneLabel.setFont(labelFont);
                phoneLabel.setForeground(new Color(102, 0, 153));
                gbc.gridx=0;
                gbc.gridy=0;
                mainPanel.add(phoneLabel,gbc);
                JLabel numberLabel=new JLabel();
                numberLabel.setText("044 23443 23342");
                numberLabel.setFont(labelFont);
                numberLabel.setForeground(new Color(51, 51, 51));
                gbc.gridx=1;
                gbc.gridy=0;
                mainPanel.add(numberLabel,gbc);
                JLabel instagramLabel=new JLabel();
                instagramLabel.setText("Instagram");
                instagramLabel.setFont(labelFont);
                instagramLabel.setForeground(new Color(102, 0, 153));
                gbc.gridx=0;
                gbc.gridy=1;
                mainPanel.add(instagramLabel,gbc);
                JLabel userNameLabel=new JLabel();
                userNameLabel.setText("batman");
                userNameLabel.setFont(labelFont);
                userNameLabel.setForeground(new Color(51, 51, 51));
                gbc.gridx=1;
                gbc.gridy=1;
                mainPanel.add(userNameLabel,gbc);
                JLabel facebookLabel=new JLabel();
                facebookLabel.setText("Facebook");
                facebookLabel.setFont(labelFont);
                facebookLabel.setForeground(new Color(102, 0, 153));
                gbc.gridx=0;
                gbc.gridy=2;
                mainPanel.add(facebookLabel,gbc);
                JLabel fbuserLabel=new JLabel();
                fbuserLabel.setText("brucewayne");
                fbuserLabel.setFont(labelFont);
                fbuserLabel.setForeground(new Color(51, 51, 51));
                gbc.gridx=1;
                gbc.gridy=2;
                mainPanel.add(fbuserLabel,gbc);
                JLabel mailLabel=new JLabel();
                mailLabel.setText("E-Mail");
                mailLabel.setFont(labelFont);
                mailLabel.setForeground(new Color(102, 0, 153));
                gbc.gridx=0;
                gbc.gridy=3;
                mainPanel.add(mailLabel,gbc);
                JLabel mailIdLabel=new JLabel();
                mailIdLabel.setText("ihcu@outlook.com");
                mailIdLabel.setFont(labelFont);
                mailIdLabel.setForeground(new Color(51, 51, 51));
                gbc.gridx=1;
                gbc.gridy=3;
                mainPanel.add(mailIdLabel,gbc);
                JButton homeButton=new JButton("Go to Home");
                homeButton.setFont(buttonFont);
                homeButton.setBackground(Color.MAGENTA);
                homeButton.addActionListener(this);
                gbc.gridx=1;
                gbc.gridy=4;
                mainPanel.add(homeButton,gbc);
                mainPanel.setBackground(Color.GRAY);
                return mainPanel;
        }
        private void docLogIn() {
                String name=nameFieldDoc.getText();
                String pass=passFieldDoc.getText();
                String cpass=null;
                try {
                        cpass=db.checkPassDoc(name);
                } catch (Exception e) {

                }
                if(cpass!=null) {
                        if(cpass.equals(pass)) {
                                card.show(mainPanel, "MainDoc");
                        }
                        else {
                                JOptionPane.showMessageDialog(new JFrame(), "You have entered a wrong password","Login Failed",JOptionPane.ERROR_MESSAGE);
                        }
                }
                else {
                        JOptionPane.showMessageDialog(new JFrame(), "No such user found","Login Failed",JOptionPane.ERROR_MESSAGE);
                }
        }
        private void recepLogIn() {
                String name=nameFieldRecep.getText();
                String pass=passFieldRecep.getText();
                String cpass=null;
                try {
                        cpass=db.checkPassRecep(name);
                } catch (Exception e) {

                }
                if(cpass!=null) {
                        if(cpass.equals(pass)) {
                                card.show(mainPanel, "MainRecep");
                        }
                        else {
                                JOptionPane.showMessageDialog(new JFrame(), "You have entered a wrong password","Login Failed",JOptionPane.ERROR_MESSAGE);
                        }
                }
                else {
                        JOptionPane.showMessageDialog(new JFrame(), "No such user found","Login Failed",JOptionPane.ERROR_MESSAGE);
                }
        }
        private JPanel feedbackPage() {
                JPanel mainPanel=new JPanel(new GridBagLayout());
                GridBagConstraints gbc=new GridBagConstraints();
                gbc.insets=new Insets(10,10,10,10);
                JLabel writeLabel=new JLabel();
                writeLabel.setText("Write you FeedBack Here");
                writeLabel.setFont(new Font("Monospaced",Font.BOLD,20));
                writeLabel.setForeground(new Color(102, 0, 153));
                gbc.gridx=0;
                gbc.gridy=0;
                mainPanel.add(writeLabel,gbc);
                text=new JTextArea(10,50);
                Border b=BorderFactory.createLineBorder(Color.MAGENTA, 5);
                text.setBorder(b);
                gbc.gridy=1;
                mainPanel.add(text,gbc);
                JButton submitButton=new JButton("Submit");
                submitButton.setFont(buttonFont);
                submitButton.setBackground(Color.MAGENTA);
                submitButton.addActionListener(this);
                gbc.gridy=2;
                mainPanel.add(submitButton,gbc);
                JButton homeButton=new JButton("Go to Home");
                homeButton.setFont(buttonFont);
                homeButton.setBackground(Color.MAGENTA);
                homeButton.addActionListener(this);
                gbc.gridy=3;
                mainPanel.add(homeButton,gbc);
                mainPanel.setBackground(Color.GRAY);
                return mainPanel;
        }
        public static void main(String[] args) {
                new PatientManagementSystem();
        }

}