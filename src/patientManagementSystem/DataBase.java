package patientManagementSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class DataBase {
        private Connection c;
        private Statement s;
        private ResultSet rs;
        public DataBase() throws ClassNotFoundException,SQLException{
                Class.forName("com.mysql.cj.jdbc.Driver");
        c=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/illuminati","root","root"); //192.168.59.237 DESKTOP-ISKJEFP
        s=c.createStatement();
        }
        public String checkPassDoc(String name) throws SQLException {
        rs=s.executeQuery("select * from doctors where username='"+name+"'");
        if(rs.next()) {
           return rs.getString(2);
        }
        return null;
        }
        public String checkPassRecep(String name) throws SQLException {
                rs=s.executeQuery("select * from receptionists where username='"+name+"'");
        if(rs.next()) {
           return rs.getString(2);
        }
        return null;
        }
        public int addPatients(JPanel mainPanel,GridBagConstraints gbc) throws SQLException {
                rs=s.executeQuery("select * from appointments");
                int x=0;
                gbc.gridx=0;
                gbc.gridy=0;
                mainPanel.add(new MyLabelTitle("PID"),gbc);
                gbc.gridx=1;
                gbc.gridy=0;
                mainPanel.add(new MyLabelTitle("Name"),gbc);
                gbc.gridx=2;
                gbc.gridy=0;
                mainPanel.add(new MyLabelTitle("Age"),gbc);
                gbc.gridx=3;
                gbc.gridy=0;
                mainPanel.add(new MyLabelTitle("Problem"),gbc);
                gbc.gridx=4;
                gbc.gridy=0;
                mainPanel.add(new MyLabelTitle("Contact"),gbc);
                gbc.gridx=5;
                gbc.gridy=0;
                mainPanel.add(new MyLabelTitle("Date/Time"),gbc);
                x++;
        while(rs.next()) {
                gbc.gridy=x;
                gbc.gridx=0;
                    mainPanel.add(new MyLabel(rs.getInt(1)+""),gbc);
                    gbc.gridx=1;
                    mainPanel.add(new MyLabel(rs.getString(2)),gbc);
                    gbc.gridx=2;
                    mainPanel.add(new MyLabel(rs.getInt(3)+""),gbc);
                    gbc.gridx=3;
                    mainPanel.add(new MyLabel(rs.getString(4)),gbc);
                    gbc.gridx=4;
                    mainPanel.add(new MyLabel(rs.getString(5)),gbc);
                    gbc.gridx=5;
                    mainPanel.add(new MyLabel(rs.getString(6)),gbc);
                x++;
        }
        return x;
        }
        public void appointmentFixure(String sql) throws SQLException {
                s.execute(sql);
        }
        public void putFeeds(String feed) throws SQLException {
                String com="insert into feedbacks values('"+feed+"')";
                s.execute(com);
        }
        public int getFeed(JPanel mainPanel,GridBagConstraints gbc) throws SQLException {
                int x=0;
                gbc.gridx=0;
                rs=s.executeQuery("select * from feedbacks");
                while(rs.next()) {
                        JLabel lab=new JLabel(rs.getString(1));
                        lab.setFont(new Font("Monospaced",Font.BOLD,20));
                        lab.setForeground(new Color(102, 0, 153));
                        gbc.gridy=x;
                        mainPanel.add(lab,gbc);
                        x++;
                }
                return x;
        }
        public int getPId() throws SQLException {
                rs=s.executeQuery("select * from appointments");
                while(rs.next()) {
                        if(rs.isLast()) {
                                return rs.getInt(1);
                        }
                }
                return 998;
         }
        public void callOutPatient() throws SQLException {
                rs=s.executeQuery("select * from appointments");
                if(!rs.next()) {
                        return;
                }
                int id=rs.getInt(1);
                System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
                VoiceManager vm=VoiceManager.getInstance();
                Voice v=vm.getVoice("kevin16");
                v.setRate(130);
                v.allocate();
                v.speak("Patient "+id);
                v.deallocate();
            }
        public String checkDateAndTime(String date, String time) throws SQLException {
                rs=s.executeQuery("select * from appointments where DateAndTime='"+date+" "+time+"'");

                if(rs.next()) {
                        System.out.println(rs.getString(6));
                        String[] h=time.split(":");
                int hrsint=Integer.parseInt(h[0]);
                int minsint=Integer.parseInt(h[1]);
                        if(minsint>=40) {
                                if(hrsint==12) {
                                        hrsint=1;
                                        minsint-=40;
                                }
                                else {
                                        hrsint++;
                                        minsint-=40;
                                }
                        }
                        else {
                                minsint+=20;
                        }
                        return checkDateAndTime(date, hrsint+":"+minsint);
                }
                else {
                        return date+" "+time;
                }
        }
        public void deletePatient() throws SQLException {
                rs=s.executeQuery("select * from appointments");
                rs.next();
                int pid=rs.getInt(1);
                s.execute("delete from appointments where Patient_ID='"+pid+"'");
        }
}
