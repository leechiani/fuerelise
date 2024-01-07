package com.furelise.period.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "period")
@Data
public class Period implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "periodID", updatable = false )
    private Integer periodID;
    
    @Min(value=1, message="請填正整數")
    @NotNull(message="請勿空白")
    @Column(name = "planPeriod")
    private Integer planPeriod;
    
    public Period() {
        super();
    }
    
    public Period(Integer planPeriod) {
    	this.planPeriod = planPeriod;
    }
    
    public Period(Integer periodID, Integer planPeriod) {
    	this.periodID = periodID;
    	this.planPeriod = planPeriod;
    }

    

    

    
    
}
