package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // 애플리케이션 로딩 시점에 하나 생성
        EntityManager em = emf.createEntityManager(); // 엔티티 매니저 꺼냄. 트랜잭션 한 단위마다 만들어줘야한다
        // code
        EntityTransaction tx = em.getTransaction(); // JPA는 트랜잭션 안에서 데이터변경이 일어나야한다.
        tx.begin(); // 트랜잭션 시작

        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team); // 영속성 컨텍스트가 되면 무조건 id는 알게 됨

            Member member = new Member();
            member.setName("member1");
           // member.setTemaId(team.getId()); // 객체 지향 스럽진 않다. setTeam 이여야 되지 않을까?
            member.setTeam(team); // 객체지향스럽게 리모델링. Team을 멤버에 넣는다.
            em.persist(member);

            em.flush(); // 영속성 컨텍스트에 있는걸 DB에 날림
            em.clear(); // 영속성 컨텍스트 초기화

            Member findMember = em.find(Member.class, member.getId()); // em.flush(), em.clear() 없으면, 캐시에서 가져옴. em.persist를 통해 1차 캐시에 넣어져있으므로

            List<Member> members = findMember.getTeam().getMembers();

            for (Member m : members){
                System.out.println("m="+m.getName());
            }
            //Long findTeamId = findMember.getTemaId();
            //Team findTeam = em.find(Team.class,findTeamId); // 객체 지향 스럽지 않다. 찾은멤버의 팀을 찾기위해 teamId를 구하고 team을 알아낸다...
            Team findTeam = findMember.getTeam(); // 객체지향스럽게 리모델링. Team을 바로 꺼냄
            System.out.println("findMember = "+findTeam.getName());

//            Team newTeam = em.find(Team.class,100L); // 100번 팀 찾음
//            findMember.setTeam(newTeam); // 멤버를 100번 팀으로 팀 변경

            tx.commit(); // 잘되면  커밋
        } catch(Exception e){
            tx.rollback(); // 문제시 롤백
        } finally {
            em.close(); // 작업 끝나면 엔티티매니저 닫아줌
        }
        emf.close();
    }
}
