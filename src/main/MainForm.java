package main;

import java.awt.*;
import javax.swing.*;

import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import entities.*;
import logic.PlayerMovement;

public class MainForm extends JFrame {
    private Random random;
    private Graphics2D g;

    private EntityPlatform platform; // зеленая платформа
    
    private ImageIcon player = new ImageIcon("res/doodle_guy.png");
    private Image player_icon = player.getImage();
    private Image scaled_icon = player_icon.getScaledInstance(70,70, Image.SCALE_SMOOTH);
    private ImageIcon newIcon = new ImageIcon(scaled_icon);

    private Player playerLogic;

    private JLabel label = new JLabel(newIcon);

    public MainForm() {
        random = new Random();

        setTitle("Doodle Jump");
        setBounds(500, 100, 500, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // setLocationRelativeTo(null);

        this.setResizable(false);
        this.setLayout(null);

        playerLogic = new Player(100, 400, 0, 0.5, 10);

        label.setSize(70,70);
        this.add(label);

        PlayerMovement controller = new PlayerMovement(playerLogic, label);

        this.addMouseMotionListener(controller);

        playerLogic.updateBounds(label);
        
    }

    public static void main(String[] args) {
        MainForm frame = new MainForm();
        frame.setVisible(true);
    }
}
