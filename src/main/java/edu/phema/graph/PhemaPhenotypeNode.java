package edu.phema.graph;

public class PhemaPhenotypeNode {
    private int id;
    private String name;

    public PhemaPhenotypeNode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.getName();
    }
}
