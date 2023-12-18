package org.example.dao;

import org.example.dto.Order;
import org.example.dto.Product;
import org.example.dto.State;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Component
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
        readFileOrder(order.getDate());
        if(orderMap.containsKey(order.getDate())) {
            ArrayList<Order> adding = orderMap.get(order.getDate());
            adding.add(order);
            orderMap.replace(order.getDate(), adding);
        } else {
            orderMap.put(order.getDate(), new ArrayList<Order>() {{
                add(order);
            }});
        }
        writeFileOrder(order.getDate());

        //can't return orderMap.put(order.getDate(), arraylist)) because that return an arraylist
        //returning order is used for unit testing service layer
        return null;
        //writeFileOrder(order.getDate());
    }


    @Override
    public void editOrder(LocalDate date, Order order) {
        readFileOrder(date);
        if(orderMap.containsKey(date)) {
            ArrayList<Order> editList = orderMap.get(date);
            for(int i = 0; i < editList.size(); i++) {
                if(editList.get(i).getOrderNumber() == order.getOrderNumber()) {
                    editList.set(i, order);
                    orderMap.replace(date, editList);
                    writeFileOrder(date);
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
        readFileOrder(date);
        return orderMap.get(date);
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNum) {
        readFileOrder(date);
        ArrayList<Order> checkForRemoval = orderMap.get(date);
        for(Order o: checkForRemoval) {
            if(o.getOrderNumber() == orderNum) {
                checkForRemoval.remove(o);
                orderMap.replace(date, checkForRemoval);
                writeFileOrder(date);
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
        readFileProduct();
        return new ArrayList<>(productMap.values());
    }
    @Override
    public List<State> getAllStates() {
        readFileState();
        return new ArrayList<>(stateMap.values());
    }
    public void readFileOrder(LocalDate date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
            String fileName = "Orders/Orders_" + date.format(formatter) + ".txt";
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
        String fileName = "Orders/Orders_" + date.format(formatter) + ".txt";
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
            FileReader read = new FileReader("Data/Taxes.txt");
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
            FileReader read = new FileReader("Data/Products.txt");
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
                    temp.setLaborCostPerSquareFoot((new BigDecimal(productArray[2])));
                    productMap.put(productArray[0], temp);
                }

            }
        }catch (Exception e) {
            System.out.println("Error trying to read file product");
        }
    }

    public void getAllFiles() {
        Set<String> listFilesUsingJavaIO = Stream.of(Objects.requireNonNull(new File("Orders").listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
        for(String s: listFilesUsingJavaIO) {
            try {
                FileReader read = new FileReader("Orders/" + s);
                BufferedReader buffer = new BufferedReader(read);
                Scanner scan = new Scanner(buffer);
                ArrayList<Order> tempArrayList = new ArrayList<>();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
                LocalDate date = LocalDate.parse(s.substring(7, 15), formatter);
                while(scan.hasNextLine()) {
                    String[] orderArray = scan.nextLine().split(",");
                    if(orderArray.length >= 12) {
                        Order temp = new Order(Integer.parseInt(orderArray[0]), orderArray[1], orderArray[2], new BigDecimal(orderArray[3]), orderArray[4],
                                new BigDecimal(orderArray[5]), new BigDecimal(orderArray[6]), new BigDecimal(orderArray[7]), new BigDecimal(orderArray[8]),
                                new BigDecimal(orderArray[9]), new BigDecimal(orderArray[10]), new BigDecimal(orderArray[11]), date);
                        tempArrayList.add(temp);
                    }
                }
                orderMap.put(date, tempArrayList);
                buffer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }


    public void exportAll() {
        getAllFiles();
        String fileName = "Export.txt";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            List<Order> exportList = getAllOrder();
            if(!exportList.isEmpty()) {
                for(Order o : exportList) {
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
        } catch (IOException e) {
            System.out.println("Error reading Export.txt");;
        }
    }
}