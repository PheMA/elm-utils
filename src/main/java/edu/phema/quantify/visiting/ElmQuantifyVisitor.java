package edu.phema.quantify.visiting;

import edu.phema.quantify.ElmQuantifierException;
import edu.phema.visiting.ElmBaseStatementPostOrderTransformationVisitor;
import org.cqframework.cql.elm.tracking.Trackable;
import org.hl7.cql.model.DataType;
import org.hl7.elm.r1.*;

import java.lang.reflect.Type;

public class ElmQuantifyVisitor extends ElmBaseStatementPostOrderTransformationVisitor<Void, ElmQuantifyContext> {
  public ElmQuantifyVisitor() {
    super();
  }

  public ElmQuantifyVisitor(boolean debug) {
    super(debug);
  }

  @Override
  protected void debug(Object o) {
    super.debug(o);
  }

  @Override
  protected void debug(String method) {
    super.debug(method);
  }

  @Override
  protected void warn(String s) {
    super.warn(s);
  }

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
  public Void visitUnaryExpression(UnaryExpression elm, ElmQuantifyContext context) {
    context.getQuantities().expressionCounts.unary++;
    return super.visitUnaryExpression(elm, context);
  }

  @Override
  public Void visitTypeSpecifier(TypeSpecifier elm, ElmQuantifyContext context) {
    return super.visitTypeSpecifier(elm, context);
  }

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
  public Void visitTupleElementDefinition(TupleElementDefinition elm, ElmQuantifyContext context) {
    return super.visitTupleElementDefinition(elm, context);
  }

  @Override
  public Void visitTupleTypeSpecifier(TupleTypeSpecifier elm, ElmQuantifyContext context) {
    context.getQuantities().typeSpecifierCounts.tuple++;
    return super.visitTupleTypeSpecifier(elm, context);
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
  public Void visitParameterDef(ParameterDef elm, ElmQuantifyContext context) {
    context.getQuantities().parameterCounts.parameterDefinition++;
    return super.visitParameterDef(elm, context);
  }

  @Override
  public Void visitParameterRef(ParameterRef elm, ElmQuantifyContext context) {
    context.getQuantities().parameterCounts.parameterReference++;
    return super.visitParameterRef(elm, context);
  }

  @Override
  public Void visitOperandDef(OperandDef elm, ElmQuantifyContext context) {
    return super.visitOperandDef(elm, context);
  }

  @Override
  public Void visitTupleElement(TupleElement elm, ElmQuantifyContext context) {
    return super.visitTupleElement(elm, context);
  }

  @Override
  public Void visitTuple(Tuple elm, ElmQuantifyContext context) {
    return super.visitTuple(elm, context);
  }

  @Override
  public Void visitInstanceElement(InstanceElement elm, ElmQuantifyContext context) {
    return super.visitInstanceElement(elm, context);
  }

  @Override
  public Void visitInstance(Instance elm, ElmQuantifyContext context) {
    return super.visitInstance(elm, context);
  }

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
  public Void visitList(List elm, ElmQuantifyContext context) {
    context.getQuantities().listOperatorCounts.listDefinition++;
    return super.visitList(elm, context);
  }

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
  public Void visitRetrieve(Retrieve elm, ElmQuantifyContext context) {

    context.getQuantities().retrieveCounts.retrieveDefinition++;
    context.getQuantities().retrieveCounts.dataSources.add(elm.getDataType().toString());

    return super.visitRetrieve(elm, context);
  }

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
  public Void visitQuantity(Quantity elm, ElmQuantifyContext context) {
    context.getQuantities().clinicalValueCounts.quantity++;

    return super.visitQuantity(elm, context);
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
  public Void visitExpressionRef(ExpressionRef elm, ElmQuantifyContext context) {
    context.getQuantities().statementCounts.reference++;

    return super.visitExpressionRef(elm, context);
  }

  @Override
  public Void visitOperandRef(OperandRef elm, ElmQuantifyContext context) {
    return super.visitOperandRef(elm, context);
  }

  @Override
  public Void visitIdentifierRef(IdentifierRef elm, ElmQuantifyContext context) {
    return super.visitIdentifierRef(elm, context);
  }

  @Override
  public Void visitLiteral(Literal elm, ElmQuantifyContext context) {
    return super.visitLiteral(elm, context);
  }

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

  //// Ignoring type operatorsOk

//  @Override
//  public Void visitIs(Is elm, ElmQuantifyContext context) {
//    return super.visitIs(elm, context);
//  }
//
//  @Override
//  public Void visitAs(As elm, ElmQuantifyContext context) {
//    return super.visitAs(elm, context);
//  }
//
//  @Override
//  public Void visitConvert(Convert elm, ElmQuantifyContext context) {
//    return super.visitConvert(elm, context);
//  }
//
//  @Override
//  public Void visitToBoolean(ToBoolean elm, ElmQuantifyContext context) {
//    return super.visitToBoolean(elm, context);
//  }
//
//  @Override
//  public Void visitToConcept(ToConcept elm, ElmQuantifyContext context) {
//    return super.visitToConcept(elm, context);
//  }
//
//  @Override
//  public Void visitToDateTime(ToDateTime elm, ElmQuantifyContext context) {
//    return super.visitToDateTime(elm, context);
//  }
//
//  @Override
//  public Void visitToDecimal(ToDecimal elm, ElmQuantifyContext context) {
//    return super.visitToDecimal(elm, context);
//  }
//
//  @Override
//  public Void visitToInteger(ToInteger elm, ElmQuantifyContext context) {
//    return super.visitToInteger(elm, context);
//  }
//
//  @Override
//  public Void visitToQuantity(ToQuantity elm, ElmQuantifyContext context) {
//    return super.visitToQuantity(elm, context);
//  }
//
//  @Override
//  public Void visitToString(ToString elm, ElmQuantifyContext context) {
//    return super.visitToString(elm, context);
//  }
//
//  @Override
//  public Void visitToTime(ToTime elm, ElmQuantifyContext context) {
//    return super.visitToTime(elm, context);
//  }

  //// Ignoring type operators


  //// Comparison operators

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
    return super.visitNotEqual(elm, context);
  }

  @Override
  public Void visitLess(Less elm, ElmQuantifyContext context) {
    return super.visitLess(elm, context);
  }

  @Override
  public Void visitGreater(Greater elm, ElmQuantifyContext context) {
    return super.visitGreater(elm, context);
  }

  @Override
  public Void visitLessOrEqual(LessOrEqual elm, ElmQuantifyContext context) {
    return super.visitLessOrEqual(elm, context);
  }

  @Override
  public Void visitGreaterOrEqual(GreaterOrEqual elm, ElmQuantifyContext context) {
    return super.visitGreaterOrEqual(elm, context);
  }

  //// Comparison operators


  //// Arithmetic operators

  @Override
  public Void visitAdd(Add elm, ElmQuantifyContext context) {
    return super.visitAdd(elm, context);
  }

  @Override
  public Void visitSubtract(Subtract elm, ElmQuantifyContext context) {
    return super.visitSubtract(elm, context);
  }

  @Override
  public Void visitMultiply(Multiply elm, ElmQuantifyContext context) {
    return super.visitMultiply(elm, context);
  }

  @Override
  public Void visitDivide(Divide elm, ElmQuantifyContext context) {
    return super.visitDivide(elm, context);
  }

  @Override
  public Void visitTruncatedDivide(TruncatedDivide elm, ElmQuantifyContext context) {
    return super.visitTruncatedDivide(elm, context);
  }

  @Override
  public Void visitModulo(Modulo elm, ElmQuantifyContext context) {
    return super.visitModulo(elm, context);
  }

  @Override
  public Void visitCeiling(Ceiling elm, ElmQuantifyContext context) {
    return super.visitCeiling(elm, context);
  }

  @Override
  public Void visitFloor(Floor elm, ElmQuantifyContext context) {
    return super.visitFloor(elm, context);
  }

  @Override
  public Void visitTruncate(Truncate elm, ElmQuantifyContext context) {
    return super.visitTruncate(elm, context);
  }

  @Override
  public Void visitAbs(Abs elm, ElmQuantifyContext context) {
    return super.visitAbs(elm, context);
  }

  @Override
  public Void visitNegate(Negate elm, ElmQuantifyContext context) {
    return super.visitNegate(elm, context);
  }

  @Override
  public Void visitRound(Round elm, ElmQuantifyContext context) {
    return super.visitRound(elm, context);
  }

  @Override
  public Void visitLn(Ln elm, ElmQuantifyContext context) {
    return super.visitLn(elm, context);
  }

  @Override
  public Void visitExp(Exp elm, ElmQuantifyContext context) {
    return super.visitExp(elm, context);
  }

  @Override
  public Void visitLog(Log elm, ElmQuantifyContext context) {
    return super.visitLog(elm, context);
  }

  @Override
  public Void visitPower(Power elm, ElmQuantifyContext context) {
    return super.visitPower(elm, context);
  }

  @Override
  public Void visitSuccessor(Successor elm, ElmQuantifyContext context) {
    return super.visitSuccessor(elm, context);
  }

  @Override
  public Void visitPredecessor(Predecessor elm, ElmQuantifyContext context) {
    return super.visitPredecessor(elm, context);
  }

  @Override
  public Void visitMinValue(MinValue elm, ElmQuantifyContext context) {
    return super.visitMinValue(elm, context);
  }

  @Override
  public Void visitMaxValue(MaxValue elm, ElmQuantifyContext context) {
    return super.visitMaxValue(elm, context);
  }

  //// Arithmetic operators

  //// String operators

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

  //// String operators

  //// DateTime operators

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

  //// DateTime operators

  //// Interval operators

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

  @Override
  public Void visitAggregateExpression(AggregateExpression elm, ElmQuantifyContext context) {
    return super.visitAggregateExpression(elm, context);
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

  @Override
  public Void visitProperty(Property elm, ElmQuantifyContext context) {
    return super.visitProperty(elm, context);
  }

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
}
