package org.example;

import org.example.controller.FlooringController;
import org.example.dao.FlooringDao;
import org.example.dao.FlooringDaoImpl;
import org.example.service.FlooringDataValidationException;
import org.example.service.FlooringService;
import org.example.service.FlooringServiceImpl;
import org.example.ui.FlooringView;
import org.example.ui.UserIO;
import org.example.ui.UserIOImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) throws FlooringDataValidationException {
        /*
        UserIO myIo = new UserIOImpl();
        FlooringView myView = new FlooringView(myIo);
        FlooringDao myDao = new FlooringDaoImpl();
        FlooringService myService = new FlooringServiceImpl(myDao);
        FlooringController controller = new FlooringController(myService, myView);
        controller.run();
         */
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("org.example");
        appContext.refresh();
        FlooringController controller =
                appContext.getBean("flooringController", FlooringController.class);
        controller.run();
    }
}