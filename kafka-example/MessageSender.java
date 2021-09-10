package com.mycompany.kafka.example;

import java.util.Date;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class MessageSender {

    Properties prop = new Properties();

    private String serverIP = "";
    private String serverPort = "";
    private String topicName = "";

    private void init() throws InterruptedException {
        prop.setProperty("bootstrap.servers", serverIP + ":" + serverPort);
        prop.setProperty("kafka.topic.name", topicName);

        KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(this.prop, new StringSerializer(), new ByteArraySerializer());

        for (int i = 0; i < 1000; ++i) {
            byte[] payload = (i + " Message from java code " + new Date()).getBytes();
            System.out.println(i + " Message from java code " + new Date());
            ProducerRecord<String, byte[]> record = new ProducerRecord<String, byte[]>(prop.getProperty("kafka.topic.name"), payload);
            producer.send(record);
            System.out.println("Record sent");
            Thread.sleep(1000);

        }
        producer.close();
    }

    public static void main(String[] args) throws InterruptedException {
        MessageSender sender = new MessageSender();
        try {
            String[] input = args[0].split("~");
            sender.serverIP = input[0];
            sender.serverPort = input[1];
            sender.topicName = input[2];
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Invalid Input!! Expected input : ServerIP~ServerPort~TopicName");
            return;
        }
        sender.init();
    }
}
