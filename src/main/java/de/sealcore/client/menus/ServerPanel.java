package de.sealcore.client.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ServerPanel extends JPanel
{
    private JLabel tblIp = new JLabel();

    public ServerPanel(MainMenu mainMenu, int index, String ip, String port)
    {
        setMaximumSize(new Dimension(1000, 50));
        setLayout(null);
        setBackground(Color.lightGray);
        setBorder(BorderFactory.createLineBorder(Color.darkGray));

        if(ip.isEmpty() || port.isEmpty())
        {
            tblIp.setText("Enter connection details manually...");
        }
        else
        {
            tblIp.setText("Server #" + index + " (" + ip + ":" + port + ")");
        }
        tblIp.setFont(new Font("Arial", Font.PLAIN, 20));
        tblIp.setBounds(10, 5, 350, 40);
        add(tblIp);

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                mainMenu.connectMenu.preFill(ip, port);
                mainMenu.connectMenu.setVisible(true);
            }
        });
    }
}
