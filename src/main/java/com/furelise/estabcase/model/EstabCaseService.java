package com.furelise.estabcase.model;

import java.math.BigDecimal;
import java.sql.Date;
//import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import com.furelise.city.model.City;
import com.furelise.city.model.CityRepository;
import com.furelise.complaint.model.Complaint;
import com.furelise.complaint.model.ComplaintRepository;
import com.furelise.exception.NumberOfModificationsException;
import com.furelise.mem.model.entity.Mem;
import com.furelise.mem.repository.MemRepository;
import com.furelise.period.model.Period;
import com.furelise.period.model.PeriodRepository;
import com.furelise.pickuptime.model.PickupTime;
import com.furelise.pickuptime.model.PickupTimeRepository;
import com.furelise.pickupway.model.PickupWay;
import com.furelise.pickupway.model.PickupWayRepository;
import com.furelise.plan.model.Plan;
import com.furelise.plan.model.PlanRepository;
import com.furelise.planord.model.PlanOrd;
import com.furelise.planord.model.PlanOrdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EstabCaseService {
	@Autowired
	SplitPlanOrdService splitPlanOrdS;
	@Autowired
	EstabCaseRepository estabCaseR;
	@Autowired
	PlanOrdRepository planOrdR;
	@Autowired
	PlanRepository planR;
	@Autowired
	PickupTimeRepository pickuoTimeR;
	@Autowired
	PeriodRepository periodR;
	@Autowired
	PickupWayRepository pickupWayR;
	@Autowired
	CityRepository cityR;
	@Autowired
    MemRepository memR;
	@Autowired
	ComplaintRepository complaintR;


	

	
	public EstabCase updateEstabCase(Integer estabCaseID,Integer empID,Integer planOrdID,
	Date estabCaseDate,BigDecimal planPricePerCase,Integer estabCaseStatus) {
		EstabCase eCase = new EstabCase();
		eCase.setEstabCaseID(estabCaseID);
		eCase.setEmpID(empID);
		eCase.setPlanOrdID(planOrdID);
		eCase.setEstabCaseDate(estabCaseDate);
		eCase.setPlanPricePerCase(planPricePerCase);
		eCase.setEstabCaseStatus(estabCaseStatus);
		estabCaseR.save(eCase);
		return eCase;
	}



	public MemEstabCaseBO getMemEstabCaseCom(Integer estabCaseID, Integer memID){
		EstabCase estabCase = estabCaseR.findById(estabCaseID).orElseThrow();
		Complaint complaintOpt=complaintR.findByEstabCaseIDAndMemID(estabCaseID,memID);
		MemEstabCaseBO memECBO = new MemEstabCaseBO(estabCase,complaintOpt);
		return memECBO;
	}

	public List<MemEstabCaseBO> getMemEstabCaseComs(List<EstabCase> estabCaseList, Integer memID){
		List <MemEstabCaseBO> memECBOList = new ArrayList<>();
		for(EstabCase estabCase:estabCaseList){
			memECBOList.add(getMemEstabCaseCom(estabCase.getEstabCaseID(),memID));
		}

		return memECBOList;
	}


	public MemEstabCaseVO getMemEstabCase(Integer planOrdID){
		MemEstabCaseVO memEC = new MemEstabCaseVO();
		PlanOrd planOrd = planOrdR.findById(planOrdID).orElseThrow();
		Plan plan = planR.findById(planOrd.getPlanID()).orElseThrow();
		PickupTime pickupTime = pickuoTimeR.findById(planOrd.getTimeID()).orElseThrow();
		Period period = periodR.findById(planOrd.getPeriodID()).orElseThrow();
		PickupWay planWay = pickupWayR.findById(planOrd.getWayID()).orElseThrow();
		City city = cityR.findByCityCode(planOrd.getCityCode());
		Mem mem = memR.findById(planOrd.getMemID()).orElseThrow();


		memEC.setPlanOrdID(planOrdID);
		memEC.setPlanName(plan.getPlanName());
		memEC.setTimeRange(pickupTime.getTimeRange());
		memEC.setPlanPeriod(period.getPlanPeriod());
		memEC.setDay(getDaysOfWeek(planOrd.getDay()));
		memEC.setDayCode(planOrd.getDay());
		memEC.setWayName(planWay.getWayName());
		memEC.setMemID(mem.getMemID());
		memEC.setMemName(mem.getMemName());
		memEC.setPlanStart(planOrd.getPlanStart());
		memEC.setPlanEnd(planOrd.getPlanEnd());
		memEC.setTotal(planOrd.getTotal());
		memEC.setCityCode(planOrd.getCityCode());
		memEC.setCityName(city.getCityName());
		memEC.setFloor(planOrd.getFloor());
		memEC.setPickupStop(planOrd.getPickupStop());
		memEC.setContact(planOrd.getContact());
		memEC.setContactTel(planOrd.getContactTel());
		memEC.setMemEstabCaseBO(getMemEstabCaseComs(estabCaseR.findByPlanOrdIDOrderByEstabCaseDateDesc(planOrdID),mem.getMemID()));


		return memEC;
	}
	public EstabCase updateEstabCaseLevel(EstabCaseLevelDTO estabCL){
		LocalDateTime currentTime = LocalDateTime.now();
		EstabCase estabCase = estabCaseR.findById(estabCL.getEstabCaseID()).orElseThrow();
		estabCase.setEstabCaseLevel(estabCL.getEstabCaseLevel());
		estabCase.setEstabCaseFeedback(estabCL.getEstabCaseFeedback());
		estabCase.setEstabCaseFBTime(Timestamp.valueOf(currentTime));

		estabCaseR.save(estabCase);

		return estabCase;
	}

	public PlanOrd updateMemPlanOrdStatus(MemPlanStatusDTO memPlanStatusDTO){
		PlanOrd planOrd = planOrdR.findById(memPlanStatusDTO.getPlanOrdID()).orElseThrow();
		planOrd.setPlanStatusID(memPlanStatusDTO.getPlanStatusID());
		planOrdR.save(planOrd);

		return  planOrd;
	}

	public Complaint addcomplaint(MemEstabCaseComDTO memEstabCaseComDTO,Integer memID){
		LocalDateTime currentTime = LocalDateTime.now();
		Complaint complaint = new Complaint();
		complaint.setMemID(memID);
		complaint.setEstabCaseID(memEstabCaseComDTO.getEstabCaseID());
		complaint.setComDetail(memEstabCaseComDTO.getComDetail());
		complaint.setComTel(memEstabCaseComDTO.getComTel());
		complaint.setComStatus(false);
		complaint.setComStart(Timestamp.valueOf(currentTime));

		complaintR.save(complaint);
		return complaint;
	}

	public List<EstabCase> getEstabCaseByPlanOrdID(Integer planOrdID) {
		
		return estabCaseR.findByPlanOrdIDOrderByEstabCaseDateDesc(planOrdID);
	}
	public EstabCase getEstabCasePK(Integer estabCaseID) {
		return estabCaseR.findById(estabCaseID).orElseThrow();
	}

	public List<EstabCase> getAllEstabCase(){
		return estabCaseR.findAll();
	}
	
	
	//建立以成立案件(方案的金額 需要planOrd.plan.planPricePerCase)
	@Transactional(rollbackOn = Exception.class)
	public void updatePlanOrd(MemPlanDTO memPlanDTO){
		PlanOrd planOrd = planOrdR.findById(memPlanDTO.getPlanOrdID()).orElseThrow();
		Plan plan = planR.findById(planOrd.getPlanID()).orElseThrow();
		Period period = periodR.findById(planOrd.getPeriodID()).orElseThrow();
		if(planOrd.getAmendLog()==0){
			planOrd.setAmendLog(1);
			planOrd.setTimeID(memPlanDTO.getTimeID());
			planOrd.setDay(memPlanDTO.getDay());

			modifyEstabCases(planOrd.getPlanOrdID());

			LocalDate currentDate = LocalDate.now();
			LocalDate threeDaysLater = currentDate.plus(3, ChronoUnit.DAYS);

			List<java.util.Date> list = splitPlanOrdS.getSplitPlanOrd(String.valueOf(threeDaysLater),period.getPlanPeriod() , memPlanDTO.getDay());
			int counts = estabCaseR.countByPlanOrdIDAndEstabCaseDateGreaterThanEqual(memPlanDTO.getPlanOrdID(), Date.valueOf(threeDaysLater));
			System.out.println(counts);
			int count=0;

				for (java.util.Date date : list) {
					if (count<counts){
						count++;
						EstabCase eCase = new EstabCase();
						java.sql.Date sqlDate = new java.sql.Date(date.getTime());
						eCase.setEstabCaseDate(sqlDate);
						eCase.setPlanOrdID(memPlanDTO.getPlanOrdID());
						eCase.setPlanPricePerCase(plan.getPlanPricePerCase());
						eCase.setTakeStatus(false);
						eCase.setEstabCaseStatus(0);
						estabCaseR.save(eCase);
					}
				}
			planOrdR.save(planOrd);


		}else{
			throw new NumberOfModificationsException("Your modification limit has been reached.");
		}
	}


	public String getDaysOfWeek(String day){
		int dayInt =0;
		String days = "";
		for(int i = 0; i<day.length();i++){

			if(day.substring(i,i+1).equals("1")){
				dayInt++;
			}
		}
		switch (dayInt){
			case 1: days = "一"; break;
			case 2: days = "二"; break;
			case 3: days = "三"; break;
			case 4: days = "四"; break;
			case 5: days = "五"; break;
			case 6: days = "六"; break;
			case 7: days = "七"; break;
		}

		return days;
	}

	public void modifyEstabCases(Integer planOrdID){
		LocalDate currentDate = LocalDate.now();
		LocalDate threeDaysLater = currentDate.plus(3, ChronoUnit.DAYS);
		estabCaseR.updateEstabCaseStatus(planOrdID, Date.valueOf(threeDaysLater));

//		List<java.util.Date> list = splitPlanOrdS.getSplitPlanOrd(String.valueOf(threeDaysLater), period, day);
	}




}
