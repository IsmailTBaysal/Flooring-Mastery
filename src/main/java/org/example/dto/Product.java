// Author: Ismail Baysal

package org.example.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private String ProductType;
    private BigDecimal CostPerSquareFoot;
    private BigDecimal LaborCostPerSquareFoot;
    private BigDecimal MaterialCost;
    private BigDecimal LaborCost;

    public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot, BigDecimal materialCost, BigDecimal laborCost) {
        ProductType = productType;
        CostPerSquareFoot = costPerSquareFoot;
        LaborCostPerSquareFoot = laborCostPerSquareFoot;
        MaterialCost = materialCost;
        LaborCost = laborCost;
    }
    public Product(String productType) {
        ProductType = productType;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return CostPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        CostPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return LaborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        LaborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return MaterialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        MaterialCost = materialCost;
    }

    public BigDecimal getLaborCost() {
        return LaborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        LaborCost = laborCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(getProductType(), product.getProductType()) && Objects.equals(getCostPerSquareFoot(), product.getCostPerSquareFoot()) && Objects.equals(getLaborCostPerSquareFoot(), product.getLaborCostPerSquareFoot()) && Objects.equals(getMaterialCost(), product.getMaterialCost()) && Objects.equals(getLaborCost(), product.getLaborCost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductType(), getCostPerSquareFoot(), getLaborCostPerSquareFoot(), getMaterialCost(), getLaborCost());
    }

    @Override
    public String toString() {
        return "Product{" +
                "ProductType='" + ProductType + '\'' +
                ", CostPerSquareFoot=" + CostPerSquareFoot +
                ", LaborCostPerSquareFoot=" + LaborCostPerSquareFoot +
                ", MaterialCost=" + MaterialCost +
                ", LaborCost=" + LaborCost +
                '}';
    }
}



