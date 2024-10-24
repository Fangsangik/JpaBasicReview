package hellojpa.applicationworkout;

import hellojpa.Member;
import jakarta.persistence.*;

import java.util.List;

public class FindMember {
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
            //조회
            //Member findMember = em.find(Member.class, 1L);
            //System.out.println("findMember.getName() = " + findMember.getName());
            //System.out.println("findMember.getId() = " + findMember.getId());
            
            //JPQL
            //Member 객체를 갖고 쿼리를 짬, table로 검색 X
            List<Member> findMember = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10) //1~10번 가져와
                    .getResultList();

            for (Member member : findMember) {
                System.out.println("member.getName() = " + member.getName());
            }

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

