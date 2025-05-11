package Gramatiques;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Getting Variables (non-terminal Symbols)
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introdueix els simbols no terminals de la gramàtica separats per ',' ");
        String line = scanner.nextLine();
        Grammar grammar = new Grammar(parseVariables(line));

        for(Variable variable : grammar.getVariables()) {
            System.out.println("Especifica les produccions del simbol " + variable.getName() + " separades per '|' ");
            line = scanner.nextLine();
            addAllProductions(line,variable,grammar.getVariables());

        }
        scanner.close();
        grammar.eliminateNonFecunds();
        grammar.eliminateNonAccessibles();
        grammar.display();
    }

    private static LinkedList<Variable> parseVariables(String line) {
        String[] variables = line.split(",");
        var linkedVariables = new LinkedList<Variable>();
        for(String variable : variables) {
            Variable dummy = new Variable(variable.trim());
            linkedVariables.add(dummy);
        }

        return linkedVariables;
    }

//    private static LinkedList<Character> parseTerminals(String line) {
//        String[] terminals = line.split(",");
//        var linkedTerminals = new LinkedList<Character>();
//        for(String terminal : terminals) {
//            Variable dummy = new Character(terminal.trim());
//            linkedTerminals.add(dummy);
//        }
//        return linkedTerminals;
//    }

    
    private static void addAllProductions(String line, Variable variable, LinkedList<Variable> variables) {
        String[] productions = line.split("\\|");
        for(String prod : productions) {
            variable.addProduction(variables, prod);
        }

    }
}
