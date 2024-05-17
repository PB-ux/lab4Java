package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "formula")
public class FormulaProgression {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "formula")
    private String formula;

    public FormulaProgression() {}

    public FormulaProgression(String formula) {
        this.formula = formula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}
