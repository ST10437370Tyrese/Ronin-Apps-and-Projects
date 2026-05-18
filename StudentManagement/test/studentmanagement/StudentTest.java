/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package studentmanagement;

import org.junit.Test;
import static org.junit.Assert.*;


import studentmanagement.Course;
import studentmanagement.Student;
import studentmanagement.StudentManagement;
import org.junit.Test;
import static org.junit.Assert.*;

public class StudentTest {
    
    @Test
    public void testStudentCreation() {
        Student student = new Student("S001", "Test Student", "test@email.com", 3);
        assertEquals("S001", student.getId());
        assertEquals("Test Student", student.getName());
        assertEquals("test@email.com", student.getEmail());
    }
    
    @Test
    public void testAddCourse() {
        Student student = new Student("S001", "Test Student", "test@email.com", 3);
        Course math = new Course("MATH101", "Calculus", 4);
        
        assertTrue(student.addCourse(math, 85));
        assertEquals(1, student.getCourseCount());
    }
    
    @Test
    public void testCalculateAverage() {
        Student student = new Student("S001", "Test Student", "test@email.com", 3);
        Course math = new Course("MATH101", "Calculus", 4);
        Course java = new Course("JAVA101", "Java", 3);
        
        student.addCourse(math, 80);
        student.addCourse(java, 90);
        
        assertEquals(85.0, student.calculateAverage(), 0.01);
    }
    
    @Test
    public void testStudentManager() {
        StudentManagement manager = new StudentManagement(5);
        Student student = new Student("S001", "Test Student", "test@email.com", 3);
        
        assertTrue(manager.addStudent(student));
        assertEquals(1, manager.getStudentCount());
        
        Student found = manager.findStudentById("S001");
        assertNotNull(found);
        assertEquals("Test Student", found.getName());
    }
    
    @Test
    public void testInvalidGrade() {
        Student student = new Student("S001", "Test Student", "test@email.com", 3);
        Course math = new Course("MATH101", "Calculus", 4);
        
        // Should fail for invalid grade
        assertFalse(student.addCourse(math, 150));
        assertEquals(0, student.getCourseCount());
    }
    
    @Test
    public void testGetGradeForCourse() {
        Student student = new Student("S001", "Test Student", "test@email.com", 3);
        Course math = new Course("MATH101", "Calculus", 4);
        
        student.addCourse(math, 85);
        assertEquals(85, student.getGradeForCourse("MATH101"));
        assertEquals(-1, student.getGradeForCourse("NONEXISTENT"));
    }
}