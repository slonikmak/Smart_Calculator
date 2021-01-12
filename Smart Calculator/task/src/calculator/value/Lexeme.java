package calculator.value;

import calculator.CalculationContext;

import java.util.Deque;

public interface Lexeme {

    boolean equalsLexeme(Lexeme other);
    boolean combine(Lexeme other);
    String getValue();
    void process(CalculationContext context);

    static Lexeme byteToValue(byte b){
        String str = String.valueOf((char)b);
        if (OperatorLexeme.pattern.matcher(str).matches()) return new OperatorLexeme(str);
        else if (NumberLexeme.pattern.matcher(str).matches()) return new NumberLexeme(str);
        else if (LetterLexeme.pattern.matcher(str).matches()) return new LetterLexeme(str);
        else if (BracketsLexeme.pattern.matcher(str).matches()) return new BracketsLexeme(str);
        else throw new RuntimeException("Invalid expression");
    }
}
