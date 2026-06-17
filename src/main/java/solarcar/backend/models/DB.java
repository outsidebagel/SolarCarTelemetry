import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.influxdb.v3.client.InfluxDBClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class DB {
    // DB Credentials   
    private static final String HOST_URL = "https://localhost:8181";

    @Value("${apikey}") 
    private static final String TOKEN;

    private static final String DATABASE = "TELEMETRY";

    // Connect to DB
    private static final InfluxDBClient client = InfluxDBClient.getInstance(HOST_URL, TOKEN.toCharArray(), DATABASE);



}
