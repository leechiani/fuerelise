package com.furelise.plan.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer>{

	//PlanOrdService, create planName drop down menu
	List<Plan> findByTimes(Integer times);
	
	//PlanOrdService, create planOrd
	@Query("SELECT planID FROM Plan WHERE planName = :planName AND times = :times")
	Integer findIdByPlanNameAndTimes(@Param(value = "planName") String planName, @Param(value = "times") Integer times);

	@Query("SELECT times FROM Plan WHERE planName = :planName")
	List<Integer> findTimeByPlanName(@Param(value="planName") String planName);
	
	//PlanService, update, delete
	@Query("SELECT planID FROM Plan WHERE planName = :planName")
	List<Integer> findIdByPlanName(@Param(value = "planName") String planName);
	
	boolean existsByPlanName(String planName);
}
