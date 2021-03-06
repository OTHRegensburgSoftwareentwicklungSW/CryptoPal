
package de.othr.blackcastle;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "OrderService", targetNamespace = "http://service.oth.swr.de/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface OrderService {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<de.swr.oth.service.Order>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "allOrders", targetNamespace = "http://service.oth.swr.de/", className = "de.swr.oth.service.AllOrders")
    @ResponseWrapper(localName = "allOrdersResponse", targetNamespace = "http://service.oth.swr.de/", className = "de.swr.oth.service.AllOrdersResponse")
    public List<Order> allOrders(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.swr.oth.service.Order
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "buyGame", targetNamespace = "http://service.oth.swr.de/", className = "de.swr.oth.service.BuyGame")
    @ResponseWrapper(localName = "buyGameResponse", targetNamespace = "http://service.oth.swr.de/", className = "de.swr.oth.service.BuyGameResponse")
    public Order buyGame(
        @WebParam(name = "arg0", targetNamespace = "")
        Game arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        User arg1);

    /**
     * 
     * @param bestellId
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getGameTitel", targetNamespace = "http://service.oth.swr.de/", className = "de.swr.oth.service.GetGameTitel")
    @ResponseWrapper(localName = "getGameTitelResponse", targetNamespace = "http://service.oth.swr.de/", className = "de.swr.oth.service.GetGameTitelResponse")
    public String getGameTitel(
        @WebParam(name = "BestellId", targetNamespace = "")
        Long bestellId);

}
