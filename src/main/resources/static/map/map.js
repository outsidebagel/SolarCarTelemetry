import {ASCRouteGeoJSON} from '../Routes/ASCRouteJSON.js';

// Initalize map with GPS cords and zoom level
var map = L.map('map').setView([51.505, -0.12], 13);
var curLatitude = 0.0;
var curLongitude = 0.0;

// Map should be locked on car
var lockedMap = true;

L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);

// Add marker to map
var marker = L.marker([51.505, -0.12]).addTo(map);

// Updates the marker and centering of map given new gps coords
export function updateMap(curLatitude, curLongitude) {
    if (lockedMap){
        // Move car marker to new lat and long on map
        marker.setLatLng([curLatitude, curLongitude]);
    
        // Re-center map on moved marker if map locked
        map.panTo([curLatitude, curLongitude])
    }
}

function onEachFeature(feature, layer) {
    // does this feature have a property named popupContent?
    if (feature.properties && feature.properties.popupContent) {
        layer.bindPopup(feature.properties.popupContent);
    }
}


L.geoJSON(ASCRouteGeoJSON, {
    onEachFeature: onEachFeature
}).addTo(map);

