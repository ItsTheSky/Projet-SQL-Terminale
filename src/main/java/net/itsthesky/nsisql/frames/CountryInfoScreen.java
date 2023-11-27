package net.itsthesky.nsisql.frames;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import net.itsthesky.nsisql.Main;
import net.itsthesky.nsisql.model.City;
import net.itsthesky.nsisql.model.Country;
import net.itsthesky.nsisql.model.Entries;
import net.itsthesky.nsisql.util.Util;

import javax.swing.*;
import java.awt.*;

public class CountryInfoScreen extends JPanel {

    private Country country;

    private final JLabel countryNameLabel;

    private final JLabel populationLabel;
    private final JLabel countryCodeLabel;
    private final JLabel citiesAmountLabel;
    private final JLabel currencyNameLabel;
    private final JLabel continentLabel;
    private final JLabel areaLabel;

    public CountryInfoScreen(Country country) {
        super();
        this.country = country;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        final var panel = Box.createVerticalBox();
        panel.setSize(new Dimension(500, 500));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);


        countryNameLabel = new JLabel(country.getCountry());
        countryNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        countryNameLabel.setSize(new Dimension(400, 36));
        countryNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        countryNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(countryNameLabel);

        final var infoFont = new Font("Segoe UI", Font.PLAIN, 18);
        final var countryCodeIcon = IconFontSwing.buildIcon(FontAwesome.FLAG, 18, Color.LIGHT_GRAY);

        populationLabel = new JLabel("Population: " + Util.readable(country.getPopulation()), IconFontSwing.buildIcon(FontAwesome.USERS, 18, Color.LIGHT_GRAY), SwingConstants.LEFT);
        populationLabel.setFont(infoFont);
        countryCodeLabel = new JLabel("Code du pays: " + country.getIso(), countryCodeIcon, SwingConstants.LEFT);
        countryCodeLabel.setFont(infoFont);
        citiesAmountLabel = new JLabel("Nombre de villes: " + Entries.executeForCount("SELECT count(*) FROM cities WHERE CountryCode = ?", country.getIso()), IconFontSwing.buildIcon(FontAwesome.BUILDING, 18, Color.LIGHT_GRAY), SwingConstants.LEFT);
        citiesAmountLabel.setFont(infoFont);
        currencyNameLabel = new JLabel("Monnaie: " + country.getCurrencyName() + " ["+country.getCurrencyCode()+"]", IconFontSwing.buildIcon(FontAwesome.MONEY, 18, Color.LIGHT_GRAY), SwingConstants.LEFT);
        currencyNameLabel.setFont(infoFont);
        continentLabel = new JLabel("Continent: " + country.getContinent(), IconFontSwing.buildIcon(FontAwesome.GLOBE, 18, Color.LIGHT_GRAY), SwingConstants.LEFT);
        continentLabel.setFont(infoFont);
        areaLabel = new JLabel("Superficie: " + Util.spaced(country.getArea()) + " km²", IconFontSwing.buildIcon(FontAwesome.SQUARE, 18, Color.LIGHT_GRAY), SwingConstants.LEFT);
        areaLabel.setFont(infoFont);

        final var grid = new JPanel();
        grid.setLayout(new GridLayout(3, 2));
        grid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        grid.add(populationLabel);
        grid.add(countryCodeLabel);
        grid.add(citiesAmountLabel);
        grid.add(currencyNameLabel);
        grid.add(continentLabel);
        grid.add(areaLabel);

        final var infos = Util.createSection("Informations", grid);
        panel.add(infos);

        final var actions = Util.createSection("Actions",
                /* new JButton("Afficher la carte") {{
                    addActionListener(e -> showMap());
                }}, */
                new JButton("Afficher les villes") {{
                    setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
                    addActionListener(e -> {
                        Main.FRAME.citiesScreen.setCountryCode(CountryInfoScreen.this.country.getIso());
                        Main.FRAME.citiesScreen.setSearch("");
                        Main.FRAME.citiesScreen.updateCities();

                        Main.FRAME.selectCities();
                    });
                }},
                new JButton("Afficher la carte") {{
                    setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
                    addActionListener(e -> showMap());
                }},
                new JButton("Afficher la capitale") {{
                    setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
                    addActionListener(e -> {
                        final City capital = Entries.executeForCity("SELECT * FROM cities, countries WHERE countries.ISO = ? AND idCapital = cities.id", CountryInfoScreen.this.country.getIso());
                        if (capital == null) {
                            JOptionPane.showMessageDialog(Main.FRAME, "Aucune capitale n'a été trouvée pour ce pays !", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Main.FRAME.selectCity(capital);
                    });
                }},
                new JButton("Afficher la capitale") {{
                    setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
                    addActionListener(e -> {
                        final City capital = Entries.executeForCity("SELECT * FROM cities, countries WHERE countries.ISO = ? AND idCapital = cities.id", CountryInfoScreen.this.country.getIso());
                        if (capital == null) {
                            JOptionPane.showMessageDialog(Main.FRAME, "Aucune capitale n'a été trouvée pour ce pays !", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Main.FRAME.selectCity(capital);
                    });
                }}
        );
        panel.add(actions);

        add(panel, BorderLayout.CENTER);
    }

    public void showMap() {
        // show map of the capital
        final var capital = Entries.executeForCity("SELECT * FROM cities, countries WHERE countries.ISO = ? AND idCapital = cities.id", country.getIso());
        if (capital == null) {
            JOptionPane.showMessageDialog(Main.FRAME, "Aucune capitale n'a été trouvée pour ce pays !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Util.showMap(country.getCountry(), capital.getLatitude(), capital.getLongitude());
    }

    public void updateCountry(Country country) {
        this.country = country;

        countryNameLabel.setText(country.getCountry());
        populationLabel.setText("Population: " + Util.readable(country.getPopulation()));
        countryCodeLabel.setText("Code du pays: " + country.getIso());
        citiesAmountLabel.setText("Nombre de villes: " + Entries.executeForCount("SELECT count(*) FROM cities WHERE CountryCode = ?", country.getIso()));
        currencyNameLabel.setText("Monnaie: " + country.getCurrencyName() + " ["+country.getCurrencyCode()+"]");
        continentLabel.setText("Continent: " + country.getContinent());
        areaLabel.setText("Superficie: " + Util.spaced(country.getArea()) + " km²");

    }
}
