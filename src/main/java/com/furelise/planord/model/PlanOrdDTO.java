package com.furelise.planord.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PlanOrdDTO {

	// 結帳用
	private String planName;
	private Integer timeID;
	private Integer periodID;
	private String times;
	private String[] weekDay;
	private Integer wayID;
	private Date planStart;
	private Date planEnd;
	private String contact;
	@Pattern(regexp="^\\d{10}$", message="手機號碼格式錯誤")
	private String contactTel;
	private String cityCode;
	private String floor;
	private String pickupStop;
	private String afterTotal;
	private String planID;
	
	//方案訂單列表用
	private Integer planOrdID;
	private String memName;
//	private String planName;
//	private Date planStart;
//	private Date planEnd;
	private BigDecimal total;
	private String planStatus;
	
	public PlanOrdDTO(Integer planOrdID, String memName, String planName, 
					  Date planStart, Date planEnd, BigDecimal total, String planStatus) {
		this.planOrdID = planOrdID;
		this.memName = memName;
		this.planName = planName;
		this.planStart = planStart;
		this.planEnd = planEnd;
		this.total = total;
		this.planStatus = planStatus;		
	}
}
