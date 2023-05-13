package study.data.jpa.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

// 실제 상속관계가 아님
@MappedSuperclass
@Getter
public class JpaBaseEntity {

    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // persist 하기 전에 발생하는 메소드 정의
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdDate = now;
        this.updatedDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

}
