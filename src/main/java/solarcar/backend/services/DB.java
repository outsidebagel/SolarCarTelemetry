package solarcar.backend.services;


import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DB {
    // Database connection and query API object
    private final InfluxDBClient db;
    private final QueryApi query;

    // Bean constructor that sets the database connection and QueryAPI
    public DB(@Value("${apiKey1}") String apiKey, @Value("${url1}") String url) {
        this.db = InfluxDBClientFactory.create(url, apiKey.toCharArray(), "ORGNAME","Cardata");
        this.query = db.getQueryApi();
    }
    
    // Query the database for new data
    public ArrayList<Object> getNewData() {
        // Stores our data to return
        ArrayList<Object> returnList = new ArrayList<>();

        // Get our latest data from InfluxDB
        // This query will return latest tables for each of our fields
        String flux = "from(bucket: \"Cardata\")\n" +
                "  |> range(start: -1h)\n" +
                "  |> filter(fn: (r) => r[\"_measurement\"] == \"dcBus\" or r[\"_measurement\"] == \"driveCMD\" or r[\"_measurement\"] == \"mainPackInfo\" or r[\"_measurement\"] == \"odoAndAmp\" or r[\"_measurement\"] == \"packVoltInfo\" or r[\"_measurement\"] == \"packTempInfo\" or r[\"_measurement\"] == \"velocity\")\n" +
                "  |> filter(fn: (r) => r[\"_field\"] == \"amphours\" or r[\"_field\"] == \"avgCellV\" or r[\"_field\"] == \"dcCurrent\" or r[\"_field\"] == \"avgTemp\" or r[\"_field\"] == \"mainPackCurrent\" or r[\"_field\"] == \"dcVoltage\" or r[\"_field\"] == \"driveMotorRPM\" or r[\"_field\"] == \"hiCellID\" or r[\"_field\"] == \"lowCellID\" or r[\"_field\"] == \"velMotorRPM\" or r[\"_field\"] == \"vel\" or r[\"_field\"] == \"summedV\" or r[\"_field\"] == \"packAmp\" or r[\"_field\"] == \"odo\" or r[\"_field\"] == \"motorCurrent\" or r[\"_field\"] == \"lowTemp\" or r[\"_field\"] == \"lowCellV\" or r[\"_field\"] == \"instVolt\" or r[\"_field\"] == \"highTemp\" or r[\"_field\"] == \"hiCellV\")\n" +
                "  |> last()";

        // Send the query
        List<FluxTable> tables = query.query(flux);

        // Loop through every table from the query
        for (FluxTable table : tables){
            List<FluxRecord> records = table.getRecords();
            // For each record (there is only one in each table in our current query) add
            for (FluxRecord record : records){
                returnList.add(new CarDataObject(record.getField(), (double) record.getValue()));
            }
        }
        return returnList;
    }

    // Our key/value pair object to return our data
    private record CarDataObject(String field, double value) {}

}









