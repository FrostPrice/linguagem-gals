package gals;

public interface Constants extends ScannerConstants, ParserConstants
{
    int EPSILON  = 0;
    int DOLLAR   = 1;

    int t_soma = 2;
    int t_subtracao = 3;
    int t_multiplicacao = 4;
    int t_divisao = 5;
    int t_exponenciacao = 6;
    int t_log = 7;
    int t_imprimir = 8;
    int t_igual = 9;
    int t_openPar = 10;
    int t_closePar = 11;
    int t_eol = 12;
    int t_variavel = 13;
    int t_numeros = 14;

}
