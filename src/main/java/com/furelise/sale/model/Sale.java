package com.furelise.sale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "sale")
@Data
public class Sale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saleID", updatable = false)
    private Integer saleID;

    @Pattern(regexp = "^[a-zA-Z0-9]{2,10}$", message = "請填入二至十位的英文字或數字")
    @NotBlank(message = "請勿空白")
    @Column(name = "coupon")
    private String coupon;

    @NotNull(message="不可為空")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "saleStart")
    private LocalDate saleStart;

    @NotNull(message = "不可為空")
    @Future(message = "日期必須是在今日(不含)之後")
	@DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "saleEnd")
    private LocalDate saleEnd;

    @NotNull(message = "不可為空")
//	@Pattern(regexp = "^[0-9]$", message = "請填入數字")
//	@NotBlank(message = "請勿空白")
    @Column(name = "disRequire")
    private BigDecimal disRequire;

    @NotNull(message = "不可為空")
//	@Pattern(regexp = "^[0-9]$", message = "請填入數字")
//	@NotBlank(message = "請勿空白")
    @Column(name = "dis")
    private BigDecimal dis;

//	@OneToMany(mappedBy="sale", cascade= CascadeType.ALL)
//	private Set<Ord> ords;	

    public Sale() {
        super();
    }

    public Sale(Integer saleID, String coupon, LocalDate saleStart,
                LocalDate saleEnd, BigDecimal disRequire, BigDecimal dis) {
        this.saleID = saleID;
        this.coupon = coupon;
        this.saleStart = saleStart;
        this.saleEnd = saleEnd;
        this.disRequire = disRequire;
        this.dis = dis;
    }

    public Sale(String coupon, LocalDate saleStart,
                LocalDate saleEnd, BigDecimal disRequire, BigDecimal dis) {
        this.coupon = coupon;
        this.saleStart = saleStart;
        this.saleEnd = saleEnd;
        this.disRequire = disRequire;
        this.dis = dis;
    }


}
