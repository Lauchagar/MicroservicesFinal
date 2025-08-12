package com.sistemasactivos.metrics.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Metric {
    
    private String name;
    private String description;
    private String baseUnit;
    private List<Measurement> measurements;
    private List<AvailableTag> availableTags;
    
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AvailableTag {
        private String tag;
        private List<String> values;
    }
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Measurement {
        private String statistic;
        private long value;
    }
}