package vttp.batch5.csf.assessment.server.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Customer {
    private String username;
    private String password;

    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "Customer [username=" + username + ", password=" + password + "]";
    }

    public static Customer populate(SqlRowSet rs){
        final Customer customer = new Customer();
        customer.setUsername(rs.getString("username"));
        customer.setPassword(rs.getString("password"));
        return customer;
    }
}
