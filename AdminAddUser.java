package templonuevocharlesluke;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.awt.*;

public class AdminAddUser extends javax.swing.JFrame {

    // FIX 1: Define FORM_WIDTH as a constant (was used but never declared)
    private static final int FORM_WIDTH = 1000;

    // FIX 2: Accept the logged-in role so exitButton navigates correctly
    private final String loggedInRole;

    public AdminAddUser(String loggedInRole) {
        this.loggedInRole = loggedInRole;
        initComponents();
        // FIX 3: Only set close operation once, here after initComponents
        // (initComponents no longer calls setDefaultCloseOperation)
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setupPlaceholders();
        loadTableData();
    }

    // Convenience no-arg constructor kept for backward compatibility
    public AdminAddUser() {
        this("Super Admin");
    }

    // ── Placeholders ──────────────────────────────────────────────────────────
    private void setupPlaceholders() {
        addPlaceholder(firstnameTextfield,    "First Name");
        addPlaceholder(middlenameTextField2,  "Middle Name");
        addPlaceholder(lastnameTextField1,    "Last Name");
        addPlaceholder(usernameTextfield1,    "Username");
        addPlaceholder(passwordTextfield,     "Password");
    }

    private void addPlaceholder(javax.swing.JTextField f, String ph) {
        f.setForeground(new Color(160, 170, 190));
        f.setText(ph);
        f.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (f.getText().equals(ph)) { f.setText(""); f.setForeground(new Color(10, 40, 80)); }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (f.getText().trim().isEmpty()) { f.setForeground(new Color(160, 170, 190)); f.setText(ph); }
            }
        });
    }

    private String getVal(javax.swing.JTextField f, String ph) {
        String v = f.getText().trim();
        return v.equals(ph) ? "" : v;
    }

    // ── Load users into table ─────────────────────────────────────────────────
    private void loadTableData() {
        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT first_name, middle_name, last_name, username, role, status FROM users";
            ResultSet rs = con.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("first_name"),
                    rs.getString("middle_name"),
                    rs.getString("last_name"),
                    rs.getString("username"),
                    rs.getString("role"),
                    rs.getString("status")
                });
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error loading table: " + e);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1              = new javax.swing.JPanel();
        headerPanel          = new javax.swing.JPanel();
        headerLabel          = new javax.swing.JLabel();
        accentLine           = new javax.swing.JPanel();
        firstnameTextfield   = new javax.swing.JTextField();
        middlenameTextField2 = new javax.swing.JTextField();
        lastnameTextField1   = new javax.swing.JTextField();
        usernameTextfield1   = new javax.swing.JTextField();
        passwordTextfield    = new javax.swing.JTextField();
        roleBox              = new javax.swing.JComboBox<>();
        jComboBox1           = new javax.swing.JComboBox<>();
        jLabel1              = new javax.swing.JLabel();
        jLabel2              = new javax.swing.JLabel();
        jLabel3              = new javax.swing.JLabel();
        jLabel4              = new javax.swing.JLabel();
        jLabel5              = new javax.swing.JLabel();
        jLabel7              = new javax.swing.JLabel();
        jLabel8              = new javax.swing.JLabel();
        lblStatus            = new javax.swing.JLabel();
        lblSearch            = new javax.swing.JLabel();
        txtSearch            = new javax.swing.JTextField();
        addUser              = new javax.swing.JButton();
        UpdateButton         = new javax.swing.JButton();
        DeleteButton         = new javax.swing.JButton();
        ClearButton          = new javax.swing.JButton();
        ClearButton1         = new javax.swing.JButton();
        exitButton           = new javax.swing.JButton();
        jScrollPane1         = new javax.swing.JScrollPane();
        jTable1              = new javax.swing.JTable();

        // ── Frame ─────────────────────────────────────────────────────────────
        // FIX 3: Removed duplicate setDefaultCloseOperation from here;
        //        it is now set once in the constructor after initComponents().
        setTitle("EDP Attendance System — User Management");
        setMinimumSize(new Dimension(FORM_WIDTH, 620));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new Color(240, 243, 250));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // ── Header ────────────────────────────────────────────────────────────
        headerPanel.setBackground(new Color(8, 35, 70));
        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setText("  User Management");
        headerPanel.add(headerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, FORM_WIDTH, 45));
        jPanel1.add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, FORM_WIDTH, 45));

        accentLine.setBackground(new Color(0, 190, 210));
        jPanel1.add(accentLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 45, FORM_WIDTH, 3));

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

        // Row 1 — First Name | Middle Name | Last Name
        jLabel5.setFont(lf); jLabel5.setForeground(lc); jLabel5.setText("First Name:");
        formCard.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 110, 18));
        firstnameTextfield.setFont(ff); firstnameTextfield.setBorder(fb);
        firstnameTextfield.setBackground(new Color(245, 247, 252));
        formCard.add(firstnameTextfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 36, 190, 32));

        jLabel7.setFont(lf); jLabel7.setForeground(lc); jLabel7.setText("Middle Name:");
        formCard.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 15, 110, 18));
        middlenameTextField2.setFont(ff); middlenameTextField2.setBorder(fb);
        middlenameTextField2.setBackground(new Color(245, 247, 252));
        formCard.add(middlenameTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 36, 190, 32));

        jLabel2.setFont(lf); jLabel2.setForeground(lc); jLabel2.setText("Last Name:");
        formCard.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 15, 110, 18));
        lastnameTextField1.setFont(ff); lastnameTextField1.setBorder(fb);
        lastnameTextField1.setBackground(new Color(245, 247, 252));
        formCard.add(lastnameTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 36, 190, 32));

        // Row 2 — Username | Password | Role | Status
        jLabel4.setFont(lf); jLabel4.setForeground(lc); jLabel4.setText("Username:");
        formCard.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 82, 110, 18));
        usernameTextfield1.setFont(ff); usernameTextfield1.setBorder(fb);
        usernameTextfield1.setBackground(new Color(245, 247, 252));
        formCard.add(usernameTextfield1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 103, 190, 32));

        jLabel1.setFont(lf); jLabel1.setForeground(lc); jLabel1.setText("Password:");
        formCard.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 82, 110, 18));
        passwordTextfield.setFont(ff); passwordTextfield.setBorder(fb);
        passwordTextfield.setBackground(new Color(245, 247, 252));
        formCard.add(passwordTextfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 103, 190, 32));

        jLabel8.setFont(lf); jLabel8.setForeground(lc); jLabel8.setText("Role:");
        formCard.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 82, 80, 18));
        roleBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            "Super Admin", "Program Head", "Department President",
            "Department Secretary", "Department Treasurer"}));
        roleBox.setFont(ff);
        formCard.add(roleBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 103, 190, 32));

        lblStatus.setFont(lf); lblStatus.setForeground(lc); lblStatus.setText("Status:");
        formCard.add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 82, 80, 18));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Active", "Inactive"}));
        jComboBox1.setFont(ff);
        formCard.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 103, 150, 32));

        jPanel1.add(formCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 980, 155));

        // ── Buttons row ───────────────────────────────────────────────────────
        addUser = new javax.swing.JButton() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(0, 60, 110) : new Color(8, 35, 70));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString("ADD USER", (getWidth() - fm.stringWidth("ADD USER")) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        addUser.setFocusPainted(false);
        addUser.setContentAreaFilled(false);
        addUser.setBorderPainted(false);
        addUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addUser.addActionListener(e -> addUserActionPerformed(e));
        jPanel1.add(addUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 222, 110, 34));

        UpdateButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        UpdateButton.setText("UPDATE");
        UpdateButton.setFocusPainted(false);
        UpdateButton.addActionListener(e -> UpdateButtonActionPerformed(e));
        jPanel1.add(UpdateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 222, 100, 34));

        DeleteButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        DeleteButton.setText("DELETE");
        DeleteButton.setFocusPainted(false);
        DeleteButton.addActionListener(e -> DeleteButtonActionPerformed(e));
        jPanel1.add(DeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 222, 100, 34));

        ClearButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        ClearButton.setText("CLEAR");
        ClearButton.setFocusPainted(false);
        ClearButton.addActionListener(e -> ClearButtonActionPerformed(e));
        jPanel1.add(ClearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 222, 100, 34));

        ClearButton1.setVisible(false);
        jPanel1.add(ClearButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        // ── Exit button (FIX 1: uses FORM_WIDTH constant) ─────────────────────
        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        exitButton.setText("← EXIT");
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(180, 50, 50));
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setOpaque(true);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.addActionListener(e -> exitButtonActionPerformed(e));
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { exitButton.setBackground(new Color(220, 60, 60)); }
            public void mouseExited(java.awt.event.MouseEvent e)  { exitButton.setBackground(new Color(180, 50, 50)); }
        });
        headerPanel.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(FORM_WIDTH - 110, 7, 90, 30));

        // ── Search row ────────────────────────────────────────────────────────
        Font lf2 = new Font("Segoe UI", Font.BOLD, 11);
        Color lc2 = new Color(8, 35, 70);
        Font ff2  = new Font("Segoe UI", Font.PLAIN, 12);

        lblSearch.setFont(lf2);
        lblSearch.setForeground(lc2);
        lblSearch.setText("Search:");
        jPanel1.add(lblSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 272, 60, 20));

        txtSearch.setFont(ff2);
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
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 269, 300, 30));

        // ── User Table ────────────────────────────────────────────────────────
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"First Name", "Middle Name", "Last Name", "Username", "Role", "Status"}
        ) { @Override public boolean isCellEditable(int r, int c) { return false; } });
        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        jTable1.setRowHeight(24);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        jTable1.getTableHeader().setBackground(new Color(8, 35, 70));
        jTable1.getTableHeader().setForeground(Color.WHITE);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { jTable1MouseClicked(evt); }
        });
        jScrollPane1.setViewportView(jTable1);
        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 308, 980, 290));

        jLabel3.setVisible(false);
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, FORM_WIDTH, 620));

        pack();
        setLocationRelativeTo(null);
    }

    // ── U-01: Add User ────────────────────────────────────────────────────────
    private void addUserActionPerformed(java.awt.event.ActionEvent evt) {
        String firstName  = getVal(firstnameTextfield,  "First Name");
        String middleName = getVal(middlenameTextField2, "Middle Name");
        String lastName   = getVal(lastnameTextField1,   "Last Name");
        String username   = getVal(usernameTextfield1,   "Username");
        String password   = getVal(passwordTextfield,    "Password");
        String role       = roleBox.getSelectedItem().toString();
        String status     = jComboBox1.getSelectedItem().toString();

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all required fields.\n(First Name, Last Name, Username, Password)",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO users(first_name, middle_name, last_name, username, password, role, status) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, firstName);
            pst.setString(2, middleName.isEmpty() ? null : middleName);
            pst.setString(3, lastName);
            pst.setString(4, username);
            pst.setString(5, password);
            pst.setString(6, role);
            pst.setString(7, status);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadTableData();
            clearFields();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── U-02: Update User ─────────────────────────────────────────────────────
    private void UpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = jTable1.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a user to update."); return; }

        int mr = jTable1.convertRowIndexToModel(row);
        String oldUsername = jTable1.getModel().getValueAt(mr, 3).toString();

        String firstName  = getVal(firstnameTextfield,  "First Name");
        String middleName = getVal(middlenameTextField2, "Middle Name");
        String lastName   = getVal(lastnameTextField1,   "Last Name");
        String username   = getVal(usernameTextfield1,   "Username");
        String newPassword = getVal(passwordTextfield,   "Password");
        String role       = roleBox.getSelectedItem().toString();
        String status     = jComboBox1.getSelectedItem().toString();

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all required fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection con = DBConnection.getConnection();

            // FIX 4: If no new password entered, fetch the existing password from DB
            // instead of incorrectly reusing oldUsername as the password.
            String finalPassword;
            if (newPassword.isEmpty()) {
                PreparedStatement fetch = con.prepareStatement(
                    "SELECT password FROM users WHERE username = ?");
                fetch.setString(1, oldUsername);
                ResultSet rs = fetch.executeQuery();
                finalPassword = rs.next() ? rs.getString("password") : "";
                rs.close();
            } else {
                finalPassword = newPassword;
            }

            String sql = "UPDATE users SET first_name=?, middle_name=?, last_name=?, username=?, password=?, role=?, status=? WHERE username=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, firstName);
            pst.setString(2, middleName.isEmpty() ? null : middleName);
            pst.setString(3, lastName);
            pst.setString(4, username);
            pst.setString(5, finalPassword);
            pst.setString(6, role);
            pst.setString(7, status);
            pst.setString(8, oldUsername);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadTableData();
            clearFields();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── U-03: Delete User ─────────────────────────────────────────────────────
    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = jTable1.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a user to delete."); return; }

        int mr = jTable1.convertRowIndexToModel(row);
        String username = jTable1.getModel().getValueAt(mr, 3).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete user '" + username + "'?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement("DELETE FROM users WHERE username=?");
                pst.setString(1, username);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "User deleted successfully!");
                loadTableData();
                clearFields();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    // ── Click row → fill form ─────────────────────────────────────────────────
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        int row = jTable1.getSelectedRow();
        if (row == -1) return;
        int mr = jTable1.convertRowIndexToModel(row);
        javax.swing.table.DefaultTableModel m = (javax.swing.table.DefaultTableModel) jTable1.getModel();

        setField(firstnameTextfield,   m.getValueAt(mr, 0), "First Name");
        setField(middlenameTextField2, m.getValueAt(mr, 1), "Middle Name");
        setField(lastnameTextField1,   m.getValueAt(mr, 2), "Last Name");
        setField(usernameTextfield1,   m.getValueAt(mr, 3), "Username");
        passwordTextfield.setForeground(new Color(160, 170, 190));
        passwordTextfield.setText("Password");
        roleBox.setSelectedItem(m.getValueAt(mr, 4) != null ? m.getValueAt(mr, 4).toString() : "Super Admin");
        jComboBox1.setSelectedItem(m.getValueAt(mr, 5) != null ? m.getValueAt(mr, 5).toString() : "Active");
    }

    private void setField(javax.swing.JTextField f, Object val, String ph) {
        String v = val != null ? val.toString() : "";
        if (v.isEmpty()) {
            f.setForeground(new Color(160, 170, 190)); f.setText(ph);
        } else {
            f.setForeground(new Color(10, 40, 80)); f.setText(v);
        }
    }

    private void ClearButtonActionPerformed(java.awt.event.ActionEvent evt)  { clearFields(); }
    private void ClearButton1ActionPerformed(java.awt.event.ActionEvent evt) { clearFields(); }

    private void clearFields() {
        firstnameTextfield.setForeground(new Color(160, 170, 190));   firstnameTextfield.setText("First Name");
        middlenameTextField2.setForeground(new Color(160, 170, 190)); middlenameTextField2.setText("Middle Name");
        lastnameTextField1.setForeground(new Color(160, 170, 190));   lastnameTextField1.setText("Last Name");
        usernameTextfield1.setForeground(new Color(160, 170, 190));   usernameTextfield1.setText("Username");
        passwordTextfield.setForeground(new Color(160, 170, 190));    passwordTextfield.setText("Password");
        roleBox.setSelectedIndex(0);
        jComboBox1.setSelectedIndex(0);
        txtSearch.setText("");
        jTable1.clearSelection();
    }

    // FIX 4: Uses loggedInRole field instead of hardcoded "Super Admin"
    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Dashboard dash = new Dashboard(loggedInRole);
        dash.setVisible(true);
        this.dispose();
    }

    private void roleBoxActionPerformed(java.awt.event.ActionEvent evt) {}
    private void firstnameTextfieldActionPerformed(java.awt.event.ActionEvent evt) {}

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new AdminAddUser().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JPanel            accentLine;
    private javax.swing.JButton           addUser;
    private javax.swing.JButton           ClearButton;
    private javax.swing.JButton           ClearButton1;
    private javax.swing.JButton           DeleteButton;
    private javax.swing.JButton           UpdateButton;
    private javax.swing.JButton           exitButton;
    private javax.swing.JTextField        firstnameTextfield;
    private javax.swing.JPanel            headerPanel;
    private javax.swing.JLabel            headerLabel;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel            jLabel1;
    private javax.swing.JLabel            jLabel2;
    private javax.swing.JLabel            jLabel3;
    private javax.swing.JLabel            jLabel4;
    private javax.swing.JLabel            jLabel5;
    private javax.swing.JLabel            jLabel7;
    private javax.swing.JLabel            jLabel8;
    private javax.swing.JLabel            lblSearch;
    private javax.swing.JLabel            lblStatus;
    private javax.swing.JPanel            jPanel1;
    private javax.swing.JScrollPane       jScrollPane1;
    private javax.swing.JTable            jTable1;
    private javax.swing.JTextField        lastnameTextField1;
    private javax.swing.JTextField        middlenameTextField2;
    private javax.swing.JTextField        passwordTextfield;
    private javax.swing.JComboBox<String> roleBox;
    private javax.swing.JTextField        txtSearch;
    private javax.swing.JTextField        usernameTextfield1;
}