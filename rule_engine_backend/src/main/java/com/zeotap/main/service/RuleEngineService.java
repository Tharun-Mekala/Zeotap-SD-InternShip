package com.zeotap.main.service;

import java.util.List;

import com.zeotap.main.entity.RuleEngine;

public interface RuleEngineService {
	RuleEngine saveRuleEngine(RuleEngine re);
	RuleEngine getByRule(String rule);
	List<RuleEngine> getAllRuleEngine();
}
