package solarcar.backend.services;

import org.springframework.stereotype.Service;

import com.influxdb.v3.client.InfluxDBClient;

@Service
public class TelemetryService {
    // Wire our DB connection
    private final InfluxDBClient influxClient;

    public TelemetryService(InfluxDBClient influxClient) {
        this.influxClient = influxClient;
    }

    // Calls our DB for Last Value Cache values
    


}
