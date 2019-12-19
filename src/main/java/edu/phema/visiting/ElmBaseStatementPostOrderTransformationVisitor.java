package edu.phema.visiting;

import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.cqframework.cql.elm.visiting.ElmLibraryVisitor;
import org.hl7.elm.r1.*;

import java.util.ArrayList;

public class ElmBaseStatementPostOrderTransformationVisitor<T, C extends ElmBaseStatementPostOrderTransformationContext> extends ElmBaseLibraryVisitor<T, C> implements ElmLibraryVisitor<T, C> {
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

    private void debug(String method) {
        if (debug) {
            System.out.println(String.format("In method: %s", method));
        }
    }

    private void warn(String s) {
        System.out.println(s);
    }

    @Override
    public T visitBinaryExpression(BinaryExpression elm, C context) {
        this.debug("visitBinaryExpression");

        for (int i = 0; i < elm.getOperand().size(); i++) {
            context.pushParent(elm, i);
            this.visitExpression(elm.getOperand().get(i), context);
            context.popParent();
        }

        return super.visitBinaryExpression(elm, context);
    }

    @Override
    public T visitElement(org.hl7.elm.r1.Element elm, C context) {
        this.debug("visitElement");
        return super.visitElement(elm, context);
    }

    @Override
    public T visitExpression(Expression elm, C context) {
        this.debug("visitExpression");
        return super.visitExpression(elm, context);
    }

    @Override
    public T visitUnaryExpression(UnaryExpression elm, C context) {
        this.debug("visitUnaryExpression");

        context.pushParent(elm);

        this.visitExpression(elm.getOperand(), context);

        context.popParent();

        return super.visitUnaryExpression(elm, context);
    }

    @Override
    public T visitTypeSpecifier(TypeSpecifier elm, C context) {
        this.debug("visitTypeSpecifier");
        return super.visitTypeSpecifier(elm, context);
    }

    @Override
    public T visitNamedTypeSpecifier(NamedTypeSpecifier elm, C context) {
        this.debug("visitNamedTypeSpecifier");
        return super.visitNamedTypeSpecifier(elm, context);
    }

    @Override
    public T visitIntervalTypeSpecifier(IntervalTypeSpecifier elm, C context) {
        this.debug("visitIntervalTypeSpecifier");
        return super.visitIntervalTypeSpecifier(elm, context);
    }

    @Override
    public T visitListTypeSpecifier(ListTypeSpecifier elm, C context) {
        this.debug("visitListTypeSpecifier");
        return super.visitListTypeSpecifier(elm, context);
    }

    @Override
    public T visitTupleElementDefinition(TupleElementDefinition elm, C context) {
        this.debug("visitTupleElementDefinition");
        return super.visitTupleElementDefinition(elm, context);
    }

    @Override
    public T visitTupleTypeSpecifier(TupleTypeSpecifier elm, C context) {
        this.debug("visitTupleTypeSpecifier");
        return super.visitTupleTypeSpecifier(elm, context);
    }

    @Override
    public T visitTernaryExpression(TernaryExpression elm, C context) {
        this.debug("visitTernaryExpression");

        for (int i = 0; i < elm.getOperand().size(); i++) {
            context.pushParent(elm, i);
            this.visitExpression(elm.getOperand().get(i), context);
            context.popParent();
        }

        return super.visitTernaryExpression(elm, context);
    }

    @Override
    public T visitNaryExpression(NaryExpression elm, C context) {
        this.debug("visitNaryExpression");

        for (int i = 0; i < elm.getOperand().size(); i++) {
            context.pushParent(elm, i);
            this.visitExpression(elm.getOperand().get(i), context);
            context.popParent();
        }

        return super.visitNaryExpression(elm, context);
    }

    @Override
    public T visitExpressionDef(ExpressionDef elm, C context) {
        this.debug("visitExpressionDef");

        context.pushParent(elm);

        this.visitExpression(elm.getExpression(), context);

        context.popParent();

        return null;
    }

    @Override
    public T visitFunctionDef(FunctionDef elm, C context) {
        this.debug("visitFunctionDef");
        this.warn("FunctionDef not handled correctly");
        return super.visitFunctionDef(elm, context);
    }

    @Override
    public T visitFunctionRef(FunctionRef elm, C context) {
        this.debug("visitFunctionRef");
        this.warn("FunctionRef not handled correctly");
        return super.visitFunctionRef(elm, context);
    }

    @Override
    public T visitParameterDef(ParameterDef elm, C context) {
        this.debug("visitParameterDef");
        this.warn("ParameterDef not handled correctly");
        return super.visitParameterDef(elm, context);
    }

    @Override
    public T visitParameterRef(ParameterRef elm, C context) {
        this.debug("visitParameterRef");
        this.warn("ParameterRef not handled correctly");
        return super.visitParameterRef(elm, context);
    }

    @Override
    public T visitOperandDef(OperandDef elm, C context) {
        this.debug("visitOperandDef");
        this.warn("OperandDef not handled correctly");
        return super.visitOperandDef(elm, context);
    }

    @Override
    public T visitTupleElement(TupleElement elm, C context) {
        this.debug("visitTupleElement");
        this.warn("TupleElement not handled correctly!");
        return super.visitTupleElement(elm, context);
    }

    @Override
    public T visitTuple(Tuple elm, C context) {
        this.debug("visitTuple");
        this.warn("Tuple not handled correctly!");
        return super.visitTuple(elm, context);
    }

    @Override
    public T visitInstanceElement(InstanceElement elm, C context) {
        this.debug("visitInstanceElement");
        this.warn("InstanceElement not handled correctly!");
        return super.visitInstanceElement(elm, context);
    }

    @Override
    public T visitInstance(Instance elm, C context) {
        this.debug("visitInstance");
        this.warn("Instance not handled correctly!");
        return super.visitInstance(elm, context);
    }

    @Override
    public T visitInterval(Interval elm, C context) {
        this.debug("visitInterval");
        this.warn("Interval not handled correctly!");
        return super.visitInterval(elm, context);
    }

    @Override
    public T visitList(List elm, C context) {
        this.debug("visitList");
        this.warn("List not handled correctly!");
        return super.visitList(elm, context);
    }

    @Override
    public T visitLibrary(Library elm, C context) {
        debug("visitLibrary");
        return super.visitLibrary(elm, context);
    }

    @Override
    public T visitUsingDef(UsingDef elm, C context) {
        debug("visitUsingDef");
        return super.visitUsingDef(elm, context);
    }

    @Override
    public T visitIncludeDef(IncludeDef elm, C context) {
        debug("visitIncludeDef");
        return super.visitIncludeDef(elm, context);
    }

    @Override
    public T visitRetrieve(Retrieve elm, C context) {
        debug("visitRetrieve");
        return super.visitRetrieve(elm, context);
    }

    @Override
    public T visitCodeSystemDef(CodeSystemDef elm, C context) {
        debug("visitCodeSystemDef");
        return super.visitCodeSystemDef(elm, context);
    }

    @Override
    public T visitValueSetDef(ValueSetDef elm, C context) {
        debug("visitValueSetDef");
        return super.visitValueSetDef(elm, context);
    }

    @Override
    public T visitCodeSystemRef(CodeSystemRef elm, C context) {
        debug("visitCodeSystemRef");
        return super.visitCodeSystemRef(elm, context);
    }

    @Override
    public T visitValueSetRef(ValueSetRef elm, C context) {
        debug("visitValueSetRef");
        return super.visitValueSetRef(elm, context);
    }

    @Override
    public T visitCode(Code elm, C context) {
        debug("visitCode");
        return super.visitCode(elm, context);
    }

    @Override
    public T visitConcept(Concept elm, C context) {
        debug("visitConcept");
        return super.visitConcept(elm, context);
    }

    @Override
    public T visitInCodeSystem(InCodeSystem elm, C context) {
        debug("visitInCodeSystem");
        return super.visitInCodeSystem(elm, context);
    }

    @Override
    public T visitInValueSet(InValueSet elm, C context) {
        debug("visitInValueSet");
        return super.visitInValueSet(elm, context);
    }

    @Override
    public T visitQuantity(Quantity elm, C context) {
        debug("visitQuantity");
        return super.visitQuantity(elm, context);
    }

    @Override
    public T visitCalculateAge(CalculateAge elm, C context) {
        debug("visitCalculateAge");
        return super.visitCalculateAge(elm, context);
    }

    @Override
    public T visitCalculateAgeAt(CalculateAgeAt elm, C context) {
        debug("visitCalculateAgeAt");
        return super.visitCalculateAgeAt(elm, context);
    }

    @Override
    public T visitExpressionRef(ExpressionRef elm, C context) {
        debug("visitExpressionRef");
        return super.visitExpressionRef(elm, context);
    }

    @Override
    public T visitOperandRef(OperandRef elm, C context) {
        debug("visitOperandRef");
        return super.visitOperandRef(elm, context);
    }

    @Override
    public T visitIdentifierRef(IdentifierRef elm, C context) {
        debug("visitIdentifierRef");
        return super.visitIdentifierRef(elm, context);
    }

    @Override
    public T visitLiteral(Literal elm, C context) {
        debug("visitLiteral");
        return super.visitLiteral(elm, context);
    }

    @Override
    public T visitAnd(And elm, C context) {
        debug("visitAnd");
        return super.visitAnd(elm, context);
    }

    @Override
    public T visitOr(Or elm, C context) {
        debug("visitOr");
        return super.visitOr(elm, context);
    }

    @Override
    public T visitXor(Xor elm, C context) {
        debug("visitXor");
        return super.visitXor(elm, context);
    }

    @Override
    public T visitNot(Not elm, C context) {
        debug("visitNot");
        return super.visitNot(elm, context);
    }

    @Override
    public T visitIf(If elm, C context) {
        debug("visitIf");
        return super.visitIf(elm, context);
    }

    @Override
    public T visitCaseItem(CaseItem elm, C context) {
        debug("visitCaseItem");
        return super.visitCaseItem(elm, context);
    }

    @Override
    public T visitCase(Case elm, C context) {
        debug("visitCase");
        return super.visitCase(elm, context);
    }

    @Override
    public T visitNull(Null elm, C context) {
        debug("visitNull");
        return super.visitNull(elm, context);
    }

    @Override
    public T visitIsNull(IsNull elm, C context) {
        debug("visitIsNull");
        return super.visitIsNull(elm, context);
    }

    @Override
    public T visitIsTrue(IsTrue elm, C context) {
        debug("visitIsTrue");
        return super.visitIsTrue(elm, context);
    }

    @Override
    public T visitIsFalse(IsFalse elm, C context) {
        debug("visitIsFalse");
        return super.visitIsFalse(elm, context);
    }

    @Override
    public T visitCoalesce(Coalesce elm, C context) {
        debug("visitCoalesce");
        return super.visitCoalesce(elm, context);
    }

    @Override
    public T visitIs(Is elm, C context) {
        debug("visitIs");
        return super.visitIs(elm, context);
    }

    @Override
    public T visitAs(As elm, C context) {
        debug("visitAs");
        return super.visitAs(elm, context);
    }

    @Override
    public T visitConvert(Convert elm, C context) {
        debug("visitConvert");
        return super.visitConvert(elm, context);
    }

    @Override
    public T visitToBoolean(ToBoolean elm, C context) {
        debug("visitToBoolean");
        return super.visitToBoolean(elm, context);
    }

    @Override
    public T visitToConcept(ToConcept elm, C context) {
        debug("visitToConcept");
        return super.visitToConcept(elm, context);
    }

    @Override
    public T visitToDateTime(ToDateTime elm, C context) {
        debug("visitToDateTime");
        return super.visitToDateTime(elm, context);
    }

    @Override
    public T visitToDecimal(ToDecimal elm, C context) {
        debug("visitToDecimal");
        return super.visitToDecimal(elm, context);
    }

    @Override
    public T visitToInteger(ToInteger elm, C context) {
        debug("visitToInteger");
        return super.visitToInteger(elm, context);
    }

    @Override
    public T visitToQuantity(ToQuantity elm, C context) {
        debug("visitToQuantity");
        return super.visitToQuantity(elm, context);
    }

    @Override
    public T visitToString(ToString elm, C context) {
        debug("visitToString");
        return super.visitToString(elm, context);
    }

    @Override
    public T visitToTime(ToTime elm, C context) {
        debug("visitToTime");
        return super.visitToTime(elm, context);
    }

    @Override
    public T visitEqual(Equal elm, C context) {
        debug("visitEqual");
        return super.visitEqual(elm, context);
    }

    @Override
    public T visitEquivalent(Equivalent elm, C context) {
        debug("visitEquivalent");
        return super.visitEquivalent(elm, context);
    }

    @Override
    public T visitNotEqual(NotEqual elm, C context) {
        debug("visitNotEqual");
        return super.visitNotEqual(elm, context);
    }

    @Override
    public T visitLess(Less elm, C context) {
        debug("visitLess");
        return super.visitLess(elm, context);
    }

    @Override
    public T visitGreater(Greater elm, C context) {
        debug("visitGreater");
        return super.visitGreater(elm, context);
    }

    @Override
    public T visitLessOrEqual(LessOrEqual elm, C context) {
        debug("visitLessOrEqual");
        return super.visitLessOrEqual(elm, context);
    }

    @Override
    public T visitGreaterOrEqual(GreaterOrEqual elm, C context) {
        debug("visitGreaterOrEqual");
        return super.visitGreaterOrEqual(elm, context);
    }

    @Override
    public T visitAdd(Add elm, C context) {
        debug("visitAdd");
        return super.visitAdd(elm, context);
    }

    @Override
    public T visitSubtract(Subtract elm, C context) {
        debug("visitSubtract");
        return super.visitSubtract(elm, context);
    }

    @Override
    public T visitMultiply(Multiply elm, C context) {
        debug("visitMultiply");
        return super.visitMultiply(elm, context);
    }

    @Override
    public T visitDivide(Divide elm, C context) {
        debug("visitDivide");
        return super.visitDivide(elm, context);
    }

    @Override
    public T visitTruncatedDivide(TruncatedDivide elm, C context) {
        debug("visitTruncatedDivide");
        return super.visitTruncatedDivide(elm, context);
    }

    @Override
    public T visitModulo(Modulo elm, C context) {
        debug("visitModulo");
        return super.visitModulo(elm, context);
    }

    @Override
    public T visitCeiling(Ceiling elm, C context) {
        debug("visitCeiling");
        return super.visitCeiling(elm, context);
    }

    @Override
    public T visitFloor(Floor elm, C context) {
        debug("visitFloor");
        return super.visitFloor(elm, context);
    }

    @Override
    public T visitTruncate(Truncate elm, C context) {
        debug("visitTruncate");
        return super.visitTruncate(elm, context);
    }

    @Override
    public T visitAbs(Abs elm, C context) {
        debug("visitAbs");
        return super.visitAbs(elm, context);
    }

    @Override
    public T visitNegate(Negate elm, C context) {
        debug("visitNegate");
        return super.visitNegate(elm, context);
    }

    @Override
    public T visitRound(Round elm, C context) {
        debug("visitRound");
        return super.visitRound(elm, context);
    }

    @Override
    public T visitLn(Ln elm, C context) {
        debug("visitLn");
        return super.visitLn(elm, context);
    }

    @Override
    public T visitExp(Exp elm, C context) {
        debug("visitExp");
        return super.visitExp(elm, context);
    }

    @Override
    public T visitLog(Log elm, C context) {
        debug("visitLog");
        return super.visitLog(elm, context);
    }

    @Override
    public T visitPower(Power elm, C context) {
        debug("visitPower");
        return super.visitPower(elm, context);
    }

    @Override
    public T visitSuccessor(Successor elm, C context) {
        debug("visitSuccessor");
        return super.visitSuccessor(elm, context);
    }

    @Override
    public T visitPredecessor(Predecessor elm, C context) {
        debug("visitPredecessor");
        return super.visitPredecessor(elm, context);
    }

    @Override
    public T visitMinValue(MinValue elm, C context) {
        debug("visitMinValue");
        return super.visitMinValue(elm, context);
    }

    @Override
    public T visitMaxValue(MaxValue elm, C context) {
        debug("visitMaxValue");
        return super.visitMaxValue(elm, context);
    }

    @Override
    public T visitConcatenate(Concatenate elm, C context) {
        debug("visitConcatenate");
        return super.visitConcatenate(elm, context);
    }

    @Override
    public T visitCombine(Combine elm, C context) {
        debug("visitCombine");
        return super.visitCombine(elm, context);
    }

    @Override
    public T visitSplit(Split elm, C context) {
        debug("visitSplit");
        return super.visitSplit(elm, context);
    }

    @Override
    public T visitLength(Length elm, C context) {
        debug("visitLength");
        return super.visitLength(elm, context);
    }

    @Override
    public T visitUpper(Upper elm, C context) {
        debug("visitUpper");
        return super.visitUpper(elm, context);
    }

    @Override
    public T visitLower(Lower elm, C context) {
        debug("visitLower");
        return super.visitLower(elm, context);
    }

    @Override
    public T visitIndexer(Indexer elm, C context) {
        debug("visitIndexer");
        return super.visitIndexer(elm, context);
    }

    @Override
    public T visitPositionOf(PositionOf elm, C context) {
        debug("visitPositionOf");
        return super.visitPositionOf(elm, context);
    }

    @Override
    public T visitSubstring(Substring elm, C context) {
        debug("visitSubstring");
        return super.visitSubstring(elm, context);
    }

    @Override
    public T visitDurationBetween(DurationBetween elm, C context) {
        debug("visitDurationBetween");
        return super.visitDurationBetween(elm, context);
    }

    @Override
    public T visitDifferenceBetween(DifferenceBetween elm, C context) {
        debug("visitDifferenceBetween");
        return super.visitDifferenceBetween(elm, context);
    }

    @Override
    public T visitDateFrom(DateFrom elm, C context) {
        debug("visitDateFrom");
        return super.visitDateFrom(elm, context);
    }

    @Override
    public T visitTimeFrom(TimeFrom elm, C context) {
        debug("visitTimeFrom");
        return super.visitTimeFrom(elm, context);
    }

    @Override
    public T visitTimezoneOffsetFrom(TimezoneOffsetFrom elm, C context) {
        debug("visitTimezoneOffsetFrom");
        return super.visitTimezoneOffsetFrom(elm, context);
    }

    @Override
    public T visitDateTimeComponentFrom(DateTimeComponentFrom elm, C context) {
        debug("visitDateTimeComponentFrom");
        return super.visitDateTimeComponentFrom(elm, context);
    }

    @Override
    public T visitTimeOfDay(TimeOfDay elm, C context) {
        debug("visitTimeOfDay");
        return super.visitTimeOfDay(elm, context);
    }

    @Override
    public T visitToday(Today elm, C context) {
        debug("visitToday");
        return super.visitToday(elm, context);
    }

    @Override
    public T visitNow(Now elm, C context) {
        debug("visitNow");
        return super.visitNow(elm, context);
    }

    @Override
    public T visitDateTime(DateTime elm, C context) {
        debug("visitDateTime");
        return super.visitDateTime(elm, context);
    }

    @Override
    public T visitTime(Time elm, C context) {
        debug("visitTime");
        return super.visitTime(elm, context);
    }

    @Override
    public T visitSameAs(SameAs elm, C context) {
        debug("visitSameAs");
        return super.visitSameAs(elm, context);
    }

    @Override
    public T visitSameOrBefore(SameOrBefore elm, C context) {
        debug("visitSameOrBefore");
        return super.visitSameOrBefore(elm, context);
    }

    @Override
    public T visitSameOrAfter(SameOrAfter elm, C context) {
        debug("visitSameOrAfter");
        return super.visitSameOrAfter(elm, context);
    }

    @Override
    public T visitWidth(Width elm, C context) {
        debug("visitWidth");
        return super.visitWidth(elm, context);
    }

    @Override
    public T visitStart(Start elm, C context) {
        debug("visitStart");
        return super.visitStart(elm, context);
    }

    @Override
    public T visitEnd(End elm, C context) {
        debug("visitEnd");
        return super.visitEnd(elm, context);
    }

    @Override
    public T visitContains(Contains elm, C context) {
        debug("visitContains");
        return super.visitContains(elm, context);
    }

    @Override
    public T visitProperContains(ProperContains elm, C context) {
        debug("visitProperContains");
        return super.visitProperContains(elm, context);
    }

    @Override
    public T visitIn(In elm, C context) {
        debug("visitIn");
        return super.visitIn(elm, context);
    }

    @Override
    public T visitProperIn(ProperIn elm, C context) {
        debug("visitProperIn");
        return super.visitProperIn(elm, context);
    }

    @Override
    public T visitIncludes(Includes elm, C context) {
        debug("visitIncludes");
        return super.visitIncludes(elm, context);
    }

    @Override
    public T visitIncludedIn(IncludedIn elm, C context) {
        debug("visitIncludedIn");
        return super.visitIncludedIn(elm, context);
    }

    @Override
    public T visitProperIncludes(ProperIncludes elm, C context) {
        debug("visitProperIncludes");
        return super.visitProperIncludes(elm, context);
    }

    @Override
    public T visitProperIncludedIn(ProperIncludedIn elm, C context) {
        debug("visitProperIncludedIn");
        return super.visitProperIncludedIn(elm, context);
    }

    @Override
    public T visitBefore(Before elm, C context) {
        debug("visitBefore");
        return super.visitBefore(elm, context);
    }

    @Override
    public T visitAfter(After elm, C context) {
        debug("visitAfter");
        return super.visitAfter(elm, context);
    }

    @Override
    public T visitMeets(Meets elm, C context) {
        debug("visitMeets");
        return super.visitMeets(elm, context);
    }

    @Override
    public T visitMeetsBefore(MeetsBefore elm, C context) {
        debug("visitMeetsBefore");
        return super.visitMeetsBefore(elm, context);
    }

    @Override
    public T visitMeetsAfter(MeetsAfter elm, C context) {
        debug("visitMeetsAfter");
        return super.visitMeetsAfter(elm, context);
    }

    @Override
    public T visitOverlaps(Overlaps elm, C context) {
        debug("visitOverlaps");
        return super.visitOverlaps(elm, context);
    }

    @Override
    public T visitOverlapsBefore(OverlapsBefore elm, C context) {
        debug("visitOverlapsBefore");
        return super.visitOverlapsBefore(elm, context);
    }

    @Override
    public T visitOverlapsAfter(OverlapsAfter elm, C context) {
        debug("visitOverlapsAfter");
        return super.visitOverlapsAfter(elm, context);
    }

    @Override
    public T visitStarts(Starts elm, C context) {
        debug("visitStarts");
        return super.visitStarts(elm, context);
    }

    @Override
    public T visitEnds(Ends elm, C context) {
        debug("visitEnds");
        return super.visitEnds(elm, context);
    }

    @Override
    public T visitCollapse(Collapse elm, C context) {
        debug("visitCollapse");
        return super.visitCollapse(elm, context);
    }

    @Override
    public T visitUnion(Union elm, C context) {
        debug("visitUnion");
        return super.visitUnion(elm, context);
    }

    @Override
    public T visitIntersect(Intersect elm, C context) {
        debug("visitIntersect");
        return super.visitIntersect(elm, context);
    }

    @Override
    public T visitExcept(Except elm, C context) {
        debug("visitExcept");
        return super.visitExcept(elm, context);
    }

    @Override
    public T visitExists(Exists elm, C context) {
        debug("visitExists");
        return super.visitExists(elm, context);
    }

    @Override
    public T visitTimes(Times elm, C context) {
        debug("visitTimes");
        return super.visitTimes(elm, context);
    }

    @Override
    public T visitFilter(Filter elm, C context) {
        debug("visitFilter");
        return super.visitFilter(elm, context);
    }

    @Override
    public T visitFirst(First elm, C context) {
        debug("visitFirst");
        return super.visitFirst(elm, context);
    }

    @Override
    public T visitLast(Last elm, C context) {
        debug("visitLast");
        return super.visitLast(elm, context);
    }

    @Override
    public T visitIndexOf(IndexOf elm, C context) {
        debug("visitIndexOf");
        return super.visitIndexOf(elm, context);
    }

    @Override
    public T visitFlatten(Flatten elm, C context) {
        debug("visitFlatten");
        return super.visitFlatten(elm, context);
    }

    @Override
    public T visitSort(Sort elm, C context) {
        debug("visitSort");
        return super.visitSort(elm, context);
    }

    @Override
    public T visitForEach(ForEach elm, C context) {
        debug("visitForEach");
        return super.visitForEach(elm, context);
    }

    @Override
    public T visitDistinct(Distinct elm, C context) {
        debug("visitDistinct");
        return super.visitDistinct(elm, context);
    }

    @Override
    public T visitCurrent(Current elm, C context) {
        debug("visitCurrent");
        return super.visitCurrent(elm, context);
    }

    @Override
    public T visitSingletonFrom(SingletonFrom elm, C context) {
        debug("visitSingletonFrom");
        return super.visitSingletonFrom(elm, context);
    }

    @Override
    public T visitAggregateExpression(AggregateExpression elm, C context) {
        debug("visitAggregateExpression");

        context.pushParent(elm, -1);
        this.visitExpression(elm.getSource(), context);
        context.popParent();

        return super.visitAggregateExpression(elm, context);
    }

    @Override
    public T visitCount(Count elm, C context) {
        debug("visitCount");
        return super.visitCount(elm, context);
    }

    @Override
    public T visitSum(Sum elm, C context) {
        debug("visitSum");
        return super.visitSum(elm, context);
    }

    @Override
    public T visitMin(Min elm, C context) {
        debug("visitMin");
        return super.visitMin(elm, context);
    }

    @Override
    public T visitMax(Max elm, C context) {
        debug("visitMax");
        return super.visitMax(elm, context);
    }

    @Override
    public T visitAvg(Avg elm, C context) {
        debug("visitAvg");
        return super.visitAvg(elm, context);
    }

    @Override
    public T visitMedian(Median elm, C context) {
        debug("visitMedian");
        return super.visitMedian(elm, context);
    }

    @Override
    public T visitMode(Mode elm, C context) {
        debug("visitMode");
        return super.visitMode(elm, context);
    }

    @Override
    public T visitVariance(Variance elm, C context) {
        debug("visitVariance");
        return super.visitVariance(elm, context);
    }

    @Override
    public T visitPopulationVariance(PopulationVariance elm, C context) {
        debug("visitPopulationVariance");
        return super.visitPopulationVariance(elm, context);
    }

    @Override
    public T visitStdDev(StdDev elm, C context) {
        debug("visitStdDev");
        return super.visitStdDev(elm, context);
    }

    @Override
    public T visitPopulationStdDev(PopulationStdDev elm, C context) {
        debug("visitPopulationStdDev");
        return super.visitPopulationStdDev(elm, context);
    }

    @Override
    public T visitAllTrue(AllTrue elm, C context) {
        debug("visitAllTrue");
        return super.visitAllTrue(elm, context);
    }

    @Override
    public T visitAnyTrue(AnyTrue elm, C context) {
        debug("visitAnyTrue");
        return super.visitAnyTrue(elm, context);
    }

    @Override
    public T visitProperty(Property elm, C context) {
        debug("visitProperty");
        return super.visitProperty(elm, context);
    }

    @Override
    public T visitAliasedQuerySource(AliasedQuerySource elm, C context) {
        debug("visitAliasedQuerySource");

        context.pushParent(elm, -1);

        this.visitExpression(elm.getExpression(), context);

        context.popParent();

        return super.visitAliasedQuerySource(elm, context);
    }

    @Override
    public T visitLetClause(LetClause elm, C context) {
        debug("visitLetClause");
        return super.visitLetClause(elm, context);
    }

    @Override
    public T visitRelationshipClause(RelationshipClause elm, C context) {
        debug("visitRelationshipClause");
        return super.visitRelationshipClause(elm, context);
    }

    @Override
    public T visitWith(With elm, C context) {
        debug("visitWith");
        return super.visitWith(elm, context);
    }

    @Override
    public T visitWithout(Without elm, C context) {
        debug("visitWithout");
        return super.visitWithout(elm, context);
    }

    @Override
    public T visitSortByItem(SortByItem elm, C context) {
        debug("visitSortByItem");
        return super.visitSortByItem(elm, context);
    }

    @Override
    public T visitByDirection(ByDirection elm, C context) {
        debug("visitByDirection");
        return super.visitByDirection(elm, context);
    }

    @Override
    public T visitByColumn(ByColumn elm, C context) {
        debug("visitByColumn");
        return super.visitByColumn(elm, context);
    }

    @Override
    public T visitByExpression(ByExpression elm, C context) {
        debug("visitByExpression");
        return super.visitByExpression(elm, context);
    }

    @Override
    public T visitSortClause(SortClause elm, C context) {
        debug("visitSortClause");
        return super.visitSortClause(elm, context);
    }

    @Override
    public T visitReturnClause(ReturnClause elm, C context) {
        debug("visitReturnClause");
        return super.visitReturnClause(elm, context);
    }

    @Override
    public T visitQuery(Query elm, C context) {
        debug("visitQuery");

        /*
            @XmlElement(namespace = "urn:hl7-org:elm:r1", required = true)
    protected List<AliasedQuerySource> source;
    @XmlElement(namespace = "urn:hl7-org:elm:r1")
    protected List<LetClause> let;
    @XmlElement(namespace = "urn:hl7-org:elm:r1")
    protected List<RelationshipClause> relationship;
    @XmlElement(namespace = "urn:hl7-org:elm:r1")
    protected Expression where;
    @XmlElement(name = "return", namespace = "urn:hl7-org:elm:r1")
    protected ReturnClause _return;
    @XmlElement(namespace = "urn:hl7-org:elm:r1")
    protected SortClause sort;    @XmlElement(namespace = "urn:hl7-org:elm:r1", required = true)
    protected List<AliasedQuerySource> source;
    @XmlElement(namespace = "urn:hl7-org:elm:r1")
    protected List<LetClause> let;
    @XmlElement(namespace = "urn:hl7-org:elm:r1")
    protected List<RelationshipClause> relationship;
    @XmlElement(namespace = "urn:hl7-org:elm:r1")
    protected Expression where;
    @XmlElement(name = "return", namespace = "urn:hl7-org:elm:r1")
    protected ReturnClause _return;
    @XmlElement(namespace = "urn:hl7-org:elm:r1")
    protected SortClause sort;
         */
        context.pushParent(elm, -1);

        elm.getSource().stream().forEach(s -> this.visitAliasedQuerySource(s, context));

        context.popParent();


        return super.visitQuery(elm, context);
    }

    @Override
    public T visitAliasRef(AliasRef elm, C context) {
        debug("visitAliasRef");
        return super.visitAliasRef(elm, context);
    }

    @Override
    public T visitQueryLetRef(QueryLetRef elm, C context) {
        debug("visitQueryLetRef");
        return super.visitQueryLetRef(elm, context);
    }
}
