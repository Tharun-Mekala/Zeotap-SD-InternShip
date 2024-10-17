package com.zeotap.main.entity;

import jakarta.persistence.*;

@Entity
public class RuleEngine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
    private String rule;
	
	@ManyToOne
    @JoinColumn(name = "root")
    private Node root;
	
	public RuleEngine() {
		super();
	}

	public RuleEngine(String rule, Node root) {
		super();
		this.rule = rule;
		this.root = root;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
	
	
	
	
}
