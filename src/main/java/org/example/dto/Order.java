package org.example.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order {
    private int orderNumber;
    private String customerName;
    private BigDecimal area;
    private Product product;

    private State state;
    private BigDecimal tax;
    private BigDecimal total;
    private LocalDate date;

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public Product getProduct() {
        return product;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public BigDecimal getTotal() {
        return total;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
