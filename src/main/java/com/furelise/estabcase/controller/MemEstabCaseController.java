package com.furelise.estabcase.controller;


import com.furelise.complaint.model.Complaint;
import com.furelise.estabcase.model.*;

import com.furelise.mem.model.entity.Mem;
import com.furelise.mem.service.AuthService;
import com.furelise.planord.model.PlanOrd;
import com.furelise.planord.model.PlanOrdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mem-estab-case")
public class MemEstabCaseController {
	@Autowired
	private EstabCaseService estabCaseService;
	@Autowired
	private AuthService authService;
	@Autowired
	private PlanOrdRepository planOrdRepository;

	@GetMapping
	public MemEstabCaseVO getAllMemEstabCase( HttpServletRequest req){
//		System.out.println(req);
//		System.out.println(req.getSession());
		Mem mem = (Mem) authService.validate(req, "mem");
//		System.out.println("mem");
//		System.out.println(mem);
		PlanOrd planOrd = planOrdRepository.findByMemIDAndPlanStatusID(mem.getMemID(), 210001).get(0);
		System.out.println(planOrd);
        return estabCaseService.getMemEstabCase(planOrd.getPlanOrdID());
	}


//	@GetMapping("/{id}/{no}")
//	public List<EstabCase> getAllEstabCases(@PathVariable String id, @PathVariable String no){
//		List<EstabCase> estabCaseList = estabCaseService.getAllEstabCase();
//		System.out.println(estabCaseList);
//		return estabCaseList;
//	}


	@PatchMapping()
	public EstabCase updateEstabCaseLevel(
			@Validated @RequestBody EstabCaseLevelDTO estabCaseLevelDTO){
		return estabCaseService.updateEstabCaseLevel(estabCaseLevelDTO);
	}

	@PostMapping()
	public Complaint addMemcomplaint(
			@Validated @RequestBody MemEstabCaseComDTO memEstabCaseComDTO,HttpServletRequest req){
		Mem mem = (Mem) authService.validate(req, "mem");
		return estabCaseService.addcomplaint(memEstabCaseComDTO,mem.getMemID());
	}

	@PostMapping("/mem-plan-ord")
	public void updateMemPlanOrd(
			@Validated @RequestBody MemPlanDTO memPlanDTO){
		estabCaseService.updatePlanOrd(memPlanDTO);
	}

	@PatchMapping("/mem-plan-ord-status")
	public PlanOrd updateMemPlanOrdStatus(
			@Validated @RequestBody MemPlanStatusDTO memPlanStatusDTO){
		return estabCaseService.updateMemPlanOrdStatus(memPlanStatusDTO);
	}

}
