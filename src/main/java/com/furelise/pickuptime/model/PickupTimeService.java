package com.furelise.pickuptime.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.furelise.planord.model.*;

@Service
public class PickupTimeService {

	@Autowired
	PickupTimeRepository dao;

	@Autowired
	PlanOrdRepository planOrdDao;

	public String addPickupTime(PickupTime req) {
		String result = "";
		if (!dao.existsByTimeRange(req.getTimeRange().trim())) {
			PickupTime pickupTime = new PickupTime(req.getTimeRange().trim());
			dao.save(pickupTime);
			result = "新增成功";
		} else {
			result = "收取時段已存在";
		}
		return result;
	}

	public String updatePickupTime(PickupTime req) {
		String result = "";
		String oldTime = dao.findById(req.getTimeID()).get().getTimeRange().trim();
		if(oldTime.equals(req.getTimeRange()) || !dao.existsByTimeRange(req.getTimeRange().trim())) {
			PickupTime pickupTime = new PickupTime(req.getTimeID(), req.getTimeRange().trim());
			dao.save(pickupTime);
			result = "更新成功";
		} else
			result = "收取時段已存在";
		return result;
	}

	public String deletePickupTime(Integer timeID) {
		List<PlanOrd> list = planOrdDao.findByTimeID(timeID);
		if (list.isEmpty()) {
			dao.deleteById(timeID);
			return "deleted successfully";
		} else
			return timeID + " is in use!";
	}

	public List<PickupTime> getAllPickupTime() {
		return dao.findAll();
	}

	public PickupTime getPickupTimeById(Integer timeID) {
		return dao.findById(timeID).orElse(null);
	}

}
