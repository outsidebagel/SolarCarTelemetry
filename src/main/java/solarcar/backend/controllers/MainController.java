package solarcar.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import solarcar.backend.Main;
import solarcar.backend.services.TelemetryService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class MainController {
    private TelemetryService telemetryService;

    public MainController(TelemetryService telemetryService){
        this.telemetryService = telemetryService;
    }
    
    @GetMapping(path = "/")
    public String mainPage(){
        return "index.html";
    }

    // Client calls when they want to subscribe
    @GetMapping(path = "/telemetry-data")
    public SseEmitter create(){
        return telemetryService.createSseEmitter();
    }    
    
    // InfluxDB sends us new data to send to subscribers
    @PostMapping("/publish")
    public void publish(@RequestBody String data) {
        System.out.println(data);
    }
}

