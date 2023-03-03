package hr.mmaracic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "app_user")
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class) //Enables auditing
public class User {

    @Id
    @EqualsAndHashCode.Exclude
    @GeneratedValue(generator = "sequence-generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequence-generator", sequenceName = "app_user_seq", allocationSize = 1)
    private Long id;

    @Size(min = 1, max = 10)
    @NotNull
    private final String name;

    @CreatedDate
    @EqualsAndHashCode.Exclude
    private OffsetDateTime created;

}
