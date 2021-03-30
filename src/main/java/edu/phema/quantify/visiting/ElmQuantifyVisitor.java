package edu.phema.quantify.visiting;

import edu.phema.quantify.ElmQuantifierException;
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

  protected void warn(String s) {
    // Mute warnings for now
    // System.out.println(s);
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
    context.getQuantities().expressionCounts.binary++;
    return super.visitBinaryExpression(elm, context);
  }

  @Override
  public Void visitElement(Element elm, ElmQuantifyContext context) {
    return super.visitElement(elm, context);
  }

  @Override
  public Void visitExpression(Expression elm, ElmQuantifyContext context) {
    return super.visitExpression(elm, context);
  }

  @Override
  public Void visitOperatorExpression(OperatorExpression elm, ElmQuantifyContext context) {
    return super.visitOperatorExpression(elm, context);
  }

  @Override
  public Void visitUnaryExpression(UnaryExpression elm, ElmQuantifyContext context) {
    context.getQuantities().expressionCounts.unary++;
    return super.visitUnaryExpression(elm, context);
  }

  @Override
  public Void visitTernaryExpression(TernaryExpression elm, ElmQuantifyContext context) {
    context.getQuantities().expressionCounts.ternary++;
    return super.visitTernaryExpression(elm, context);
  }

  @Override
  public Void visitNaryExpression(NaryExpression elm, ElmQuantifyContext context) {
    context.getQuantities().expressionCounts.nary++;
    return super.visitNaryExpression(elm, context);
  }

  @Override
  public Void visitTypeSpecifier(TypeSpecifier elm, ElmQuantifyContext context) {
    return super.visitTypeSpecifier(elm, context);
  }

  @Override
  public Void visitAggregateExpression(AggregateExpression elm, ElmQuantifyContext context) {
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
    context.getQuantities().literalCounts.total++;

    String type = elm.getValueType().getLocalPart();
    context.getQuantities().literalCounts.types.add(type);

    // Evaluating a literal
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
    return super.visitTuple(elm, context);
  }

  @Override
  public Void visitTupleElement(TupleElement elm, ElmQuantifyContext context) {
    return super.visitTupleElement(elm, context);
  }

  @Override
  public Void visitTupleElementDefinition(TupleElementDefinition elm, ElmQuantifyContext context) {
    return super.visitTupleElementDefinition(elm, context);
  }

  @Override
  public Void visitInstance(Instance elm, ElmQuantifyContext context) {
    return super.visitInstance(elm, context);
  }

  @Override
  public Void visitInstanceElement(InstanceElement elm, ElmQuantifyContext context) {
    return super.visitInstanceElement(elm, context);
  }

  @Override
  public Void visitProperty(Property elm, ElmQuantifyContext context) {
    return super.visitProperty(elm, context);
  }

  @Override
  public Void visitSearch(Search elm, ElmQuantifyContext context) {
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
    context.getQuantities().clinicalValueCounts.codeSystemDefinition++;

    return super.visitCodeSystemDef(elm, context);
  }

  @Override
  public Void visitValueSetDef(ValueSetDef elm, ElmQuantifyContext context) {
    context.getQuantities().clinicalValueCounts.valueSetDefinition++;

    return super.visitValueSetDef(elm, context);
  }

  @Override
  public Void visitCodeSystemRef(CodeSystemRef elm, ElmQuantifyContext context) {
    context.getQuantities().clinicalValueCounts.codeSystemReference++;

    return super.visitCodeSystemRef(elm, context);
  }

  @Override
  public Void visitValueSetRef(ValueSetRef elm, ElmQuantifyContext context) {
    context.getQuantities().clinicalValueCounts.valueSetReference++;

    return super.visitValueSetRef(elm, context);
  }

  @Override
  public Void visitCode(Code elm, ElmQuantifyContext context) {
    context.getQuantities().clinicalValueCounts.codeLiteral++;

    return super.visitCode(elm, context);
  }

  @Override
  public Void visitConcept(Concept elm, ElmQuantifyContext context) {
    context.getQuantities().clinicalValueCounts.conceptLiteral++;

    return super.visitConcept(elm, context);
  }

  @Override
  public Void visitQuantity(Quantity elm, ElmQuantifyContext context) {
    context.getQuantities().clinicalValueCounts.quantity++;

    return super.visitQuantity(elm, context);
  }

  @Override
  public Void visitRatio(Ratio elm, ElmQuantifyContext context) {
    return super.visitRatio(elm, context);
  }

  @Override
  public Void visitCodeDef(CodeDef elm, ElmQuantifyContext context) {
    return super.visitCodeDef(elm, context);
  }

  @Override
  public Void visitConceptDef(ConceptDef elm, ElmQuantifyContext context) {
    return super.visitConceptDef(elm, context);
  }

  @Override
  public Void visitCodeRef(CodeRef elm, ElmQuantifyContext context) {
    return super.visitCodeRef(elm, context);
  }

  @Override
  public Void visitConceptRef(ConceptRef elm, ElmQuantifyContext context) {
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
    context.getQuantities().typeSpecifierCounts.named++;
    return super.visitNamedTypeSpecifier(elm, context);
  }

  @Override
  public Void visitIntervalTypeSpecifier(IntervalTypeSpecifier elm, ElmQuantifyContext context) {
    context.getQuantities().typeSpecifierCounts.interval++;
    return super.visitIntervalTypeSpecifier(elm, context);
  }

  @Override
  public Void visitListTypeSpecifier(ListTypeSpecifier elm, ElmQuantifyContext context) {
    context.getQuantities().typeSpecifierCounts.list++;
    return super.visitListTypeSpecifier(elm, context);
  }

  @Override
  public Void visitTupleTypeSpecifier(TupleTypeSpecifier elm, ElmQuantifyContext context) {
    context.getQuantities().typeSpecifierCounts.tuple++;
    return super.visitTupleTypeSpecifier(elm, context);
  }

  @Override
  public Void visitChoiceTypeSpecifier(ChoiceTypeSpecifier elm, ElmQuantifyContext context) {
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
    return super.visitLibrary(elm, context);
  }

  @Override
  public Void visitUsingDef(UsingDef elm, ElmQuantifyContext context) {
    context.getQuantities().usingCounts.usingDefinition++;
    return super.visitUsingDef(elm, context);
  }

  @Override
  public Void visitIncludeDef(IncludeDef elm, ElmQuantifyContext context) {
    context.getQuantities().includeCounts.includeDefinition++;
    return super.visitIncludeDef(elm, context);
  }

  @Override
  public Void visitContextDef(ContextDef elm, ElmQuantifyContext context) {
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
    context.getQuantities().parameterCounts.parameterDefinition++;
    return super.visitParameterDef(elm, context);
  }

  @Override
  public Void visitParameterRef(ParameterRef elm, ElmQuantifyContext context) {
    context.getQuantities().parameterCounts.parameterReference++;
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
    context.getQuantities().statementCounts.definition++;
    return super.visitExpressionDef(elm, context);
  }

  @Override
  public Void visitFunctionDef(FunctionDef elm, ElmQuantifyContext context) {
    context.getQuantities().statementCounts.functionDefinition++;
    return super.visitFunctionDef(elm, context);
  }

  @Override
  public Void visitFunctionRef(FunctionRef elm, ElmQuantifyContext context) {
    context.getQuantities().statementCounts.functionReference++;
    return super.visitFunctionRef(elm, context);
  }

  @Override
  public Void visitOperandDef(OperandDef elm, ElmQuantifyContext context) {
    // Used in function signature
    return super.visitOperandDef(elm, context);
  }

  @Override
  public Void visitExpressionRef(ExpressionRef elm, ElmQuantifyContext context) {
    // Calling another define statement
    context.getQuantities().statementCounts.reference++;

    return super.visitExpressionRef(elm, context);
  }

  @Override
  public Void visitOperandRef(OperandRef elm, ElmQuantifyContext context) {
    // Referring to a function argument
    return super.visitOperandRef(elm, context);
  }

  @Override
  public Void visitIdentifierRef(IdentifierRef elm, ElmQuantifyContext context) {
    // Referring to an identifier
    return super.visitIdentifierRef(elm, context);
  }

  @Override
  public Void visitAccessModifier(AccessModifier elm, ElmQuantifyContext context) {
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
    return super.visitAliasedQuerySource(elm, context);
  }

  @Override
  public Void visitLetClause(LetClause elm, ElmQuantifyContext context) {
    return super.visitLetClause(elm, context);
  }

  @Override
  public Void visitRelationshipClause(RelationshipClause elm, ElmQuantifyContext context) {
    return super.visitRelationshipClause(elm, context);
  }

  @Override
  public Void visitWith(With elm, ElmQuantifyContext context) {
    return super.visitWith(elm, context);
  }

  @Override
  public Void visitWithout(Without elm, ElmQuantifyContext context) {
    return super.visitWithout(elm, context);
  }

  @Override
  public Void visitSortByItem(SortByItem elm, ElmQuantifyContext context) {
    return super.visitSortByItem(elm, context);
  }

  @Override
  public Void visitByDirection(ByDirection elm, ElmQuantifyContext context) {
    return super.visitByDirection(elm, context);
  }

  @Override
  public Void visitByColumn(ByColumn elm, ElmQuantifyContext context) {
    return super.visitByColumn(elm, context);
  }

  @Override
  public Void visitByExpression(ByExpression elm, ElmQuantifyContext context) {
    return super.visitByExpression(elm, context);
  }

  @Override
  public Void visitSortClause(SortClause elm, ElmQuantifyContext context) {
    return super.visitSortClause(elm, context);
  }

  @Override
  public Void visitReturnClause(ReturnClause elm, ElmQuantifyContext context) {
    return super.visitReturnClause(elm, context);
  }

  @Override
  public Void visitQuery(Query elm, ElmQuantifyContext context) {
    return super.visitQuery(elm, context);
  }

  @Override
  public Void visitAliasRef(AliasRef elm, ElmQuantifyContext context) {
    return super.visitAliasRef(elm, context);
  }

  @Override
  public Void visitQueryLetRef(QueryLetRef elm, ElmQuantifyContext context) {
    return super.visitQueryLetRef(elm, context);
  }

  @Override
  public Void visitRetrieve(Retrieve elm, ElmQuantifyContext context) {

    context.getQuantities().retrieveCounts.retrieveDefinition++;
    context.getQuantities().retrieveCounts.dataSources.add(elm.getDataType().toString());

    return super.visitRetrieve(elm, context);
  }

  @Override
  public Void visitCodeFilterElement(CodeFilterElement elm, ElmQuantifyContext context) {
    return super.visitCodeFilterElement(elm, context);
  }

  @Override
  public Void visitDateFilterElement(DateFilterElement elm, ElmQuantifyContext context) {
    return super.visitDateFilterElement(elm, context);
  }

  @Override
  public Void visitOtherFilterElement(OtherFilterElement elm, ElmQuantifyContext context) {
    return super.visitOtherFilterElement(elm, context);
  }

  @Override
  public Void visitIncludeElement(IncludeElement elm, ElmQuantifyContext context) {
    return super.visitIncludeElement(elm, context);
  }

  @Override
  public Void visitAggregateClause(AggregateClause elm, ElmQuantifyContext context) {
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
    context.getQuantities().comparisonCounts.equal++;

    return super.visitEqual(elm, context);
  }

  @Override
  public Void visitEquivalent(Equivalent elm, ElmQuantifyContext context) {
    context.getQuantities().comparisonCounts.equivalent++;

    return super.visitEquivalent(elm, context);
  }

  @Override
  public Void visitNotEqual(NotEqual elm, ElmQuantifyContext context) {
    context.getQuantities().comparisonCounts.notEqual++;
    return super.visitNotEqual(elm, context);
  }

  @Override
  public Void visitLess(Less elm, ElmQuantifyContext context) {

    context.getQuantities().comparisonCounts.less++;
    return super.visitLess(elm, context);
  }

  @Override
  public Void visitGreater(Greater elm, ElmQuantifyContext context) {

    context.getQuantities().comparisonCounts.greater++;
    return super.visitGreater(elm, context);
  }

  @Override
  public Void visitLessOrEqual(LessOrEqual elm, ElmQuantifyContext context) {
    context.getQuantities().comparisonCounts.lessOrEqual++;
    return super.visitLessOrEqual(elm, context);
  }

  @Override
  public Void visitGreaterOrEqual(GreaterOrEqual elm, ElmQuantifyContext context) {
    context.getQuantities().comparisonCounts.greaterOrEqual++;
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
    context.getQuantities().logicalExpressionCounts.and++;

    return super.visitAnd(elm, context);
  }

  @Override
  public Void visitOr(Or elm, ElmQuantifyContext context) {
    context.getQuantities().logicalExpressionCounts.or++;

    return super.visitOr(elm, context);
  }

  @Override
  public Void visitXor(Xor elm, ElmQuantifyContext context) {
    context.getQuantities().logicalExpressionCounts.xor++;

    return super.visitXor(elm, context);
  }

  @Override
  public Void visitNot(Not elm, ElmQuantifyContext context) {
    context.getQuantities().logicalExpressionCounts.not++;

    return super.visitNot(elm, context);
  }

  @Override
  public Void visitImplies(Implies elm, ElmQuantifyContext context) {
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
    context.getQuantities().nullologicalCounts._null++;

    return super.visitNull(elm, context);
  }

  @Override
  public Void visitIsNull(IsNull elm, ElmQuantifyContext context) {
    context.getQuantities().nullologicalCounts.isNull++;

    return super.visitIsNull(elm, context);
  }

  @Override
  public Void visitIsTrue(IsTrue elm, ElmQuantifyContext context) {
    context.getQuantities().nullologicalCounts.isTrue++;

    return super.visitIsTrue(elm, context);
  }

  @Override
  public Void visitIsFalse(IsFalse elm, ElmQuantifyContext context) {
    context.getQuantities().nullologicalCounts.isFalse++;

    return super.visitIsFalse(elm, context);
  }

  @Override
  public Void visitCoalesce(Coalesce elm, ElmQuantifyContext context) {
    context.getQuantities().nullologicalCounts.coalesce++;

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

    context.getQuantities().conditionalCounts._if++;

    return super.visitIf(elm, context);
  }

  @Override
  public Void visitCaseItem(CaseItem elm, ElmQuantifyContext context) {
    context.getQuantities().conditionalCounts.caseItem++;

    return super.visitCaseItem(elm, context);
  }

  @Override
  public Void visitCase(Case elm, ElmQuantifyContext context) {
    context.getQuantities().conditionalCounts._case++;

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

    context.getQuantities().arithmeticOperatorCounts.add++;
    return super.visitAdd(elm, context);
  }

  @Override
  public Void visitSubtract(Subtract elm, ElmQuantifyContext context) {
    context.getQuantities().arithmeticOperatorCounts.subtract++;
    return super.visitSubtract(elm, context);
  }

  @Override
  public Void visitMultiply(Multiply elm, ElmQuantifyContext context) {

    context.getQuantities().arithmeticOperatorCounts.multiply++;
    return super.visitMultiply(elm, context);
  }

  @Override
  public Void visitDivide(Divide elm, ElmQuantifyContext context) {

    context.getQuantities().arithmeticOperatorCounts.divide++;
    return super.visitDivide(elm, context);
  }

  @Override
  public Void visitTruncatedDivide(TruncatedDivide elm, ElmQuantifyContext context) {
    context.getQuantities().arithmeticOperatorCounts.truncatedDivide++;
    return super.visitTruncatedDivide(elm, context);
  }

  @Override
  public Void visitModulo(Modulo elm, ElmQuantifyContext context) {

    context.getQuantities().arithmeticOperatorCounts.modulo++;
    return super.visitModulo(elm, context);
  }

  @Override
  public Void visitCeiling(Ceiling elm, ElmQuantifyContext context) {

    context.getQuantities().arithmeticOperatorCounts.ceiling++;
    return super.visitCeiling(elm, context);
  }

  @Override
  public Void visitFloor(Floor elm, ElmQuantifyContext context) {

    context.getQuantities().arithmeticOperatorCounts.floor++;
    return super.visitFloor(elm, context);
  }

  @Override
  public Void visitTruncate(Truncate elm, ElmQuantifyContext context) {

    context.getQuantities().arithmeticOperatorCounts.truncate++;
    return super.visitTruncate(elm, context);
  }

  @Override
  public Void visitAbs(Abs elm, ElmQuantifyContext context) {

    context.getQuantities().arithmeticOperatorCounts.abs++;
    return super.visitAbs(elm, context);
  }

  @Override
  public Void visitNegate(Negate elm, ElmQuantifyContext context) {

    context.getQuantities().arithmeticOperatorCounts.negate++;
    return super.visitNegate(elm, context);
  }

  @Override
  public Void visitRound(Round elm, ElmQuantifyContext context) {
    context.getQuantities().arithmeticOperatorCounts.round++;
    return super.visitRound(elm, context);
  }

  @Override
  public Void visitLn(Ln elm, ElmQuantifyContext context) {
    context.getQuantities().arithmeticOperatorCounts.ln++;
    return super.visitLn(elm, context);
  }

  @Override
  public Void visitExp(Exp elm, ElmQuantifyContext context) {
    context.getQuantities().arithmeticOperatorCounts.exp++;
    return super.visitExp(elm, context);
  }

  @Override
  public Void visitLog(Log elm, ElmQuantifyContext context) {
    context.getQuantities().arithmeticOperatorCounts.log++;
    return super.visitLog(elm, context);
  }

  @Override
  public Void visitPower(Power elm, ElmQuantifyContext context) {
    context.getQuantities().arithmeticOperatorCounts.power++;
    return super.visitPower(elm, context);
  }

  @Override
  public Void visitSuccessor(Successor elm, ElmQuantifyContext context) {
    context.getQuantities().arithmeticOperatorCounts.successor++;
    return super.visitSuccessor(elm, context);
  }

  @Override
  public Void visitPredecessor(Predecessor elm, ElmQuantifyContext context) {
    context.getQuantities().arithmeticOperatorCounts.predecessor++;
    return super.visitPredecessor(elm, context);
  }

  @Override
  public Void visitMinValue(MinValue elm, ElmQuantifyContext context) {
    context.getQuantities().arithmeticOperatorCounts.minValue++;
    return super.visitMinValue(elm, context);
  }

  @Override
  public Void visitMaxValue(MaxValue elm, ElmQuantifyContext context) {
    context.getQuantities().arithmeticOperatorCounts.maxValue++;
    return super.visitMaxValue(elm, context);
  }

  @Override
  public Void visitPrecision(Precision elm, ElmQuantifyContext context) {
    return super.visitPrecision(elm, context);
  }

  @Override
  public Void visitLowBoundary(LowBoundary elm, ElmQuantifyContext context) {
    return super.visitLowBoundary(elm, context);
  }

  @Override
  public Void visitHighBoundary(HighBoundary elm, ElmQuantifyContext context) {
    return super.visitHighBoundary(elm, context);
  }

  @Override
  public Void visitTotal(Total elm, ElmQuantifyContext context) {
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
    return super.visitConcatenate(elm, context);
  }

  @Override
  public Void visitCombine(Combine elm, ElmQuantifyContext context) {
    return super.visitCombine(elm, context);
  }

  @Override
  public Void visitSplit(Split elm, ElmQuantifyContext context) {
    return super.visitSplit(elm, context);
  }

  @Override
  public Void visitLength(Length elm, ElmQuantifyContext context) {
    return super.visitLength(elm, context);
  }

  @Override
  public Void visitUpper(Upper elm, ElmQuantifyContext context) {
    return super.visitUpper(elm, context);
  }

  @Override
  public Void visitLower(Lower elm, ElmQuantifyContext context) {
    return super.visitLower(elm, context);
  }

  @Override
  public Void visitIndexer(Indexer elm, ElmQuantifyContext context) {
    return super.visitIndexer(elm, context);
  }

  @Override
  public Void visitPositionOf(PositionOf elm, ElmQuantifyContext context) {
    return super.visitPositionOf(elm, context);
  }

  @Override
  public Void visitSubstring(Substring elm, ElmQuantifyContext context) {
    return super.visitSubstring(elm, context);
  }

  @Override
  public Void visitSplitOnMatches(SplitOnMatches elm, ElmQuantifyContext context) {
    return super.visitSplitOnMatches(elm, context);
  }

  @Override
  public Void visitLastPositionOf(LastPositionOf elm, ElmQuantifyContext context) {
    return super.visitLastPositionOf(elm, context);
  }

  @Override
  public Void visitStartsWith(StartsWith elm, ElmQuantifyContext context) {
    return super.visitStartsWith(elm, context);
  }

  @Override
  public Void visitEndsWith(EndsWith elm, ElmQuantifyContext context) {
    return super.visitEndsWith(elm, context);
  }

  @Override
  public Void visitMatches(Matches elm, ElmQuantifyContext context) {
    return super.visitMatches(elm, context);
  }

  @Override
  public Void visitReplaceMatches(ReplaceMatches elm, ElmQuantifyContext context) {
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
    return super.visitDurationBetween(elm, context);
  }

  @Override
  public Void visitDifferenceBetween(DifferenceBetween elm, ElmQuantifyContext context) {
    return super.visitDifferenceBetween(elm, context);
  }

  @Override
  public Void visitDateFrom(DateFrom elm, ElmQuantifyContext context) {
    return super.visitDateFrom(elm, context);
  }

  @Override
  public Void visitTimeFrom(TimeFrom elm, ElmQuantifyContext context) {
    return super.visitTimeFrom(elm, context);
  }

  @Override
  public Void visitTimezoneOffsetFrom(TimezoneOffsetFrom elm, ElmQuantifyContext context) {
    return super.visitTimezoneOffsetFrom(elm, context);
  }

  @Override
  public Void visitDateTimeComponentFrom(DateTimeComponentFrom elm, ElmQuantifyContext context) {
    return super.visitDateTimeComponentFrom(elm, context);
  }

  @Override
  public Void visitTimeOfDay(TimeOfDay elm, ElmQuantifyContext context) {
    return super.visitTimeOfDay(elm, context);
  }

  @Override
  public Void visitToday(Today elm, ElmQuantifyContext context) {
    return super.visitToday(elm, context);
  }

  @Override
  public Void visitNow(Now elm, ElmQuantifyContext context) {
    return super.visitNow(elm, context);
  }

  @Override
  public Void visitDateTime(DateTime elm, ElmQuantifyContext context) {
    return super.visitDateTime(elm, context);
  }

  @Override
  public Void visitTime(Time elm, ElmQuantifyContext context) {
    return super.visitTime(elm, context);
  }

  @Override
  public Void visitSameAs(SameAs elm, ElmQuantifyContext context) {
    return super.visitSameAs(elm, context);
  }

  @Override
  public Void visitSameOrBefore(SameOrBefore elm, ElmQuantifyContext context) {
    return super.visitSameOrBefore(elm, context);
  }

  @Override
  public Void visitSameOrAfter(SameOrAfter elm, ElmQuantifyContext context) {
    return super.visitSameOrAfter(elm, context);
  }

  @Override
  public Void visitTimezoneFrom(TimezoneFrom elm, ElmQuantifyContext context) {
    return super.visitTimezoneFrom(elm, context);
  }

  @Override
  public Void visitDate(Date elm, ElmQuantifyContext context) {
    return super.visitDate(elm, context);
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
    context.getQuantities().intervalOperatorCounts.intervalDefinition++;
    // Push something here to capture element type

    try {
      boolean isTemporal = isTemporal(elm);
    } catch (Exception e) {

    }

    return super.visitInterval(elm, context);
  }

  @Override
  public Void visitWidth(Width elm, ElmQuantifyContext context) {
    return super.visitWidth(elm, context);
  }

  @Override
  public Void visitStart(Start elm, ElmQuantifyContext context) {
    return super.visitStart(elm, context);
  }

  @Override
  public Void visitEnd(End elm, ElmQuantifyContext context) {
    return super.visitEnd(elm, context);
  }

  @Override
  public Void visitContains(Contains elm, ElmQuantifyContext context) {
    return super.visitContains(elm, context);
  }

  @Override
  public Void visitProperContains(ProperContains elm, ElmQuantifyContext context) {
    return super.visitProperContains(elm, context);
  }

  @Override
  public Void visitIn(In elm, ElmQuantifyContext context) {
    context.getQuantities().intervalOperatorCounts.in++;

    try {
      boolean isTemporal = isTemporal(elm);
    } catch (Exception e) {

    }

    return super.visitIn(elm, context);
  }

  @Override
  public Void visitProperIn(ProperIn elm, ElmQuantifyContext context) {
    return super.visitProperIn(elm, context);
  }

  @Override
  public Void visitIncludes(Includes elm, ElmQuantifyContext context) {
    return super.visitIncludes(elm, context);
  }

  @Override
  public Void visitIncludedIn(IncludedIn elm, ElmQuantifyContext context) {
    return super.visitIncludedIn(elm, context);
  }

  @Override
  public Void visitProperIncludes(ProperIncludes elm, ElmQuantifyContext context) {
    return super.visitProperIncludes(elm, context);
  }

  @Override
  public Void visitProperIncludedIn(ProperIncludedIn elm, ElmQuantifyContext context) {
    return super.visitProperIncludedIn(elm, context);
  }

  @Override
  public Void visitBefore(Before elm, ElmQuantifyContext context) {
    return super.visitBefore(elm, context);
  }

  @Override
  public Void visitAfter(After elm, ElmQuantifyContext context) {
    return super.visitAfter(elm, context);
  }

  @Override
  public Void visitMeets(Meets elm, ElmQuantifyContext context) {
    return super.visitMeets(elm, context);
  }

  @Override
  public Void visitMeetsBefore(MeetsBefore elm, ElmQuantifyContext context) {
    return super.visitMeetsBefore(elm, context);
  }

  @Override
  public Void visitMeetsAfter(MeetsAfter elm, ElmQuantifyContext context) {
    return super.visitMeetsAfter(elm, context);
  }

  @Override
  public Void visitOverlaps(Overlaps elm, ElmQuantifyContext context) {
    return super.visitOverlaps(elm, context);
  }

  @Override
  public Void visitOverlapsBefore(OverlapsBefore elm, ElmQuantifyContext context) {
    return super.visitOverlapsBefore(elm, context);
  }

  @Override
  public Void visitOverlapsAfter(OverlapsAfter elm, ElmQuantifyContext context) {
    return super.visitOverlapsAfter(elm, context);
  }

  @Override
  public Void visitStarts(Starts elm, ElmQuantifyContext context) {
    return super.visitStarts(elm, context);
  }

  @Override
  public Void visitEnds(Ends elm, ElmQuantifyContext context) {
    return super.visitEnds(elm, context);
  }

  @Override
  public Void visitCollapse(Collapse elm, ElmQuantifyContext context) {
    return super.visitCollapse(elm, context);
  }

  @Override
  public Void visitUnion(Union elm, ElmQuantifyContext context) {
    return super.visitUnion(elm, context);
  }

  @Override
  public Void visitIntersect(Intersect elm, ElmQuantifyContext context) {
    return super.visitIntersect(elm, context);
  }

  @Override
  public Void visitExcept(Except elm, ElmQuantifyContext context) {
    return super.visitExcept(elm, context);
  }

  @Override
  public Void visitSize(Size elm, ElmQuantifyContext context) {
    return super.visitSize(elm, context);
  }

  @Override
  public Void visitPointFrom(PointFrom elm, ElmQuantifyContext context) {
    return super.visitPointFrom(elm, context);
  }

  @Override
  public Void visitExpand(Expand elm, ElmQuantifyContext context) {
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
    context.getQuantities().listOperatorCounts.listDefinition++;
    return super.visitList(elm, context);
  }

  @Override
  public Void visitExists(Exists elm, ElmQuantifyContext context) {
    return super.visitExists(elm, context);
  }

  @Override
  public Void visitTimes(Times elm, ElmQuantifyContext context) {
    return super.visitTimes(elm, context);
  }

  @Override
  public Void visitFilter(Filter elm, ElmQuantifyContext context) {
    return super.visitFilter(elm, context);
  }

  @Override
  public Void visitFirst(First elm, ElmQuantifyContext context) {
    return super.visitFirst(elm, context);
  }

  @Override
  public Void visitLast(Last elm, ElmQuantifyContext context) {
    return super.visitLast(elm, context);
  }

  @Override
  public Void visitIndexOf(IndexOf elm, ElmQuantifyContext context) {
    return super.visitIndexOf(elm, context);
  }

  @Override
  public Void visitFlatten(Flatten elm, ElmQuantifyContext context) {
    return super.visitFlatten(elm, context);
  }

  @Override
  public Void visitSort(Sort elm, ElmQuantifyContext context) {
    return super.visitSort(elm, context);
  }

  @Override
  public Void visitForEach(ForEach elm, ElmQuantifyContext context) {
    return super.visitForEach(elm, context);
  }

  @Override
  public Void visitDistinct(Distinct elm, ElmQuantifyContext context) {
    return super.visitDistinct(elm, context);
  }

  @Override
  public Void visitCurrent(Current elm, ElmQuantifyContext context) {
    return super.visitCurrent(elm, context);
  }

  @Override
  public Void visitSingletonFrom(SingletonFrom elm, ElmQuantifyContext context) {
    return super.visitSingletonFrom(elm, context);
  }

  // not sure where the following belong

  @Override
  public Void visitSlice(Slice elm, ElmQuantifyContext context) {
    return super.visitSlice(elm, context);
  }

  @Override
  public Void visitRepeat(Repeat elm, ElmQuantifyContext context) {
    return super.visitRepeat(elm, context);
  }

  @Override
  public Void visitIteration(Iteration elm, ElmQuantifyContext context) {
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
    return super.visitAggregate(elm, context);
  }

  @Override
  public Void visitProduct(Product elm, ElmQuantifyContext context) {
    return super.visitProduct(elm, context);
  }

  @Override
  public Void visitGeometricMean(GeometricMean elm, ElmQuantifyContext context) {
    return super.visitGeometricMean(elm, context);
  }

  @Override
  public Void visitCount(Count elm, ElmQuantifyContext context) {
    return super.visitCount(elm, context);
  }

  @Override
  public Void visitSum(Sum elm, ElmQuantifyContext context) {
    return super.visitSum(elm, context);
  }

  @Override
  public Void visitMin(Min elm, ElmQuantifyContext context) {
    return super.visitMin(elm, context);
  }

  @Override
  public Void visitMax(Max elm, ElmQuantifyContext context) {
    return super.visitMax(elm, context);
  }

  @Override
  public Void visitAvg(Avg elm, ElmQuantifyContext context) {
    return super.visitAvg(elm, context);
  }

  @Override
  public Void visitMedian(Median elm, ElmQuantifyContext context) {
    return super.visitMedian(elm, context);
  }

  @Override
  public Void visitMode(Mode elm, ElmQuantifyContext context) {
    return super.visitMode(elm, context);
  }

  @Override
  public Void visitVariance(Variance elm, ElmQuantifyContext context) {
    return super.visitVariance(elm, context);
  }

  @Override
  public Void visitPopulationVariance(PopulationVariance elm, ElmQuantifyContext context) {
    return super.visitPopulationVariance(elm, context);
  }

  @Override
  public Void visitStdDev(StdDev elm, ElmQuantifyContext context) {
    return super.visitStdDev(elm, context);
  }

  @Override
  public Void visitPopulationStdDev(PopulationStdDev elm, ElmQuantifyContext context) {
    return super.visitPopulationStdDev(elm, context);
  }

  @Override
  public Void visitAllTrue(AllTrue elm, ElmQuantifyContext context) {
    return super.visitAllTrue(elm, context);
  }

  @Override
  public Void visitAnyTrue(AnyTrue elm, ElmQuantifyContext context) {
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
    return super.visitIs(elm, context);
  }

  @Override
  public Void visitAs(As elm, ElmQuantifyContext context) {
    return super.visitAs(elm, context);
  }

  @Override
  public Void visitConvert(Convert elm, ElmQuantifyContext context) {
    return super.visitConvert(elm, context);
  }

  @Override
  public Void visitToBoolean(ToBoolean elm, ElmQuantifyContext context) {
    return super.visitToBoolean(elm, context);
  }

  @Override
  public Void visitToConcept(ToConcept elm, ElmQuantifyContext context) {
    return super.visitToConcept(elm, context);
  }

  @Override
  public Void visitToDateTime(ToDateTime elm, ElmQuantifyContext context) {
    return super.visitToDateTime(elm, context);
  }

  @Override
  public Void visitToDecimal(ToDecimal elm, ElmQuantifyContext context) {
    return super.visitToDecimal(elm, context);
  }

  @Override
  public Void visitToInteger(ToInteger elm, ElmQuantifyContext context) {
    return super.visitToInteger(elm, context);
  }

  @Override
  public Void visitToQuantity(ToQuantity elm, ElmQuantifyContext context) {
    return super.visitToQuantity(elm, context);
  }

  @Override
  public Void visitToString(ToString elm, ElmQuantifyContext context) {
    return super.visitToString(elm, context);
  }

  @Override
  public Void visitToTime(ToTime elm, ElmQuantifyContext context) {
    return super.visitToTime(elm, context);
  }

  @Override
  public Void visitCanConvert(CanConvert elm, ElmQuantifyContext context) {
    return super.visitCanConvert(elm, context);
  }

  @Override
  public Void visitConvertsToBoolean(ConvertsToBoolean elm, ElmQuantifyContext context) {
    return super.visitConvertsToBoolean(elm, context);
  }

  @Override
  public Void visitToChars(ToChars elm, ElmQuantifyContext context) {
    return super.visitToChars(elm, context);
  }

  @Override
  public Void visitConvertsToDate(ConvertsToDate elm, ElmQuantifyContext context) {
    return super.visitConvertsToDate(elm, context);
  }

  @Override
  public Void visitToDate(ToDate elm, ElmQuantifyContext context) {
    return super.visitToDate(elm, context);
  }

  @Override
  public Void visitConvertsToDateTime(ConvertsToDateTime elm, ElmQuantifyContext context) {
    return super.visitConvertsToDateTime(elm, context);
  }

  @Override
  public Void visitConvertsToLong(ConvertsToLong elm, ElmQuantifyContext context) {
    return super.visitConvertsToLong(elm, context);
  }

  @Override
  public Void visitToLong(ToLong elm, ElmQuantifyContext context) {
    return super.visitToLong(elm, context);
  }

  @Override
  public Void visitConvertsToDecimal(ConvertsToDecimal elm, ElmQuantifyContext context) {
    return super.visitConvertsToDecimal(elm, context);
  }

  @Override
  public Void visitConvertsToInteger(ConvertsToInteger elm, ElmQuantifyContext context) {
    return super.visitConvertsToInteger(elm, context);
  }

  @Override
  public Void visitToList(ToList elm, ElmQuantifyContext context) {
    return super.visitToList(elm, context);
  }

  @Override
  public Void visitConvertQuantity(ConvertQuantity elm, ElmQuantifyContext context) {
    return super.visitConvertQuantity(elm, context);
  }

  @Override
  public Void visitCanConvertQuantity(CanConvertQuantity elm, ElmQuantifyContext context) {
    return super.visitCanConvertQuantity(elm, context);
  }

  @Override
  public Void visitConvertsToQuantity(ConvertsToQuantity elm, ElmQuantifyContext context) {
    return super.visitConvertsToQuantity(elm, context);
  }

  @Override
  public Void visitConvertsToRatio(ConvertsToRatio elm, ElmQuantifyContext context) {
    return super.visitConvertsToRatio(elm, context);
  }

  @Override
  public Void visitToRatio(ToRatio elm, ElmQuantifyContext context) {
    return super.visitToRatio(elm, context);
  }

  @Override
  public Void visitConvertsToString(ConvertsToString elm, ElmQuantifyContext context) {
    return super.visitConvertsToString(elm, context);
  }

  @Override
  public Void visitConvertsToTime(ConvertsToTime elm, ElmQuantifyContext context) {
    return super.visitConvertsToTime(elm, context);
  }

  @Override
  public Void visitChildren(Children elm, ElmQuantifyContext context) {
    return super.visitChildren(elm, context);
  }

  @Override
  public Void visitDescendents(Descendents elm, ElmQuantifyContext context) {
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
    context.getQuantities().clinicalOperatorCounts.inCodeSystem++;

    return super.visitInCodeSystem(elm, context);
  }

  @Override
  public Void visitInValueSet(InValueSet elm, ElmQuantifyContext context) {
    context.getQuantities().clinicalOperatorCounts.inValueSet++;

    return super.visitInValueSet(elm, context);
  }

  @Override
  public Void visitCalculateAge(CalculateAge elm, ElmQuantifyContext context) {
    context.getQuantities().clinicalOperatorCounts.calculateAge++;

    return super.visitCalculateAge(elm, context);
  }

  @Override
  public Void visitCalculateAgeAt(CalculateAgeAt elm, ElmQuantifyContext context) {
    context.getQuantities().clinicalOperatorCounts.calculateAgeAt++;

    return super.visitCalculateAgeAt(elm, context);
  }

  @Override
  public Void visitAnyInCodeSystem(AnyInCodeSystem elm, ElmQuantifyContext context) {
    return super.visitAnyInCodeSystem(elm, context);
  }

  @Override
  public Void visitAnyInValueSet(AnyInValueSet elm, ElmQuantifyContext context) {
    return super.visitAnyInValueSet(elm, context);
  }

  @Override
  public Void visitSubsumes(Subsumes elm, ElmQuantifyContext context) {
    return super.visitSubsumes(elm, context);
  }

  @Override
  public Void visitSubsumedBy(SubsumedBy elm, ElmQuantifyContext context) {
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
    return super.visitMessage(elm, context);
  }
  //</editor-fold>
}