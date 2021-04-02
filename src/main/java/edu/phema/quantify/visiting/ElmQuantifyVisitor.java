package edu.phema.quantify.visiting;

import edu.phema.quantify.ElmQuantifierException;
import edu.phema.quantify.ElmQuantities;
import edu.phema.visiting.ElmBaseStatementPostOrderTransformationVisitor;
import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.cqframework.cql.elm.visiting.ElmBaseVisitor;
import org.hl7.cql.model.DataType;
import org.hl7.elm.r1.*;

/**
 * Documentation found here: http://build.fhir.org/ig/HL7/cql/04-logicalspecification.html
 */
public class ElmQuantifyVisitor extends ElmBaseLibraryVisitor<Void, ElmQuantifyContext> {
  private boolean debug;

  public ElmQuantifyVisitor() {
    super();
  }

  public ElmQuantifyVisitor(boolean debug) {
    this.debug = debug;
  }

  //<editor-fold desc="LOGGING FUNCTIONS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // LOGGING FUNCTIONS
  //
  /////////////////////////////////////////////////////////////////////////////

  protected void debug(Object o) {
    if (debug) {
      System.out.printf("Visiting: %s%n", o.getClass().getName());
    }
  }

  protected void debug(String method) {
    if (debug) {
      System.out.printf("In method: %s%n", method);
    }
  }

  protected void debug(String method, Object o) {
    if (debug) {
      System.out.printf("In method: %s, visiting: %s%n", method, o.getClass().getName());
    }
  }

  protected void warn(String s) {
    System.out.println("WARNING: " + s);
  }
  //</editor-fold>

  //<editor-fold desc="MEASURE DERIVATION FUNCTIONS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // MEASURE DERIVATION FUNCTIONS
  //
  /////////////////////////////////////////////////////////////////////////////

  protected DataType getType(Trackable el) {
    return el.getResultType();
  }

  protected boolean isTemporal(Trackable el) throws ElmQuantifierException {
    DataType dataType = getType(el);

    if (dataType == null) {
      throw new ElmQuantifierException("Unable to get result type for element: " + el.getClass().getSimpleName());
    }

    // This obviously isn't perfect
    return dataType.toString().toUpperCase().contains("DATE") ||
      dataType.toString().toUpperCase().contains("TIME");
  }
  //</editor-fold>

  //<editor-fold desc="ABSTRACT BASE OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // ABSTRACT BASE OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitBinaryExpression(BinaryExpression elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.binaryExpression++;
    return super.visitBinaryExpression(elm, context);
  }

  @Override
  public Void visitElement(Element elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.element++;
    return super.visitElement(elm, context);
  }

  @Override
  public Void visitExpression(Expression elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.expression++;
    return super.visitExpression(elm, context);
  }

  @Override
  public Void visitOperatorExpression(OperatorExpression elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.operatorExpression++;
    return super.visitOperatorExpression(elm, context);
  }

  @Override
  public Void visitUnaryExpression(UnaryExpression elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.unaryExpression++;
    return super.visitUnaryExpression(elm, context);
  }

  @Override
  public Void visitTernaryExpression(TernaryExpression elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.ternaryExpression++;
    return super.visitTernaryExpression(elm, context);
  }

  @Override
  public Void visitNaryExpression(NaryExpression elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.naryExpression++;
    return super.visitNaryExpression(elm, context);
  }

  @Override
  public Void visitTypeSpecifier(TypeSpecifier elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.typeSpecifier++;
    return super.visitTypeSpecifier(elm, context);
  }

  @Override
  public Void visitAggregateExpression(AggregateExpression elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.aggregateExpression++;
    return super.visitAggregateExpression(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="1. SIMPLE VALUES">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 1. SIMPLE VALUES
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitLiteral(Literal elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLiteralCounts.literal++;

    // PhEMA counts
    context.getQuantities().phemaLiteralCounts.total++;
    String type = elm.getValueType().getLocalPart();
    context.getQuantities().phemaLiteralCounts.types.add(type);

    return super.visitLiteral(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="2. STRUCTURED VALUES">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 2. STRUCTURED VALUES
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitTuple(Tuple elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLiteralCounts.tuple++;
    return super.visitTuple(elm, context);
  }

  @Override
  public Void visitTupleElement(TupleElement elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLiteralCounts.tupleElement++;
    return super.visitTupleElement(elm, context);
  }

  @Override
  public Void visitTupleElementDefinition(TupleElementDefinition elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLiteralCounts.tupleElementDefinition++;
    return super.visitTupleElementDefinition(elm, context);
  }

  @Override
  public Void visitInstance(Instance elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLiteralCounts.instance++;
    return super.visitInstance(elm, context);
  }

  @Override
  public Void visitInstanceElement(InstanceElement elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLiteralCounts.instanceElement++;
    return super.visitInstanceElement(elm, context);
  }

  @Override
  public Void visitProperty(Property elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLiteralCounts.property++;
    return super.visitProperty(elm, context);
  }

  @Override
  public Void visitSearch(Search elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLiteralCounts.search++;
    return super.visitSearch(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="3. CLINICAL VALUES">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 3. CLINICAL VALUES
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitCodeSystemDef(CodeSystemDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.codeSystemDef++;
    return super.visitCodeSystemDef(elm, context);
  }

  @Override
  public Void visitValueSetDef(ValueSetDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.valueSetDef++;
    return super.visitValueSetDef(elm, context);
  }

  @Override
  public Void visitCodeSystemRef(CodeSystemRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.codeSystemRef++;
    return super.visitCodeSystemRef(elm, context);
  }

  @Override
  public Void visitValueSetRef(ValueSetRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.valueSetRef++;
    return super.visitValueSetRef(elm, context);
  }

  @Override
  public Void visitCode(Code elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.code++;
    return super.visitCode(elm, context);
  }

  @Override
  public Void visitConcept(Concept elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.concept++;
    return super.visitConcept(elm, context);
  }

  @Override
  public Void visitQuantity(Quantity elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.quantity++;

    // PhEMA Counts
    context.getQuantities().phemaLiteralCounts.total++;
    context.getQuantities().phemaLiteralCounts.types.add("Quantity (" + elm.getUnit() + ")");

    return super.visitQuantity(elm, context);
  }

  @Override
  public Void visitRatio(Ratio elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.ratio++;

    // PhEMA Counts
    context.getQuantities().phemaLiteralCounts.total++;
    context.getQuantities().phemaLiteralCounts.types.add("Ratio (" + elm.getNumerator().getUnit() + "/" + elm.getDenominator().getUnit() + ")");

    return super.visitRatio(elm, context);
  }

  @Override
  public Void visitCodeDef(CodeDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.codeDef++;
    return super.visitCodeDef(elm, context);
  }

  @Override
  public Void visitConceptDef(ConceptDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.conceptDef++;
    return super.visitConceptDef(elm, context);
  }

  @Override
  public Void visitCodeRef(CodeRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.codeRef++;
    return super.visitCodeRef(elm, context);
  }

  @Override
  public Void visitConceptRef(ConceptRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.conceptRef++;
    return super.visitConceptRef(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="4. TYPE SPECIFIERS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 4. TYPE SPECIFIERS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitNamedTypeSpecifier(NamedTypeSpecifier elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeSpecifierCounts.namedTypeSpecifier++;
    return super.visitNamedTypeSpecifier(elm, context);
  }

  @Override
  public Void visitIntervalTypeSpecifier(IntervalTypeSpecifier elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeSpecifierCounts.intervalTypeSpecifier++;
    return super.visitIntervalTypeSpecifier(elm, context);
  }

  @Override
  public Void visitListTypeSpecifier(ListTypeSpecifier elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeSpecifierCounts.listTypeSpecifier++;
    return super.visitListTypeSpecifier(elm, context);
  }

  @Override
  public Void visitTupleTypeSpecifier(TupleTypeSpecifier elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeSpecifierCounts.tupleTypeSpecifier++;
    return super.visitTupleTypeSpecifier(elm, context);
  }

  @Override
  public Void visitChoiceTypeSpecifier(ChoiceTypeSpecifier elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeSpecifierCounts.choiceTypeSpecifier++;
    return super.visitChoiceTypeSpecifier(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="5/6. LIBRARIES + DATA MODEL">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 5/6. LIBRARIES + DATA MODEL
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitLibrary(Library elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.library++;
    return super.visitLibrary(elm, context);
  }

  @Override
  public Void visitUsingDef(UsingDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.usingDef++;
    return super.visitUsingDef(elm, context);
  }

  @Override
  public Void visitIncludeDef(IncludeDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.includeDef++;
    return super.visitIncludeDef(elm, context);
  }

  @Override
  public Void visitContextDef(ContextDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.contextDef++;
    return super.visitContextDef(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="7. PARAMETERS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 7. PARAMETERS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitParameterDef(ParameterDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.parameterDef++;
    return super.visitParameterDef(elm, context);
  }

  @Override
  public Void visitParameterRef(ParameterRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.parameterRef++;
    return super.visitParameterRef(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="9. REUSING LOGIC">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 9. REUSING LOGIC
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitExpressionDef(ExpressionDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.expressionDef++;
    return super.visitExpressionDef(elm, context);
  }

  @Override
  public Void visitFunctionDef(FunctionDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.functionDef++;
    return super.visitFunctionDef(elm, context);
  }

  @Override
  public Void visitFunctionRef(FunctionRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.functionRef++;
    return super.visitFunctionRef(elm, context);
  }

  @Override
  public Void visitOperandDef(OperandDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.operandDef++;
    // Used in function signature
    return super.visitOperandDef(elm, context);
  }

  @Override
  public Void visitExpressionRef(ExpressionRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.expressionRef++;

    // Calling another define statement

    try {
      // Push the current library ID here so we can know where to
      // find refs later that have a libraryName of null
      if (elm.getLibraryName() != null) {
        context.pushLibrary(elm.getLibraryName());
      }

      ExpressionDef def = context.getPhenotype().getExpressionDef(context.currentLibrary(), elm.getName());

      visitExpressionDef(def, context);

      if (elm.getLibraryName() != null) {
        context.popLibrary();
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    return super.visitExpressionRef(elm, context);
  }

  @Override
  public Void visitOperandRef(OperandRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.operandRef++;
    // Referring to a function argument
    return super.visitOperandRef(elm, context);
  }

  @Override
  public Void visitIdentifierRef(IdentifierRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.identifierRef++;
    // Referring to an identifier
    return super.visitIdentifierRef(elm, context);
  }

  @Override
  public Void visitAccessModifier(AccessModifier elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.accessModifier++;
    return super.visitAccessModifier(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="10/11. QUERIES + RETRIEVE">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 10/11. QUERIES + RETRIEVE
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitAliasedQuerySource(AliasedQuerySource elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.aliasedQuerySource++;
    return super.visitAliasedQuerySource(elm, context);
  }

  @Override
  public Void visitLetClause(LetClause elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.letClause++;
    return super.visitLetClause(elm, context);
  }

  @Override
  public Void visitRelationshipClause(RelationshipClause elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.relationshipClause++;
    return super.visitRelationshipClause(elm, context);
  }

  @Override
  public Void visitWith(With elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.with++;
    return super.visitWith(elm, context);
  }

  @Override
  public Void visitWithout(Without elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.without++;
    return super.visitWithout(elm, context);
  }

  @Override
  public Void visitSortByItem(SortByItem elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.sortByItem++;
    return super.visitSortByItem(elm, context);
  }

  @Override
  public Void visitByDirection(ByDirection elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.byDirection++;
    return super.visitByDirection(elm, context);
  }

  @Override
  public Void visitByColumn(ByColumn elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.byColumn++;
    return super.visitByColumn(elm, context);
  }

  @Override
  public Void visitByExpression(ByExpression elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.byExpression++;
    return super.visitByExpression(elm, context);
  }

  @Override
  public Void visitSortClause(SortClause elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.sortClause++;
    return super.visitSortClause(elm, context);
  }

  @Override
  public Void visitReturnClause(ReturnClause elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.returnClause++;
    return super.visitReturnClause(elm, context);
  }

  @Override
  public Void visitQuery(Query elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.query++;
    return super.visitQuery(elm, context);
  }

  @Override
  public Void visitAliasRef(AliasRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.aliasRef++;
    return super.visitAliasRef(elm, context);
  }

  @Override
  public Void visitQueryLetRef(QueryLetRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.queryLetRef++;
    return super.visitQueryLetRef(elm, context);
  }

  @Override
  public Void visitRetrieve(Retrieve elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.retrieve++;

    context.getQuantities().phemaQueryCounts.retrieveTotal++;
    context.getQuantities().phemaQueryCounts.dataSources.add(elm.getDataType().toString());

    return super.visitRetrieve(elm, context);
  }

  @Override
  public Void visitCodeFilterElement(CodeFilterElement elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.codeFilterElement++;
    return super.visitCodeFilterElement(elm, context);
  }

  @Override
  public Void visitDateFilterElement(DateFilterElement elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.dateFilterElement++;
    return super.visitDateFilterElement(elm, context);
  }

  @Override
  public Void visitOtherFilterElement(OtherFilterElement elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.otherFilterElement++;
    return super.visitOtherFilterElement(elm, context);
  }

  @Override
  public Void visitIncludeElement(IncludeElement elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.includeElement++;
    return super.visitIncludeElement(elm, context);
  }

  @Override
  public Void visitAggregateClause(AggregateClause elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.aggregateClause++;
    return super.visitAggregateClause(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="12. COMPARISON OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 12. COMPARISON OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitEqual(Equal elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmComparisonCounts.equal++;
    return super.visitEqual(elm, context);
  }

  @Override
  public Void visitEquivalent(Equivalent elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmComparisonCounts.equivalent++;
    return super.visitEquivalent(elm, context);
  }

  @Override
  public Void visitNotEqual(NotEqual elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmComparisonCounts.notEqual++;
    return super.visitNotEqual(elm, context);
  }

  @Override
  public Void visitLess(Less elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmComparisonCounts.less++;
    return super.visitLess(elm, context);
  }

  @Override
  public Void visitGreater(Greater elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmComparisonCounts.greater++;
    return super.visitGreater(elm, context);
  }

  @Override
  public Void visitLessOrEqual(LessOrEqual elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmComparisonCounts.lessOrEqual++;
    return super.visitLessOrEqual(elm, context);
  }

  @Override
  public Void visitGreaterOrEqual(GreaterOrEqual elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmComparisonCounts.greaterOrEqual++;
    return super.visitGreaterOrEqual(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="13. LOGICAL OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 13. LOGICAL OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitAnd(And elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLogicalCounts.and++;
    return super.visitAnd(elm, context);
  }

  @Override
  public Void visitOr(Or elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLogicalCounts.or++;
    return super.visitOr(elm, context);
  }

  @Override
  public Void visitXor(Xor elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLogicalCounts.xor++;
    return super.visitXor(elm, context);
  }

  @Override
  public Void visitNot(Not elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLogicalCounts.not++;
    return super.visitNot(elm, context);
  }

  @Override
  public Void visitImplies(Implies elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLogicalCounts.implies++;
    return super.visitImplies(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="14. NULLOLOGICAL OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 14. NULLOLOGICAL OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitNull(Null elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmNullCounts._null++;
    return super.visitNull(elm, context);
  }

  @Override
  public Void visitIsNull(IsNull elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmNullCounts.isNull++;
    return super.visitIsNull(elm, context);
  }

  @Override
  public Void visitIsTrue(IsTrue elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmNullCounts.isTrue++;
    return super.visitIsTrue(elm, context);
  }

  @Override
  public Void visitIsFalse(IsFalse elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmNullCounts.isFalse++;
    return super.visitIsFalse(elm, context);
  }

  @Override
  public Void visitCoalesce(Coalesce elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmNullCounts.coalesce++;
    return super.visitCoalesce(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="15. CONDITIONAL OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 15. CONDITIONAL OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitIf(If elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmConditionalCounts._if++;
    return super.visitIf(elm, context);
  }

  @Override
  public Void visitCaseItem(CaseItem elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmConditionalCounts.caseItem++;
    return super.visitCaseItem(elm, context);
  }

  @Override
  public Void visitCase(Case elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmConditionalCounts._case++;
    return super.visitCase(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="16. ARITHMETIC OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 16. ARITHMETIC OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitAdd(Add elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.add++;

    return super.visitAdd(elm, context);
  }

  @Override
  public Void visitSubtract(Subtract elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.subtract++;
    return super.visitSubtract(elm, context);
  }

  @Override
  public Void visitMultiply(Multiply elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.multiply++;
    return super.visitMultiply(elm, context);
  }

  @Override
  public Void visitDivide(Divide elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.divide++;
    return super.visitDivide(elm, context);
  }

  @Override
  public Void visitTruncatedDivide(TruncatedDivide elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.truncatedDivide++;
    return super.visitTruncatedDivide(elm, context);
  }

  @Override
  public Void visitModulo(Modulo elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.modulo++;
    return super.visitModulo(elm, context);
  }

  @Override
  public Void visitCeiling(Ceiling elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.ceiling++;
    return super.visitCeiling(elm, context);
  }

  @Override
  public Void visitFloor(Floor elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.floor++;
    return super.visitFloor(elm, context);
  }

  @Override
  public Void visitTruncate(Truncate elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.truncate++;
    return super.visitTruncate(elm, context);
  }

  @Override
  public Void visitAbs(Abs elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.abs++;
    return super.visitAbs(elm, context);
  }

  @Override
  public Void visitNegate(Negate elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.negate++;
    return super.visitNegate(elm, context);
  }

  @Override
  public Void visitRound(Round elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.round++;
    return super.visitRound(elm, context);
  }

  @Override
  public Void visitLn(Ln elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.ln++;
    return super.visitLn(elm, context);
  }

  @Override
  public Void visitExp(Exp elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.exp++;
    return super.visitExp(elm, context);
  }

  @Override
  public Void visitLog(Log elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.log++;
    return super.visitLog(elm, context);
  }

  @Override
  public Void visitPower(Power elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.power++;
    return super.visitPower(elm, context);
  }

  @Override
  public Void visitSuccessor(Successor elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.successor++;
    return super.visitSuccessor(elm, context);
  }

  @Override
  public Void visitPredecessor(Predecessor elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.predecessor++;
    return super.visitPredecessor(elm, context);
  }

  @Override
  public Void visitMinValue(MinValue elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.minValue++;
    return super.visitMinValue(elm, context);
  }

  @Override
  public Void visitMaxValue(MaxValue elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.maxValue++;
    return super.visitMaxValue(elm, context);
  }

  @Override
  public Void visitPrecision(Precision elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.precision++;
    return super.visitPrecision(elm, context);
  }

  @Override
  public Void visitLowBoundary(LowBoundary elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.lowBoundary++;
    return super.visitLowBoundary(elm, context);
  }

  @Override
  public Void visitHighBoundary(HighBoundary elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.highBoundary++;
    return super.visitHighBoundary(elm, context);
  }

  @Override
  public Void visitTotal(Total elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.total++;
    // not sure where this belongs
    return super.visitTotal(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="17. STRING OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 17. STRING OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitConcatenate(Concatenate elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.concatenate++;
    return super.visitConcatenate(elm, context);
  }

  @Override
  public Void visitCombine(Combine elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.combine++;
    return super.visitCombine(elm, context);
  }

  @Override
  public Void visitSplit(Split elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.split++;
    return super.visitSplit(elm, context);
  }

  @Override
  public Void visitLength(Length elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.length++;
    return super.visitLength(elm, context);
  }

  @Override
  public Void visitUpper(Upper elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.upper++;
    return super.visitUpper(elm, context);
  }

  @Override
  public Void visitLower(Lower elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.lower++;
    return super.visitLower(elm, context);
  }

  @Override
  public Void visitIndexer(Indexer elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.indexer++;
    return super.visitIndexer(elm, context);
  }

  @Override
  public Void visitPositionOf(PositionOf elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.positionOf++;
    return super.visitPositionOf(elm, context);
  }

  @Override
  public Void visitSubstring(Substring elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.substring++;
    return super.visitSubstring(elm, context);
  }

  @Override
  public Void visitSplitOnMatches(SplitOnMatches elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.splitOnMatches++;
    return super.visitSplitOnMatches(elm, context);
  }

  @Override
  public Void visitLastPositionOf(LastPositionOf elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.lastPositionOf++;
    return super.visitLastPositionOf(elm, context);
  }

  @Override
  public Void visitStartsWith(StartsWith elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.startsWith++;
    return super.visitStartsWith(elm, context);
  }

  @Override
  public Void visitEndsWith(EndsWith elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.endsWith++;
    return super.visitEndsWith(elm, context);
  }

  @Override
  public Void visitMatches(Matches elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.matches++;
    return super.visitMatches(elm, context);
  }

  @Override
  public Void visitReplaceMatches(ReplaceMatches elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.replaceMatches++;
    return super.visitReplaceMatches(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="18. DATE AND TIME OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 18. DATE AND TIME OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitDurationBetween(DurationBetween elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.durationBetween++;
    return super.visitDurationBetween(elm, context);
  }

  @Override
  public Void visitDifferenceBetween(DifferenceBetween elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.differenceBetween++;
    return super.visitDifferenceBetween(elm, context);
  }

  @Override
  public Void visitDateFrom(DateFrom elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.dateFrom++;
    return super.visitDateFrom(elm, context);
  }

  @Override
  public Void visitTimeFrom(TimeFrom elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.timeFrom++;
    return super.visitTimeFrom(elm, context);
  }

  @Override
  public Void visitTimezoneOffsetFrom(TimezoneOffsetFrom elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.timezoneOffsetFrom++;
    return super.visitTimezoneOffsetFrom(elm, context);
  }

  @Override
  public Void visitDateTimeComponentFrom(DateTimeComponentFrom elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.dateTimeComponentFrom++;
    return super.visitDateTimeComponentFrom(elm, context);
  }

  @Override
  public Void visitTimeOfDay(TimeOfDay elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.timeOfDay++;
    return super.visitTimeOfDay(elm, context);
  }

  @Override
  public Void visitToday(Today elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.today++;
    return super.visitToday(elm, context);
  }

  @Override
  public Void visitNow(Now elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.now++;
    return super.visitNow(elm, context);
  }

  @Override
  public Void visitDateTime(DateTime elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.dateTime++;

    // PhEMA Counts
    context.getQuantities().phemaLiteralCounts.total++;
    context.getQuantities().phemaLiteralCounts.types.add("DateTime");

    // Don't descend/evaluate subexpressions
    return null;
  }

  @Override
  public Void visitTime(Time elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.time++;
    context.getQuantities().phemaLiteralCounts.total++;
    context.getQuantities().phemaLiteralCounts.types.add("Time");

    // Don't descend/evaluate subexpressions
    return null;
  }

  @Override
  public Void visitSameAs(SameAs elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.sameAs++;
    return super.visitSameAs(elm, context);
  }

  @Override
  public Void visitSameOrBefore(SameOrBefore elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.sameOrBefore++;
    return super.visitSameOrBefore(elm, context);
  }

  @Override
  public Void visitSameOrAfter(SameOrAfter elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.sameOrAfter++;
    return super.visitSameOrAfter(elm, context);
  }

  @Override
  public Void visitTimezoneFrom(TimezoneFrom elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.timezoneFrom++;
    return super.visitTimezoneFrom(elm, context);
  }

  @Override
  public Void visitDate(Date elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.date++;
    context.getQuantities().phemaLiteralCounts.total++;
    context.getQuantities().phemaLiteralCounts.types.add("Date");

    // Don't descend/evaluate subexpressions
    return null;
  }
  //</editor-fold>

  //<editor-fold desc="19. INTERVAL OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 19. INTERVAL OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitInterval(Interval elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.interval++;

    // Push something here to capture element type

    try {
      boolean isTemporal = isTemporal(elm);
    } catch (Exception e) {

    }

    return super.visitInterval(elm, context);
  }

  @Override
  public Void visitWidth(Width elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.width++;
    return super.visitWidth(elm, context);
  }

  @Override
  public Void visitStart(Start elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.start++;
    return super.visitStart(elm, context);
  }

  @Override
  public Void visitEnd(End elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.end++;
    return super.visitEnd(elm, context);
  }

  @Override
  public Void visitContains(Contains elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.contains++;
    return super.visitContains(elm, context);
  }

  @Override
  public Void visitProperContains(ProperContains elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.properContains++;
    return super.visitProperContains(elm, context);
  }

  @Override
  public Void visitIn(In elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.in++;

    try {
      boolean isTemporal = isTemporal(elm);
    } catch (Exception e) {

    }

    return super.visitIn(elm, context);
  }

  @Override
  public Void visitProperIn(ProperIn elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.properIn++;
    return super.visitProperIn(elm, context);
  }

  @Override
  public Void visitIncludes(Includes elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.includes++;
    return super.visitIncludes(elm, context);
  }

  @Override
  public Void visitIncludedIn(IncludedIn elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.includedIn++;
    return super.visitIncludedIn(elm, context);
  }

  @Override
  public Void visitProperIncludes(ProperIncludes elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.properIncludes++;
    return super.visitProperIncludes(elm, context);
  }

  @Override
  public Void visitProperIncludedIn(ProperIncludedIn elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.properIncludedIn++;
    return super.visitProperIncludedIn(elm, context);
  }

  @Override
  public Void visitBefore(Before elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.before++;
    return super.visitBefore(elm, context);
  }

  @Override
  public Void visitAfter(After elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.after++;
    return super.visitAfter(elm, context);
  }

  @Override
  public Void visitMeets(Meets elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.meets++;
    return super.visitMeets(elm, context);
  }

  @Override
  public Void visitMeetsBefore(MeetsBefore elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.meetsBefore++;
    return super.visitMeetsBefore(elm, context);
  }

  @Override
  public Void visitMeetsAfter(MeetsAfter elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.meetsAfter++;
    return super.visitMeetsAfter(elm, context);
  }

  @Override
  public Void visitOverlaps(Overlaps elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.overlaps++;
    return super.visitOverlaps(elm, context);
  }

  @Override
  public Void visitOverlapsBefore(OverlapsBefore elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.overlapsBefore++;
    return super.visitOverlapsBefore(elm, context);
  }

  @Override
  public Void visitOverlapsAfter(OverlapsAfter elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.overlapsAfter++;
    return super.visitOverlapsAfter(elm, context);
  }

  @Override
  public Void visitStarts(Starts elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.starts++;
    return super.visitStarts(elm, context);
  }

  @Override
  public Void visitEnds(Ends elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.ends++;
    return super.visitEnds(elm, context);
  }

  @Override
  public Void visitCollapse(Collapse elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.collapse++;
    return super.visitCollapse(elm, context);
  }

  @Override
  public Void visitUnion(Union elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.union++;
    return super.visitUnion(elm, context);
  }

  @Override
  public Void visitIntersect(Intersect elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.intersect++;
    return super.visitIntersect(elm, context);
  }

  @Override
  public Void visitExcept(Except elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.except++;
    return super.visitExcept(elm, context);
  }

  @Override
  public Void visitSize(Size elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.size++;
    return super.visitSize(elm, context);
  }

  @Override
  public Void visitPointFrom(PointFrom elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.pointFrom++;
    return super.visitPointFrom(elm, context);
  }

  @Override
  public Void visitExpand(Expand elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.expand++;
    return super.visitExpand(elm, context);
  }

  //</editor-fold>

  //<editor-fold desc="20. LIST OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 20. LIST OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitList(List elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.list++;
    return super.visitList(elm, context);
  }

  @Override
  public Void visitExists(Exists elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.exists++;
    return super.visitExists(elm, context);
  }

  @Override
  public Void visitTimes(Times elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.times++;
    return super.visitTimes(elm, context);
  }

  @Override
  public Void visitFilter(Filter elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.filter++;
    return super.visitFilter(elm, context);
  }

  @Override
  public Void visitFirst(First elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.first++;
    return super.visitFirst(elm, context);
  }

  @Override
  public Void visitLast(Last elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.last++;
    return super.visitLast(elm, context);
  }

  @Override
  public Void visitIndexOf(IndexOf elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.indexOf++;
    return super.visitIndexOf(elm, context);
  }

  @Override
  public Void visitFlatten(Flatten elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.flatten++;
    return super.visitFlatten(elm, context);
  }

  @Override
  public Void visitSort(Sort elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.sort++;
    return super.visitSort(elm, context);
  }

  @Override
  public Void visitForEach(ForEach elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.forEach++;
    return super.visitForEach(elm, context);
  }

  @Override
  public Void visitDistinct(Distinct elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.distinct++;
    return super.visitDistinct(elm, context);
  }

  @Override
  public Void visitCurrent(Current elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.current++;
    return super.visitCurrent(elm, context);
  }

  @Override
  public Void visitSingletonFrom(SingletonFrom elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.singletonFrom++;
    return super.visitSingletonFrom(elm, context);
  }

  // not sure where the following belong

  @Override
  public Void visitSlice(Slice elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.slice++;
    return super.visitSlice(elm, context);
  }

  @Override
  public Void visitRepeat(Repeat elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.repeat++;
    return super.visitRepeat(elm, context);
  }

  @Override
  public Void visitIteration(Iteration elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.iteration++;
    return super.visitIteration(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="21. AGGREGATE OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 21. AGGREGATE OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////
  @Override
  public Void visitAggregate(Aggregate elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.aggregate++;
    return super.visitAggregate(elm, context);
  }

  @Override
  public Void visitProduct(Product elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.product++;
    return super.visitProduct(elm, context);
  }

  @Override
  public Void visitGeometricMean(GeometricMean elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.geometricMean++;
    return super.visitGeometricMean(elm, context);
  }

  @Override
  public Void visitCount(Count elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.count++;
    return super.visitCount(elm, context);
  }

  @Override
  public Void visitSum(Sum elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.sum++;
    return super.visitSum(elm, context);
  }

  @Override
  public Void visitMin(Min elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.min++;
    return super.visitMin(elm, context);
  }

  @Override
  public Void visitMax(Max elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.max++;
    return super.visitMax(elm, context);
  }

  @Override
  public Void visitAvg(Avg elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.avg++;
    return super.visitAvg(elm, context);
  }

  @Override
  public Void visitMedian(Median elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.median++;
    return super.visitMedian(elm, context);
  }

  @Override
  public Void visitMode(Mode elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.mode++;
    return super.visitMode(elm, context);
  }

  @Override
  public Void visitVariance(Variance elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.variance++;
    return super.visitVariance(elm, context);
  }

  @Override
  public Void visitPopulationVariance(PopulationVariance elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.populationVariance++;
    return super.visitPopulationVariance(elm, context);
  }

  @Override
  public Void visitStdDev(StdDev elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.stdDev++;
    return super.visitStdDev(elm, context);
  }

  @Override
  public Void visitPopulationStdDev(PopulationStdDev elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.populationStdDev++;
    return super.visitPopulationStdDev(elm, context);
  }

  @Override
  public Void visitAllTrue(AllTrue elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.allTrue++;
    return super.visitAllTrue(elm, context);
  }

  @Override
  public Void visitAnyTrue(AnyTrue elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.anyTrue++;
    return super.visitAnyTrue(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="22. TYPE OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 22. TYPE OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitIs(Is elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.is++;
    return super.visitIs(elm, context);
  }

  @Override
  public Void visitAs(As elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.as++;
    return super.visitAs(elm, context);
  }

  @Override
  public Void visitConvert(Convert elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convert++;
    return super.visitConvert(elm, context);
  }

  @Override
  public Void visitToBoolean(ToBoolean elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toBoolean++;
    return super.visitToBoolean(elm, context);
  }

  @Override
  public Void visitToConcept(ToConcept elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toConcept++;
    return super.visitToConcept(elm, context);
  }

  @Override
  public Void visitToDateTime(ToDateTime elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toDateTime++;
    return super.visitToDateTime(elm, context);
  }

  @Override
  public Void visitToDecimal(ToDecimal elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toDecimal++;
    return super.visitToDecimal(elm, context);
  }

  @Override
  public Void visitToInteger(ToInteger elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toInteger++;
    return super.visitToInteger(elm, context);
  }

  @Override
  public Void visitToQuantity(ToQuantity elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toQuantity++;
    return super.visitToQuantity(elm, context);
  }

  @Override
  public Void visitToString(ToString elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toString++;
    return super.visitToString(elm, context);
  }

  @Override
  public Void visitToTime(ToTime elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toTime++;
    return super.visitToTime(elm, context);
  }

  @Override
  public Void visitCanConvert(CanConvert elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.canConvert++;
    return super.visitCanConvert(elm, context);
  }

  @Override
  public Void visitConvertsToBoolean(ConvertsToBoolean elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToBoolean++;
    return super.visitConvertsToBoolean(elm, context);
  }

  @Override
  public Void visitToChars(ToChars elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toChars++;
    return super.visitToChars(elm, context);
  }

  @Override
  public Void visitConvertsToDate(ConvertsToDate elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToDate++;
    return super.visitConvertsToDate(elm, context);
  }

  @Override
  public Void visitToDate(ToDate elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toDate++;
    return super.visitToDate(elm, context);
  }

  @Override
  public Void visitConvertsToDateTime(ConvertsToDateTime elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToDateTime++;
    return super.visitConvertsToDateTime(elm, context);
  }

  @Override
  public Void visitConvertsToLong(ConvertsToLong elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToLong++;
    return super.visitConvertsToLong(elm, context);
  }

  @Override
  public Void visitToLong(ToLong elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toLong++;
    return super.visitToLong(elm, context);
  }

  @Override
  public Void visitConvertsToDecimal(ConvertsToDecimal elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToDecimal++;
    return super.visitConvertsToDecimal(elm, context);
  }

  @Override
  public Void visitConvertsToInteger(ConvertsToInteger elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToInteger++;
    return super.visitConvertsToInteger(elm, context);
  }

  @Override
  public Void visitToList(ToList elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toList++;
    return super.visitToList(elm, context);
  }

  @Override
  public Void visitConvertQuantity(ConvertQuantity elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertQuantity++;
    return super.visitConvertQuantity(elm, context);
  }

  @Override
  public Void visitCanConvertQuantity(CanConvertQuantity elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.canConvertQuantity++;
    return super.visitCanConvertQuantity(elm, context);
  }

  @Override
  public Void visitConvertsToQuantity(ConvertsToQuantity elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToQuantity++;
    return super.visitConvertsToQuantity(elm, context);
  }

  @Override
  public Void visitConvertsToRatio(ConvertsToRatio elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToRatio++;
    return super.visitConvertsToRatio(elm, context);
  }

  @Override
  public Void visitToRatio(ToRatio elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toRatio++;
    return super.visitToRatio(elm, context);
  }

  @Override
  public Void visitConvertsToString(ConvertsToString elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToString++;
    return super.visitConvertsToString(elm, context);
  }

  @Override
  public Void visitConvertsToTime(ConvertsToTime elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToTime++;
    return super.visitConvertsToTime(elm, context);
  }

  @Override
  public Void visitChildren(Children elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.children++;
    return super.visitChildren(elm, context);
  }

  @Override
  public Void visitDescendents(Descendents elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.descendents++;
    return super.visitDescendents(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="23. CLINICAL OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 23. CLINICAL OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitInCodeSystem(InCodeSystem elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTerminologyCounts.inCodeSystem++;
    return super.visitInCodeSystem(elm, context);
  }

  @Override
  public Void visitInValueSet(InValueSet elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTerminologyCounts.inValueSet++;
    return super.visitInValueSet(elm, context);
  }

  @Override
  public Void visitCalculateAge(CalculateAge elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTerminologyCounts.calculateAge++;
    return super.visitCalculateAge(elm, context);
  }

  @Override
  public Void visitCalculateAgeAt(CalculateAgeAt elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTerminologyCounts.calculateAgeAt++;
    return super.visitCalculateAgeAt(elm, context);
  }

  @Override
  public Void visitAnyInCodeSystem(AnyInCodeSystem elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTerminologyCounts.anyInCodeSystem++;
    return super.visitAnyInCodeSystem(elm, context);
  }

  @Override
  public Void visitAnyInValueSet(AnyInValueSet elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTerminologyCounts.anyInValueSet++;
    return super.visitAnyInValueSet(elm, context);
  }

  @Override
  public Void visitSubsumes(Subsumes elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTerminologyCounts.subsumes++;
    return super.visitSubsumes(elm, context);
  }

  @Override
  public Void visitSubsumedBy(SubsumedBy elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTerminologyCounts.subsumedBy++;
    return super.visitSubsumedBy(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="24. ERRORS AND MESSAGES">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 24. ERRORS AND MESSAGES
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitMessage(Message elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmErrorCounts.message++;
    return super.visitMessage(elm, context);
  }
  //</editor-fold>
}