package net.itsthesky.nsisql.frames;

import net.itsthesky.nsisql.Main;
import net.itsthesky.nsisql.model.City;
import net.itsthesky.nsisql.model.Entries;
import org.intellij.lang.annotations.Language;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CitiesScreen extends JPanel {

    private String search = "";
    private String countryCode = "";

    private final JList<City> cities;
    private final JTextField searchBox;
    private final JTextField countryCodeBox;

    public CitiesScreen() {
        super();
        setSize(500, 500);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(true);
        cities = new JList<>();

        final JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 2));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Filtrer"));
        add(searchPanel);

        // Nom de la ville
        final var cityNameLabel = new JLabel("Nom de la ville");
        searchPanel.add(cityNameLabel);
        searchBox = new JTextField();
        searchBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) searchBox.getPreferredSize().getHeight()));
        searchBox.addActionListener(e -> {
            search = searchBox.getText();
            updateCities();
        });
        searchPanel.add(searchBox);

        // Code du pays
        final var countryCodeLabel = new JLabel("Code du pays");
        searchPanel.add(countryCodeLabel);
        countryCodeBox = new JTextField();
        countryCodeBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) countryCodeBox.getPreferredSize().getHeight()));
        countryCodeBox.addActionListener(e -> {
            countryCode = countryCodeBox.getText();
            updateCities();
        });
        searchPanel.add(countryCodeBox);

        cities.setListData(Entries.executeForCities("SELECT * FROM cities").toArray(new City[0]));
        cities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cities.setLayoutOrientation(JList.VERTICAL);
        cities.setVisibleRowCount(-1);
        cities.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting())
                return;
            final var selected = cities.getSelectedValue();
            if (selected == null)
                return;
            Main.FRAME.selectCity(selected);
        });
        cities.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                final var component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                final var city = (City) value;
                setText("["+ city.getCountryCode() +"] " + city.getCityName());
                return component;
            }
        });

        add(new JScrollPane(cities));
    }

    public void updateCities() {
        final boolean hasSearch = !Objects.equals(search, "");
        final boolean hasCountryCode = !Objects.equals(countryCode, "");

        @Language("SQL") String baseRequest = "SELECT * FROM cities";

        if (hasSearch || hasCountryCode)
            baseRequest += " WHERE";

        if (hasSearch)
            baseRequest += " CityName LIKE ?";

        if (hasSearch && hasCountryCode)
            baseRequest += " AND";

        if (hasCountryCode)
            baseRequest += " CountryCode LIKE ?";

        final Object[] params = new String[hasSearch && hasCountryCode ? 2 : 1];
        int i = 0;
        if (hasSearch)
            params[i++] = "%" + search + "%";
        if (hasCountryCode)
            params[i] = "%" + countryCode + "%";

        cities.setListData(Entries.executeForCities(baseRequest, params).toArray(new City[0]));
    }

    public void setSearch(String search) {
        this.search = search;
        searchBox.setText(search);
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        countryCodeBox.setText(countryCode);
    }
}
