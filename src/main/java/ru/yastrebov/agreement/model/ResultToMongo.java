package ru.yastrebov.agreement.model;

import lombok.*;
import ru.yastrebov.agreement.model.enums.Status;

import java.time.ZonedDateTime;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ResultToMongo {

    Long id;

    ZonedDateTime date;

    Status status;
}
