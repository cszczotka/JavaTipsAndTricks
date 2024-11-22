package com.cszczotka.kilka;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

record Metric(UUID id, UUID deviceId, ZonedDateTime time, BigDecimal value) {}
public interface MetricClient {
    List<Metric> getAllByDeviceIdIn(Collection<UUID> deviceIds);
}
