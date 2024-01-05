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
	
	//planOrdService
	@Query("SELECT p FROM PlanOrd p WHERE p.memID = :memID AND p.planEnd > CURRENT_DATE + 3")
	List<PlanOrd> findLaterPlanEnd(@Param(value = "memID") Integer memID);
	
	//pickupWayService
	List<PlanOrd> findByWayID(Integer wayID);
	
	//planService
	List<PlanOrd> findByPlanID(Integer planID);
	
	//pickupTimeService
	List<PlanOrd> findByTimeID(Integer timeID);
}
