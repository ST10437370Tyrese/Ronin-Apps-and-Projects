/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagement;

public class Student extends Person {
    private int[] grades;
    private Course[] courses;
    private int courseCount;
    
    public Student(String id, String name, String email, int maxCourses) {
        super(id, name, email); // Calling parent constructor
        this.grades = new int[maxCourses];
        this.courses = new Course[maxCourses];
        this.courseCount = 0;
    }
    
    // Add course with grade
    public boolean addCourse(Course course, int grade) {
        if (courseCount < courses.length && grade >= 0 && grade <= 100) {
            courses[courseCount] = course;
            grades[courseCount] = grade;
            courseCount++;
            return true;
        }
        return false;
    }
    
    // Calculate average grade
    public double calculateAverage() {
        if (courseCount == 0) return 0.0;
        
        int total = 0;
        for (int i = 0; i < courseCount; i++) {
            total += grades[i];
        }
        return (double) total / courseCount;
    }
    
    // Get grade for specific course
    public int getGradeForCourse(String courseCode) {
        for (int i = 0; i < courseCount; i++) {
            if (courses[i].getCourseCode().equals(courseCode)) {
                return grades[i];
            }
        }
        return -1; // Not found
    }
    
    // Generate report
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("STUDENT REPORT\n");
        report.append("==============\n");
        report.append(super.toString()).append("\n");
        report.append("Average Grade: ").append(String.format("%.2f", calculateAverage())).append("\n\n");
        report.append("COURSE DETAILS:\n");
        
        for (int i = 0; i < courseCount; i++) {
            report.append("- ").append(courses[i].getCourseName())
                  .append(" (").append(courses[i].getCourseCode())
                  .append("): ").append(grades[i]).append("\n");
        }
        
        return report.toString();
    }
    
    // Get course count
    public int getCourseCount() {
        return courseCount;
    }
}