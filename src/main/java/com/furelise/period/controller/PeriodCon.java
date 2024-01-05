package com.furelise.period.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.furelise.city.model.*;
import com.furelise.period.model.*;
import com.furelise.pickupway.model.*;

@Controller
@RequestMapping("/period")
public class PeriodCon {

	@Autowired
	PeriodService periodSvc;

	// return view
	@GetMapping("/")
	public String periodList() {
		return "period_list";
	}
	
	@GetMapping("/all")
	@ResponseBody
	public List<Period> getAllPeriods() {
		List<Period> periodList = periodSvc.getAllPeriod();
		return periodList;
	}

	// return view
	@GetMapping("/add")
	public String addperiod() {
		return "period_create";
	}

	@PostMapping("/adding")
	@ResponseBody
	public String addPeriod(@Valid @RequestBody Period req) {
		return periodSvc.addPeriod(req);
	}
	
	// return view
	@GetMapping("/update")
	public String updateperiod(@RequestParam String periodID, Model model) {
		Period period = periodSvc.getPeriodById(Integer.valueOf(periodID));
		model.addAttribute("periodID", periodID);
		model.addAttribute("planPeriod", period.getPlanPeriod());
		return "period_update";
	}

	@PutMapping("/updating")
	@ResponseBody
	public String updatePeriod(@Valid @RequestBody Period req) {
		return periodSvc.updatePeriod(req);
	}

	@DeleteMapping("/deleting")
	@ResponseBody
	public String deletePeriod(@RequestBody Period req) {
		return periodSvc.deletePeriod(req.getPeriodID());
	}
}
