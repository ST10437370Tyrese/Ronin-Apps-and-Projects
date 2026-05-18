/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package studentmanagement;


public class StudentManagement {
    private Student[] students;
    private int studentCount;
    
    public StudentManagement(int capacity) {
        students = new Student[capacity];
        studentCount = 0;
    }
    
    // Add student to array
    public boolean addStudent(Student student) {
        if (studentCount < students.length) {
            students[studentCount] = student;
            studentCount++;
            return true;
        }
        return false;
    }
    
    // Find student by ID using linear search
    public Student findStudentById(String id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                return students[i];
            }
        }
        return null;
    }
    
    // Generate class report
    public void generateClassReport() {
        System.out.println("CLASS REPORT");
        System.out.println("============");
        System.out.println("Total Students: " + studentCount);
        System.out.println();
        
        double classAverage = 0;
        int totalCourses = 0;
        
        for (int i = 0; i < studentCount; i++) {
            Student student = students[i];
            System.out.println(student.generateReport());
            classAverage += student.calculateAverage();
            totalCourses += student.getCourseCount();
            System.out.println("----------------------------");
        }
        
        if (studentCount > 0) {
            System.out.println("CLASS STATISTICS:");
            System.out.println("Average Grade: " + String.format("%.2f", classAverage / studentCount));
            System.out.println("Total Courses Registered: " + totalCourses);
        }
    }
    
    // Get all students (for testing)
    public Student[] getAllStudents() {
        return students;
    }
    
    // Get student count
    public int getStudentCount() {
        return studentCount;
    }
}
