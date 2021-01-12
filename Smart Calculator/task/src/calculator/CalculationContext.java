package calculator;

import calculator.value.Lexeme;

import java.util.ArrayDeque;
import java.util.Deque;

public class CalculationContext {
    private Deque<Lexeme> result = new ArrayDeque<>();
    private Deque<Lexeme> buffer = new ArrayDeque<>();

    public void pushToBuffer(Lexeme lexeme) {
        buffer.push(lexeme);
    }

    public void pushToResult(Lexeme lexeme) {
        result.push(lexeme);
    }

    public Lexeme peekBuffer() {
        return buffer.peekFirst();
    }

    public Lexeme pollBuffer() {
        return buffer.pollFirst();
    }

    public Lexeme pollResult() {
        return result.pollFirst();
    }

    public boolean isBufferEmpty(){
        return buffer.isEmpty();
    }
}
