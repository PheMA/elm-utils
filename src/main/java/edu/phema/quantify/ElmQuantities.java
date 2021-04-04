package edu.phema.quantify;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class ElmQuantities {
  public Stack<PhemaAnalysisDepths> depths;
  public PhemaAnalysisDimensions dimensions;

  public ElmQuantities() {
    depths = new Stack<>();

    // push the global depth counter
    depths.push(new PhemaAnalysisDepths());

    dimensions = new PhemaAnalysisDimensions();
  }


  /////////////////////////////////////////////////////////////////////////////
  //
  // ANALYSIS DIMENSIONS: Object for counting relevant metrics in a given context
  //
  /////////////////////////////////////////////////////////////////////////////
  public class PhemaAnalysisDepths {
    // Logical expressions
    public int phemaLogicalMaxDepth = 0;
    private int phemaLogicalDepth = 0;

    public void incrementPhemaLogicalDepth() {
      phemaLogicalDepth++;

      if (phemaLogicalDepth > phemaLogicalMaxDepth) {
        phemaLogicalMaxDepth = phemaLogicalDepth;
      }
    }

    public void decrementPhemaLogicalDepth() {
      phemaLogicalDepth--;
    }

    // Comparison expressions
    public int phemaComparisonMaxDepth = 0;
    private int phemaComparisonDepth = 0;

    public void incrementPhemaComparisonDepth() {
      phemaComparisonDepth++;

      if (phemaComparisonDepth > phemaComparisonMaxDepth) {
        phemaComparisonMaxDepth = phemaComparisonDepth;
      }
    }

    public void decrementPhemaComparisonDepth() {
      phemaComparisonDepth--;
    }

    // Arithmetic expressions
    public int phemaArithmeticMaxDepth = 0;
    private int phemaArithmeticDepth = 0;

    public void incrementPhemaArithmeticDepth() {
      phemaArithmeticDepth++;

      if (phemaArithmeticDepth > phemaArithmeticMaxDepth) {
        phemaArithmeticMaxDepth = phemaArithmeticDepth;
      }
    }

    public void decrementPhemaArithmeticDepth() {
      phemaArithmeticDepth--;
    }

    // Aggregate expressions
    public int phemaAggregateMaxDepth = 0;
    private int phemaAggregateDepth = 0;

    public void incrementPhemaAggregateDepth() {
      phemaAggregateDepth++;

      if (phemaAggregateDepth > phemaAggregateMaxDepth) {
        phemaAggregateMaxDepth = phemaAggregateDepth;
      }
    }

    public void decrementPhemaAggregateDepth() {
      phemaAggregateDepth--;
    }

    // Data expressions
    public int whereClauseMaxDepth = 0;
    private int whereClauseDepth = 0;

    public void setWhereClauseDepth(int depth) {
      whereClauseDepth = depth;

      if (whereClauseDepth > whereClauseMaxDepth) {
        whereClauseMaxDepth = whereClauseDepth;
      }
    }

    // Conditional expressions
    public int phemaConditionalMaxDepth = 0;
    private int phemaConditionalDepth = 0;

    public void incrementPhemaConditionalDepth() {
      phemaConditionalDepth++;

      if (phemaConditionalDepth > phemaConditionalMaxDepth) {
        phemaConditionalMaxDepth = phemaConditionalDepth;
      }
    }

    public void decrementPhemaConditionalDepth() {
      phemaConditionalDepth--;
    }

    // Temporal expressions
    public int phemaTemporalMaxDepth = 0;
    private int phemaTemporalDepth = 0;

    public void incrementPhemaTemporalDepth() {
      phemaTemporalDepth++;

      if (phemaTemporalDepth > phemaTemporalMaxDepth) {
        phemaTemporalMaxDepth = phemaTemporalDepth;
      }
    }

    public void decrementPhemaTemporalDepth() {
      phemaTemporalDepth--;
    }

    // Modularity expressions
    public int phemaExpressionMaxDepth = 0;
    private int phemaExpressionDepth = 0;

    public void incrementPhemaExpressionDepth() {
      phemaExpressionDepth++;

      if (phemaExpressionDepth > phemaExpressionMaxDepth) {
        phemaExpressionMaxDepth = phemaExpressionDepth;
      }
    }

    public void decrementPhemaExpressionDepth() {
      phemaExpressionDepth--;
    }

    // Terminology expressions
    public int phemaTerminologyMaxDepth = 0;
    private int phemaTerminologyDepth = 0;

    public void incrementPhemaTerminologyDepth() {
      phemaTerminologyDepth++;

      if (phemaTerminologyDepth > phemaTerminologyMaxDepth) {
        phemaTerminologyMaxDepth = phemaTerminologyDepth;
      }
    }

    public void decrementPhemaTerminologyDepth() {
      phemaTerminologyDepth--;
    }

    // Collection expressions
    public int phemaCollectionMaxDepth = 0;
    private int phemaCollectionDepth = 0;

    public void incrementPhemaCollectionDepth() {
      phemaCollectionDepth++;

      if (phemaCollectionDepth > phemaCollectionMaxDepth) {
        phemaCollectionMaxDepth = phemaCollectionDepth;
      }
    }

    public void decrementPhemaCollectionDepth() {
      phemaCollectionDepth--;
    }
  }

  public class PhemaAnalysisDimensions {
    // Literals
    public PhemaLiteralCounts phemaLiteralCounts = new PhemaLiteralCounts();

    // Logical expressions
    public PhemaLogicalCounts phemaLogicalCounts = new PhemaLogicalCounts();

    // Comparison expressions
    public PhemaComparisonCounts phemaComparisonCounts = new PhemaComparisonCounts();

    // Arithmetic expressions
    public PhemaArithmeticCounts phemaArithmeticCounts = new PhemaArithmeticCounts();

    // Aggregate expressions
    public PhemaAggregateCounts phemaAggregateCounts = new PhemaAggregateCounts();

    // Data expressions
    public PhemaDataCounts phemaDataCounts = new PhemaDataCounts();

    // Conditional expressions
    public PhemaConditionalCounts phemaConditionalCounts = new PhemaConditionalCounts();

    // Temporal expressions
    public PhemaTemporalCounts phemaTemporalCounts = new PhemaTemporalCounts();

    // Modularity
    public PhemaModularityCounts phemaModularityCounts = new PhemaModularityCounts();

    // Terminology
    public PhemaTerminologyCounts phemaTerminologyCounts = new PhemaTerminologyCounts();

    // Collections
    public PhemaCollectionCounts phemaCollectionCounts = new PhemaCollectionCounts();
  }

  /////////////////////////////////////////////////////////////////////////////
  //
  // DERIVED COUNTS: The stuff we want to report
  //
  /////////////////////////////////////////////////////////////////////////////
  // Used to count both simple and clinical values
  public class PhemaLiteralCounts {
    public int total;
    public Set<String> types = new HashSet<>();
  }

  public class PhemaLogicalCounts {
    public int and;
    public int or;
    public int not;
    public int implies;
    public int xor;
  }

  public class PhemaComparisonCounts {
    public int equal;
    public int equivalent;
    public int notEqual;
    public int less;
    public int greater;
    public int lessOrEqual;
    public int greaterOrEqual;
  }

  public class PhemaArithmeticCounts {
    public int add;
    public int subtract;
    public int multiply;
    public int divide;
    public int truncatedDivide;
    public int modulo;
    public int ceiling;
    public int floor;
    public int truncate;
    public int abs;
    public int negate;
    public int round;
    public int ln;
    public int exp;
    public int log;
    public int power;
    public int successor;
    public int predecessor;
    public int minValue;
    public int maxValue;
    public int precision;
    public int lowBoundary;
    public int highBoundary;
    public int total;
  }

  public class PhemaAggregateCounts {
    public int product;
    public int geometricMean;
    public int count;
    public int sum;
    public int min;
    public int max;
    public int avg;
    public int median;
    public int mode;
    public int variance;
    public int populationVariance;
    public int stdDev;
    public int populationStdDev;
    public int allTrue;
    public int anyTrue;
  }

  public class PhemaDataCounts {
    public int retrieveTotal;
    public Set<String> dataSources = new HashSet<>();
    public int whereClauseExpressionMaxCount;
    public int whereClauseExpressionMaxDepth;
  }

  public class PhemaConditionalCounts {
    public int _if;
    public int _case;
  }

  public class PhemaTemporalCounts {
    // Allen's operators
    public int precedes;
    public int isPrecededBy;
    public int meets;
    public int isMetBy;
    public int overlapsWith;
    public int isOverlappedBy;
    public int starts;
    public int isStartedBy;
    public int during;
    public int contains;
    public int finishes;
    public int isFinishedBy;
    public int equals;

    // Patient age
    public int calculateAge;
    public int calculateAgeAt;

    // Other temporal operators
    public int durationBetween;
    public int differenceBetween;
    public int today;
    public int now;

    /*
    public int sameAs;        // equals
    public int sameOrBefore;  // meets
    public int sameOrAfter;   // isMetBy
     */
  }

  public class PhemaModularityCounts {
    // totals
    public int expression;
    public int statement;
    public int function;

    // local totals
    public int localStatement;
    public int localFunction;

    // external totals
    public int exteralStatement;
    public int exteralFunction;
  }

  public class PhemaTerminologyCounts {
    public int inCodeSystem;
    public int inValueSet;
    public int anyInCodeSystem;
    public int anyInValueSet;
    public int subsumes;
    public int subsumedBy;
  }

  public class PhemaCollectionCounts {
    // list operations
    public int exists;
    public int times;
    public int filter;
    public int first;
    public int last;
    public int indexOf;
    public int flatten;
    public int sort;
    public int forEach;
    public int distinct;
    public int current;
    public int singletonFrom;
    public int slice;
    public int repeat;
    public int iteration;

    // operations from intervals that also apply to lists
    public int contains;
    public int equal;
    public int equivalent;
    public int except;
    public int in;
    public int includes;
    public int includedIn;
    public int notEqual;
    public int properContains;
    public int properIn;
    public int properIncludes;
    public int properIncludedIn;
    public int union;
  }

  /////////////////////////////////////////////////////////////////////////////
  //
  // AUTOMATIC COUNTS: Raw ELM node counts
  //
  /////////////////////////////////////////////////////////////////////////////

  public class ElmExpressionCounts {
    public int binaryExpression;
    public int element;
    public int expression;
    public int operatorExpression;
    public int unaryExpression;
    public int ternaryExpression;
    public int naryExpression;
    public int typeSpecifier;
    public int aggregateExpression;
  }

  public class ElmLiteralCounts {
    public int literal;
    public int tuple;
    public int tupleElement;
    public int tupleElementDefinition;
    public int instance;
    public int instanceElement;
    public int property;
    public int search;
  }

  public class ElmClinicalValueCounts {
    public int codeSystemDef;
    public int valueSetDef;
    public int codeSystemRef;
    public int valueSetRef;
    public int code;
    public int concept;
    public int quantity;
    public int ratio;
    public int codeDef;
    public int conceptDef;
    public int codeRef;
    public int conceptRef;
  }

  public class ElmTypeSpecifierCounts {
    public int namedTypeSpecifier;
    public int intervalTypeSpecifier;
    public int listTypeSpecifier;
    public int tupleTypeSpecifier;
    public int choiceTypeSpecifier;
  }

  public class ElmReuseCounts {
    public int library;
    public int usingDef;
    public int includeDef;
    public int contextDef;
    public int parameterDef;
    public int parameterRef;
    public int expressionDef;
    public int functionDef;
    public int functionRef;
    public int operandDef;
    public int expressionRef;
    public int operandRef;
    public int identifierRef;
    public int accessModifier;
  }

  public class ElmQueryCounts {
    public int aliasedQuerySource;
    public int letClause;
    public int relationshipClause;
    public int with;
    public int without;
    public int sortByItem;
    public int byDirection;
    public int byColumn;
    public int byExpression;
    public int sortClause;
    public int returnClause;
    public int query;
    public int aliasRef;
    public int queryLetRef;
    public int retrieve;
    public int codeFilterElement;
    public int dateFilterElement;
    public int otherFilterElement;
    public int includeElement;
    public int aggregateClause;
  }

  public class ElmComparisonCounts {
    public int equal;
    public int equivalent;
    public int notEqual;
    public int less;
    public int greater;
    public int lessOrEqual;
    public int greaterOrEqual;
  }

  public class ElmLogicalCounts {
    public int and;
    public int or;
    public int xor;
    public int not;
    public int implies;
  }

  public class ElmNullCounts {
    public int _null;
    public int isNull;
    public int isTrue;
    public int isFalse;
    public int coalesce;
  }

  public class ElmConditionalCounts {
    public int _if;
    public int caseItem;
    public int _case;
  }

  public class ElmArithmeticCounts {
    public int add;
    public int subtract;
    public int multiply;
    public int divide;
    public int truncatedDivide;
    public int modulo;
    public int ceiling;
    public int floor;
    public int truncate;
    public int abs;
    public int negate;
    public int round;
    public int ln;
    public int exp;
    public int log;
    public int power;
    public int successor;
    public int predecessor;
    public int minValue;
    public int maxValue;
    public int precision;
    public int lowBoundary;
    public int highBoundary;
    public int total;
  }

  public class ElmStringCounts {
    public int concatenate;
    public int combine;
    public int split;
    public int length;
    public int upper;
    public int lower;
    public int indexer;
    public int positionOf;
    public int substring;
    public int splitOnMatches;
    public int lastPositionOf;
    public int startsWith;
    public int endsWith;
    public int matches;
    public int replaceMatches;
  }

  public class ElmTemporalCounts {
    public int durationBetween;
    public int differenceBetween;
    public int dateFrom;
    public int timeFrom;
    public int timezoneOffsetFrom;
    public int dateTimeComponentFrom;
    public int timeOfDay;
    public int today;
    public int now;
    public int dateTime;
    public int time;
    public int sameAs;
    public int sameOrBefore;
    public int sameOrAfter;
    public int timezoneFrom;
    public int date;
  }

  public class ElmIntervalCounts {
    public int interval;
    public int width;
    public int start;
    public int end;
    public int contains;
    public int properContains;
    public int in;
    public int properIn;
    public int includes;
    public int includedIn;
    public int properIncludes;
    public int properIncludedIn;
    public int before;
    public int after;
    public int meets;
    public int meetsBefore;
    public int meetsAfter;
    public int overlaps;
    public int overlapsBefore;
    public int overlapsAfter;
    public int starts;
    public int ends;
    public int collapse;
    public int union;
    public int intersect;
    public int except;
    public int size;
    public int pointFrom;
    public int expand;
  }

  public class ElmListCounts {
    public int list;
    public int exists;
    public int times;
    public int filter;
    public int first;
    public int last;
    public int indexOf;
    public int flatten;
    public int sort;
    public int forEach;
    public int distinct;
    public int current;
    public int singletonFrom;
    public int slice;
    public int repeat;
    public int iteration;
  }

  public class ElmAggregateCounts {
    public int aggregate;
    public int product;
    public int geometricMean;
    public int count;
    public int sum;
    public int min;
    public int max;
    public int avg;
    public int median;
    public int mode;
    public int variance;
    public int populationVariance;
    public int stdDev;
    public int populationStdDev;
    public int allTrue;
    public int anyTrue;
  }

  public class ElmTypeCounts {
    public int is;
    public int as;
    public int convert;
    public int toBoolean;
    public int toConcept;
    public int toDateTime;
    public int toDecimal;
    public int toInteger;
    public int toQuantity;
    public int toString;
    public int toTime;
    public int canConvert;
    public int convertsToBoolean;
    public int toChars;
    public int convertsToDate;
    public int toDate;
    public int convertsToDateTime;
    public int convertsToLong;
    public int toLong;
    public int convertsToDecimal;
    public int convertsToInteger;
    public int toList;
    public int convertQuantity;
    public int canConvertQuantity;
    public int convertsToQuantity;
    public int convertsToRatio;
    public int toRatio;
    public int convertsToString;
    public int convertsToTime;
    public int children;
    public int descendents;
  }

  public class ElmTerminologyCounts {
    public int inCodeSystem;
    public int inValueSet;
    public int calculateAge;
    public int calculateAgeAt;
    public int anyInCodeSystem;
    public int anyInValueSet;
    public int subsumes;
    public int subsumedBy;
  }

  public class ElmErrorCounts {
    public int message;
  }

  public ElmExpressionCounts elmExpressionCounts = new ElmExpressionCounts();
  public ElmLiteralCounts elmLiteralCounts = new ElmLiteralCounts();
  public ElmClinicalValueCounts elmClinicalValueCounts = new ElmClinicalValueCounts();
  public ElmTypeSpecifierCounts elmTypeSpecifierCounts = new ElmTypeSpecifierCounts();
  public ElmReuseCounts elmReuseCounts = new ElmReuseCounts();
  public ElmQueryCounts elmQueryCounts = new ElmQueryCounts();
  public ElmComparisonCounts elmComparisonCounts = new ElmComparisonCounts();
  public ElmLogicalCounts elmLogicalCounts = new ElmLogicalCounts();
  public ElmNullCounts elmNullCounts = new ElmNullCounts();
  public ElmConditionalCounts elmConditionalCounts = new ElmConditionalCounts();
  public ElmArithmeticCounts elmArithmeticCounts = new ElmArithmeticCounts();
  public ElmStringCounts elmStringCounts = new ElmStringCounts();
  public ElmTemporalCounts elmTemporalCounts = new ElmTemporalCounts();
  public ElmIntervalCounts elmIntervalCounts = new ElmIntervalCounts();
  public ElmListCounts elmListCounts = new ElmListCounts();
  public ElmAggregateCounts elmAggregateCounts = new ElmAggregateCounts();
  public ElmTypeCounts elmTypeCounts = new ElmTypeCounts();
  public ElmTerminologyCounts elmTerminologyCounts = new ElmTerminologyCounts();
  public ElmErrorCounts elmErrorCounts = new ElmErrorCounts();

  @JsonIgnore
  public String getJson() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

    return mapper.writeValueAsString(this);
  }
}
