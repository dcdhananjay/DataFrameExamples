package com.mycompany.kafka.example;

import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class MessageReceiver {

    private String topic = "";
    private String group = "";

    private String serverIP = "";
    private String serverPort = "";

    private void consume() {
        Properties prop = new Properties();
        prop.put("bootstrap.servers", serverIP + ":" + serverPort);
        prop.put("group.id", group);
        prop.put("auto.commit.interval.ms", "1000");
        prop.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(prop);
        consumer.subscribe(Arrays.asList(topic));
        for (int i = 0; i < 1000; ++i) {
            ConsumerRecords<String, String> records = consumer.poll(1000L);
            System.out.println("size = " + records.count());
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Received Message : " + record.value());
            }
        }
        System.out.println("End");
    }

    public static void main(String[] args) {
        MessageReceiver receiver = new MessageReceiver();
        try {
            String[] input = args[0].split("~");
            receiver.serverIP = input[0];
            receiver.serverPort = input[1];
            receiver.topic = input[2];
            receiver.group = input[3];
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Invalid Input!! Expected input : ServerIP~ServerPort~TopicName~GroupName");
            return;
        }
        receiver.consume();
    }
}
