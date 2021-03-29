package edu.phema.quantify;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.HashSet;
import java.util.Set;

public class ElmQuantities {
  public static class ExpressionCounts {
    public int unary;
    public int binary;
    public int ternary;
    public int nary;
    public int aggregate;
  }

  public static class LogicalExpressionCounts {
    public int and;
    public int or;
    public int not;
    public int implies;
    public int xor;
  }

  public static class TypeSpecifierCounts {
    public int named;
    public int list;
    public int interval;
    public int tuple;
    public int choice;
  }

  public static class ParameterCounts {
    public int parameterDefinition;
    public int parameterReference;
  }

  public static class StatementCounts {
    public int definition;
    public int reference;
    public int functionDefinition;
    public int functionReference;
  }

  public static class IntervalOperatorCounts {
    public int intervalDefinition;
    public int in;
  }

  public static class ListOperatorCounts {
    public int listDefinition;
  }

  public static class UsingCounts {
    public int usingDefinition;
  }

  public static class IncludeCounts {
    public int includeDefinition;
  }

  public static class RetrieveCounts {
    public int retrieveDefinition;
    public Set<String> dataSources = new HashSet<>();
  }

  public static class NullologicalCounts {
    public int _null;
    public int coalesce;
    public int isTrue;
    public int isFalse;
    public int isNull;
  }

  public static class ConditionalCounts {
    public int _if;
    public int then;
    public int _else;

    public int _case;
    public int caseItem;
  }

  public static class ClinicalValueCounts {
    public int codeSystemDefinition;
    public int codeSystemReference;
    public int valueSetDefinition;
    public int valueSetReference;
    public int codeDefinition;
    public int codeReference;
    public int conceptDefinition;
    public int conceptReference;
    public int conceptLiteral;
    public int codeLiteral;
    public int quantity;
  }

  public static class ComparisonCounts {
    public int equal;
    public int equivalent;
  }

  public static class ClinicalOperatorCounts {

    // Could be in terminology counts?
    public int inCodeSystem;
    public int inValueSet;

    public int calculateAge;
    public int calculateAgeAt;
  }

  public ExpressionCounts expressionCounts = new ExpressionCounts();
  public TypeSpecifierCounts typeSpecifierCounts = new TypeSpecifierCounts();
  public StatementCounts statementCounts = new StatementCounts();
  public LogicalExpressionCounts logicalExpressionCounts = new LogicalExpressionCounts();
  public ParameterCounts parameterCounts = new ParameterCounts();
  public IntervalOperatorCounts intervalOperatorCounts = new IntervalOperatorCounts();
  public ListOperatorCounts listOperatorCounts = new ListOperatorCounts();
  public UsingCounts usingCounts = new UsingCounts();
  public IncludeCounts includeCounts = new IncludeCounts();
  public RetrieveCounts retrieveCounts = new RetrieveCounts();
  public ClinicalValueCounts clinicalValueCounts = new ClinicalValueCounts();
  public ComparisonCounts comparisonCounts = new ComparisonCounts();
  public ClinicalOperatorCounts clinicalOperatorCounts = new ClinicalOperatorCounts();
  public NullologicalCounts nullologicalCounts = new NullologicalCounts();
  public ConditionalCounts conditionalCounts = new ConditionalCounts();

  @JsonIgnore
  public String getJson() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

    return mapper.writeValueAsString(this);
  }
}
