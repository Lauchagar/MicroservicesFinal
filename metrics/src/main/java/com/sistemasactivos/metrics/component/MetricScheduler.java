package com.sistemasactivos.metrics.component;

import com.sistemasactivos.metrics.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class MetricScheduler {
    
    @Autowired
    private MetricService metricService;
    // Ejecuta el metodo cada 30 segundos
    @Scheduled(cron = "0/30 * * * * *")
    public void saveMetrics() throws Exception{
            metricService.saveDiskMS1();
            metricService.saveMemoryMS1();
    }

}


