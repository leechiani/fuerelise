package com.furelise.planord.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

import lombok.Data;

//import com.furelise.city.model.City;
//import com.furelise.mem.model.Mem;
//import com.furelise.plan.model.Plan;
//import com.furelise.period.model.Period;
//import com.furelise.pickuptime.model.PickupTime;
//import com.furelise.pickupway.model.PickupWay;
//import com.furelise.planstatus.model.PlanStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Objects;

@Entity
@Table(name = "planord")
@Data
public class PlanOrd implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planOrdID", updatable = false)
    private Integer planOrdID;
	
	@Column(name = "planID")
    private Integer planID;

    @Column(name = "timeID")
    private Integer timeID;

    @Column(name = "periodID")
    private Integer periodID;

    @Column(name = "`day`")
    private String day;
    
    @Column(name = "wayID")
    private Integer wayID;
    
    
    @Column(name = "memID")
    private Integer memID;

    @Column(name = "planStart")
    private Date planStart;

    @Column(name = "planEnd")
    private Date planEnd;
    
    @Column(name = "cityCode")
    private String cityCode;

    @Column(name = "floor")
    private String floor;

    @Column(name = "pickupStop")
    private String pickupStop;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "planOrdDate", updatable = false, insertable = false)
    private Timestamp planOrdDate;

    @Column(name = "amendLog")
    private Integer amendLog;

    @Column(name = "planStatusID")
    private Integer planStatusID;

    @Column(name = "contact")
    private String contact;

    @Column(name = "contactTel")
    private String contactTel;

    public PlanOrd() {
        super();
    }

	public PlanOrd(Integer planID, Integer timeID, Integer periodID, String day, Integer wayID, Integer memID,
			Date planStart, Date planEnd, String cityCode, String floor, String pickupStop, BigDecimal total,
			Integer amendLog, Integer planStatusID, String contact, String contactTel) {
		super();
		this.planID = planID;
		this.timeID = timeID;
		this.periodID = periodID;
		this.day = day;
		this.wayID = wayID;
		this.memID = memID;
		this.planStart = planStart;
		this.planEnd = planEnd;
		this.cityCode = cityCode;
		this.floor = floor;
		this.pickupStop = pickupStop;
		this.total = total;
		this.amendLog = amendLog;
		this.planStatusID = planStatusID;
		this.contact = contact;
		this.contactTel = contactTel;
	}
    

}