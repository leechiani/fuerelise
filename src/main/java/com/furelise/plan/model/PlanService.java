package com.furelise.plan.model;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.furelise.planord.model.*;

@Service
public class PlanService {

	@Autowired
	PlanRepository dao;

	@Autowired
	PlanOrdRepository planOrdDao;

	// 一次產生五筆
	@Transactional
	public String addPlan(Plan req) {
		BigDecimal planPrice = req.getPlanPrice();
		BigDecimal planPricePerCase = req.getPlanPricePerCase();
		if (dao.existsByPlanName(req.getPlanName())) {
			return "方案名稱不可重複";
		} else if (planPrice.compareTo(planPricePerCase) < 0) {
			return "價格不可低於案件報酬";
		} else {
			for (int i = 0; i < 5; i++) {
				Plan plan = new Plan(req.getPlanName(), req.getLiter(), planPrice.multiply(new BigDecimal(i + 1)),
						planPricePerCase, (i + 1), req.getPlanUpload());
				try {
					dao.save(plan);
				} catch (Exception e) {
					e.printStackTrace();
					return "發生錯誤";
				}
			}
		}
		return "新增成功";
	}

	//方案名、公升數同步更新
	@Transactional
	public String updatePlan(Plan req) {
		BigDecimal planPrice = req.getPlanPrice();
		BigDecimal planPricePerCase = req.getPlanPricePerCase();
		Plan p = dao.findById(req.getPlanID()).get();
		String oldName = p.getPlanName();
		Integer oldLiter = p.getLiter(); 
		
		if (planPrice.compareTo(planPricePerCase) < 0) {
			return "價格不可低於案件報酬";
		}
		//變更方案名，方案名重複
		else if (!req.getPlanName().equals(oldName) && dao.existsByPlanName(req.getPlanName())) {
			return "方案名稱不可重複";
		}
		//未變更方案名，未變更公升數>單獨更新
		else if (req.getPlanName().equals(oldName) && req.getLiter().equals(oldLiter)) {
			Plan plan = new Plan(req.getPlanID(), req.getPlanName(), 
						req.getLiter(), planPrice, planPricePerCase,
						req.getTimes(), req.getPlanUpload());
			dao.save(plan);
			return "更新成功";
		}
		// 修改五筆方案名及公升數
		else {
			try {
				// 更新中的方案ID
				Integer thisPlanId = req.getPlanID();
				Plan plan = new Plan(thisPlanId, req.getPlanName(), 
									req.getLiter(), planPrice, planPricePerCase,
									req.getTimes(), req.getPlanUpload());
				dao.save(plan);

				// 找出其餘ID
				List<Integer> pList = dao.findIdByPlanName(oldName);
				for (Integer id : pList) {
					if (!id.equals(thisPlanId)) {
						Plan other = dao.findById(id).get();
						// 僅更新方案名及公升數
						Plan newPlan = new Plan(id, req.getPlanName(), req.getLiter(),
								other.getPlanPrice().stripTrailingZeros(),
								other.getPlanPricePerCase().stripTrailingZeros(), other.getTimes(),
								other.getPlanUpload());
						dao.save(newPlan);
					}
				}
				return "更新成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "錯誤";
			}
		}
	}

	// 同名方案一次刪除
	@Transactional
	public String deletePlan(String planName) {
		String result = "";
		// find planID by planName
		List<Integer> idList = dao.findIdByPlanName(planName);
		// find planOrd with planID mentioned
		for (Integer planID : idList) {
			List<PlanOrd> ordList = planOrdDao.findByPlanID(planID);
			if (ordList.isEmpty()) {
				result = "刪除成功";
			} else {
				result = "不可刪除";
				break;
			}
		}
		if (result.equals("刪除成功")) {
			for (Integer planID : idList) {
				try {
					dao.deleteById(planID);
				} catch (Exception e) {
					e.printStackTrace();
					result = "錯誤";
				}
			}
		}
		return result;
	}

	public List<Plan> getAllPlan() {
		return dao.findAll();
	}

	public Plan getPlanById(Integer planID) {
		return dao.findById(planID).orElse(null);
	}
}
