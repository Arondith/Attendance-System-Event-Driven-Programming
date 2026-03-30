package templonuevocharlesluke;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CollegesForm extends javax.swing.JFrame {

    Connection conn;

    public CollegesForm() {
        initComponents();
         setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); // ← add this
        connect();
        loadTable();
        setupPlaceholders();
    }

    // ── DB Connection ─────────────────────────────────────────────────────────
    public void connect() {
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Connection error: " + e.getMessage());
        }
    }

    // ── Placeholders ──────────────────────────────────────────────────────────
    private void setupPlaceholders() {
        addPlaceholder(txtCollegeName, "e.g. College of Engineering");
        addPlaceholder(txtDeanFname,   "First Name");
        addPlaceholder(txtDeanMname,   "Middle Name");
        addPlaceholder(txtDeanLname,   "Last Name");
    }

    private void addPlaceholder(javax.swing.JTextField f, String ph) {
        f.setForeground(new Color(160, 170, 190));
        f.setText(ph);
        f.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (f.getText().equals(ph)) { f.setText(""); f.setForeground(new Color(10,40,80)); }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (f.getText().trim().isEmpty()) { f.setForeground(new Color(160,170,190)); f.setText(ph); }
            }
        });
    }

    private String getVal(javax.swing.JTextField f, String ph) {
        String v = f.getText().trim();
        return v.equals(ph) ? "" : v;
    }

    // ── C-01: Load table ──────────────────────────────────────────────────────
    public void loadTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) courseTable.getModel();
            model.setRowCount(0);
            ResultSet rs = conn.prepareStatement(
                "SELECT * FROM colleges ORDER BY id DESC").executeQuery();
            while (rs.next()) {
                String mid = rs.getString("dean_mname");
                String fullName = rs.getString("dean_fname") + " " +
                    (mid != null && !mid.isEmpty() ? mid + " " : "") +
                    rs.getString("dean_lname");
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("college_name"),
                    fullName.trim()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Load error: " + e.getMessage());
        }
    }

    // ── C-02: Search ──────────────────────────────────────────────────────────
    public void searchCollege() {
        try {
            String search = txtSearch.getText().trim();
            DefaultTableModel model = (DefaultTableModel) courseTable.getModel();
            model.setRowCount(0);
            PreparedStatement pst = conn.prepareStatement(
                "SELECT * FROM colleges WHERE college_name LIKE ? OR " +
                "dean_fname LIKE ? OR dean_lname LIKE ?");
            pst.setString(1, "%" + search + "%");
            pst.setString(2, "%" + search + "%");
            pst.setString(3, "%" + search + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String mid = rs.getString("dean_mname");
                String fullName = rs.getString("dean_fname") + " " +
                    (mid != null && !mid.isEmpty() ? mid + " " : "") +
                    rs.getString("dean_lname");
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("college_name"),
                    fullName.trim()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Search error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1       = new javax.swing.JPanel();
        headerPanel   = new javax.swing.JPanel();
        headerLabel   = new javax.swing.JLabel();
        accentLine    = new javax.swing.JPanel();
        lblCollegeName= new javax.swing.JLabel();
        txtCollegeName= new javax.swing.JTextField();
        lblDean       = new javax.swing.JLabel();
        lblDeanFname  = new javax.swing.JLabel();
        txtDeanFname  = new javax.swing.JTextField();
        lblDeanMname  = new javax.swing.JLabel();
        txtDeanMname  = new javax.swing.JTextField();
        lblDeanLname  = new javax.swing.JLabel();
        txtDeanLname  = new javax.swing.JTextField();
        lblSearch     = new javax.swing.JLabel();
        txtSearch     = new javax.swing.JTextField();
        addButton     = new javax.swing.JButton();
        updateButton  = new javax.swing.JButton();
        deleteButton  = new javax.swing.JButton();
        clearButton   = new javax.swing.JButton();
        exitButton    = new javax.swing.JButton();
        jScrollPane1  = new javax.swing.JScrollPane();
        courseTable   = new javax.swing.JTable();

        // Legacy vars kept for GEN compatibility
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        courseName = new javax.swing.JTextField();

        // ── Frame ─────────────────────────────────────────────────────────────
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EDP Attendance System — Colleges");
        setMinimumSize(new Dimension(780, 580));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new Color(240, 243, 250));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // ── Header ────────────────────────────────────────────────────────────
        headerPanel.setBackground(new Color(8, 35, 70));
        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setText("  Colleges Management");
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

        // Row 1 — College Name (full width)
        lblCollegeName.setFont(lf); lblCollegeName.setForeground(lc);
        lblCollegeName.setText("College Name:");
        formCard.add(lblCollegeName, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 120, 18));
        txtCollegeName.setFont(ff); txtCollegeName.setBorder(fb);
        txtCollegeName.setBackground(new Color(245, 247, 252));
        formCard.add(txtCollegeName, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 36, 410, 32));

        // Row 2 — Dean label
        lblDean.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDean.setForeground(new Color(0, 190, 210));
        lblDean.setText("Dean Information");
        formCard.add(lblDean, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 82, 200, 18));

        // Row 3 — First Name | Middle Name | Last Name
        lblDeanFname.setFont(lf); lblDeanFname.setForeground(lc);
        lblDeanFname.setText("First Name:");
        formCard.add(lblDeanFname, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 105, 100, 18));
        txtDeanFname.setFont(ff); txtDeanFname.setBorder(fb);
        txtDeanFname.setBackground(new Color(245, 247, 252));
        formCard.add(txtDeanFname, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 126, 200, 32));

        lblDeanMname.setFont(lf); lblDeanMname.setForeground(lc);
        lblDeanMname.setText("Middle Name:");
        formCard.add(lblDeanMname, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 105, 110, 18));
        txtDeanMname.setFont(ff); txtDeanMname.setBorder(fb);
        txtDeanMname.setBackground(new Color(245, 247, 252));
        formCard.add(txtDeanMname, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 126, 180, 32));

        lblDeanLname.setFont(lf); lblDeanLname.setForeground(lc);
        lblDeanLname.setText("Last Name:");
        formCard.add(lblDeanLname, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 105, 100, 18));
        txtDeanLname.setFont(ff); txtDeanLname.setBorder(fb);
        txtDeanLname.setBackground(new Color(245, 247, 252));
        formCard.add(txtDeanLname, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 126, 200, 32));

        jPanel1.add(formCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 760, 175));

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
                g2.drawString("SAVE", (getWidth()-fm.stringWidth("SAVE"))/2,
                    (getHeight()+fm.getAscent()-fm.getDescent())/2);
                g2.dispose();
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        addButton.setFocusPainted(false); addButton.setContentAreaFilled(false);
        addButton.setBorderPainted(false); addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> addButtonActionPerformed(e));
        jPanel1.add(addButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 242, 90, 34));

        updateButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        updateButton.setText("UPDATE");
        updateButton.setFocusPainted(false);
        updateButton.addActionListener(e -> updateButtonActionPerformed(e));
        jPanel1.add(updateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 242, 90, 34));

        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        deleteButton.setText("DELETE");
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> deleteButtonActionPerformed(e));
        jPanel1.add(deleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 242, 90, 34));

        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        clearButton.setText("CLEAR");
        clearButton.setFocusPainted(false);
        clearButton.addActionListener(e -> clearButtonActionPerformed(e));
        jPanel1.add(clearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 242, 90, 34));

        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        exitButton.setText("EXIT");
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> exitButtonActionPerformed(e));
        jPanel1.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 242, 90, 34));

        // ── Search row ────────────────────────────────────────────────────────
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblSearch.setForeground(lc);
        lblSearch.setText("Search:");
        jPanel1.add(lblSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 292, 60, 20));

        txtSearch.setFont(ff);
        txtSearch.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new Color(200, 210, 225), 1),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (txtSearch.getText().trim().isEmpty()) loadTable();
                else searchCollege();
            }
        });
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 289, 300, 30));

        // ── Colleges Table ────────────────────────────────────────────────────
        courseTable.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "College Name", "Dean"}
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
        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 328, 760, 235));

        // Hide legacy
        jLabel1.setVisible(false); jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel2.setVisible(false); jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel3.setVisible(false); jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel4.setVisible(false); jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel5.setVisible(false); jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jComboBox1.setVisible(false); jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jPanel2.setVisible(false); jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jPanel3.setVisible(false); jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        courseName.setVisible(false); jPanel1.add(courseName, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 580));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // ── C-01: Save College + auto-create Dean user account ───────────────────
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String collegeName = getVal(txtCollegeName, "e.g. College of Engineering");
        String fName = getVal(txtDeanFname, "First Name");
        String mName = getVal(txtDeanMname, "Middle Name");
        String lName = getVal(txtDeanLname, "Last Name");

        if (collegeName.isEmpty() || fName.isEmpty() || lName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all required fields.\n(College Name, Dean First Name, Last Name)",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Insert college
            PreparedStatement pst = conn.prepareStatement(
                "INSERT INTO colleges (college_name, dean_fname, dean_mname, dean_lname) VALUES (?,?,?,?)");
            pst.setString(1, collegeName);
            pst.setString(2, fName);
            pst.setString(3, mName.isEmpty() ? null : mName);
            pst.setString(4, lName);
            pst.executeUpdate();

            // Auto-create Dean user account
            String username = fName.toLowerCase() + "." + lName.toLowerCase();
            PreparedStatement pstUser = conn.prepareStatement(
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
                "College saved successfully!\nDean user account created.\nUsername: " + username + "\nPassword: password123",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            loadTable();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── C-03: Update College ──────────────────────────────────────────────────
    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = courseTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a row to update."); return; }
        int mr = courseTable.convertRowIndexToModel(row);
        int id = (int) courseTable.getModel().getValueAt(mr, 0);

        String collegeName = getVal(txtCollegeName, "e.g. College of Engineering");
        String fName = getVal(txtDeanFname, "First Name");
        String mName = getVal(txtDeanMname, "Middle Name");
        String lName = getVal(txtDeanLname, "Last Name");

        if (collegeName.isEmpty() || fName.isEmpty() || lName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all required fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            PreparedStatement pst = conn.prepareStatement(
                "UPDATE colleges SET college_name=?, dean_fname=?, dean_mname=?, dean_lname=? WHERE id=?");
            pst.setString(1, collegeName);
            pst.setString(2, fName);
            pst.setString(3, mName.isEmpty() ? null : mName);
            pst.setString(4, lName);
            pst.setInt(5, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "College updated successfully!");
            loadTable();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // ── C-04: Delete College ──────────────────────────────────────────────────
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = courseTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a row to delete."); return; }
        int mr = courseTable.convertRowIndexToModel(row);
        int id = (int) courseTable.getModel().getValueAt(mr, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete this college?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                PreparedStatement pst = conn.prepareStatement("DELETE FROM colleges WHERE id=?");
                pst.setInt(1, id);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "College deleted successfully!");
                loadTable();
                clearFields();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) { clearFields(); }

    // ── Click row → fill form ─────────────────────────────────────────────────
    private void courseTableMouseClicked(java.awt.event.MouseEvent evt) {
        int row = courseTable.getSelectedRow();
        if (row == -1) return;
        int mr = courseTable.convertRowIndexToModel(row);

        txtCollegeName.setText(courseTable.getModel().getValueAt(mr, 1).toString());
        txtCollegeName.setForeground(new Color(10, 40, 80));

        // Parse dean full name back into fields
        String full = courseTable.getModel().getValueAt(mr, 2).toString();
        String[] parts = full.trim().split("\\s+");
        if (parts.length == 1) {
            txtDeanFname.setText(parts[0]); txtDeanFname.setForeground(new Color(10,40,80));
            txtDeanMname.setForeground(new Color(160,170,190)); txtDeanMname.setText("Middle Name");
            txtDeanLname.setForeground(new Color(160,170,190)); txtDeanLname.setText("Last Name");
        } else if (parts.length == 2) {
            txtDeanFname.setText(parts[0]); txtDeanFname.setForeground(new Color(10,40,80));
            txtDeanMname.setForeground(new Color(160,170,190)); txtDeanMname.setText("Middle Name");
            txtDeanLname.setText(parts[1]); txtDeanLname.setForeground(new Color(10,40,80));
        } else {
            txtDeanFname.setText(parts[0]); txtDeanFname.setForeground(new Color(10,40,80));
            txtDeanLname.setText(parts[parts.length-1]); txtDeanLname.setForeground(new Color(10,40,80));
            StringBuilder mid = new StringBuilder();
            for (int i = 1; i < parts.length-1; i++) { if (i>1) mid.append(" "); mid.append(parts[i]); }
            txtDeanMname.setText(mid.toString()); txtDeanMname.setForeground(new Color(10,40,80));
        }
    }

    private void clearFields() {
        txtCollegeName.setForeground(new Color(160,170,190)); txtCollegeName.setText("e.g. College of Engineering");
        txtDeanFname.setForeground(new Color(160,170,190));   txtDeanFname.setText("First Name");
        txtDeanMname.setForeground(new Color(160,170,190));   txtDeanMname.setText("Middle Name");
        txtDeanLname.setForeground(new Color(160,170,190));   txtDeanLname.setText("Last Name");
        txtSearch.setText("");
        courseTable.clearSelection();
        loadTable();
    }

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        new Dashboard().setVisible(true);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new CollegesForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel         accentLine;
    private javax.swing.JButton        addButton;
    private javax.swing.JButton        clearButton;
    private javax.swing.JTextField     courseName;   // legacy kept
    private javax.swing.JTable         courseTable;
    private javax.swing.JButton        deleteButton;
    private javax.swing.JButton        exitButton;
    private javax.swing.JPanel         headerPanel;
    private javax.swing.JLabel         headerLabel;
    private javax.swing.JComboBox<String> jComboBox1; // legacy kept
    private javax.swing.JLabel         jLabel1;
    private javax.swing.JLabel         jLabel2;
    private javax.swing.JLabel         jLabel3;
    private javax.swing.JLabel         jLabel4;
    private javax.swing.JLabel         jLabel5;
    private javax.swing.JPanel         jPanel1;
    private javax.swing.JPanel         jPanel2;
    private javax.swing.JPanel         jPanel3;
    private javax.swing.JScrollPane    jScrollPane1;
    private javax.swing.JLabel         lblCollegeName;
    private javax.swing.JLabel         lblDean;
    private javax.swing.JLabel         lblDeanFname;
    private javax.swing.JLabel         lblDeanMname;
    private javax.swing.JLabel         lblDeanLname;
    private javax.swing.JLabel         lblSearch;
    private javax.swing.JTextField     txtCollegeName;
    private javax.swing.JTextField     txtDeanFname;
    private javax.swing.JTextField     txtDeanMname;
    private javax.swing.JTextField     txtDeanLname;
    private javax.swing.JTextField     txtSearch;
    private javax.swing.JButton        updateButton;
    // End of variables declaration//GEN-END:variables
}