package com.furelise.pickuptime.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import lombok.Data;



@Entity
@Table (name = "pickuptime")
@Data
public class PickupTime implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timeID", updatable = false)
    private Integer timeID;
    
    @Pattern(regexp="^\\d{2}:\\d{2}-\\d{2}:\\d{2}$", message="請填格式00:00-23:59")
    @Column(name = "timeRange")
    private String timeRange;
    
    public PickupTime() {
    	
    }

    public PickupTime(Integer timeID, String timeRange){
    	this.timeID = timeID;
        this.timeRange = timeRange;
    }  
    
    public PickupTime(String timeRange) {
    	this.timeRange = timeRange;
    }
    
}
