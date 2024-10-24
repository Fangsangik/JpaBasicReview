package hellojpa.영속성_컨텍스트;

import hellojpa.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class 변경감지 {
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
            Member findMember = em.find(Member.class, 150L);
            findMember.setName("ZZZZZZ");

            //찾아온 다음에 data 변경 -> 굳이 persist 안해도 됨
            //em.persist(member);

            System.out.println("findMember.getId() = " + findMember.getId());

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

