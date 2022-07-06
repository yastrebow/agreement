package ru.yastrebov.agreement.kafka;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yastrebov.agreement.exception.handler.MethodNotAllowedException;
import ru.yastrebov.agreement.model.ProcessedRequest;
import ru.yastrebov.agreement.model.ResultToMongo;
import ru.yastrebov.agreement.model.enums.Status;

import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, ResultToMongo> kafkaTemplate;

    public void sendMessage(@RequestBody ResultToMongo message) {

        ListenableFuture<SendResult<String, ResultToMongo>> future = kafkaTemplate.send("agreement_requests", message);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, ResultToMongo> result) {
                System.out.println("Sent message=[" + message
                        + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(@NotNull Throwable exception) {
                System.out.println("Unable to sent message=["
                        + message + "] due to: " + exception.getMessage());
            }
        });
    }
}
