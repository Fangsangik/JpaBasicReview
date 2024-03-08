package main1;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class SameEntity {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction emt = em.getTransaction();
        emt.begin();

        try {
            Member member = new Member();
            member.setId(1L);
            member.setName("memberA");

            Member member1 = new Member();
            member.setId(2L);
            member.setName("memberB");

            em.persist(member);
            em.persist(member1);

            Member a = em.find(Member.class, "memberA");
            Member b = em.find(Member.class, "memberA");

            System.out.println(a == b);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
