package net.itsthesky.nsisql.model;

import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class Entries {

    private static Connection connection;

    public static void init() {

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db-project.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // ##############################################

    public static Country executeForCountry(@Language("SQL") String request,
                                            Object... args) {
        try {
            final var statement = connection.prepareStatement(request);
            for (int i = 0; i < args.length; i++)
                statement.setObject(i + 1, args[i]);
            final var result = statement.executeQuery();
            if (!result.next())
                return null;

            return new Country(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static City executeForCity(@Language("SQL") String request,
                                      Object... args) {
        try {
            final var statement = connection.prepareStatement(request);
            for (int i = 0; i < args.length; i++)
                statement.setObject(i + 1, args[i]);
            final var result = statement.executeQuery();
            if (!result.next())
                return null;

            return new City(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ##############################################

    public static List<Country> executeForCountries(@Language("SQL") String request,
                                                    Object... args) {
        try {
            final var statement = connection.prepareStatement(request);
            for (int i = 0; i < args.length; i++)
                statement.setObject(i + 1, args[i]);
            final var result = statement.executeQuery();
            final var countries = new ArrayList<Country>();
            while (result.next())
                countries.add(new Country(result));

            return countries;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<City> executeForCities(@Language("SQL") String request,
                                              Object... args) {
        try {
            final var statement = connection.prepareStatement(request);
            for (int i = 0; i < args.length; i++)
                if (args[i] != null)
                    statement.setObject(i + 1, args[i]);
            final var result = statement.executeQuery();
            final var cities = new ArrayList<City>();
            while (result.next())
                cities.add(new City(result));

            return cities;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int executeForCount(@Language("SQL") String request,
                                      Object... args) {
        try {
            final var statement = connection.prepareStatement(request);
            for (int i = 0; i < args.length; i++)
                statement.setObject(i + 1, args[i]);
            final var result = statement.executeQuery();
            if (!result.next())
                throw new RuntimeException("No count found for request: " + request);

            return result.getInt(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
