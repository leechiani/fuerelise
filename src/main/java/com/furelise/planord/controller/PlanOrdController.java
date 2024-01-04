package com.furelise.planord.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.furelise.planord.model.*;
import com.furelise.city.model.City;
import com.furelise.period.model.Period;
import com.furelise.pickuptime.model.PickupTime;
import com.furelise.pickupway.model.PickupWay;
import com.furelise.plan.model.*;
import com.furelise.mem.model.entity.*;

@Controller
//@RequestMapping("/planord")
public class PlanOrdController {

	@Autowired
	PlanOrdService planOrdSvc;
	@Autowired
	PlanService planSvc;

	// return view
	@GetMapping("/planord/")
	public String planOrdList() {
		return "planord_list";
	}

	// list data, for ajax using
	@GetMapping("/planord/all")
	@ResponseBody
	public List<PlanOrd> getAllPlanOrds() {
		return planOrdSvc.getAllPlanOrd();
	}

	// return view planord_detail
	@GetMapping("/planord/detail")
	public String planDetail(@RequestParam String planOrdID, Model model) {
		PlanOrd planOrd = planOrdSvc.getPlanOrdById(Integer.valueOf(planOrdID));
		Plan plan = planSvc.getPlanById(planOrd.getPlanID());

		model.addAttribute("planOrdID", planOrdID);
		model.addAttribute("memName", planOrdSvc.getMemNameById(planOrd.getMemID()));
		model.addAttribute("planName", plan.getPlanName());
		model.addAttribute("timeRange", planOrdSvc.getTimeRange(planOrd.getTimeID()));
		model.addAttribute("day", planOrdSvc.getWeekDay(planOrd.getDay()));
		model.addAttribute("wayName", planOrdSvc.getWayName(planOrd.getWayID()));
		model.addAttribute("total", planOrd.getTotal());
		model.addAttribute("planPeriod", planOrdSvc.getPlanPeriod(planOrd.getPeriodID()));
		model.addAttribute("planStart", planOrd.getPlanStart());
		model.addAttribute("planEnd", planOrd.getPlanEnd());
		model.addAttribute("cityCode", planOrd.getCityCode());
		model.addAttribute("floor", planOrd.getFloor());
		model.addAttribute("pickupStop", planOrd.getPickupStop());
		model.addAttribute("contact", planOrd.getContact());
		model.addAttribute("contactTel", planOrd.getContactTel());
		model.addAttribute("planOrdDate", planOrd.getPlanOrdDate());
		model.addAttribute("amendLog", planOrd.getAmendLog());
		model.addAttribute("planStatus", planOrdSvc.getPlanStatus(planOrd.getPlanStatusID()));
		return "planord_detail";
	}

	// return view
	@GetMapping("/planmall/intro")
	public String planIntro() {
		return "planord_intro";
	}

	// return view
	@GetMapping("/planmall/try")
	public String planTry() {
		return "planord_try";
	}

	// return view
	@GetMapping("/planmall/shop")
	public String planShop() {
		return "planord_shop";
	}

	// create planName drop down menu
	@GetMapping("/planmall/planname")
	@ResponseBody
	public List<Plan> getAllPlanNames() {
		return planOrdSvc.findByTimes();
	}

	// create timeRange drop down menu
	@GetMapping("/planmall/timerange")
	@ResponseBody
	public List<PickupTime> getAllPickupTimes() {
		return planOrdSvc.getPickupTime();
	}

	// create planPeriod drop down menu
	@GetMapping("/planmall/planperiod")
	@ResponseBody
	public List<Period> getAllPeriod() {
		return planOrdSvc.getPeriod();
	}

	// create wayName drop down menu
	@GetMapping("/planmall/wayname")
	@ResponseBody
	public List<PickupWay> getAllPickupWay() {
		return planOrdSvc.getPickupWay();
	}

	// create city drop down menu
	@GetMapping("/planmall/citycode")
	@ResponseBody
	public List<City> getAllCity() {
		return planOrdSvc.getCity();
	}
	
	// create times drop down menu (not in use)
	@PostMapping("/planmall/times")
	@ResponseBody
	public List<Integer> getTimeByPlanName(@RequestBody Plan req){
		return planOrdSvc.getTimeByPlanName(req.getPlanName());
	}
	
	// verify member w/o planord on progress
	@PostMapping("/planmall/checkenddate")
	@ResponseBody
	public boolean verifyPlanOrdPurchase(HttpServletRequest req, @RequestBody PlanOrd planOrd) {
		Mem mem = (Mem) req.getSession().getAttribute("mem");
		if (mem != null) {
			String planStart = planOrd.getPlanStart().toString();
			Integer memID = mem.getMemID();
			return planOrdSvc.verifyPlanOrdPurchase(memID, planStart) ;
			// true:可以訂 false:不可以訂
		} else
			return false;		
	}

	// return view
	@GetMapping("/planord/update")
	public String updatePlanOrd(@RequestParam String planOrdID, Model model) {
		PlanOrd planOrd = planOrdSvc.getPlanOrdById(Integer.valueOf(planOrdID));
		Plan plan = planSvc.getPlanById(planOrd.getPlanID());

		model.addAttribute("times", plan.getTimes());
		model.addAttribute("planOrdID", planOrdID);
		model.addAttribute("memName", planOrdSvc.getMemNameById(planOrd.getMemID()));
		model.addAttribute("planName", plan.getPlanName());
		model.addAttribute("timeRange", planOrdSvc.getTimeRange(planOrd.getTimeID()));
		model.addAttribute("day", planOrd.getDay());
		model.addAttribute("wayName", planOrdSvc.getWayName(planOrd.getWayID()));
		model.addAttribute("total", planOrd.getTotal());
		model.addAttribute("planPeriod", planOrdSvc.getPlanPeriod(planOrd.getPeriodID()));
		model.addAttribute("planStart", planOrd.getPlanStart());
		model.addAttribute("planEnd", planOrd.getPlanEnd());
		model.addAttribute("cityCode", planOrd.getCityCode());
		model.addAttribute("floor", planOrd.getFloor());
		model.addAttribute("pickupStop", planOrd.getPickupStop());
		model.addAttribute("contact", planOrd.getContact());
		model.addAttribute("contactTel", planOrd.getContactTel());
		model.addAttribute("planOrdDate", planOrd.getPlanOrdDate());
		model.addAttribute("amendLog", planOrd.getAmendLog());
		model.addAttribute("planStatus", planOrdSvc.getPlanStatus(planOrd.getPlanStatusID()));
		return "planord_update";
	}

//	// amend data, for ajax using
//	
//	@PutMapping("/updating")
//	@ResponseBody
//	public PlanOrd updatePlanOrd(@RequestBody PlanOrd req) {
//		return planOrdSvc.updatePlanOrd(req);
//	}

//	// 單筆查詢
//	@GetMapping("/{planOrdID}")
//	@ResponseBody
//	public PlanOrd getPlanOrdById(@PathVariable Integer planOrdID) {
//		return planOrdSvc.getPlanOrdById(planOrdID);
//	}
}
