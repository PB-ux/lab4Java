package org.example.util;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Objects;

import java.io.BufferedReader;
import java.io.FileReader;

import org.example.model.FormulaProgression;
import org.example.model.Progression;

public class ProgressionFileManager {

    private String filename;

    ProgressionFileManager(String filename) {
        this.filename = filename;
    }

    // read csv and insert rows
    public void insertRows(Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            FileReader file = new FileReader(filename);
            BufferedReader br = new BufferedReader(file);
            br.readLine();
            String line;


            FormulaProgression formulaProgressionA = new FormulaProgression("a");
            FormulaProgression formulaProgressionG = new FormulaProgression("g");
            session.save(formulaProgressionA);
            session.save(formulaProgressionG);

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 5) {
                    Progression progression = getProgression(parts, formulaProgressionA, formulaProgressionG);
                    session.save(progression);
                }
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
        }
    }

    private static Progression getProgression(String[] parts, FormulaProgression formulaProgressionA, FormulaProgression formulaProgressionG) {
        String type = parts[0];
        int start = Integer.parseInt(parts[1]);
        int step = Integer.parseInt(parts[2]);
        int terms = Integer.parseInt(parts[3]);
        String formula = parts[4];

        Progression progression = new Progression();
        progression.setCurrentIndex(0);
        progression.setStart(start);
        progression.setStep(step);
        progression.setTerms(terms);
        progression.setType(type);

        if (Objects.equals(type, "arithmetic")) progression.setFormula(formulaProgressionA);
        if (Objects.equals(type, "geometric")) progression.setFormula(formulaProgressionG);

        return progression;
    }

}
