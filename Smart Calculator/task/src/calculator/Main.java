package calculator;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

public class Main {

    static String helpString = "The program must calculate expressions like these: 4 + 6 - 8, 2 - 3 - 4, and so on.";
    static Pattern varNamePattern = Pattern.compile("^[a-zA-Z]+$");

    static Variables variables = new Variables();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression;
        while (true) {
            try {
                expression = scanner.nextLine();
                if ("/help".equals(expression)) {
                    System.out.println(helpString);
                } else if (expression.equals("/exit")) {
                    System.out.println("Bye!");
                    break;
                } else if (expression.isBlank()) {

                } else if (expression.startsWith("/")) {
                    System.out.println("Unknown command");
                } else if (expression.contains("=")) {
                    processVar(expression);
                } else {
                    Calculator calculator = new Calculator(expression.replaceAll(" ", ""), variables);
                    System.out.println(calculator.calc());
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void processVar(String expression) {
        expression = expression.replaceAll(" ", "");
        String[] tokens = expression.split("=");
        if (tokens.length != 2) {
            throw new RuntimeException("Invalid assignment");
        }
        String varName = tokens[0];
        String varValue = tokens[1];

        if (!isVarNameCorrect(varName)) {
            throw new RuntimeException("Invalid identifier");
        }
        BigInteger numberValue = calculate(varValue, variables);
        variables.assignVar(varName, numberValue);
    }

    private static BigInteger calculate(String expression, Variables variables){
        Calculator calculator = new Calculator(expression, variables);
        return calculator.calc();
    }

    private static boolean isVarNameCorrect(String var) {
        return varNamePattern.matcher(var).matches();
    }


}
