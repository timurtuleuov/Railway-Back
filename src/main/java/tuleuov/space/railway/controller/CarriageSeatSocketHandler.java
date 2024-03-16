package tuleuov.space.railway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import tuleuov.space.railway.dto.CarriageSeatUpdateMessage;
import tuleuov.space.railway.dto.SeatDTO;
import tuleuov.space.railway.entity.Carriage;
import tuleuov.space.railway.entity.Route;
import tuleuov.space.railway.entity.Seat;
import tuleuov.space.railway.service.*;

import java.util.List;

@Slf4j
public class CarriageSeatSocketHandler implements WebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SeatService seatService;
    private final CarriageService carriageService;
    private final RouteService routeService;

    @Autowired
    public CarriageSeatSocketHandler(SeatService seatService, CarriageService carriageService, RouteService routeService) {
        this.seatService = seatService;
        this.carriageService = carriageService;
        this.routeService = routeService;
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Выводим сообщение о успешном установлении соединения
        System.out.println("Connection established successfully.");
    }


    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        try {
            CarriageSeatUpdateMessage updateMessage = objectMapper.readValue(message.getPayload().toString(), CarriageSeatUpdateMessage.class);
//            Carriage carriage = carriageService.getCarriageById(updateMessage.getCarriageId());

            List<Seat> updatedSeats = seatService.getSeatsByCarriageId(updateMessage.getCarriageId());

            // Преобразование в список SeatDTO
            List<SeatDTO> updatedSeatDTOs = seatService.mapSeatsToSeatDTOs(updatedSeats);

            // Отправляем обновленный список мест через WebSocket
            session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(updatedSeatDTOs)));

        } catch (Exception e) {
            // Обработка исключений
            e.printStackTrace();
        }
    }




    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // Журналируем ошибку транспорта
        System.out.println("Transport error occurred: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // Выводим информацию о закрытии соединения
        System.out.println("Connection closed. Status: " + closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        // Выводим информацию о поддержке частичных сообщений
        System.out.println("Partial messages supported: false");
        return false;
    }

}
