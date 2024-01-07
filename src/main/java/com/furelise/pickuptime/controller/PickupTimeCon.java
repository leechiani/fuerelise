package com.furelise.pickuptime.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.furelise.pickuptime.model.*;

@Controller
@RequestMapping("/pickuptime")
public class PickupTimeCon {

	@Autowired
	PickupTimeService pickupTimeSvc;

	// return view
	@GetMapping("/")
	public String pickupTimeList() {
		return "pickuptime_list";
	}

	@GetMapping("/all")
	@ResponseBody
	public List<PickupTime> getAllPickupTimes() {
		List<PickupTime> pickupTimeList = pickupTimeSvc.getAllPickupTime();
		return pickupTimeList;
	}

	// return view
	@GetMapping("/add")
	public String addPickupTime() {
		return "pickuptime_create";
	}

	@PostMapping("/adding")
	@ResponseBody
	public String addPickupTime(@Valid @RequestBody PickupTime req) {
		return pickupTimeSvc.addPickupTime(req);
	}

	// return view
	@GetMapping("/update")
	public String updatePickupTime(@RequestParam String timeID, Model model) {
		PickupTime pickupTime = pickupTimeSvc.getPickupTimeById(Integer.valueOf(timeID));
		model.addAttribute("timeID", timeID);
		model.addAttribute("timeRange", pickupTime.getTimeRange());
		return "pickuptime_update";
	}

	@PutMapping("/updating")
	@ResponseBody
	public String updatePickupTime(@Valid @RequestBody PickupTime req) {
		return pickupTimeSvc.updatePickupTime(req);
	}

	@DeleteMapping("deleting")
	@ResponseBody
	public String deletePickupTime(@RequestBody PickupTime req) {
		return pickupTimeSvc.deletePickupTime(req.getTimeID());
	}
//	
//	@GetMapping("{timeID}")
//	// 參數名和@PathVariable名一樣時可省略
//	public PickupTime getPickupTimeById(@PathVariable("timeID") Integer timeID) {
//		return pickupTimeSvc.getPickupTimeById(timeID);
//	}
}
