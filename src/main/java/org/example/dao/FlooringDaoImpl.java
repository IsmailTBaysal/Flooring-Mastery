package org.example.dao;

import org.example.dto.Order;
import org.example.dto.Product;
import org.example.dto.State;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    public void editOrder(LocalDate date, Order order) {
        if(orderMap.containsKey(date)) {
            ArrayList<Order> adding = orderMap.get(date);
            adding.add(order);
            orderMap.replace(date, adding);
            return;
        }
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
    public void readFileOrder(LocalDate date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
            String fileName = "Orders_" + date.format(formatter) + ".txt";
            FileReader read = new FileReader(fileName);
            BufferedReader buffer = new BufferedReader(read);
            Scanner scan = new Scanner(buffer);
            while(scan.hasNextLine()) {
                String[] orderArray = scan.nextLine().split(",");
                if(orderArray.length >= 12) {
                    Order temp = new Order(Integer.parseInt(orderArray[0]), orderArray[1], orderArray[2], new BigDecimal(orderArray[3]), orderArray[4],
                            new BigDecimal(orderArray[5]), new BigDecimal(orderArray[6]), new BigDecimal(orderArray[7]), new BigDecimal(orderArray[8]),
                            new BigDecimal(orderArray[9]), new BigDecimal(orderArray[10]), new BigDecimal(orderArray[11]), date);
                }
            }
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

            //Example input bufferedWriter.write("John::Doe::123 Main Street Hometown, OH, 12345");

            List<Order> orderList = orderMap.get(date);
            for(Order o : orderList) {
                /*
                Need finishing touches for getting state info and product info
                 */
                String writingOut = o.getOrderNumber() + "," + o.getCustomerName() + "," + o.getState();
                bufferedWriter.write(writingOut);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error writing file");
        }
    }
}