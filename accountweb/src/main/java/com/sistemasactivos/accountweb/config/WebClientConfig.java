package com.sistemasactivos.accountweb.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig implements WebFluxConfigurer{
    
    @Value("${base.url}")
    private String baseUrl;
    @Value("${auth.username}")
    private String username;
    @Value("${auth.password}")
    private String password;
    
    @Bean
    public WebClient getWebClient(){
        
        HttpClient httpClinet = HttpClient.create()
                .tcpConfiguration(client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(1000))
                        .addHandlerLast(new WriteTimeoutHandler(10))));
        
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClinet.wiretap(true));
        
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(headers -> headers.setBasicAuth(username, password))
                .clientConnector(connector)
                .build();
    }
    
}
