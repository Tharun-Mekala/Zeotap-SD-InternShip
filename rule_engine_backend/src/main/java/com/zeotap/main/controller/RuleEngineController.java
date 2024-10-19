package com.zeotap.main.controller;

import java.util.*;
import java.util.StringTokenizer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zeotap.main.entity.Node;
import com.zeotap.main.entity.RuleEngine;
import com.zeotap.main.models.Rule;
import com.zeotap.main.service.NodeService;
import com.zeotap.main.service.RuleEngineService;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class RuleEngineController {
	
	private RuleEngineService ruleEngineService;
	private NodeService nodeService;
	private static boolean flag = false;
	final static String Clause = "AND OR WHERE WITH AS HAVING";
	public RuleEngineController(RuleEngineService ruleEngineService, NodeService nodeService) {
		super();
		this.ruleEngineService = ruleEngineService;
		this.nodeService = nodeService;
	}
	
	private static boolean validate_rule(String rule)
	{
		if(!rule.matches(".*[><=].*"))
			return false;
		StringTokenizer tokens = new StringTokenizer(rule, " ()", true);
		Stack<String> parentheses_stack = new Stack<>();
		Stack<String> operator_stack = new Stack<>();
		Stack<String> node_stack = new Stack<>();
		while(tokens.hasMoreElements())
		{
			String s =tokens.nextToken();
			if(s.equals(" "))
				continue;
			else if(s.equals("("))
				parentheses_stack.push(s);
			else if(s.equals(")"))
			{
				if(parentheses_stack.isEmpty())
					return false;
				parentheses_stack.pop();
				if(!operator_stack.isEmpty())
				{
					String temp = operator_stack.pop();
					node_stack.pop();
					node_stack.pop();
					node_stack.push(temp);
				}
			}
			else if(Clause.contains(s))
			{
				operator_stack.push(s);
			}
			else if(">=<=".contains(s))
			{
				node_stack.pop();
				tokens.nextToken();
				tokens.nextToken();
				node_stack.push(s);
			}
			else
				node_stack.push(s);
			
		}
		while(!operator_stack.isEmpty())
		{
			String temp  = operator_stack.pop();
			if(node_stack.size()<2)
				return false;
			node_stack.pop();
			node_stack.pop();
			node_stack.push(temp);
		}
		if(node_stack.size()==1 && parentheses_stack.isEmpty() )
			return true;
		return false;
	}
	@PostMapping("rule_engine/create_rule")
	@ResponseBody
	public ResponseEntity<Node> create_rule(@RequestParam String rule)
	{	
		Node errorNode = new Node("Error", "Invalid input. Please check your data and try again.");
		if(!flag)
			if(!validate_rule(rule))
				return ResponseEntity.badRequest().body(errorNode);
		RuleEngine re = null;
		re = ruleEngineService.getByRule(rule);
		if(re!=null)
			return ResponseEntity.ok(re.getRoot());
		StringTokenizer tokens = new StringTokenizer(rule, " ()", true);
		Stack<Node> operator_stack = new Stack<>();
		Stack<Node> node_stack = new Stack<>();
		while(tokens.hasMoreElements())
		{
			String s =tokens.nextToken();
			if(s.equals("(")||s.equals(" "))
				continue;
			else if(s.equals(")"))
			{
				if(!operator_stack.isEmpty())
				{
					Node temp = operator_stack.pop();
					temp.setRight(node_stack.pop());
					temp.setLeft(node_stack.pop());
					temp = nodeService.saveNode(temp);
					node_stack.push(temp);
				}
			}
			else if(Clause.contains(s))
			{
				Node temp =new Node("operator",s);
				temp = nodeService.saveNode(temp);
				operator_stack.push(temp);
			}
			else if(">=<=".contains(s))
			{
				Node temp =new Node("operand",s);
				temp.setLeft(node_stack.pop());
				tokens.nextToken();
				Node rightNode =  new Node("",tokens.nextToken());
				temp.setRight(rightNode);
				temp = nodeService.saveNode(temp);
				node_stack.push(temp);
			}
			else
			{
				Node temp = new Node("",s);
				temp = nodeService.saveNode(temp);
				node_stack.push(temp);
			}
		}
		while(!operator_stack.isEmpty())
		{
			Node temp = operator_stack.pop();
			temp.setRight(node_stack.pop());
			temp.setLeft(node_stack.pop());
			temp = nodeService.updateNode(temp);
			node_stack.push(temp);
		}
		Node root = node_stack.isEmpty()?null:node_stack.peek();
		re = new RuleEngine(rule,root);
		re = ruleEngineService.saveRuleEngine(re);
		return root==null?ResponseEntity.badRequest().body(errorNode):ResponseEntity.ok(root);
	}
	
	@PostMapping("rule_engine/combine_rules")
	@ResponseBody
	public ResponseEntity<Node>  combine_rule(@RequestBody List<String> rules) {
	    Node root = null;
	    Node errorNode = new Node("Error", "Invalid input. Please check your data and try again.");
	    
	    for(String rule:rules)
	    {
	    	if(!validate_rule(rule))
	    	{
				return ResponseEntity.badRequest().body(errorNode);
	    	}
	    }
	    flag=true;
	    for (String rule : rules) 
	    {
	    	
	        ResponseEntity<Node> responseEntity = create_rule(rule);
	        if (responseEntity.getStatusCode() == HttpStatus.OK) 
	        {
	            Node newRule = responseEntity.getBody();
	            if (root == null) 
	                root = newRule; 
	            else 
	            {
	                Node orNode = new Node("operator", "OR");
	                orNode.setLeft(root);
	                orNode.setRight(newRule);
	                nodeService.saveNode(orNode);
	                root = orNode;
	            }
	        } 
	        else if (responseEntity.getStatusCode() == HttpStatus.CONFLICT) 
	        {
	            Node existingRule = responseEntity.getBody();
	            if (root == null)
	                root = existingRule;
	            else 
	            {
	                Node orNode = new Node("operator", "OR");
	                orNode.setLeft(root);
	                orNode.setRight(existingRule);
	                nodeService.saveNode(orNode);
	                root = orNode; 
	            }
	        } 
	        else 
	        {
	        	flag=false;
	           return ResponseEntity.badRequest().body(errorNode);
	        }
	    }
	    flag=false;
		return root==null?ResponseEntity.badRequest().body(errorNode):ResponseEntity.ok(root);
	}
	
	@PostMapping("rule_engine/evaluate_rule")
	public ResponseEntity<Boolean> evaluate_rule(@RequestBody Rule data)
	{
		List<RuleEngine> list = ruleEngineService.getAllRuleEngine();
		Map<String,String> map = data.toHashMap();
		
		for(RuleEngine re :list)
			if(evaluate_rule_fun(re.getRoot(),map))
				return ResponseEntity.ok(true);
		
		return ResponseEntity.ok(false);
		
	}
	static boolean evaluate_rule_fun(Node root,Map<String, String> data)
	{
		if(root==null)
			return true;
		 if (root.getType().equals("operator")) 
		 {
		        boolean leftEval = evaluate_rule_fun(root.getLeft(), data);
		        boolean rightEval = evaluate_rule_fun(root.getRight(), data);
		        switch (root.getValue()) 
		        {
		            case "AND":
		                return leftEval && rightEval;
		            case "OR":
		                return leftEval || rightEval;
		            default:
		                throw new UnsupportedOperationException("Unknown operand: " + root.getValue());
		        }
		 }
		 else if (root.getType().equals("operand")) 
		 {
		        String leftVal = root.getLeft().getValue();  
		        String rightVal = root.getRight().getValue();
		        String actualData = data.get(leftVal);
		        switch (root.getValue()) 
		        {
		            case ">":
		                return rightVal.compareTo(actualData)<0;
		            case "<":
		                return rightVal.compareTo(actualData)>0;
		            case "=":
		                return rightVal.contains(actualData);
		            case ">=":
		                return Integer.parseInt(actualData) >= Integer.parseInt(rightVal);
		            case "<=":
		                return Integer.parseInt(actualData) <= Integer.parseInt(rightVal);
		            default:
		                throw new UnsupportedOperationException("Unknown comparison operator: " + root.getValue());
		        }
		    }
		    return false; 
	}
}