package hellojpa.영속성_컨텍스트;

import hellojpa.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Flush {
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
            //영속
            Member member = new Member("member200", 200L);
            em.persist(member);

            // 강제로 호출 (쿼리가 날라가는 것을 보고 싶다)
            em.flush();
            System.out.println("=========================");

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

