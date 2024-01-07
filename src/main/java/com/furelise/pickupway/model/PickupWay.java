package com.furelise.pickupway.model;

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

import lombok.Data;


@Entity
@Table(name = "pickupway")
@Data
public class PickupWay implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wayID", updatable = false)
    private Integer wayID;

    @Column(name = "wayName")
    private String wayName;

    public PickupWay() {
        super();
    }
    
    public PickupWay(Integer wayID, String wayName) {
    	this.wayID = wayID;
    	this.wayName = wayName;
    }
    
    public PickupWay(String wayName) {
    	this.wayName = wayName;
    }
}
