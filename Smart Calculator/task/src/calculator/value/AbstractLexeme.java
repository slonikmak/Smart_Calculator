package calculator.value;

import java.util.Objects;

public abstract class AbstractLexeme implements Lexeme {
    String value;

    public AbstractLexeme(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equalsLexeme(Lexeme other) {
        return getClass() == other.getClass();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractLexeme that = (AbstractLexeme) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
