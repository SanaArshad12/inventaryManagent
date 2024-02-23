import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
class InventoryManagementSystem extends JFrame {
    private ArrayList<String> inventory = new ArrayList<>();
    private JTextField itemField;
    private JTextArea displayArea;
    private final String DATA_FILE = "savaData.txt";

    public InventoryManagementSystem() {
        loadInventoryFromFile();
        createUI();
    }

    private void createUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create panels
        JPanel topPanel = new JPanel();
        JPanel centerPanel = new JPanel();

        // Create buttons
        JButton enterDataButton = new JButton("Enter Data");
        JButton showDataButton = new JButton("Show Data");
        JButton saveDataButton = new JButton("Save Data");
        JButton updateDataButton = new JButton("Update Data");
        JButton deleteDataButton = new JButton("Delete Data"); // Delete data button

        // Create text field and area
        itemField = new JTextField(20);
        displayArea = new JTextArea(10, 50);
        displayArea.setEditable(false);

        // Add components to panels
        topPanel.add(enterDataButton);
        topPanel.add(showDataButton);
        topPanel.add(saveDataButton);
        topPanel.add(updateDataButton);
        topPanel.add(deleteDataButton); // Add delete button to top panel

        centerPanel.add(new JScrollPane(displayArea));

        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // Action listeners
        enterDataButton.addActionListener(e -> onEnterData());
        showDataButton.addActionListener(e -> onShowData());
        saveDataButton.addActionListener(e -> onSaveData());
        updateDataButton.addActionListener(e -> onUpdateData());
        deleteDataButton.addActionListener(e -> onDeleteData()); // Add action listener for delete button
    }

    private void onEnterData() {
        String item = JOptionPane.showInputDialog(this, "Enter item:");
        if (item != null && !item.isEmpty()) {
            inventory.add(item);
            JOptionPane.showMessageDialog(this, "Item added.");
        }
    }

    private void onShowData() {
        displayArea.setText("");
        for (String item : inventory) {
            displayArea.append(item + "\n");
        }
    }

    private void onSaveData() {
        try (FileWriter writer = new FileWriter(DATA_FILE, false)) {
            for (String item : inventory) {
                writer.write(item + "\n");
            }
            JOptionPane.showMessageDialog(this, "Data saved to file.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save data: " + e.getMessage());
        }
    }
    private void loadInventoryFromFile() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    inventory.add(scanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "File not found: " + e.getMessage());
            }
        }
    }

    private void onUpdateData() {
        String currentData = JOptionPane.showInputDialog(this, "Enter old item:");
        if (currentData != null && inventory.contains(currentData)) {
            String newData = JOptionPane.showInputDialog(this, "Enter new item for " + currentData + ":");
            if (newData != null && !newData.isEmpty()) {
                inventory.set(inventory.indexOf(currentData), newData);
                JOptionPane.showMessageDialog(this, "Item updated.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Item not found.");
        }
    }

    private void onDeleteData() {
        String itemToDelete = JOptionPane.showInputDialog(this, "Enter item to delete:");
        if (itemToDelete != null && inventory.contains(itemToDelete)) {
            inventory.remove(itemToDelete);
            JOptionPane.showMessageDialog(this, "Item deleted.");
        } else {
            JOptionPane.showMessageDialog(this, "Item not found.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InventoryManagementSystem frame = new InventoryManagementSystem();
            frame.setVisible(true);
        });
    }
}
