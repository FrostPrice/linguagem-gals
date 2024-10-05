package gals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Semantico implements Constants {
    Map<String, Integer> variables = new HashMap<String, Integer>();
    Stack<List<Integer>> operandStack = new Stack<>(); // Pilha para operandos aninhados
    Stack<List<String>> operatorStack = new Stack<>(); // Pilha para operadores aninhados
    List<Integer> operandList = new ArrayList<>(); // Lista de operandos
    List<String> operatorList = new ArrayList<>(); // Lista de operadores
    String currentVariable;

    public void executeAction(int action, Token token) throws SemanticError {
        switch (action) {
            // End of Line do print na tela
            case 1:
                System.out.println(currentVariable + " = "
                        + Integer.toBinaryString(variables.get(currentVariable)) + "\n");
                break;

            // Atribuir o valor da expressão a uma variável
            case 2:
                currentVariable = token.getLexeme();
                break;

            // End of Line para o atribuir variável
            case 3:
                if (!operandStack.isEmpty()) {
                    throw new SemanticError("Expressão incompleta: parênteses não foram fechados.");
                }

                evaluateExpression(operandList, operatorList); // Avalia a expressão inteira
                variables.put(currentVariable, operandList.get(0)); // O resultado será o primeiro (e único) elemento
                                                                    // restante

                operandList.clear(); // Limpa a lista para a próxima expressão
                operatorList.clear();
                break;

            // Operadores (Soma, Subtração, Multiplicação, Divisão, Potenciação, Logaritmo)
            case 4:
                addOperator(token.getLexeme());
                break;

            // Valores numéricos utilizados nas expressões
            case 5:
                addOperand(Integer.parseInt(token.getLexeme(), 2));
                break;

            // Variáveis utilizadas nas expressões
            case 6:
                addOperand(variables.get(token.getLexeme()));
                break;
            // Abrir parênteses
            case 7:
                // Empilha as listas atuais e cria novas para a nova expressão
                operandStack.push(operandList);
                operatorStack.push(operatorList);
                operandList = new ArrayList<>();
                operatorList = new ArrayList<>();
                break;
            // Fechar parênteses
            case 8:
                // Avalia a expressão dentro dos parênteses
                evaluateExpression(operandList, operatorList);

                // O resultado da avaliação será o primeiro (e único) elemento restante
                Integer parenthesesResult = operandList.get(0);

                // Restaura as listas anteriores (Camada inferior)
                List<Integer> previousOperandList = operandStack.pop();
                List<String> previousOperatorList = operatorStack.pop();

                // Adiciona o resultado da expressão dentro dos parênteses à lista da camada
                // atual (inferior)
                previousOperandList.add(parenthesesResult);

                // Atualiza operandList e operatorList para apontar para a camada superior
                // restaurada
                operandList = previousOperandList;
                operatorList = previousOperatorList;
                break;

            default:
                throw new SemanticError("Ação não definida.");
        }
    }

    private void addOperator(String operator) {
        operatorList.add(operator);
    }

    private void addOperand(Integer operand) {
        operandList.add(operand);
    }

    private void evaluateExpression(List<Integer> operandList, List<String> operatorList) {
        // Prioridade de operadores: log, **, *, /, +, -
        List<String> operators = new ArrayList<>();
        operators.add("log");
        operators.add("**");
        operators.add("*");
        operators.add("/");
        operators.add("+");
        operators.add("-");
        evaluateOperator(operators.subList(0, 1), operandList, operatorList);
        evaluateOperator(operators.subList(1, 2), operandList, operatorList);
        evaluateOperator(operators.subList(2, 4), operandList, operatorList);
        evaluateOperator(operators.subList(4, 6), operandList, operatorList);
    }

    private void evaluateOperator(List<String> targetOperators, List<Integer> operandList, List<String> operatorList) {
        for (int i = 0; i < operatorList.size(); i++) {
            String operator = operatorList.get(i);

            if (targetOperators.contains(operator)) {
                Integer num1 = operandList.get(i);
                Integer num2 = !operator.equals("log")
                        ? operandList.get(i + 1)
                        : null; // O segundo operando não é necessário para o logaritmo
                Integer result = null;

                // Realiza a operação de acordo com o operador
                switch (operator) {
                    case "**":
                        // System.out.println(num1 + " ** " + num2);
                        result = (int) Math.pow(num1, num2);
                        break;
                    case "*":
                        // System.out.println(num1 + " * " + num2);
                        result = num1 * num2;
                        break;
                    case "/":
                        if (num2 == 0) {
                            throw new ArithmeticException("Divisão por zero.");
                        }
                        // System.out.println(num1 + " / " + num2);
                        result = num1 / num2;

                        break;

                    case "+":
                        // System.out.println(num1 + " + " + num2);
                        result = num1 + num2;
                        break;

                    case "-":
                        // System.out.println(num1 + " - " + num2);
                        result = num1 - num2;

                        break;
                    case "log":
                        if (num1 <= 0) {
                            throw new ArithmeticException("Logaritmo de número não positivo.");
                        }

                        // System.out.println("log(" + num1 + ")");
                        result = (int) Math.log10(num1);

                        // Atualiza apenas o primeiro operando, pois o logaritmo não precisa de um
                        // segundo
                        operandList.set(i, result);

                        // Remove o operador processado
                        operatorList.remove(i);
                        i--; // Reajusta o índice para continuar a verificação
                        continue;
                }

                // Atualiza operandos e operadores
                operandList.set(i, result); // Substitui o primeiro operando pelo resultado
                operandList.remove(i + 1); // Remove o segundo operando
                operatorList.remove(i); // Remove o operador processado
                i--; // Reajusta o índice para continuar a verificação
            }
        }
    }

}
