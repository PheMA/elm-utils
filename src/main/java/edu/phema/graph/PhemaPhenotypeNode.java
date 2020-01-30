package edu.phema.graph;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhemaPhenotypeNode that = (PhemaPhenotypeNode) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
