package de.sealcore.client.menus;

import de.sealcore.networking.NetworkHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ServerBrowserMenu extends JFrame
{
    public String url; //This is the ip that the server browser file is hosted on
    public MainMenu mainMenu;

    private final JLabel lblHeader = new JLabel("Select a server...");

    private final JScrollPane spServers = new JScrollPane();
    private final JPanel lsServers = new JPanel();
    private final JButton btnCancel = new JButton("Cancel");
    private final JButton btnRefresh = new JButton("Refresh");
    private final JButton btnConnect =  new JButton("Connect");

    private final int WIDTH = 500;
    private final int HEIGHT = 600;

    public ServerBrowserMenu(MainMenu mainMenu)
    {
        super("Server Browser");
        this.mainMenu = mainMenu;

        setLayout(null);
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        setupUi();
    }

    private void setupUi()
    {
        lblHeader.setBounds(20, 20, 300, 30);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 28));

        lsServers.setLayout(new BoxLayout(lsServers, BoxLayout.Y_AXIS));

        spServers.setBounds(20, 60, 440, 430);
        spServers.setViewportView(lsServers);

        btnCancel.setBounds(20, HEIGHT - 95, 215, 40);
        btnCancel.setFont(new Font("Arial", Font.BOLD, 16));
        btnCancel.addActionListener(e -> dispose());

        btnRefresh.setBounds(245, HEIGHT - 95, 215, 40);
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 16));
        btnRefresh.addActionListener(this::refresh);

        add(lblHeader);
        add(spServers);
        add(btnCancel);
        add(btnRefresh);
        add(btnConnect);
    }

    public void refresh(ActionEvent e)
    {
        lsServers.removeAll();
        ArrayList<String> servers = NetworkHandler.getServerList(url);

        int i = 1;
        for(String s : servers)
        {
            String[] split =  s.split(":");
            ServerPanel p = new ServerPanel(mainMenu, i, split[0], split[1]);
            lsServers.add(p);
            i++;
        }
        ServerPanel p = new ServerPanel(mainMenu, i, "", "");
        lsServers.add(p);

        lsServers.revalidate();
        lsServers.repaint();
    }
}
