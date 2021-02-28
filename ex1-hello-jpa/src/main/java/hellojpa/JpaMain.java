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
// 멤버 생성
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
//            // em.persist(member); // 멤버 저장. DB에 저장하는 것이 아니라 영속성컨텍스트에 저장하는 것이다. 이는 커밋이 되야 DB에 저장됨
//
//            // 멤버 찾기
//            //Member findMember = em.find(Member.class,1L);
//            //System.out.println("findMember = "+findMember.getId());
//            List<Member> result = em.createQuery("select m from Member as m", Member.class) // 멤버 객체를 대상으로 쿼리를 짠다
//                    .setFirstResult(5) // 5번부터
//                    .setMaxResults(8) // 8개 가져와
//                    .getResultList();
//
//            for (Member member : result){
//                System.out.println("member.name = "+member.getName());
//            }
//
//            // 멤버 삭제
//            Member findMember = em.find(Member.class,1L);
//            em.remove(findMember);

            // 멤버 수정
              //Member findMember = em.find(Member.class,2L);
              //findMember.setName("HelloJPB"); // em에서 찾아온 객체는 JPA가 관리하여 변경사항있으면 커밋직전에 update 쿼리가 날라감

              //em.persist(findMember); // 수정하고 저장? -> 안해도됨. 자바 컬렉션에서 setName 와 같이 수정한 것이므로 수정하면 끝이지 다시 컬렉션에 넣을 필요없다
            tx.commit(); // 잘되면  커밋
        } catch(Exception e){
            tx.rollback(); // 문제시 롤백
        } finally {
            em.close(); // 작업 끝나면 엔티티매니저 닫아줌
        }

        emf.close();
    }
}
