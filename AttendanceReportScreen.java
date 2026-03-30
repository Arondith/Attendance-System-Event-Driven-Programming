package templonuevocharlesluke;

import java.sql.*;
import java.awt.*;
import java.awt.print.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AttendanceReportScreen extends javax.swing.JFrame {

    private Connection conn;

    public AttendanceReportScreen() {
        initComponents();
         setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); // ← add this
        connect();
        loadFilters();
        loadTable();
    }

    private void connect() {
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_system", "root", "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Connection error: " + e.getMessage());
        }
    }

    // ── Load filter dropdowns ─────────────────────────────────────────────────
    private void loadFilters() {
        try {
            // Events
            cmbEvent.addItem("-- All Events --");
            ResultSet rs = conn.prepareStatement(
                "SELECT DISTINCT event_name FROM events ORDER BY event_name").executeQuery();
            while (rs.next()) cmbEvent.addItem(rs.getString("event_name"));

            // Status
            cmbStatus.addItem("-- All Status --");
            cmbStatus.addItem("Present");
            cmbStatus.addItem("Late");
            cmbStatus.addItem("Absent");

            // Date
            cmbDate.addItem("-- All Dates --");
            rs = conn.prepareStatement(
                "SELECT DISTINCT DATE(time_in) as d FROM attendance " +
                "ORDER BY d DESC").executeQuery();
            while (rs.next()) {
                String d = rs.getString("d");
                if (d != null) cmbDate.addItem(d);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Filter load error: " + e.getMessage());
        }
    }

    // ── AR-01: Load/filter table ──────────────────────────────────────────────
    private void loadTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) reportTable.getModel();
            model.setRowCount(0);

            String event  = cmbEvent.getSelectedItem().toString();
            String status = cmbStatus.getSelectedItem().toString();
            String date   = cmbDate.getSelectedItem().toString();
            String search = txtSearch.getText().trim();

            StringBuilder sql = new StringBuilder(
                "SELECT a.studentID, s.firstName, s.last_name, s.course, " +
                "e.event_name, a.time_in, a.status " +
                "FROM attendance a " +
                "LEFT JOIN students s ON a.studentID = s.studentID " +
                "LEFT JOIN events e ON a.event_id = e.id " +
                "WHERE 1=1 ");

            if (!event.startsWith("-- All"))  sql.append("AND e.event_name = ? ");
            if (!status.startsWith("-- All")) sql.append("AND a.status = ? ");
            if (!date.startsWith("-- All"))   sql.append("AND DATE(a.time_in) = ? ");
            if (!search.isEmpty())
                sql.append("AND (a.studentID LIKE ? OR s.firstName LIKE ? OR s.last_name LIKE ?) ");

            sql.append("ORDER BY a.time_in DESC");

            PreparedStatement pst = conn.prepareStatement(sql.toString());
            int idx = 1;
            if (!event.startsWith("-- All"))  pst.setString(idx++, event);
            if (!status.startsWith("-- All")) pst.setString(idx++, status);
            if (!date.startsWith("-- All"))   pst.setString(idx++, date);
            if (!search.isEmpty()) {
                pst.setString(idx++, "%" + search + "%");
                pst.setString(idx++, "%" + search + "%");
                pst.setString(idx++, "%" + search + "%");
            }

            ResultSet rs = pst.executeQuery();
            int present = 0, late = 0, absent = 0;

            while (rs.next()) {
                String timeIn = rs.getString("time_in");
                String datePart = (timeIn != null && timeIn.length() >= 10) ? timeIn.substring(0,10) : "";
                String timePart = (timeIn != null && timeIn.length() >= 19) ? timeIn.substring(11,19) : "";
                String sts = rs.getString("status");
                String firstName = rs.getString("firstName") != null ? rs.getString("firstName") : "";
                String lastName  = rs.getString("last_name") != null ? rs.getString("last_name") : "";

                model.addRow(new Object[]{
                    rs.getString("studentID"),
                    firstName + " " + lastName,
                    rs.getString("course"),
                    rs.getString("event_name"),
                    datePart,
                    timePart,
                    sts
                });

                if ("Present".equalsIgnoreCase(sts)) present++;
                else if ("Late".equalsIgnoreCase(sts)) late++;
                else absent++;
            }

            lblTotal.setText("Total: " + model.getRowCount() +
                "   ✅ Present: " + present +
                "   ⏰ Late: " + late +
                "   ❌ Absent: " + absent);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Load error: " + e.getMessage());
        }
    }

   private void exportToPDF() {
    DefaultTableModel model = (DefaultTableModel) reportTable.getModel();
    if (model.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "No records to export.",
            "Empty Report", JOptionPane.WARNING_MESSAGE);
        return;
    }

    JFileChooser fc = new JFileChooser();
    fc.setSelectedFile(new File("AttendanceReport_" +
        LocalDate.now().toString().replace("-","") + ".pdf"));
    fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
        "PDF Files (*.pdf)", "pdf"));

    if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

    File file = fc.getSelectedFile();
    if (!file.getName().endsWith(".pdf"))
        file = new File(file.getAbsolutePath() + ".pdf");

    final File finalFile = file;

    // Column widths (points) — total ~750 for landscape A4
    final int[] colWidths = {70, 120, 90, 130, 70, 60, 60};
    final String[] headers = {"Student ID","Student Name","Course","Event","Date","Time","Status"};
    final int ROWS_PER_PAGE = 30;
    final int totalRows = model.getRowCount();
    final int totalPages = (int) Math.ceil((double) totalRows / ROWS_PER_PAGE);

    // Snapshot data before printing
    final String[][] data = new String[totalRows][7];
    for (int r = 0; r < totalRows; r++)
        for (int c = 0; c < 7; c++)
            data[r][c] = model.getValueAt(r, c) != null ?
                model.getValueAt(r, c).toString() : "";

    final String eventSel  = cmbEvent.getSelectedItem().toString();
    final String statusSel = cmbStatus.getSelectedItem().toString();
    final String dateSel   = cmbDate.getSelectedItem().toString();
    final String generated = LocalDateTime.now().format(
        DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a"));
    final String summaryText = lblTotal.getText();

    PrinterJob job = PrinterJob.getPrinterJob();

    // Set landscape A4
    PageFormat pf = job.defaultPage();
    Paper paper = new Paper();
    // A4 landscape in points (1 inch = 72 pts): 842 x 595
    paper.setSize(842, 595);
    paper.setImageableArea(30, 30, 782, 535);
    pf.setPaper(paper);
    pf.setOrientation(PageFormat.LANDSCAPE);

    job.setPrintable((graphics, pageFormat, pageIndex) -> {
        if (pageIndex >= totalPages) return Printable.NO_SUCH_PAGE;

        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        double ox = pageFormat.getImageableX();
        double oy = pageFormat.getImageableY();
        g2.translate(ox, oy);

        int pageW = (int) pageFormat.getImageableWidth();
        int y = 0;

        // ── Header (first page only) ──────────────────────────────────────
        if (pageIndex == 0) {
            // Title bar
            g2.setColor(new Color(8, 35, 70));
            g2.fillRect(0, y, pageW, 32);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
            g2.drawString("Attendance Report", 12, y + 22);
            y += 32;

            // Accent line
            g2.setColor(new Color(0, 190, 210));
            g2.fillRect(0, y, pageW, 3);
            y += 8;

            // Generated date
            g2.setColor(new Color(100, 110, 130));
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 9));
            g2.drawString("Generated: " + generated, 4, y + 10);
            y += 16;

            // Filters
            g2.setColor(new Color(240, 243, 250));
            g2.fillRect(0, y, pageW, 18);
            g2.setColor(new Color(60, 70, 90));
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 9));
            g2.drawString("Event: " + eventSel +
                "   |   Status: " + statusSel +
                "   |   Date: " + dateSel, 6, y + 13);
            y += 22;

            // Summary
            g2.setColor(new Color(8, 35, 70));
            g2.setFont(new Font("Segoe UI", Font.BOLD, 9));
            g2.drawString(summaryText.replace("✅","").replace("⏰","").replace("❌",""), 4, y + 10);
            y += 18;
        } else {
            // Continuation header
            g2.setColor(new Color(8, 35, 70));
            g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
            g2.drawString("Attendance Report (continued) — Page " + (pageIndex + 1), 4, y + 14);
            y += 22;
        }

        // ── Table header row ──────────────────────────────────────────────
        g2.setColor(new Color(8, 35, 70));
        int x = 0;
        for (int colWidthHeader : colWidths) {
            g2.fillRect(x, y, colWidthHeader, 20);
            x += colWidthHeader;
        }
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 9));
        x = 0;
        for (int c = 0; c < headers.length; c++) {
            g2.drawString(headers[c], x + 4, y + 14);
            x += colWidths[c];
        }
        y += 20;

        // ── Table data rows ───────────────────────────────────────────────
        int startRow = pageIndex * ROWS_PER_PAGE;
        int endRow   = Math.min(startRow + ROWS_PER_PAGE, totalRows);

        for (int r = startRow; r < endRow; r++) {
            Color rowBg = (r % 2 == 0) ? Color.WHITE : new Color(240, 243, 250);
            g2.setColor(rowBg);
            g2.fillRect(0, y, pageW, 16);

            // Row border
            g2.setColor(new Color(220, 225, 235));
            g2.drawLine(0, y + 16, pageW, y + 16);

            x = 0;
            for (int c = 0; c < 7; c++) {
                String val = data[r][c];

                // Status column coloring
                if (c == 6) {
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 8));
                    g2.setColor(
                        val.equals("Present") ? new Color(0, 140, 60) :
                        val.equals("Late")    ? new Color(200, 80, 0) :
                                               new Color(180, 40, 40));
                } else {
                    g2.setFont(new Font("Segoe UI", Font.PLAIN, 8));
                    g2.setColor(new Color(40, 40, 60));
                }

                // Truncate text if too wide
                FontMetrics fm = g2.getFontMetrics();
                while (val.length() > 1 && fm.stringWidth(val) > colWidths[c] - 6)
                    val = val.substring(0, val.length() - 1);

                g2.drawString(val, x + 4, y + 12);
                x += colWidths[c];
            }
            y += 16;
        }

        // ── Page footer ───────────────────────────────────────────────────
        int footerY = (int) pageFormat.getImageableHeight() - 20;
        g2.setColor(new Color(180, 185, 200));
        g2.drawLine(0, footerY, pageW, footerY);
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 8));
        g2.setColor(new Color(120, 130, 150));
        g2.drawString("EDP Attendance System", 4, footerY + 12);
        g2.drawString("Page " + (pageIndex + 1) + " of " + totalPages,
            pageW - 60, footerY + 12);

        return Printable.PAGE_EXISTS;

    }, pf);

    // Show print dialog to save as PDF
    job.setJobName("AttendanceReport");
    if (job.printDialog()) {
        try {
            job.print();
            JOptionPane.showMessageDialog(this,
                "Report printed/saved successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(this, "Print error: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

    // ── UI ────────────────────────────────────────────────────────────────────
    private void initComponents() {

        jPanel1     = new JPanel();
        headerPanel = new JPanel();
        headerLabel = new JLabel();
        accentLine  = new JPanel();
        cmbEvent    = new JComboBox<>();
        cmbStatus   = new JComboBox<>();
        cmbDate     = new JComboBox<>();
        txtSearch   = new JTextField();
        btnFilter   = new JButton();
        btnExport   = new JButton();
        btnClear    = new JButton();
        btnExit     = new JButton();
        lblTotal    = new JLabel("Total: 0");
        jScrollPane1 = new JScrollPane();
        reportTable  = new JTable();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("EDP Attendance System — Attendance Report");
        setMinimumSize(new Dimension(950, 650));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new Color(240, 243, 250));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // Header
        headerPanel.setBackground(new Color(8, 35, 70));
        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setText("  Attendance Report");
        headerPanel.add(headerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 45));
        jPanel1.add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 45));

        accentLine.setBackground(new Color(0, 190, 210));
        jPanel1.add(accentLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 45, 950, 3));

        // Filter card
        JPanel filterCard = new JPanel();
        filterCard.setBackground(Color.WHITE);
        filterCard.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 235), 1));
        filterCard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Font lf = new Font("Segoe UI", Font.BOLD, 11);
        Font ff = new Font("Segoe UI", Font.PLAIN, 12);
        Color lc = new Color(8, 35, 70);

        // Event filter
        JLabel lEvent = new JLabel("Event:");
        lEvent.setFont(lf); lEvent.setForeground(lc);
        filterCard.add(lEvent, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 12, 60, 18));
        cmbEvent.setFont(ff);
        filterCard.add(cmbEvent, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 32, 220, 30));

        // Status filter
        JLabel lStatus = new JLabel("Status:");
        lStatus.setFont(lf); lStatus.setForeground(lc);
        filterCard.add(lStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 12, 60, 18));
        cmbStatus.setFont(ff);
        filterCard.add(cmbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 32, 160, 30));

        // Date filter
        JLabel lDate = new JLabel("Date:");
        lDate.setFont(lf); lDate.setForeground(lc);
        filterCard.add(lDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 12, 60, 18));
        cmbDate.setFont(ff);
        filterCard.add(cmbDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 32, 180, 30));

        // Search
        JLabel lSearch = new JLabel("Search:");
        lSearch.setFont(lf); lSearch.setForeground(lc);
        filterCard.add(lSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 12, 60, 18));
        txtSearch.setFont(ff);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200,210,225), 1),
            BorderFactory.createEmptyBorder(4,8,4,8)));
        filterCard.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 32, 200, 30));

        jPanel1.add(filterCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 930, 78));

        // Buttons
        Font bf = new Font("Segoe UI", Font.BOLD, 11);

        btnFilter.setFont(bf); btnFilter.setText("🔍 FILTER");
        btnFilter.setBackground(new Color(8,35,70)); btnFilter.setForeground(Color.WHITE);
        btnFilter.setFocusPainted(false); btnFilter.setBorderPainted(false);
        btnFilter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFilter.addActionListener(e -> loadTable());
        jPanel1.add(btnFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 145, 120, 34));

        btnClear.setFont(bf); btnClear.setText("CLEAR");
        btnClear.setFocusPainted(false);
        btnClear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClear.addActionListener(e -> {
            cmbEvent.setSelectedIndex(0);
            cmbStatus.setSelectedIndex(0);
            cmbDate.setSelectedIndex(0);
            txtSearch.setText("");
            loadTable();
        });
        jPanel1.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 145, 90, 34));

        btnExport.setFont(bf); btnExport.setText("📄 EXPORT HTML/PDF");
        btnExport.setBackground(new Color(0,140,60)); btnExport.setForeground(Color.WHITE);
        btnExport.setFocusPainted(false); btnExport.setBorderPainted(false);
        btnExport.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExport.addActionListener(e -> exportToPDF());
        jPanel1.add(btnExport, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 145, 160, 34));

        btnExit.setFont(bf); btnExit.setText("EXIT");
        btnExit.setFocusPainted(false);
        btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExit.addActionListener(e -> { new Dashboard().setVisible(true); this.dispose(); });
        jPanel1.add(btnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 145, 70, 34));

        // Summary label
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTotal.setForeground(new Color(8, 35, 70));
        jPanel1.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 930, 22));

        // Table
        reportTable.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Student ID","Student Name","Course","Event","Date","Time","Status"}
        ) { @Override public boolean isCellEditable(int r, int c) { return false; } });
        reportTable.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        reportTable.setRowHeight(24);
        reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reportTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        reportTable.getTableHeader().setBackground(new Color(8, 35, 70));
        reportTable.getTableHeader().setForeground(Color.WHITE);

        // Color status column
        reportTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 243, 250));
                    if (column == 6 && value != null) {
                        String v = value.toString();
                        setForeground(v.equals("Present") ? new Color(0,140,60) :
                                      v.equals("Late")    ? new Color(200,80,0) :
                                                            new Color(180,40,40));
                        setFont(new Font("Segoe UI", Font.BOLD, 11));
                    } else {
                        setForeground(new Color(40,40,60));
                        setFont(new Font("Segoe UI", Font.PLAIN, 11));
                    }
                }
                return this;
            }
        });

        jScrollPane1.setViewportView(reportTable);
        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 218, 930, 400));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 650));
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new AttendanceReportScreen().setVisible(true));
    }

    // Variables
    private JPanel      jPanel1, headerPanel, accentLine, jScrollPane1_panel;
    private JLabel      headerLabel, lblTotal;
    private JComboBox<String> cmbEvent, cmbStatus, cmbDate;
    private JTextField  txtSearch;
    private JButton     btnFilter, btnExport, btnClear, btnExit;
    private JScrollPane jScrollPane1;
    private JTable      reportTable;
}