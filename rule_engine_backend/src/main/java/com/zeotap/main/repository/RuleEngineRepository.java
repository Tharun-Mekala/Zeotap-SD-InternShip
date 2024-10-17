package com.zeotap.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zeotap.main.entity.RuleEngine;

public interface RuleEngineRepository extends JpaRepository<RuleEngine,Long> {
	RuleEngine findByrule(String rule);
}
