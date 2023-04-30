package study.data.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.data.jpa.dto.MemberDTO;
import study.data.jpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /*
    * @Query 어노테이션의 장점은 정적 쿼리이기 때문에 어플리케이션 실행 당시 에러 확인 가능
    * */
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUserNameList();

    /**
     * DTO 조회
     */
    @Query("select new study.data.jpa.dto.MemberDTO(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDTO> findMemberDto();

    /**
     * 컬렉션 파라메터  조회
     */
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username);
    Member findMemberByUsername(String username);
    Optional<Member> findOptionalByUsername(String username);

    // total count 같이 조회
    Page<Member> findByAge(int age, Pageable pageable);

    /**
    * JPA 에서 벌크성 연산은 조심히 사용해야함
     * 영속성 컨텍스트 이슈
     * @Modifying(clearAutomatically = true) query 이후 clear
    */
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age+1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);


    /**
     * n+1 fetch join
     * */
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    /**
     * n+1 fetch join
     * @EntityGraph 어노테이션 활용
     * */
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();
}
