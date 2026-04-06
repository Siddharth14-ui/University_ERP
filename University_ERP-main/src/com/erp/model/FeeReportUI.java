package com.erp.model;

import com.erp.dao.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class FeeReportUI {
    private DefaultTableModel model;
    private int feeId;
    private JTable table;

    private JTextField studentIdField;
    private JTextField semesterField;
    private JComboBox<String> feeTypeDropdown;
    private JComboBox<String> courseDropdown;


    public static void main(String[] args){


        new FeeReportUI().createUI();
    }

    public void createUI(){

        Color primary = new Color(45, 62, 80);      // dark blue
        Color accent = new Color(52, 152, 219);     // blue
        Color bg = new Color(245, 247, 250);        // light background
        Color text = new Color(44, 62, 80);



        JFrame frame = new JFrame("ERP - Fee Dashboard");
        frame.setSize(1400,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();

        JButton refreshBtn = new JButton("Refresh");
        JButton addFeeBtn = new JButton("Add Fee");
        JButton paymentBtn = new JButton("Make Payment");

        JButton addStudentBtn = new JButton("Add Student");
        JButton deleteStudentBtn = new JButton("Delete Student");
        JButton updateStudentBtn = new JButton("Update Student");
        JButton addCourseBtn = new JButton("Add Course");
        JButton updateCourseBtn = new JButton("Update Course");
        JButton deleteCourseBtn = new JButton("Delete Course");
        JButton attendanceBtn = new JButton("Attendance");

        topPanel.add(refreshBtn);
        topPanel.add(addFeeBtn);
        topPanel.add(paymentBtn);
        topPanel.add(addStudentBtn);
        topPanel.add(deleteStudentBtn);
        //topPanel.add(updateStudentBtn);

        topPanel.add(addCourseBtn);
        topPanel.add(updateCourseBtn);
        topPanel.add(deleteCourseBtn);
        topPanel.add(attendanceBtn);

        topPanel.setBackground(bg);

        frame.add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Fee ID","StudentId","Name","Semester","Fee Type","Course","Total","Paid","Due"};

        model = new DefaultTableModel(new String[]{"Fee ID","Student ID","Name","Semester","Fee Type","Course","Total","Paid","Due"},0);

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        studentIdField = new JTextField(15);
        semesterField = new JTextField(15);

        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);

        table.getSelectionModel().addListSelectionListener(e -> {

            if (e.getValueIsAdjusting()) return;

            int row = table.getSelectedRow();

            if (row != -1) {
                studentIdField.setText(model.getValueAt(row, 1).toString());
                semesterField.setText(model.getValueAt(row, 3).toString());
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Fee Records"));
        frame.add(scrollPane, BorderLayout.CENTER);

        loadData(model);

        refreshBtn.addActionListener(e -> loadData(model));

        addFeeBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            String studentId = "";
            String semester = "";

            if (selectedRow != -1) {
                studentId = model.getValueAt(selectedRow, 1).toString();
                semester = model.getValueAt(selectedRow, 3).toString();
            }

            openAddFeeForm(studentId, semester);
        });

        paymentBtn.addActionListener(e -> {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(null, "Select a row first!");
                        return;
                    }
                    int feeId = ((Number) model.getValueAt(selectedRow, 0)).intValue();
            System.out.println("selected fee id: "+feeId);

                    openPaymentForm(feeId);

                });

        JButton[] buttons = {refreshBtn, addFeeBtn, paymentBtn,addStudentBtn,deleteStudentBtn,addCourseBtn,updateCourseBtn,deleteCourseBtn,attendanceBtn};

        for (JButton btn : buttons) {
            btn.setBackground(accent);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.BOLD, 13));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBorder(BorderFactory.createEmptyBorder(8,15,8,15));
        }

        JTextField[] fields = {studentIdField, semesterField};

        for (JTextField tf : fields) {
            tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            tf.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200,200,200)),
                    BorderFactory.createEmptyBorder(5,5,5,5)
            ));
        }

        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(primary);
        table.getTableHeader().setForeground(Color.WHITE);

        table.setSelectionBackground(new Color(174, 214, 241));
        table.setGridColor(new Color(220,220,220));


        frame.getContentPane().setBackground(bg);
        frame.setVisible(true);


        addStudentBtn.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField phoneField = new JTextField();

            Object[] colorFields = {
                    "Student ID:", idField,
                    "Name:", nameField,
                    "Email:", emailField,
                    "Phone:", phoneField
            };

            int option = JOptionPane.showConfirmDialog(null, colorFields, "Add Student", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                Student s = new Student(
                        idField.getText(),
                        nameField.getText(),
                        emailField.getText(),
                        phoneField.getText()
                );

                new StudentDAO().addStudent(s);
            }
        });

        deleteStudentBtn.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Student ID to delete:");
            if (id != null && !id.isEmpty()) {
                new StudentDAO().deleteStudent(id);
            }
        });

        //course button actionlistener
        addCourseBtn.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField creditsField = new JTextField();

            Object[] nameFields = {
                    "Course ID:", idField,
                    "Course Name:", nameField,
                    "Credits:", creditsField
            };

            int option = JOptionPane.showConfirmDialog(null,nameFields, "Add Course", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                try {
                    Course c = new Course(
                            idField.getText(),
                            nameField.getText(),
                            Integer.parseInt(creditsField.getText())
                    );

                    new CourseDAO().addCourse(c);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Input");
                }
            }
        });

        //updating course
        updateCourseBtn.addActionListener(e -> {
            JTextField oldId = new JTextField();
            JTextField newId = new JTextField();
            JTextField newName = new JTextField();
            JTextField newCredits = new JTextField();

            Object[] namingFields = {
                    "Existing Course ID:", oldId,
                    "New Course ID:", newId,
                    "New Name:", newName,
                    "New Credits:", newCredits
            };

            int option = JOptionPane.showConfirmDialog(null, namingFields, "Update Course", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                try {
                    new CourseDAO().updateCourse(
                            newId.getText(),
                            oldId.getText(),
                            newName.getText(),
                            Integer.parseInt(newCredits.getText())
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Input");
                }
            }
        });

        //course deletion
        deleteCourseBtn.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Course ID to delete:");

            if (id != null && !id.isEmpty()) {
                new CourseDAO().deleteCourse(id);
            }
        });

        //attendance ui actionListener
        attendanceBtn.addActionListener(e -> openAttendanceUI());

    }

    public int getFeeId(){
        return feeId ;
    }

    private void loadData(DefaultTableModel model){
        model.setRowCount(0);

        FeesReportDAO dao = new FeesReportDAO();
        List<FeeReport> reports = dao.getFullFeesReport();

        System.out.println("reloading table");

        for(FeeReport fr : reports){
            model.addRow(new Object[]{
                    fr.getFeeId(),
                    fr.getStudentId(),
                    fr.getStudentName(),
                    fr.getSemester(),
                    fr.getFeeName(),
                    fr.getCourseName(),
                    fr.getTotalFee(),
                    fr.getPaidAmount(),
                    fr.getDueAmount()
            });
            System.out.println("inserted in jtbale: "+model.getValueAt(model.getRowCount()-1,0));
        }
    }

    private void openAddFeeForm(String studentId, String semester) {

        JDialog dialog = new JDialog();
        dialog.setTitle("Add Student Fee");
        dialog.setSize(400, 350);
        dialog.setLayout(new BorderLayout());

        // 🔥 FORM PANEL
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 🔹 LOCAL FIELDS ONLY
        JTextField studentIdInput = new JTextField();
        JTextField semesterInput = new JTextField();
        studentIdInput.setText(studentId);
        semesterInput.setText(semester);

        JTextField amountField = new JTextField();

        // 🔹 DROPDOWNS
        JComboBox<String> feeTypeDropdown = new JComboBox<>();
        JComboBox<String> courseDropdown = new JComboBox<>();

        // 🔹 LOAD DATA
        Map<Integer, String> feeTypeMap = FeeTypeDAO.getAllFeeTypes();
        Map<String, String> courseMap = CourseDAO.getAllCourses();

        for (String name : feeTypeMap.values()) {
            feeTypeDropdown.addItem(name);
        }

        for (String name : courseMap.values()) {
            courseDropdown.addItem(name);
        }

        courseDropdown.setEnabled(false);

        // 🔹 ENABLE COURSE ONLY WHEN NEEDED
        feeTypeDropdown.addActionListener(e -> {
            String selected = (String) feeTypeDropdown.getSelectedItem();

            if ("Course".equalsIgnoreCase(selected)) {
                courseDropdown.setEnabled(true);
            } else {
                courseDropdown.setEnabled(false);
            }
        });

        // 🔥 ADD COMPONENTS PROPERLY
        formPanel.add(new JLabel("Student ID:"));
        formPanel.add(studentIdInput);

        formPanel.add(new JLabel("Semester:"));
        formPanel.add(semesterInput);

        formPanel.add(new JLabel("Fee Type:"));
        formPanel.add(feeTypeDropdown);

        formPanel.add(new JLabel("Course:"));
        formPanel.add(courseDropdown);

        formPanel.add(new JLabel("Amount:"));
        formPanel.add(amountField);

        // 🔥 BUTTON PANEL
        JPanel buttonPanel = new JPanel();
        JButton submitBtn = new JButton("Submit");
        buttonPanel.add(submitBtn);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // 🔥 SUBMIT LOGIC
        submitBtn.addActionListener(e -> {
            try {
                String inputStudentId = studentIdInput.getText();
                String inputSemester = semesterInput.getText();
                double amount = Double.parseDouble(amountField.getText());

                // 🔹 GET FEE TYPE ID
                String selected = (String) feeTypeDropdown.getSelectedItem();

                int feeTypeId = -1;
                for (Map.Entry<Integer, String> entry : feeTypeMap.entrySet()) {
                    if (entry.getValue().equals(selected)) {
                        feeTypeId = entry.getKey();
                        break;
                    }
                }

                // 🔹 GET COURSE ID
                String referenceId = null;

                if (courseDropdown.isEnabled()) {
                    String selectedCourse = (String) courseDropdown.getSelectedItem();

                    for (Map.Entry<String, String> entry : courseMap.entrySet()) {
                        if (entry.getValue().equals(selectedCourse)) {
                            referenceId = entry.getKey();
                            break;
                        }
                    }
                }

                // 🔹 INSERT
                StudentFeesDAO dao = new StudentFeesDAO();
                dao.addStudentFees(inputStudentId, inputSemester, feeTypeId, referenceId, amount);

                dialog.dispose();
                loadData(model);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid Input");
            }
        });

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void openPaymentForm(int feeId){
        JDialog dialog = new JDialog();
        dialog.setTitle("Make Payment");
        dialog.setSize(300,200);
        dialog.setLayout(new GridLayout(3,2));

//        JTextField feeIdField = new JTextField();
        JTextField amountField = new JTextField();

//        dialog.add(new JLabel("Student Fee ID: "));
//        dialog.add(feeIdField);
        dialog.add(new JLabel());
        dialog.add(new JLabel());

        dialog.add(new JLabel("Amount: "));
        dialog.add(amountField);

        JButton submitBtn = new JButton("Submit");
        dialog.add(new JLabel());
        dialog.add(submitBtn);

        submitBtn.addActionListener(e->{
            try{
//                int feeId = Integer.parseInt(feeIdField.getText());
                double amount = Double.parseDouble(amountField.getText());

                PaymentDAO dao = new PaymentDAO();
                dao.addPayment(feeId, amount);

                dialog.dispose();
                loadData(model);

            }catch (Exception exp){
                JOptionPane.showMessageDialog(dialog, "Invalid Input");
            }
        });
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void openAttendanceUI() {

        JDialog dialog = new JDialog();
        dialog.setTitle("Attendance");
        dialog.setSize(600,400);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(null);

        // Table
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID","Student","Course","Date","Status"}, 0
        );

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        // Form
        JPanel form = new JPanel(new GridLayout(5,2));

        JTextField studentField = new JTextField();
        JTextField courseField = new JTextField();
        JTextField dateField = new JTextField("2026-04-06");

        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Present","Absent"});

        form.add(new JLabel("Student ID"));
        form.add(studentField);

        form.add(new JLabel("Course ID"));
        form.add(courseField);

        form.add(new JLabel("Date (YYYY-MM-DD)"));
        form.add(dateField);

        form.add(new JLabel("Status"));
        form.add(statusBox);

        JButton markBtn = new JButton("Mark");
        form.add(new JLabel());
        form.add(markBtn);

        // Load data
        Runnable loadData = () -> {
            try {
                model.setRowCount(0);
                AttendanceDAO dao = new AttendanceDAO();
                java.sql.ResultSet rs = dao.getAttendance();

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("attendance_id"),
                            rs.getString("student_id"),
                            rs.getString("course_id"),
                            rs.getDate("date"),
                            rs.getString("status")
                    });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };

        loadData.run();

        // Button action
        markBtn.addActionListener(e -> {
            try {
                new AttendanceDAO().markAttendance(
                        studentField.getText(),
                        courseField.getText(),
                        dateField.getText(),
                        (String) statusBox.getSelectedItem()
                );

                loadData.run();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error");
            }
        });

        dialog.add(form, BorderLayout.NORTH);
        dialog.add(scroll, BorderLayout.CENTER);

        dialog.setVisible(true);
    }


}
