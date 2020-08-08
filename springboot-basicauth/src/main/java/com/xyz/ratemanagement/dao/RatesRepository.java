package com.xyz.ratemanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.ratemanagement.model.Rate;

@Repository
public interface RatesRepository extends JpaRepository<Rate, Long>{
	 Rate findByRateId(Long id);
}
