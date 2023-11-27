package net.itsthesky.nsisql.model;

import java.sql.ResultSet;

public class Country {

    private final String iso;
    private final String country;
    private final int idCapital;
    private final double area;
    private final int population;
    private final String continent;
    private final String currencyCode;
    private final String currencyName;

    public Country(ResultSet set) throws Exception {
        this.iso = set.getString("ISO");
        this.country = set.getString("Country");
        this.idCapital = set.getInt("idCapital");
        this.area = set.getDouble("Area");
        this.population = set.getInt("Population");
        this.continent = set.getString("Continent");
        this.currencyCode = set.getString("CurrencyCode");
        this.currencyName = set.getString("CurrencyName");
    }

    public String getIso() {
        return iso;
    }

    public String getCountry() {
        return country;
    }

    public int getIdCapital() {
        return idCapital;
    }

    public double getArea() {
        return area;
    }

    public int getPopulation() {
        return population;
    }

    public String getContinent() {
        return continent;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }
}
