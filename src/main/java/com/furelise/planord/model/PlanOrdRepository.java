package com.furelise.planord.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.furelise.planord.model.*;

@Repository
public interface PlanOrdRepository extends JpaRepository<PlanOrd, Integer>{
	List<PlanOrd> findByMemID(Integer memID);
	
	List<PlanOrd> findByMemIDAndPlanStatusID(Integer memID, Integer planStatusID);
	
	//pickupWayService，deleting
	List<PlanOrd> findByWayID(Integer wayID);
	
	//planService，deleting
	List<PlanOrd> findByPlanID(Integer planID);
	
	//pickupTimeService，deleting
	List<PlanOrd> findByTimeID(Integer timeID);
	
	//periodService，deleting
	List<PlanOrd> findByPeriodID(Integer periodID);
	
}
