package edu.phema.graph;

import org.cqframework.cql.elm.tracking.TrackBack;
import org.hl7.elm.r1.VersionedIdentifier;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PhemaPhenotypeNodeTest {
    @Test
    public void implicitNullLibraryReferences() {
        PhemaPhenotypeNode node = new PhemaPhenotypeNode(1, "Test");
        assertNull(node.getLibraryReferences());
    }

    @Test
    public void explicitNullLibraryReferences() {
        PhemaPhenotypeNode node = new PhemaPhenotypeNode(1, "Test", null);
        assertNull(node.getLibraryReferences());
    }

    @Test
    public void emptyLibraryReferences() {
        PhemaPhenotypeNode node = new PhemaPhenotypeNode(1, "Test", new ArrayList<TrackBack>());
        assertNotNull(node.getLibraryReferences());
        assertEquals(0, node.getLibraryReferences().size());
    }

    @Test
    public void fiilledLibraryReferences() {
        ArrayList<TrackBack> trackbacks = new ArrayList<TrackBack>() {{
            add(new TrackBack(new VersionedIdentifier(), 1, 2, 10, 11));
            add(new TrackBack(new VersionedIdentifier(), 11, 12, 20, 21));
        }};
        PhemaPhenotypeNode node = new PhemaPhenotypeNode(1, "Test", trackbacks);
        assertNotNull(node.getLibraryReferences());
        assertEquals(2, node.getLibraryReferences().size());
        trackbacks.forEach(t -> assertTrue(node.getLibraryReferences().stream().filter(x -> x.startLine == t.getStartLine()
            && x.startChar == t.getStartChar()
            && x.endLine == t.getEndLine()
            && x.endChar == t.getEndChar()).findAny().isPresent()));
    }
}
