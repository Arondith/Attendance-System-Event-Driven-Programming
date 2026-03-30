package templonuevocharlesluke;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CoursesForm extends javax.swing.JFrame {

    Connection conn;

    public CoursesForm() {
        initComponents();
         setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); // ← add this
        connect();
        loadTable();
        setupPlaceholder();
    }

    public void connect() {
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Connection error: " + e.getMessage());
        }
    }

    private void setupPlaceholder() {
        courseName.setForeground(new Color(160, 170, 190));
        courseName.setText("Enter course name");
        courseName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (courseName.getText().equals("Enter course name")) {
                    courseName.setText(""); courseName.setForeground(new Color(10,40,80));
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (courseName.getText().trim().isEmpty()) {
                    courseName.setForeground(new Color(160,170,190));
                    courseName.setText("Enter course name");
                }
            }
        });
    }

    public void loadTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) courseTable.getModel();
            model.setRowCount(0);
            ResultSet rs = conn.prepareStatement(
                "SELECT * FROM coursesform ORDER BY id DESC").executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("department"),
                    rs.getString("course_name")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Load error: " + e.getMessage());
        }
    }

    public void searchCourse() {
        try {
            String search = txtSearch.getText().trim();
            PreparedStatement pst;
            try {
                int idSearch = Integer.parseInt(search);
                pst = conn.prepareStatement(
                    "SELECT * FROM coursesform WHERE id=? OR department LIKE ? OR course_name LIKE ?");
                pst.setInt(1, idSearch);
                pst.setString(2, "%" + search + "%");
                pst.setString(3, "%" + search + "%");
            } catch (NumberFormatException e) {
                pst = conn.prepareStatement(
                    "SELECT * FROM coursesform WHERE department LIKE ? OR course_name LIKE ?");
                pst.setString(1, "%" + search + "%");
                pst.setString(2, "%" + search + "%");
            }
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) courseTable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("department"),      // fixed: was department_name
                    rs.getString("course_name")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Search error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1      = new javax.swing.JPanel();
        headerPanel  = new javax.swing.JPanel();
        headerLabel  = new javax.swing.JLabel();
        accentLine   = new javax.swing.JPanel();
        courseName   = new javax.swing.JTextField();
        jComboBox1   = new javax.swing.JComboBox<>();
        txtSearch    = new javax.swing.JTextField();
        addButton    = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        clearButton  = new javax.swing.JButton();
        exitButton   = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        courseTable  = new javax.swing.JTable();

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        // Frame
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EDP Attendance System — Courses");
        setMinimumSize(new Dimension(680, 580));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new Color(240, 243, 250));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // Header
        headerPanel.setBackground(new Color(8, 35, 70));
        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setText("  Course Management");
        headerPanel.add(headerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 45));
        jPanel1.add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 45));

        accentLine.setBackground(new Color(0, 190, 210));
        jPanel1.add(accentLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 45, 680, 3));

        // Form card
        javax.swing.JPanel formCard = new javax.swing.JPanel();
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(220, 225, 235), 1));
        formCard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Font lf = new Font("Segoe UI", Font.BOLD, 11);
        Font ff = new Font("Segoe UI", Font.PLAIN, 12);
        Color lc = new Color(8, 35, 70);
        javax.swing.border.Border fb = javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new Color(200, 210, 225), 1),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8));

        // Course Name
        javax.swing.JLabel lName = new javax.swing.JLabel("Course Name:");
        lName.setFont(lf); lName.setForeground(lc);
        formCard.add(lName, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 120, 18));
        courseName.setFont(ff); courseName.setBorder(fb);
        courseName.setBackground(new Color(245, 247, 252));
        formCard.add(courseName, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 36, 280, 32));

        // Department
        javax.swing.JLabel lDept = new javax.swing.JLabel("Department:");
        lDept.setFont(lf); lDept.setForeground(lc);
        formCard.add(lDept, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 15, 120, 18));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            "-- Select Department --",
            "CEAC", "CBGA", "CHS", "CED", "CAS",
            "Information Technology", "Computer Science",
            "Civil Engineering", "Electrical Engineering",
            "Accounting", "Architecture", "Nursing",
            "Education", "Business Administration"
        }));
        jComboBox1.setFont(ff);
        formCard.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 36, 280, 32));

        jPanel1.add(formCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 660, 90));

        // Buttons
        addButton = new javax.swing.JButton() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(0,60,110) : new Color(8,35,70));
                g2.fillRect(0,0,getWidth(),getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString("ADD", (getWidth()-fm.stringWidth("ADD"))/2,
                    (getHeight()+fm.getAscent()-fm.getDescent())/2);
                g2.dispose();
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        addButton.setFocusPainted(false); addButton.setContentAreaFilled(false);
        addButton.setBorderPainted(false); addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> addButtonActionPerformed(e));
        jPanel1.add(addButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 158, 80, 34));

        updateButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        updateButton.setText("UPDATE");
        updateButton.setFocusPainted(false);
        updateButton.addActionListener(e -> updateButtonActionPerformed(e));
        jPanel1.add(updateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 158, 90, 34));

        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        deleteButton.setText("DELETE");
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> deleteButtonActionPerformed(e));
        jPanel1.add(deleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 158, 90, 34));

        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        clearButton.setText("CLEAR");
        clearButton.setFocusPainted(false);
        clearButton.addActionListener(e -> clearButtonActionPerformed(e));
        jPanel1.add(clearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 158, 90, 34));

        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        exitButton.setText("EXIT");
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> exitButtonActionPerformed(e));
        jPanel1.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 158, 90, 34));

        // Search
        javax.swing.JLabel lSearch = new javax.swing.JLabel("Search:");
        lSearch.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lSearch.setForeground(lc);
        jPanel1.add(lSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 208, 60, 20));

        txtSearch.setFont(ff);
        txtSearch.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new Color(200, 210, 225), 1),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (txtSearch.getText().trim().isEmpty()) loadTable();
                else searchCourse();
            }
        });
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 205, 300, 30));

        // Table
        courseTable.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Department", "Course Name"}
        ) { @Override public boolean isCellEditable(int r, int c) { return false; } });
        courseTable.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        courseTable.setRowHeight(24);
        courseTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        courseTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        courseTable.getTableHeader().setBackground(new Color(8, 35, 70));
        courseTable.getTableHeader().setForeground(Color.WHITE);
        courseTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { courseTableMouseClicked(evt); }
        });
        jScrollPane1.setViewportView(courseTable);
        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 245, 660, 315));

        // Hide legacy
        jLabel1.setVisible(false); jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel2.setVisible(false); jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel3.setVisible(false); jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel4.setVisible(false); jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel5.setVisible(false); jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jPanel2.setVisible(false); jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jPanel3.setVisible(false); jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 580));

        pack();
        setLocationRelativeTo(null);
    }

    // Add
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String dept  = jComboBox1.getSelectedItem().toString();
        String cname = courseName.getText().trim();

        if (dept.equals("-- Select Department --")) {
            JOptionPane.showMessageDialog(this, "Please select a Department.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cname.isEmpty() || cname.equals("Enter course name")) {
            JOptionPane.showMessageDialog(this, "Please enter a Course Name.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            PreparedStatement pst = conn.prepareStatement(
                "INSERT INTO coursesform (department, course_name) VALUES (?,?)");
            pst.setString(1, dept); pst.setString(2, cname);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Course added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadTable(); clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Update
    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = courseTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a row to update."); return; }
        int mr = courseTable.convertRowIndexToModel(row);
        int id = (int) courseTable.getModel().getValueAt(mr, 0);
        try {
            PreparedStatement pst = conn.prepareStatement(
                "UPDATE coursesform SET department=?, course_name=? WHERE id=?");
            pst.setString(1, jComboBox1.getSelectedItem().toString());
            pst.setString(2, courseName.getText().trim());
            pst.setInt(3, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Course updated successfully!");
            loadTable(); clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // Delete
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = courseTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a row to delete."); return; }
        int mr = courseTable.convertRowIndexToModel(row);
        int id = (int) courseTable.getModel().getValueAt(mr, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete this course?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                PreparedStatement pst = conn.prepareStatement("DELETE FROM coursesform WHERE id=?");
                pst.setInt(1, id); pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Course deleted successfully!");
                loadTable(); clearFields();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) { clearFields(); }

    private void courseTableMouseClicked(java.awt.event.MouseEvent evt) {
        int row = courseTable.getSelectedRow();
        if (row == -1) return;
        int mr = courseTable.convertRowIndexToModel(row);
        jComboBox1.setSelectedItem(courseTable.getModel().getValueAt(mr, 1).toString());
        courseName.setText(courseTable.getModel().getValueAt(mr, 2).toString());
        courseName.setForeground(new Color(10, 40, 80));
    }

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {
        if (txtSearch.getText().trim().isEmpty()) loadTable(); else searchCourse();
    }

    private void courseNameActionPerformed(java.awt.event.ActionEvent evt) {}

    private void clearFields() {
        courseName.setForeground(new Color(160, 170, 190));
        courseName.setText("Enter course name");
        jComboBox1.setSelectedIndex(0);
        txtSearch.setText("");
        courseTable.clearSelection();
        loadTable();
    }

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        new Dashboard().setVisible(true);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new CoursesForm().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JPanel         accentLine;
    private javax.swing.JButton        addButton;
    private javax.swing.JButton        clearButton;
    private javax.swing.JTextField     courseName;
    private javax.swing.JTable         courseTable;
    private javax.swing.JButton        deleteButton;
    private javax.swing.JButton        exitButton;
    private javax.swing.JPanel         headerPanel;
    private javax.swing.JLabel         headerLabel;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel         jLabel1;
    private javax.swing.JLabel         jLabel2;
    private javax.swing.JLabel         jLabel3;
    private javax.swing.JLabel         jLabel4;
    private javax.swing.JLabel         jLabel5;
    private javax.swing.JPanel         jPanel1;
    private javax.swing.JPanel         jPanel2;
    private javax.swing.JPanel         jPanel3;
    private javax.swing.JScrollPane    jScrollPane1;
    private javax.swing.JTextField     txtSearch;
    private javax.swing.JButton        updateButton;
    // End of variables declaration
}