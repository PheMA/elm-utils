package edu.phema.translate;

import org.cqframework.cql.cql2elm.CqlTranslator;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.hl7.elm.r1.Library;

public class CqlToElmTranslator {
    public CqlToElmTranslator() {
    }

    /**
     * Create an CqlTranslator instance for
     * a given CQL string
     *
     * @param cqlString The CQL string
     * @return A CqlTranslator instance
     * @see org.cqframework.cql.cql2elm.CqlTranslator
     */
    private CqlTranslator createTranslatorFromString(String cqlString) {
        ModelManager modelManager = new ModelManager();
        return CqlTranslator.fromText(cqlString, modelManager, new LibraryManager(modelManager));
    }

    /**
     * Convert a CQL string to an ELM library
     *
     * @param cqlString The CQL string to convert
     * @return Corresponding ELM library
     * @see org.hl7.elm.r1.Library
     */
    public Library cqlToElm(String cqlString) {
        return this.createTranslatorFromString(cqlString).toELM();
    }

    /**
     * Convert CQL string to ELM JSON
     *
     * @param cqlString The CQL string to convert
     * @return Corresponding ELM JSON
     */
    public String cqlToElmJson(String cqlString) {
        return this.createTranslatorFromString(cqlString).toJson();
    }

    /**
     * Convery CQL string to ELM XML
     *
     * @param cqlString The CQL string to convert
     * @return Corresponding ELM XML
     */
    public String cqlToElmXml(String cqlString) {
        return this.createTranslatorFromString(cqlString).toXml();
    }
}
