package edu.phema.quanitfy;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import edu.phema.phenotype.Phenotype;
import edu.phema.quantify.ElmQuantities;
import edu.phema.quantify.PhenotypeQuantifier;
import org.cqframework.cql.cql2elm.CqlTranslator;
import org.cqframework.cql.cql2elm.ModelManager;
import org.hl7.elm.r1.Library;
import org.hl7.fhir.r4.model.Bundle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PhenotypeQuantifierTest {
  private CqlTranslator translator;
  private ModelManager modelManager;
  private Library library;

  @BeforeEach
  public void setup() throws Exception {
    modelManager = new ModelManager();
  }

  public void runTest(String phenotypePath) throws Exception {
    FhirContext ctx = FhirContext.forR4();

    IParser parser = ctx.newJsonParser();

    Bundle bundle = parser.parseResource(Bundle.class, this.getClass().getClassLoader().getResourceAsStream(phenotypePath));

    Phenotype phenotype = new Phenotype(bundle);

    PhenotypeQuantifier quantifier = new PhenotypeQuantifier(phenotype);

    ElmQuantities quantities = quantifier.quantify();

    System.out.println(quantities.getJson());
  }

  @Test
  public void autism() throws Exception {
    runTest("phenotypes/phema-phenotype.162.autism.bundle.json");
  }

  @Test
  public void boneScanUtilization() throws Exception {
    runTest("phenotypes/phema-phenotype.1197.bone-scan-utilization.bundle.json");
  }

  @Test
  public void height() throws Exception {
    runTest("phenotypes/phema-phenotype.13.height.bundle.json");
  }

  @Test
  public void hdl() throws Exception {
    runTest("phenotypes/phema-phenotype.12.high-density-lipoproteins-hdl.bundle.json");
  }

  @Test
  public void cataracts() throws Exception {
    runTest("phenotypes/phema-phenotype.9.cataracts.bundle.json");
  }

  @Test
  public void fh() throws Exception {
    runTest("phenotypes/phema-phenotype.602.familial-hypercholesterolemia.bundle.json");
  }

  @Test
  public void uc() throws Exception {
    runTest("phenotypes/phema-phenotype.1404.urinary-incontinence.bundle.json");
  }

  @Test
  public void warfarin() throws Exception {
    runTest("phenotypes/phema-phenotype.156.warfarin-doseresponse.bundle.json");
  }
}




