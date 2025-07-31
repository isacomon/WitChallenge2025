package com.example.rest.producer;

import com.example.requests.CalculatorRequest;
import com.example.responses.CalculatorResponse;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.messaging.Message;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CalculatorProducerTests {

    @Mock
    private ReplyingKafkaTemplate<String, CalculatorRequest, CalculatorResponse> kafkaTemplate;

    @InjectMocks
    private CalculatorProducer producer;

    @BeforeEach
    void setUp() throws Exception {
        Field requestField = CalculatorProducer.class.getDeclaredField("requestTopic");
        requestField.setAccessible(true);
        requestField.set(producer, "calculator-topic");

        Field replyField = CalculatorProducer.class.getDeclaredField("replyTopic");
        replyField.setAccessible(true);
        replyField.set(producer, "calculator-reply-topic");
    }

    @Test
    void calculatorProducerTests_HappyPath() throws Exception {
        // Setup
        CalculatorRequest request = new CalculatorRequest("ADD", BigDecimal.valueOf(2), BigDecimal.valueOf(2));
        CalculatorResponse response = new CalculatorResponse(BigDecimal.valueOf(4));

        ConsumerRecord<String, CalculatorResponse> consumerRecord = new ConsumerRecord<>(
                "calculator-reply-topic", 0, 0L, null, response
        );
        RequestReplyFuture<String, CalculatorRequest, CalculatorResponse> mockFuture = mock(RequestReplyFuture.class);
        when(kafkaTemplate.sendAndReceive((ProducerRecord<String, CalculatorRequest>)any())).thenReturn(mockFuture);
        when(mockFuture.get(10, TimeUnit.SECONDS)).thenReturn(consumerRecord);

        MDC.put("requestId", "1111");

        try{
            // Act
            CalculatorResponse result = producer.sendCalcRequest(request);
            // Assert
            assertEquals(BigDecimal.valueOf(4), result.getResult());
        } catch (Exception e){
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }
}
