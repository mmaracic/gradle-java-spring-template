package hr.mmaracic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "app_user")
@NoArgsConstructor
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
    @NonNull
    private String name;

    @CreatedDate
    @EqualsAndHashCode.Exclude
    private OffsetDateTime created;

}
