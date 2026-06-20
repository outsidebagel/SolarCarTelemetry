import { updateMap } from './map/map.js';

// Recieve our SSE connection
const evtSource = new EventSource("http://localhost:8080/telemetry-data");

// Listens for a SSE from our server 
evtSource.addEventListener('message', e => {
        setNewData(JSON.parse(e.data))
    });

// Updates the DOM with passed in telemetry data
function setNewData(data){
        for (var element in data){  
            document.getElementById(element).innerText = data[element];
        }
        
        updateMap(data.gpsLang, data.gpsLong);
        
}







