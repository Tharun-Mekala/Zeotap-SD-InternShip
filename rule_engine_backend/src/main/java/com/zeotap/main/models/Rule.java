package com.zeotap.main.models;

import java.util.HashMap;
import java.util.Map;

public class Rule {
	private Long age;
	private String department;
	private Long Salary;
	private Long experience;
	
	//Constructor
	public Rule(Long age, String department, Long salary, Long experience) {
		super();
		this.age = age;
		this.department = department;
		Salary = salary;
		this.experience = experience;
	}
	
	//Getters and Setter
	public Long getAge() {
		return age;
	}
	public void setAge(Long age) {
		this.age = age;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Long getSalary() {
		return Salary;
	}
	public void setSalary(Long salary) {
		Salary = salary;
	}
	public Long getExperience() {
		return experience;
	}
	public void setExperience(Long experience) {
		this.experience = experience;
	}
	
	@Override
    public String toString() {
        return "Rule [age=" + age + 
               ", department=" + department + 
               ", salary=" + Salary + 
               ", experience=" + experience + "]";
    }
	
	public Map<String, String> toHashMap() {
	    Map<String, String> ruleMap = new HashMap<>();
	    ruleMap.put("age", ""+this.age);
	    ruleMap.put("department", this.department);
	    ruleMap.put("salary", ""+this.Salary);
	    ruleMap.put("experience", ""+this.experience);
	    return ruleMap;
	}
}
