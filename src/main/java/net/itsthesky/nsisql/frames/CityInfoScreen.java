package net.itsthesky.nsisql.frames;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import net.itsthesky.nsisql.Main;
import net.itsthesky.nsisql.model.City;
import net.itsthesky.nsisql.model.Entries;
import net.itsthesky.nsisql.util.Util;

import javax.swing.*;
import java.awt.*;

public class CityInfoScreen extends JPanel {

    private City city;

    private final JLabel cityNameLabel;
    private final JLabel populationLabel;
    private final JLabel countryCodeLabel;
    private final JLabel cityIdLabel;

    public CityInfoScreen(City city) {
        super();
        this.city = city;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        final var panel = Box.createVerticalBox();
        panel.setSize(new Dimension(500, 500));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);


        cityNameLabel = new JLabel(city.getCityName());
        cityNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        cityNameLabel.setSize(new Dimension(400, 36));
        cityNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cityNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(cityNameLabel);

        final var infoFont = new Font("Segoe UI", Font.PLAIN, 18);
        final var countryCodeIcon = IconFontSwing.buildIcon(FontAwesome.FLAG, 18, Color.LIGHT_GRAY);

        populationLabel = new JLabel("Population: " + Util.readable(city.getPopulation()), IconFontSwing.buildIcon(FontAwesome.USERS, 18, Color.LIGHT_GRAY), SwingConstants.LEFT);
        populationLabel.setFont(infoFont);
        countryCodeLabel = new JLabel("Code du pays: " + city.getCountryCode(), countryCodeIcon, SwingConstants.LEFT);
        countryCodeLabel.setFont(infoFont);
        cityIdLabel = new JLabel("ID: " + city.getId(), IconFontSwing.buildIcon(FontAwesome.HASHTAG, 18, Color.LIGHT_GRAY), SwingConstants.LEFT);
        cityIdLabel.setFont(infoFont);

        final var infos = Util.createSection("Informations", populationLabel, countryCodeLabel, cityIdLabel);
        panel.add(infos);

        // Actions
        final var actions = Util.createSection("Actions",
                new JButton("Afficher la carte") {{
                    addActionListener(e -> Util.showMap(CityInfoScreen.this.city.getCityName(), CityInfoScreen.this.city.getLatitude(), CityInfoScreen.this.city.getLongitude()));
                    setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
                }},
                new JButton("Afficher le pays") {{
                    addActionListener(e -> {
                        final var country = Entries.executeForCountry("SELECT * FROM countries WHERE ISO = ?", CityInfoScreen.this.city.getCountryCode());
                        if (country == null) {
                            JOptionPane.showMessageDialog(null, "Le pays '" + CityInfoScreen.this.city.getCountryCode() + "' n'existe pas dans la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Main.FRAME.selectCountry(country);
                    });
                    setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
                }},
                new JButton("Afficher le pays") {{
                    addActionListener(e -> {
                        final var country = Entries.executeForCountry("SELECT * FROM countries WHERE ISO = ?", CityInfoScreen.this.city.getCountryCode());
                        if (country == null) {
                            JOptionPane.showMessageDialog(null, "Le pays '" + CityInfoScreen.this.city.getCountryCode() + "' n'existe pas dans la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Main.FRAME.selectCountry(country);
                    });
                    setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
                }}
        );
        panel.add(actions);

        add(panel);
    }

    // ##############################################

    public void updateCity(City city) {
        this.city = city;

        cityNameLabel.setText(city.getCityName());
        populationLabel.setText("Population: " + Util.readable(city.getPopulation()));
        countryCodeLabel.setText("Code du pays: " + city.getCountryCode());
        cityIdLabel.setText("ID: " + city.getId());

        repaint();
    }
}
