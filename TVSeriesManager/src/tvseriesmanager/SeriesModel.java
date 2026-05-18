/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tvseriesmanager;


public class SeriesModel {
    public String SeriesId;
    public String SeriesName;
    public String SeriesAge;
    public String SeriesNumberOfEpisodes;
    
    // Default constructor
    public SeriesModel() {
    }
    
    // Constructor
    public SeriesModel(String seriesId, String seriesName, String seriesAge, String seriesNumberOfEpisodes) {
        this.SeriesId = seriesId;
        this.SeriesName = seriesName;
        this.SeriesAge = seriesAge;
        this.SeriesNumberOfEpisodes = seriesNumberOfEpisodes;   //(IIE, 2025)
    }
    
    // Getters / Setters
    public String getSeriesId() {
        return SeriesId;
    }
    
    public void setSeriesId(String seriesId) {
        this.SeriesId = seriesId;
    }
    
    public String getSeriesName() {
        return SeriesName;
    }
    
    public void setSeriesName(String seriesName) {
        this.SeriesName = seriesName;
    }
    
    public String getSeriesAge() {
        return SeriesAge;
    }
    
    public void setSeriesAge(String seriesAge) {
        this.SeriesAge = seriesAge;
    }
    
    public String getSeriesNumberOfEpisodes() {
        return SeriesNumberOfEpisodes;
    }
    
    public void setSeriesNumberOfEpisodes(String seriesNumberOfEpisodes) {
        this.SeriesNumberOfEpisodes = seriesNumberOfEpisodes;   //(IIE,2025)
    }
}