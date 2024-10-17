package com.zeotap.main.service;

import com.zeotap.main.entity.Node;

public interface NodeService {
	Node saveNode(Node node);
	Node updateNode(Node node);
	Node getNodeById(Long id);
}
