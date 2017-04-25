/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function test() {

    var gmap = PF('tmap').getMap();

//    var marker;
//
//    marker = new google.maps.Marker({
//        map: gmap,
//        draggable: true,
//        animation: google.maps.Animation.DROP,
//        position: {lat: 40.212, lng: -100.28}
//    });
//    marker.setAnimation(google.maps.Animation.BOUNCE);
}


google.maps.event.addDomListener(window, 'load', test);
