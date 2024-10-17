package com.zeotap.main.controller;

import java.util.*;
import java.util.StringTokenizer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
public class RuleEngineController {
	
	private RuleEngineService ruleEngineService;
	private NodeService nodeService;
	
	public RuleEngineController(RuleEngineService ruleEngineService, NodeService nodeService) {
		super();
		this.ruleEngineService = ruleEngineService;
		this.nodeService = nodeService;
	}
	
	@PostMapping("rule_engine/create_rule")
	@ResponseBody
	public Node create_rule(@RequestParam String rule)
	{
		final String Clause = "AND OR WHERE WITH AS HAVING";
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
		RuleEngine re = new RuleEngine(rule,root);
		re = ruleEngineService.saveRuleEngine(re);
		return root;
	}
	
	@PostMapping("rule_engine/combine_rules")
	@ResponseBody
	public Node combine_rule(@RequestBody List<String> rules)
	{
		Node root = null;
		for(String s:rules)
		{
			RuleEngine re = ruleEngineService.getByRule(s);
			if(root==null)
			{
				
				root=(re!=null)?re.getRoot():create_rule(s);
			}
			else
			{
				Node newRule = (re!=null)?re.getRoot():create_rule(s);
				Node orNode = new Node("operator", "OR");
				orNode.setLeft(root);
				orNode.setRight(newRule);
				nodeService.saveNode(orNode);
	            root = orNode;
			}
			
		}
		return root;
	}
	
	@GetMapping("rule_engine/evaluate_rule")
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
