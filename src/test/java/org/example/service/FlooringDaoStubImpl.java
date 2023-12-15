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
    public State onlyState;
    public Product onlyProduct;

    public FlooringDaoStubImpl() {
        onlyOrder = new Order("1", "Washinton", "Wood", new BigDecimal(1), LocalDate.now());
        onlyState = new State("Washinton", new BigDecimal(1));
        onlyProduct = new Product("Wood", new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1));
    }

    public FlooringDaoStubImpl(Order order) {
        onlyOrder = order;
    }
    @Override
    public Order addOrder(Order order) {
        if(order.getOrderNumber() == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        }
        else {
            return null;
        }
    }

    @Override
    public Order editOrder(LocalDate date, Order order) {
        if(date == onlyOrder.getDate()) {
            Order returnedOrder = onlyOrder;
            onlyOrder = order;
            return returnedOrder;
        }
        else {
            return null;
        }
    }

    @Override
    public List<Order> getOrders(LocalDate date) {
        List<Order> orderList = new ArrayList<>();
        orderList.add(onlyOrder);
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
        if(stateName.equalsIgnoreCase(onlyState.getStateName())) {
            return onlyState;
        }
        else {
            return null;
        }
    }

    @Override
    public State addState(String stateName, State state) {
        return null;
    }

    @Override
    public Product getProduct(String productName) {
        if(productName.equalsIgnoreCase(onlyProduct.getProductType())) {
            return onlyProduct;
        }
        else {
            return null;
        }
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
}
