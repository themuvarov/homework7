package com.example.bike.service;

import com.example.bike.model.RentRequestMessage;
import com.example.bike.model.RentResponseMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.example.bike.model.RentRequestMessage.Type.UNRENT;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumer {

    private final BikeService bikeService;

    private final KafkaTemplate<String, RentResponseMessage> kafkaTemplate;

    @KafkaListener(topics = "rent-in", groupId = "bike", containerFactory = "kafkaListenerContainerFactory")
    public void getCommands(RentRequestMessage message) {

        switch (message.getCommand()) {
            case CHECK -> {
                RentResponseMessage response = new RentResponseMessage();
                if (bikeService.checkIfAvailable(message)) {
                    response.setType(RentResponseMessage.Type.AVAILABLE);
                    response.setMessage("Ok");
                    response.setWorkflowId(message.getWorkflowId());
                } else {
                    response.setType(RentResponseMessage.Type.NOT_AVAILABLE);
                    response.setMessage("Busy");
                    response.setWorkflowId(message.getWorkflowId());
                }
                kafkaTemplate.send("rent-out", response);
            }
            case RENT -> {
                RentResponseMessage response = new RentResponseMessage();
                if (bikeService.checkIfAvailable(message)) {
                    bikeService.occupyBike(message);

                    response.setType(RentResponseMessage.Type.OCCUPIED);
                    response.setMessage("Busy");
                    response.setWorkflowId(message.getWorkflowId());

                } else {
                        response.setType(RentResponseMessage.Type.NOT_AVAILABLE);
                        response.setMessage("Busy");
                        response.setWorkflowId(message.getWorkflowId());
                }
                kafkaTemplate.send("rent-out", response);
            }
            case UNRENT -> {
                RentResponseMessage response = new RentResponseMessage();
                if (!bikeService.checkIfAvailable(message)) {
                    bikeService.freeBike(message);

                }
                /*response.setType(RentResponseMessage.Type.AVAILABLE);
                response.setMessage("Ok");
                response.setWorkflowId(message.getWorkflowId());
                kafkaTemplate.send("rent-out", response);*/

            }
        }

        log.info("Rent service received: {} {} {} {}", message.getBikeQr(), message.getMessage(),
                message.getRegion(), message.getWorkflowId());
    }
}
