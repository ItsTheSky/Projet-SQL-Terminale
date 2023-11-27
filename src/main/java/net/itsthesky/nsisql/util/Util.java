package net.itsthesky.nsisql.util;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.util.Set;

public final class Util {

    /**
     * Create a new setting section with the input name.
     * <br> A small gray border will be drawn around the section, with the name on the top middle.
     * <br> The input components will be added to the section.
     * @param name The name of the section
     * @param components The components to add to the section
     * @return The created section
     */
    public static JPanel createSection(String name, Component ... components) {
        final JPanel section = new JPanel();
        JPanel currentLine = new JPanel();
        currentLine.setLayout(new BoxLayout(currentLine, BoxLayout.Y_AXIS));
        currentLine.setAlignmentY(Component.TOP_ALIGNMENT);
        section.add(currentLine);

        section.setLayout(new BoxLayout(section, BoxLayout.X_AXIS));
        section.setBorder(BorderFactory.createTitledBorder(name));
        for (Component component : components) {
            if (component == null) {
                currentLine = new JPanel();
                section.add(currentLine);
            } else {
                currentLine.add(component);
            }
        }

        section.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        return section;
    }

    /**
     * Run the input runnable asynchronously.
     */
    public static void runAsync(Runnable runnable) {
        new Thread(runnable).start();
    }

    /**
     * Convert big number to a more readable format.
     * e.g. 1000 -> 1K, 1000000 -> 1M, 1000000000 -> 1B
     * @param value The value to convert
     * @return The converted value
     */
    public static String readable(double value) {
        if (value < 1000)
            return String.valueOf(value);

        if (value < 1000000)
            return String.format("%.2fK", value / 1000);

        if (value < 1000000000)
            return String.format("%.2fM", value / 1000000);

        return String.format("%.2fB", value / 1000000000);
    }

    /**
     * Convert big number to a more readable format, with spaces between each thousand.
     * @param value The value to convert
     * @return The converted value
     */
    public static String spaced(double value) {
        return String.format("%,d", (int) value);
    }

    public static void showMap(String name, double lat, double lon) {
        runAsync(() -> {
            final var mapViewer = new JXMapViewer();
            final var info = new OSMTileFactoryInfo();
            final var tileFactory = new DefaultTileFactory(info);
            mapViewer.setTileFactory(tileFactory);
            tileFactory.setThreadPoolSize(8);
            tileFactory.setUserAgent("NSI SQL Project");

            mapViewer.setPreferredSize(new Dimension(400, 300));
            mapViewer.setZoom(6);
            mapViewer.setAddressLocation(new GeoPosition(lat, lon));

            MouseInputListener mia = new PanMouseInputListener(mapViewer);
            mapViewer.addMouseListener(mia);
            mapViewer.addMouseMotionListener(mia);
            mapViewer.addMouseListener(new CenterMapListener(mapViewer));
            mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
            mapViewer.addKeyListener(new PanKeyListener(mapViewer));

            WaypointPainter<SwingWaypoint> waypointPainter = new WaypointPainter<>();
            waypointPainter.setWaypoints(Set.of(
                    new SwingWaypoint(name, new GeoPosition(lat, lon))
            ));
            mapViewer.setOverlayPainter(waypointPainter);

            // show a new window
            final var frame = new JFrame("Carte de '" + name + "'");
            frame.setSize(500, 500);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.add(mapViewer);
            frame.setVisible(true);
        });
    }

}
