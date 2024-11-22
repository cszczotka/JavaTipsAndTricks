package com.cszczotka.kilka;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

record Device(UUID id, String name, ZonedDateTime lastRebootTime) {}

public interface DeviceClient {
    List<Device> getAllByIdIn(Collection<UUID> ids);
}

