package templonuevocharlesluke;

import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.geom.*;

public class Dashboard extends javax.swing.JFrame {

    // Pie chart data — Attendance status
    private int[] attendanceCounts = new int[3];
    private final String[] attendanceLabels = {"Present", "Late", "Absent"};
    private final Color[] attendanceColors = {
        new Color(46, 204, 113),
        new Color(230, 126, 34),
        new Color(231, 76, 60)
    };

    // Bar chart data — Students per course
    private java.util.List<String> courseNames = new java.util.ArrayList<>();
    private java.util.List<Integer> courseCounts = new java.util.ArrayList<>();
    private final Color[] barColors = {
        new Color(52,152,219), new Color(46,204,113), new Color(155,89,182),
        new Color(230,126,34), new Color(231,76,60),  new Color(26,188,156),
        new Color(241,196,15), new Color(52,73,94)
    };

    // Summary cards
    private int[] counts = new int[6];
    private final String[] labels = {"Colleges","Departments","Students","Courses","Users","Events"};
    private final Color[] chartColors = {
        new Color(52,152,219), new Color(46,204,113),
        new Color(155,89,182), new Color(230,126,34),
        new Color(231,76,60),  new Color(26,188,156)
    };

    public Dashboard(String userRole) {
        initComponents();
         setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); // ← add this
        loadStudents();
        loadChartData();
        if (!userRole.equalsIgnoreCase("Super Admin")) {
            AddUser.setVisible(false);
        }
        setLocationRelativeTo(null);
    }

    public Dashboard() {
        initComponents();
         setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); // ← add this
        loadStudents();
        loadChartData();
        setLocationRelativeTo(null);
    }

    private void loadChartData() {
        // Summary cards
        String[] tables = {"colleges","departments","students","courses","users","events"};
        for (int i = 0; i < tables.length; i++) {
            final int idx = i;
            final String tbl = tables[i];
            new Thread(() -> {
                try {
                    java.sql.Connection c = java.sql.DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/attendance_system","root","");
                    java.sql.ResultSet rs = c.prepareStatement(
                        "SELECT COUNT(*) FROM " + tbl).executeQuery();
                    if (rs.next()) counts[idx] = rs.getInt(1);
                    c.close();
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        pieChartPanel.repaint();
                        barChartPanel.repaint();
                    });
                } catch (Exception e) { counts[idx] = 0; }
            }).start();
        }

        // Pie chart — Attendance status
        new Thread(() -> {
            try {
                java.sql.Connection c = java.sql.DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/attendance_system","root","");

                java.sql.ResultSet rs = c.prepareStatement(
                    "SELECT COUNT(*) FROM attendance WHERE status = 'Present'").executeQuery();
                if (rs.next()) attendanceCounts[0] = rs.getInt(1);

                rs = c.prepareStatement(
                    "SELECT COUNT(*) FROM attendance WHERE status = 'Late'").executeQuery();
                if (rs.next()) attendanceCounts[1] = rs.getInt(1);

                rs = c.prepareStatement(
                    "SELECT COUNT(*) FROM students WHERE studentID NOT IN " +
                    "(SELECT DISTINCT studentID FROM attendance)").executeQuery();
                if (rs.next()) attendanceCounts[2] = rs.getInt(1);

                c.close();
                javax.swing.SwingUtilities.invokeLater(() -> pieChartPanel.repaint());
            } catch (Exception e) {
                System.out.println("Pie chart error: " + e.getMessage());
            }
        }).start();

        // Bar chart — Students per course
        new Thread(() -> {
            try {
                java.sql.Connection c = java.sql.DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/attendance_system","root","");
                java.sql.ResultSet rs = c.prepareStatement(
                    "SELECT course, COUNT(*) as total FROM students " +
                    "WHERE course IS NOT NULL AND course != '' " +
                    "GROUP BY course ORDER BY total DESC LIMIT 8").executeQuery();

                courseNames.clear();
                courseCounts.clear();
                while (rs.next()) {
                    courseNames.add(rs.getString("course"));
                    courseCounts.add(rs.getInt("total"));
                }
                c.close();
                javax.swing.SwingUtilities.invokeLater(() -> barChartPanel.repaint());
            } catch (Exception e) {
                System.out.println("Bar chart error: " + e.getMessage());
            }
        }).start();
    }

    private void loadStudents() {
        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
        try {
            java.sql.Connection con = java.sql.DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system","root","");
            java.sql.ResultSet rs = con.prepareStatement(
                "SELECT studentID, firstName, course, student_type FROM students").executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("studentID"), rs.getString("firstName"),
                    rs.getString("course"),    rs.getString("student_type")
                });
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Load students error: " + e.getMessage());
        }
    }

    private void drawPieChart(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int total = 0;
        for (int c : attendanceCounts) total += c;

        int pw = pieChartPanel.getWidth();
        int ph = pieChartPanel.getHeight();
        int diameter = Math.min(pw - 180, ph - 30);
        int x = 20, y = (ph - diameter) / 2;

        if (total == 0) {
            g2.setColor(new Color(200,200,200));
            g2.fillOval(x, y, diameter, diameter);
            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            g2.drawString("No attendance data yet", x + diameter/2 - 60, y + diameter/2);
            return;
        }

        double startAngle = 0;
        for (int i = 0; i < attendanceCounts.length; i++) {
            if (attendanceCounts[i] == 0) continue;
            double angle = 360.0 * attendanceCounts[i] / total;
            g2.setColor(attendanceColors[i]);
            g2.fill(new Arc2D.Double(x, y, diameter, diameter, startAngle, angle, Arc2D.PIE));
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2));
            g2.draw(new Arc2D.Double(x, y, diameter, diameter, startAngle, angle, Arc2D.PIE));

            double midAngle = Math.toRadians(startAngle + angle / 2);
            int lx = (int)(x + diameter/2 + (diameter/2 * 0.6) * Math.cos(midAngle));
            int ly = (int)(y + diameter/2 - (diameter/2 * 0.6) * Math.sin(midAngle));
            int pct = (int)Math.round(100.0 * attendanceCounts[i] / total);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
            g2.drawString(pct + "%", lx - 10, ly + 5);

            startAngle += angle;
        }

        int legendX = x + diameter + 20;
        int legendY = y + (diameter / 2) - (attendanceLabels.length * 28 / 2);
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        for (int i = 0; i < attendanceLabels.length; i++) {
            g2.setColor(attendanceColors[i]);
            g2.fillRoundRect(legendX, legendY + i*32, 16, 16, 4, 4);
            g2.setColor(new Color(40,40,60));
            g2.drawString(attendanceLabels[i] + ": " + attendanceCounts[i],
                legendX + 24, legendY + i*32 + 13);
        }
    }

    private void drawBarChart(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int pw = barChartPanel.getWidth();
        int ph = barChartPanel.getHeight();
        int padL = 50, padB = 55, padT = 20, padR = 20;
        int chartW = pw - padL - padR;
        int chartH = ph - padT - padB;

        g2.setColor(new Color(240,243,250));
        g2.fillRect(padL, padT, chartW, chartH);

        if (courseCounts.isEmpty()) {
            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            g2.drawString("No course data yet", padL + chartW/2 - 55, padT + chartH/2);
            return;
        }

        int maxVal = courseCounts.stream().mapToInt(Integer::intValue).max().orElse(1);
        if (maxVal == 0) maxVal = 1;

        int gridLines = 5;
        for (int i = 0; i <= gridLines; i++) {
            int gy = padT + chartH - (chartH * i / gridLines);
            g2.setColor(new Color(210,215,225));
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10, new float[]{4}, 0));
            g2.drawLine(padL, gy, padL + chartW, gy);
            g2.setColor(new Color(100,110,130));
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            g2.setStroke(new BasicStroke(1));
            g2.drawString(String.valueOf(maxVal * i / gridLines), padL - 30, gy + 4);
        }

        g2.setStroke(new BasicStroke(1));
        int n = courseCounts.size();
        int spacing = chartW / n;
        int barW = Math.max(20, spacing - 16);

        for (int i = 0; i < n; i++) {
            int barH = (int)((double) courseCounts.get(i) / maxVal * chartH);
            int bx = padL + i * spacing + (spacing - barW) / 2;
            int by = padT + chartH - barH;

            g2.setColor(new Color(0,0,0,30));
            g2.fillRoundRect(bx+3, by+3, barW, barH, 6, 6);

            g2.setColor(barColors[i % barColors.length]);
            g2.fillRoundRect(bx, by, barW, barH, 6, 6);

            g2.setColor(new Color(40,40,60));
            g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
            String val = String.valueOf(courseCounts.get(i));
            int valW = g2.getFontMetrics().stringWidth(val);
            g2.drawString(val, bx + (barW - valW)/2, by - 4);

            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            String name = courseNames.get(i);
            if (name.length() > 8) name = name.substring(0, 7) + "…";
            int lw = g2.getFontMetrics().stringWidth(name);
            g2.drawString(name, bx + (barW - lw)/2, padT + chartH + 16);
        }

        g2.setColor(new Color(180,185,200));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawLine(padL, padT, padL, padT + chartH);
        g2.drawLine(padL, padT + chartH, padL + chartW, padT + chartH);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1        = new javax.swing.JPanel();
        jPanel2        = new javax.swing.JPanel();
        jLabel1        = new javax.swing.JButton();
        jLabel2        = new javax.swing.JLabel();
        jLabel4        = new javax.swing.JLabel();
        jLabel5        = new javax.swing.JLabel();
        jLabel6        = new javax.swing.JLabel();
        jLabel7        = new javax.swing.JButton();
        jLabel8        = new javax.swing.JLabel();
        jLabel9        = new javax.swing.JButton();
        jLabel10       = new javax.swing.JButton();
        jLabel11       = new javax.swing.JButton();
        jLabel12       = new javax.swing.JLabel();
        jLabel13       = new javax.swing.JButton();
        jLabel14       = new javax.swing.JLabel();
        jLabel15       = new javax.swing.JLabel();
        jLabel16       = new javax.swing.JLabel();
        jLabel17       = new javax.swing.JLabel();
        jLabel18       = new javax.swing.JLabel();
        AddUser        = new javax.swing.JButton();
        collegesButton = new javax.swing.JButton();
        DepartmentButton = new javax.swing.JButton();
        CoursesForm    = new javax.swing.JButton();
        CoursesForm2   = new javax.swing.JButton();
        CoursesForm3   = new javax.swing.JButton();
        CoursesForm4   = new javax.swing.JButton();
        CoursesForm5   = new javax.swing.JButton();
        CoursesForm6   = new javax.swing.JButton();
        CoursesForm7   = new javax.swing.JButton();
        logoutButton   = new javax.swing.JButton();
        jScrollPane2   = new javax.swing.JScrollPane();
        jTable2        = new javax.swing.JTable();

        pieChartPanel = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawPieChart((Graphics2D) g);
            }
        };
        pieChartPanel.setBackground(Color.WHITE);
        pieChartPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(220,225,235)));

        barChartPanel = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBarChart((Graphics2D) g);
            }
        };
        barChartPanel.setBackground(Color.WHITE);
        barChartPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(220,225,235)));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EDP Attendance System — Dashboard");
        setMinimumSize(new Dimension(1366, 830));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new Color(240, 243, 250));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new Color(8, 35, 70));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0,0,3,0,new Color(0,190,210)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Color navBg    = new Color(8,35,70);
        Color navHover = new Color(0,60,110);
        Font  navFont  = new Font("Segoe UI", Font.BOLD, 11);

        java.util.function.Consumer<javax.swing.JButton> styleNav = btn -> {
            btn.setFont(navFont);
            btn.setForeground(Color.WHITE);
            btn.setBackground(navBg);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(true);
            btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(navHover); }
                public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(navBg); }
            });
        };

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Charles/Icons/online-learning.png")));
        jLabel1.setText("Course"); styleNav.accept(jLabel1);
        jLabel1.addActionListener(e -> jLabel1MouseClicked(null));
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,2,85,65));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Charles/Icons/students.png")));
        jLabel7.setText("Student"); styleNav.accept(jLabel7);
        jLabel7.addActionListener(e -> jLabel7MouseClicked(null));
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(103,2,85,65));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Charles/Icons/back-to-school.png")));
        jLabel9.setText("Event"); styleNav.accept(jLabel9);
        jLabel9.addActionListener(e -> jLabel9MouseClicked(null));
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(196,2,85,65));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Charles/Icons/attendance.png")));
        jLabel10.setText("Attendance"); styleNav.accept(jLabel10);
        jLabel10.addActionListener(e -> jLabel10MouseClicked(null));
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(289,2,95,65));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Charles/Icons/corporate.png")));
        jLabel11.setText("Department"); styleNav.accept(jLabel11);
        jLabel11.addActionListener(e -> jLabel11MouseClicked(null));
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(392,2,100,65));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Charles/Icons/lecture-room.png")));
        jLabel13.setText("Year & Section"); styleNav.accept(jLabel13);
        jLabel13.addActionListener(e -> jLabel13MouseClicked(null));
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(500,2,115,65));

        // ── NEW: Report button using CoursesForm2 ─────────────────────────────
        CoursesForm2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Charles/Icons/attendance.png")));
        CoursesForm2.setText("Report"); styleNav.accept(CoursesForm2);
        CoursesForm2.addActionListener(e -> CoursesForm2ActionPerformed(e));
        jPanel2.add(CoursesForm2, new org.netbeans.lib.awtextra.AbsoluteConstraints(623,2,85,65));

        CoursesForm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Charles/Icons/user.png")));
        CoursesForm.setText("Courses"); styleNav.accept(CoursesForm);
        CoursesForm.addActionListener(e -> CoursesFormActionPerformed(e));
        jPanel2.add(CoursesForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(870,2,85,65));

        DepartmentButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Charles/Icons/networking.png")));
        DepartmentButton.setText("Dept New"); styleNav.accept(DepartmentButton);
        DepartmentButton.addActionListener(e -> DepartmentButtonActionPerformed(e));
        jPanel2.add(DepartmentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(963,2,85,65));

        collegesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Charles/Icons/student (1).png")));
        collegesButton.setText("Colleges"); styleNav.accept(collegesButton);
        collegesButton.addActionListener(e -> collegesButtonActionPerformed(e));
        jPanel2.add(collegesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1056,2,85,65));

        AddUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Charles/Icons/account.png")));
        AddUser.setText("User Add"); styleNav.accept(AddUser);
        AddUser.addActionListener(e -> AddUserActionPerformed(e));
        jPanel2.add(AddUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(1149,2,85,65));

        logoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Charles/Icons/logout.png")));
        logoutButton.setText("LOGOUT");
        logoutButton.setFont(navFont); logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(navBg); logoutButton.setOpaque(true);
        logoutButton.setBorderPainted(false); logoutButton.setContentAreaFilled(true);
        logoutButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        logoutButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { logoutButton.setBackground(new Color(180,50,50)); }
            public void mouseExited(java.awt.event.MouseEvent e)  { logoutButton.setBackground(navBg); }
        });
        logoutButton.addActionListener(e -> logoutButtonActionPerformed(e));
        jPanel2.add(logoutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1242,2,85,65));

        // Hide unused
        jLabel2.setVisible(false);  jPanel2.add(jLabel2,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel4.setVisible(false);  jPanel2.add(jLabel4,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel5.setVisible(false);  jPanel2.add(jLabel5,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel6.setVisible(false);  jPanel2.add(jLabel6,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel8.setVisible(false);  jPanel2.add(jLabel8,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel12.setVisible(false); jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel14.setVisible(false); jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel15.setVisible(false); jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel16.setVisible(false); jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel17.setVisible(false); jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel18.setVisible(false); jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        // CoursesForm2 is now the Report button — no longer hidden
        CoursesForm3.setVisible(false); jPanel2.add(CoursesForm3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        CoursesForm4.setVisible(false); jPanel2.add(CoursesForm4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        CoursesForm5.setVisible(false); jPanel2.add(CoursesForm5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        CoursesForm6.setVisible(false); jPanel2.add(CoursesForm6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        CoursesForm7.setVisible(false); jPanel2.add(CoursesForm7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,10,1340,75));

        // Summary Cards
        String[] cardLabels  = {"Total Colleges","Total Departments","Total Students","Total Courses","Total Users","Total Events"};
        String[] cardTables  = {"colleges","departments","students","courses","users","events"};
        Color[]  cardColors2 = {
            new Color(52,152,219), new Color(46,204,113), new Color(155,89,182),
            new Color(230,126,34), new Color(231,76,60),  new Color(26,188,156)
        };
        int cW=212, cH=80, cGap=10, cY=95;

        for (int i = 0; i < cardLabels.length; i++) {
            javax.swing.JPanel card = new javax.swing.JPanel(new BorderLayout());
            card.setBackground(cardColors2[i]);
            card.setBorder(javax.swing.BorderFactory.createEmptyBorder(10,15,10,15));

            javax.swing.JLabel titleLbl = new javax.swing.JLabel(cardLabels[i]);
            titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            titleLbl.setForeground(Color.WHITE);

            javax.swing.JLabel countLbl = new javax.swing.JLabel("...");
            countLbl.setFont(new Font("Segoe UI Black", Font.BOLD, 26));
            countLbl.setForeground(Color.WHITE);
            countLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

            card.add(titleLbl, BorderLayout.NORTH);
            card.add(countLbl, BorderLayout.CENTER);

            final int idx = i;
            final String tbl = cardTables[i];
            new Thread(() -> {
                try {
                    java.sql.Connection c = java.sql.DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/attendance_system","root","");
                    java.sql.ResultSet rs = c.prepareStatement(
                        "SELECT COUNT(*) FROM "+tbl).executeQuery();
                    if (rs.next()) {
                        counts[idx] = rs.getInt(1);
                        final String cnt = String.valueOf(counts[idx]);
                        javax.swing.SwingUtilities.invokeLater(() -> {
                            countLbl.setText(cnt);
                            pieChartPanel.repaint();
                            barChartPanel.repaint();
                        });
                    }
                    c.close();
                } catch (Exception e) {
                    javax.swing.SwingUtilities.invokeLater(() -> countLbl.setText("0"));
                }
            }).start();

            jPanel1.add(card, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                10 + i*(cW+cGap), cY, cW, cH));
        }

        // Pie Chart
        javax.swing.JLabel pieTitle = new javax.swing.JLabel("  Pie Chart — Attendance Status");
        pieTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pieTitle.setOpaque(true);
        pieTitle.setBackground(new Color(8,35,70));
        pieTitle.setForeground(Color.WHITE);
        pieTitle.setPreferredSize(new Dimension(100, 30));
        pieTitle.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,8,0,0));

        javax.swing.JPanel pieWrapper = new javax.swing.JPanel(new BorderLayout());
        pieWrapper.setBackground(Color.WHITE);
        pieWrapper.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(220,225,235)));
        pieWrapper.add(pieTitle, BorderLayout.NORTH);
        pieWrapper.add(pieChartPanel, BorderLayout.CENTER);
        jPanel1.add(pieWrapper, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 185, 660, 270));

        // Bar Chart
        javax.swing.JLabel barTitle = new javax.swing.JLabel("  Bar Graph — Students per Course");
        barTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        barTitle.setOpaque(true);
        barTitle.setBackground(new Color(8,35,70));
        barTitle.setForeground(Color.WHITE);
        barTitle.setPreferredSize(new Dimension(100, 30));
        barTitle.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,8,0,0));

        javax.swing.JPanel barWrapper = new javax.swing.JPanel(new BorderLayout());
        barWrapper.setBackground(Color.WHITE);
        barWrapper.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(220,225,235)));
        barWrapper.add(barTitle, BorderLayout.NORTH);
        barWrapper.add(barChartPanel, BorderLayout.CENTER);
        jPanel1.add(barWrapper, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 185, 670, 270));

        // Student Table
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"Student ID","First Name","Course","Student Type"}
        ) { @Override public boolean isCellEditable(int r, int c) { return false; } });
        jTable2.setRowHeight(24);
        jTable2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jTable2.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        jTable2.getTableHeader().setBackground(new Color(8,35,70));
        jTable2.getTableHeader().setForeground(Color.WHITE);
        jTable2.setSelectionBackground(new Color(210,228,255));

        javax.swing.JLabel tableTitle = new javax.swing.JLabel("  Student Records");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableTitle.setOpaque(true);
        tableTitle.setBackground(new Color(8,35,70));
        tableTitle.setForeground(Color.WHITE);
        tableTitle.setPreferredSize(new Dimension(100, 30));
        tableTitle.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,8,0,0));

        jScrollPane2.setViewportView(jTable2);
        javax.swing.JPanel tablePanel = new javax.swing.JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(220,225,235)));
        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(jScrollPane2, BorderLayout.CENTER);
        jPanel1.add(tablePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 465, 1340, 325));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,1366,800));
        pack();
    }

    // Navigation
    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int c = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to logout?", "Logout",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (c == JOptionPane.YES_OPTION) { new LoginScreen().setVisible(true); this.dispose(); }
    }
    private void logoutButtonMouseClicked(java.awt.event.MouseEvent evt) {}
    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt)  { new CourseEntryForm().setVisible(true);            this.dispose(); }
    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt)  { new StudentEntryForm().setVisible(true);           this.dispose(); }
    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt)  { new SchoolEventsEntryForm().setVisible(true);      this.dispose(); }
    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) { new AttendanceScannerScreen().setVisible(true);    this.dispose(); }
    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) { new CollegeDepartmentEntryForm().setVisible(true); this.dispose(); }
    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) { new YearSectionEntryForm().setVisible(true);       this.dispose(); }
    private void AddUserActionPerformed(java.awt.event.ActionEvent evt)          { new AdminAddUser().setVisible(true);    this.dispose(); }
    private void collegesButtonActionPerformed(java.awt.event.ActionEvent evt)   { new CollegesForm().setVisible(true);   this.dispose(); }
    private void DepartmentButtonActionPerformed(java.awt.event.ActionEvent evt) { new DepartmentForm().setVisible(true); this.dispose(); }
    private void CoursesFormActionPerformed(java.awt.event.ActionEvent evt)      { new CoursesForm().setVisible(true);    this.dispose(); }
    private void CoursesForm2ActionPerformed(java.awt.event.ActionEvent evt)     { new AttendanceReportScreen().setVisible(true); this.dispose(); } // ← REPORT
    private void CoursesForm3ActionPerformed(java.awt.event.ActionEvent evt) {}
    private void CoursesForm4ActionPerformed(java.awt.event.ActionEvent evt) {}
    private void CoursesForm5ActionPerformed(java.awt.event.ActionEvent evt) {}
    private void CoursesForm6ActionPerformed(java.awt.event.ActionEvent evt) {}
    private void CoursesForm7ActionPerformed(java.awt.event.ActionEvent evt) {}

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {}
        java.awt.EventQueue.invokeLater(() -> new Dashboard().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JButton        AddUser;
    private javax.swing.JButton        CoursesForm;
    private javax.swing.JButton        CoursesForm2;
    private javax.swing.JButton        CoursesForm3;
    private javax.swing.JButton        CoursesForm4;
    private javax.swing.JButton        CoursesForm5;
    private javax.swing.JButton        CoursesForm6;
    private javax.swing.JButton        CoursesForm7;
    private javax.swing.JButton        DepartmentButton;
    private javax.swing.JButton        collegesButton;
    private javax.swing.JButton        jLabel1;
    private javax.swing.JButton        jLabel7;
    private javax.swing.JButton        jLabel9;
    private javax.swing.JButton        jLabel10;
    private javax.swing.JButton        jLabel11;
    private javax.swing.JButton        jLabel13;
    private javax.swing.JLabel         jLabel2;
    private javax.swing.JLabel         jLabel4;
    private javax.swing.JLabel         jLabel5;
    private javax.swing.JLabel         jLabel6;
    private javax.swing.JLabel         jLabel8;
    private javax.swing.JLabel         jLabel12;
    private javax.swing.JLabel         jLabel14;
    private javax.swing.JLabel         jLabel15;
    private javax.swing.JLabel         jLabel16;
    private javax.swing.JLabel         jLabel17;
    private javax.swing.JLabel         jLabel18;
    private javax.swing.JPanel         jPanel1;
    private javax.swing.JPanel         jPanel2;
    private javax.swing.JPanel         pieChartPanel;
    private javax.swing.JPanel         barChartPanel;
    private javax.swing.JScrollPane    jScrollPane2;
    private javax.swing.JTable         jTable2;
    private javax.swing.JButton        logoutButton;
    // End of variables declaration
}