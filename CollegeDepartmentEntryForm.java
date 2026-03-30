package templonuevocharlesluke;

import javax.swing.JOptionPane;
import java.sql.*;
import java.awt.*;

public class CollegeDepartmentEntryForm extends javax.swing.JFrame {

    public CollegeDepartmentEntryForm() {
        initComponents();
         setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); // ← add this
        setupPlaceholders();
        loadTableData();
    }

    // ── Placeholders ──────────────────────────────────────────────────────────
    private void setupPlaceholders() {
        addPlaceholder(dataOne,   "e.g. BSIT");
        addPlaceholder(dataTwo,   "e.g. BS Information Technology");
        addPlaceholder(dataThree, "Dean / Head Full Name");
        addPlaceholder(dataFour,  "e.g. Room 101, Building A");
        addPlaceholder(dataFive,  "email@example.com");
        addPlaceholder(dataSix,   "e.g. 09123456789");
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
    private void loadTableData() {
        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
            ResultSet rs = con.prepareStatement("SELECT * FROM college_departments ORDER BY id DESC").executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("dept_code"),
                    rs.getString("dept_name"),
                    rs.getString("dean_name"),
                    rs.getString("office_location"),
                    rs.getString("contact_email"),
                    rs.getString("contact_number"),
                    rs.getString("status")
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

        setField(dataOne,   m.getValueAt(mr,1), "e.g. BSIT");
        setField(dataTwo,   m.getValueAt(mr,2), "e.g. BS Information Technology");
        setField(dataThree, m.getValueAt(mr,3), "Dean / Head Full Name");
        setField(dataFour,  m.getValueAt(mr,4), "e.g. Room 101, Building A");
        setField(dataFive,  m.getValueAt(mr,5), "email@example.com");
        setField(dataSix,   m.getValueAt(mr,6), "e.g. 09123456789");
        status_one.setSelectedItem(m.getValueAt(mr,7));
    }

    private void setField(javax.swing.JTextField f, Object val, String ph) {
        String v = val != null ? val.toString() : "";
        if (v.isEmpty()) { f.setForeground(new Color(160,170,190)); f.setText(ph); }
        else             { f.setForeground(new Color(10,40,80));    f.setText(v); }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1     = new javax.swing.JPanel();
        headerPanel = new javax.swing.JPanel();
        headerLabel = new javax.swing.JLabel();
        accentLine  = new javax.swing.JPanel();
        dataOne     = new javax.swing.JTextField();
        dataTwo     = new javax.swing.JTextField();
        dataThree   = new javax.swing.JTextField();
        dataFour    = new javax.swing.JTextField();
        dataFive    = new javax.swing.JTextField();
        dataSix     = new javax.swing.JTextField();
        status_one  = new javax.swing.JComboBox<>();
        jLabel1     = new javax.swing.JLabel();
        jLabel2     = new javax.swing.JLabel();
        jLabel3     = new javax.swing.JLabel();
        jLabel4     = new javax.swing.JLabel();
        jLabel5     = new javax.swing.JLabel();
        jLabel6     = new javax.swing.JLabel();
        jLabel7     = new javax.swing.JLabel();
        jLabel8     = new javax.swing.JLabel();
        jLabel9     = new javax.swing.JLabel();
        jButton1    = new javax.swing.JButton();
        jButton2    = new javax.swing.JButton();
        jButton3    = new javax.swing.JButton();
        lblSearch   = new javax.swing.JLabel();
        txtSearch   = new javax.swing.JTextField();
        deleteButton= new javax.swing.JButton();
        updateButton= new javax.swing.JButton();
        jScrollPane1= new javax.swing.JScrollPane();
        jTable1     = new javax.swing.JTable();
        jPanel2     = new javax.swing.JPanel();

        // ── Frame ─────────────────────────────────────────────────────────────
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EDP Attendance System — College Department");
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
        headerLabel.setText("  College Department Entry Form");
        headerPanel.add(headerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,780,45));
        jPanel1.add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,780,45));

        accentLine.setBackground(new Color(0, 190, 210));
        jPanel1.add(accentLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,45,780,3));

        // ── Form card ─────────────────────────────────────────────────────────
        javax.swing.JPanel formCard = new javax.swing.JPanel();
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(220,225,235),1));
        formCard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Font lf = new Font("Segoe UI", Font.BOLD, 11);
        Font ff = new Font("Segoe UI", Font.PLAIN, 12);
        Color lc = new Color(8, 35, 70);
        javax.swing.border.Border fb = javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new Color(200,210,225),1),
            javax.swing.BorderFactory.createEmptyBorder(4,8,4,8));

        // Row 1 — Dept Code | Dept Name | Dean
        jLabel6.setFont(lf); jLabel6.setForeground(lc); jLabel6.setText("Dept Code:");
        formCard.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(15,15,110,18));
        dataOne.setFont(ff); dataOne.setBorder(fb); dataOne.setBackground(new Color(245,247,252));
        formCard.add(dataOne, new org.netbeans.lib.awtextra.AbsoluteConstraints(15,36,140,32));

        jLabel5.setFont(lf); jLabel5.setForeground(lc); jLabel5.setText("Dept Name:");
        formCard.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170,15,130,18));
        dataTwo.setFont(ff); dataTwo.setBorder(fb); dataTwo.setBackground(new Color(245,247,252));
        formCard.add(dataTwo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170,36,220,32));

        jLabel3.setFont(lf); jLabel3.setForeground(lc); jLabel3.setText("Dean / Head:");
        formCard.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(405,15,120,18));
        dataThree.setFont(ff); dataThree.setBorder(fb); dataThree.setBackground(new Color(245,247,252));
        formCard.add(dataThree, new org.netbeans.lib.awtextra.AbsoluteConstraints(405,36,200,32));

        // Row 2 — Office | Email | Contact | Status
        jLabel9.setFont(lf); jLabel9.setForeground(lc); jLabel9.setText("Office Location:");
        formCard.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(15,82,130,18));
        dataFour.setFont(ff); dataFour.setBorder(fb); dataFour.setBackground(new Color(245,247,252));
        formCard.add(dataFour, new org.netbeans.lib.awtextra.AbsoluteConstraints(15,103,180,32));

        jLabel8.setFont(lf); jLabel8.setForeground(lc); jLabel8.setText("Email:");
        formCard.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(210,82,80,18));
        dataFive.setFont(ff); dataFive.setBorder(fb); dataFive.setBackground(new Color(245,247,252));
        formCard.add(dataFive, new org.netbeans.lib.awtextra.AbsoluteConstraints(210,103,180,32));

        jLabel4.setFont(lf); jLabel4.setForeground(lc); jLabel4.setText("Contact:");
        formCard.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(405,82,80,18));
        dataSix.setFont(ff); dataSix.setBorder(fb); dataSix.setBackground(new Color(245,247,252));
        formCard.add(dataSix, new org.netbeans.lib.awtextra.AbsoluteConstraints(405,103,140,32));

        jLabel7.setFont(lf); jLabel7.setForeground(lc); jLabel7.setText("Status:");
        formCard.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(560,82,70,18));
        status_one.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Active","Inactive"}));
        status_one.setFont(ff);
        formCard.add(status_one, new org.netbeans.lib.awtextra.AbsoluteConstraints(560,103,130,32));

        jPanel1.add(formCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,55,760,155));

        // ── Buttons row ───────────────────────────────────────────────────────
        jButton2 = new javax.swing.JButton() {
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
        jButton2.setFocusPainted(false); jButton2.setContentAreaFilled(false);
        jButton2.setBorderPainted(false); jButton2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButton2.addActionListener(e -> jButton2ActionPerformed(e));
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,222,90,34));

        updateButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        updateButton.setText("UPDATE");
        updateButton.setFocusPainted(false);
        updateButton.addActionListener(e -> updateRecord());
        jPanel1.add(updateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(110,222,90,34));

        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        deleteButton.setText("DELETE");
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> deleteRecord());
        jPanel1.add(deleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(210,222,90,34));

        jButton3.setFont(new Font("Segoe UI", Font.BOLD, 11));
        jButton3.setText("CLEAR");
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(e -> jButton3ActionPerformed(e));
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310,222,90,34));

        jButton1.setFont(new Font("Segoe UI", Font.BOLD, 11));
        jButton1.setText("EXIT");
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(e -> jButton1ActionPerformed(e));
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670,222,90,34));

        // ── Search row ────────────────────────────────────────────────────────
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblSearch.setForeground(lc); lblSearch.setText("Search:");
        jPanel1.add(lblSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,270,60,20));

        txtSearch.setFont(ff);
        txtSearch.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new Color(200,210,225),1),
            javax.swing.BorderFactory.createEmptyBorder(4,8,4,8)));
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
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(70,267,300,30));

        // ── Table ─────────────────────────────────────────────────────────────
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"ID","Code","Name","Dean","Office","Email","Contact","Status"}
        ) { @Override public boolean isCellEditable(int r, int c) { return false; } });
        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        jTable1.setRowHeight(24);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        jTable1.getTableHeader().setBackground(new Color(8,35,70));
        jTable1.getTableHeader().setForeground(Color.WHITE);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { tableRowClicked(); }
        });
        jScrollPane1.setViewportView(jTable1);
        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,305,760,355));

        // Hidden legacy
        jLabel1.setVisible(false); jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jLabel2.setVisible(false); jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        jPanel2.setVisible(false); jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,780,680));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // ── Save ──────────────────────────────────────────────────────────────────
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        String code   = getVal(dataOne,   "e.g. BSIT");
        String name   = getVal(dataTwo,   "e.g. BS Information Technology");
        String dean   = getVal(dataThree, "Dean / Head Full Name");
        String office = getVal(dataFour,  "e.g. Room 101, Building A");
        String email  = getVal(dataFive,  "email@example.com");
        String contact= getVal(dataSix,   "e.g. 09123456789");
        String status = status_one.getSelectedItem().toString();

        if (code.isEmpty() || name.isEmpty() || dean.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in required fields.\n(Dept Code, Dept Name, Dean/Head)",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system","root","")) {
            String sql = "INSERT INTO college_departments (dept_code, dept_name, dean_name, office_location, contact_email, contact_number, status) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, code); pst.setString(2, name);
            pst.setString(3, dean); pst.setString(4, office);
            pst.setString(5, email); pst.setString(6, contact);
            pst.setString(7, status);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Department saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadTableData(); jButton3ActionPerformed(evt);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Update ────────────────────────────────────────────────────────────────
    private void updateRecord() {
        int row = jTable1.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a row to update."); return; }
        int mr = jTable1.convertRowIndexToModel(row);
        String id = jTable1.getModel().getValueAt(mr, 0).toString();

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system","root","")) {
            String sql = "UPDATE college_departments SET dept_code=?, dept_name=?, dean_name=?, office_location=?, contact_email=?, contact_number=?, status=? WHERE id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, getVal(dataOne,"e.g. BSIT"));
            pst.setString(2, getVal(dataTwo,"e.g. BS Information Technology"));
            pst.setString(3, getVal(dataThree,"Dean / Head Full Name"));
            pst.setString(4, getVal(dataFour,"e.g. Room 101, Building A"));
            pst.setString(5, getVal(dataFive,"email@example.com"));
            pst.setString(6, getVal(dataSix,"e.g. 09123456789"));
            pst.setString(7, status_one.getSelectedItem().toString());
            pst.setString(8, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Department updated successfully!");
            loadTableData(); clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // ── Delete ────────────────────────────────────────────────────────────────
    private void deleteRecord() {
        int row = jTable1.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a row to delete."); return; }
        int mr = jTable1.convertRowIndexToModel(row);
        String id = jTable1.getModel().getValueAt(mr, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this record?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/attendance_system","root","")) {
                con.prepareStatement("DELETE FROM college_departments WHERE id=" + id).executeUpdate();
                JOptionPane.showMessageDialog(this, "Deleted successfully!");
                loadTableData(); clearFields();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) { clearFields(); }

    private void clearFields() {
        dataOne.setForeground(new Color(160,170,190));   dataOne.setText("e.g. BSIT");
        dataTwo.setForeground(new Color(160,170,190));   dataTwo.setText("e.g. BS Information Technology");
        dataThree.setForeground(new Color(160,170,190)); dataThree.setText("Dean / Head Full Name");
        dataFour.setForeground(new Color(160,170,190));  dataFour.setText("e.g. Room 101, Building A");
        dataFive.setForeground(new Color(160,170,190));  dataFive.setText("email@example.com");
        dataSix.setForeground(new Color(160,170,190));   dataSix.setText("e.g. 09123456789");
        status_one.setSelectedIndex(0);
        txtSearch.setText("");
        jTable1.clearSelection();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Return to Dashboard?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) { new Dashboard().setVisible(true); this.dispose(); }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new CollegeDepartmentEntryForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel         accentLine;
    private javax.swing.JTextField     dataFive;
    private javax.swing.JTextField     dataFour;
    private javax.swing.JTextField     dataOne;
    private javax.swing.JTextField     dataSix;
    private javax.swing.JTextField     dataThree;
    private javax.swing.JTextField     dataTwo;
    private javax.swing.JButton        deleteButton;
    private javax.swing.JButton        updateButton;
    private javax.swing.JPanel         headerPanel;
    private javax.swing.JLabel         headerLabel;
    private javax.swing.JButton        jButton1;
    private javax.swing.JButton        jButton2;
    private javax.swing.JButton        jButton3;
    private javax.swing.JLabel         jLabel1;
    private javax.swing.JLabel         jLabel2;
    private javax.swing.JLabel         jLabel3;
    private javax.swing.JLabel         jLabel4;
    private javax.swing.JLabel         jLabel5;
    private javax.swing.JLabel         jLabel6;
    private javax.swing.JLabel         jLabel7;
    private javax.swing.JLabel         jLabel8;
    private javax.swing.JLabel         jLabel9;
    private javax.swing.JPanel         jPanel1;
    private javax.swing.JPanel         jPanel2;
    private javax.swing.JScrollPane    jScrollPane1;
    private javax.swing.JTable         jTable1;
    private javax.swing.JLabel         lblSearch;
    private javax.swing.JComboBox<String> status_one;
    private javax.swing.JTextField     txtSearch;
    // End of variables declaration//GEN-END:variables
}