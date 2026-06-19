package solarcar.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import solarcar.backend.services.TelemetryService;

import java.util.Map;

@RestController
public class TelemetryController {
    private TelemetryService telemetryService;

    public TelemetryController(TelemetryService telemetryService){
        this.telemetryService = telemetryService;
    }

    // Client calls when they want to subscribe
    @GetMapping(path = "/telemetry-data")
    public SseEmitter create(){
        return telemetryService.createSseEmitter();
    }    
    
    // InfluxDB sends us new data to send to subscribers
    @PostMapping("/telegraf-publish")
    public void publish(@RequestBody Map<String, Object> newDataJSON) {
        telemetryService.publish(newDataJSON);
    }
}

