package calculator;

import calculator.value.Lexeme;
import calculator.value.NumberLexeme;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Variables {
    Map<String, BigInteger> vars = new HashMap<>();

    public void assignVar(String name, BigInteger value){
        vars.put(name, value);
    }

    public NumberLexeme replaceVar(Lexeme value) {
        BigInteger varValue = vars.get(value.getValue());
        if (varValue != null) {
            return new NumberLexeme(varValue);
        } else {
            throw new RuntimeException("Unknown variable");
        }
    }

}
