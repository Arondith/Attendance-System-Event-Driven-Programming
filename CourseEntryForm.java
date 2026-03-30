package templonuevocharlesluke;

import javax.swing.JOptionPane;
import java.sql.*;
import java.awt.*;

public class CourseEntryForm extends javax.swing.JFrame {

    public CourseEntryForm() {
        initComponents();
         setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); // ← add this
        setupPlaceholders();
        loadTable();
    }

    // ── Placeholders ──────────────────────────────────────────────────────────
    private void setupPlaceholders() {
        addPlaceholder(course_code,     "e.g. BSIT101");
        addPlaceholder(course_name,     "e.g. BS Information Technology");
        addPlaceholder(number_of_units, "e.g. 3");
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

    // ── Load table ────────────────────────────────────────────────────────────
    private void loadTable() {
        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
            ResultSet rs = con.prepareStatement(
                "SELECT * FROM courses ORDER BY id DESC").executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("course_code"),
                    rs.getString("course_name"),
                    rs.getString("units"),
                    rs.getString("course_type"),
                    rs.getString("status"),
                    rs.getString("description")
                });
            }
        } catch (Exception e) { /* silent */ }
    }

    // ── Click row → fill form ─────────────────────────────────────────────────
    private void tableRowClicked() {
        int row = jTable1.getSelectedRow();
        if (row == -1) return;
        int mr = jTable1.convertRowIndexToModel(row);
        javax.swing.table.DefaultTableModel m =
            (javax.swing.table.DefaultTableModel) jTable1.getModel();

        setField(course_code,     m.getValueAt(mr,1), "e.g. BSIT101");
        setField(course_name,     m.getValueAt(mr,2), "e.g. BS Information Technology");
        setField(number_of_units, m.getValueAt(mr,3), "e.g. 3");
        course_type.setSelectedItem(m.getValueAt(mr,4));
        course_status.setSelectedItem(m.getValueAt(mr,5));
        Object desc = m.getValueAt(mr,6);
        course_description.setText(desc != null ? desc.toString() : "");
    }

    private void setField(javax.swing.JTextField f, Object val, String ph) {
        String v = val != null ? val.toString() : "";
        if (v.isEmpty()) { f.setForeground(new Color(160,170,190)); f.setText(ph); }
        else             { f.setForeground(new Color(10,40,80));    f.setText(v); }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1          = new javax.swing.JPanel();
        headerPanel      = new javax.swing.JPanel();
        headerLabel      = new javax.swing.JLabel();
        accentLine       = new javax.swing.JPanel();
        course_code      = new javax.swing.JTextField();
        course_name      = new javax.swing.JTextField();
        number_of_units  = new javax.swing.JTextField();
        course_type      = new javax.swing.JComboBox<>();
        course_status    = new javax.swing.JComboBox<>();
        jScrollPane1     = new javax.swing.JScrollPane();
        course_description = new javax.swing.JTextArea();
        jButton3         = new javax.swing.JButton();
        jButton4         = new javax.swing.JButton();
        jButton5         = new javax.swing.JButton();
        deleteButton     = new javax.swing.JButton();
        lblSearch        = new javax.swing.JLabel();
        txtSearch        = new javax.swing.JTextField();
        jScrollPane2     = new javax.swing.JScrollPane();
        jTable1          = new javax.swing.JTable();

        // Legacy labels kept for GEN compatibility
        jLabel1  = new javax.swing.JLabel();
        jLabel3  = new javax.swing.JLabel();
        jLabel4  = new javax.swing.JLabel();
        jLabel6  = new javax.swing.JLabel();
        jLabel7  = new javax.swing.JLabel();
        jLabel8  = new javax.swing.JLabel();
        jLabel9  = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel3  = new javax.swing.JPanel();

        // ── Frame ─────────────────────────────────────────────────────────────
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EDP Attendance System — Course Entry");
        setMinimumSize(new Dimension(780, 680));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new Color(240, 243, 250));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // ── Header ────────────────────────────────────────────────────────────
        headerPanel.setBackground(new Color(8, 35, 70));
        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setText("  Course Entry Form");
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

        // Row 1 — Course Code | Course Name | Units
        javax.swing.JLabel lCode = new javax.swing.JLabel("Course Code:");
        lCode.setFont(lf); lCode.setForeground(lc);
        formCard.add(lCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 120, 18));
        course_code.setFont(ff); course_code.setBorder(fb);
        course_code.setBackground(new Color(245, 247, 252));
        formCard.add(course_code, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 36, 160, 32));

        javax.swing.JLabel lName = new javax.swing.JLabel("Course Name:");
        lName.setFont(lf); lName.setForeground(lc);
        formCard.add(lName, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 15, 120, 18));
        course_name.setFont(ff); course_name.setBorder(fb);
        course_name.setBackground(new Color(245, 247, 252));
        formCard.add(course_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 36, 240, 32));

        javax.swing.JLabel lUnits = new javax.swing.JLabel("Units:");
        lUnits.setFont(lf); lUnits.setForeground(lc);
        formCard.add(lUnits, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 15, 60, 18));
        number_of_units.setFont(ff); number_of_units.setBorder(fb);
        number_of_units.setBackground(new Color(245, 247, 252));
        formCard.add(number_of_units, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 36, 80, 32));

        // Row 2 — Type | Status | Description
        javax.swing.JLabel lType = new javax.swing.JLabel("Course Type:");
        lType.setFont(lf); lType.setForeground(lc);
        formCard.add(lType, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 82, 110, 18));
        course_type.setModel(new javax.swing.DefaultComboBoxModel<>(
            new String[]{"Core", "Major", "Elective"}));
        course_type.setFont(ff);
        formCard.add(course_type, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 103, 155, 32));

        javax.swing.JLabel lStatus = new javax.swing.JLabel("Status:");
        lStatus.setFont(lf); lStatus.setForeground(lc);
        formCard.add(lStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 82, 80, 18));
        course_status.setModel(new javax.swing.DefaultComboBoxModel<>(
            new String[]{"Active", "Inactive"}));
        course_status.setFont(ff);
        formCard.add(course_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 103, 130, 32));

        javax.swing.JLabel lDesc = new javax.swing.JLabel("Description (optional):");
        lDesc.setFont(lf); lDesc.setForeground(lc);
        formCard.add(lDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 82, 180, 18));
        course_description.setFont(ff);
        course_description.setRows(3);
        course_description.setBorder(fb);
        course_description.setBackground(new Color(245, 247, 252));
        course_description.setLineWrap(true);
        course_description.setWrapStyleWord(true);
        jScrollPane1.setViewportView(course_description);
        formCard.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 103, 390, 60));

        jPanel1.add(formCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 760, 175));

        // ── Buttons row ───────────────────────────────────────────────────────
        jButton4 = new javax.swing.JButton() {
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
        jButton4.setFocusPainted(false); jButton4.setContentAreaFilled(false);
        jButton4.setBorderPainted(false); jButton4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButton4.addActionListener(e -> jButton4ActionPerformed(e));
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 242, 90, 34));

        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        deleteButton.setText("DELETE");
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> deleteRecord());
        jPanel1.add(deleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 242, 90, 34));

        jButton5.setFont(new Font("Segoe UI", Font.BOLD, 11));
        jButton5.setText("CLEAR");
        jButton5.setFocusPainted(false);
        jButton5.addActionListener(e -> jButton5ActionPerformed(e));
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 242, 90, 34));

        jButton3.setFont(new Font("Segoe UI", Font.BOLD, 11));
        jButton3.setText("EXIT");
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(e -> jButton3ActionPerformed(e));
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 242, 90, 34));

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
            public void keyReleased(java.awt.event.KeyEvent e) {
                String q = txtSearch.getText().trim();
                javax.swing.table.DefaultTableModel model =
                    (javax.swing.table.DefaultTableModel) jTable1.getModel();
                javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> tr =
                    new javax.swing.table.TableRowSorter<>(model);
                jTable1.setRowSorter(tr);
                tr.setRowFilter(q.isEmpty() ? null :
                    javax.swing.RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(q)));
            }
        });
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 289, 300, 30));

        // ── Table ─────────────────────────────────────────────────────────────
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Code", "Course Name", "Units", "Type", "Status", "Description"}
        ) { @Override public boolean isCellEditable(int r, int c) { return false; } });
        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        jTable1.setRowHeight(24);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        jTable1.getTableHeader().setBackground(new Color(8, 35, 70));
        jTable1.getTableHeader().setForeground(Color.WHITE);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { tableRowClicked(); }
        });
        jScrollPane2.setViewportView(jTable1);
        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 328, 760, 335));

        // Hide legacy
        jLabel1.setVisible(false);  jPanel1.add(jLabel1,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel3.setVisible(false);  jPanel1.add(jLabel3,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel4.setVisible(false);  jPanel1.add(jLabel4,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel6.setVisible(false);  jPanel1.add(jLabel6,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel7.setVisible(false);  jPanel1.add(jLabel7,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel8.setVisible(false);  jPanel1.add(jLabel8,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel9.setVisible(false);  jPanel1.add(jLabel9,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel10.setVisible(false); jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jPanel3.setVisible(false);  jPanel1.add(jPanel3,  new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 680));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // ── Save Course ───────────────────────────────────────────────────────────
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        String code  = getVal(course_code,     "e.g. BSIT101");
        String cname = getVal(course_name,     "e.g. BS Information Technology");
        String units = getVal(number_of_units, "e.g. 3");
        String type  = course_type.getSelectedItem().toString();
        String status= course_status.getSelectedItem().toString();
        String desc  = course_description.getText().trim();

        if (code.isEmpty() || cname.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in Course Code and Course Name.",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
            PreparedStatement pst = con.prepareStatement(
                "INSERT INTO courses (course_code, course_name, units, course_type, status, description) VALUES (?,?,?,?,?,?)");
            pst.setString(1, code);
            pst.setString(2, cname);
            pst.setString(3, units.isEmpty() ? null : units);
            pst.setString(4, type);
            pst.setString(5, status);
            pst.setString(6, desc.isEmpty() ? null : desc);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Course saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadTable();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Delete Course ─────────────────────────────────────────────────────────
    private void deleteRecord() {
        int row = jTable1.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a course to delete."); return; }
        int mr = jTable1.convertRowIndexToModel(row);
        int id = (int) jTable1.getModel().getValueAt(mr, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete this course?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
                PreparedStatement pst = con.prepareStatement("DELETE FROM courses WHERE id=?");
                pst.setInt(1, id);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Course deleted successfully!");
                loadTable();
                clearFields();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    // ── Clear ─────────────────────────────────────────────────────────────────
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) { clearFields(); }

    private void clearFields() {
        course_code.setForeground(new Color(160,170,190));     course_code.setText("e.g. BSIT101");
        course_name.setForeground(new Color(160,170,190));     course_name.setText("e.g. BS Information Technology");
        number_of_units.setForeground(new Color(160,170,190)); number_of_units.setText("e.g. 3");
        course_type.setSelectedIndex(0);
        course_status.setSelectedIndex(0);
        course_description.setText("");
        txtSearch.setText("");
        jTable1.clearSelection();
    }

    // ── Exit ──────────────────────────────────────────────────────────────────
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Return to Dashboard?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            new Dashboard().setVisible(true);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new CourseEntryForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel         accentLine;
    private javax.swing.JTextField     course_code;
    private javax.swing.JTextArea      course_description;
    private javax.swing.JTextField     course_name;
    private javax.swing.JComboBox<String> course_status;
    private javax.swing.JComboBox<String> course_type;
    private javax.swing.JButton        deleteButton;
    private javax.swing.JPanel         headerPanel;
    private javax.swing.JLabel         headerLabel;
    private javax.swing.JButton        jButton3;
    private javax.swing.JButton        jButton4;
    private javax.swing.JButton        jButton5;
    private javax.swing.JLabel         jLabel1;
    private javax.swing.JLabel         jLabel3;
    private javax.swing.JLabel         jLabel4;
    private javax.swing.JLabel         jLabel6;
    private javax.swing.JLabel         jLabel7;
    private javax.swing.JLabel         jLabel8;
    private javax.swing.JLabel         jLabel9;
    private javax.swing.JLabel         jLabel10;
    private javax.swing.JPanel         jPanel1;
    private javax.swing.JPanel         jPanel3;
    private javax.swing.JScrollPane    jScrollPane1;
    private javax.swing.JScrollPane    jScrollPane2;
    private javax.swing.JTable         jTable1;
    private javax.swing.JLabel         lblSearch;
    private javax.swing.JTextField     number_of_units;
    private javax.swing.JTextField     txtSearch;
    // End of variables declaration//GEN-END:variables
}