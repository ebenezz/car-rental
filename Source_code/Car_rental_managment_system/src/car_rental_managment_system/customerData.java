/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental_managment_system;

import java.sql.Date;

/**
 *
 * @author abenezer
 */
public class customerData {
    private Integer customer_id;
    private String firstName;
    private String lastName;
    private String gender;
    private Integer car_id;
    private String brand;
    private String model;
    private Double total;
    private Date date_rented;
    private Date date_return;
    
    public customerData(Integer customer_id,String firstName,String lastName
            ,String gender,Integer car_id,String brand,String model,Double total,Date date_rented,Date date_return){
        this.customer_id=customer_id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.gender=gender;
        this.car_id=car_id;
        this.brand=brand;
        this.model=model;
        this.total=total;
        this.date_rented=date_rented;                                                           
        this.date_return=date_return;
        
        
    }
    public Integer getCustomer_id(){
        return customer_id;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getGender(){
        return gender;
        }
    public Integer getCar_id(){
        return car_id;
    }
    public String getBrand(){
        return brand;
        }
    public String getModel(){
        return model;
        }    
    public Double getTotal(){
        return total;
    }
    public Date getDate_rented(){
        return date_rented;
    }
    public Date getDate_return(){
        return date_return;
    }
}

