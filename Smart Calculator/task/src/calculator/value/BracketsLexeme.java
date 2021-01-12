package calculator.value;

import calculator.CalculationContext;
import java.util.regex.Pattern;

public class BracketsLexeme extends AbstractLexeme {
    static Pattern pattern = Pattern.compile("[()]");

    public BracketsLexeme(String value) {
        super(value);
    }

    @Override
    public boolean combine(Lexeme other) {
        if ( other instanceof BracketsLexeme && !other.equals(this)) {
            throw new RuntimeException("Invalid expression");
        }
        return false;
    }

    @Override
    public void process(CalculationContext context) {
        if ("(".equals(value)) {
            context.pushToBuffer(this);
        } else {
            Lexeme top = context.peekBuffer();
            if (top instanceof OperatorLexeme) {
                OperatorLexeme.calc(context);
                process(context);
            } else if (top instanceof BracketsLexeme) {
                if ("(".equals(top.getValue())) {
                        context.pollBuffer();
                }
            } else if (context.isBufferEmpty()) {
                throw new RuntimeException("Invalid expression");
            }
        }
    }
}
