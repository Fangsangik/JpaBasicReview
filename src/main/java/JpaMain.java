import main1.Member;
import member.Address;
import member.Member1;
import member.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        //데이터에 관한 작업은 transaction에서 해줘야 함
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //등록
        try {
            Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");

            em.persist(member);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();

        //조회
        try {
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getName() = " + findMember.getName());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

        //수정
        try {
            Member findMember = em.find(Member.class, 2L);
            findMember.setName("GoodBye");
            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

        //삭제
        try {
            Member findMember = em.find(Member.class, 1L);
            em.remove(findMember);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

         //JPQL
        List<Member> rst = em.createQuery("select m from Member as m", Member.class)
                .setFirstResult(5)
                .setMaxResults(8)
                .getResultList();
        for (Member member :
                rst) {
            System.out.println("member = " + member.getName());
        }

        //Team Member 동시 조회
        try{
            Member1 member1 = em.find(Member1.class, 1L);
            printMember(member1);
            tx.commit();

            em.flush();
            em.clear();

        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

        //프록시
        try {
            Member1 member1 = new Member1();
            member1.setUserName("Hello");

            em.persist(member1);
            em.flush();
            em.clear();

            Member1 findMember = em.find(Member1.class, member1.getId());
            Member1 findByReference = em.getReference(Member1.class, member1.getId());
            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getUserName() = " + findMember.getUserName());
            System.out.println("findByReference.getId() = " + findByReference.getId());

            Address address = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("o1d1", address.getStreet(), address.getZipCode()));
            findMember.getFavoriteFood().remove("한식");
            findMember.getFavoriteFood().add("양식");



            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
        
    }

    private static void printMember(Member1 member1) {
        System.out.println("member1.getUserName() = " + member1.getUserName());
    }

    private static void printMemberAndTeam(Member1 member1) {
        String userName = member1.getUserName();
        System.out.println("userName = " + userName);

        Team team = member1.getTeam();
        System.out.println("team = " + team);
    }
}

