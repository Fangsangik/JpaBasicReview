package main1;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Find_1_Cache {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setId(1L);
            member.setName("UserA");

            em.persist(member);

            Member findMember = em.find(Member.class, "UserA");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();

    }
}
