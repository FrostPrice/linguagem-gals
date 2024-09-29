package gals;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Semantico implements Constants {
    Map<String, Integer> variables = new HashMap<String, Integer>();
    Stack<Integer> calculationStack = new Stack<>();
    String currentVariable;

    public void executeAction(int action, Token token) throws SemanticError {
        // System.out.println("Ação #" + action + ", Token: " + token);

        Integer num1, num2;

        switch (action) {
            // End of Line do print na tela
            case 1:
                System.out.println("Resultado: " + Integer.toBinaryString(variables.get(currentVariable)) + "\n");

                break;

            // Atribuir o valor da expressão a uma variável
            case 2:
                currentVariable = token.getLexeme();
                break;

            // End of Line para o atribuir variavel
            case 3:
                variables.put(currentVariable, calculationStack.pop());
                break;

            // Soma
            case 4:
                num2 = calculationStack.pop();
                num1 = calculationStack.pop();

                calculationStack.push(num1 + num2);
                break;

            // Substracao
            case 5:
                num2 = calculationStack.pop();
                num1 = calculationStack.pop();

                calculationStack.push(num1 - num2);
                break;

            // Multiplicacao
            case 6:
                num2 = calculationStack.pop();
                num1 = calculationStack.pop();

                calculationStack.push(num1 * num2);
                break;

            // Divisao
            case 7:
                num2 = calculationStack.pop();
                num1 = calculationStack.pop();

                calculationStack.push(num1 / num2);
                break;

            // Exponenciacao
            case 8:
                num2 = calculationStack.pop();
                num1 = calculationStack.pop();

                calculationStack.push((int) Math.pow(num1, num2));
                break;

            // Logaritmo
            case 9:
                num1 = calculationStack.pop();

                calculationStack.push((int) Math.log10(num1));
                break;

            // Valores numericos utilizados nas expressões
            case 10:
                calculationStack.push(Integer.parseInt(token.getLexeme(), 2));
                break;

            // Variaveis utilizadas nas expressões
            case 11:
                calculationStack.push(variables.get(token.getLexeme()));
                break;

            default:
                throw new SemanticError("Ação não definida.");
        }
    }
}
