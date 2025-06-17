package de.sealcore.client.menus;

import de.sealcore.Main;
import de.sealcore.util.ResourceManager;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame
{
    private final AboutMenu aboutMenu = new AboutMenu();
    public final ConnectMenu connectMenu = new ConnectMenu();

    private final int WIDTH = 900;
    private final int HEIGHT = 500;

    private final JLayeredPane layeredPane = new JLayeredPane();
    private final JLabel lblHeader = new JLabel("SealCore");
    private final JLabel lblVersion = new JLabel("Version " + Main.VERSION + " (" + Main.BUILDDATE + ")");
    private final JLabel lblCopyright = new JLabel("(c) 2025 Seeloewen & CDLemmi");
    private final JButton btnPlay = new JButton("Play");
    private final JButton btnAbout = new JButton("About");
    private final JButton btnExit = new JButton("Exit");
    private final JLabel lblImage = new JLabel();

    public MainMenu()
    {
        super("SealCore");

        setLayout(null);
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setupUi();
    }

    private void setupUi()
    {
        layeredPane.setSize(WIDTH, HEIGHT);

        //Background Label
        lblImage.setSize(WIDTH, HEIGHT);
        lblImage.setIcon(ResourceManager.createImageIcon("Background.png"));

        //Header Label
        lblHeader.setBounds(280, 20, 500, 100);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 72));
        lblHeader.setForeground(new Color(255, 255, 255));

        //Version Label
        lblVersion.setBounds(20, HEIGHT - 120, 300, 100);
        lblVersion.setFont(new Font("Arial", Font.PLAIN, 22));
        lblVersion.setForeground(new Color(255, 255, 255));

        //Developer Label
        lblCopyright.setBounds(WIDTH - 350, HEIGHT - 120, 350, 100);
        lblCopyright.setFont(new Font("Arial", Font.PLAIN, 22));
        lblCopyright.setForeground(new Color(255, 255, 255));

        //Play Button
        btnPlay.setBounds(260, 150, 350, 50);
        btnPlay.setFont(new Font("Arial", Font.PLAIN, 26));
        btnPlay.addActionListener(e -> connectMenu.setVisible(true));

        //About Button
        btnAbout.setBounds(260, 215, 350, 50);
        btnAbout.setFont(new Font("Arial", Font.PLAIN, 26));
        btnAbout.addActionListener(e -> aboutMenu.setVisible(true));

        //Exit Button
        btnExit.setBounds(260, 280, 350, 50);
        btnExit.setFont(new Font("Arial", Font.PLAIN, 26));
        btnExit.addActionListener(e -> System.exit(0));

        layeredPane.add(lblImage, Integer.valueOf(0));
        layeredPane.add(btnPlay, Integer.valueOf(1));
        layeredPane.add(btnAbout, Integer.valueOf(1));
        layeredPane.add(btnExit, Integer.valueOf(1));
        layeredPane.add(lblHeader, Integer.valueOf(1));
        layeredPane.add(lblVersion, Integer.valueOf(1));
        layeredPane.add(lblCopyright, Integer.valueOf(1));

        add(layeredPane);
    }
}
