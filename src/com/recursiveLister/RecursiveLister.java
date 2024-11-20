package com.recursiveLister;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RecursiveLister extends JFrame {
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton startButton;
    private JButton quitButton;
    private JLabel titleLabel;

    public RecursiveLister() {
        // Set up the JFrame
        setTitle("Recursive File Lister");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title Label
        titleLabel = new JLabel("Recursive File Lister", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Text Area and Scroll Pane
        textArea = new JTextArea();
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start");
        quitButton = new JButton("Quit");
        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        startButton.addActionListener(new StartButtonListener());
        quitButton.addActionListener(e -> System.exit(0));

        // Make visible
        setVisible(true);
    }

    private void listFilesRecursively(File directory, StringBuilder output) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                output.append(file.getAbsolutePath()).append("\n");
                if (file.isDirectory()) {
                    listFilesRecursively(file, output);
                }
            }
        }
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int choice = fileChooser.showOpenDialog(RecursiveLister.this);
            if (choice == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                textArea.setText("Listing files for directory: " + selectedDirectory.getAbsolutePath() + "\n\n");
                StringBuilder output = new StringBuilder();
                listFilesRecursively(selectedDirectory, output);
                textArea.append(output.toString());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RecursiveLister::new);
    }
}
