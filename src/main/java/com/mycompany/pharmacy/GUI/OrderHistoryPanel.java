package com.mycompany.pharmacy.GUI;

import com.mycompany.pharmacy.model.Customer;
import com.mycompany.pharmacy.model.Order;

import javax.swing.*;
import java.awt.*;

public class OrderHistoryPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color PRIMARY_COLOR = new Color(129, 152, 218);
    private static final Color HOVER_COLOR = new Color(120, 123, 181);
    private static final Color TEXT_COLOR = new Color(51, 51, 51);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private final Customer customer;
    private final JTextArea historyArea;

    public OrderHistoryPanel(Customer customer) {
        this.customer = customer;
        setLayout(new BorderLayout(20, 20));
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(BACKGROUND_COLOR);
        
        JLabel title = new JLabel("Order History", JLabel.LEFT);
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);
        titlePanel.add(title, BorderLayout.WEST);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton cancelOrderBtn = createButton("Cancel Order", () -> {
            if (customer.getOrderHistory().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "You have no orders to cancel.",
                    "No Orders",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            String orderNumber = JOptionPane.showInputDialog(this,
                "Please enter the order number to cancel:",
                "Cancel Order",
                JOptionPane.QUESTION_MESSAGE);

            if (orderNumber != null && !orderNumber.trim().isEmpty()) {
                boolean success = customer.cancelOrder(orderNumber);
                if (success) {
                    JOptionPane.showMessageDialog(this,
                        "✅ Order #" + orderNumber + " has been canceled successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "❌ Unable to cancel order #" + orderNumber + ".\nIt may not exist or is not cancellable.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
                refreshOrderHistory();
            }
        });

        JButton refreshBtn = createButton("Refresh Orders", this::refreshOrderHistory);
        
        buttonPanel.add(cancelOrderBtn);
        buttonPanel.add(refreshBtn);
        titlePanel.add(buttonPanel, BorderLayout.EAST);

        // History Content Area
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(TEXT_FONT);
        historyArea.setBackground(new Color(245, 245, 245));
        historyArea.setForeground(TEXT_COLOR);
        historyArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        refreshOrderHistory();
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(TEXT_FONT);
        button.setForeground(TEXT_COLOR);
        button.setBackground(PRIMARY_COLOR);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(160, 40));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        button.addActionListener(e -> action.run());

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(HOVER_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    private void refreshOrderHistory() {
        historyArea.setText("");
        historyArea.append("\n=== Order History ===\n\n");
        
        if (customer.getOrderHistory().isEmpty()) {
            historyArea.append("No orders found.\n");
        } else {
            for (Order order : customer.getOrderHistory()) {
                historyArea.append(order.toString() + "\n");
            }
        }
    }
}
