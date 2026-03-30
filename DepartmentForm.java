package templonuevocharlesluke;

import javax.swing.JOptionPane;
import java.sql.*;
import java.awt.*;

public class DepartmentForm extends javax.swing.JFrame {

    public DepartmentForm() {
        initComponents();
         setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); // ← add this
        loadColleges();
        loadTableData();
        addPlaceholder(txtDeptName,   "Enter Department Name");
        addPlaceholder(txtFirstName,  "First Name");
        addPlaceholder(txtMiddleName, "Middle Name");
        addPlaceholder(txtLastName,   "Last Name");
    }

    // ── Placeholder ───────────────────────────────────────────────────────────
    private void addPlaceholder(javax.swing.JTextField field, String placeholder) {
        field.setForeground(new Color(160, 170, 190));
        field.setText(placeholder);
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText(""); field.setForeground(new Color(10,40,80));
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().trim().isEmpty()) {
                    field.setForeground(new Color(160,170,190)); field.setText(placeholder);
                }
            }
        });
    }

    private String getFieldValue(javax.swing.JTextField field, String placeholder) {
        String val = field.getText().trim();
        return val.equals(placeholder) ? "" : val;
    }

    // ── Load departments into JTable ──────────────────────────────────────────
    private void loadTableData() {
        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "");
            String sql = "SELECT id, college, dept_name, coordinator_fname, coordinator_mname, coordinator_lname FROM departments";
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                String mid = rs.getString("coordinator_mname");
                String fullName = rs.getString("coordinator_fname") + " " +
                    (mid != null && !mid.isEmpty() ? mid + " " : "") +
                    rs.getString("coordinator_lname");
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("college"),
                    rs.getString("dept_name"),
                    fullName.trim()
                });
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Load Table Error: " + e.getMessage());
        }
    }

    // ── Hardcoded colleges ────────────────────────────────────────────────────
    public void loadColleges() {
        cmbCollege.removeAllItems();
        cmbCollege.addItem("-- Select College --");
        cmbCollege.addItem("CEAC");
        cmbCollege.addItem("CBGA");
        cmbCollege.addItem("CHS");
        cmbCollege.addItem("CED");
        cmbCollege.addItem("CAS");
        cmbCollege.setSelectedIndex(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1      = new javax.swing.JPanel();
        headerPanel  = new javax.swing.JPanel();
        headerLabel  = new javax.swing.JLabel();
        accentLine   = new javax.swing.JPanel();
        cmbCollege   = new javax.swing.JComboBox<>();
        txtDeptName  = new javax.swing.JTextField();
        txtFirstName = new javax.swing.JTextField();
        txtMiddleName= new javax.swing.JTextField();
        txtLastName  = new javax.swing.JTextField();
        addButton    = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        clearButton  = new javax.swing.JButton();
        exitButton   = new javax.swing.JButton();
        txtSearch    = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1      = new javax.swing.JTable();

        // Legacy labels kept for GEN compatibility
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();

        // ── Frame ─────────────────────────────────────────────────────────────
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EDP Attendance System — Department");
        setMinimumSize(new Dimension(780, 620));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new Color(240, 243, 250));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // ── Header ────────────────────────────────────────────────────────────
        headerPanel.setBackground(new Color(8, 35, 70));
        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setText("  Department Management");
        headerPanel.add(headerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 45));
        jPanel1.add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 45));

        accentLine.setBackground(new Color(0, 190, 210));
        jPanel1.add(accentLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 45, 780, 3));

        // ── Form card ─────────────────────────────────────────────────────────
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

        // Row 1 — College | Department Name
        javax.swing.JLabel lCollege = new javax.swing.JLabel("College:");
        lCollege.setFont(lf); lCollege.setForeground(lc);
        formCard.add(lCollege, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 80, 18));

        cmbCollege.setFont(ff);
        formCard.add(cmbCollege, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 36, 200, 32));

        javax.swing.JLabel lDept = new javax.swing.JLabel("Department Name:");
        lDept.setFont(lf); lDept.setForeground(lc);
        formCard.add(lDept, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 15, 140, 18));

        txtDeptName.setFont(ff); txtDeptName.setBorder(fb);
        txtDeptName.setBackground(new Color(245, 247, 252));
        formCard.add(txtDeptName, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 36, 230, 32));

        // Row 2 — Coordinator section
        javax.swing.JLabel lCoord = new javax.swing.JLabel("Program Coordinator:");
        lCoord.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lCoord.setForeground(new Color(0, 190, 210));
        formCard.add(lCoord, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, 220, 20));

        javax.swing.JLabel lFname = new javax.swing.JLabel("First Name:");
        lFname.setFont(lf); lFname.setForeground(lc);
        formCard.add(lFname, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 33, 100, 18));

        txtFirstName.setFont(ff); txtFirstName.setBorder(fb);
        txtFirstName.setBackground(new Color(245, 247, 252));
        formCard.add(txtFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 54, 140, 32));

        javax.swing.JLabel lMname = new javax.swing.JLabel("Middle:");
        lMname.setFont(lf); lMname.setForeground(lc);
        formCard.add(lMname, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 33, 70, 18));

        txtMiddleName.setFont(ff); txtMiddleName.setBorder(fb);
        txtMiddleName.setBackground(new Color(245, 247, 252));
        formCard.add(txtMiddleName, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 54, 120, 32));

        javax.swing.JLabel lLname = new javax.swing.JLabel("Last Name:");
        lLname.setFont(lf); lLname.setForeground(lc);
        formCard.add(lLname, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 92, 100, 18));

        txtLastName.setFont(ff); txtLastName.setBorder(fb);
        txtLastName.setBackground(new Color(245, 247, 252));
        formCard.add(txtLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 113, 270, 32));

        jPanel1.add(formCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 760, 160));

        // ── Buttons row ───────────────────────────────────────────────────────
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
        jPanel1.add(addButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 228, 90, 34));

        updateButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        updateButton.setText("UPDATE");
        updateButton.setFocusPainted(false);
        updateButton.addActionListener(e -> updateButtonActionPerformed(e));
        jPanel1.add(updateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 228, 90, 34));

        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        deleteButton.setText("DELETE");
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> deleteButtonActionPerformed(e));
        jPanel1.add(deleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 228, 90, 34));

        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        clearButton.setText("CLEAR");
        clearButton.setFocusPainted(false);
        clearButton.addActionListener(e -> clearButtonActionPerformed(e));
        jPanel1.add(clearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 228, 90, 34));

        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        exitButton.setText("EXIT");
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> exitButtonActionPerformed(e));
        jPanel1.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 228, 90, 34));

        // ── Search row ────────────────────────────────────────────────────────
        javax.swing.JLabel lSearch = new javax.swing.JLabel("Search:");
        lSearch.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lSearch.setForeground(lc);
        jPanel1.add(lSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 278, 60, 20));

        txtSearch.setFont(ff);
        txtSearch.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new Color(200,210,225),1),
            javax.swing.BorderFactory.createEmptyBorder(4,8,4,8)));
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) { txtSearchKeyReleased(evt); }
        });
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 275, 300, 30));

        // ── Table ─────────────────────────────────────────────────────────────
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"Dept ID", "College", "Department Name", "Coordinator"}
        ) { @Override public boolean isCellEditable(int row, int col) { return false; } });
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        jTable1.setRowHeight(24);
        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        jTable1.getTableHeader().setBackground(new Color(8, 35, 70));
        jTable1.getTableHeader().setForeground(Color.WHITE);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { jTable1MouseClicked(evt); }
        });
        jScrollPane1.setViewportView(jTable1);
        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 315, 760, 290));

        // Hide legacy
        jLabel1.setVisible(false); jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel2.setVisible(false); jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel3.setVisible(false); jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel4.setVisible(false); jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel5.setVisible(false); jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel6.setVisible(false); jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel7.setVisible(false); jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel8.setVisible(false); jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jPanel2.setVisible(false); jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 620));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmbCollegeActionPerformed(java.awt.event.ActionEvent evt) {}

    // ── D-01: Add Department ──────────────────────────────────────────────────
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String college  = (String) cmbCollege.getSelectedItem();
        String deptName = getFieldValue(txtDeptName,   "Enter Department Name");
        String fName    = getFieldValue(txtFirstName,  "First Name");
        String mName    = getFieldValue(txtMiddleName, "Middle Name");
        String lName    = getFieldValue(txtLastName,   "Last Name");

        if (college == null || college.equals("-- Select College --")) {
            JOptionPane.showMessageDialog(this, "Please select a College.");
            return;
        }
        if (deptName.isEmpty() || fName.isEmpty() || lName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all required fields.\n(Department Name, First Name, Last Name)");
            return;
        }

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "");

            PreparedStatement pstDept = con.prepareStatement(
                "INSERT INTO departments (college, dept_name, department_name, coordinator_fname, coordinator_mname, coordinator_lname) VALUES (?,?,?,?,?,?)");
            pstDept.setString(1, college);
            pstDept.setString(2, deptName);
            pstDept.setString(3, deptName);
            pstDept.setString(4, fName);
            pstDept.setString(5, mName.isEmpty() ? null : mName);
            pstDept.setString(6, lName);
            pstDept.executeUpdate();

            String username = fName.toLowerCase() + "." + lName.toLowerCase();
            PreparedStatement pstUser = con.prepareStatement(
                "INSERT INTO users (first_name, middle_name, last_name, username, password, role, status) VALUES (?,?,?,?,?,?,?)");
            pstUser.setString(1, fName);
            pstUser.setString(2, mName.isEmpty() ? null : mName);
            pstUser.setString(3, lName);
            pstUser.setString(4, username);
            pstUser.setString(5, "password123");
            pstUser.setString(6, "Program Head");
            pstUser.setString(7, "Active");
            pstUser.executeUpdate();

            JOptionPane.showMessageDialog(this,
                "Department Added Successfully!\n" +
                "Coordinator account created.\n" +
                "Username: " + username + "\nPassword: password123");
            loadTableData();
            clearFields();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // ── D-02: Search ──────────────────────────────────────────────────────────
    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {
        String searchStr = txtSearch.getText().trim();
        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable1.getModel();

        javax.swing.RowSorter<?> existingSorter = jTable1.getRowSorter();
        javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> tr;
        if (existingSorter instanceof javax.swing.table.TableRowSorter) {
            tr = (javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel>) existingSorter;
        } else {
            tr = new javax.swing.table.TableRowSorter<>(model);
            jTable1.setRowSorter(tr);
        }
        if (searchStr.isEmpty()) {
            tr.setRowFilter(null);
        } else {
            try {
                tr.setRowFilter(javax.swing.RowFilter.regexFilter(
                    "(?i)" + java.util.regex.Pattern.quote(searchStr)));
            } catch (java.util.regex.PatternSyntaxException e) {
                tr.setRowFilter(null);
            }
        }
    }

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) { clearFields(); }

    // ── Delete ────────────────────────────────────────────────────────────────
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) { JOptionPane.showMessageDialog(this, "Please select a row to delete."); return; }
        int modelRow = jTable1.convertRowIndexToModel(selectedRow);
        String id = jTable1.getModel().getValueAt(modelRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete this department?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
                PreparedStatement pst = con.prepareStatement("DELETE FROM departments WHERE id=?");
                pst.setString(1, id);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Department Deleted Successfully!");
                loadTableData();
                clearFields();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Delete Error: " + e.getMessage());
            }
        }
    }

    // ── Update ────────────────────────────────────────────────────────────────
    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) { JOptionPane.showMessageDialog(this, "Please select a row to update."); return; }

        String college  = (String) cmbCollege.getSelectedItem();
        String deptName = getFieldValue(txtDeptName,   "Enter Department Name");
        String fName    = getFieldValue(txtFirstName,  "First Name");
        String mName    = getFieldValue(txtMiddleName, "Middle Name");
        String lName    = getFieldValue(txtLastName,   "Last Name");

        if (college == null || college.equals("-- Select College --")) {
            JOptionPane.showMessageDialog(this, "Please select a College."); return;
        }
        if (deptName.isEmpty() || fName.isEmpty() || lName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields."); return;
        }

        int modelRow = jTable1.convertRowIndexToModel(selectedRow);
        String id = jTable1.getModel().getValueAt(modelRow, 0).toString();

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
            PreparedStatement pst = con.prepareStatement(
                "UPDATE departments SET college=?, dept_name=?, department_name=?, coordinator_fname=?, coordinator_mname=?, coordinator_lname=? WHERE id=?");
            pst.setString(1, college);
            pst.setString(2, deptName);
            pst.setString(3, deptName);
            pst.setString(4, fName);
            pst.setString(5, mName.isEmpty() ? null : mName);
            pst.setString(6, lName);
            pst.setString(7, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Department Updated Successfully!");
            loadTableData();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update Error: " + e.getMessage());
        }
    }

    // ── Click row → fill form ─────────────────────────────────────────────────
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        int viewRow = jTable1.getSelectedRow();
        if (viewRow == -1) return;
        int modelRow = jTable1.convertRowIndexToModel(viewRow);

        String collegeName = jTable1.getModel().getValueAt(modelRow, 1).toString();
        String deptName    = jTable1.getModel().getValueAt(modelRow, 2).toString();
        String fullName    = jTable1.getModel().getValueAt(modelRow, 3) != null
            ? jTable1.getModel().getValueAt(modelRow, 3).toString() : "";

        cmbCollege.setSelectedItem(collegeName);
        if (!collegeName.equals(cmbCollege.getSelectedItem())) cmbCollege.setSelectedIndex(0);

        txtDeptName.setForeground(new Color(10,40,80)); txtDeptName.setText(deptName);

        String[] parts = fullName.trim().split("\\s+");
        if (parts.length == 1) {
            txtFirstName.setForeground(new Color(10,40,80)); txtFirstName.setText(parts[0]);
            txtMiddleName.setForeground(new Color(160,170,190)); txtMiddleName.setText("Middle Name");
            txtLastName.setForeground(new Color(160,170,190)); txtLastName.setText("Last Name");
        } else if (parts.length == 2) {
            txtFirstName.setForeground(new Color(10,40,80));  txtFirstName.setText(parts[0]);
            txtMiddleName.setForeground(new Color(160,170,190)); txtMiddleName.setText("Middle Name");
            txtLastName.setForeground(new Color(10,40,80));   txtLastName.setText(parts[1]);
        } else {
            txtFirstName.setForeground(new Color(10,40,80)); txtFirstName.setText(parts[0]);
            txtLastName.setForeground(new Color(10,40,80));  txtLastName.setText(parts[parts.length-1]);
            StringBuilder mid = new StringBuilder();
            for (int i = 1; i < parts.length-1; i++) { if(i>1) mid.append(" "); mid.append(parts[i]); }
            txtMiddleName.setForeground(new Color(10,40,80)); txtMiddleName.setText(mid.toString());
        }
    }

    // ── Exit ──────────────────────────────────────────────────────────────────
    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        new Dashboard().setVisible(true);
        this.dispose();
    }

    // ── Clear ─────────────────────────────────────────────────────────────────
    private void clearFields() {
        cmbCollege.setSelectedIndex(0);
        txtDeptName.setForeground(new Color(160,170,190));   txtDeptName.setText("Enter Department Name");
        txtFirstName.setForeground(new Color(160,170,190));  txtFirstName.setText("First Name");
        txtMiddleName.setForeground(new Color(160,170,190)); txtMiddleName.setText("Middle Name");
        txtLastName.setForeground(new Color(160,170,190));   txtLastName.setText("Last Name");
        txtSearch.setText("");
        jTable1.clearSelection();
        javax.swing.RowSorter<?> sorter = jTable1.getRowSorter();
        if (sorter instanceof javax.swing.table.TableRowSorter) {
            ((javax.swing.table.TableRowSorter<?>) sorter).setRowFilter(null);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new DepartmentForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel         accentLine;
    private javax.swing.JButton        addButton;
    private javax.swing.JButton        clearButton;
    private javax.swing.JComboBox<String> cmbCollege;
    private javax.swing.JButton        deleteButton;
    private javax.swing.JButton        exitButton;
    private javax.swing.JPanel         headerPanel;
    private javax.swing.JLabel         headerLabel;
    private javax.swing.JLabel         jLabel1;
    private javax.swing.JLabel         jLabel2;
    private javax.swing.JLabel         jLabel3;
    private javax.swing.JLabel         jLabel4;
    private javax.swing.JLabel         jLabel5;
    private javax.swing.JLabel         jLabel6;
    private javax.swing.JLabel         jLabel7;
    private javax.swing.JLabel         jLabel8;
    private javax.swing.JPanel         jPanel1;
    private javax.swing.JPanel         jPanel2;
    private javax.swing.JScrollPane    jScrollPane1;
    private javax.swing.JTable         jTable1;
    private javax.swing.JTextField     txtDeptName;
    private javax.swing.JTextField     txtFirstName;
    private javax.swing.JTextField     txtLastName;
    private javax.swing.JTextField     txtMiddleName;
    private javax.swing.JTextField     txtSearch;
    private javax.swing.JButton        updateButton;
    // End of variables declaration//GEN-END:variables
}