package com.furelise.pickupway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.furelise.pickupway.model.*;

@Controller
@RequestMapping("/pickupway")
public class PickupWayCon {

	@Autowired
	PickupWayService pickupWaySvc;

	// return view
	@GetMapping("/")
	public String pickupWayList() {
		return "pickupway_list";
	}

	// list data, for ajax using
	@GetMapping("/all")
	@ResponseBody
	public List<PickupWay> getAllPickupWays() {
		List<PickupWay> pickupWayList = pickupWaySvc.getAllPickupWay();
		return pickupWayList;
	}

	// return view
	@GetMapping("/add")
	public String addPickupWay() {
		return "pickupway_create";
	}

	// add data, for ajax using
	@PostMapping("/adding")
	@ResponseBody
	public String addPickupWay(@RequestBody PickupWay req) {
		return pickupWaySvc.addPickupWay(req);
	}

	// return view
	@GetMapping("/update")
	public String updatePickupWay(@RequestParam String wayID, Model model) {
		PickupWay pickupWay = pickupWaySvc.getPickupWayById(Integer.valueOf(wayID));
		model.addAttribute("wayID", wayID);
		model.addAttribute("wayName", pickupWay.getWayName());
		return "pickupway_update";
	}

	// amend data, for ajax using
	@PutMapping("/updating")
	@ResponseBody
	public String updatePickupWay(@RequestBody PickupWay req) {
		return pickupWaySvc.updatePickupWay(req);
	}

	// delete data, for ajax using
	@DeleteMapping("/deleting")
	@ResponseBody
	public String deletePickupWay(@RequestBody PickupWay req) {
		return pickupWaySvc.deletePickupWay(req.getWayID());
	}



}
