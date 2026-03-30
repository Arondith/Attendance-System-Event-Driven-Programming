package templonuevocharlesluke;

import javax.swing.JOptionPane;
import java.sql.*;
import java.awt.*;
import java.time.*;
import java.time.format.*;

public class AttendanceScannerScreen extends javax.swing.JFrame {

    private javax.swing.Timer clockTimer;

    public AttendanceScannerScreen() {
        initComponents();
        startClock();
        loadTodayEvents();
        loadAttendanceTable();
        scanner_field.requestFocusInWindow();
    }

    private void startClock() {
        clockTimer = new javax.swing.Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();
            c_date.setText(now.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
            c_time.setText(now.format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
        });
        clockTimer.start();
        LocalDateTime now = LocalDateTime.now();
        c_date.setText(now.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
        c_time.setText(now.format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
    }

    private void loadTodayEvents() {
        t_events.setText("");
        String today = LocalDate.now().toString();
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
            String sql = "SELECT event_name, start_time, end_time, grace_period FROM events WHERE start_date = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, today);
            ResultSet rs = pst.executeQuery();
            StringBuilder sb = new StringBuilder();
            boolean found = false;
            while (rs.next()) {
                found = true;
                sb.append("📌 ").append(rs.getString("event_name"))
                  .append("\n   Time: ").append(rs.getString("start_time"))
                  .append(" - ").append(rs.getString("end_time"))
                  .append("\n   Grace: ").append(rs.getString("grace_period")).append(" min\n\n");
            }
            t_events.setText(found ? sb.toString() : "No events scheduled for today.");
        } catch (Exception e) {
            t_events.setText("Could not load events.");
        }
    }

    private void loadAttendanceTable() {
        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        String today = LocalDate.now().toString();
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
            String sql = "SELECT a.studentID, s.firstName, e.event_name, a.time_in, a.status " +
                         "FROM attendance a " +
                         "LEFT JOIN students s ON a.studentID = s.studentID " +
                         "LEFT JOIN events e ON a.event_id = e.id " +
                         "WHERE DATE(a.time_in) = ? ORDER BY a.time_in DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, today);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String timeIn = rs.getString("time_in");
                String datePart = (timeIn != null && timeIn.length() >= 10) ? timeIn.substring(0, 10) : "";
                String timePart = (timeIn != null && timeIn.length() >= 19) ? timeIn.substring(11, 19) : "";
                model.addRow(new Object[]{
                    rs.getString("studentID"),
                    rs.getString("firstName"),
                    rs.getString("event_name"),
                    datePart,
                    timePart,
                    rs.getString("status")
                });
            }
        } catch (Exception e) { /* silent */ }
    }

    private void processScan() {
        String barcode = scanner_field.getText().trim();
        if (barcode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter or scan a Student ID.",
                "No Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String today     = LocalDate.now().toString();
        LocalTime nowTime = LocalTime.now();

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {

            // Step 1: Check student exists
            PreparedStatement checkStu = con.prepareStatement(
                "SELECT firstName, last_name FROM students WHERE studentID = ?");
            checkStu.setString(1, barcode);
            ResultSet stuRs = checkStu.executeQuery();

            if (!stuRs.next()) {
                lblStudentName.setText("Student not found: " + barcode);
                lblStudentName.setForeground(new Color(200, 50, 50));
                scanner_field.setText("");
                return;
            }

            String studentName = stuRs.getString("firstName") + " " +
                (stuRs.getString("last_name") != null ? stuRs.getString("last_name") : "");

            // Step 2: Get today's active event
            PreparedStatement evtPst = con.prepareStatement(
                "SELECT id, event_name, start_time, grace_period FROM events WHERE start_date = ? LIMIT 1");
            evtPst.setString(1, today);
            ResultSet evtRs = evtPst.executeQuery();

            if (!evtRs.next()) {
                JOptionPane.showMessageDialog(this, "No event scheduled for today.",
                    "No Event", JOptionPane.WARNING_MESSAGE);
                scanner_field.setText("");
                return;
            }

            int    eventId      = evtRs.getInt("id");
            String eventName    = evtRs.getString("event_name");
            String startTimeStr = evtRs.getString("start_time");
            int    gracePeriod  = evtRs.getInt("grace_period");

            // Step 3: Check duplicate
            PreparedStatement dupPst = con.prepareStatement(
                "SELECT id FROM attendance WHERE studentID = ? AND event_id = ? AND DATE(time_in) = ?");
            dupPst.setString(1, barcode);
            dupPst.setInt(2, eventId);
            dupPst.setString(3, today);
            ResultSet dupRs = dupPst.executeQuery();

            if (dupRs.next()) {
                lblStudentName.setText("⚠ " + studentName + " — Already recorded!");
                lblStudentName.setForeground(new Color(200, 120, 0));
                lblStatus.setText("DUPLICATE SCAN — not recorded");
                lblStatus.setForeground(new Color(200, 120, 0));
                scanner_field.setText("");
                return;
            }

            // Step 4: Determine on-time or late
            LocalTime startTime  = LocalTime.parse(startTimeStr);
            LocalTime cutoffTime = startTime.plusMinutes(gracePeriod);
            String attendStatus  = nowTime.isAfter(cutoffTime) ? "Late" : "Present";

            // Step 5: Record attendance
            String scanDateTime = today + " " + nowTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            PreparedStatement insPst = con.prepareStatement(
                "INSERT INTO attendance (studentID, event_id, status, time_in) VALUES (?,?,?,?)");
            insPst.setString(1, barcode);
            insPst.setInt(2, eventId);
            insPst.setString(3, attendStatus);
            insPst.setString(4, scanDateTime);
            insPst.executeUpdate();

            // Step 6: Update display
            lblStudentName.setText("✓ " + studentName);
            lblStudentName.setForeground(new Color(0, 140, 60));
            lblStatus.setText(attendStatus.equals("Late") ? "⏰ LATE" : "✅ PRESENT");
            lblStatus.setForeground(attendStatus.equals("Late") ?
                new Color(200, 80, 0) : new Color(0, 140, 60));
            lblEvent.setText("Event: " + eventName);

            loadAttendanceTable();
            scanner_field.setText("");
            scanner_field.requestFocusInWindow();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1       = new javax.swing.JPanel();
        headerPanel   = new javax.swing.JPanel();
        headerLabel   = new javax.swing.JLabel();
        accentLine    = new javax.swing.JPanel();
        jLabel3       = new javax.swing.JLabel();
        scanner_field = new javax.swing.JTextField();
        jButton1      = new javax.swing.JButton();
        jLabel5       = new javax.swing.JLabel();
        c_date        = new javax.swing.JTextField();
        jLabel4       = new javax.swing.JLabel();
        c_time        = new javax.swing.JTextField();
        lblStudentName = new javax.swing.JLabel();
        lblStatus     = new javax.swing.JLabel();
        lblEvent      = new javax.swing.JLabel();
        jLabel9       = new javax.swing.JLabel();
        jScrollPane1  = new javax.swing.JScrollPane();
        t_events      = new javax.swing.JTextArea();
        jScrollPane2  = new javax.swing.JScrollPane();
        jTable1       = new javax.swing.JTable();
        jButton2      = new javax.swing.JButton();

        jLabel1  = new javax.swing.JLabel();
        jLabel2  = new javax.swing.JLabel();
        jLabel6  = new javax.swing.JLabel();
        jLabel7  = new javax.swing.JLabel();
        jLabel8  = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2  = new javax.swing.JPanel();
        jPanel3  = new javax.swing.JPanel();
        jPanel4  = new javax.swing.JPanel();
        jPanel5  = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EDP Attendance System — Scanner");
        setMinimumSize(new Dimension(650, 760));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new Color(240, 243, 250));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // Header
        headerPanel.setBackground(new Color(8, 35, 70));
        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setText("  Attendance Scanner");
        headerPanel.add(headerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 45));
        jPanel1.add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 45));

        accentLine.setBackground(new Color(0, 190, 210));
        jPanel1.add(accentLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 45, 650, 3));

        // Date/Time card
        javax.swing.JPanel dtCard = new javax.swing.JPanel();
        dtCard.setBackground(new Color(8, 35, 70));
        dtCard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new Font("Segoe UI", Font.BOLD, 11));
        jLabel5.setForeground(new Color(140, 180, 220));
        jLabel5.setText("DATE");
        dtCard.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 8, 60, 16));

        c_date.setFont(new Font("Segoe UI", Font.BOLD, 14));
        c_date.setForeground(Color.WHITE);
        c_date.setBackground(new Color(8, 35, 70));
        c_date.setBorder(null);
        c_date.setEditable(false);
        dtCard.add(c_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 26, 220, 24));

        jLabel4.setFont(new Font("Segoe UI", Font.BOLD, 11));
        jLabel4.setForeground(new Color(140, 180, 220));
        jLabel4.setText("TIME");
        dtCard.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 8, 60, 16));

        c_time.setFont(new Font("Segoe UI", Font.BOLD, 14));
        c_time.setForeground(new Color(0, 210, 230));
        c_time.setBackground(new Color(8, 35, 70));
        c_time.setBorder(null);
        c_time.setEditable(false);
        dtCard.add(c_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 26, 180, 24));

        jPanel1.add(dtCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 630, 60));

        // Scanner card
        javax.swing.JPanel scanCard = new javax.swing.JPanel();
        scanCard.setBackground(Color.WHITE);
        scanCard.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(220, 225, 235), 1));
        scanCard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new Font("Segoe UI", Font.BOLD, 11));
        jLabel3.setForeground(new Color(8, 35, 70));
        jLabel3.setText("Scan Student Barcode / Enter Student ID:");
        scanCard.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 12, 320, 18));

        scanner_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        scanner_field.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new Color(0, 190, 210), 2),
            javax.swing.BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        scanner_field.setBackground(new Color(245, 252, 255));
        scanner_field.addActionListener(e -> processScan());
        scanCard.add(scanner_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 35, 370, 38));

        jButton1 = new javax.swing.JButton() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(0,60,110) : new Color(8,35,70));
                g2.fillRect(0,0,getWidth(),getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString("SCAN", (getWidth()-fm.stringWidth("SCAN"))/2,
                    (getHeight()+fm.getAscent()-fm.getDescent())/2);
                g2.dispose();
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        jButton1.setFocusPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setBorderPainted(false);
        jButton1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButton1.addActionListener(e -> processScan());
        scanCard.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(395, 35, 90, 38));

        lblStudentName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblStudentName.setForeground(new Color(8, 35, 70));
        lblStudentName.setText("Waiting for scan...");
        scanCard.add(lblStudentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 88, 500, 28));

        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblStatus.setForeground(new Color(140, 150, 170));
        lblStatus.setText("Status: —");
        scanCard.add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 118, 300, 22));

        lblEvent.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblEvent.setForeground(new Color(100, 110, 130));
        lblEvent.setText("Event: —");
        scanCard.add(lblEvent, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 140, 400, 18));

        jPanel1.add(scanCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 122, 630, 170));

        // Today's Events
        jLabel9.setFont(new Font("Segoe UI", Font.BOLD, 11));
        jLabel9.setForeground(new Color(8, 35, 70));
        jLabel9.setText("Today's Scheduled Events:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 304, 220, 18));

        t_events.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        t_events.setEditable(false);
        t_events.setBackground(new Color(245, 247, 252));
        t_events.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        jScrollPane1.setViewportView(t_events);
        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 325, 630, 100));

        // Attendance Table
        javax.swing.JLabel lblTable = new javax.swing.JLabel("Today's Attendance Records:");
        lblTable.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTable.setForeground(new Color(8, 35, 70));
        jPanel1.add(lblTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 435, 250, 18));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"Student ID", "Student Name", "Event", "Date", "Time", "Status"}
        ) { @Override public boolean isCellEditable(int r, int c) { return false; } });
        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        jTable1.setRowHeight(24);
        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        jTable1.getTableHeader().setBackground(new Color(8, 35, 70));
        jTable1.getTableHeader().setForeground(Color.WHITE);
        jScrollPane2.setViewportView(jTable1);
        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 456, 630, 240));

        // EXIT button
        jButton2.setFont(new Font("Segoe UI", Font.BOLD, 11));
        jButton2.setText("EXIT");
        jButton2.setFocusPainted(false);
        jButton2.addActionListener(e -> {
            if (clockTimer != null) clockTimer.stop();
            new Dashboard().setVisible(true);
            this.dispose();
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(545, 710, 90, 32));

        // Hide legacy components
        jLabel1.setVisible(false);  jPanel1.add(jLabel1,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel2.setVisible(false);  jPanel1.add(jLabel2,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel6.setVisible(false);  jPanel1.add(jLabel6,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel7.setVisible(false);  jPanel1.add(jLabel7,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel8.setVisible(false);  jPanel1.add(jLabel8,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel10.setVisible(false); jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jPanel2.setVisible(false);  jPanel1.add(jPanel2,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jPanel3.setVisible(false);  jPanel1.add(jPanel3,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jPanel4.setVisible(false);  jPanel1.add(jPanel4,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jPanel5.setVisible(false);  jPanel1.add(jPanel5,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 760));

        pack();
        setLocationRelativeTo(null);
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        if (clockTimer != null) clockTimer.stop();
        new Dashboard().setVisible(true);
        this.dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new AttendanceScannerScreen().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JPanel         accentLine;
    private javax.swing.JTextField     c_date;
    private javax.swing.JTextField     c_time;
    private javax.swing.JPanel         headerPanel;
    private javax.swing.JLabel         headerLabel;
    private javax.swing.JButton        jButton1;
    private javax.swing.JButton        jButton2;
    private javax.swing.JLabel         jLabel1;
    private javax.swing.JLabel         jLabel2;
    private javax.swing.JLabel         jLabel3;
    private javax.swing.JLabel         jLabel4;
    private javax.swing.JLabel         jLabel5;
    private javax.swing.JLabel         jLabel6;
    private javax.swing.JLabel         jLabel7;
    private javax.swing.JLabel         jLabel8;
    private javax.swing.JLabel         jLabel9;
    private javax.swing.JLabel         jLabel10;
    private javax.swing.JLabel         lblEvent;
    private javax.swing.JLabel         lblStatus;
    private javax.swing.JLabel         lblStudentName;
    private javax.swing.JPanel         jPanel1;
    private javax.swing.JPanel         jPanel2;
    private javax.swing.JPanel         jPanel3;
    private javax.swing.JPanel         jPanel4;
    private javax.swing.JPanel         jPanel5;
    private javax.swing.JScrollPane    jScrollPane1;
    private javax.swing.JScrollPane    jScrollPane2;
    private javax.swing.JTable         jTable1;
    private javax.swing.JTextField     scanner_field;
    private javax.swing.JTextArea      t_events;
    // End of variables declaration
}