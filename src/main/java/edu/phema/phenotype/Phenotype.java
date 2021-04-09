package edu.phema.phenotype;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.phema.quantify.ElmQuantifierException;
import org.cqframework.cql.cql2elm.CqlTranslator;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.hl7.elm.r1.*;
import org.hl7.elm.r1.Library;
import org.hl7.fhir.r4.model.*;

import java.lang.String;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Phenotype {
  private static final String PHENOTYPE_ENTRY_POINT = "Phenotype Entry Point";
  private static final String CQL_CONTENT_TYPE = "text/cql";
  private static final String ELM_CONTENT_TYPE = "application/elm+json";
  private static final String PHENOTYPE_ENTRY_EXPR = "Case";

  private Bundle phenotype;
  private InMemoryLibrarySourceLoader sourceLoader;

  private ModelManager modelManager;
  private LibraryManager libraryManager;

  private Map<String, Library> libraries;
  private Map<String, ValueSet> valueSets;

  public Phenotype(Bundle bundle) throws ElmQuantifierException {
    this.phenotype = bundle;
    this.sourceLoader = new InMemoryLibrarySourceLoader();
    configureTranslator();
  }

  public Phenotype(URL bundleUrl) throws Exception {
    ObjectMapper mapper = new ObjectMapper();

    this.phenotype = mapper.readValue(bundleUrl, Bundle.class);
    this.sourceLoader = new InMemoryLibrarySourceLoader();
    configureTranslator();
  }

  public void configureTranslator() throws ElmQuantifierException {
    modelManager = new ModelManager();
    libraryManager = new LibraryManager(modelManager);

    // Add all libraries in the bundle to the source loader
    List<org.hl7.fhir.r4.model.Library> libraries = phenotype.getEntry().stream()
      .filter(x -> x.getResource().getResourceType() == ResourceType.Library)
      .map(x -> (org.hl7.fhir.r4.model.Library) x.getResource())
      .collect(Collectors.toList());

    for (org.hl7.fhir.r4.model.Library library : libraries) {
      addLibrary(library);
    }

    libraryManager.setLibrarySourceLoader(this.sourceLoader);

    parseResources();
  }

  public void parseResources() throws ElmQuantifierException {
    // Parse all libraries to ELM
    this.libraries = new HashMap<>();

    List<org.hl7.fhir.r4.model.Library> libraries = phenotype.getEntry().stream()
      .filter(x -> x.getResource().getResourceType() == ResourceType.Library)
      .map(x -> (org.hl7.fhir.r4.model.Library) x.getResource())
      .collect(Collectors.toList());

    for (org.hl7.fhir.r4.model.Library library : libraries) {
      Library parsed = libraryResourceToElm(library);

      this.libraries.put(library.getName(), parsed);
    }

    // Extract value sets
    this.valueSets = new HashMap<>();

    phenotype.getEntry().stream()
      .filter(x -> x.getResource().getResourceType() == ResourceType.ValueSet)
      .map(x -> (org.hl7.fhir.r4.model.ValueSet) x.getResource())
      .forEach(v -> this.valueSets.put(v.getIdElement().getIdPart(), v));
  }

  public String getCqlFromLibrary(org.hl7.fhir.r4.model.Library library) throws ElmQuantifierException {
    List<Attachment> attachments = library.getContent();
    for (Attachment attach : attachments) {
      if (attach.getContentType().equals(CQL_CONTENT_TYPE)) {
        byte[] data = attach.getData();
        if (data != null) {
          return new String(data);
        }
      }
    }

    throw new ElmQuantifierException("No CQL found in Library resource");
  }

  public void addLibrary(org.hl7.fhir.r4.model.Library library) throws ElmQuantifierException {
    String cql = getCqlFromLibrary(library);
    this.sourceLoader.addCql(cql);
  }

  public Library libraryResourceToElm(org.hl7.fhir.r4.model.Library library) throws ElmQuantifierException {
    String cql = getCqlFromLibrary(library);

    CqlTranslator translator = CqlTranslator.fromText(cql, this.modelManager, this.libraryManager);

    return translator.toELM();
  }

  public Library getLibrary(String libraryId) throws ElmQuantifierException {
    Optional<org.hl7.fhir.r4.model.Library> maybeLibrary = phenotype.getEntry().stream()
      .filter(x -> x.getResource().getResourceType() == ResourceType.Library)
      .map(x -> (org.hl7.fhir.r4.model.Library) x.getResource())
      .filter(l -> l.getId().equals(libraryId) || l.getName().equals(libraryId))
      .findFirst();

    if (!maybeLibrary.isPresent()) {
      throw new ElmQuantifierException("No Library found with ID: " + libraryId);
    }

    return libraryResourceToElm(maybeLibrary.get());
  }

  public String getEntryPointLibraryName() throws ElmQuantifierException {
    String libraryId = getEntryPointLibraryId();

    Optional<org.hl7.fhir.r4.model.Library> maybeLibrary = phenotype.getEntry().stream()
      .filter(x -> x.getResource().getResourceType() == ResourceType.Library)
      .map(x -> (org.hl7.fhir.r4.model.Library) x.getResource())
      .filter(l -> l.getId().equals(libraryId))
      .findFirst();

    if (!maybeLibrary.isPresent()) {
      throw new ElmQuantifierException("Could not find entry point library name");
    }

    return maybeLibrary.get().getName();
  }

  public String getEntryPointLibraryId() throws ElmQuantifierException {
    try {
      List<org.hl7.fhir.r4.model.Library> libraryEntries = phenotype.getEntry().stream()
        .filter(x -> x.getResource().getResourceType() == ResourceType.Library)
        .map(x -> (org.hl7.fhir.r4.model.Library) x.getResource())
        .collect(Collectors.toList());

      Optional<Composition> compositionEntry = phenotype.getEntry().stream()
        .filter(x -> x.getResource().getResourceType() == ResourceType.Composition)
        .map(x -> (Composition) x.getResource())
        .findFirst();

      if (!compositionEntry.isPresent()) {
        throw new ElmQuantifierException("Phenotype does not have a Composition resource");
      }

      Composition composition = compositionEntry.get();
      List<Composition.SectionComponent> sections = composition.getSection();
      Optional<Composition.SectionComponent> entrySection = sections.stream()
        .filter(x -> x.getTitle().equals(PHENOTYPE_ENTRY_POINT))
        .findFirst();

      if (!entrySection.isPresent()) {
        throw new ElmQuantifierException("Not entry point found in Composition resource");
      }

      return entrySection.get().getEntry().get(0).getReference();
    } catch (Exception e) {
      throw new ElmQuantifierException("Error getting library entry point", e);
    }
  }

  public Library getEntryPoint() throws ElmQuantifierException {
    String entryPointId = getEntryPointLibraryId();

    return getLibrary(entryPointId);
  }

  ///////////////////////////////////////////////////////////////
  //
  // GET EXPRESSION DEFINITIONS
  //
  ///////////////////////////////////////////////////////////////

  public ExpressionDef getExpressionDef(Library library, String expressionName) throws ElmQuantifierException {
    Optional<ExpressionDef> maybeExpression = library.getStatements().getDef().stream()
      .filter(e -> e.getName().equals(expressionName)).findFirst();

    if (!maybeExpression.isPresent()) {
      throw new ElmQuantifierException("No '" + expressionName + "' statement found in library '" + library.getIdentifier().getId() + "'");
    }

    return maybeExpression.get();
  }

  public ExpressionDef getExpressionDef(String libraryId, String expressionName) throws ElmQuantifierException {
    Library library = getLibrary(libraryId);

    return getExpressionDef(library, expressionName);
  }

  // Get the definition for a referenced expression
  public ExpressionDef getExpressionDef(ExpressionRef ref) throws ElmQuantifierException {
    Library library;

    // If there's no library name, it must be a local expression
    if (ref.getLibraryName() == null) {
      library = getEntryPoint();

      return getExpressionDef(library, ref.getName());
    } else {
      library = getLibrary(ref.getLibraryName());

      return getExpressionDef(library, ref.getName());
    }
  }

  public ExpressionDef getEntryPointExpressionDef() throws ElmQuantifierException {
    Library library = getEntryPoint();

    // This duplicated code allows for a case insensitive entry point statement name

    Optional<ExpressionDef> maybeExpression = library.getStatements().getDef().stream()
      .filter(e -> e.getName().toUpperCase().equals(PHENOTYPE_ENTRY_EXPR.toUpperCase())).findFirst();

    if (!maybeExpression.isPresent()) {
      throw new ElmQuantifierException("No '" + PHENOTYPE_ENTRY_EXPR + "' statement found in entry point library");
    }

    return maybeExpression.get();
  }

  ///////////////////////////////////////////////////////////////
  //
  // GET EXPRESSION DEFINITIONS
  //
  ///////////////////////////////////////////////////////////////

  ///////////////////////////////////////////////////////////////
  //
  // GET FUNCTION DEFINITIONS
  //
  ///////////////////////////////////////////////////////////////

  public FunctionDef getFunctionDef(String libraryId, String functionName) throws ElmQuantifierException {
    Library library = getLibrary(libraryId);

    return getFunctionDef(library, functionName);
  }

  public FunctionDef getFunctionDef(Library library, String functionName) throws ElmQuantifierException {
    Optional<FunctionDef> maybeExpression = library.getStatements().getDef().stream()
      .filter(e -> e instanceof FunctionDef)
      .map(e -> (FunctionDef) e)
      .filter(f -> f.getName().equals(functionName))
      .findFirst();

    if (!maybeExpression.isPresent()) {
      throw new ElmQuantifierException("No '" + functionName + "' function found in library '" + library.getIdentifier().getId() + "'");
    }

    return maybeExpression.get();
  }


  ///////////////////////////////////////////////////////////////
  //
  // GET FUNCTION DEFINITIONS
  //
  ///////////////////////////////////////////////////////////////

  // Terminology helper functions
  public ValueSet getValueSet(String name, String libraryId) throws ElmQuantifierException {
    Library library = getLibrary(libraryId);

    Optional<ValueSetDef> maybeValueSetDef = library.getValueSets().getDef()
      .stream().filter(v -> v.getName().equals(name)).findFirst();

    if (!maybeValueSetDef.isPresent()) {
      throw new ElmQuantifierException("Could not find value set " + name + " in library " + libraryId);
    }

    ValueSetDef valueSetDef = maybeValueSetDef.get();

    ValueSet valueSet = this.valueSets.get(valueSetDef.getId());

    if (valueSet == null) {
      throw new ElmQuantifierException("Could not find value set " + name + " in library " + libraryId);
    }

    return valueSet;
  }

  public int countCodesInValueSet(String name, String libraryId) throws ElmQuantifierException {
    ValueSet valueSet = getValueSet(name, libraryId);

    return countCodesInValueSet(valueSet, null);
  }

  public int countCodesInValueSet(ValueSet valueSet, String system) throws ElmQuantifierException {
    int total = 0;

    for (ValueSet.ConceptSetComponent i : valueSet.getCompose().getInclude()) {
      if (i.getConcept() != null) {
        if (i.getValueSet() != null && i.getValueSet().size() != 0) {
          // follow reference if there is one
          for (CanonicalType c : i.getValueSet()) {
            ValueSet v = getValueSetFromCanonical(c.getValue());

            total += countCodesInValueSet(v, system);
          }
        } else {
          // Count the number of codes
          if (system == null || (i.getSystem() != null && i.getSystem().equals(system))) {
            total += i.getConcept().size();
          }
        }
      }
    }

    return total;
  }

  public ValueSet getValueSetFromCanonical(String canonicalUrl) throws ElmQuantifierException {
    Optional<ValueSet> maybeValueSet = this.valueSets.values().stream()
      .filter(v -> v.getUrl().equals(canonicalUrl)).findFirst();

    if (!maybeValueSet.isPresent()) {
      throw new ElmQuantifierException("Could not find value set with URL: " + canonicalUrl);
    }

    return maybeValueSet.get();
  }

  public Collection<String> getAllSystemsInValueSet(String name, String libraryId) throws ElmQuantifierException {
    ValueSet valueSet = getValueSet(name, libraryId);

    return getAllSystemsInValueSet(valueSet);
  }

  public Collection<String> getAllSystemsInValueSet(ValueSet valueSet) throws ElmQuantifierException {
    Set<String> uniqueSystems = new HashSet<>();

    for (ValueSet.ConceptSetComponent i : valueSet.getCompose().getInclude()) {
      if (i.getConcept() != null && i.getConcept().size() != 0) {
        uniqueSystems.add(i.getSystem());
      } else if (i.getValueSet() != null) {
        for (CanonicalType c : i.getValueSet()) {
          ValueSet v = getValueSetFromCanonical(c.getValue());

          uniqueSystems.addAll(getAllSystemsInValueSet(v));
        }
      }
    }

    return uniqueSystems;
  }

  public int countCodesForSystemInValueSet(String system, String valueSetName, String libraryId) throws
    ElmQuantifierException {
    ValueSet valueSet = getValueSet(valueSetName, libraryId);

    return countCodesForSystemInValueSet(valueSet, system);
  }

  public int countCodesForSystemInValueSet(ValueSet valueSet, String system) throws ElmQuantifierException {
    return countCodesInValueSet(valueSet, system);
  }
}
