package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "progressions")
public class Progression {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "terms")
    private int terms;

    @Column(name = "current_index")
    private int currentIndex;

    @Column(name = "start")
    private int start;

    @Column(name = "step")
    private int step;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name="formula_id", nullable=false)
    private FormulaProgression formulaProgression;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FormulaProgression getFormula() {
        return formulaProgression;
    }

    public void setFormula(FormulaProgression formula) {
        this.formulaProgression = formula;
    }

    public int getTerms() {
        return terms;
    }

    public void setTerms(int terms) {
        this.terms = terms;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
