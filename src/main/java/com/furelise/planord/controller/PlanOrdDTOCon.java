package com.furelise.planord.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.furelise.planord.model.*;
import com.furelise.mem.model.entity.*;

@RestController
@RequestMapping("/planorddto")
public class PlanOrdDTOCon {

	@Autowired
	PlanOrdService planOrdSvc;

	// add data, for ajax using
	@PostMapping("/adding")
	public PlanOrd addPlanOrd(@Valid @RequestBody PlanOrdDTO dto, HttpServletRequest req) {
		Mem mem = (Mem)(req.getSession().getAttribute("mem"));
		if (mem != null){
			Integer memID = mem.getMemID();
			return planOrdSvc.addPlanOrd(dto, memID); //回傳新增的方案訂單物件
		} else 
			return null;
	}

	// list data, for ajax using
	@GetMapping("/finding")
	public List<PlanOrdDTO> getAllPlanOrds() {
		return planOrdSvc.getPlanOrdInfo();
	}
	
	
	@PostMapping("/price")
	public BigDecimal getPrice(@RequestBody PlanOrdDTO dto) {
		return planOrdSvc.getPrice(dto);
	}
}
