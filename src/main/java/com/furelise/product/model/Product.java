package com.furelise.product.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.furelise.productclass.model.ProductClass;
import com.furelise.validation.ValidByteArray;

import lombok.Data;


@Entity
@Table(name = "product")
@Data
public class Product implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pID", updatable = false)
	private Integer pID;

	@NotBlank(message="商品名稱請勿空白")
	@Column(name = "pName")
	private String pName;
	
    @NotNull(message = "不可為空")
	@Column(name = "pPrice")
	private BigDecimal pPrice;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "pClassID", referencedColumnName = "pClassID")
	private ProductClass productClass;


	public void setProductClass(ProductClass productClass) {
		this.productClass = productClass;
	}
	
	@NotNull
	@Digits(integer = 5, fraction = 0, message = "請輸入數字")
	@Column(name = "pStock")
	private Integer pStock;

	@Column(name = "pStatus")
	private Boolean pStatus;

	@NotNull(message="不可為空")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name = "pUpload")
	private LocalDate pUpload;

	@Column(name = "pDetail")
	private String pDetail;

	@ValidByteArray(message= "請至少上傳一張照片")
	@Size(max = 1048576, message = "檔案大小不能超過 1 MB")
	@Column(name = "pImage1", columnDefinition = "longblob")
	private byte[] pImage1;

	@Size(max = 1048576, message = "檔案大小不能超過 1 MB")
	@Column(name = "pImage2", columnDefinition = "longblob")
	private byte[] pImage2;

	@Size(max = 1048576, message = "檔案大小不能超過 1 MB")
	@Column(name = "pImage3", columnDefinition = "longblob")
	private byte[] pImage3;
	
//	@OneToMany( mappedBy = "pID", cascade= CascadeType.ALL)
//	private Set<OrdDetail> ordDetails;


//	@OneToMany( mappedBy = "product", cascade= CascadeType.ALL)
//	private Set<ShopCart> shopCarts;
	
	
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

}
