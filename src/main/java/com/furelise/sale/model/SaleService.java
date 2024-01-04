package com.furelise.sale.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleService {
	@Autowired
	SaleRepository dao;

	public boolean addSale(Sale sale) {
		boolean isPass = false;
		if (!dao.existsByCoupon(sale.getCoupon())) {
			dao.save(sale);
			isPass = true;
		}

		return isPass;
	}

	public Sale updateSale(Sale sale) {
			dao.save(sale);	
		return sale;
	}

	public void deleteSale(Integer saleID) {
		dao.deleteById(saleID);
	}

	public Sale getOneSale(Integer saleID) {
		Optional<Sale> optional = dao.findById(saleID);
		return optional.orElse(null);
	}

	public List<Sale> getAll() {
		return dao.findAll();
	}

	public Sale getSaleByCoupon(String coupon) { 
		if(coupon == null || coupon.isBlank()||coupon.isEmpty()) {
			coupon = "sale0000";
			
			
			return dao.findSaleByCoupon(coupon);
		} else {
			
			return dao.findSaleByCoupon(coupon);
		}
		

	}

	// verify coupon
	public String verifyCoupon(String coupon, String total) {
		String result = "";
		// if sale in active
		List<Sale> saleList = dao.findActiveSalesWithCoupon(coupon);
		System.out.println(saleList);
		if (saleList.isEmpty())
			result = "優惠碼不存在";
		// if total >= disRequire
		else if (new BigDecimal(total).compareTo(saleList.get(0).getDisRequire()) < 0) {
			result = "未達折扣門檻";
		} else
			result = "-" + saleList.get(0).getDis();
		return result;
	}
}
