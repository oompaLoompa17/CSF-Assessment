package vttp.batch5.csf.assessment.server.models;

import java.util.Date;

public class Order {
    private String order_id;
    private String payment_id;
    private Date order_date;
    private double total;
    private String username;
    public String getOrder_id() {
        return order_id;
    }
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
    public String getPayment_id() {
        return payment_id;
    }
    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }
    public Date getOrder_date() {
        return order_date;
    }
    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String toString() {
        return "Order [order_id=" + order_id + ", payment_id=" + payment_id + ", order_date=" + order_date + ", total="
                + total + ", username=" + username + "]";
    }

    
}
