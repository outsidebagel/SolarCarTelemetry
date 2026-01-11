package solarcar.backend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// handles requests to the main website (/)
// returns index.html to the user
@Controller
public class MainController {

    @RequestMapping("/")
    public String mainPage(){
        return "index.html";
    }

}
