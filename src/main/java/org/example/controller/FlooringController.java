// Author: Ismail Baysal

package org.example.controller;

import org.example.dto.Order;
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

    public void run() {
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
            List<Order> allOrders = service.getAllOrders();
            boolean exportSuccess = service.exportAllOrdersToFile(allOrders);

            if (exportSuccess) {
                view.displayExportSuccessBanner();
            } else {
                view.displayExportFailureBanner();
            }
        } catch (Exception e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void removeOrder() {
        view.displayRemoveOrderBanner();
        LocalDate date = view.getDate();
        int orderNumber = view.getOrderNumber();
        view.displayOrder(service.getOrder(orderNumber, date));
        if (view.getConfirmation()){ // Order will be deleted after user confirms
            service.removeOrder(orderNumber);
            view.displayRemoveSuccessBanner();
        }else {
            view.displayRemoveCanceledBanner();
        }
    }

    private void editOrder() {
        view.displayEditOrderBanner();
        LocalDate date = view.getDate();
        int orderNumber = view.getOrderNumber();
        Order existingOrder = service.getOrder(orderNumber, date);

        if (existingOrder != null){
            view.displayOrder(existingOrder);

            // Ask the user for each field
            String newCustomerName = view.getUpdatedCustomerName(existingOrder.getCustomerName());
            String newState = view.getUpdatedState(existingOrder.getState().getStateName());
            String newProductType = view.getUpdatedProductType(existingOrder.getProduct().getProductType());
            BigDecimal newArea = view.getUpdatedArea(existingOrder.getArea());


            // Create a new Order
            Order updatedOrder = new Order(orderNumber,newCustomerName,newState,newProductType,newArea, date);

            // Calculate the order if state, product type, or area are changed
            if (!existingOrder.getState().equalsIgnoreCase(newState)
                    || !existingOrder.getProductType().equalsIgnoreCase(newProductType)
                    || !existingOrder.getArea().equals(newArea)) {
                updatedOrder = service.calculateOrder(updatedOrder);
            }

            view.displayOrder(updatedOrder);

            if (view.getConfirmation()){
                service.editOrder(updatedOrder);

                view.displayEditSuccessBanner();
            }else {
                view.displayEditCanceledBanner();
            }
            view.displayOrderNotFoundBanner();
        }
    }

    private void addOrder() {
        view.displayCreateOrderBanner();
        boolean hasErrors = false;
        do {
            Order currentOrder = view.getNewOrderInfo();
            try {
                service.createOrder(currentOrder);
                view.displayCreateOrderSuccessBanner();
                hasErrors = false;
            }catch (Exception e){
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }
        }while(hasErrors);
    }

    private void displayOrders(){
    List<Order> orderList = service.getOrders(view.getDate());
    view.displayOrderList(orderList);
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

}
