package com.furelise.planord.model;

import com.furelise.city.model.City;
import com.furelise.city.model.CityRepository;
import com.furelise.estabcase.model.SplitPlanOrdService;
import com.furelise.mem.repository.MemRepository;
import com.furelise.period.model.Period;
import com.furelise.period.model.PeriodRepository;
import com.furelise.pickuptime.model.PickupTime;
import com.furelise.pickuptime.model.PickupTimeRepository;
import com.furelise.pickupway.model.PickupWay;
import com.furelise.pickupway.model.PickupWayRepository;
import com.furelise.plan.model.Plan;
import com.furelise.plan.model.PlanRepository;
import com.furelise.planstatus.model.PlanStatusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlanOrdService {

	@Autowired
	PlanOrdRepository dao;

	@Autowired
	MemRepository memDao;

	@Autowired
	PlanRepository planDao;

	@Autowired
	PeriodRepository periodDao;

	@Autowired
	PickupTimeRepository pickupTimeDao;

	@Autowired
	PickupWayRepository pickupWayDao;

	@Autowired
	CityRepository cityDao;

	@Autowired
	PlanStatusRepository planStatusDao;
	@Autowired
	SplitPlanOrdService splitPlanOrdService;

	public PlanOrd addPlanOrd(PlanOrdDTO req, Integer memID) {
		// 狀態碼210003=待付款，210001進行中
		Integer planID = getPlanId(req.getPlanName(), req.getTimes());
		String day = getPickupDay(req.getWeekDay());

		PlanOrd planOrd = new PlanOrd(planID, req.getTimeID(), req.getPeriodID(), day, req.getWayID(), memID,
				req.getPlanStart(), req.getPlanEnd(), req.getCityCode(), req.getFloor(), req.getPickupStop(),
				new BigDecimal(req.getAfterTotal()), 0, 210001, req.getContact(), req.getContactTel());

		PlanOrd createPlanOrd = dao.save(planOrd);

		System.out.println(planOrd);
		// 拆單
		Plan plan = planDao.findById(createPlanOrd.getPlanID()).orElseThrow();
		Period period = periodDao.findById(createPlanOrd.getPeriodID()).orElseThrow();
		splitPlanOrdService.addEstabCases(createPlanOrd.getPlanOrdID(), String.valueOf(createPlanOrd.getPlanStart()),
				period.getPlanPeriod(), createPlanOrd.getDay(), plan.getPlanPricePerCase());

		return createPlanOrd;
	}

	// 方案名+收取次數取得方案ID
	private Integer getPlanId(String planName, String times) {
		return planDao.findIdByPlanNameAndTimes(planName, Integer.valueOf(times));
	}

	// 取得收取日字串
	private String getPickupDay(String[] weekDay) {
		StringBuilder initDay = new StringBuilder("0000000");
		// checkbox回傳String[] weekDay，長度=checked幾項
		for (int i = 0; i < weekDay.length; i++) {
			// weekDay[0](星期一)="0", weekDay[1](星期二)="1"...
			int dayIndex = Integer.parseInt(weekDay[i]);
			// initDay相對應位置改為1
			initDay.setCharAt(dayIndex, '1');
		}
		return initDay.toString();
	}

	public void deletePlanOrd(Integer planOrdID) {
		dao.deleteById(planOrdID);
	}

	public List<PlanOrd> getAllPlanOrd() {
		return dao.findAll();
	}

	public PlanOrd getPlanOrdById(Integer planOrdID) {
		return dao.findById(planOrdID).orElse(null);
	}

// ↓↓↓↓for show name instead of ID in list and detail PlanOrdController↓↓↓↓
	// join查詢所有方案，以名稱而非ID顯示
	public List<PlanOrdDTO> getPlanOrdInfo() {
		Integer planOrdId = 0;
		String memName = "";
		String planName = "";
		Date planStart = null;
		Date planEnd = null;
		BigDecimal total = new BigDecimal(0);
		String planStatus = "";

		List<PlanOrdDTO> infoList = new ArrayList<PlanOrdDTO>();

		List<PlanOrd> planOrdList = dao.findAll();
		for (PlanOrd p : planOrdList) {
			planOrdId = p.getPlanOrdID();
			memName = getMemNameById(p.getMemID());
			planName = getPlanNameById(p.getPlanID());
			planStart = p.getPlanStart();
			planEnd = p.getPlanEnd();
			total = p.getTotal();
			planStatus = getPlanStatus(p.getPlanStatusID());

			PlanOrdDTO info = new PlanOrdDTO(planOrdId, memName, planName, planStart, planEnd, total, planStatus);
			infoList.add(info);
		}
		return infoList;
	}

	// planID找planName
	public String getPlanNameById(Integer planID) {
		return planDao.findById(planID).get().getPlanName();
	}

	// memID找memName
	public String getMemNameById(Integer memID) {
		return memDao.findById(memID).get().getMemName();
	}

	// timeID找timeRange
	public String getTimeRange(Integer timeID) {
		return pickupTimeDao.findById(timeID).get().getTimeRange();
	}

	// wayID找wayName
	public String getWayName(Integer wayID) {
		return pickupWayDao.findById(wayID).get().getWayName();
	}

	// periodID找planPeriod
	public String getPlanPeriod(Integer periodID) {
		return periodDao.findById(periodID).get().getPlanPeriod().toString();
	}

	// planStatusID找planStatus
	public String getPlanStatus(Integer planStatusID) {
		return planStatusDao.findById(planStatusID).get().getPlanStatus();
	}

// ↑↑↑↑↑for list name instead of ID, PlanOrdController↑↑↑↑

	// 找當前使用者是否有待收取舊訂單且新訂單開始日早於舊訂單結束日
	public boolean verifyPlanOrdPurchase(Integer memID, String planStart) {
		// find planOrd of a member
		boolean proceed = false;
		List<PlanOrd> planOrdList = dao.findByMemID(memID);
		if (planOrdList.isEmpty())
			proceed = true;
		// check if planOrd is valid or in progress
		else {
			List<PlanOrd> newList = planOrdList.stream().filter(p -> 
									p.getPlanStatusID().equals(210001) && 
									Date.valueOf(planStart).compareTo(p.getPlanEnd()) <= 0)
									.collect(Collectors.toList());
			if (newList.isEmpty())
				proceed = true;
		}
		return proceed;
	}

	// day字串轉換為星期幾"1001100"
	public String getWeekDay(String day) {
		StringBuilder sb = new StringBuilder();
		char mon = day.charAt(0);
		char tue = day.charAt(1);
		char wed = day.charAt(2);
		char thu = day.charAt(3);
		char fri = day.charAt(4);
		char sat = day.charAt(5);
		char sun = day.charAt(6);
		if (mon == '1')
			sb.append("星期一/");
		if (tue == '1')
			sb.append("星期二/");
		if (wed == '1')
			sb.append("星期三/");
		if (thu == '1')
			sb.append("星期四/");
		if (fri == '1')
			sb.append("星期五/");
		if (sat == '1')
			sb.append("星期六/");
		if (sun == '1')
			sb.append("星期日/");
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	// 取得不重複方案名 + 1次的價格
	public List<Plan> findByTimes() {
		return planDao.findByTimes(1);
	}

	// for PlanOrdController drop down menu
	// 取得PickupTime
	public List<PickupTime> getPickupTime() {
		return pickupTimeDao.findAll();
	}

	// 取得Period
	public List<Period> getPeriod() {
		return periodDao.findAll();
	}

	// 取得PickupWay
	public List<PickupWay> getPickupWay() {
		return pickupWayDao.findAll();
	}

	// 取得City
	public List<City> getCity() {
		return cityDao.findAll();
	}

	public BigDecimal getPrice(PlanOrdDTO dto) {
		Integer planID = getPlanId(dto.getPlanName(), dto.getTimes());
		if (planID != null)
			return planDao.findById(planID).get().getPlanPrice();
		else
			return null;
	}

//	取得各方案可選的收取次數
	public List<Integer> getTimeByPlanName(String planName) {
		System.out.println(planName);
		return planDao.findTimeByPlanName(planName);
	}

}
