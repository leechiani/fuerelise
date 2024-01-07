package com.furelise.city.model;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.checkerframework.common.aliasing.qual.Unique;

//import com.furelise.emp.model.Emp;
//import com.furelise.planord.model.PlanOrd;

import lombok.Data;

@Entity
@Table(name = "city")
@Data
public class City implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cityID")
	private Integer cityID;

	@Pattern(regexp = "^\\d{3}$", message = "請填三位數字")
	@Unique
	@NotBlank(message = "請勿空白")
	@Column(name = "cityCode", columnDefinition = "char")
	private String cityCode;

	@NotBlank(message = "請勿空白")
	@Column(name = "cityName")
	private String cityName;

	public City() {
		super();
	}

}
