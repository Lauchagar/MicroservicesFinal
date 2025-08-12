package com.sistemasactivos.apirest.bff.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
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
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
//@EnableWebFluxSecurity
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer  {
    
    @Value("${base.url.ms1}")
    private String urlms1;
    @Value("${base.url.ms2}")
    private String urlms2;
    
    @Bean
    @Qualifier("webClientAccount")
    public WebClient webclient1(){
        return getWebClient(urlms1);
    }
    
    @Bean
    @Qualifier("webClientToken")
    public WebClient webclient2(){
        return getWebClient(urlms2);
    }
    
    
    private WebClient getWebClient(String baseUrl){
        
        HttpClient httpClinet = HttpClient.create()
                .tcpConfiguration(client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000000)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(10000))
                        .addHandlerLast(new WriteTimeoutHandler(10))));
        
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClinet.wiretap(true));
        
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(
                HttpClient.create().secure(sslContextSpec -> sslContextSpec.sslContext(
                        SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)
                )
                )))
                .baseUrl(baseUrl)
                .clientConnector(connector)
                .defaultHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
                .build();   
    }
}

