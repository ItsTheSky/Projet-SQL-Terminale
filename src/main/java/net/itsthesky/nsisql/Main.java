package net.itsthesky.nsisql;

import com.formdev.flatlaf.FlatDarculaLaf;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import net.itsthesky.nsisql.frames.WelcomeFrame;
import net.itsthesky.nsisql.model.Entries;

import javax.swing.*;

public class Main {

    public static WelcomeFrame FRAME;

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        Entries.init();
        IconFontSwing.register(FontAwesome.getIconFont());

        FlatDarculaLaf.setup();
        UIManager.setLookAndFeel(new FlatDarculaLaf());

        FRAME = new WelcomeFrame();

        FRAME.setVisible(true);
        FRAME.setLocationRelativeTo(null);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.repaint();
        FRAME.revalidate();
    }

}