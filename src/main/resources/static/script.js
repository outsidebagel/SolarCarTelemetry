async function getNewData(){
    const request = await new Request("http://localhost:8080/getNewData");

    const response = await fetch(request);
    const newData = await response.json();
    setNewData(newData);
}

function setNewData(data){
        for (let i = 0; i < data.length; i++){
            console.log(data[i].field);
            document.getElementById(data[i].field).innerText = data[i].value;
        }
}

setInterval(getNewData, 150);









