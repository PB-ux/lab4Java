package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import java.util.List;

import org.example.model.Progression;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.openSession();

//        ProgressionFileManager fileManager = new ProgressionFileManager("resources/data.csv");

//        fileManager.insertRows(session);
//        List<Progression> progressions = loadAllData(Progression.class, session);
//        for (int i = 0; i < progressions.size(); i++) {
//            System.out.println(progressions.get(i).getType());
//        }

//        changeData(progressions, session);

//        List<Progression> progressionsWithValue3 = getByCondition(3, session);
//        System.out.println("Progressions with value = 3:");
//        for (Progression progression : progressionsWithValue3) {
//            System.out.println(progression.getStart());
//        }

        pagination(session, 1, 10);

        session.close();
        sessionFactory.close();
    }

    private static <T> List<T> loadAllData(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);

        criteria.from(type);

        List<T> data = session.createQuery(criteria).getResultList();

        return data;
    }

    private static void changeData(List<Progression> progressions, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            for (Progression progression : progressions) {
                progression.setStep(10);
                session.update(progression);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void getByCondition(Session session, int pageNumber, int pageSize) {
        String hql = "SELECT p FROM Progression p JOIN p.formulaProgression f WHERE p.type = :type AND f.formula = :formula";
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            Query<Progression> query = session.createQuery(hql, Progression.class);
            query.setParameter("type", "arithmetic");
            query.setParameter("formula", "a");
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);

            List<Progression> resultList = query.getResultList();

            for (Progression progression : resultList) {
                System.out.println("Type: " + progression.getType() + ", Start: " + progression.getStart() + ", Step: " + progression.getStep());
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void pagination(Session session, int pageNumber, int pageSize) {
        Transaction transaction = null;

        String hql = "SELECT p FROM Progression p";

        try {
            transaction = session.beginTransaction();

            Query<Progression> query = session.createQuery(hql, Progression.class);

            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);

            List<Progression> result = query.getResultList();

            for (Progression progression : result) {
                System.out.println("Type: " + progression.getType() + ", Start: " + progression.getStart() + ", Step: " + progression.getStep());
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}