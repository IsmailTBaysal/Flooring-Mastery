package org.example.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Order {
    private int orderNumber;
    private String customerName;
    private BigDecimal area;
    private Product product;
    private State state;
    private BigDecimal tax;
    private BigDecimal total;
    private LocalDate date;
    public Order() {

    }
    public Order(int orderNumber, String customerName, String stateName, BigDecimal taxRate, String productType,
                 BigDecimal area, BigDecimal costPerSquareFoot , BigDecimal laborCostPerSquareFoot ,
                 BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax, BigDecimal total, LocalDate date) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.area = area;
        this.tax = tax;
        this.total = total;
        this.date = date;
        state = new State(stateName, taxRate);
        product = new Product(productType, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost);

    }
    public Order(String newCustomerName, String newState, String newProductType, BigDecimal newArea, LocalDate date) {
        this.date=date;
        this.customerName = newCustomerName;
        this.area = newArea;

        state = new State(newState);
        product = new Product(newProductType);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return getOrderNumber() == order.getOrderNumber() && Objects.equals(getCustomerName(), order.getCustomerName()) && Objects.equals(getArea(), order.getArea()) && Objects.equals(getProduct(), order.getProduct()) && Objects.equals(getState(), order.getState()) && Objects.equals(getTax(), order.getTax()) && Objects.equals(getTotal(), order.getTotal()) && Objects.equals(getDate(), order.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderNumber(), getCustomerName(), getArea(), getProduct(), getState(), getTax(), getTotal(), getDate());
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", customerName='" + customerName + '\'' +
                ", area=" + area +
                ", product=" + product +
                ", state=" + state +
                ", tax=" + tax +
                ", total=" + total +
                ", date=" + date +
                '}';
    }
}
