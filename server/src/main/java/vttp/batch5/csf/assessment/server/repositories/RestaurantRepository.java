package vttp.batch5.csf.assessment.server.repositories;



import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;
import vttp.batch5.csf.assessment.server.models.Customer;
import vttp.batch5.csf.assessment.server.models.Order;

@Repository
// Use the following class for MySQL database
public class RestaurantRepository {
    @Autowired JdbcTemplate template;

    public static final String GET_ALL = "select * from customers where username = ? and password = sha2(?, 224)";
    final String INSERT_ORDER = "INSERT INTO place_orders (order_id, payment_id, order_date, total, username) values (?,?,?,?,?)";

    public boolean getCustomer(Customer c){
        SqlRowSet rs = template.queryForRowSet(GET_ALL);
        while (rs.next()){
            return true;
        }
        return false;
    }

    public void saveOrder(Order o){
        template.update(INSERT_ORDER,
            o.getOrder_id(),
            o.getPayment_id(),
            o.getOrder_date(),
            o.getTotal(),
            o.getUsername()
        );
    }

    
}
