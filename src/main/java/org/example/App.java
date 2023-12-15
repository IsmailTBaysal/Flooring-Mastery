package org.example;

import org.example.controller.FlooringController;
import org.example.dao.FlooringDao;
import org.example.dao.FlooringDaoImpl;
import org.example.service.FlooringService;
import org.example.service.FlooringServiceImpl;
import org.example.ui.FlooringView;
import org.example.ui.UserIO;
import org.example.ui.UserIOImpl;

public class App {
    public static void main(String[] args) {

        UserIO myIo = new UserIOImpl();
        FlooringView myView = new FlooringView(myIo);
        FlooringDao myDao = new FlooringDaoImpl();
        FlooringService myService = new FlooringServiceImpl(myDao);
        FlooringController controller = new FlooringController(myService, myView);
        controller.run();

    }
}