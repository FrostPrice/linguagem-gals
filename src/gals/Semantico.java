package gals;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Semantico implements Constants {
    Map<String, Integer> variables = new HashMap<String, Integer>();
    Stack<Integer> calculationStack = new Stack<>();
    String currentVariable;

    public void executeAction(int action, Token token) throws SemanticError {
        System.out.println("Ação #" + action + ", Token: " + token);

        Integer num1, num2;

        switch (action) {
            // End of Line do print na tela
            case 1:

                break;

            // Atribuir o valor da expressão a uma variável
            case 2:
                currentVariable = token.getLexeme();
                System.out.println("Variável: " + currentVariable);
                break;
            // End of Line para o atribuir variavel
            case 3:
                variables.put(currentVariable, calculationStack.pop());
                System.out.println("Variáveis: " + variables);
                break;
            // Soma
            case 4:
                // num1 = calculationStack.pop();
                // num2 = calculationStack.pop();

                // System.out.println("num1: " + num1 + ", num2: " + num2);
                // calculationStack.push(num1 + num2);
                break;
            // Substracao
            case 5:
                num1 = calculationStack.pop();
                num2 = calculationStack.pop();
                calculationStack.push(num1 - num2);
                break;
            // Multiplicacao
            case 6:

                break;
            // Divisao
            case 7:

                break;
            // Exponenciacao
            case 8:

                break;
            // Logaritmo
            case 9:
                break;
            // Valores numericos utilizados nas expressões
            case 10:
                calculationStack.push(Integer.parseInt(token.getLexeme()));
                break;
            // Variaveis utilizadas nas expressões
            case 11:
                break;
            default:
                throw new SemanticError("Ação não definida.");
        }
    }
}
