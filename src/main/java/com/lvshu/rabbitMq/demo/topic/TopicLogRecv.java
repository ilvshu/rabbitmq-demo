package com.lvshu.rabbitMq.demo.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.lvshu.rabbitMq.demo.routing.RoutingErrorConsumer;
import com.lvshu.rabbitMq.demo.routing.RoutingInfoConsumer;
import com.lvshu.rabbitMq.demo.routing.RoutingWarningConsumer;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;

public class TopicLogRecv {
	private static Connection connection = null;
	private static String host = "192.168.220.128";
	private static String exchange = "log_topic_exchange";
	static{
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(host);
			connection = factory.newConnection();
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		errorConsumer();
		userConsumer();
		warningConsumer();
	}
	
	
	public static void errorConsumer() throws Exception{
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(exchange, BuiltinExchangeType.TOPIC);
		String queue = channel.queueDeclare().getQueue();
		channel.queueBind(queue, exchange, "error.*");
		Consumer consumer = new TopicErrorConsumer(channel);
		channel.basicConsume(queue, true, consumer);
	}
	
	public static void userConsumer() throws Exception{
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(exchange, BuiltinExchangeType.TOPIC);
		String queue = channel.queueDeclare().getQueue();
		channel.queueBind(queue, exchange, "*.user");
		Consumer consumer = new TopicUserConsumer(channel);
		channel.basicConsume(queue, true, consumer);
	}
	
	public static void warningConsumer() throws Exception{
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(exchange, BuiltinExchangeType.TOPIC);
		String queue = channel.queueDeclare().getQueue();
		channel.queueBind(queue, exchange, "warning.*");
		Consumer consumer = new TopicWarningConsumer(channel);
		channel.basicConsume(queue, true, consumer);
	}
}
