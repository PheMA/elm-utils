package edu.phema.quantify;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.*;

public class ElmQuantities {
  public Stack<PhemaAnalysisDepths> depths;
  public PhemaAnalysisDimensions dimensions;

  public ElmQuantities() {
    depths = new Stack<>();

    // push the global depth counter
    depths.push(new PhemaAnalysisDepths());

    dimensions = new PhemaAnalysisDimensions();
  }

  public void pushDepthTracker() {
    depths.push(new PhemaAnalysisDepths());
  }

  public PhemaAnalysisDepths popDepthTracker() {
    return depths.pop();
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

    public int sortClauseMaxDepth = 0;
    private int sortClauseDepth = 0;

    public void setSortClauseDepth(int depth) {
      sortClauseDepth = depth;

      if (sortClauseDepth > sortClauseMaxDepth) {
        sortClauseMaxDepth = sortClauseDepth;
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

    // Total expressions
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

    // Modular expressions (statement and function calls)
    public int phemaModularityMaxDepth = 0;
    private int phemaModularityDepth = 0;

    public void incrementPhemaModularityDepth() {
      phemaModularityDepth++;

      if (phemaModularityDepth > phemaModularityMaxDepth) {
        phemaModularityMaxDepth = phemaModularityDepth;
      }
    }

    public void decrementPhemaModularityDepth() {
      phemaModularityDepth--;
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
    // 1. Literals
    public PhemaLiteralCounts phemaLiteralCounts = new PhemaLiteralCounts();

    // 2. Logical expressions
    public PhemaLogicalCounts phemaLogicalCounts = new PhemaLogicalCounts();

    // 3. Comparison expressions
    public PhemaComparisonCounts phemaComparisonCounts = new PhemaComparisonCounts();

    // 4. Arithmetic expressions
    public PhemaArithmeticCounts phemaArithmeticCounts = new PhemaArithmeticCounts();

    // 5. Aggregate expressions
    public PhemaAggregateCounts phemaAggregateCounts = new PhemaAggregateCounts();

    // 6. Data expressions
    public PhemaDataCounts phemaDataCounts = new PhemaDataCounts();

    // 7. Conditional expressions
    public PhemaConditionalCounts phemaConditionalCounts = new PhemaConditionalCounts();

    // 8. Temporal expressions
    public PhemaTemporalCounts phemaTemporalCounts = new PhemaTemporalCounts();

    // 9. Modularity
    public PhemaModularityCounts phemaModularityCounts = new PhemaModularityCounts();

    // 10. Terminology
    public PhemaTerminologyCounts phemaTerminologyCounts = new PhemaTerminologyCounts();

    // 11. Collections
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
    public Set<String> dataModels = new HashSet<>();

    public int retrieve;
    public Set<String> dataSources = new HashSet<>();

    public int query;
    public int sort;
    public int property;
    public int aggregate;

    public int whereClauseMaxExpressionCount;
    public int whereClauseMaxDepth;

    public int sortClauseMaxExpressionCount;
    public int sortClauseMaxDepth;

    public void recordWhereClauseMaxDepth(int depth) {
      if (depth > whereClauseMaxDepth) {
        whereClauseMaxDepth = depth;
      }
    }

    public void recordWhereClauseExpressionCount(int count) {
      if (count > whereClauseMaxExpressionCount) {
        whereClauseMaxExpressionCount = count;
      }
    }

    public void recordSortClauseMaxDepth(int depth) {
      if (depth > sortClauseMaxDepth) {
        sortClauseMaxDepth = depth;
      }
    }

    public void recordSortClauseExpressionCount(int count) {
      if (count > sortClauseMaxExpressionCount) {
        sortClauseMaxExpressionCount = count;
      }
    }
  }

  public class PhemaConditionalCounts {
    public int _if;
    public int _case;

    // Debatable whether this should go here
    public int coalesce;
  }

  public class PhemaTemporalCounts {
    // interval
    public int start;

    // Patient age
    public int calculateAge;
    public int calculateAgeAt;

    // Other temporal operators
    public int durationBetween;
    public int differenceBetween;
    public int today;
    public int now;

    public int sameAs;
    public int sameOrBefore;
    public int sameOrAfter;
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

  public class PhemaModularityCounts {
    // includes
    public Set<String> includes = new HashSet<>();

    // totals
    public int expression;
    public int statementDef;
    public int functionDef;

    // local totals
    public int localStatementCalls;
    public int localFunctionCalls;

    // external totals
    public int externalStatementCalls;
    public int externalFunctionCalls;
  }

  public class PhemaTerminologyCounts {
    // definitions
    public int codeDef;
    public int codeSystemDef;
    public int conceptDef;
    public int valueSetDef;

    // references
    public int codeRef;
    public int codeSystemRef;
    public int conceptRef;
    public int valueSetRef;

    // operations
    public int inCodeSystem;
    public int inValueSet;
    public int anyInCodeSystem;
    public int anyInValueSet;
    public int subsumes;
    public int subsumedBy;

    // value set numbers
    public Set<String> uniqueValueSets = new HashSet<>();
    public List<Integer> perValueSetCounts = new ArrayList<>();
    public Map<String, Integer> perSystemCounts = new HashMap<>();
    public int codes;
    public int systems;
  }

  public class PhemaCollectionCounts {
    // non-temporal interval
    public int start;

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
    public int end;
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
    public int intersect;
    public int size;
    public int pointFrom;
    public int expand;
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
