package ssp;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class KafkaProducerExample {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092"); // 关键配置
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 6; i < 10; i++) {
            System.out.println("发送消息：" + i);
            producer.send(new ProducerRecord<>("dzr", "message-" + i));
        }
        producer.close();
        System.out.println("消息已发送！");
    }
}
