package solarcar.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import solarcar.backend.services.DB;

import java.util.ArrayList;

@RestController
public class RestAPI {
    @Autowired
    private final DB db;

    public RestAPI(DB db) {
        this.db = db;
    }

    @GetMapping("/getNewData")
    public ArrayList<Object> velocity(){
        return db.getNewData();
    }
}

