package net.itsthesky.nsisql.model;

import java.sql.ResultSet;

public class City {

    private final int id;
    private final String cityName;
    private final double latitude;
    private final double longitude;
    private final String countryCode;
    private final int population;

    public City(ResultSet set) throws Exception {
        this.id = set.getInt("id");
        this.cityName = set.getString("CityName");
        this.latitude = set.getDouble("Latitude");
        this.longitude = set.getDouble("Longitude");
        this.countryCode = set.getString("CountryCode");
        this.population = set.getInt("Population");
    }

    public int getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public int getPopulation() {
        return population;
    }
}
