package calculator.value;

import calculator.CalculationContext;

import java.math.BigInteger;
import java.util.regex.Pattern;

public class NumberLexeme extends AbstractLexeme {
    static Pattern pattern = Pattern.compile("\\d+");

    BigInteger numberValue;

    public NumberLexeme(String value){
        super(value);
        this.numberValue = new BigInteger(value);
    }

    public NumberLexeme(BigInteger value){
        super(String.valueOf(value));
        this.numberValue = value;
    }

    @Override
    public boolean combine(Lexeme other) {
        value = other.getValue()+value;
        numberValue = new BigInteger(value);
        return true;
    }

    @Override
    public void process(CalculationContext context) {
        context.pushToResult(this);
    }

    public BigInteger getNumberValue() {
        return numberValue;
    }
}
