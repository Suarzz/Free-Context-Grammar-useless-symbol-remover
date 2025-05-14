package Gramatiques;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Getting Variables (non-terminal Symbols)
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the non-terminal symbols of the grammar, separated by commas ',' ");
        String line = scanner.nextLine();
        Grammar grammar = new Grammar(parseVariables(line));

        for(Variable variable : grammar.getVariables()) {
            System.out.println("Specify the productions of symbol " + variable.getName() + " separated by '|' ");
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



    private static void addAllProductions(String line, Variable variable, LinkedList<Variable> variables) {
        String[] productions = line.split("\\|");
        for(String prod : productions) {
            variable.addProduction(variables, prod);
        }

    }
}
