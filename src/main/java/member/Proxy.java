package member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Proxy {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member1 member1 = em.find(Member1.class, 1L);
            printMemberAndTea(member1);
            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void printMemberAndTea(Member1 member1){
        String userName = member1.getUserName();
        Team team = member1.getTeam();

        System.out.println("userName = " + userName);
        System.out.println("team = " + team);
    }

    private static void printMember(Member1 member1){
        System.out.println("member1 = " + member1.getUserName());
    }
}
