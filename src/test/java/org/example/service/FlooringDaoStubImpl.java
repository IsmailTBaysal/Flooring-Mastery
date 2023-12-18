package org.example.service;

import org.example.dao.FlooringDao;
import org.example.dao.FlooringDaoImpl;
import org.example.dto.Order;
import org.example.dto.Product;
import org.example.dto.State;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlooringDaoStubImpl implements FlooringDao {

    public Order onlyOrder;
    public ArrayList<State> onlyState;
    public ArrayList<Product>  onlyProduct;

    public FlooringDaoStubImpl() {
        onlyOrder = new Order("1", "Washington", "Wood", new BigDecimal(1), LocalDate.now());
        onlyOrder.setOrderNumber(1);
        onlyState.add(new State("Washington", new BigDecimal(1)));
        onlyState.add(new State("Florida", new BigDecimal(1)));
        onlyProduct.add(new Product("Wood",
                new BigDecimal(1),
                new BigDecimal(1),
                new BigDecimal(1),
                new BigDecimal(1)));
        onlyProduct.add(new Product("Rock",
                new BigDecimal(1),
                new BigDecimal(1),
                new BigDecimal(1),
                new BigDecimal(1)));

    }
    public FlooringDaoStubImpl(Order floridaOrder) {
        customValues(floridaOrder);
    }
   /* public FlooringDaoStubImpl(Order order) {
        onlyOrder = order;
    }
    */

    public void customValues(Order customOrder) { //create a new
        onlyOrder = customOrder;
        onlyState = new State(customOrder.getState().getStateName(), new BigDecimal(1));
        onlyProduct = new Product(
                customOrder.getProduct().getProductType(),
                new BigDecimal(1),
                new BigDecimal(1),
                new BigDecimal(1),
                new BigDecimal(1)
        );
    }
    @Override
    public Order addOrder(Order order) {
        return onlyOrder = order;
    }

    @Override
    public void editOrder(LocalDate date, Order order) {
        if(date == onlyOrder.getDate() && order.getOrderNumber() == onlyOrder.getOrderNumber()) {
            onlyOrder = order;
        }

    }

    @Override
    public List<Order> getOrders(LocalDate date) {
        List<Order> orderList = new ArrayList<>();
        if (date.isEqual(onlyOrder.getDate())) {
            orderList.add(onlyOrder);
        }
        return orderList;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNum) {
        if(date == onlyOrder.getDate() && orderNum == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        }
        else {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrder() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(onlyOrder);
        return orderList;
    }

    @Override
    public State getState(String stateName) {
        for (State state : onlyState) {
            if (state.getStateName().equalsIgnoreCase(stateName)) {
                return state;
            }
        }
        return null;
    }

    @Override
    public State addState(String stateName, State state) {
        return null;
    }

    @Override
    public Product getProduct(String productName) {
        for (Product product : onlyProduct) {
            if (product.getProductType().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public Product addProduct(String productName, Product product) {
        return null;
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> productList = new ArrayList<>();
        productList.add(onlyProduct);
        return productList;
    }

    @Override
    public void exportAll() {

    }
}
