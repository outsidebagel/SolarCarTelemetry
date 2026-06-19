package solarcar.backend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class PageController {
    @GetMapping(path = "/")
    public String mainPage(){
        return "index.html";
    }
}