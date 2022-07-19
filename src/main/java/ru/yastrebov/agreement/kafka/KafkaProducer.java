package ru.yastrebov.agreement.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yastrebov.agreementStatisticsLib.model.AgreementStatistics;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, AgreementStatistics> kafkaTemplate;

    public AgreementStatistics sendMessage(@RequestBody AgreementStatistics message) {

        ListenableFuture<SendResult<String, AgreementStatistics>> future = kafkaTemplate.send("agreement_statistics", message);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, AgreementStatistics> result) {
                log.info("Sent message=[" + message
                        + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(@NotNull Throwable exception) {
                log.info("Unable to sent message=["
                        + message + "] due to: " + exception.getMessage());
            }
        });
        return message;
    }
}
