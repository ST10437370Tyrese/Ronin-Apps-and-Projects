/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package tvseriesmanager;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 *
 * @author lab_services_student
 */

public class SeriesTest {
    
    private Series series;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    
    @BeforeEach
    public void setUp() {
        // Redirect System.out to capture output
        System.setOut(new PrintStream(outputStream));
        series = new Series(new Scanner(System.in));
    }
    
    @Test
    public void TestSearchSeries() {
        // Arrange / Expected
        SeriesModel testSeries = new SeriesModel("101", "Test Series", "12", "10");
        series.seriesList.add(testSeries);
        
        // Simulate user input
        String input = "101";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        series.scanner = new Scanner(System.in);
        
        // Act / Actual
        series.SearchSeries();
        
        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("SERIES ID: 101"));
        assertTrue(output.contains("SERIES NAME: Test Series"));
        assertTrue(output.contains("SERIES AGE RESTRICTION: 12"));
        assertTrue(output.contains("SERIES NUMBER OF EPISODES: 10"));   //(Gemini AI,2025)
    }
    
    @Test
    public void TestSearchSeries_SeriesNotFound() {
        // Arrange - Add some test data but search for different ID
        SeriesModel testSeries = new SeriesModel("101", "Test Series", "12", "10");
        series.seriesList.add(testSeries);
        
        // Simulate user input for non-existent ID
        String input = "999";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        series.scanner = new Scanner(System.in);
        
        // Act / Actual
        series.SearchSeries();
        
        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Series with Series Id: 999 was not found!")); //(Gemini AI, 2025)
    }
    
    @Test
    public void TestUpdateSeries() {
        // Arrange/ Expected 
        SeriesModel testSeries = new SeriesModel("101", "Old Name", "12", "10");
        series.seriesList.add(testSeries);
        
        // Simulate user input for update
        String input = "101\nNew Name\n14\n15\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        series.scanner = new Scanner(System.in);
        
        // Act / Actual
        series.UpdateSeries();
        
        // Assert
        SeriesModel updatedSeries = series.seriesList.get(0);
        assertEquals("New Name", updatedSeries.SeriesName);
        assertEquals("14", updatedSeries.SeriesAge);
        assertEquals("15", updatedSeries.SeriesNumberOfEpisodes);       //(Gemini AI, 2025)
    }
    
    @Test
    public void TestDeleteSeries() {
        // Arrange / Expected
        SeriesModel testSeries = new SeriesModel("101", "Test Series", "12", "10");
        series.seriesList.add(testSeries);
        assertEquals(1, series.seriesList.size());
        
        // Simulate user input for delete (yes confirmation)
        String input = "101\ny\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        series.scanner = new Scanner(System.in);
        
        // Act / Actual
        series.DeleteSeries();
        
        // Assert
        assertEquals(0, series.seriesList.size());
        String output = outputStream.toString();
        assertTrue(output.contains("WAS deleted!"));        //(Gemini AI, 2025)
    }
    
    @Test
    public void TestDeleteSeries_SeriesNotFound() {
        // Arrange - Add test data but try to delete different ID
        SeriesModel testSeries = new SeriesModel("101", "Test Series", "12", "10");
        series.seriesList.add(testSeries);
        int originalSize = series.seriesList.size();
        
        // Simulate user input for non-existent ID
        String input = "999\ny\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        series.scanner = new Scanner(System.in);
        
        // Act
        series.DeleteSeries();
        
        // Assert - Series should not be deleted
        assertEquals(originalSize, series.seriesList.size());
        String output = outputStream.toString();
        assertTrue(output.contains("was not found"));       //(Gemini AI, 2025)
    }
    
    @Test
    public void TestSeriesAgeRestriction_AgeValid() {
        // Test valid age restrictions
        assertTrue(isAgeValid("2"));
        assertTrue(isAgeValid("12"));
        assertTrue(isAgeValid("18"));
    }
    
    @Test
    public void TestSeriesAgeRestriction_SeriesAgeInvalid() {
        // Test invalid age restrictions
        assertFalse(isAgeValid("1"));   // Below minimum
        assertFalse(isAgeValid("19"));  // Above maximum
        assertFalse(isAgeValid("0"));   // Below minimum
        assertFalse(isAgeValid("100")); // Way above maximum
        assertFalse(isAgeValid("abc")); // Non-numeric
        assertFalse(isAgeValid("12.5")); // Decimal
        //(Gemini AI, 2025)
    }
    
    // Helper method to test age validation
    private boolean isAgeValid(String age) {
        try {
            int ageValue = Integer.parseInt(age);
            return ageValue >= 2 && ageValue <= 18;
        } catch (NumberFormatException e) {
            return false;   //(Gemini AI, 2025)
        }
    }
}