package solarcar.backend.services;

import org.springframework.stereotype.Service;



import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Service
public class TelemetryService {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    // private final Pattern parsePattern = Pattern.compile("")

    public SseEmitter createSseEmitter() {
        // Create and add a emitter for our new client
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((e) -> emitters.remove(emitter));
        
        return emitter;
    }

    public void publish(Map<String, Object> teleData) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().data(teleData));
            } catch (IOException e) {
                emitter.complete();
            }
        }
    }
}
