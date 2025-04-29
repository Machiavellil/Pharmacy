package com.mycompany.pharmacy.GUI;

import com.mycompany.pharmacy.handler.MedicineHandler;
import com.mycompany.pharmacy.model.Customer;
import com.mycompany.pharmacy.model.Medicine;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class BrowseMedicinesPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color PRIMARY_COLOR = new Color(129, 152, 218);
    private static final Color HOVER_BORDER_COLOR = new Color(100, 149, 237);
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private final MedicineHandler medicineHandler;
    private final Customer customer;
    private final PlaceholderTextField searchField;
    private final DefaultListModel<String> suggestionsModel;
    private final JList<String> suggestionsList;
    private final DancingCatPanel dancingCatPanel;

    public BrowseMedicinesPanel(Customer customer, MedicineHandler handler) {
        this.medicineHandler = handler;
        this.customer = customer;
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        // Title
        JLabel title = new JLabel("Browse & Add Medicines", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(PRIMARY_COLOR);

        // Search Field (modern rounded look)
        searchField = new PlaceholderTextField("Search for medicines...");
        searchField.setFont(TEXT_FONT);
        searchField.setPreferredSize(new Dimension(300, 38));
        searchField.setMaximumSize(new Dimension(300, 38));
        searchField.setBackground(new Color(245, 245, 245));
        searchField.setForeground(TEXT_COLOR);
        searchField.setHorizontalAlignment(SwingConstants.LEFT);
        searchField.setBorder(createRoundedBorder(Color.LIGHT_GRAY));

        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                searchField.setBorder(createRoundedBorder(HOVER_BORDER_COLOR));
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                searchField.setBorder(createRoundedBorder(Color.LIGHT_GRAY));
            }
        });

        // Suggestions
        suggestionsModel = new DefaultListModel<>();
        suggestionsList = new JList<>(suggestionsModel);
        suggestionsList.setFont(TEXT_FONT);
        suggestionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane suggestionsScroll = new JScrollPane(suggestionsList);

        // Dancing Cat Panel
        dancingCatPanel = new DancingCatPanel();
        dancingCatPanel.setPreferredSize(new Dimension(200, 200));

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchField.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(title);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(searchField);

        // Center Panel with cat and suggestions
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.add(suggestionsScroll, BorderLayout.CENTER);
        centerPanel.add(dancingCatPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // Auto-complete logic
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateSuggestions(); }
            public void removeUpdate(DocumentEvent e) { updateSuggestions(); }
            public void changedUpdate(DocumentEvent e) { updateSuggestions(); }
        });

        suggestionsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    String selected = suggestionsList.getSelectedValue();
                    if (selected != null) addToCart(selected);
                }
            }
        });
    }

    private void updateSuggestions() {
        String input = searchField.getText().toLowerCase();
        suggestionsModel.clear();

        if (input.isBlank()) return;

        List<Medicine> matches = medicineHandler.getAllMedicines()
                .stream()
                .filter(m -> m.getName().toLowerCase().startsWith(input))
                .collect(Collectors.toList());

        for (Medicine med : matches) {
            suggestionsModel.addElement(med.getName() + " - $" + med.getPrice() +
                    (med.isPrescriptionRequired() ? " (Prescription Required)" : ""));
        }
    }

    private void addToCart(String selectedText) {
        String medicineName = selectedText.split(" - ")[0];
        Medicine selectedMedicine = medicineHandler.getAllMedicines()
                .stream()
                .filter(m -> m.getName().equalsIgnoreCase(medicineName))
                .findFirst()
                .orElse(null);

        if (selectedMedicine == null) {
            JOptionPane.showMessageDialog(this, "Medicine not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedMedicine.isPrescriptionRequired() && !customer.hasValidPrescriptionFor(selectedMedicine)) {
            JOptionPane.showMessageDialog(this, "Prescription required for " + selectedMedicine.getName(), "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String quantityStr = JOptionPane.showInputDialog(this, "Enter quantity:", "Add to Cart", JOptionPane.PLAIN_MESSAGE);
        if (quantityStr != null && !quantityStr.isBlank()) {
            try {
                int quantity = Integer.parseInt(quantityStr);
                customer.getCart().addItem(selectedMedicine, quantity);
                JOptionPane.showMessageDialog(this, "✅ Added to cart!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid quantity!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private Border createRoundedBorder(Color color) {
        return new CompoundBorder(
                new LineBorder(color, 1, true),
                new EmptyBorder(8, 16, 8, 16)
        );
    }

    private class DancingCatPanel extends JPanel {
        private int frame = 0;
        private Timer animationTimer;

        public DancingCatPanel() {
            setBackground(BACKGROUND_COLOR);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Start animation timer
            animationTimer = new Timer(100, e -> {
                frame = (frame + 1) % 4;
                repaint();
            });
            animationTimer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int centerX = width / 2;
            int centerY = height / 2;

            // Draw cat body
            g2d.setColor(new Color(180, 180, 180));  // Darker gray for body
            g2d.fillOval(centerX - 30, centerY - 20, 60, 40);

            // Draw cat head
            g2d.setColor(new Color(220, 220, 220));  // Lighter gray for head
            g2d.fillOval(centerX - 20, centerY - 40, 40, 40);

            // Draw ears (same color as head)
            g2d.fillPolygon(
                new int[]{centerX - 15, centerX - 5, centerX - 20},
                new int[]{centerY - 40, centerY - 60, centerY - 50},
                3
            );
            g2d.fillPolygon(
                new int[]{centerX + 15, centerX + 5, centerX + 20},
                new int[]{centerY - 40, centerY - 60, centerY - 50},
                3
            );

            // Draw eyes
            g2d.setColor(Color.BLACK);
            g2d.fillOval(centerX - 10, centerY - 30, 8, 8);
            g2d.fillOval(centerX + 2, centerY - 30, 8, 8);

            // Draw whiskers
            g2d.drawLine(centerX - 20, centerY - 20, centerX - 30, centerY - 15);
            g2d.drawLine(centerX - 20, centerY - 15, centerX - 30, centerY - 10);
            g2d.drawLine(centerX + 20, centerY - 20, centerX + 30, centerY - 15);
            g2d.drawLine(centerX + 20, centerY - 15, centerX + 30, centerY - 10);

            // Draw dancing legs based on frame
            int legY = centerY + 20;
            switch (frame) {
                case 0:
                    // First dance position
                    g2d.drawLine(centerX - 20, legY, centerX - 30, legY + 20);
                    g2d.drawLine(centerX - 10, legY, centerX - 20, legY + 20);
                    g2d.drawLine(centerX + 10, legY, centerX + 20, legY + 20);
                    g2d.drawLine(centerX + 20, legY, centerX + 30, legY + 20);
                    break;
                case 1:
                    // Second dance position
                    g2d.drawLine(centerX - 20, legY, centerX - 25, legY + 20);
                    g2d.drawLine(centerX - 10, legY, centerX - 15, legY + 20);
                    g2d.drawLine(centerX + 10, legY, centerX + 15, legY + 20);
                    g2d.drawLine(centerX + 20, legY, centerX + 25, legY + 20);
                    break;
                case 2:
                    // Third dance position
                    g2d.drawLine(centerX - 20, legY, centerX - 20, legY + 20);
                    g2d.drawLine(centerX - 10, legY, centerX - 10, legY + 20);
                    g2d.drawLine(centerX + 10, legY, centerX + 10, legY + 20);
                    g2d.drawLine(centerX + 20, legY, centerX + 20, legY + 20);
                    break;
                case 3:
                    // Fourth dance position
                    g2d.drawLine(centerX - 20, legY, centerX - 15, legY + 20);
                    g2d.drawLine(centerX - 10, legY, centerX - 5, legY + 20);
                    g2d.drawLine(centerX + 10, legY, centerX + 5, legY + 20);
                    g2d.drawLine(centerX + 20, legY, centerX + 15, legY + 20);
                    break;
            }
        }
    }
}

// Modern placeholder field with custom paint
class PlaceholderTextField extends JTextField {
    private final String placeholder;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty() && !(FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.GRAY);
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            g2.drawString(placeholder, 16, getHeight() / 2 + getFont().getSize() / 2 - 4);
            g2.dispose();
        }
    }
}
