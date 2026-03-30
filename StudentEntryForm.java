package templonuevocharlesluke;

import javax.swing.JOptionPane;
import java.sql.*;
import java.awt.*;
import java.io.*;
import java.util.Base64;

public class StudentEntryForm extends javax.swing.JFrame {

    private javax.swing.ButtonGroup genderGroup;
    private String photoBase64 = null;

    public StudentEntryForm() {
        initComponents();
         setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); // ← add this
        setupGenderGroup();
        setupPlaceholders();
        loadColleges();
        loadTableData();
    }

    private void setupGenderGroup() {
        genderGroup = new javax.swing.ButtonGroup();
        genderGroup.add(rdMale);
        genderGroup.add(rdFemale);
    }

    // ── Placeholders ──────────────────────────────────────────────────────────
    private void setupPlaceholders() {
        addPlaceholder(txtStudentID,  "e.g. 2025-0001");
        addPlaceholder(txtFirstName,  "First Name");
        addPlaceholder(txtMiddleName, "Middle Name");
        addPlaceholder(txtLastName,   "Last Name");
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

    // ── Load colleges ─────────────────────────────────────────────────────────
    private void loadColleges() {
        cmbCollege.removeAllItems();
        cmbCollege.addItem("-- Select College --");
        cmbCollege.addItem("CEAC");
        cmbCollege.addItem("CBGA");
        cmbCollege.addItem("CHS");
        cmbCollege.addItem("CED");
        cmbCollege.addItem("CAS");
    }

    // ── Load departments by college ───────────────────────────────────────────
    private void loadDepartments(String college) {
        cmbDepartment.removeAllItems();
        cmbDepartment.addItem("-- Select Department --");
        cmbCourse.removeAllItems();
        cmbCourse.addItem("-- Select Course --");
        if (college.startsWith("--")) return;
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
            PreparedStatement pst = con.prepareStatement(
                "SELECT dept_name FROM departments WHERE college=? ORDER BY dept_name");
            pst.setString(1, college);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) cmbDepartment.addItem(rs.getString("dept_name"));
        } catch (Exception e) { /* silent */ }
    }

    // ── Load courses — hardcoded list ────────────────────────────────────────
    private void loadCourses(String dept) {
        cmbCourse.removeAllItems();
        cmbCourse.addItem("-- Select Course --");
        cmbCourse.addItem("Computer Science");
        cmbCourse.addItem("Information Technology");
        cmbCourse.addItem("Software Engineering");
        cmbCourse.addItem("Data Science");
        cmbCourse.addItem("Cybersecurity");
        cmbCourse.addItem("Artificial Intelligence");
        cmbCourse.addItem("Business Administration");
        cmbCourse.addItem("Accounting");
        cmbCourse.addItem("Economics");
        cmbCourse.addItem("Marketing");
        cmbCourse.addItem("Finance");
        cmbCourse.addItem("Human Resource Management");
        cmbCourse.addItem("Psychology");
        cmbCourse.addItem("Sociology");
        cmbCourse.addItem("Biology");
        cmbCourse.addItem("Chemistry");
        cmbCourse.addItem("Physics");
        cmbCourse.addItem("Mathematics");
        cmbCourse.addItem("Statistics");
        cmbCourse.addItem("Environmental Science");
        cmbCourse.addItem("Mechanical Engineering");
        cmbCourse.addItem("Electrical Engineering");
        cmbCourse.addItem("Civil Engineering");
        cmbCourse.addItem("Architecture");
        cmbCourse.addItem("Graphic Design");
        cmbCourse.addItem("Fashion Design");
        cmbCourse.addItem("Hospitality Management");
        cmbCourse.addItem("Tourism");
        cmbCourse.addItem("Education");
        cmbCourse.addItem("Nursing");
        cmbCourse.addItem("Pharmacy");
        cmbCourse.addItem("Law");
        cmbCourse.addItem("Political Science");
        cmbCourse.addItem("History");
        cmbCourse.addItem("Philosophy");
        cmbCourse.addItem("Journalism");
        cmbCourse.addItem("Mass Communication");
        cmbCourse.addItem("Music");
        cmbCourse.addItem("Fine Arts");
        cmbCourse.addItem("Sports Science");
    }

    // ── Load students into table ──────────────────────────────────────────────
    private void loadTableData() {
        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
            String sql = "SELECT studentID, firstName, middle_name, last_name, course, year_level, student_type FROM students";
            ResultSet rs = con.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("studentID"),
                    rs.getString("firstName"),
                    rs.getString("middle_name"),
                    rs.getString("last_name"),
                    rs.getString("course"),
                    rs.getString("year_level"),
                    rs.getString("student_type")
                });
            }
        } catch (Exception e) { /* silent */ }
    }

    // ── Search filter ─────────────────────────────────────────────────────────
    private void filterTable(String query) {
        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable1.getModel();
        javax.swing.RowSorter<?> s = jTable1.getRowSorter();
        javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> tr;
        if (s instanceof javax.swing.table.TableRowSorter) {
            tr = (javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel>) s;
        } else {
            tr = new javax.swing.table.TableRowSorter<>(model);
            jTable1.setRowSorter(tr);
        }
        tr.setRowFilter(query.isEmpty() ? null :
            javax.swing.RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(query)));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1        = new javax.swing.JPanel();
        headerPanel    = new javax.swing.JPanel();
        headerLabel    = new javax.swing.JLabel();
        accentLine     = new javax.swing.JPanel();

        // Form fields
        lblStudentID   = new javax.swing.JLabel();
        txtStudentID   = new javax.swing.JTextField();
        lblFirstName   = new javax.swing.JLabel();
        txtFirstName   = new javax.swing.JTextField();
        lblMiddleName  = new javax.swing.JLabel();
        txtMiddleName  = new javax.swing.JTextField();
        lblLastName    = new javax.swing.JLabel();
        txtLastName    = new javax.swing.JTextField();
        lblCollege     = new javax.swing.JLabel();
        cmbCollege     = new javax.swing.JComboBox<>();
        lblDepartment  = new javax.swing.JLabel();
        cmbDepartment  = new javax.swing.JComboBox<>();
        lblCourse      = new javax.swing.JLabel();
        cmbCourse      = new javax.swing.JComboBox<>();
        lblYearLevel   = new javax.swing.JLabel();
        cmbYearLevel   = new javax.swing.JComboBox<>();
        lblStudentType = new javax.swing.JLabel();
        cmbStudentType = new javax.swing.JComboBox<>();
        lblGender      = new javax.swing.JLabel();
        rdMale         = new javax.swing.JRadioButton();
        rdFemale       = new javax.swing.JRadioButton();
        lblPhoto       = new javax.swing.JLabel();
        photoPreview   = new javax.swing.JLabel();
        lblSearch      = new javax.swing.JLabel();
        txtSearch      = new javax.swing.JTextField();

        // Buttons
        saveButton     = new javax.swing.JButton();
        photoButton    = new javax.swing.JButton();
        clearButton    = new javax.swing.JButton();
        deleteButton   = new javax.swing.JButton();
        exitButton     = new javax.swing.JButton();

        // Table
        jScrollPane1   = new javax.swing.JScrollPane();
        jTable1        = new javax.swing.JTable();

        // ── Frame ─────────────────────────────────────────────────────────────
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EDP Attendance System — Student Entry");
        setMinimumSize(new Dimension(780, 730));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new Color(240, 243, 250));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // ── Header ────────────────────────────────────────────────────────────
        headerPanel.setBackground(new Color(8, 35, 70));
        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setText("  Student Entry Form");
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

        // Row 1 — Student ID | First Name | Middle Name | Last Name
        lblStudentID.setFont(lf); lblStudentID.setForeground(lc); lblStudentID.setText("Student ID:");
        formCard.add(lblStudentID, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 120, 18));
        txtStudentID.setFont(ff); txtStudentID.setBorder(fb); txtStudentID.setBackground(new Color(245,247,252));
        formCard.add(txtStudentID, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 36, 140, 32));

        lblFirstName.setFont(lf); lblFirstName.setForeground(lc); lblFirstName.setText("First Name:");
        formCard.add(lblFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 15, 120, 18));
        txtFirstName.setFont(ff); txtFirstName.setBorder(fb); txtFirstName.setBackground(new Color(245,247,252));
        formCard.add(txtFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 36, 150, 32));

        lblMiddleName.setFont(lf); lblMiddleName.setForeground(lc); lblMiddleName.setText("Middle Name:");
        formCard.add(lblMiddleName, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 15, 120, 18));
        txtMiddleName.setFont(ff); txtMiddleName.setBorder(fb); txtMiddleName.setBackground(new Color(245,247,252));
        formCard.add(txtMiddleName, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 36, 140, 32));

        lblLastName.setFont(lf); lblLastName.setForeground(lc); lblLastName.setText("Last Name:");
        formCard.add(lblLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 15, 120, 18));
        txtLastName.setFont(ff); txtLastName.setBorder(fb); txtLastName.setBackground(new Color(245,247,252));
        formCard.add(txtLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 36, 155, 32));

        // Row 2 — College | Department | Course
        lblCollege.setFont(lf); lblCollege.setForeground(lc); lblCollege.setText("College:");
        formCard.add(lblCollege, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 82, 100, 18));
        cmbCollege.setFont(ff);
        cmbCollege.addActionListener(e -> {
            String sel = (String) cmbCollege.getSelectedItem();
            if (sel != null) loadDepartments(sel);
        });
        formCard.add(cmbCollege, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 103, 155, 32));

        lblDepartment.setFont(lf); lblDepartment.setForeground(lc); lblDepartment.setText("Department:");
        formCard.add(lblDepartment, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 82, 120, 18));
        cmbDepartment.setFont(ff);
        cmbDepartment.addActionListener(e -> {
            String sel = (String) cmbDepartment.getSelectedItem();
            if (sel != null) loadCourses(sel); // loads hardcoded course list
        });
        formCard.add(cmbDepartment, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 103, 190, 32));

        lblCourse.setFont(lf); lblCourse.setForeground(lc); lblCourse.setText("Course:");
        formCard.add(lblCourse, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 82, 100, 18));
        cmbCourse.setFont(ff);
        formCard.add(cmbCourse, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 103, 255, 32));

        // Row 3 — Year Level | Student Type | Gender
        lblYearLevel.setFont(lf); lblYearLevel.setForeground(lc); lblYearLevel.setText("Year Level:");
        formCard.add(lblYearLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 150, 100, 18));
        cmbYearLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            "First Year", "Second Year", "Third Year", "Fourth Year"}));
        cmbYearLevel.setFont(ff);
        formCard.add(cmbYearLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 171, 155, 32));

        lblStudentType.setFont(lf); lblStudentType.setForeground(lc); lblStudentType.setText("Student Type:");
        formCard.add(lblStudentType, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 150, 120, 18));
        cmbStudentType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Regular", "Irregular"}));
        cmbStudentType.setFont(ff);
        formCard.add(cmbStudentType, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 171, 155, 32));

        lblGender.setFont(lf); lblGender.setForeground(lc); lblGender.setText("Gender:");
        formCard.add(lblGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 150, 80, 18));
        rdMale.setFont(ff); rdMale.setText("Male"); rdMale.setBackground(Color.WHITE);
        formCard.add(rdMale, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 171, 65, 28));
        rdFemale.setFont(ff); rdFemale.setText("Female"); rdFemale.setBackground(Color.WHITE);
        formCard.add(rdFemale, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 171, 75, 28));

        // Photo section
        lblPhoto.setFont(lf); lblPhoto.setForeground(lc); lblPhoto.setText("Student Photo:");
        formCard.add(lblPhoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 150, 120, 18));

        photoPreview.setBackground(new Color(230, 235, 245));
        photoPreview.setOpaque(true);
        photoPreview.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(200,210,225)));
        photoPreview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        photoPreview.setText("No Photo");
        photoPreview.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        formCard.add(photoPreview, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 171, 115, 80));

        photoButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        photoButton.setText("Upload Photo");
        photoButton.setFocusPainted(false);
        photoButton.addActionListener(e -> uploadPhoto());
        formCard.add(photoButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 255, 115, 28));

        jPanel1.add(formCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 760, 295));

        // ── ROW 1: Action Buttons ─────────────────────────────────────────────
        // Custom painted SAVE button
        saveButton = new javax.swing.JButton() {
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
        saveButton.setFocusPainted(false); saveButton.setContentAreaFilled(false);
        saveButton.setBorderPainted(false); saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.addActionListener(e -> saveStudent());
        jPanel1.add(saveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 362, 90, 34));

        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        clearButton.setText("CLEAR");
        clearButton.setFocusPainted(false);
        clearButton.addActionListener(e -> clearFields());
        jPanel1.add(clearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 362, 90, 34));

        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        deleteButton.setText("DELETE");
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> deleteStudent());
        jPanel1.add(deleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 362, 90, 34));

        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        exitButton.setText("EXIT");
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this, "Return to Dashboard?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) { new Dashboard().setVisible(true); this.dispose(); }
        });
        jPanel1.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 362, 90, 34));

        // ── ROW 2: Search bar (separate row below buttons) ────────────────────
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblSearch.setForeground(lc);
        lblSearch.setText("Search:");
        jPanel1.add(lblSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 60, 20));

        txtSearch.setFont(ff);
        txtSearch.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new Color(200,210,225),1),
            javax.swing.BorderFactory.createEmptyBorder(4,8,4,8)));
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                filterTable(txtSearch.getText().trim());
            }
        });
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 407, 300, 30));

        // ── Student Table ─────────────────────────────────────────────────────
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"Student ID","First Name","Middle Name","Last Name","Course","Year Level","Type"}
        ) { @Override public boolean isCellEditable(int r, int c) { return false; } });
        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        jTable1.setRowHeight(24);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        jTable1.getTableHeader().setBackground(new Color(8, 35, 70));
        jTable1.getTableHeader().setForeground(Color.WHITE);
        javax.swing.table.JTableHeader header = jTable1.getTableHeader();
        header.setOpaque(true);
        header.setBackground(new Color(8, 35, 70));
        header.setForeground(Color.WHITE);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { tableRowClicked(); }
        });
        jScrollPane1.setViewportView(jTable1);
        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 447, 760, 270));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 730));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // ── Upload photo → resize then store as Base64 (S-01) ───────────────────
    private void uploadPhoto() {
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Image Files", "jpg", "jpeg", "png", "gif"));
        if (fc.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                // ── Resize to max 200x200 before encoding to keep size small ──
                java.awt.image.BufferedImage original =
                    javax.imageio.ImageIO.read(file);
                int maxSize = 200;
                int w = original.getWidth();
                int h = original.getHeight();
                // Scale down proportionally
                if (w > maxSize || h > maxSize) {
                    double scale = Math.min((double) maxSize / w, (double) maxSize / h);
                    w = (int)(w * scale);
                    h = (int)(h * scale);
                }
                java.awt.image.BufferedImage resized =
                    new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_RGB);
                java.awt.Graphics2D g2 = resized.createGraphics();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                    java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(original, 0, 0, w, h, null);
                g2.dispose();

                // ── Encode resized image to Base64 ────────────────────────────
                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                javax.imageio.ImageIO.write(resized, "jpg", baos);
                photoBase64 = Base64.getEncoder().encodeToString(baos.toByteArray());

                // ── Show preview ──────────────────────────────────────────────
                javax.swing.ImageIcon icon = new javax.swing.ImageIcon(
                    resized.getScaledInstance(113, 78, Image.SCALE_SMOOTH));
                photoPreview.setIcon(icon);
                photoPreview.setText("");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error loading photo: " + e.getMessage());
            }
        }
    }

    // ── Click row → fill form ─────────────────────────────────────────────────
    private void tableRowClicked() {
        int row = jTable1.getSelectedRow();
        if (row == -1) return;
        int mr = jTable1.convertRowIndexToModel(row);
        javax.swing.table.DefaultTableModel m = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        txtStudentID.setText(String.valueOf(m.getValueAt(mr,0))); txtStudentID.setForeground(new Color(10,40,80));
        txtFirstName.setText(String.valueOf(m.getValueAt(mr,1))); txtFirstName.setForeground(new Color(10,40,80));
        txtMiddleName.setText(String.valueOf(m.getValueAt(mr,2))); txtMiddleName.setForeground(new Color(10,40,80));
        txtLastName.setText(String.valueOf(m.getValueAt(mr,3))); txtLastName.setForeground(new Color(10,40,80));
        cmbCourse.setSelectedItem(m.getValueAt(mr,4));
        cmbYearLevel.setSelectedItem(m.getValueAt(mr,5));
        cmbStudentType.setSelectedItem(m.getValueAt(mr,6));
    }

    // ── S-01: Save Student ────────────────────────────────────────────────────
    private void saveStudent() {
        String sid    = getVal(txtStudentID,  "e.g. 2025-0001");
        String fname  = getVal(txtFirstName,  "First Name");
        String mname  = getVal(txtMiddleName, "Middle Name");
        String lname  = getVal(txtLastName,   "Last Name");
        String college = (String) cmbCollege.getSelectedItem();
        String dept    = (String) cmbDepartment.getSelectedItem();
        String course  = (String) cmbCourse.getSelectedItem();
        String yrLevel = (String) cmbYearLevel.getSelectedItem();
        String stype   = (String) cmbStudentType.getSelectedItem();
        String gender  = rdMale.isSelected() ? "Male" : rdFemale.isSelected() ? "Female" : "";

        if (sid.isEmpty() || fname.isEmpty() || lname.isEmpty()
            || college.startsWith("--") || dept.startsWith("--")
            || course.startsWith("--") || gender.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all required fields.\n(Student ID, Name, College, Department, Course, Gender)",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
            String sql = "INSERT INTO students (studentID, firstName, middle_name, last_name, " +
                "course, year_level, student_type, gender) " +
                "VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, sid);
            pst.setString(2, fname);
            pst.setString(3, mname.isEmpty() ? null : mname);
            pst.setString(4, lname);
            pst.setString(5, course);
            pst.setString(6, yrLevel);
            pst.setString(7, stype);
            pst.setString(8, gender);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadTableData();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Delete Student ────────────────────────────────────────────────────────
    private void deleteStudent() {
        int row = jTable1.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a student to delete."); return; }
        int mr = jTable1.convertRowIndexToModel(row);
        String sid = jTable1.getModel().getValueAt(mr, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this student?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/attendance_system", "root", "")) {
                PreparedStatement pst = con.prepareStatement("DELETE FROM students WHERE studentID=?");
                pst.setString(1, sid);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                loadTableData();
                clearFields();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    // ── Clear fields ──────────────────────────────────────────────────────────
    private void clearFields() {
        txtStudentID.setForeground(new Color(160,170,190)); txtStudentID.setText("e.g. 2025-0001");
        txtFirstName.setForeground(new Color(160,170,190)); txtFirstName.setText("First Name");
        txtMiddleName.setForeground(new Color(160,170,190)); txtMiddleName.setText("Middle Name");
        txtLastName.setForeground(new Color(160,170,190)); txtLastName.setText("Last Name");
        cmbCollege.setSelectedIndex(0);
        cmbDepartment.removeAllItems(); cmbDepartment.addItem("-- Select Department --");
        loadCourses(""); // restore full course list on clear
        cmbYearLevel.setSelectedIndex(0);
        cmbStudentType.setSelectedIndex(0);
        genderGroup.clearSelection();
        photoPreview.setIcon(null); photoPreview.setText("No Photo");
        photoBase64 = null;
        txtSearch.setText("");
        jTable1.clearSelection();
        filterTable("");
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new StudentEntryForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel         accentLine;
    private javax.swing.JButton        clearButton;
    private javax.swing.JComboBox<String> cmbCollege;
    private javax.swing.JComboBox<String> cmbCourse;
    private javax.swing.JComboBox<String> cmbDepartment;
    private javax.swing.JComboBox<String> cmbStudentType;
    private javax.swing.JComboBox<String> cmbYearLevel;
    private javax.swing.JButton        deleteButton;
    private javax.swing.JButton        exitButton;
    private javax.swing.JPanel         headerPanel;
    private javax.swing.JLabel         headerLabel;
    private javax.swing.JTable         jTable1;
    private javax.swing.JPanel         jPanel1;
    private javax.swing.JScrollPane    jScrollPane1;
    private javax.swing.JLabel         lblCollege;
    private javax.swing.JLabel         lblCourse;
    private javax.swing.JLabel         lblDepartment;
    private javax.swing.JLabel         lblFirstName;
    private javax.swing.JLabel         lblGender;
    private javax.swing.JLabel         lblLastName;
    private javax.swing.JLabel         lblMiddleName;
    private javax.swing.JLabel         lblPhoto;
    private javax.swing.JLabel         lblSearch;
    private javax.swing.JLabel         lblStudentID;
    private javax.swing.JLabel         lblStudentType;
    private javax.swing.JLabel         lblYearLevel;
    private javax.swing.JLabel         photoPreview;
    private javax.swing.JButton        photoButton;
    private javax.swing.JRadioButton   rdFemale;
    private javax.swing.JRadioButton   rdMale;
    private javax.swing.JButton        saveButton;
    private javax.swing.JTextField     txtFirstName;
    private javax.swing.JTextField     txtLastName;
    private javax.swing.JTextField     txtMiddleName;
    private javax.swing.JTextField     txtSearch;
    private javax.swing.JTextField     txtStudentID;
    // End of variables declaration//GEN-END:variables
}