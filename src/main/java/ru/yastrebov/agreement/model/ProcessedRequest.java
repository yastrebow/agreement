package ru.yastrebov.agreement.model;

import lombok.*;
import ru.yastrebov.agreement.model.enums.Status;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "processed_requests")
public class ProcessedRequest {

    @Id
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "birth_date")
    LocalDate birthDate;

    @Column(name = "amount")
    Long amount;

    @Column(name = "term")
    Integer term;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    Status status;
}

