package calculator.value;

import calculator.CalculationContext;

import java.util.Deque;
import java.util.regex.Pattern;

public class LetterLexeme extends AbstractLexeme {
    static Pattern pattern = Pattern.compile("[a-zA-Z]+");

    public LetterLexeme(String value) {
        super(value);
    }

    @Override
    public boolean combine(Lexeme other) {
        value = other.getValue()+value;
        return true;
    }

    @Override
    public void process(CalculationContext context) {
        context.pushToResult(this);
    }
}
