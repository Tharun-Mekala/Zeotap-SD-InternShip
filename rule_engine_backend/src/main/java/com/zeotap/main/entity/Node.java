package com.zeotap.main.entity;

import jakarta.persistence.*;

@Entity
public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "left_id") // Renamed for clarity
    private Node left;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "right_id") // Renamed for clarity
    private Node right;

    public Node() {
        super();
    }

    public Node(String type, String value) {
        super();
        this.type = type;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
