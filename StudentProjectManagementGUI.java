package STUDENTMANAGEMNT;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class StudentProjectManagementGUI extends JFrame {
    private List<Student> students;
    private List<Course> courses;
    private Map<Integer, Student> rollNumberToStudentMap;
    private boolean teacherLoggedIn;

    private DefaultTableModel studentTableModel;
    private DefaultTableModel courseTableModel;
    private JTextArea outputTextArea;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();





    public StudentProjectManagementGUI() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
        rollNumberToStudentMap = new HashMap<>();
        teacherLoggedIn = false;

        setTitle("Student Project Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel actionPanel = createActionPanel();
        JPanel studentPanel = createStudentPanel();
        JPanel coursePanel = createCoursePanel();
        Color backgroundColor = new Color(213, 205, 205);
        tabbedPane.addTab("Registration", actionPanel);
        actionPanel.setBackground(backgroundColor);
        studentPanel.setBackground(backgroundColor);
        coursePanel.setBackground(backgroundColor);
        actionPanel.setBorder(null);
        tabbedPane.addTab("Students", studentPanel);
        tabbedPane.addTab("Courses", coursePanel);
        tabbedPane.setBackground(Color.black);
        tabbedPane.setForeground(Color.gray);
        add(tabbedPane, BorderLayout.CENTER);
        outputTextArea = new JTextArea();


        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        

        studentTableModel = new DefaultTableModel();
        studentTableModel.addColumn("Name");
        studentTableModel.addColumn("ID Number");
        studentTableModel.addColumn("Address");
        studentTableModel.addColumn("Contact Details");

        JTable studentTable = new JTable(studentTableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        panel.add(scrollPane, BorderLayout.CENTER);


        return panel;
    }

    private JPanel createCoursePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        courseTableModel = new DefaultTableModel();
        courseTableModel.addColumn("Course Name");
        courseTableModel.addColumn("Course Code");

        JTable courseTable = new JTable(courseTableModel);
        JScrollPane scrollPane = new JScrollPane(courseTable);

        panel.add(scrollPane, BorderLayout.CENTER);



        return panel;
    }
    private JPanel createActionPanel() {
        JPanel panel = new JPanel(null);
    
    
        JButton addStudentButton = new JButton("Register Student");
        addStudentButton.setBounds(415,20, 250, 70);
        addStudentButton.setBackground(Color.black);
        addStudentButton.setForeground(Color.white);
        addStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });
    
        JButton addCourseButton = new JButton("Register Course");
        addCourseButton.setBounds(415, 110, 250, 70);
        addCourseButton.setBackground(Color.black);
        addCourseButton.setForeground(Color.white);
        addCourseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCourse();
            }
        });
    
        JButton assignGradesButton = new JButton("Teachers-Login");
        assignGradesButton.setBounds(15, 70, 250, 70);
        assignGradesButton.setBackground(Color.black);
        assignGradesButton.setForeground(Color.white);
        assignGradesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                assignGrades();
            }
        });
    
        JButton displayRegisteredStudentsButton = new JButton("Display Registered Students");
        displayRegisteredStudentsButton.setBackground(Color.black);
        displayRegisteredStudentsButton.setForeground(Color.white);
        displayRegisteredStudentsButton.setBounds(15, 340, 250, 70);
        displayRegisteredStudentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayRegisteredStudents();
            }
        });
    
        JButton displayRegisteredCoursesButton = new JButton("Display Registered Courses");
        displayRegisteredCoursesButton.setBounds(15, 430, 250, 70);
        displayRegisteredCoursesButton.setBackground(Color.black);
        displayRegisteredCoursesButton.setForeground(Color.WHITE);
        displayRegisteredCoursesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayRegisteredCourses();
            }
        });
    
        panel.add(addStudentButton);
        panel.add(addCourseButton);
        panel.add(assignGradesButton);
        panel.add(displayRegisteredStudentsButton);
        panel.add(displayRegisteredCoursesButton);
    
        return panel;
    }
    
    
    
    private void addStudent() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
    
        JTextField nameField = new JTextField();
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
    
        JTextField rollNumberField = new JTextField();
        panel.add(new JLabel("ID Number:"));
        panel.add(rollNumberField);
    
        JTextField addressField = new JTextField();
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
    
        JTextField contactDetailsField = new JTextField();
        panel.add(new JLabel("Contact Details:"));
        panel.add(contactDetailsField);
    
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Student",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            int rollNumber = Integer.parseInt(rollNumberField.getText());
            String address = addressField.getText();
            String contactDetails = contactDetailsField.getText();
    
            // Check if student with the same roll number already exists in the file
            boolean studentExistsInFile = false;
    
            try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PC\\Desktop\\studentrecordmanagementsystem\\StudentRecordManagementSystem\\student.txt"))) {
                String line;
                boolean isFirstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue; // Skip the header row
                    }
                    String[] data = line.split("\t\t");
                    if (data.length >= 2) { // Check if the data array has at least 2 elements
                        int existingRollNumber = Integer.parseInt(data[1]);
                        if (existingRollNumber == rollNumber) {
                            studentExistsInFile = true;
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
    
            if (studentExistsInFile) {
                JOptionPane.showMessageDialog(this, "Student with the same ID number already exists in the file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Student student = new Student(name, rollNumber, address, contactDetails);
                students.add(student);
                rollNumberToStudentMap.put(rollNumber, student);
    
                // Update the student table
                studentTableModel.addRow(new Object[]{name, rollNumber, address, contactDetails});
    
                // Write student data to file
                try (FileWriter writer = new FileWriter("C:\\Users\\PC\\Desktop\\studentmanagemnt\\STUDENTMANAGEMNT\\student.txt", true);
                     BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
                    // Check if file is empty
                    File file = new File("C:\\Users\\PC\\Desktop\\studentmanagemnt\\STUDENTMANAGEMNT\\student.txt");
                    if (file.length() == 0) {
                        bufferedWriter.write("Student Name\t\tRoll Number\t\t\tAddress\t\t\tContact Details\n");
                    }
                    bufferedWriter.write(name + "\t\t" + rollNumber + "\t\t" + address + "\t\t" + contactDetails + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
    
                outputTextArea.setText("Student added successfully!");
            }
        }
    }
    
    



    private void addCourse() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
    
        JTextField courseNameField = new JTextField();
        JTextField courseCodeField = new JTextField();
    
        panel.add(new JLabel("Course Name:"));
        panel.add(courseNameField);
        panel.add(new JLabel("Course Code:"));
        panel.add(courseCodeField);
    
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Course",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
        if (result == JOptionPane.OK_OPTION) {
            String courseName = courseNameField.getText();
            String courseCode = courseCodeField.getText();
    
            Course course = new Course(courseName, courseCode);
            courses.add(course);
    
            // Update the course table
            courseTableModel.addRow(new Object[]{courseName, courseCode});
    
            // Write course data to file
            try (FileWriter writer = new FileWriter("C:\\Users\\PC\\Desktop\\studentmanagemnt\\STUDENTMANAGEMNT\\course.txt", true);
                 BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
                // Check if file is empty
                File file = new File("C:\\Users\\PC\\Desktop\\studentmanagemnt\\STUDENTMANAGEMNT\\course.txt");
                if (file.length() == 0) {
                    bufferedWriter.write("Course Name\t\tCourse Code\n");
                }
                bufferedWriter.write(courseName + "\t\t" + courseCode + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
    
            outputTextArea.setText("Course added successfully!");
        }
    }
private void displayRegisteredStudents() {
    JPanel panel = new JPanel(new BorderLayout());

    JComboBox<Course> courseComboBox = new JComboBox<>(courses.toArray(new Course[0]));
    courseComboBox.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {   
        }
    });

    panel.add(courseComboBox, BorderLayout.NORTH);

    JTable studentTable = new JTable();
    studentTable.setModel(new DefaultTableModel(
            new Object[]{"Name", "ID Number", "Address", "Contact Details"}, 0));

    JScrollPane scrollPane = new JScrollPane(studentTable);
    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Set FlowLayout alignment to the right
    JTextField searchField = new JTextField(15);
    JButton searchButton = new JButton("Search");

    searchButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String searchRollNumber = searchField.getText();
            try {
                searchStudentsByRollNumber(searchRollNumber, studentTable.getModel());
            } catch (StudentNotFoundException ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Student Not Found", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    searchPanel.add(searchField);
    searchPanel.add(searchButton);
    panel.add(searchPanel, BorderLayout.NORTH); // Set the search panel in the BorderLayout.NORTH position

    JButton displayButton = new JButton("Display");
    displayButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
            model.setRowCount(0); // Clear the table

            try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PC\\Desktop\\studentmanagemnt\\STUDENTMANAGEMNT\\student.txt"))) {
                String line;
                boolean isFirstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue; // Skip the header row
                    }
                    String[] data = line.split("\t\t");
                    String name = data[0];
                    int rollNumber = Integer.parseInt(data[1]);
                    String address = data[2];
                    String contactDetails = data[3];
                    model.addRow(new Object[]{name, rollNumber, address, contactDetails});
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    });

    panel.add(displayButton, BorderLayout.SOUTH);

    // Add the panel to your UI or frame
    JOptionPane.showMessageDialog(this, panel, "Display Registered Students", JOptionPane.PLAIN_MESSAGE);
}

private void searchStudentsByRollNumber(String rollNumber, TableModel model) throws StudentNotFoundException {
    DefaultTableModel defaultModel = (DefaultTableModel) model;
    defaultModel.setRowCount(0); // Clear the table

    try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PC\\Desktop\\studentmanagemnt\\STUDENTMANAGEMNT\\student.txt"))) {
        String line;
        boolean isFirstLine = true;
        while ((line = reader.readLine()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                continue; // Skip the header row
            }
            String[] data = line.split("\t\t");
            String name = data[0];
            int studentRollNumber = Integer.parseInt(data[1]);
            String address = data[2];
            String contactDetails = data[3];
            if (String.valueOf(studentRollNumber).equalsIgnoreCase(rollNumber)) {
                defaultModel.addRow(new Object[]{name, studentRollNumber, address, contactDetails});
                return; // Exit the loop after finding the matching student
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    throw new StudentNotFoundException("No student found with the given roll number.");
}

class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String message) {
        super(message);
    }
}

private void displayRegisteredCourses() {
    File file = new File("C:\\Users\\PC\\Desktop\\studentmanagemnt\\STUDENTMANAGEMNT\\course.txt");
    if (file.length() == 0) {
        JOptionPane.showMessageDialog(this, "No courses available.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    JPanel panel = new JPanel(new BorderLayout());

    JTable courseTable = new JTable();
    courseTable.setModel(new DefaultTableModel(new Object[]{"Course Name", "Course Code"}, 0));

    JScrollPane scrollPane = new JScrollPane(courseTable);
    panel.add(scrollPane, BorderLayout.CENTER);

    JButton displayButton = new JButton("Display");
    displayButton.addActionListener(new ActionListener() {
        private Object currentStudent;

        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel) courseTable.getModel();
            model.setRowCount(0); // Clear the table

            List<Course> registeredCourses = new ArrayList<>(); // Create a new list to hold registered courses

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                boolean isFirstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue; // Skip the header row
                    }
                    String[] data = line.split("\t\t");
                    if (data.length >= 2) { // Check if the data array has at least 2 elements
                        String courseName = data[0];
                        String courseCode = data[1];
                        model.addRow(new Object[]{courseName,courseCode});
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            
        }
}});

    panel.add(displayButton, BorderLayout.SOUTH);

    JOptionPane.showMessageDialog(this, panel, "Display Registered Courses", JOptionPane.PLAIN_MESSAGE);
}


//assign point and grades to each student for each course from file 
private void assignGrades() {
    String password = JOptionPane.showInputDialog(this, "Enter password:");
    try (BufferedReader passwordReader = new BufferedReader(new FileReader("C:\\Users\\PC\\Desktop\\studentmanagemnt\\STUDENTMANAGEMNT\\password.txt"))) {
        String storedPassword;
        boolean passwordMatch = false;
        
        while ((storedPassword = passwordReader.readLine()) != null) {
            if (password != null && password.equals(storedPassword)) {
                passwordMatch = true;
                break;
            }
        }
    
        // Compare the entered password with the stored password
        if (passwordMatch) {
        JPanel panel = new JPanel(new BorderLayout());

        // Create the table with required columns
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Student Name");
        tableModel.addColumn("ID Number");
        tableModel.addColumn("Address");
        tableModel.addColumn("Contact Details");

        JTable gradesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(gradesTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Read student data from file and populate the table
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PC\\Desktop\\studentmanagemnt\\STUDENTMANAGEMNT\\student.txt"))) {
            String line;
            int lineCounter = 0;
            while ((line = reader.readLine()) != null) {
                if (lineCounter > 0) { // Skip the first line
                    String[] studentData = line.split("\t\t");
                    tableModel.addRow(studentData);
                }
                lineCounter++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        

        JButton assignButton = new JButton("Assign Grades");
        assignButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the data from the table and assign grades for each course
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String studentName = (String) tableModel.getValueAt(i, 0);
                    String idNumber = (String) tableModel.getValueAt(i, 1);
                    String address = (String) tableModel.getValueAt(i, 2);
                    String contactDetails = (String) tableModel.getValueAt(i, 3);

                    // Read course data from file
                    try (BufferedReader courseReader = new BufferedReader(new FileReader("C:\\Users\\PC\\Desktop\\studentmanagemnt\\STUDENTMANAGEMNT\\course.txt"))) {
                        String courseLine;
                        int courseCounter = 0;
                        while ((courseLine = courseReader.readLine()) != null) {
                            if (courseCounter > 0) { // Skip the first line
                                String[] courseData = courseLine.split("\t\t");
                                String courseIdFromFile = courseData[0];
                                String courseName = courseData[0];
                    
                                // Get the points entered by the teacher for the current course
                                String points = JOptionPane.showInputDialog(panel, "Enter points for " + studentName + " in " + courseName + ":");
                                if (points != null) {
                                    // Assign grades based on student data and course
                                    String grades = calculateGrades(points, courseName);
                    
                                    // Update the table with assigned grades and points
                                    tableModel.addColumn(courseName + " Points"); // Add column for course points
                                    tableModel.addColumn(courseName + " Grades"); // Add column for course grades
                    
                                    tableModel.setValueAt(points, i, tableModel.getColumnCount() - 2); // Set points value in table
                                    tableModel.setValueAt(grades, i, tableModel.getColumnCount() - 1); // Set grades value in table
                                }
                            }
                            courseCounter++;
                        }ArrayList<String> coursePoints = new ArrayList<>();
                        ArrayList<String> courseGrades = new ArrayList<>();
                        
                        while ((courseLine = courseReader.readLine()) != null) {
                            if (courseCounter > 0) { // Skip the first line
                                String[] courseData = courseLine.split("\t\t");
                                String courseIdFromFile = courseData[0];
                                String courseName = courseData[0];
                        
                                // Get the points entered by the teacher for the current course
                                String points = JOptionPane.showInputDialog(panel, "Enter points for " + studentName + " in " + courseName + ":");
                                if (points != null) {
                                    // Assign grades based on student data and course
                                    String grades = calculateGrades(points, courseName);
                        
                                    // Add course points and grades to ArrayLists
                                    coursePoints.add(points);
                                    courseGrades.add(grades);
                                }
                            }
                            courseCounter++;
                        }
                        
                        for (int k = 0; k < tableModel.getRowCount(); k++) {
                            for (int j = 0; j < coursePoints.size(); j++) {
                                // Set points value in table
                                tableModel.setValueAt(coursePoints.get(j), k, (j * 2) + 2);
                        
                                // Set grades value in table
                                tableModel.setValueAt(courseGrades.get(j), k, (j * 2) + 3);
                            }
                        }
                        
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    
                }

                // Write the table data to the grades file
                try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\PC\\Desktop\\studentmanagemnt\\STUDENTMANAGEMNT\\grades.txt"))) {
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        StringBuilder line = new StringBuilder();
                        for (int j = 0; j < tableModel.getColumnCount(); j++) {
                            line.append(tableModel.getValueAt(i, j));
                            if (j < tableModel.getColumnCount() - 1) {
                                line.append("\t\t");
                            }
                        }
                        writer.println(line.toString());
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        panel.add(assignButton, BorderLayout.SOUTH);
        JOptionPane.showMessageDialog(this, panel);
    } else {
        JOptionPane.showMessageDialog(this, "Invalid password!");
    }
}catch (IOException ex) {
        ex.printStackTrace();
    }

}

private String calculateGrades(String points, String courseName) {
    int pointsValue = Integer.parseInt(points);
    if (pointsValue >= 90 && pointsValue <= 100) {
        return "A+";
    }
    if (pointsValue > 85 && pointsValue < 90) {
        return "A";
    } if (pointsValue >= 80 && pointsValue < 85) {
        return "A-";
    }else if (pointsValue >= 75 && pointsValue < 80) {
        return "B+";
    } else if (pointsValue >= 70 && pointsValue < 75) {
        return "B";
    } else if (pointsValue >= 65 && pointsValue < 70) {
        return "B-";
    } else if (pointsValue >= 60 && pointsValue < 65) {
        return "C+";
    } else if (pointsValue >= 50 && pointsValue < 60) {
        return "C";
    } else if (pointsValue >= 45 && pointsValue < 50) {
        return "C-";
    } else if (pointsValue >= 40 && pointsValue < 45) {
        return "D";
    } else if (pointsValue >= 0 && pointsValue < 40) {
        return "F";
    }
// Return the course name as the grade if it is not recognized
    else{
        return "NG";
    }
    
    
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                StudentProjectManagementGUI graphics = new StudentProjectManagementGUI();
                graphics.setSize(700, 600);
                graphics.setVisible(true);
                graphics.setResizable(false);
                graphics.setUndecorated(true);

            }});
    }
}