# Interpretador de linguagem de programação

Objetivo: exercitar os conteúdos vistos em sala sobre linguagens livres de contexto, sob perspectiva de Compiladores.
Enunciado: Implementar uma gramática e um interpretador utilizando o GALs. A linguagem deverá permitir:

- Todas as variáveis e operações são sobre números binários inteiros sem sinal (positivos);
- As operações que a linguagem deverá permitir são:
  - atribuição de valores ou expressões a variáveis;
  - exibição dos valores de variáveis na tela;
  - cálculo: soma, subtração, multiplicação, divisão, exponenciação, log.

## Exemplo da linguagem

```txt
A = 10;
B = 11;
B = 111 + A * B;
Show ( B );
```

## Exemplo de saída

```txt
A = 10;
B = 11;
B = 111 + A * B;
show ( B );
// Resultado: 11011

A = log(1100100);
show(A);
// Resultado: 10
B = A * 10;
show ( B );
// Resultado: 100
C = 10 ** 11;
show(C);
// Resultado: 1000

A = 1;
B = 1 - A;
show ( B );
// Resultado: 0
```

## Gramática

```txt
<lista_comandos> ::= <comando> <lista_comandos> | <comando>;
<comando> ::= imprimir openPar <calculos> closePar eol #1 | variavel igual <calculos> eol #2;
<calculos> ::= <calculos> <calculo> |  <valor>;
<calculo> ::= soma <valor> #3
  | subtracao <valor> #4
  | multiplicacao <valor> #5
  | divisao <valor> #6
  | exponenciacao <valor> #7;
<funcao> ::= log openPar <valor> closePar #8;
<valor> ::= numeros | variavel | <funcao>;
```

## Numeração das Ações Semânticas

```txt
1. End of Line do print na tela
2. Atribuir o valor da expressão a uma variável
3. End of Line para o atribuir variavel
4. Somar o valor da expressão
5. Subtrair o valor da expressão
6. Multiplicar o valor da expressão
7. Dividir o valor da expressão
8. Exponenciar o valor da expressão
9. Calcular o logaritmo do valor da expressão
10. Valores numericos utilizados nas expressões
11. Variaveis utilizadas nas expressões
```
