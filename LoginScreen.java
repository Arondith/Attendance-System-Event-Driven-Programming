package templonuevocharlesluke;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.awt.*;

public class LoginScreen extends javax.swing.JFrame {

    public LoginScreen() {
        initComponents();
       
        setupUI();
    }

    // ── Username placeholder ──────────────────────────────────────────────────
    private void setupUI() {
        usernameTextfield.setForeground(new Color(160, 170, 190));
        usernameTextfield.setText("Enter username");
        usernameTextfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (usernameTextfield.getText().equals("Enter username")) {
                    usernameTextfield.setText("");
                    usernameTextfield.setForeground(new Color(10, 40, 80));
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (usernameTextfield.getText().trim().isEmpty()) {
                    usernameTextfield.setForeground(new Color(160, 170, 190));
                    usernameTextfield.setText("Enter username");
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel         = new javax.swing.JPanel();
        leftPanel         = new javax.swing.JPanel();
        accentBar         = new javax.swing.JPanel();
        appNameLabel      = new javax.swing.JLabel();
        appSubLabel       = new javax.swing.JLabel();
        dividerLine       = new javax.swing.JSeparator();
        quoteLabel        = new javax.swing.JLabel();
        versionLabel      = new javax.swing.JLabel();
        rightPanel        = new javax.swing.JPanel();
        welcomeLabel      = new javax.swing.JLabel();
        welcomeSubLabel   = new javax.swing.JLabel();
        userLabel         = new javax.swing.JLabel();
        usernameTextfield = new javax.swing.JTextField();
        passLabel         = new javax.swing.JLabel();
        passwordTextfield = new javax.swing.JPasswordField();
        footerLabel       = new javax.swing.JLabel();

        
        loginButton = new javax.swing.JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(40, 40, 40) : new Color(15, 15, 15));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth("LOGIN")) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString("LOGIN", x, y);
                g2.dispose();
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        // ── Frame ─────────────────────────────────────────────────────────────
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(820, 480));
        setResizable(false);
        setTitle("EDP Attendance System — Login");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // ── Main panel ────────────────────────────────────────────────────────
        mainPanel.setBackground(new Color(240, 243, 250));
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // ════════════════ LEFT PANEL ═════════════════
        leftPanel.setBackground(new Color(8, 35, 70));
        leftPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        accentBar.setBackground(new Color(0, 190, 210));
        accentBar.setLayout(null);
        leftPanel.add(accentBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 6, 480));

        appSubLabel.setFont(new Font("Segoe UI", Font.BOLD, 9));
        appSubLabel.setForeground(new Color(0, 210, 230));
        appSubLabel.setText("● EVENT-DRIVEN PLATFORM");
        leftPanel.add(appSubLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 110, 240, 16));

        appNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        appNameLabel.setForeground(Color.WHITE);
        appNameLabel.setText("<html>EDP<br/>Attendance<br/>System</html>");
        leftPanel.add(appNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 130, 250, 115));

        dividerLine.setForeground(new Color(0, 190, 210));
        dividerLine.setBackground(new Color(0, 190, 210));
        leftPanel.add(dividerLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 260, 210, 3));

        quoteLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        quoteLabel.setForeground(new Color(110, 155, 210));
        quoteLabel.setText("<html>Track attendance.<br/>Monitor events.<br/>Manage records.</html>");
        leftPanel.add(quoteLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 273, 230, 60));

        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        versionLabel.setForeground(new Color(60, 90, 130));
        versionLabel.setText("v1.0.0  |  2025");
        leftPanel.add(versionLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 445, 150, 16));

        mainPanel.add(leftPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 480));

        // ════════════════ RIGHT PANEL ════════════════
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(8, 35, 70));
        welcomeLabel.setText("Welcome Back!");
        rightPanel.add(welcomeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 300, 36));

        welcomeSubLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        welcomeSubLabel.setForeground(new Color(140, 150, 170));
        welcomeSubLabel.setText("Please sign in to your account");
        rightPanel.add(welcomeSubLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 116, 300, 20));

        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        userLabel.setForeground(new Color(8, 35, 70));
        userLabel.setText("USERNAME");
        rightPanel.add(userLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 158, 120, 18));

        usernameTextfield.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        usernameTextfield.setForeground(new Color(160, 170, 190));
        usernameTextfield.setBackground(new Color(245, 247, 252));
        usernameTextfield.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new Color(200, 210, 225), 1),
            javax.swing.BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        rightPanel.add(usernameTextfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 330, 40));

        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        passLabel.setForeground(new Color(8, 35, 70));
        passLabel.setText("PASSWORD");
        rightPanel.add(passLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 232, 120, 18));

        passwordTextfield.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passwordTextfield.setEchoChar('●');
        passwordTextfield.setBackground(new Color(245, 247, 252));
        passwordTextfield.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new Color(200, 210, 225), 1),
            javax.swing.BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        passwordTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        rightPanel.add(passwordTextfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 254, 330, 40));

        rightPanel.add(loginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 314, 330, 50));

        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        footerLabel.setForeground(new Color(190, 195, 210));
        footerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        footerLabel.setText("© 2025 EDP Attendance System. All rights reserved.");
        rightPanel.add(footerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 445, 400, 18));

        mainPanel.add(rightPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 500, 480));
        getContentPane().add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 480));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // ── Login logic ───────────────────────────────────────────────────────────
    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String username = usernameTextfield.getText().trim();
        String password = new String(passwordTextfield.getPassword()).trim();

        if (username.equals("Enter username")) username = "";

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields.",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            if (con == null) {
                JOptionPane.showMessageDialog(this,
                    "Cannot connect to database.\nPlease check if MySQL is running.",
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String roleFromDB = rs.getString("role");
                if (roleFromDB == null || roleFromDB.trim().isEmpty()) {
                    roleFromDB = "User";
                }
                JOptionPane.showMessageDialog(this,
                    "Login successful! Welcome, " + username + ".",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                Dashboard dash = new Dashboard(roleFromDB);
                dash.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Invalid username or password.",
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
                passwordTextfield.setText("");
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Database error: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            // default look and feel
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel         accentBar;
    private javax.swing.JLabel         appNameLabel;
    private javax.swing.JLabel         appSubLabel;
    private javax.swing.JSeparator     dividerLine;
    private javax.swing.JLabel         footerLabel;
    private javax.swing.JButton        loginButton;
    private javax.swing.JPanel         leftPanel;
    private javax.swing.JPanel         mainPanel;
    private javax.swing.JLabel         passLabel;
    private javax.swing.JPasswordField passwordTextfield;
    private javax.swing.JLabel         quoteLabel;
    private javax.swing.JPanel         rightPanel;
    private javax.swing.JLabel         userLabel;
    private javax.swing.JTextField     usernameTextfield;
    private javax.swing.JLabel         versionLabel;
    private javax.swing.JLabel         welcomeLabel;
    private javax.swing.JLabel         welcomeSubLabel;
    // End of variables declaration//GEN-END:variables
}