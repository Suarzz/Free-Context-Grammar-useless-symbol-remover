package Gramatiques;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class Variable {
    private final String name;
    private LinkedList<Production> productions;

    public Variable(String name) {
        this.name = name;
        this.productions = new LinkedList<>();
    }

    protected String getName() {
        return name;
    }

    protected LinkedList<Production> getProductions() {
        return productions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName()).append(" -> ");

        if (productions.isEmpty()) {
            sb.append("ε"); // If no productions, we show empty production
        } else {
            for (int i = 0; i < productions.size(); i++) {
                Production prod = productions.get(i);
                for (Object component : prod.getComponents()) {
                    if (component instanceof Variable) {
                        sb.append(((Variable) component).getName());
                    } else if (component instanceof Character) {
                        sb.append((Character) component);
                    }
                }
                if (i < productions.size() - 1) {
                    sb.append(" | ");
                }
            }
        }

        return sb.toString();
    }


    protected void addProduction(LinkedList<Variable> variables, String production) {
        ArrayList<Object> components = new ArrayList<>();

        for(int i = 0; i < production.length(); i++) {
            Variable foundVariable = findVariable(variables,production.charAt(i));
            if(foundVariable != null ) {
                components.add(foundVariable);
            } else {
                components.add(production.charAt(i));
            }
        }
        productions.add(new Production(components));
    }

    protected void removeProduction(Production production) {
        productions.remove(production);
    }

    private Variable findVariable(LinkedList<Variable> variables, char c) {
        for(Variable variable : variables) {
            if(variable.getName().equals(String.valueOf(c))) {
                return variable;
            }
        }
        return null;
    }

    protected boolean isImmediateFecund() {
        for(Production prod : productions) {//If a production has no variables (only terminals) it's true
            if(!prod.hasVariables()) {
                return true;
            }
        }
        return false;
    }

    protected HashSet<Variable> accessibleVariables() {
        HashSet<Variable> accessibles = new HashSet<>();
        for(Production prod : productions) {
            accessibles.addAll(prod.getVariablesInProduction());
        }
        return accessibles;
    }

}
