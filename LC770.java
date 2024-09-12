import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * LC770
 */
public class LC770 {

    public static void main(String[] args) {
        var sol = new Solution();
        System.out.println(sol.basicCalculatorIV("e + 8 - a + 5", new String[] { "e" }, new int[] { 1 }));
        System.out.println("------------------");

        System.out.println(sol.basicCalculatorIV("e - 8 + temperature - pressure", new String[] { "e", "temperature" },
                new int[] { 1, 12 }));
        System.out.println("------------------");

        System.out.println(sol.basicCalculatorIV("(e + 8) * (e - 8)", new String[] {}, new int[] {}));
        System.out.println("------------------");

        System.out.println(sol.basicCalculatorIV("a * b * c + b * a * c * 4", new String[] {}, new int[] {}));
        System.out.println("------------------");

        System.out.println(sol.basicCalculatorIV("e * ((e + 8) * (e - 8)) * e", new String[] {}, new int[] {}));
        System.out.println("------------------");
    }
}

enum Symbol {
    Multiply,
    Add,
    Subtract,
    Undefined;

    public static Symbol getSymbol(String ch) {
        switch (ch) {
            case "*":
                return Multiply;
            case "+":
                return Add;
            case "-":
                return Subtract;
            default:
                return Undefined;
        }
    }
}

class Chunk {
    private List<Term> terms;

    Chunk(List<Term> terms) {
        this.terms = simplify(terms);
    }

    Chunk(String variable) {
        this.terms = new ArrayList<>();
        terms.add(new Term(variable));
    }

    Chunk(Integer integer) {
        this.terms = new ArrayList<>();
        terms.add(new Term(integer));
    }

    public List<Term> getTerms() {
        if (terms.size() == 0) {
            System.out.println("Zero size term!");
        }

        return this.terms;
    }

    public static Chunk parseChunk(String chunkStr) {
        if (chunkStr == null || chunkStr.length() == 0) {
            System.out.println("Empty Chunk!!!");
            return null;
        } else if (chunkStr.charAt(0) == '(') {
            return Expression.parseExpression(chunkStr.substring(1, chunkStr.length() - 1));
        } else if (chunkStr.matches("[a-z]+")) {
            return new Chunk(chunkStr);
        } else if (chunkStr.matches("-?[0-9]+")) {
            return new Chunk(Integer.valueOf(chunkStr));
        }

        System.out.println("Cannot parse chunk: " + chunkStr);
        return null;
    }

    public String toString() {
        return this.getTerms().toString();
    }

    private List<Term> simplify(List<Term> rawTerms) {
        Map<String, Term> termMap = new HashMap<>();

        for (Term rawTerm : rawTerms) {
            var varStr = rawTerm.getVarStr();
            if (termMap.containsKey(varStr)) {
                Term newTerm = termMap.get(varStr).add(rawTerm);
                if (newTerm.getCoEfficient() != 0) {
                    termMap.put(varStr, newTerm);
                }
                else {
                    termMap.remove(varStr);
                }
            } else {
                termMap.put(varStr, rawTerm);
            }
        }

        var simplifiedTerms = new ArrayList<>(termMap.values());

        Collections.sort(simplifiedTerms, (Term t1, Term t2) -> {
            if (t1.getDegree() == t2.getDegree()) {
                return t1.getVarStr().compareTo(t2.getVarStr());
            }

            return t2.getDegree() - t1.getDegree();
        });

        return simplifiedTerms;
    }
}

class ChunkOrSymbol {
    private Chunk chunk;
    private Symbol symbol;

    ChunkOrSymbol(Chunk chunk) {
        this.chunk = chunk;
        this.symbol = Symbol.Undefined;
    }

    ChunkOrSymbol(Symbol symbol) {
        this.symbol = symbol;
        this.chunk = null;
    }

    public Symbol getSymbol() {
        return this.symbol;
    }

    public Chunk getChunk() {
        return this.chunk;
    }

    public String toString() {
        if (this.getSymbol() != Symbol.Undefined) {
            return this.getSymbol().toString();
        }

        return this.getChunk().toString();
    }
}

class Term {
    private Integer coEfficient;
    private SortedSet<String> variableNames;
    private Map<String, Integer> variableDegrees;
    private Integer degree;

    Term(Integer coEfficient) {
        this(coEfficient, new TreeSet<>(), new HashMap<>());
    }

    Term(String variable) {
        this.coEfficient = 1;

        this.variableNames = new TreeSet<>();
        this.variableNames.add(variable);

        this.variableDegrees = new HashMap<>();
        variableDegrees.put(variable, 1);

        this.degree = 1;
    }

    private Term(Integer coEfficient, SortedSet<String> variableNames, Map<String, Integer> variableDegrees) {
        this.coEfficient = coEfficient;
        this.variableNames = variableNames;
        this.variableDegrees = variableDegrees;

        this.degree = 0;
        for (var varDegrees : variableDegrees.values()) {
            this.degree += varDegrees;
        }
    }

    public Integer getCoEfficient() {
        return this.coEfficient;
    }

    public Integer getDegree() {
        return this.degree;
    }

    public String getVarStr() {
        StringBuilder sb = new StringBuilder();
        for (var variable : variableNames) {
            sb.append('*');
            sb.append(String.join("*", Collections.nCopies(variableDegrees.get(variable), variable)));
        }

        return sb.toString();
    }

    public Term multiply(Term that) {
        var multipliedCoEfficient = this.coEfficient * that.coEfficient;
        var multipliedVariableNames = new TreeSet<>(this.variableNames);
        multipliedVariableNames.addAll(that.variableNames);
        var multipliedVariables = Stream.of(this.variableDegrees, that.variableDegrees)
                .flatMap(variables -> variables.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (degree1, degree2) -> degree1 + degree2));

        return new Term(multipliedCoEfficient, multipliedVariableNames, multipliedVariables);
    }

    public Term add(Term that) {
        if (!this.getVarStr().equals(that.getVarStr())) {
            System.out.println("WTF cannot add these");
            return new Term(0);
        }

        var newCoEfficient = this.coEfficient + that.coEfficient;
        var newVariableNames = new TreeSet<>(this.variableNames);
        var newVariableDegrees = new HashMap<>(this.variableDegrees);

        return new Term(newCoEfficient, newVariableNames, newVariableDegrees);
    }

    public String toString() {
        if (coEfficient == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(coEfficient);
        sb.append(getVarStr());

        return sb.toString();
    }
}

class Expression {
    private Stack<ChunkOrSymbol> evalStack;
    private String expressionStr;

    Expression(String expressionInput) {
        this.expressionStr = expressionInput + " ";
        this.evalStack = new Stack<>();
        evalStack.push(new ChunkOrSymbol(Symbol.Add));
    }

    public static Chunk parseExpression(String expressionStr) {
        return new Expression(expressionStr).parseExpression();
    }

    private Chunk parseExpression() {
        int openCount = 0;
        StringBuilder token = new StringBuilder();
        for(var ch : expressionStr.toCharArray()) {

            if (ch == ' ' && openCount == 0) {
                parseToken(token.toString());
                token.setLength(0);
            }
            else {
                token.append(ch);
            }

            if (ch == '(') {
                openCount += 1;
            }
            else if (ch == ')') {
                openCount -= 1;
            }
        }

        // for (int i = 0; i < evalStack.size(); i++) {
        //     System.out.println(evalStack.elementAt(i));
        // }

        Chunk resultChunk = Chunk.parseChunk("0");

        while (!evalStack.empty()) {
            Chunk opChunk = evalStack.pop().getChunk();
            Symbol op = evalStack.pop().getSymbol();
            System.out.println(op + " " + opChunk);

            switch (op) {
                case Add:
                    resultChunk = add(resultChunk, opChunk);
                    break;
                case Subtract:
                    resultChunk = subtract(resultChunk, opChunk);
                    break;
                default:
                    System.out.println("WTF trouble at eval");
                    break;
            }
        }

        return resultChunk;
    }

    private void parseToken(String token) {
        // System.out.println(token);
        Symbol parsedSymbol = Symbol.getSymbol(token);
        if (parsedSymbol == Symbol.Undefined) {
            var parseChunk = Chunk.parseChunk(token);
            if (!evalStack.isEmpty() && evalStack.peek().getSymbol() == Symbol.Multiply) {
                evalStack.pop();
                var previousChunk = evalStack.pop();

                var multipliedChunk = multiply(previousChunk.getChunk(), parseChunk);
                evalStack.push(new ChunkOrSymbol(multipliedChunk));
            } else {
                evalStack.add(new ChunkOrSymbol(parseChunk));
            }
        } else {
            evalStack.add(new ChunkOrSymbol(parsedSymbol));
        }
    }

    private Chunk subtract(Chunk chunkA, Chunk chunkB) {
        return add(chunkA, multiply(chunkB, Chunk.parseChunk("-1")));
    }

    private Chunk add(Chunk chunkA, Chunk chunkB) {
        List<Term> addedTerms = Stream.of(chunkA.getTerms(), chunkB.getTerms()).flatMap(terms -> terms.stream())
                .toList();
        return new Chunk(addedTerms);
    }

    private static Chunk multiply(Chunk chunkA, Chunk chunkB) {
        List<Term> multipliedTerms = new ArrayList<>();
        for (var termA : chunkA.getTerms()) {
            for (var termB : chunkB.getTerms()) {
                multipliedTerms.add(termA.multiply(termB));
            }
        }

        return new Chunk(multipliedTerms);
    }
}

class Solution {
    public List<String> basicCalculatorIV(String expression, String[] evalvars, int[] evalints) {
        System.out.println(expression);
        expression = replaceVariables(expression, evalvars, evalints);
        System.out.println(expression);
        var result = Expression.parseExpression(expression);
        System.out.println(result);

        return result.getTerms().stream().map(Term::toString).filter(s -> s.length() > 0).toList();
    }

    private String replaceVariables(String expression, String[] evalvars, int[] evalints) {
        for (Integer i = 0; i < evalvars.length; i++) {
            expression = expression.replaceAll("\\b" + evalvars[i] + "\\b", String.valueOf(evalints[i]));
        }

        return expression;

    }
}