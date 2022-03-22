package com.modestcarz.modestcarzbootapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.modestcarz.modestcarzbootapp.Model.Cars;

@Repository
public interface CarsRepository extends JpaRepository<Cars, Long> {
	
	@Query(value = "select max(TMC_ID) from tbl_modest_cars", nativeQuery=true)
	public Integer getmaxTmcId();

}
