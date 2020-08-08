package edu.phema.graph;

import org.cqframework.cql.elm.tracking.TrackBack;
import org.hl7.fhir.Library;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PhemaPhenotypeNode {
    private int id;
    private String name;
    private List<LibraryReference> libraryReferences;

    /**
     * Provides a lightweight representation of a CQL library (code file) reference.
     * This allows us to provide a link between our higher-level nodes and where they
     * are originally defined within the CQL.
     */
    public class LibraryReference {
        public int startLine;
        public int startChar;
        public int endLine;
        public int endChar;

        public LibraryReference() {}
        public LibraryReference(int startLine, int startChar, int endLine, int endChar) {
            this.startLine = startLine;
            this.startChar = startChar;
            this.endLine = endLine;
            this.endChar = endChar;
        }
    }

    public PhemaPhenotypeNode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Construct a new PhemaPhenotypeNode
     * @param id A unique identifier for this node
     * @param name The name of the node to be used in visualizations
     * @param trackBacks A collection of references in the original CQL where this element is defined
     */
    public PhemaPhenotypeNode(int id, String name, List<TrackBack> trackBacks) {
        this.id = id;
        this.name = name;

        if (trackBacks != null) {
            this.libraryReferences = new ArrayList<>(trackBacks.size());
            trackBacks.forEach(t -> this.libraryReferences.add(new LibraryReference(
                t.getStartLine(), t.getStartChar(), t.getEndLine(), t.getEndChar()
            )));
        }
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<LibraryReference> getLibraryReferences() {
        return this.libraryReferences;
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
