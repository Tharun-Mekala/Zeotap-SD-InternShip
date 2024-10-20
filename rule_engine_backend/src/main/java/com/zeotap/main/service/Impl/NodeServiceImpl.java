package com.zeotap.main.service.Impl;

import org.springframework.stereotype.Service;

import com.zeotap.main.entity.Node;
import com.zeotap.main.repository.NodeRepository;
import com.zeotap.main.service.NodeService;

@Service
public class NodeServiceImpl implements NodeService{

	private NodeRepository nodeRepository;
	
	
	public NodeServiceImpl(NodeRepository nodeRepository) {
		super();
		this.nodeRepository = nodeRepository;
	}
	
	@Override
	public Node saveNode(Node node) {
		return nodeRepository.save(node);
	}

	@Override
	public Node updateNode(Node node) {
		Node temp = getNodeById(node.getId());
		temp.setLeft(node.getLeft());
		temp.setRight(node.getRight());
		return nodeRepository.save(temp);
	}

	@Override
	public Node getNodeById(Long id) {
		return nodeRepository.findById(id).get();
	}
	
}
