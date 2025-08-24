package com.example.water;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WaterReleaseGUI().createAndShowGUI();
        });
    }
}

class WaterReleaseGUI {

    private JFrame frame;
    private JTextField durationField;
    private JTextField gateField;
    private JTextArea notesArea;
    private JButton releaseButton;

    public void createAndShowGUI() {
        frame = new JFrame("Water Release Notifier");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Panel for inputs
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Duration (minutes):"));
        durationField = new JTextField();
        panel.add(durationField);

        panel.add(new JLabel("Gate:"));
        gateField = new JTextField();
        panel.add(gateField);

        panel.add(new JLabel("Notes:"));
        notesArea = new JTextArea(3, 20);
        JScrollPane scroll = new JScrollPane(notesArea);
        panel.add(scroll);

        releaseButton = new JButton("Release Water & Notify");
        releaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRelease();
            }
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.add(releaseButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void handleRelease() {
        try {
            int duration = Integer.parseInt(durationField.getText());
            String gate = gateField.getText();
            String notes = notesArea.getText();
            LocalDateTime releaseTime = LocalDateTime.now();

            // Insert event
            EventDAO eventDAO = new EventDAO();
            eventDAO.insertRelease(releaseTime, duration, gate, notes);

            // Fetch villagers
            VillagerDAO villagerDAO = new VillagerDAO();
            List<String> phoneNumbers = villagerDAO.getAllPhoneNumbers();

            if (phoneNumbers.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No villagers found in database!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Send SMS
            TwilioService smsService = new TwilioService();
            String message = "Water released from gate " + gate + " for " + duration + " mins. Notes: " + notes;
            smsService.sendBulkSms(phoneNumbers, message);

            JOptionPane.showMessageDialog(frame, "Notifications sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid number for duration.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}