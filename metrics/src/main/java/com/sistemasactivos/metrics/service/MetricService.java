package com.sistemasactivos.metrics.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemasactivos.metrics.interfaces.IMetricService;
import com.sistemasactivos.metrics.model.DiskSpace;
import com.sistemasactivos.metrics.model.MemoryUsage;
import com.sistemasactivos.metrics.model.Metric;
import com.sistemasactivos.metrics.repository.DiskSpaceRepository;
import com.sistemasactivos.metrics.repository.MemoryUsageRepository;
import jakarta.transaction.Transactional;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class MetricService implements IMetricService{

    @Autowired
    private DiskSpaceRepository diskSpaceRepository;
    @Autowired
    private MemoryUsageRepository memoryUsageRepository;
    
    private final WebClient webClientMS1;
    
    public MetricService(@Qualifier("webClientMS1")WebClient webClientMS1) {
        this.webClientMS1 = webClientMS1;
    }
    
    @Override
    public Mono<String> getStatusMS1() {
        return webClientMS1.get()
                .uri("/health")
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(responseBody -> {
                try {
                    JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
                    String status = jsonNode.get("status").asText();
                    return Mono.just(status);
                } catch (Exception e) {
                    return Mono.error(new RuntimeException("Error al procesar la respuesta JSON", e));
                }
            });
    }
    
    @Override
    public Mono<Metric> getDiskFreeMS1() {
        return webClientMS1.get()
                .uri("/metrics/disk.free")
                .retrieve()
                .bodyToMono(Metric.class);
    }
    
    @Override
    public Mono<Metric> getDiskTotalMS1() {
        return webClientMS1.get()
                .uri("/metrics/disk.total")      
                .retrieve()
                .bodyToMono(Metric.class);
    }

    @Override
    public Mono<Metric> getMemoryUsedMS1() {
        return webClientMS1.get()
                .uri("/metrics/jvm.memory.used")      
                .retrieve()
                .bodyToMono(Metric.class);
    }
    
    @Override
    public Mono<Metric> getMemoryMaxMS1() {
        return webClientMS1.get()
                .uri("/metrics/jvm.memory.max")      
                .retrieve()
                .bodyToMono(Metric.class);
    }
    
    @Transactional
    @Override
    public void saveDiskMS1() throws Exception{
        LocalDateTime horario = LocalDateTime.now().withNano(0);
        String ms = "Account";
        
        Mono<Double> diskFree = getDiskFreeMS1().flatMap(metric -> {
            if (metric != null && !metric.getMeasurements().isEmpty()) {
                Metric.Measurement measurement = metric.getMeasurements().get(0);
                double value = gigaBytesDosDecimales((double)measurement.getValue());
                return Mono.just(value);
            }
            else{
                return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo recuperar el valor."));
            }
        });
        
        Mono<String> status = getStatusMS1();
        
        Mono<Double> diskTotal = getDiskTotalMS1().flatMap(metric -> {
            if (metric != null && !metric.getMeasurements().isEmpty()) {
                Metric.Measurement measurement = metric.getMeasurements().get(0);
                double value = gigaBytesDosDecimales((double)measurement.getValue());
                return Mono.just(value);
            }
            else{
                return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo recuperar el valor."));
            }
        });     
        
        diskFree.subscribe(diskFreeValue -> {
            status.subscribe(statusValue -> {
                diskTotal.subscribe(diskTotalValue -> {
                    DiskSpace diskSpace = new DiskSpace(horario, ms, diskFreeValue, statusValue, diskTotalValue);
                    diskSpaceRepository.save(diskSpace);
                });
            });
        });
    }
    
    @Transactional
    @Override
    public void saveMemoryMS1() throws Exception{
        LocalDateTime horario = LocalDateTime.now().withNano(0);
        String ms = "Account";
        
        Mono<Double> memoryUsed = getMemoryUsedMS1().flatMap(metric -> {
            if (metric != null && !metric.getMeasurements().isEmpty()) {
                Metric.Measurement measurement = metric.getMeasurements().get(0);
                double value = megaBytesDosDecimales((double)measurement.getValue());
                return Mono.just(value);
            }
            else{
                return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo recuperar el valor."));
            }
        });
        
        Mono<String> status = getStatusMS1();
        
        Mono<Double> memoryMax = getMemoryMaxMS1().flatMap(metric -> {
            if (metric != null && !metric.getMeasurements().isEmpty()) {
                Metric.Measurement measurement = metric.getMeasurements().get(0);
                double value = megaBytesDosDecimales((double)measurement.getValue());
                return Mono.just(value);
            }
            else{
                return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo recuperar el valor."));
            }
        });     
        
        memoryUsed.subscribe(memoryUsedValue -> {
            status.subscribe(statusValue -> {
                memoryMax.subscribe(memoryMaxValue -> {
                    MemoryUsage memoryUsage = new MemoryUsage(horario, ms, memoryUsedValue, statusValue, memoryMaxValue);
                    memoryUsageRepository.save(memoryUsage);
                });
            });
        });
    }
    
    private double gigaBytesDosDecimales(double valor) {
        double gigabytes = valor / (1024 * 1024 * 1024);
        DecimalFormat formato = new DecimalFormat("#.##");
        String valorFormateado = formato.format(gigabytes);
        valorFormateado = valorFormateado.replace(",", ".");
        return Double.parseDouble(valorFormateado);
    }
    private double megaBytesDosDecimales(double valor) {
        double megabytes = valor / (1024 * 1024);
        DecimalFormat formato = new DecimalFormat("#.##");
        String valorFormateado = formato.format(megabytes);
        valorFormateado = valorFormateado.replace(",", ".");
        return Double.parseDouble(valorFormateado);
    }
}
