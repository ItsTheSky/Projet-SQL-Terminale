package net.itsthesky.nsisql.frames;

import net.itsthesky.nsisql.model.City;
import net.itsthesky.nsisql.model.Country;
import net.itsthesky.nsisql.model.Entries;

import javax.swing.*;
import java.awt.*;

public class WelcomeFrame extends JFrame {

    public final CityInfoScreen cityInfoScreen;
    public final CountryInfoScreen countryInfoScreen;
    public final CitiesScreen citiesScreen;

    private final JTabbedPane tabbedPane;

    public WelcomeFrame() {
        super("Projet 1 - NSI SQL");

        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        // Make a tab system
        tabbedPane = new JTabbedPane();

        // Add the tabs
        tabbedPane.addTab("Liste des villes", citiesScreen = new CitiesScreen());
        tabbedPane.addTab("Ville", cityInfoScreen = new CityInfoScreen(
                Entries.executeForCity("SELECT * FROM cities WHERE CityName = ?", "Dubai")
        ));
        //tabbedPane.addTab("Liste des pays", new CountriesScreen());
        tabbedPane.addTab("Pays", countryInfoScreen = new CountryInfoScreen(
                Entries.executeForCountry("SELECT * FROM countries WHERE ISO = ?", "AQ")
        ));

        // Add the tab system to the frame
        add(tabbedPane);

        // Make a navbar
        var navbar = new JMenuBar();
        var aboutTab = new JMenu("À Propos");
        navbar.add(aboutTab);
        setJMenuBar(navbar);

        // Setup navigation
        setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void selectCity(City city) {
        cityInfoScreen.updateCity(city);
        tabbedPane.setSelectedIndex(1);
    }

    public void selectCountry(Country country) {
        countryInfoScreen.updateCountry(country);
        tabbedPane.setSelectedIndex(2);
    }

    public void selectCities() {
        tabbedPane.setSelectedIndex(0);
    }
}
