package com.zeotap.main.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zeotap.main.entity.RuleEngine;
import com.zeotap.main.repository.RuleEngineRepository;
import com.zeotap.main.service.RuleEngineService;

@Service
public class RuleEngineServiceImpl  implements RuleEngineService{
	private RuleEngineRepository ruleEngineRepository;
	
	
	public RuleEngineServiceImpl(RuleEngineRepository ruleEngineRepository) {
		super();
		this.ruleEngineRepository = ruleEngineRepository;
	}

	@Override
	public RuleEngine saveRuleEngine(RuleEngine re) {
		return ruleEngineRepository.save(re);
	}

	@Override
	public RuleEngine getByRule(String rule) {
		return ruleEngineRepository.findByrule(rule);
	}

	@Override
	public List<RuleEngine> getAllRuleEngine() {
		return ruleEngineRepository.findAll();
	}
	
}
