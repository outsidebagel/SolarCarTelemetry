// Retrieves new telemetry data from the telemetry API
 function getNewData(){
    // Change API URL when using non-local deployment
    const request = await new Request("http://localhost:8080/getNewData");

    const response = await fetch(request);
    const newData = await response.json();
    setNewData(newData);
}

// Updates the DOM with passed in telemetry data
function setNewData(data){
        for (let i = 0; i < data.length; i++){
            document.getElementById(data[i].field).innerText = data[i].value;
        }
}

// Calls the API every 150 ms
setInterval(getNewData, 150);









