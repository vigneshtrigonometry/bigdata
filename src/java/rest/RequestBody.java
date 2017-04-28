/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vignesh
 */
@XmlRootElement
public class RequestBody {

   
    @XmlElement
    String lat;
    @XmlElement
    String lon;
    @XmlElement
    String fraud;
}
