// Author: Ismail Baysal

package org.example.controller;

import org.example.dto.Order;
import org.example.dto.Product;
import org.example.dto.State;
import org.example.service.FlooringDataValidationException;
import org.example.service.FlooringDuplicateOrderException;
import org.example.service.FlooringService;
import org.example.ui.FlooringView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FlooringController {
    private FlooringView view;
    private FlooringService service;

    public FlooringController(FlooringService service,FlooringView view){
        this.service=service;
        this.view =view;
    }

    public void run() throws FlooringDataValidationException {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {

            menuSelection = getMenuSelection();
            switch (menuSelection) {
                case 1:
                    displayOrders();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    exportAllData();
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
            }

        }
        exitMessage();

    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private void exportAllData() {

        view.displayExportDataBanner();

        try {
            service.exportAllOrdersToFile();
            view.displayExportSuccessBanner();
        } catch (Exception e) {
            view.displayErrorMessage(e.getMessage());
        }
    }
    private void removeOrder() {
        view.displayRemoveOrderBanner();
        LocalDate date = view.getDate();
        int orderNumber = view.getOrderNumber();
        view.displayOrder(service.getOrder(orderNumber, date));
        if (service.getOrder(orderNumber, date) != null) {
            if (view.getConfirmation()){ // Order will be deleted after user confirms
                service.removeOrder(orderNumber, date);
                view.displayRemoveSuccessBanner();
            }else {
                view.displayRemoveCanceledBanner();
            }
        }
        else {
            view.displayErrorMessage("No order found at " + date + " and number " + orderNumber);
        }
    }
    private void editOrder() throws FlooringDataValidationException {
        view.displayEditOrderBanner();
        LocalDate date = view.getDate();
        int orderNumber = view.getOrderNumber();
        Order existingOrder = service.getOrder(orderNumber, date);

        if (existingOrder != null){
            view.displayOrder(existingOrder);
            List<Product> productList = service.getProducts();
            List<State> stateList = service.getStates();

            // Ask the user for each field
            String newCustomerName = view.getUpdatedCustomerName();
            String newState = view.getUpdatedState(stateList);
            String newProductType = view.getUpdatedProductType(productList);
            BigDecimal newArea = view.getUpdatedArea();

            // Editing Order
            existingOrder.setCustomerName(newCustomerName);
            existingOrder.getState().setStateName(newState);
            existingOrder.getProduct().setProductType(newProductType);
            existingOrder.setArea(newArea);

            Order calculatedOrder = service.calculateOrder(existingOrder);
            view.displayOrder(calculatedOrder);
            if (view.getConfirmation()){
                service.editOrder(calculatedOrder);

                view.displayEditSuccessBanner();
            }else {
                view.displayEditCanceledBanner();
            }
        }
        else {
            view.displayErrorMessage("No order found at " + date + " and number " + orderNumber);
        }
    }

    private void addOrder() {
        view.displayCreateOrderBanner();
        boolean hasErrors = false;
        do {
            List<Product> productList = service.getProducts();
            List<State> stateList = service.getStates();
            Order currentOrder = view.getNewOrderInfo(productList, stateList);


            int uniqueOrderNumber = service.generateUniqueOrderNumber(currentOrder.getDate());

            try {
                currentOrder.setOrderNumber(uniqueOrderNumber);
                Order calculatedOrder = service.calculateOrder(currentOrder);

                view.displayOrder(calculatedOrder);
                if (view.getConfirmation()){
                    service.createOrder(calculatedOrder);
                    view.displayCreateOrderSuccessBanner();
                }else {
                    view.displayEditCanceledBanner();
                }
                hasErrors = false;
            }
            /*catch (Exception e){
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }
            */
            catch (FlooringDuplicateOrderException | FlooringDataValidationException e) {
                hasErrors = true;
                throw new RuntimeException(e);
            }
        }while(hasErrors);
    }

    private void displayOrders(){

        List<Order> orderList = service.getOrders(view.getDate());
        if (orderList != null) {
            view.displayOrderList(orderList);
        }else {
            view.displayErrorMessage("Error: There is no order on that date.");
        }
    }
    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }
    public List<Product> getAvailableProducts() {
        return service.getProducts();
    }

}
