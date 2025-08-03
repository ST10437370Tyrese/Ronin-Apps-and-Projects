
package grocerybillcalc;

import javax.swing.JOptionPane;

public class GroceryBillCalc {
    public static void main(String[] args) {
        // Declare variables
        String item1, item2, item3;
        double price1, price2, price3;
        int quantity1, quantity2, quantity3;

        // Get user input for the first item
        item1 = JOptionPane.showInputDialog("Enter the name of the first item:");
        price1 = Double.parseDouble(JOptionPane.showInputDialog("Enter the price of " + item1 + ":"));
        quantity1 = Integer.parseInt(JOptionPane.showInputDialog("Enter the quantity of " + item1 + ":"));

        // Get user input for the second item
        item2 = JOptionPane.showInputDialog("Enter the name of the second item:");
        price2 = Double.parseDouble(JOptionPane.showInputDialog("Enter the price of " + item2 + ":"));
        quantity2 = Integer.parseInt(JOptionPane.showInputDialog("Enter the quantity of " + item2 + ":"));

        // Get user input for the third item
        item3 = JOptionPane.showInputDialog("Enter the name of the third item:");
        price3 = Double.parseDouble(JOptionPane.showInputDialog("Enter the price of " + item3 + ":"));
        quantity3 = Integer.parseInt(JOptionPane.showInputDialog("Enter the quantity of " + item3 + ":"));

        // Calculate the total bill
        double totalBill = (price1 * quantity1) + (price2 * quantity2) + (price3 * quantity3);

        // Display the final bill amount using JOptionPane
        String billSummary = "===== Grocery Bill Summary =====\n" +
                item1 + " (" + quantity1 + " x R" + price1 + ") = R" + (price1 * quantity1) + "\n" +
                item2 + " (" + quantity2 + " x R" + price2 + ") = R" + (price2 * quantity2) + "\n" +
                item3 + " (" + quantity3 + " x R" + price3 + ") = R" + (price3 * quantity3) + "\n" +
                "--------------------------------\n" +
                String.format("Total Amount Payable: R%.2f", totalBill);

        JOptionPane.showMessageDialog(null, billSummary, "Grocery Bill", JOptionPane.INFORMATION_MESSAGE);
    }
}
