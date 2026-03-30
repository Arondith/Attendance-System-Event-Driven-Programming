package templonuevocharlesluke;

import javax.swing.JOptionPane;
import java.sql.*;

public class SchoolEventsEntryForm extends javax.swing.JFrame {

    public SchoolEventsEntryForm() {
        initComponents();
         setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); // ← add this
        loadDepartments();
    }

    // ── Load departments into dropdown ────────────────────────────────────────
    private void loadDepartments() {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
            String sql = "SELECT dept_name FROM departments ORDER BY dept_name";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            cmbDepartment.removeAllItems();
            cmbDepartment.addItem("-- Select Department --");
            while (rs.next()) {
                cmbDepartment.addItem(rs.getString("dept_name"));
            }
        } catch (Exception e) {
            // fallback — keep placeholder only
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1        = new javax.swing.JPanel();
        headerPanel    = new javax.swing.JPanel();
        headerLabel    = new javax.swing.JLabel();
        jScrollPane1   = new javax.swing.JScrollPane();
        jTable1        = new javax.swing.JTable();

        // Form fields
        lblAcadYear    = new javax.swing.JLabel();
        cmbAcadYear    = new javax.swing.JComboBox<>();
        lblDepartment  = new javax.swing.JLabel();
        cmbDepartment  = new javax.swing.JComboBox<>();
        lblEventName   = new javax.swing.JLabel();
        txtEventName   = new javax.swing.JTextField();
        lblStartDate   = new javax.swing.JLabel();
        txtStartDate   = new javax.swing.JTextField();
        lblEndDate     = new javax.swing.JLabel();
        txtEndDate     = new javax.swing.JTextField();
        lblStartTime   = new javax.swing.JLabel();
        txtStartTime   = new javax.swing.JTextField();
        lblEndTime     = new javax.swing.JLabel();
        txtEndTime     = new javax.swing.JTextField();
        lblGracePeriod = new javax.swing.JLabel();
        txtGracePeriod = new javax.swing.JTextField();
        lblPenalty     = new javax.swing.JLabel();
        txtPenalty     = new javax.swing.JTextField();

        // Buttons
        saveButton     = new javax.swing.JButton();
        clearButton    = new javax.swing.JButton();
        deleteButton   = new javax.swing.JButton();
        exitButton     = new javax.swing.JButton();

        // ── Frame ─────────────────────────────────────────────────────────────
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EDP Attendance System — Events");
        setMinimumSize(new java.awt.Dimension(750, 620));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // ── Main panel ────────────────────────────────────────────────────────
        jPanel1.setBackground(new java.awt.Color(240, 243, 250));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // ── Header bar — navy matching login ──────────────────────────────────
        headerPanel.setBackground(new java.awt.Color(8, 35, 70));
        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        headerLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        headerLabel.setForeground(java.awt.Color.WHITE);
        headerLabel.setText("  School Events Entry Form");
        headerPanel.add(headerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 45));

        jPanel1.add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 45));

        // ── Teal accent line ──────────────────────────────────────────────────
        javax.swing.JPanel accent = new javax.swing.JPanel();
        accent.setBackground(new java.awt.Color(0, 190, 210));
        jPanel1.add(accent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 45, 750, 3));

        // ── White form card ───────────────────────────────────────────────────
        javax.swing.JPanel formCard = new javax.swing.JPanel();
        formCard.setBackground(java.awt.Color.WHITE);
        formCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 225, 235), 1));
        formCard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        java.awt.Font labelFont = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11);
        java.awt.Font fieldFont = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12);
        java.awt.Color labelColor = new java.awt.Color(8, 35, 70);
        javax.swing.border.Border fieldBorder = javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 210, 225), 1),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)
        );

        // Row 1 — Academic Year | Department
        lblAcadYear.setFont(labelFont); lblAcadYear.setForeground(labelColor);
        lblAcadYear.setText("Academic Year:");
        formCard.add(lblAcadYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 130, 20));

        cmbAcadYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            "2023-2024", "2024-2025", "2025-2026", "2026-2027"
        }));
        cmbAcadYear.setFont(fieldFont);
        cmbAcadYear.setSelectedItem("2025-2026");
        formCard.add(cmbAcadYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 43, 160, 32));

        lblDepartment.setFont(labelFont); lblDepartment.setForeground(labelColor);
        lblDepartment.setText("Department:");
        formCard.add(lblDepartment, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 130, 20));

        cmbDepartment.setFont(fieldFont);
        formCard.add(cmbDepartment, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 43, 220, 32));

        // Row 2 — Event Name (full width)
        lblEventName.setFont(labelFont); lblEventName.setForeground(labelColor);
        lblEventName.setText("Event Name:");
        formCard.add(lblEventName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 130, 20));

        txtEventName.setFont(fieldFont); txtEventName.setBorder(fieldBorder);
        txtEventName.setBackground(new java.awt.Color(245, 247, 252));
        formCard.add(txtEventName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 113, 660, 32));

        // Row 3 — Start Date | End Date
        lblStartDate.setFont(labelFont); lblStartDate.setForeground(labelColor);
        lblStartDate.setText("Start Date (YYYY-MM-DD):");
        formCard.add(lblStartDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 200, 20));

        txtStartDate.setFont(fieldFont); txtStartDate.setBorder(fieldBorder);
        txtStartDate.setBackground(new java.awt.Color(245, 247, 252));
        txtStartDate.setText("2025-01-10");
        formCard.add(txtStartDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 183, 200, 32));

        lblEndDate.setFont(labelFont); lblEndDate.setForeground(labelColor);
        lblEndDate.setText("End Date (YYYY-MM-DD):");
        formCard.add(lblEndDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, 200, 20));

        txtEndDate.setFont(fieldFont); txtEndDate.setBorder(fieldBorder);
        txtEndDate.setBackground(new java.awt.Color(245, 247, 252));
        txtEndDate.setText("2025-01-10");
        formCard.add(txtEndDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 183, 200, 32));

        // Row 4 — Start Time | End Time
        lblStartTime.setFont(labelFont); lblStartTime.setForeground(labelColor);
        lblStartTime.setText("Start Time (HH:MM):");
        formCard.add(lblStartTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 180, 20));

        txtStartTime.setFont(fieldFont); txtStartTime.setBorder(fieldBorder);
        txtStartTime.setBackground(new java.awt.Color(245, 247, 252));
        txtStartTime.setText("08:00");
        formCard.add(txtStartTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 253, 180, 32));

        lblEndTime.setFont(labelFont); lblEndTime.setForeground(labelColor);
        lblEndTime.setText("End Time (HH:MM):");
        formCard.add(lblEndTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 230, 180, 20));

        txtEndTime.setFont(fieldFont); txtEndTime.setBorder(fieldBorder);
        txtEndTime.setBackground(new java.awt.Color(245, 247, 252));
        txtEndTime.setText("10:00");
        formCard.add(txtEndTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 253, 180, 32));

        // Row 5 — Grace Period | Penalty Amount
        lblGracePeriod.setFont(labelFont); lblGracePeriod.setForeground(labelColor);
        lblGracePeriod.setText("Grace Period (minutes):");
        formCard.add(lblGracePeriod, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 180, 20));

        txtGracePeriod.setFont(fieldFont); txtGracePeriod.setBorder(fieldBorder);
        txtGracePeriod.setBackground(new java.awt.Color(245, 247, 252));
        txtGracePeriod.setText("15");
        formCard.add(txtGracePeriod, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 323, 180, 32));

        lblPenalty.setFont(labelFont); lblPenalty.setForeground(labelColor);
        lblPenalty.setText("Penalty Amount:");
        formCard.add(lblPenalty, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 300, 180, 20));

        txtPenalty.setFont(fieldFont); txtPenalty.setBorder(fieldBorder);
        txtPenalty.setBackground(new java.awt.Color(245, 247, 252));
        txtPenalty.setText("50");
        formCard.add(txtPenalty, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 323, 180, 32));

        jPanel1.add(formCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 730, 375));

        // ── Buttons ───────────────────────────────────────────────────────────
        saveButton = new javax.swing.JButton() {
            @Override protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new java.awt.Color(0, 60, 110) : new java.awt.Color(8, 35, 70));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(java.awt.Color.WHITE);
                g2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
                java.awt.FontMetrics fm = g2.getFontMetrics();
                g2.drawString("SAVE", (getWidth()-fm.stringWidth("SAVE"))/2, (getHeight()+fm.getAscent()-fm.getDescent())/2);
                g2.dispose();
            }
            @Override protected void paintBorder(java.awt.Graphics g) {}
        };
        saveButton.setFocusPainted(false); saveButton.setContentAreaFilled(false);
        saveButton.setBorderPainted(false); saveButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveButton.addActionListener(e -> saveButtonActionPerformed(e));
        jPanel1.add(saveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 445, 100, 36));

        clearButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        clearButton.setText("CLEAR");
        clearButton.setFocusPainted(false);
        clearButton.addActionListener(e -> clearButtonActionPerformed(e));
        jPanel1.add(clearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 445, 100, 36));

        deleteButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        deleteButton.setText("DELETE");
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> deleteButtonActionPerformed(e));
        jPanel1.add(deleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 445, 100, 36));

        exitButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        exitButton.setText("EXIT");
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> exitButtonActionPerformed(e));
        jPanel1.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 445, 100, 36));

        // ── Events table ──────────────────────────────────────────────────────
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Academic Year", "Department", "Event Name", "Start Date", "End Date", "Start Time", "End Time", "Grace", "Penalty"}
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        jTable1.setRowHeight(24);
        jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        jTable1.getTableHeader().setBackground(new java.awt.Color(8, 35, 70));
        jTable1.getTableHeader().setForeground(java.awt.Color.WHITE);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { tableRowClicked(); }
        });
        jScrollPane1.setViewportView(jTable1);
        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, 730, 120));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 620));

        pack();
        setLocationRelativeTo(null);
        loadEvents();
    }// </editor-fold>//GEN-END:initComponents

    // ── Load events into table ────────────────────────────────────────────────
    private void loadEvents() {
        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
            String sql = "SELECT * FROM events ORDER BY id DESC";
            ResultSet rs = con.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("academic_year"),
                    rs.getString("department"),
                    rs.getString("event_name"),
                    rs.getString("start_date") != null ? rs.getString("start_date") : rs.getString("event_date"),
                    rs.getString("end_date"),
                    rs.getString("start_time"),
                    rs.getString("end_time"),
                    rs.getInt("grace_period"),
                    rs.getDouble("penalty_amount")
                });
            }
        } catch (Exception e) {
            // table stays empty if DB error
        }
    }

    // ── Click row → fill form ─────────────────────────────────────────────────
    private void tableRowClicked() {
        int row = jTable1.getSelectedRow();
        if (row == -1) return;
        int modelRow = jTable1.convertRowIndexToModel(row);
        javax.swing.table.DefaultTableModel m = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        cmbAcadYear.setSelectedItem(m.getValueAt(modelRow, 1));
        cmbDepartment.setSelectedItem(m.getValueAt(modelRow, 2));
        txtEventName.setText(String.valueOf(m.getValueAt(modelRow, 3)));
        txtStartDate.setText(String.valueOf(m.getValueAt(modelRow, 4)));
        txtEndDate.setText(String.valueOf(m.getValueAt(modelRow, 5)));
        txtStartTime.setText(String.valueOf(m.getValueAt(modelRow, 6)));
        txtEndTime.setText(String.valueOf(m.getValueAt(modelRow, 7)));
        txtGracePeriod.setText(String.valueOf(m.getValueAt(modelRow, 8)));
        txtPenalty.setText(String.valueOf(m.getValueAt(modelRow, 9)));
    }

    // ── E-01: Save Event ──────────────────────────────────────────────────────
    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String acadYear   = cmbAcadYear.getSelectedItem().toString();
        String dept       = cmbDepartment.getSelectedItem().toString();
        String eventName  = txtEventName.getText().trim();
        String startDate  = txtStartDate.getText().trim();
        String endDate    = txtEndDate.getText().trim();
        String startTime  = txtStartTime.getText().trim();
        String endTime    = txtEndTime.getText().trim();
        String grace      = txtGracePeriod.getText().trim();
        String penalty    = txtPenalty.getText().trim();

        if (dept.equals("-- Select Department --") || eventName.isEmpty() ||
            startDate.isEmpty() || endDate.isEmpty() || startTime.isEmpty() ||
            endTime.isEmpty() || grace.isEmpty() || penalty.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all required fields.",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
            String sql = "INSERT INTO events (academic_year, department, event_name, start_date, end_date, start_time, end_time, grace_period, penalty_amount) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, acadYear);
            pst.setString(2, dept);
            pst.setString(3, eventName);
            pst.setString(4, startDate);
            pst.setString(5, endDate);
            pst.setString(6, startTime);
            pst.setString(7, endTime);
            pst.setInt(8, Integer.parseInt(grace));
            pst.setDouble(9, Double.parseDouble(penalty));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Event saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadEvents();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving event: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Delete Event ──────────────────────────────────────────────────────────
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an event to delete.");
            return;
        }
        int modelRow = jTable1.convertRowIndexToModel(row);
        String id = jTable1.getModel().getValueAt(modelRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete this event?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
                PreparedStatement pst = con.prepareStatement("DELETE FROM events WHERE id=?");
                pst.setString(1, id);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Event deleted successfully!");
                loadEvents();
                clearFields();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    // ── Clear fields ──────────────────────────────────────────────────────────
    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {
        clearFields();
    }

    private void clearFields() {
        cmbAcadYear.setSelectedItem("2025-2026");
        cmbDepartment.setSelectedIndex(0);
        txtEventName.setText("");
        txtStartDate.setText("2025-01-10");
        txtEndDate.setText("2025-01-10");
        txtStartTime.setText("08:00");
        txtEndTime.setText("10:00");
        txtGracePeriod.setText("15");
        txtPenalty.setText("50");
        jTable1.clearSelection();
    }

    // ── Exit → Dashboard ──────────────────────────────────────────────────────
    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Return to Dashboard?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new Dashboard().setVisible(true);
            this.dispose();
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new SchoolEventsEntryForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbAcadYear;
    private javax.swing.JComboBox<String> cmbDepartment;
    private javax.swing.JButton           clearButton;
    private javax.swing.JButton           deleteButton;
    private javax.swing.JButton           exitButton;
    private javax.swing.JPanel            headerPanel;
    private javax.swing.JLabel            headerLabel;
    private javax.swing.JPanel            jPanel1;
    private javax.swing.JScrollPane       jScrollPane1;
    private javax.swing.JTable            jTable1;
    private javax.swing.JLabel            lblAcadYear;
    private javax.swing.JLabel            lblDepartment;
    private javax.swing.JLabel            lblEndDate;
    private javax.swing.JLabel            lblEndTime;
    private javax.swing.JLabel            lblEventName;
    private javax.swing.JLabel            lblGracePeriod;
    private javax.swing.JLabel            lblPenalty;
    private javax.swing.JLabel            lblStartDate;
    private javax.swing.JLabel            lblStartTime;
    private javax.swing.JButton           saveButton;
    private javax.swing.JTextField        txtEndDate;
    private javax.swing.JTextField        txtEndTime;
    private javax.swing.JTextField        txtEventName;
    private javax.swing.JTextField        txtGracePeriod;
    private javax.swing.JTextField        txtPenalty;
    private javax.swing.JTextField        txtStartDate;
    private javax.swing.JTextField        txtStartTime;
    // End of variables declaration//GEN-END:variables
}