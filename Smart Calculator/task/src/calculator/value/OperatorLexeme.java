package calculator.value;

import calculator.CalculationContext;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

public class OperatorLexeme extends AbstractLexeme {
    static Pattern pattern = Pattern.compile("[+\\-*/^]");

    static Map<String, Integer> priorityOperators = new HashMap<>(){{
        put("+", 1);
        put("-", 1);
        put("*", 2);
        put("/", 2);
        put("^", 3);
    }};

    static Map<String, BiFunction<NumberLexeme, NumberLexeme, NumberLexeme>> functions = new HashMap<>(){{
       put("+", (val1, val2)->new NumberLexeme(val1.getNumberValue().add(val2.getNumberValue())));
       put("-", (val1, val2)->new NumberLexeme(val1.getNumberValue().subtract(val2.getNumberValue())));
       put("*", (val1, val2)->new NumberLexeme(val1.getNumberValue().multiply(val2.getNumberValue())));
       put("/", (val1, val2)->new NumberLexeme(val1.getNumberValue().divide(val2.getNumberValue())));
       put("^", (val1, val2)->new NumberLexeme(val1.getNumberValue().pow(val2.getNumberValue().intValue())));
    }};

    public OperatorLexeme(String value) {
        super(value);
    }

    @Override
    public boolean combine(Lexeme other) {
        if ("+".equals(value)) {
            if ("-".equals(other.getValue())) value = "-";
        } else if ("-".equals(value)) {
            if ("-".equals(other.getValue())) value = "+";
        } else if ("*".equals(other.getValue()) || "/".equals(other.getValue())) {
            throw new RuntimeException("Invalid expression");
        }
        return true;
    }

    @Override
    public void process(CalculationContext context) {
        if (context.isBufferEmpty()) {
            context.pushToBuffer(this);
        } else {
            Lexeme top = context.peekBuffer();
            if (top instanceof OperatorLexeme) {
                if (priorityOperators.get(value) > priorityOperators.get(top.getValue())) {
                    context.pushToBuffer(this);
                } else {
                    calc(context);
                    process(context);
                }
            } else if (top instanceof BracketsLexeme) {
                if ("(".equals(top.getValue())) {
                    context.pushToBuffer(this);
                }
            }
        }
    }

    public static void calc(CalculationContext context) {
        Lexeme val2 = context.pollResult();
        Lexeme val1 = context.pollResult();
        Lexeme operator = context.pollBuffer();
        if (val1 == null) {
            val1 = new NumberLexeme(BigInteger.ZERO);
        }
        if (!(val1 instanceof NumberLexeme) || !(val2 instanceof  NumberLexeme) || !(operator instanceof OperatorLexeme)) {
            throw new RuntimeException("Invalid expression");
        }
        context.pushToResult(functions.get(operator.getValue()).apply((NumberLexeme) val1, (NumberLexeme) val2));
    }
}
