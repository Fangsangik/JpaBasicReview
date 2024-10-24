package hellojpa.applicationworkout;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class CreateMember {
    public static void main(String[] args) {
        //unitName을 넘긴다.
        //DB와 연결 다 됨 
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hello");

        //스레드간 절대 공유 X
        EntityManager em = emf.createEntityManager();
        //모든 데이터 변경은 Transaction 내에서 설계해야 함
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //code
        try {
            //JPA의 모든 코드는 transaction에서 실행해야 한다.
            Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");
            em.persist(member);

            //commit 안하면 반영 안됨
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

