package Gramatiques;

import java.util.ArrayList;
import java.util.HashSet;

public class Production {
    private final ArrayList<Object> components;

    public Production(ArrayList<Object> components) {
        this.components = components;
    }

    public ArrayList<Object> getComponents() {
        return components;
    }

    protected boolean hasVariables() {
        for(Object obj : components) {
            if(obj instanceof Variable) {
                return true;
            }
        }
        return false;
    }

    protected HashSet<Variable> getVariablesInProduction() {
        HashSet<Variable> variables = new HashSet<>();
        for(Object obj : components) {
            if(obj instanceof Variable) {
                variables.add((Variable) obj);
            }
        }
        return variables;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("TODO");
    }
}
