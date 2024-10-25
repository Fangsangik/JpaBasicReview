package hellojpa.연관관계;

import hellojpa.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class 단방향 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Team team = new Team();
            team.setName("TeamA");
            //team.getMemberBs().add(memberB);
            em.persist(team);

            MemberB memberB = new MemberB();
            memberB.setName("b");
            memberB.setTeam(team);
            em.persist(memberB);

            //team.getMemberBs().add(memberB);

            //em.flush();
            //em.clear();

            Team team1 = em.find(Team.class, team.getId());
            List<MemberB> list = team1.getMemberBs();

            System.out.println("============");
            for (MemberB b : list) {
                System.out.println("b.getName() = " + b.getName());
            }
            System.out.println("=============");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
