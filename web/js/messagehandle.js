/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {

    var writeToChatboard = function (text) {
        $(".logger").val(text + "\n");
    }

    var url = "ws://localhost:8080/FraudDetection/listen";
    var socket = new WebSocket(url);

    socket.onopen = function () {
        writeToChatboard("Connected to Socket server : Listening to transactions");
    }
    socket.onclose = function () {
        writeToChatboard("Disconnected from Socket server");
    }


    socket.onmessage = function (evt) {
//        alert('message received')
        var msg = JSON.parse(evt.data);
        var myData = JSON.parse(msg["message"]);


        var gmap = PF('tmap').getMap();

        var marker;

        marker = new google.maps.Marker({
            map: gmap,
            animation: google.maps.Animation.DROP,
            position: {lat: parseFloat(myData["latitude"]), lng: parseFloat(myData["longitude"])}
        });
        switch(myData["fraud"]){
            case "0":
                marker.setIcon('http://maps.google.com/mapfiles/ms/icons/green-dot.png');
                break;
            case "1":
                marker.setIcon('http://maps.google.com/mapfiles/ms/icons/red-dot.png');
                break;
        }
        marker.setAnimation(google.maps.Animation.BOUNCE);
        setTimeout(function(){ marker.setMap(null); }, 1000);
    }
})