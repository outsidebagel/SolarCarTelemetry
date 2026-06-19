import { updateMap } from './map/map.js';

// Recieve our SSE connection
const evtSource = new EventSource("http://localhost:8080/telemetry-data");

// Listens for a SSE from our server 
evtSource.addEventListener('message', e => {
        console.log(e.data)
        //  setNewData(e.data)
    });

// Updates the DOM with passed in telemetry data
function setNewData(data){
        for (let i = 0; i < data.length - 1; i++){
            document.getElementById(data[i].field).innerText = data[i].value;
        }
        
        // Update the map
        updateMap(data.latitude, data.longitude);
        
}







