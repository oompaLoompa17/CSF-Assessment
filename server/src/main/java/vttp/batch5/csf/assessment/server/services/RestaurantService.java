package vttp.batch5.csf.assessment.server.services;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;
import vttp.batch5.csf.assessment.server.models.Customer;
import vttp.batch5.csf.assessment.server.models.Order;
import vttp.batch5.csf.assessment.server.repositories.OrdersRepository;
import vttp.batch5.csf.assessment.server.repositories.RestaurantRepository;

@Service
public class RestaurantService {

  @Autowired private RestaurantRepository restRepo;
  @Autowired private OrdersRepository orderRepo;

  // TODO: Task 2.2
  // You may change the method's signature
  public List<Document> getMenu() {
    return orderRepo.getMenu();
  }
  
  // TODO: Task 4  
  public boolean getCustomer(Customer c){
    return restRepo.getCustomer(c);
  }

  public void saveOrder(Order o){
    restRepo.saveOrder(o);
  }

  public boolean saveOrderMongo(JsonObject j){
    return restRepo.saveOrderMongo(j);
  }
}
