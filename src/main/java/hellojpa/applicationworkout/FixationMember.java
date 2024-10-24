package hellojpa.applicationworkout;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class FixationMember {
    public static void main(String[] args) {
        //unitName을 넘긴다.
        //DB와 연결 다 됨
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //code
        try {
            //수정
            //변경 되었는지 안되었는지 transaction에서 관리
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("HelloJpa");
            //em.persist(findMember); 저장 안해도 됨, Java collection을 다루는 개념

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            //DataBase가 무언가를 물고 동작, 꼭 닫고 동작
            em.close();
        }
        emf.close();
    }
}

