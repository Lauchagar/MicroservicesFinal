package com.sistemasactivos.metrics.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig  implements WebFluxConfigurer{
    @Value("${base.url.ms1}")
    private String urlms1;
    
    @Bean
    @Qualifier("webClientMS1")
    public WebClient webclient1(){
        return getWebClient(urlms1);
    }
    
//    org.slf4j.Logger logger= LoggerFactory.getLogger(WebFluxConfig.class);
    
    private WebClient getWebClient(String baseUrl){
        
        HttpClient httpClinet = HttpClient.create()
                .tcpConfiguration(client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000000)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(10000))
                        .addHandlerLast(new WriteTimeoutHandler(10))));
        
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClinet.wiretap(true));
        
        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(connector)
                .defaultHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
                .build();   
    }
}
