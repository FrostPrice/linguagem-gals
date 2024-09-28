import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import gals.*;

public class App {
    public static void main(String[] args) throws Exception {
        App app = new App();
        File f = new File("src/code.txt");
        String text = app.readFile(f);
        System.out.println("Codigo da Arquivo:\n" + text);

        try {
            Lexico lexico = new Lexico(new java.io.StringReader(text));
            Semantico semantico = new Semantico();
            Sintatico sintatico = new Sintatico();

            sintatico.parse(lexico, semantico);
        } catch (LexicalError | SemanticError | SyntaticError e) {
            System.err.println(e);
        }
    }

    public String readFile(File f) {
        String text = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            while (line != null) {
                text += line;
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return text;
    }
}
