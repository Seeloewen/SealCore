package de.sealcore.client.menus;

import com.formdev.flatlaf.FlatLightLaf;
import de.sealcore.Main;
import de.sealcore.util.ResourceManager;
import de.sealcore.util.logging.Log;

import javax.swing.*;
import java.awt.*;

import static de.sealcore.util.logging.LogType.RENDERING;

public class AboutMenu extends JFrame
{
    private final int WIDTH = 550;
    private final int HEIGHT = 600;

    private final JLabel lblAbout = new JLabel("SealCore");
    private final JLabel lblVersion = new JLabel("Version " + Main.VERSION + " (" + Main.BUILDDATE + ")");
    private final JLabel lblDeveloper = new JLabel("By Seeloewen & CDLemmi");
    private final JTabbedPane tcAbout = new JTabbedPane();
    private final JScrollPane spThirdParty = new JScrollPane();
    private final JScrollPane spChangelog = new JScrollPane();
    private final JTextArea rtbChangelog = new JTextArea();
    private final JTextArea rtbThirdParty = new JTextArea();

    public AboutMenu()
    {
        super("About");

        setLayout(null);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        try
        {
            UIManager.setLookAndFeel(new FlatLightLaf());
        }
        catch (Exception e)
        {
            Log.error(RENDERING, "Could not set Look and Feel for About Menu: " + e.getMessage());
        }

        setupUi();
    }

    private void setupUi()
    {
        //About label
        lblAbout.setBounds(WIDTH / 2 - 90, 20, 200, 50);
        lblAbout.setFont(new Font("Arial", Font.BOLD, 36));

        //Version label
        lblVersion.setBounds(WIDTH / 2 - 130, 60, 400, 50);
        lblVersion.setFont(new Font("Arial", Font.PLAIN, 22));

        //Developer label
        lblDeveloper.setBounds(WIDTH / 2 - 140, 90, 400, 50);
        lblDeveloper.setFont(new Font("Arial", Font.PLAIN, 22));

        //Tabcontrol
        tcAbout.setBounds(30, 140, 470, 400);
        tcAbout.addTab("Changelog", spChangelog);
        tcAbout.addTab("Third-Party Licenses", spThirdParty);

        //Scrollpanes for tabcontrol
        spChangelog.setBounds(0, 0, tcAbout.getWidth(), tcAbout.getHeight());
        spThirdParty.setBounds(0, 0, tcAbout.getWidth(), tcAbout.getHeight());

        //Textarea for changelog and third-party licenses
        rtbChangelog.setBounds(10, 10, spChangelog.getWidth() - 20, spChangelog.getHeight() - 20);
        rtbChangelog.setLineWrap(true);
        rtbChangelog.setWrapStyleWord(true);
        rtbChangelog.setOpaque(false);
        rtbChangelog.setEditable(false);
        rtbThirdParty.setBounds(10, 10, spChangelog.getWidth() - 20, spChangelog.getHeight() - 20);
        rtbThirdParty.setLineWrap(true);
        rtbThirdParty.setWrapStyleWord(true);
        rtbThirdParty.setOpaque(false);
        rtbThirdParty.setEditable(false);

        //Load the content for changelog and licenses
        try
        {
            rtbChangelog.setText(ResourceManager.getResourceFileAsString("changelog.txt"));
            rtbThirdParty.setText(ResourceManager.getResourceFileAsString("licenses.txt"));
        }
        catch (Exception e)
        {
            Log.error(RENDERING, "Could not get content for About Menu from file: " + e.getMessage());
        }

        spChangelog.setViewportView(rtbChangelog);
        spThirdParty.setViewportView(rtbThirdParty);

        add(lblAbout);
        add(lblVersion);
        add(lblDeveloper);
        add(tcAbout);
    }


}
