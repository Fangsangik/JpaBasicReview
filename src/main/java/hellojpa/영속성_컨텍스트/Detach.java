package hellojpa.영속성_컨텍스트;

import hellojpa.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Detach {
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
            Member findMember1 = em.find(Member.class, 150L);
            //dirty Checking
            findMember1.setName("AAAAAA");

            //JPA에서 관리 X -> transaction시 아무것도 안올라감
            em.clear();

            Member findMember2 = em.find(Member.class, 150L);


            System.out.println("=======================");

            //commit 시점에 db 날라감
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

