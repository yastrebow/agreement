package ru.yastrebov.agreement.kafka;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yastrebov.agreement.model.ProcessedRequestDTO;

import javax.validation.constraints.NotNull;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, ProcessedRequestDTO> kafkaTemplate;

    public ProcessedRequestDTO sendMessage(@RequestBody ProcessedRequestDTO message) {

        ListenableFuture<SendResult<String, ProcessedRequestDTO>> future = kafkaTemplate.send("agreement_requests", message);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, ProcessedRequestDTO> result) {
                System.out.println("Sent message=[" + message
                        + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(@NotNull Throwable exception) {
                System.out.println("Unable to sent message=["
                        + message + "] due to: " + exception.getMessage());
            }

        });
        return message;
    }

    public ProcessedRequestDTO createMessageForSending(ProcessedRequestDTO processedRequestDTO) {

        return processedRequestDTO;
    }
}
