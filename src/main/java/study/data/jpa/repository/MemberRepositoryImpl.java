package study.data.jpa.repository;

import lombok.RequiredArgsConstructor;
import study.data.jpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    /**
     * 다양한 이유로 인터페이스의 메서드를 직접 구현하고 싶다면?
     * JPA 직접 사용(EntityManager)
     * 스프링 JDBC Template 사용 MyBatis 사용
     * 데이터베이스 커넥션 직접 사용 등등
     * Querydsl 사용
     */

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member  m")
                .getResultList();
    }
}
