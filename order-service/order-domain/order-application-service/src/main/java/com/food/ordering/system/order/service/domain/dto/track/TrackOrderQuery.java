package com.food.ordering.system.order.service.domain.dto.track;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class TrackOrderQuery {
    @NotNull
    final UUID orderTrackingId;

}
