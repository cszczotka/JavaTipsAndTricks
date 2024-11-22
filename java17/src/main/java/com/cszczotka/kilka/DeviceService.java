package com.cszczotka.kilka;


import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

record DeviceDetailsDto(UUID id, String name, ZonedDateTime lastRebootTime, List<MetricDto> lastMetrics) {}
record MetricDto(UUID id, ZonedDateTime time, BigDecimal value) {}

/*
  Assume we have 2 clients - DeviceClient and MetricClient.
  DeviceClient may return short info about devices by their ids.
  MetricClient may return all metrics for the list of devices by their ids.
  Both clients are configured as Spring beans that can be autowired to other beans.
  You need to implement a method getDevicesDetails(Set<UUID> ids) in DeviceService class.
  The method must return the list of devices details.
  !!!IMPORTANT  metrics history in device details must contain
                ONLY items registered after the last device reboot time.
  !!!NOTE  code style will also be taken into account when the solution is evaluated.
*/

public class DeviceService {
    private final DeviceClient deviceClient;
    private final MetricClient metricClient;

    public DeviceService(DeviceClient deviceClient, MetricClient metricClient) {
        this.deviceClient = deviceClient;
        this.metricClient = metricClient;
    }

    public List<DeviceDetailsDto> getDevicesDetails(Set<UUID> ids) {
        // TODO implement
        // read the description of the task in the comment at the top of the file
        List<DeviceDetailsDto> result = new ArrayList<>();
        List<Device> devices = deviceClient.getAllByIdIn(ids);
        List<Metric> metrics = metricClient.getAllByDeviceIdIn(ids);

        for(Device device : devices) {
            List<MetricDto> deviceMetrics = toMetricDtoList(getMetricsByDeviceId(device.id(), device.lastRebootTime(), metrics));
            result.add(new DeviceDetailsDto(device.id(), device.name(), device.lastRebootTime(), deviceMetrics));
        }
        return result;
    }

    static List<Metric> getMetricsByDeviceId(UUID deviceId, ZonedDateTime lastRebootTime, List<Metric> metrics) {
        return metrics.stream()
                .filter(m -> m.deviceId().toString().equals(deviceId.toString()))
                .filter( m-> m.time().toInstant().toEpochMilli() >= lastRebootTime.toInstant().toEpochMilli())
                .collect(Collectors.toList());
    }

   static List<MetricDto> toMetricDtoList(List<Metric> metrics) {
        return metrics.stream()
                .map(m -> new MetricDto(m.id(), m.time(), m.value()))
                .collect(Collectors.toList());
    }

}

