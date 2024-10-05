package gals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Semantico implements Constants {
    Map<String, Integer> variables = new HashMap<String, Integer>();
    List<Integer> operandList = new ArrayList<>(); // Lista de operandos
    List<String> operatorList = new ArrayList<>(); // Lista de operadores
    String currentVariable;

    public void executeAction(int action, Token token) throws SemanticError {
        Integer num1;

        switch (action) {
            // End of Line do print na tela
            case 1:
                System.out.println("Resultado: " + Integer.toBinaryString(variables.get(currentVariable)) + "\n");
                break;

            // Atribuir o valor da expressão a uma variável
            case 2:
                currentVariable = token.getLexeme();
                break;

            // End of Line para o atribuir variável
            case 3:
                evaluateExpression(); // Avalia a expressão inteira
                variables.put(currentVariable, operandList.get(0)); // O resultado será o primeiro (e único) elemento
                                                                    // restante
                operandList.clear(); // Limpa a lista para a próxima expressão
                operatorList.clear();
                break;

            // Soma
            case 4:
                operatorList.add("+");
                break;

            // Subtração
            case 5:
                operatorList.add("-");
                break;

            // Multiplicação
            case 6:
                operatorList.add("*");
                break;

            // Divisão
            case 7:
                operatorList.add("/");
                break;

            // Exponenciação
            case 8:
                operatorList.add("**");
                break;

            // Logaritmo
            case 9:
                num1 = operandList.remove(operandList.size() - 1); // Remove o último operando
                operandList.add((int) Math.log10(num1));
                break;

            // Valores numéricos utilizados nas expressões
            case 10:
                operandList.add(Integer.parseInt(token.getLexeme(), 2));
                break;

            // Variáveis utilizadas nas expressões
            case 11:
                operandList.add(variables.get(token.getLexeme()));
                break;

            default:
                throw new SemanticError("Ação não definida.");
        }
    }

    private void evaluateExpression() {
        // Primeira prioridade: ** (exponenciação)
        evaluateOperator("**");

        // Segunda prioridade: * e /
        evaluateOperator("*");
        evaluateOperator("/");

        // Terceira prioridade: + e -
        evaluateOperator("+");
        evaluateOperator("-");
    }

    private void evaluateOperator(String targetOperator) {
        for (int i = 0; i < operatorList.size(); i++) {
            if (operatorList.get(i).equals(targetOperator)) {
                Integer num1 = operandList.get(i);
                Integer num2 = operandList.get(i + 1);
                Integer result = null;

                // Realiza a operação de acordo com o operador
                switch (targetOperator) {
                    case "**":
                        System.out.println(num1 + "**" + num2);
                        result = (int) Math.pow(num1, num2);
                        break;
                    case "*":
                        System.out.println(num1 + "*" + num2);
                        result = num1 * num2;
                        break;
                    case "/":
                        System.out.println(num1 + "/" + num2);
                        result = num1 / num2;
                        break;
                    case "+":
                        System.out.println(num1 + "+" + num2);
                        result = num1 + num2;
                        break;
                    case "-":
                        System.out.println(num1 + "-" + num2);
                        result = num1 - num2;
                        break;
                }

                // Atualiza operandos e operadores
                operandList.set(i, result); // Substitui o primeiro operando pelo resultado
                operandList.remove(i + 1); // Remove o segundo operando
                operatorList.remove(i); // Remove o operador processado
                i--; // Reajusta o índice para continuar a verificação

                // Continua avaliando a expressão até que todos os operadores de alta
                // precedência sejam processados
            }
        }
    }
}
