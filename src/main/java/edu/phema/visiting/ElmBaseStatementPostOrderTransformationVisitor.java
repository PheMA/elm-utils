package edu.phema.visiting;

import org.cqframework.cql.elm.visiting.ElmLibraryVisitor;
import org.hl7.elm.r1.*;

import java.util.ArrayList;

public class ElmBaseStatementPostOrderTransformationVisitor<C> extends ElmBaseStatementPostOrderVisitor<Element, C> implements ElmLibraryVisitor<Element, C> {
    private boolean debug;

    public ElmBaseStatementPostOrderTransformationVisitor() {
        this.debug = false;
    }

    public ElmBaseStatementPostOrderTransformationVisitor(boolean debug) {
        this.debug = debug;
    }

    private void debug(Object o) {
        if (debug) {
            System.out.println(String.format("Visiting: %s", o.getClass().getName()));
        }
    }

    private void warn(String s) {
        System.out.println(s);
    }

    @Override
    public Element visitBinaryExpression(BinaryExpression elm, C context) {
        this.debug(elm);

        ArrayList<Expression> transformedOps = new ArrayList<>();
        for (Expression expression : elm.getOperand()) {
            transformedOps.add((Expression) this.visitExpression(expression, context));
        }
        elm.getOperand().clear();
        elm.withOperand(transformedOps);

        return super.visitBinaryExpression(elm, context);
    }

    @Override
    public Element visitElement(org.hl7.elm.r1.Element elm, C context) {
        this.debug(elm);
        return super.visitElement(elm, context);
    }

    @Override
    public Element visitLibrary(Library elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitUsingDef(UsingDef elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitIncludeDef(IncludeDef elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitExpression(Expression elm, C context) {
        this.debug(elm);
        return super.visitExpression(elm, context);
    }

    @Override
    public Element visitUnaryExpression(UnaryExpression elm, C context) {
        this.debug(elm);
        return super.visitUnaryExpression(elm, context);
    }

    @Override
    public Element visitRetrieve(Retrieve elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitCodeSystemDef(CodeSystemDef elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitValueSetDef(ValueSetDef elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitCodeSystemRef(CodeSystemRef elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitValueSetRef(ValueSetRef elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitCode(Code elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitConcept(Concept elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitInCodeSystem(InCodeSystem elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitInValueSet(InValueSet elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitQuantity(Quantity elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitCalculateAge(CalculateAge elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitCalculateAgeAt(CalculateAgeAt elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitTypeSpecifier(TypeSpecifier elm, C context) {
        this.debug(elm);
        return super.visitTypeSpecifier(elm, context);
    }

    @Override
    public Element visitNamedTypeSpecifier(NamedTypeSpecifier elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitIntervalTypeSpecifier(IntervalTypeSpecifier elm, C context) {
        this.debug(elm);
        return super.visitIntervalTypeSpecifier(elm, context);
    }

    @Override
    public Element visitListTypeSpecifier(ListTypeSpecifier elm, C context) {
        this.debug(elm);
        return super.visitListTypeSpecifier(elm, context);
    }

    @Override
    public Element visitTupleElementDefinition(TupleElementDefinition elm, C context) {
        this.debug(elm);
        return super.visitTupleElementDefinition(elm, context);
    }

    @Override
    public Element visitTupleTypeSpecifier(TupleTypeSpecifier elm, C context) {
        this.debug(elm);
        return super.visitTupleTypeSpecifier(elm, context);
    }

    @Override
    public Element visitTernaryExpression(TernaryExpression elm, C context) {
        this.debug(elm);
        return super.visitTernaryExpression(elm, context);
    }

    @Override
    public Element visitNaryExpression(NaryExpression elm, C context) {
        this.debug(elm);
        return super.visitNaryExpression(elm, context);
    }

    @Override
    public Element visitExpressionDef(ExpressionDef elm, C context) {
        this.debug(elm);
        elm.setExpression((Expression) this.visitExpression(elm.getExpression(), context));
        return elm;
    }

    @Override
    public Element visitFunctionDef(FunctionDef elm, C context) {
        this.debug(elm);
        this.warn("FunctionDef not handled correctly");
        return elm;
    }

    @Override
    public Element visitExpressionRef(ExpressionRef elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitFunctionRef(FunctionRef elm, C context) {
        this.debug(elm);
        this.warn("FunctionRef not handled correctly");
        return elm;
    }

    @Override
    public Element visitParameterDef(ParameterDef elm, C context) {
        this.debug(elm);
        this.warn("ParameterDef not handled correctly");
        return elm;
    }

    @Override
    public Element visitParameterRef(ParameterRef elm, C context) {
        this.debug(elm);
        this.warn("ParameterRef not handled correctly");
        return elm;
    }

    @Override
    public Element visitOperandDef(OperandDef elm, C context) {
        this.debug(elm);
        this.warn("OperandDef not handled correctly");
        return elm;
    }

    @Override
    public Element visitOperandRef(OperandRef elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitIdentifierRef(IdentifierRef elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitLiteral(Literal elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitTupleElement(TupleElement elm, C context) {
        this.debug(elm);
        this.warn("TupleElement not handled correctly!");
        return null;
    }

    @Override
    public Element visitTuple(Tuple elm, C context) {
        this.debug(elm);
        this.warn("Tuple not handled correctly!");
        return elm;
    }

    @Override
    public Element visitInstanceElement(InstanceElement elm, C context) {
        this.debug(elm);
        this.warn("InstanceElement not handled correctly!");
        return null;
    }

    @Override
    public Element visitInstance(Instance elm, C context) {
        this.debug(elm);
        this.warn("Instance not handled correctly!");
        return elm;
    }

    @Override
    public Element visitInterval(Interval elm, C context) {
        this.debug(elm);
        this.warn("Interval not handled correctly!");
        return elm;
    }

    @Override
    public Element visitList(List elm, C context) {
        this.debug(elm);
        this.warn("List not handled correctly!");
        return elm;
    }

    @Override
    public Element visitAnd(And elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitOr(Or elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitXor(Xor elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitNot(Not elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitIf(If elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitCaseItem(CaseItem elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitCase(Case elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitNull(Null elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitIsNull(IsNull elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitIsTrue(IsTrue elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitIsFalse(IsFalse elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitCoalesce(Coalesce elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitIs(Is elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitAs(As elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitConvert(Convert elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitToBoolean(ToBoolean elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitToConcept(ToConcept elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitToDateTime(ToDateTime elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitToDecimal(ToDecimal elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitToInteger(ToInteger elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitToQuantity(ToQuantity elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitToString(ToString elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitToTime(ToTime elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitEqual(Equal elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitEquivalent(Equivalent elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitNotEqual(NotEqual elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitLess(Less elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitGreater(Greater elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitLessOrEqual(LessOrEqual elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitGreaterOrEqual(GreaterOrEqual elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitAdd(Add elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitSubtract(Subtract elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitMultiply(Multiply elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitDivide(Divide elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitTruncatedDivide(TruncatedDivide elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitModulo(Modulo elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitCeiling(Ceiling elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitFloor(Floor elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitTruncate(Truncate elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitAbs(Abs elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitNegate(Negate elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitRound(Round elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitLn(Ln elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitExp(Exp elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitLog(Log elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitPower(Power elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitSuccessor(Successor elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitPredecessor(Predecessor elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitMinValue(MinValue elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitMaxValue(MaxValue elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitConcatenate(Concatenate elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitCombine(Combine elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitSplit(Split elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitLength(Length elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitUpper(Upper elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitLower(Lower elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitIndexer(Indexer elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitPositionOf(PositionOf elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitSubstring(Substring elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitDurationBetween(DurationBetween elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitDifferenceBetween(DifferenceBetween elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitDateFrom(DateFrom elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitTimeFrom(TimeFrom elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitTimezoneOffsetFrom(TimezoneOffsetFrom elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitDateTimeComponentFrom(DateTimeComponentFrom elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitTimeOfDay(TimeOfDay elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitToday(Today elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitNow(Now elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitDateTime(DateTime elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitTime(Time elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitSameAs(SameAs elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitSameOrBefore(SameOrBefore elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitSameOrAfter(SameOrAfter elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitWidth(Width elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitStart(Start elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitEnd(End elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitContains(Contains elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitProperContains(ProperContains elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitIn(In elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitProperIn(ProperIn elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitIncludes(Includes elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitIncludedIn(IncludedIn elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitProperIncludes(ProperIncludes elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitProperIncludedIn(ProperIncludedIn elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitBefore(Before elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitAfter(After elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitMeets(Meets elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitMeetsBefore(MeetsBefore elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitMeetsAfter(MeetsAfter elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitOverlaps(Overlaps elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitOverlapsBefore(OverlapsBefore elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitOverlapsAfter(OverlapsAfter elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitStarts(Starts elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitEnds(Ends elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitCollapse(Collapse elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitUnion(Union elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitIntersect(Intersect elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitExcept(Except elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitExists(Exists elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitTimes(Times elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitFilter(Filter elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitFirst(First elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitLast(Last elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitIndexOf(IndexOf elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitFlatten(Flatten elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitSort(Sort elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitForEach(ForEach elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitDistinct(Distinct elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitCurrent(Current elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitSingletonFrom(SingletonFrom elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitAggregateExpression(AggregateExpression elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitCount(Count elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitSum(Sum elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitMin(Min elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitMax(Max elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitAvg(Avg elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitMedian(Median elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitMode(Mode elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitVariance(Variance elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitPopulationVariance(PopulationVariance elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitStdDev(StdDev elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitPopulationStdDev(PopulationStdDev elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitAllTrue(AllTrue elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitAnyTrue(AnyTrue elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitProperty(Property elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitAliasedQuerySource(AliasedQuerySource elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitLetClause(LetClause elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitRelationshipClause(RelationshipClause elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitWith(With elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitWithout(Without elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitSortByItem(SortByItem elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitByDirection(ByDirection elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitByColumn(ByColumn elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitByExpression(ByExpression elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitSortClause(SortClause elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitReturnClause(ReturnClause elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitQuery(Query elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitAliasRef(AliasRef elm, C context) {
        this.debug(elm);
        return elm;
    }

    @Override
    public Element visitQueryLetRef(QueryLetRef elm, C context) {
        this.debug(elm);
        return elm;
    }
}
