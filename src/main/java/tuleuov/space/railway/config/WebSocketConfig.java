package tuleuov.space.railway.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import tuleuov.space.railway.controller.CarriageSeatSocketHandler;
import tuleuov.space.railway.service.CarriageService;
import tuleuov.space.railway.service.RouteService;
import tuleuov.space.railway.service.SeatService;

import java.util.List;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker( "/carriage-seats");
//        config.setApplicationDestinationPrefixes("/book");
//        config.setUserDestinationPrefix("/carriage-seats");
//    }
    private final SeatService seatService;
    private final CarriageService carriageService;
    private  final RouteService routeService;

    public WebSocketConfig(SeatService seatService, CarriageService carriageService, RouteService routeService) {
        this.seatService = seatService;
        this.carriageService = carriageService;
        this.routeService = routeService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(carriageSeatSocketHandler(), "/carriage-seats").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler carriageSeatSocketHandler(){
        return new CarriageSeatSocketHandler(seatService, carriageService, routeService);
    }



//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry
//                .addEndpoint("/ws")
//                .setAllowedOrigins("*")
//                .withSockJS();
//    }

//    @Override
//    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
//        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
//        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setObjectMapper(new ObjectMapper());
//        converter.setContentTypeResolver(resolver);
//        messageConverters.add(converter);
//        return false;
//    }
}

