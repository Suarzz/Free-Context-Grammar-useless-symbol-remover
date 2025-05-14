package Gramatiques;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Grammar {
    private LinkedList<Variable> variables;
    private final Variable startingSymbol;

    public Grammar(LinkedList<Variable> variables) {
        this.variables = variables;
        this.startingSymbol = variables.getFirst();
    }

    protected Variable getStartingSymbol() {
        return startingSymbol;
    }

    protected LinkedList<Variable> getVariables() {
        return variables;
    }

    public void display() {
        if(variables.contains(startingSymbol)) {
            for(Variable variable : variables) {
                System.out.println(variable.toString());
            }
        }
        System.out.println("Empty grammar");
    }

    protected void eliminateNonFecunds() {
        HashSet<Variable> fecunds = new HashSet<>();
        //In the first iteration we check if they are immediate fecunds
        for(Variable variable : this.variables) {
            if(variable.isImmediateFecund()) {
                fecunds.add(variable);
            }
        }

        //On the later iterations we get the non immediate fecunds
        fecunds = obtainNonImmediateFecunds(fecunds);

        //Deleting non fecund variables and productions
        Iterator<Variable> iterator = this.variables.iterator();
        while (iterator.hasNext()) {
            Variable variable = iterator.next();
            if (!fecunds.contains(variable)) {
                iterator.remove(); 
            }
        }

        for (Variable variable : this.variables) {
            Iterator<Production> productionIterator = variable.getProductions().iterator();
            while (productionIterator.hasNext()) {
                Production production = productionIterator.next();
                HashSet<Variable> prodVariables = production.getVariablesInProduction();
                for (Variable prodVariable : prodVariables) {
                    if (!fecunds.contains(prodVariable)) {
                        productionIterator.remove(); // <- safe removal
                        break;
                    }
                }
            }
        }

    }

    private HashSet<Variable> obtainNonImmediateFecunds(HashSet<Variable> fecunds) {
        boolean changesMade;

        do {
            changesMade = false;

            for (Variable variable : this.variables) {
                if (fecunds.contains(variable)) {
                    continue; // If already fecund we skip it
                }

                for (Production production : variable.getProductions()) {
                    boolean allFecund = true; //We need all variables to be fecund


                    HashSet<Variable> prodVariables = production.getVariablesInProduction();
                    for(Variable prodVar : prodVariables) {
                        if(!fecunds.contains(prodVar)) {
                            allFecund = false;
                            break;
                        }
                    }

                    if (allFecund) {
                        fecunds.add(variable);
                        changesMade = true;
                        break; // No need to see more productions because we know it's fecund
                    }
                }
            }
        } while (changesMade);

        return fecunds;
    }


    protected void eliminateNonAccessibles() {
        HashSet<Variable> accessibles = new HashSet<>();
        accessibles.add(this.startingSymbol);

        int startCardinal;
        do {
            startCardinal = accessibles.size();
            HashSet<Variable> newAccessibles = new HashSet<>(); //Auxiliar
            for (Variable variable : accessibles) {
                newAccessibles.addAll(variable.accessibleVariables());
            }
            accessibles.addAll(newAccessibles); // add after traverse
        } while (startCardinal < accessibles.size());

        Iterator<Variable> iterator = this.variables.iterator();
        while (iterator.hasNext()) {
            Variable variable = iterator.next();
            if (!accessibles.contains(variable)) {
                iterator.remove();
            }
        }

        for (Variable variable : this.variables) {
            Iterator<Production> productionIterator = variable.getProductions().iterator();
            while (productionIterator.hasNext()) {
                Production production = productionIterator.next();
                HashSet<Variable> prodVariables = production.getVariablesInProduction();
                for (Variable prodVariable : prodVariables) {
                    if (!accessibles.contains(prodVariable)) {
                        productionIterator.remove();
                        break;
                    }
                }
            }
        }
    }

}
