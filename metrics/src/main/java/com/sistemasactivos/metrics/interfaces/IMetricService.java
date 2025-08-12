package com.sistemasactivos.metrics.interfaces;

import com.sistemasactivos.metrics.model.Metric;
import reactor.core.publisher.Mono;


public interface IMetricService {
    
    public Mono<String> getStatusMS1();
    public Mono<Metric> getDiskFreeMS1();
    public Mono<Metric> getDiskTotalMS1();
    public Mono<Metric> getMemoryUsedMS1();
    public Mono<Metric> getMemoryMaxMS1();
    public void saveDiskMS1() throws Exception;
    public void saveMemoryMS1() throws Exception;
}
