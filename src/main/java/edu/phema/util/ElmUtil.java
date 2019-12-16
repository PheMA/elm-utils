package edu.phema.util;

import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.Library;

import java.util.Optional;

public class ElmUtil {

    /**
     * Returns the statement with a given name from an ELM library
     *
     * @param library The ELM library
     * @param name The name of the statement to find
     * @return The statement with the given name, if it exists
     * @throws ElmUtilException If the statement cannot be found
     */
    public ExpressionDef getStatementByName(Library library, String name) throws ElmUtilException {
        Optional<ExpressionDef> statement = library.getStatements().getDef().stream().filter(s -> s.getName().equals(name)).findFirst();

        if (statement.isPresent()) {
            return statement.get();
        } else {
            throw new ElmUtilException(String.format("Could not find statement with name '%s' in library", name));
        }
    }
}
