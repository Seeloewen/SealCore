package de.sealcore.client.menus;

import com.formdev.flatlaf.FlatLightLaf;
import de.sealcore.client.Client;
import de.sealcore.util.TypeParser;
import de.sealcore.util.logging.Log;

import javax.swing.*;
import java.awt.*;

import static de.sealcore.util.logging.LogType.RENDERING;

public class ConnectMenu extends JFrame
{
    private final MainMenu menu;

    private final int WIDTH = 500;
    private final int HEIGHT = 260;

    private final JLabel lblHeader = new JLabel("Connect to server...");
    private final JLabel lblIp = new JLabel("IP:");
    private final JLabel lblPort = new JLabel("Port:");
    private final JTextField tbIp = new JTextField();
    private final JTextField tbPort = new JTextField("5000");
    private final JButton btnCancel = new JButton("Cancel");
    private final JButton btnConnect = new JButton("Connect");

    public ConnectMenu(MainMenu menu)
    {
        super("Connect");

        this.menu = menu;

        setLayout(null);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        try
        {
            UIManager.setLookAndFeel(new FlatLightLaf());
        }
        catch (Exception e)
        {
            Log.error(RENDERING, "Could not set Look and Feel for Connect Menu: " + e.getMessage());
        }

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

        tbIp.setBounds(70, 80, 395, 30);
        tbPort.setBounds(70, 118, 395, 30);

        btnConnect.setBounds(20, HEIGHT - 100, 215, 40);
        btnConnect.setFont(new Font("Arial", Font.PLAIN, 18));
        btnConnect.addActionListener(e -> connect(tbIp.getText(), tbPort.getText()));
        btnCancel.setBounds(WIDTH - 250, HEIGHT - 100, 215, 40);
        btnCancel.setFont(new Font("Arial", Font.PLAIN, 18));

        add(btnCancel);
        add(btnConnect);
        add(lblHeader);
        add(lblIp);
        add(lblPort);
        add(tbIp);
        add(tbPort);
    }

    public void connect(String ip, String port)
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

        Client.start(ip, TypeParser.getInt(port));
    }

    public void setConnectingState(boolean isConnecting)
    {
        //Toggle the components based on the connection state
        btnConnect.setEnabled(!isConnecting);
        btnConnect.setText(isConnecting ? "Connecting..." : "Connect");
        btnCancel.setEnabled(!isConnecting);
    }
}
