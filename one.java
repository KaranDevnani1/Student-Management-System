import java.util.*;
import java.io.*;
abstract class Person {    // Abstract Class: Person
    protected String name;// Attributes
    protected int ID;
    protected int age;
    public abstract void displayInfo();// Abstract method
}
interface Enrollable { // Interface: Enrollable
    void enrollCourse(Course course);
    void withdrawCourse(Course course);
}
abstract class CourseParticipant implements Enrollable { // Abstract Class: CourseParticipant
    protected List<Course> enrolledCourses; // Attribute
    public CourseParticipant() { // Constructor
        enrolledCourses = new ArrayList<>();
    }
    public void enrollCourse(Course course) {     // Methods
        enrolledCourses.add(course);
        System.out.println("Enrolled in course: " + course.getName());
    }
    public void withdrawCourse(Course course) {
        enrolledCourses.remove(course);
        System.out.println("Withdrawn from course: " + course.getName());
    }
}
class Student extends Person implements Enrollable {  // Class: Student (extends Person, implements Enrollable)
    CourseParticipant enrolledCourses;     // Methods
    public void displayInfo() {
        System.out.println("Student Name: " + name); // Implementation to display student information
        System.out.println("Student ID: " + ID);
        System.out.println("Student Age: " + age);
    }
    public void enrollCourse(Course course) {
        enrolledCourses.enrollCourse(course);// Implementation to enroll in a course
    }
    public void withdrawCourse(Course course) {
        enrolledCourses.withdrawCourse(course); // Implementation to withdraw from a course
    }
}
class Teacher extends Person{// Class: Teacher (extends Person)
    public void displayInfo() {     // Methods
        System.out.println("Teacher Name: " + name); // Implementation to display teacher information
        System.out.println("Teacher ID: " + ID);
        System.out.println("Teacher Age: " + age);
    }
    Teacher(String name, int ID, int age) {
        this.name = name;
        this.ID = ID;
        this.age = age;
    }
    public String getName() {
        return name;
    }
}
class Course { // Class: Course
    String name; // Attributes
    String code;
    String instructor;
    List<Student> enrolledStudents;
    // Constructor
    Course(String name, String code, String instructor) {
        this.name = name;
        this.code = code;
        this.instructor = instructor;
        this.enrolledStudents = new ArrayList<>();
    }
    // Methods
    String getName() {
        return name;
    }
    void addStudent(Student student) {
        // Implementation to add a student to the course
        enrolledStudents.add(student);
        System.out.println("Student " + student.name + " added to course " + name);
    }
    String getCode() {
        return code;
    }
    String getInstructor() {
        return instructor;
    }
}
class CourseManager { // Class: CourseManager
    List<Course> courses;
    CourseManager() {
        courses = new ArrayList<>();
    }
    void addCourse(Course course)  {        // Implementation to add a new course
        courses.add(course);
        System.out.println("Course added: " + course.getName());
    }
    void enrollStudent(Course course, Student student) {
        course.addStudent(student); // Implementation to enroll a student in a course
    }
    List<Course> getCourses() {
        return courses;
    }
}
class StudentManager { // Class: StudentManager
     List<Student> students;    // Methods
    StudentManager() {
        students = new ArrayList<>();
    }
    void addStudent(Student student) {
        System.out.println("Student added: " + student.name);// Implementation to add a new student
        students.add(student);
    }
    void enrollInCourse(Student student, Course course) {
        course.addStudent(student);// Implementation to enroll a student in a course
    }
    List<Student> getStudents() {
        return students;
    }
}
class TeacherManager { // Class: TeacherManager
    List<Teacher> teachers;    // Methods
    TeacherManager() {
        teachers = new ArrayList<>();
    }
    void addTeacher(Teacher teacher) {
        System.out.println("Teacher added: " + teacher.name);// Implementation to add a new teacher
        teachers.add(teacher);
    }
    List<Teacher> getTeachers() {
        return teachers;    
    }
}
class FileManager {// Class: FileManager
    List<Student> loadStudentsFromFile()  {     // Methods
        List<Student> students = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("students.csv"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    Student student = new Student();
                    student.name = parts[0];
                    student.ID = Integer.parseInt(parts[1]);
                    student.age = Integer.parseInt(parts[2]);
                    students.add(student);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while loading student data: " + e.getMessage());
        }
        return students;
    }
    List<Teacher> loadTeachersFromFile() {
        List<Teacher> teachers = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("teachers.csv"));
            String line,name;
            int ID,age;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    name = parts[0];
                    ID = Integer.parseInt(parts[1]);
                    age = Integer.parseInt(parts[2]);
                   Teacher teacher = new Teacher(name,ID,age);
                    teachers.add(teacher);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while loading teacher data: " + e.getMessage());
        }
        return teachers;
    }
           List<Course> loadCoursesFromFile() {
        List<Course> courses = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("courses.csv"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    Course course = new Course(parts[0], parts[1],parts[2]);
                    courses.add(course);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while loading course data: " + e.getMessage());
        }
        return courses;
    }
        void loadData(StudentManager studentManager, TeacherManager teacherManager, CourseManager courseManager) {
        List<Student> students = loadStudentsFromFile();
        System.out.println(students);
        for (Student student : students) {
            System.out.println(student);
            studentManager.addStudent(student);
        }
        List<Teacher> teachers = loadTeachersFromFile();
        for (Teacher teacher : teachers) {
            teacherManager.addTeacher(teacher);
        }
        List<Course> courses = loadCoursesFromFile();
        for (Course course : courses) {
            courseManager.addCourse(course);
        }
    }
}
// Main Class
class Main {
           static void main(String[] args) {
        // Initialize the system
        FileManager fileManager = new FileManager();
        CourseManager courseManager = new CourseManager();
        StudentManager studentManager = new StudentManager();
        TeacherManager teacherManager = new TeacherManager();
        // Load data from files
        fileManager.loadData(studentManager, teacherManager, courseManager);
        // Start the user interface
        Scanner scanner = new Scanner(System.in);
        int choice;
        Course course = null; // Initialize course variable
        Student student = null; // Initialize student variable
        Teacher teacher = null; // Initialize teacher variable
        do {
            System.out.println("Welcome to Student Management System");
            System.out.println("1. Add Course");
            System.out.println("2. Add Student");
            System.out.println("3. Add Teacher");
            System.out.println("4. Enroll Student in Course");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // Add Course
                    System.out.print("Enter course name: ");
                    String courseName = scanner.next();
                    System.out.print("Enter course code: ");
                    String courseCode = scanner.next();
                    // Assume instructor already exists
                    List<Teacher> teacherList = teacherManager.getTeachers();
                    System.out.println("Select a teacher to assign the course:");
                    for (int i = 0; i < teacherList.size(); i++) {
                        System.out.println((i + 1) + ". " + teacherList.get(i).name);
                    }           
                    System.out.print("Enter the name of the teacher: ");
                    scanner.nextLine(); 
                    String teacherName1 = scanner.nextLine().trim(); // Read input and remove leading/trailing whitespace
                    System.out.println("Entered student name: " + teacherName1);                   
                    boolean found = false;
                    for (Teacher s : teacherList) {
                        System.out.println("Comparing with: " + s.name); // Debugging output
                        if (s.name.equalsIgnoreCase(teacherName1)) { // Compare ignoring case
                            teacher = s;
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                       System.out.println("done");
                    } else {
                        System.out.println("Student not found.");
                    }
                     // Create instructor object
                    course = new Course(courseName, courseCode, teacher.getName());
                    courseManager.addCourse(course);
                    addCourse(course); // Write course details to CSV
                    break;
                case 2:
                    // Add Student
                    System.out.print("Enter student name: ");
                    String studentName = scanner.next();
                    System.out.print("Enter student ID: ");
                    int studentID;
                    while (true) {
                        try {
                            studentID = scanner.nextInt();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid ID (integer).");
                            scanner.nextLine(); // Clear the input buffer
                        }
                    }
                    System.out.print("Enter student age: ");
                    int studentAge;
                    while (true) {
                        try {
                            studentAge = scanner.nextInt();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid age (integer).");
                            scanner.nextLine(); // Clear the input buffer
                        }
                    }
                    student = new Student();
                    student.name = studentName;
                    student.ID = studentID;
                    student.age = studentAge;
                    studentManager.addStudent(student);
                    addStudent(student); // Write student details to CSV
                    break;
                case 3:
                    // Add Teacher
                    System.out.print("Enter teacher name: ");
                    String teacherName = scanner.next();
                    System.out.print("Enter teacher ID: ");
                    int teacherID;
                    while (true) {
                        try {
                            teacherID = Integer.parseInt(scanner.next());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid integer for teacher ID.");
                        }
                    }
                    System.out.print("Enter teacher age: ");
                    int teacherAge;
                    while (true) {
                        try {
                            teacherAge = Integer.parseInt(scanner.next());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid integer for teacher age.");
                        }
                    }
                    teacher = new Teacher(teacherName, teacherID, teacherAge);
                    teacherManager.addTeacher(teacher);
                    addTeacher(teacher); // Write teacher details to CSV
                    break;
                    case 4:
                    List<Student> studentList = studentManager.getStudents();
                    List<Course> courseList = courseManager.getCourses();
                    System.out.println("Select a student to enroll in the course:");
                    for (int i = 0; i < studentList.size(); i++) {
                        System.out.println((i + 1) + ". " + studentList.get(i).name);
                    }
                    scanner.nextLine();
                    System.out.print("Enter the name of the student: ");
                    String studentName1 = scanner.nextLine().trim(); // Read input and remove leading/trailing whitespace
                    System.out.println("Entered student name: " + studentName1); // Debugging output, to check the value read from input
                    System.out.println("Select a course to enroll:");
                    for (int i = 0; i < courseList.size(); i++) {
                        System.out.println((i + 1) + ". " + courseList.get(i).getName());
                    }
                    System.out.print("Enter the name of the course: ");
                    String courseName1 = scanner.nextLine().trim(); // Read input and remove leading/trailing whitespace
                    System.out.println("Entered student name: " + courseName1); // Debugging output, to check the value read from input
                    System.out.println(courseList);
                    for (int i = 0; i < courseList.size(); i++) {
                        if(courseList.get(i).getName().equalsIgnoreCase(courseName1))
                        {
                            course = courseList.get(i);
                            
                            System.out.println("hhii");
                            break;
                        }
                        System.out.println("hi");
                    }
                    boolean found1 = false;
                    for (Student s : studentList) {
                        System.out.println("Comparing with: " + s.name); // Debugging output
                        if (s.name.equalsIgnoreCase(studentName1)) { // Compare ignoring case
                            student = s;
                            found1 = true;
                            break;
                        }
                    }
                    if (found1) {
                        if (course != null) {
                            studentManager.enrollInCourse(student, course);
                            System.out.println("Student enrolled successfully.");
                        } else {
                            System.out.println("Please add a course first.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 5:
                    // Exit
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
        scanner.close();
    }
           static void addStudent(Student student) {
        //  To add a new student
        System.out.println("Student added: " + student.name);
        // Add code to save student details to a CSV file
        try {
            FileWriter writer = new FileWriter("students.csv", true);
            writer.append(student.name + "," + student.ID + "," + student.age + "\n");
            writer.close();
            System.out.println("Student details saved to students.csv");
        } catch (IOException e) {
            System.out.println("An error occurred while saving student details: " + e.getMessage());
        }
    }
           static void addTeacher(Teacher teacher) {
        // To add a new teacher
        System.out.println("Teacher added: " + teacher.name);
        // Add code to save teacher details to a CSV file
        try {
            FileWriter writer = new FileWriter("teachers.csv", true);
            writer.append(teacher.name + "," + teacher.ID + "," + teacher.age + "\n");
            writer.close();
            System.out.println("Teacher details saved to teachers.csv");
        } catch (IOException e) {
            System.out.println("An error occurred while saving teacher details: " + e.getMessage());
        }
    }
           static void addCourse(Course course) {
        // To add a new course
        System.out.println("Course added: " + course.getName());
        // Add code to save course details to a CSV file
        try {
            FileWriter writer = new FileWriter("courses.csv", true);
            writer.append(course.getName() + "," + course.getCode() + "," + course.getInstructor() + "\n");
            writer.close();
            System.out.println("Course details saved to courses.csv");
        } catch (IOException e) {
            System.out.println("An error occurred while saving course details: " + e.getMessage());
        }
    }
}