package com.spring.microservices.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long>{

	
	//Query on findByFromAndTo in ExchangeValueRepository
	ExchangeValue findByFromAndTo(String from, String to);
}
