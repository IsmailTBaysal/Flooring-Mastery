package org.example.dao;

import org.example.dto.Order;
import org.example.dto.Product;
import org.example.dto.State;

import java.time.LocalDate;
import java.util.List;

public interface FlooringDao {
    Order addOrder(Order order);
    void editOrder(LocalDate date, int orderNum);
    Order getOrder(LocalDate date, int orderNum);
    Order removeOrder(LocalDate date, int orderNum);
    List<Order> getAllOrder();
    
    State getState(String stateName);
    State addState(String stateName, State state);

    Product getProduct(String productName);
    Product addProduct(String productName, Product product);
    List<Product> getAllProduct();
}
