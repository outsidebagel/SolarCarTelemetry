package solarcar.backend.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import solarcar.backend.model.SolarCarTelemetry;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Service
public class TelemetryService {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    // // Parse through our new influx data
    // public SolarCarTelemetry parseNewInfluxData(String lineData) {

    // }

    public SseEmitter createSseEmitter() {
        // Create and add a emitter for our new client
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((e) -> emitters.remove(emitter));
        
        return emitter;
    }

    public void publish(@RequestParam String message) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().data(message));
            } catch (IOException e) {
                emitter.complete();
            }
        }
    }
}
