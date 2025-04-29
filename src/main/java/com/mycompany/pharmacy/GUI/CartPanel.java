package com.mycompany.pharmacy.GUI;

import com.mycompany.pharmacy.model.Cart;
import com.mycompany.pharmacy.model.CartItem;
import com.mycompany.pharmacy.model.Customer;
import javax.swing.*;
import java.awt.*;

public class CartPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color PRIMARY_COLOR = new Color(129, 152, 218);
    private static final Color HOVER_COLOR = new Color(120, 123, 181);
    private static final Color TEXT_COLOR = new Color(51, 51, 51);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private final Cart cart;
    private final Customer customer;
    private final JTextArea cartArea;

    public CartPanel(Cart cart, Customer customer) {
        this.cart = cart;
        this.customer = customer;
        setLayout(new BorderLayout(20, 20));
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(BACKGROUND_COLOR);
        
        JLabel title = new JLabel("Shopping Cart", JLabel.LEFT);
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);
        titlePanel.add(title, BorderLayout.WEST);

        // Cart Content Area
        cartArea = new JTextArea();
        cartArea.setEditable(false);
        cartArea.setFont(TEXT_FONT);
        cartArea.setBackground(new Color(245, 245, 245));
        cartArea.setForeground(TEXT_COLOR);
        cartArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JScrollPane scrollPane = new JScrollPane(cartArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton placeOrderBtn = createButton("Place Order", () -> {
            if (cart.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Your cart is empty! Add some items before placing an order.",
                    "Empty Cart",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to place this order?\nTotal: $" + String.format("%.2f", cart.calculateTotal()),
                "Confirm Order",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                customer.placeOrder();
                JOptionPane.showMessageDialog(this,
                    "✅ Order placed successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                refreshCart();
            }
        });

        JButton refreshBtn = createButton("Refresh Cart", this::refreshCart);
        
        buttonPanel.add(placeOrderBtn);
        buttonPanel.add(refreshBtn);
        titlePanel.add(buttonPanel, BorderLayout.EAST);

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        refreshCart();
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

    private void refreshCart() {
        cartArea.setText("");
        cartArea.append("\n=== Your Cart ===\n\n");
        
        if (cart.isEmpty()) {
            cartArea.append("Cart is empty.\n");
        } else {
            for (CartItem item : cart.getItems()) {
                cartArea.append(String.format("%s x%d - $%.2f\n", 
                    item.getMedicine().getName(), 
                    item.getQuantity(), 
                    item.getMedicine().getPrice() * item.getQuantity()));
            }
            cartArea.append(String.format("\nTotal: $%.2f\n", cart.calculateTotal()));
        }
    }
}
