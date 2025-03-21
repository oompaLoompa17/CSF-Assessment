package vttp.batch5.csf.assessment.server.controllers;

import java.io.StringReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.util.json.JSONParser;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch5.csf.assessment.server.models.Customer;
import vttp.batch5.csf.assessment.server.models.MenuItem;
import vttp.batch5.csf.assessment.server.models.Order;
import vttp.batch5.csf.assessment.server.services.RestaurantService;

@RestController
@RequestMapping(path="/api")
public class RestaurantController {

  @Autowired private RestaurantService restSvc;

  private final RestTemplate restTemplate = new RestTemplate();
  private final String PAYMENT_URL= "https://payment-service-production-a75a.up.railway.app/";

  // TODO: Task 2.2
  // You may change the method's signature
  @GetMapping(path="/menu")
  public ResponseEntity<String> getMenus() {
    List<Document> docs = restSvc.getMenu();
    System.out.printf(">>> you are here with %s\n\n", docs.toString());
    JsonArrayBuilder jab = Json.createArrayBuilder();
    for (Document d: docs){
      JsonObject jo = Json.createObjectBuilder()
        .add("id", d.getString("_id"))
        .add("name", d.getString("name"))
        .add("description", d.getString("description"))
        .add("price", d.getDouble("price"))
        .build();
        jab.add(jo);
    }
    JsonArray menu = jab.build();

    return ResponseEntity.ok().body(menu.toString());
  }

  // TODO: Task 4
  // Do not change the method's signature
  @PostMapping(path="food_order")
  public ResponseEntity<String> postFoodOrder(@RequestBody String payload) {
    JsonReader reader = Json.createReader(new StringReader(payload));
    JsonObject jObj = reader.readObject();
    JsonArray items = jObj.getJsonArray("items");
    List<MenuItem> itemList= new LinkedList<>();

    double totalPrice = 0;

    for(int i = 0; i < items.size(); i ++){
      MenuItem li = new MenuItem();
      li.setId(items.getJsonObject(i).getString("id"));
      li.setQuantity(items.getJsonObject(i).getInt("quantity"));
      li.setPrice(items.getJsonObject(i).getInt("price"));
      totalPrice += li.getQuantity()*li.getPrice();
      itemList.add(li);
    }

    Customer cust = new Customer();
    cust.setUsername(jObj.getString("username"));
    cust.setPassword(jObj.getString("password"));
    
  
    if(restSvc.getCustomer(cust)){
      JsonObject paymentJO = Json.createObjectBuilder()
        .add("order_id", UUID.randomUUID().toString().substring(0, 8))
        .add("payer", cust.getUsername())
        .add("payee", "your official name")
        .add("payment", totalPrice)
        .build();

      HttpHeaders headers = new HttpHeaders();
      headers.set("X-Authenticate", cust.getUsername());
      HttpEntity<String> entity = new HttpEntity<>(headers);
      RequestEntity<String> req = RequestEntity
        .post(PAYMENT_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .body(paymentJO.toString());


      ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
      JsonReader reader2 = Json.createReader(new StringReader(resp.getBody()));
      JsonObject jObj2 = reader.readObject(); //successful payment json

      Order order = new Order();
      order.setOrder_id(jObj2.getString("order_id"));
      order.setPayment_id(jObj2.getString("payment_id"));
      order.setOrder_date(new Date(jObj2.getInt("timestamp")));
      order.setTotal(totalPrice);
      order.setUsername(cust.getUsername());
      restSvc.saveOrder(order);
      
      return ResponseEntity.ok().body("");
    }else{
      JsonObject failObj = Json.createObjectBuilder()
      .add("message", "Invalid username and/or password")
      .build();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failObj.toString());
    }
  }
}
