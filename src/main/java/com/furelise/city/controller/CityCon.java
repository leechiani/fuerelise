package com.furelise.city.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.furelise.city.model.*;

@Controller
@RequestMapping("/city")
public class CityCon {

	@Autowired
	CityService citySvc;

	// return view
	@GetMapping("/all")
	public String cityList(Model model) {
		model.addAttribute("cityList", citySvc.getAllCity());

		return "b_city_list";
	}

	// return view
	@GetMapping("/new")
	public String createForm(Model model) {

		model.addAttribute("city", new City());
		return "b_city_create";
	}

	// return data
	@PostMapping("/new")
	public String citySubmit(@Valid City city, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			return "b_city_create";
		} else {
			String proceed = citySvc.addCity(city);
			if (proceed.equals("duplicated cityCode")) {
				model.addAttribute("errorMessage1", "郵遞區號已存在");
				return "b_city_create";
			} else if (proceed.equals("duplicated cityName")) {
				model.addAttribute("errorMessage2", "區域已存在");
				return "b_city_create";
			} else {
				return "redirect:/city/all";
			}
		}
	}

	@PostMapping("/getOne")
	public String getOne(@RequestParam Integer cityID, Model model) {
		City city = citySvc.getCityById(cityID);
		model.addAttribute("city", city);
		return "b_city_update";
	}

	// 不能用put
	@PostMapping("/update")
	public String cityUpdate(@Valid City city, BindingResult result, @RequestParam Integer cityID, Model model) {
		String oldCityCode = citySvc.getCityById(cityID).getCityCode();
		String oldCityName = citySvc.getCityById(cityID).getCityName();
		if (result.hasErrors()) {
			return "b_city_update";
		} else {
			String proceed = citySvc.updateCity(city, oldCityCode, oldCityName);
			if (proceed.equals("duplicated cityCode")) {
				model.addAttribute("errorMessage1", "郵遞區號已存在");
				return "b_city_update";
			} else if (proceed.equals("duplicated cityName")) {
				model.addAttribute("errorMessage2", "區域已存在");
				return "b_city_update";
			} else {
				return "redirect:/city/all";
			}
		}
	}

	@PostMapping("/delete")
	public String cityDelete(@RequestParam String cityID) {
		citySvc.delete(Integer.valueOf(cityID));
		return "redirect:/city/all";
	}
}