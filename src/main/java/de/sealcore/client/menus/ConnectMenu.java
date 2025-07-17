package de.sealcore.client.menus;

import de.sealcore.client.Client;
import de.sealcore.util.TypeParser;

import javax.swing.*;
import java.awt.*;

public class ConnectMenu extends JFrame
{
    private final int WIDTH = 500;
    private final int HEIGHT = 298;

    private final JLabel lblHeader = new JLabel("Connect to server...");
    private final JLabel lblIp = new JLabel("IP:");
    private final JLabel lblPort = new JLabel("Port:");
    private final JLabel lblDisplayName = new JLabel("Name:");
    private final JTextField tbIp = new JTextField();
    private final JTextField tbPort = new JTextField("5000");
    private final JTextField tbDisplayName = new JTextField("");
    private final JButton btnCancel = new JButton("Cancel");
    private final JButton btnConnect = new JButton("Connect");

    public ConnectMenu()
    {
        super("Connect");

        setResizable(false);
        setLayout(null);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        setupUi();
    }

    private void setupUi()
    {
        lblHeader.setBounds(20, 20, 300, 30);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 28));

        lblIp.setBounds(20, 80, 100, 30);
        lblIp.setFont(new Font("Arial", Font.BOLD, 16));

        lblPort.setBounds(20, 118, 100, 30);
        lblPort.setFont(new Font("Arial", Font.BOLD, 16));

        lblDisplayName.setBounds(20, 156, 100, 30);
        lblDisplayName.setFont(new Font("Arial", Font.BOLD, 16));

        tbIp.setBounds(80, 80, 385, 30);
        tbPort.setBounds(80, 118, 385, 30);
        tbDisplayName.setBounds(80, 156, 385, 30);

        btnConnect.setBounds(20, HEIGHT - 100, 215, 40);
        btnConnect.setFont(new Font("Arial", Font.PLAIN, 18));
        btnConnect.addActionListener(e -> connect(tbIp.getText(), tbPort.getText(), tbDisplayName.getText()));
        btnCancel.setBounds(WIDTH - 250, HEIGHT - 100, 215, 40);
        btnCancel.setFont(new Font("Arial", Font.PLAIN, 18));
        btnCancel.addActionListener(e -> setVisible(false));

        add(btnCancel);
        add(btnConnect);
        add(lblHeader);
        add(lblIp);
        add(lblPort);
        add(lblDisplayName);
        add(tbIp);
        add(tbPort);
        add(tbDisplayName);
    }

    public void connect(String ip, String port, String displayName)
    {
        setConnectingState(true);

        //Check if both an ip and a port were entered
        if (ip.isEmpty() || port.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please enter both an ip and a port.", "Error", JOptionPane.ERROR_MESSAGE);
            setConnectingState(false);
            return;
        }

        //Check if the port is a valid double
        if (!TypeParser.isInt(port))
        {
            JOptionPane.showMessageDialog(null, "Please enter a valid port", "Error", JOptionPane.ERROR_MESSAGE);
            setConnectingState(false);
            return;
        }

        Client.start(ip, TypeParser.getInt(port), displayName);
    }

    public void setConnectingState(boolean isConnecting)
    {
        //Toggle the components based on the connection state
        btnConnect.setEnabled(!isConnecting);
        btnConnect.setText(isConnecting ? "Connecting..." : "Connect");
        btnCancel.setEnabled(!isConnecting);
    }
}
