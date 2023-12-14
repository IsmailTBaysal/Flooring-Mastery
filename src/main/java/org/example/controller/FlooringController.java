// Author: Ismail Baysal
package org.example.controller;

import org.example.service.FlooringService;
import org.example.ui.FlooringView;

public class FlooringController {
    private FlooringView view;
    private FlooringService service;

    public FlooringController(FlooringService service,FlooringView view){
        this.service=service;
        this.view =view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {
            switch (menuSelection) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    keepGoing = false;
                    break;
                default:
            }

        }
    }

}
