package org.example.dao;

import org.example.dto.Order;
import org.example.dto.Product;
import org.example.dto.State;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlooringDaoImpl implements FlooringDao{
    private Map<LocalDate, ArrayList<Order>> orderMap = new HashMap<>();
    private Map<String, State> stateMap = new HashMap<>();
    private Map<String, Product> productMap = new HashMap<>();
    @Override
    public void addOrder(Order order) {
        if(orderMap.containsKey(order.getDate())) {
            ArrayList<Order> adding = orderMap.get(order.getDate());
            adding.add(order);
            orderMap.replace(order.getDate(), adding);
            return;
        }
        orderMap.put(order.getDate(), new ArrayList<Order>(){{add(order);}});
    }

    @Override
    public void editOrder(LocalDate date, int orderNum) {
        //Depends on how we handle things
    }

    @Override
    public List<Order> getOrders(LocalDate date) {
        return orderMap.get(date);
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNum) {
        ArrayList<Order> checkForRemoval = orderMap.get(date);
        for(Order o: checkForRemoval) {
            if(o.getOrderNumber() == orderNum) {
                checkForRemoval.remove(o);
                orderMap.replace(date, checkForRemoval);
                return o;
            }
        }
        //System.out.println("No order was found");
        return null;
    }

    @Override
    public List<Order> getAllOrder() {
        ArrayList<Order> temp = new ArrayList<>();
        for(ArrayList<Order> a : orderMap.values()) {
            temp.addAll(a);
        }
        return temp;
    }

    @Override
    public State getState(String stateName) {
        return stateMap.get(stateName);
    }

    @Override
    public State addState(String stateName, State state) {
        return stateMap.put(stateName, state);
    }

    @Override
    public Product getProduct(String productName) {
        return productMap.get(productName);
    }

    @Override
    public Product addProduct(String productName, Product product) {
        return productMap.put(productName, product);
    }

    @Override
    public List<Product> getAllProduct() {
        return new ArrayList<>(productMap.values());
    }
}