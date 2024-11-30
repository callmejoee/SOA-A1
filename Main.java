import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

class Student {
    public final String ID;
    public final String FirstName;
    public final String LastName;
    public final String Gender;
    public final double GPA;
    public final int Level;
    public final String Address;
    public Student(String ID, String FirstName, String LastName,String Gender, double GPA, int Level, String Address) {
        this.ID = ID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Gender = Gender;
        this.GPA = GPA;
        this.Level = Level;
        this.Address = Address;
    }
    public String getID() { return ID; }
    public String getFirstName() { return FirstName; }
    public String getLastName() { return LastName; }
    public String getGender(){return Gender;}
    public double getGPA() {return GPA;}
    public int getLevel() {return Level;}
    public String getAddress(){return Address;}
    public void printStudent() {
        System.out.println("Student Information:");
        System.out.println("ID: " + ID);
        System.out.println("First Name: " + FirstName);
        System.out.println("Last Name: " + LastName);
        System.out.println("Gender: " + Gender);
        System.out.println("GPA: " + GPA);
        System.out.println("Level: " + Level);
        System.out.println("Address: " + Address);
        System.out.println("-----------------------------");}
}
public class Main{
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        try (Scanner input = new Scanner(System.in)) {
            while (true) {
                System.out.print("\n");
                System.out.println(
                        """
                                1. Add Student Data.
                                2. Search By GPA.
                                3. Search By FirstName.
                                4. Delete Record.
                                5. Close.
                                """);
                System.out.println("Enter what u want to do: ");
                String choice = input.nextLine();
                switch (choice) {
                    case "1":
                        addStudent(students);
                        writeToXML(students);
                        break;
                    case "2":
                        System.out.print("Enter the GPA: ");
                        double gpa = input.nextDouble();
                        input.nextLine();
                        searchByGPA(gpa);
                        break;
                    case "3":
                        System.out.print("Enter the first name: ");
                        String name = input.nextLine();
                        searchByFirstName(name);
                        break;
                    case "4":
                        System.out.print("Enter the ID of the student to delete: ");
                        String studentID = input.nextLine();
                        deleteStudentByID(studentID);
                        break;
                    case "5":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Wrong Choice\n");
                }
            }
        }
    }
    public static void addStudent(List<Student> students) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of students: ");
        int numOfStudents = input.nextInt();
        input.nextLine();
        for (int i = 0; i < numOfStudents; i++) {
            System.out.println("Enter data of student " + (i + 1));
            System.out.print("ID: ");
            String id = input.nextLine();
            System.out.print("First Name: ");
            String firstName = input.nextLine();
            System.out.print("Last Name: ");
            String lastName = input.nextLine();
            System.out.print("Gender: ");
            String gender = input.nextLine();
            System.out.print("GPA: ");
            double gpa = input.nextDouble();
            input.nextLine();
            System.out.print("Level: ");
            int level = input.nextInt();
            input.nextLine();
            System.out.print("Address: ");
            String address = input.nextLine();
            //input.nextLine();
            students.add(new Student(id, firstName, lastName, gender, gpa, level, address));
        }
    }


    public static Student loadStudentFromXML(Element studentElement) {
        String studentID = studentElement.getAttribute("ID");
        String firstName = studentElement.getElementsByTagName("Firstname").item(0).getTextContent();
        String lastName = studentElement.getElementsByTagName("Lastname").item(0).getTextContent();
        String gender = studentElement.getElementsByTagName("Gender").item(0).getTextContent();
        double gpa = Double.parseDouble(studentElement.getElementsByTagName("GPA").item(0).getTextContent());
        int level = Integer.parseInt(studentElement.getElementsByTagName("Level").item(0).getTextContent());
        String address = studentElement.getElementsByTagName("Address").item(0).getTextContent();
        return new Student(studentID, firstName, lastName, gender, gpa, level, address);
    }
    public static void searchByGPA(double gpa) {
        try {
            File file = new File("students.xml");
            if (file.exists()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(file);
                doc.getDocumentElement().normalize();
                var nodeList = doc.getElementsByTagName("Student");
                boolean found = false;
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element studentElement = (Element) nodeList.item(i);
                    double studentGPA = Double.parseDouble(studentElement.getElementsByTagName("GPA").item(0).getTextContent());
                    if (studentGPA == gpa) {
                        Student student = loadStudentFromXML(studentElement);
                        student.printStudent();
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No student found with GPA: " + gpa);
                }
            } else {
                System.out.println("The file 'students.xml' does not exist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void searchByFirstName(String firstName) {
        try {
            File file = new File("students.xml");
            if (file.exists()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(file);
                doc.getDocumentElement().normalize();
                var nodeList = doc.getElementsByTagName("Student");
                boolean found = false;
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element studentElement = (Element) nodeList.item(i);
                    String studentFirstName = studentElement.getElementsByTagName("Firstname").item(0).getTextContent();
                    if (studentFirstName.equalsIgnoreCase(firstName)) {
                        Student student = loadStudentFromXML(studentElement);
                        student.printStudent();
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No student found with First Name: " + firstName);
                }
            } else {
                System.out.println("The file 'students.xml' does not exist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void deleteStudentByID(String studentID) {
        try {
            File file = new File("students.xml");
            if (file.exists()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(file);
                doc.getDocumentElement().normalize();
                var nodeList = doc.getElementsByTagName("Student");
                boolean found = false;
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element studentElement = (Element) nodeList.item(i);
                    String studentIDFromXML = studentElement.getAttribute("ID");
                    if (studentIDFromXML.equals(studentID)) {
                        studentElement.getParentNode().removeChild(studentElement);
                        found = true;
                        break;
                    }
                }
                if (found) {
                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(file);
                    transformer.transform(source, result);
                    System.out.println("Student with ID " + studentID + " deleted successfully.");
                } else {
                    System.out.println("No student found with ID " + studentID);
                }
            } else {
                System.out.println("The file 'students.xml' does not exist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeToXML(List<Student>students) {
        try {
            File file = new File("students.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            // Check if the file exists, and parse existing data;
            if (file.exists()) {
                doc = builder.parse(file);
                doc.getDocumentElement().normalize();
            }
            else {
                doc = builder.newDocument();
                Element rootElement = doc.createElement("University");
                doc.appendChild(rootElement);
            }
            Element root = doc.getDocumentElement();
            // Add new student to the XML
            for (Student stud : students) {
                Element studElement = doc.createElement("Student");
                studElement.setAttribute("ID", stud.getID());
                Element fnameElement = doc.createElement("Firstname");
                fnameElement.appendChild(doc.createTextNode(stud.getFirstName()));
                studElement.appendChild(fnameElement);
                Element lnameElement = doc.createElement("Lastname");
                lnameElement.appendChild(doc.createTextNode(stud.getLastName()));
                studElement.appendChild(lnameElement);
                Element genderElement = doc.createElement("Gender");
                genderElement.appendChild(doc.createTextNode(String.valueOf(stud.getGender())));
                studElement.appendChild(genderElement);
                Element gpaElement = doc.createElement("GPA");
                gpaElement.appendChild(doc.createTextNode(String.valueOf(stud.getGPA())));
                studElement.appendChild(gpaElement);
                Element levelElement = doc.createElement("Level");
                levelElement.appendChild(doc.createTextNode(String.valueOf(stud.getLevel())));
                studElement.appendChild(levelElement);
                Element addressElement = doc.createElement("Address");
                addressElement.appendChild(doc.createTextNode(String.valueOf(stud.getAddress())));
                studElement.appendChild(addressElement);
                // Append the stud to the root
                root.appendChild(studElement);
            }
            DOMSource dom = new DOMSource(doc);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StreamResult result = new StreamResult("students.xml");
            transformer.transform(dom, result);
            System.out.println("Student added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}