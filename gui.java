import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
class StudentGUI extends JFrame {
    private final List<Student> students = new ArrayList<>();
    public StudentGUI() {
        setTitle("Student Management System");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        // Button to add student
        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(e -> addStudentDialog());
        add(addButton);
        // Button to search by GPA
        JButton searchGPAButton = new JButton("Search by GPA");
        searchGPAButton.addActionListener(e -> searchByGPADialog());
        add(searchGPAButton);
        // Button to search by first name
        JButton searchFirstNameButton = new JButton("Search by First Name");
        searchFirstNameButton.addActionListener(e -> searchByFirstNameDialog());
        add(searchFirstNameButton);
        // Button to delete student by ID
        JButton deleteButton = new JButton("Delete Student by ID");
        deleteButton.addActionListener(e -> deleteStudentDialog());
        add(deleteButton);
        setVisible(true);
    }
    private void addStudentDialog() {
        JTextField idField = new JTextField(10);
        JTextField firstNameField = new JTextField(10);
        JTextField lastNameField = new JTextField(10);
        JTextField genderField = new JTextField(10);
        JTextField gpaField = new JTextField(10);
        JTextField levelField = new JTextField(10);
        JTextField addressField = new JTextField(10);
        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderField);
        panel.add(new JLabel("GPA:"));
        panel.add(gpaField);
        panel.add(new JLabel("Level:"));
        panel.add(levelField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        int result = JOptionPane.showConfirmDialog(null, panel, "Add Student", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String id = idField.getText();
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String gender = genderField.getText();
                double gpa = Double.parseDouble(gpaField.getText());
                int level = Integer.parseInt(levelField.getText());
                String address = addressField.getText();
                students.add(new Student(id, firstName, lastName, gender, gpa, level, address));
                JOptionPane.showMessageDialog(null, "Student added successfully!");
                // Update XML file
                Main.writeToXML(students);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error adding student. Check input values.");
            }
        }
    }
    private void searchByGPADialog() {
        String gpaInput = JOptionPane.showInputDialog("Enter GPA to search:");
        try {
            double gpa = Double.parseDouble(gpaInput);
            Main.searchByGPA(gpa);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid GPA entered.");
        }
    }
    private void searchByFirstNameDialog() {
        String firstName = JOptionPane.showInputDialog("Enter First Name to search:");
        if (firstName != null && !firstName.isEmpty()) {
            Main.searchByFirstName(firstName);
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid First Name.");
        }
    }
    private void deleteStudentDialog() {
        String id = JOptionPane.showInputDialog("Enter Student ID to delete:");
        if (id != null && !id.isEmpty()) {
            Main.deleteStudentByID(id);
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid ID.");
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentGUI::new);
    }
}