package org.example.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class State {
    private String stateAbbreviation;
    private String stateName;
    private BigDecimal taxRate;

    public State (String stateName, BigDecimal taxRate) {
        this.stateName = stateName;
        this.taxRate = taxRate;
    }
    public State(String stateName) {
        this.stateName = stateName;
    }
    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
    public String getStateAbbreviation() {
        return stateAbbreviation;
    }
    public String getStateName() {
        return stateName;
    }
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state = (State) o; // Explicit casting to State
        return Objects.equals(getStateAbbreviation(), state.getStateAbbreviation()) &&
                Objects.equals(getStateName(), state.getStateName()) &&
                Objects.equals(getTaxRate(), state.getTaxRate());
        /*
        if (this == o) return true;
        if (!(o instanceof State state)) return false;
        return Objects.equals(getStateAbbreviation(), state.getStateAbbreviation()) && Objects.equals(getStateName(), state.getStateName()) && Objects.equals(getTaxRate(), state.getTaxRate());

         */
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStateAbbreviation(), getStateName(), getTaxRate());
    }

    @Override
    public String toString() {
        return "State{" +
                "stateAbbreviation='" + stateAbbreviation + '\'' +
                ", stateName='" + stateName + '\'' +
                ", taxRate=" + taxRate +
                '}';
    }
}
