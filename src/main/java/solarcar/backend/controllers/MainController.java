package solarcar.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import solarcar.backend.services.DB;

import java.util.ArrayList;

@RestController
public class MainController {
    @GetMapping("/")
    public String mainPage(){
        return "index.html";
    }
}

