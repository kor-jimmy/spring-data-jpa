package study.data.jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.data.jpa.entity.Member;
import study.data.jpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/member/{id}")
    public String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    // 도메인 클래스 컴버터
    // 실무 권장 X
    @GetMapping("/member2/{id}")
    public String findMember2(@PathVariable("id") Member member){
        return member.getUsername();
    }

    // /members?page=0&size=5&sort=id,desc&sort=username,desc
    // sort는 기본이 asc
    @GetMapping("/members")
    public Page<Member> list(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        return page;
    }

    @PostConstruct
    public void init() {
        for(int i=0; i<100; i++){
            memberRepository.save(new Member("user"+i, i));
        }

    }

}
