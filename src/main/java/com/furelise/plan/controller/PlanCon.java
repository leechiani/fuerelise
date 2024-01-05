package com.furelise.plan.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.furelise.plan.model.*;

@Controller
@RequestMapping("/plan")
public class PlanCon {

	@Autowired
	PlanService planSvc;

	// return view
	@GetMapping("/")
	public String planList() {
		return "plan_list";
	}

	@GetMapping("/all")
	@ResponseBody
	public List<Plan> getAllPlans() {
		List<Plan> planList = planSvc.getAllPlan();
		return planList;
	}

	// return view
	@GetMapping("/add")
	public String addPlan() {
		return "plan_create";
	}

	// 一次產生五筆
	@PostMapping("/adding")
	@ResponseBody
	public String addPlan(@Valid @RequestBody Plan req) {
		return planSvc.addPlan(req);
	}

	// return view
	@GetMapping("/update")
	public String updatePlan(@RequestParam String planID, Model model) {
		Plan plan = planSvc.getPlanById(Integer.valueOf(planID));
		model.addAttribute("planID", planID);
		model.addAttribute("planName", plan.getPlanName());
		model.addAttribute("liter", plan.getLiter());
		model.addAttribute("planPrice", plan.getPlanPrice());
		model.addAttribute("planPricePerCase", plan.getPlanPricePerCase());
		model.addAttribute("times", plan.getTimes());
		return "plan_update";
	}

	@PutMapping("/updating")
	@ResponseBody
	public String updatePlan(@Valid @RequestBody Plan req) {
		return planSvc.updatePlan(req);
	}

	// 同名方案一次刪除
	@DeleteMapping("deleting")
	@ResponseBody
	public String deletePlan(@RequestBody Plan req) {
		return planSvc.deletePlan(req.getPlanName());
	}

}
