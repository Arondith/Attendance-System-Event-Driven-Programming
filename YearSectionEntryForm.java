package templonuevocharlesluke;

import javax.swing.JOptionPane;
import java.sql.*;
import java.awt.*;

public class YearSectionEntryForm extends javax.swing.JFrame {

    public YearSectionEntryForm() {
        initComponents();
         setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); // ← add this
        setupPlaceholders();
        loadTableData();
    }

    // ── Placeholders ──────────────────────────────────────────────────────────
    private void setupPlaceholders() {
        addPlaceholder(txtSectionName,  "e.g. Section A");
        addPlaceholder(txtAcademicYear, "e.g. 2025-2026");
        addPlaceholder(txtAdviser,      "Adviser Full Name");
        addPlaceholder(txtMaxStudents,  "e.g. 40");
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

    // ── Load data into table ──────────────────────────────────────────────────
    private void loadTableData() {
        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
            String sql = "SELECT * FROM year_sections ORDER BY id DESC";
            ResultSet rs = con.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("year_level"),
                    rs.getString("section_name"),
                    rs.getString("academic_year"),
                    rs.getString("adviser"),
                    rs.getString("max_students")
                });
            }
        } catch (Exception e) { /* table stays empty */ }
    }

    // ── Click row → fill form ─────────────────────────────────────────────────
    private void tableRowClicked() {
        int row = jTable1.getSelectedRow();
        if (row == -1) return;
        int mr = jTable1.convertRowIndexToModel(row);
        javax.swing.table.DefaultTableModel m =
            (javax.swing.table.DefaultTableModel) jTable1.getModel();
        cmbYearLevel.setSelectedItem(m.getValueAt(mr, 1));
        txtSectionName.setText(String.valueOf(m.getValueAt(mr, 2)));  txtSectionName.setForeground(new Color(10,40,80));
        txtAcademicYear.setText(String.valueOf(m.getValueAt(mr, 3))); txtAcademicYear.setForeground(new Color(10,40,80));
        txtAdviser.setText(String.valueOf(m.getValueAt(mr, 4)));      txtAdviser.setForeground(new Color(10,40,80));
        txtMaxStudents.setText(String.valueOf(m.getValueAt(mr, 5)));  txtMaxStudents.setForeground(new Color(10,40,80));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1        = new javax.swing.JPanel();
        headerPanel    = new javax.swing.JPanel();
        headerLabel    = new javax.swing.JLabel();
        accentLine     = new javax.swing.JPanel();
        lblYearLevel   = new javax.swing.JLabel();
        cmbYearLevel   = new javax.swing.JComboBox<>();
        lblSectionName = new javax.swing.JLabel();
        txtSectionName = new javax.swing.JTextField();
        lblAcadYear    = new javax.swing.JLabel();
        txtAcademicYear= new javax.swing.JTextField();
        lblAdviser     = new javax.swing.JLabel();
        txtAdviser     = new javax.swing.JTextField();
        lblMaxStudents = new javax.swing.JLabel();
        txtMaxStudents = new javax.swing.JTextField();
        saveButton     = new javax.swing.JButton();
        clearButton    = new javax.swing.JButton();
        deleteButton   = new javax.swing.JButton();
        exitButton     = new javax.swing.JButton();
        lblSearch      = new javax.swing.JLabel();
        txtSearch      = new javax.swing.JTextField();
        jScrollPane1   = new javax.swing.JScrollPane();
        jTable1        = new javax.swing.JTable();

        // ── Frame ─────────────────────────────────────────────────────────────
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EDP Attendance System — Year & Section");
        setMinimumSize(new Dimension(780, 620));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // ── Main panel ────────────────────────────────────────────────────────
        jPanel1.setBackground(new Color(240, 243, 250));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // ── Header ────────────────────────────────────────────────────────────
        headerPanel.setBackground(new Color(8, 35, 70));
        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setText("  Year & Section Entry Form");
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

        // Row 1 — Year Level | Section Name | Academic Year
        lblYearLevel.setFont(lf); lblYearLevel.setForeground(lc);
        lblYearLevel.setText("Year Level:");
        formCard.add(lblYearLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 20, 120, 18));

        cmbYearLevel.setModel(new javax.swing.DefaultComboBoxModel<>(
            new String[]{"1st Year", "2nd Year", "3rd Year", "4th Year"}));
        cmbYearLevel.setFont(ff);
        formCard.add(cmbYearLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 41, 140, 32));

        lblSectionName.setFont(lf); lblSectionName.setForeground(lc);
        lblSectionName.setText("Section Name:");
        formCard.add(lblSectionName, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 20, 120, 18));

        txtSectionName.setFont(ff); txtSectionName.setBorder(fb);
        txtSectionName.setBackground(new Color(245, 247, 252));
        formCard.add(txtSectionName, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 41, 160, 32));

        lblAcadYear.setFont(lf); lblAcadYear.setForeground(lc);
        lblAcadYear.setText("Academic Year:");
        formCard.add(lblAcadYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 20, 130, 18));

        txtAcademicYear.setFont(ff); txtAcademicYear.setBorder(fb);
        txtAcademicYear.setBackground(new Color(245, 247, 252));
        formCard.add(txtAcademicYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 41, 160, 32));

        // Row 2 — Adviser | Max Students
        lblAdviser.setFont(lf); lblAdviser.setForeground(lc);
        lblAdviser.setText("Adviser Name:");
        formCard.add(lblAdviser, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 88, 130, 18));

        txtAdviser.setFont(ff); txtAdviser.setBorder(fb);
        txtAdviser.setBackground(new Color(245, 247, 252));
        formCard.add(txtAdviser, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 109, 300, 32));

        lblMaxStudents.setFont(lf); lblMaxStudents.setForeground(lc);
        lblMaxStudents.setText("Maximum Students:");
        formCard.add(lblMaxStudents, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 88, 150, 18));

        txtMaxStudents.setFont(ff); txtMaxStudents.setBorder(fb);
        txtMaxStudents.setBackground(new Color(245, 247, 252));
        formCard.add(txtMaxStudents, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 109, 180, 32));

        jPanel1.add(formCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 760, 160));

        // ── Buttons row ───────────────────────────────────────────────────────
        // Custom painted SAVE button
        saveButton = new javax.swing.JButton() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(0,60,110) : new Color(8,35,70));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString("SAVE", (getWidth()-fm.stringWidth("SAVE"))/2,
                    (getHeight()+fm.getAscent()-fm.getDescent())/2);
                g2.dispose();
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        saveButton.setFocusPainted(false); saveButton.setContentAreaFilled(false);
        saveButton.setBorderPainted(false); saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.addActionListener(e -> saveRecord());
        jPanel1.add(saveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 228, 90, 34));

        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        clearButton.setText("CLEAR");
        clearButton.setFocusPainted(false);
        clearButton.addActionListener(e -> clearFields());
        jPanel1.add(clearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 228, 90, 34));

        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        deleteButton.setText("DELETE");
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> deleteRecord());
        jPanel1.add(deleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 228, 90, 34));

        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        exitButton.setText("EXIT");
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this,
                "Return to Dashboard?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) { new Dashboard().setVisible(true); this.dispose(); }
        });
        jPanel1.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 228, 90, 34));

        // ── Search row ────────────────────────────────────────────────────────
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblSearch.setForeground(lc);
        lblSearch.setText("Search:");
        jPanel1.add(lblSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 278, 60, 20));

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
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 275, 300, 30));

        // ── Table ─────────────────────────────────────────────────────────────
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Year Level", "Section Name", "Academic Year", "Adviser", "Max Students"}
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
        jScrollPane1.setViewportView(jTable1);
        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 315, 760, 285));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 620));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // ── Save record ───────────────────────────────────────────────────────────
    private void saveRecord() {
        String yrLevel  = cmbYearLevel.getSelectedItem().toString();
        String secName  = getVal(txtSectionName,  "e.g. Section A");
        String acadYear = getVal(txtAcademicYear, "e.g. 2025-2026");
        String adviser  = getVal(txtAdviser,      "Adviser Full Name");
        String maxStu   = getVal(txtMaxStudents,  "e.g. 40");

        if (secName.isEmpty() || acadYear.isEmpty() || adviser.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all required fields.\n(Section Name, Academic Year, Adviser)",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
            String sql = "INSERT INTO year_sections (year_level, section_name, academic_year, adviser, max_students) VALUES (?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, yrLevel);
            pst.setString(2, secName);
            pst.setString(3, acadYear);
            pst.setString(4, adviser);
            pst.setString(5, maxStu.isEmpty() ? null : maxStu);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Year & Section saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadTableData();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Delete record ─────────────────────────────────────────────────────────
    private void deleteRecord() {
        int row = jTable1.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a row to delete."); return; }
        int mr = jTable1.convertRowIndexToModel(row);
        String id = jTable1.getModel().getValueAt(mr, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete this record?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
                PreparedStatement pst = con.prepareStatement("DELETE FROM year_sections WHERE id=?");
                pst.setString(1, id);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Record deleted successfully!");
                loadTableData();
                clearFields();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    // ── Clear fields ──────────────────────────────────────────────────────────
    private void clearFields() {
        cmbYearLevel.setSelectedIndex(0);
        txtSectionName.setForeground(new Color(160,170,190));  txtSectionName.setText("e.g. Section A");
        txtAcademicYear.setForeground(new Color(160,170,190)); txtAcademicYear.setText("e.g. 2025-2026");
        txtAdviser.setForeground(new Color(160,170,190));      txtAdviser.setText("Adviser Full Name");
        txtMaxStudents.setForeground(new Color(160,170,190));  txtMaxStudents.setText("e.g. 40");
        txtSearch.setText("");
        jTable1.clearSelection();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new YearSectionEntryForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel         accentLine;
    private javax.swing.JButton        clearButton;
    private javax.swing.JComboBox<String> cmbYearLevel;
    private javax.swing.JButton        deleteButton;
    private javax.swing.JButton        exitButton;
    private javax.swing.JPanel         headerPanel;
    private javax.swing.JLabel         headerLabel;
    private javax.swing.JPanel         jPanel1;
    private javax.swing.JScrollPane    jScrollPane1;
    private javax.swing.JTable         jTable1;
    private javax.swing.JLabel         lblAcadYear;
    private javax.swing.JLabel         lblAdviser;
    private javax.swing.JLabel         lblMaxStudents;
    private javax.swing.JLabel         lblSearch;
    private javax.swing.JLabel         lblSectionName;
    private javax.swing.JLabel         lblYearLevel;
    private javax.swing.JButton        saveButton;
    private javax.swing.JTextField     txtAcademicYear;
    private javax.swing.JTextField     txtAdviser;
    private javax.swing.JTextField     txtMaxStudents;
    private javax.swing.JTextField     txtSearch;
    private javax.swing.JTextField     txtSectionName;
    // End of variables declaration//GEN-END:variables
}