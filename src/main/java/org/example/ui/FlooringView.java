package org.example.ui;

import org.example.dto.Order;
import org.example.dto.Product;
import org.example.dto.State;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class FlooringView {
    private UserIO io;
    Scanner sc = new Scanner(System.in);
    public FlooringView(UserIO io) { // constructor
        this.io = io;
    }
    public int printMenuAndGetSelection() {
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");

        return io.readInt("Please select from the above choices.", 1, 6);
    }

    public Order getNewOrderInfo(List<Product> availableProducts, List<State> availableStates) {
        LocalDate orderDate;
        do {
            orderDate = getDate();
            if (orderDate.isBefore(LocalDate.now().plusDays(1))) {
                io.print("Order date must be in the future.");
            }
        } while (orderDate.isBefore(LocalDate.now().plusDays(1)));

        String customerName = io.readString("Please enter customer name: ");
        boolean validState = false;
        String state = "";
        do {
            state = io.readString("Please enter state: ");
            for (State s : availableStates) {
                if (s.getStateName().equalsIgnoreCase(state)) {
                    validState = true;
                    break;
                }
            }
            if(!validState) {
                io.print("We do not service " + state);
            }
        } while(!validState);
        io.print("Product types:\n");
        availableProducts.forEach(product -> io.print(product.getProductType() + " - Cost per square foot: " + product.getCostPerSquareFoot() + ", Labor cost per square foot: " + product.getLaborCostPerSquareFoot()));
        boolean validProduct = false;
        String productType = "";
        do {
            productType = io.readString("Please enter a product type: ");
            for (Product p : availableProducts) {
                if (p.getProductType().equalsIgnoreCase(productType)) {
                    validProduct = true;
                    break;
                }
            }
            if(!validProduct) {
                io.print(productType + " is an invalid product type");
            }
        } while (!validProduct);
        BigDecimal area;
        do {
            area = io.readBigDecimal("Please enter area: ");
            if (area.compareTo(new BigDecimal("100")) < 0) {
                io.print("Minimum order size is 100 sq ft.");
            }
        } while (area.compareTo(new BigDecimal("100")) < 0);

        //State orderState = new State(state);
        //Product orderProductType = new Product(productType);

        Order currentOrder = new Order(customerName, state, productType, area, orderDate);
        /*
        currentOrder.setCustomerName(customerName); 
        currentOrder.setState(orderState);
        currentOrder.setProduct(orderProductType);
        currentOrder.setArea(area);
        currentOrder.setDate(orderDate);
        */
        return currentOrder;
    }

    public void displayCreateOrderBanner() {
        io.print("=== Create Order ===");
    }
    public void displayOrder(Order order) {
        if (order != null) {
            io.print("Order date: " + order.getDate());
            io.print("Order number: " + order.getOrderNumber());
            io.print("Customer Name: " + order.getCustomerName());
            io.print("State: " + order.getState().getStateName());
            io.print("Product type: " + order.getProduct().getProductType());
            io.print("Area: " + order.getArea());
            io.print("Tax: " + order.getTax());
            io.print("Total: " + order.getTotal());
            //io.print("");
        } else {
            io.print("No such order.");
        }
        //io.readString("Please hit enter to continue.");
    }
    public void displayRemoveOrderBanner() {
        io.print("=== Remove Order ===");
    }
    public void displayRemoveResult(Order orderRecord) {
        if(orderRecord != null){
            io.print("Order successfully removed.");
        }else{
            io.print("No such order.");
        }
        //io.readString("Please hit enter to continue.");
    }
    public void displayExitBanner() {
        io.print("Thank you! Hope to see you again!");
    }
    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
    public void displayRemoveSuccessBanner() { //check if it works
        io.print("Student successfully removed.");
    }
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
    public void displayOrderList(List<Order> orderList) {
        io.print("===============");
        for (Order currentOrder : orderList) {
            /*
            String orderInfo = String.format("Order #%s : %s, %s, %s, %s, %s, %s, %s, %s, %s, %s" ,
                    currentOrder.getOrderNumber(),
                    currentOrder.getCustomerName(),
                    currentOrder.getState().getStateName(),
                    currentOrder.getState().getTaxRate(),
                    currentOrder.getProduct().getProductType(),
                    currentOrder.getArea(),
                    currentOrder.getProduct().getCostPerSquareFoot(),
                    currentOrder.getProduct().getLaborCostPerSquareFoot(),
                    currentOrder.getProduct().getMaterialCost(),
                    currentOrder.getTax(),
                    currentOrder.getTotal()


            );
            io.print(orderInfo);
             */
            displayOrder(currentOrder);
            io.print("===============");
        }
        //io.readString("Please hit enter to continue.");
    }
    public LocalDate getDate() {
        return io.readLocalDate("Please enter order date (YYYY-MM-DD): ");
    }
    public int getOrderNumber() {
        return io.readInt("Please enter order number: ");
    }
    public void displayRemoveCanceledBanner() { io.print("=== Remove Order ==="); }
    public void displayCreateOrderSuccessBanner() {
        io.print("Order successfully created.");
    }
    public void displayRemoveOrderSuccessBanner() {
        io.print("Order successfully removed.");
    }
    public void displayExportDataBanner() { io.print("=== All Existing Data ==="); }
    public void displayExportSuccessBanner() {
        io.print("Order successfully exported.");
    }
    public void displayExportFailureBanner() {
        io.print("Order unsuccessfully exported.");
    }
    public void displayEditOrderBanner() { io.print("=== Edit Order ==="); }

    public void displayEditSuccessBanner() {
        io.print("Order successfully edited.");
    }
    public void displayEditCanceledBanner() {
        io.print("Edit order was canceled.");
    }
    public void displayOrderNotFoundBanner() {
        io.print("No order found.");
    }
    public boolean getConfirmation() {
        String result = io.readString("Place the order? (Y/N): ");
        return result.equalsIgnoreCase("Y");
    }
    public String getUpdatedCustomerName(String currentCustomerName) {
        return io.readString("Enter new customer name: ");
    }
    public String getUpdatedState(String currentCustomerName) {
        return io.readString("Enter new state: ");
    }
    public String getUpdatedProductType(String currentCustomerName) {
        return io.readString("Enter new product: ");
    }
    public BigDecimal getUpdatedArea(BigDecimal currentCustomerName) {
        return io.readBigDecimal("Enter new area: ");
    }
}
