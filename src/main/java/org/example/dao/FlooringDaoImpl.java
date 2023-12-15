package org.example.dao;

import org.example.dto.Order;
import org.example.dto.Product;
import org.example.dto.State;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FlooringDaoImpl implements FlooringDao{
    private Map<LocalDate, ArrayList<Order>> orderMap = new HashMap<>();
    private Map<String, State> stateMap = new HashMap<>();
    private Map<String, Product> productMap = new HashMap<>();
    @Override
    /*
    addOrder is returning Order for testcase purpose.
    Do NOT try to store return order
    treat as returned void type
    */
    public Order addOrder(Order order) {
        //readFileOrder(order.getDate());
        if(orderMap.containsKey(order.getDate())) {
            ArrayList<Order> adding = orderMap.get(order.getDate());
            adding.add(order);
            orderMap.replace(order.getDate(), adding);
        } else {
            orderMap.put(order.getDate(), new ArrayList<Order>() {{
                add(order);
            }});
        }
        //can't return orderMap.put(order.getDate(), arraylist)) because that return an arraylist
        //returning order is used for unit testing service layer
        return null;
        //writeFileOrder(order.getDate());
    }


    @Override
    public void editOrder(LocalDate date, Order order) {
        //readFileOrder(date);
        if(orderMap.containsKey(date)) {
            ArrayList<Order> editList = orderMap.get(date);
            for(int i = 0; i < editList.size(); i++) {
                if(editList.get(i).getOrderNumber() == order.getOrderNumber()) {
                    editList.set(i, order);
                    orderMap.replace(date, editList);
                    return;
                }
            }
        }
        else {
            System.out.println("No order found");
        }
    }

    @Override
    public List<Order> getOrders(LocalDate date) {
        //readFileOrder(date);
        return orderMap.get(date);
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNum) {
        //readFileOrder(date);
        ArrayList<Order> checkForRemoval = orderMap.get(date);
        for(Order o: checkForRemoval) {
            if(o.getOrderNumber() == orderNum) {
                checkForRemoval.remove(o);
                orderMap.replace(date, checkForRemoval);
                //writeFileOrder(date);
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
        readFileState();
        return stateMap.get(stateName);
    }

    @Override
    public State addState(String stateName, State state) {
        return stateMap.put(stateName, state);
    }

    @Override
    public Product getProduct(String productName) {
        readFileProduct();
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
    public void readFileOrder(LocalDate date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
            String fileName = "Orders_" + date.format(formatter) + ".txt";
            //Check if file exists
            if(!(new File(fileName).exists())) {
                return;
            }
            //reads file
            FileReader read = new FileReader(fileName);
            BufferedReader buffer = new BufferedReader(read);
            Scanner scan = new Scanner(buffer);
            /*
            OrderNumber – Integer
            CustomerName – String
            State – String
            TaxRate – BigDecimal
            ProductType – String
            Area – BigDecimal
            CostPerSquareFoot – BigDecimal
            LaborCostPerSquareFoot – BigDecimal
            MaterialCost – BigDecimal
            LaborCost – BigDecimal
            Tax – BigDecimal
            Total – BigDecimal
             */
            ArrayList<Order> tempArrayList = new ArrayList<>();
            while(scan.hasNextLine()) {
                //seperate variables based on ,
                String[] orderArray = scan.nextLine().split(",");
                if(orderArray.length >= 12) {
                    Order temp = new Order(Integer.parseInt(orderArray[0]), orderArray[1], orderArray[2], new BigDecimal(orderArray[3]), orderArray[4],
                            new BigDecimal(orderArray[5]), new BigDecimal(orderArray[6]), new BigDecimal(orderArray[7]), new BigDecimal(orderArray[8]),
                            new BigDecimal(orderArray[9]), new BigDecimal(orderArray[10]), new BigDecimal(orderArray[11]), date);
                    tempArrayList.add(temp);
                }
            }
            orderMap.put(date, tempArrayList);
        }catch (Exception e) {
            System.out.println("Error trying to read file order");
        }
    }

    public void writeFileOrder(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String fileName = "Orders_" + date.format(formatter) + ".txt";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            /*
            OrderNumber – Integer
            CustomerName – String
            State – String
            TaxRate – BigDecimal
            ProductType – String
            Area – BigDecimal
            CostPerSquareFoot – BigDecimal
            LaborCostPerSquareFoot – BigDecimal
            MaterialCost – BigDecimal
            LaborCost – BigDecimal
            Tax – BigDecimal
            Total – BigDecimal
             */
            List<Order> orderList = orderMap.get(date);
            if(!orderList.isEmpty()) {
                for(Order o : orderList) {
                    String writingOut = o.getOrderNumber() + "," + o.getCustomerName() + "," + o.getState().getStateName() + "," + o.getState().getTaxRate()
                            + "," + o.getProduct().getProductType() + "," + o.getArea() + "," + o.getProduct().getCostPerSquareFoot() + ","
                            + o.getProduct().getLaborCostPerSquareFoot() + "," + o.getProduct().getMaterialCost() + "," + o.getProduct().getLaborCost() + ","
                            + o.getTax() + "," + o.getTotal();
                    bufferedWriter.write(writingOut);
                    bufferedWriter.newLine();
                }
            }
            else {
                bufferedWriter.write("");
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error writing file");
        }
    }

    public void readFileState () {
        try {
            //reads file
            FileReader read = new FileReader("Taxes.txt");
            BufferedReader buffer = new BufferedReader(read);
            Scanner scan = new Scanner(buffer);
            /*
            StateAbbreviation – String
            StateName – String
            TaxRate – BigDecimal
             */
            while(scan.hasNextLine()) {
                //seperate variables based on ,
                String[] stateArray = scan.nextLine().split(",");
                if(stateArray.length >= 3) {
                    State temp = new State(stateArray[1], new BigDecimal(stateArray[2]));
                    stateMap.put(stateArray[1], temp);
                }

            }
        }catch (Exception e) {
            System.out.println("Error trying to read file state");
        }
    }

    public void readFileProduct() {
        try {
            //reads file
            FileReader read = new FileReader("Products.txt");
            BufferedReader buffer = new BufferedReader(read);
            Scanner scan = new Scanner(buffer);
            /*
                ProductType – String
                CostPerSquareFoot – BigDecimal
                LaborCostPerSquareFoot – BigDecimal
             */
            while(scan.hasNextLine()) {
                //seperate variables based on ,
                String[] productArray = scan.nextLine().split(",");
                if(productArray.length >= 3) {

                    Product temp = new Product(productArray[0]);
                    temp.setCostPerSquareFoot(new BigDecimal(productArray[1]));
                    temp.setLaborCost((new BigDecimal(productArray[2])));
                    productMap.put(productArray[1], temp);
                }

            }
        }catch (Exception e) {
            System.out.println("Error trying to read file product");
        }

    }
}