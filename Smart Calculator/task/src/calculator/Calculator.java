package calculator;

import calculator.value.*;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;

public class Calculator {
    String expression;
    Deque<Lexeme> expressionStack = new ArrayDeque<>();
    CalculationContext context = new CalculationContext();
    Variables vars;


    public Calculator(String expression, Variables vars) {
        this.expression = expression;
        this.vars = vars;
    }

    private void prepareExpression() {
        byte[] bytes = expression.getBytes();
        expressionStack.push(Lexeme.byteToValue(bytes[0]));
        for (int i = 1; i < bytes.length; i++) {
            Lexeme current = Lexeme.byteToValue(bytes[i]);
            Lexeme prev = expressionStack.peekFirst();
            checkLexeme(prev, current);
            if (current.equalsLexeme(prev) && current.combine(prev)) {
                expressionStack.pollFirst();
            }
            expressionStack.addFirst(current);
        }
    }

    private void checkLexeme(Lexeme prev, Lexeme current){
        if ((prev instanceof NumberLexeme && current instanceof LetterLexeme)
        || (prev instanceof LetterLexeme && current instanceof NumberLexeme)) {
            throw new RuntimeException("Invalid identifier");
        }
    }

    public BigInteger calc() {
        prepareExpression();
        while (!expressionStack.isEmpty()) {
            Lexeme lexeme = expressionStack.pollLast();
            if (lexeme instanceof LetterLexeme) {
                lexeme = vars.replaceVar(lexeme);
            }
            lexeme.process(context);
        }
        while (!context.isBufferEmpty()) {
           OperatorLexeme.calc(context);
        } try {
            return new BigInteger(context.pollResult().getValue());
        } catch (Exception e) {
            throw new RuntimeException("Invalid expression");
        }
        //return Long.parseLong(context.pollResult().getValue());
    }
}
