package com.lvshu.rabbitMq.demo.routing;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;

public class RoutingLogRecv {
	private static Connection connection = null;
	private static String host = "192.168.220.128";
	private static String exchange = "log_exchange";
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
		infoConsumer();
		warningConsumer();
	}
	
	
	public static void errorConsumer() throws Exception{
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(exchange, BuiltinExchangeType.DIRECT);
		String queue = channel.queueDeclare().getQueue();
		channel.queueBind(queue, exchange, "error");
		Consumer consumer = new RoutingErrorConsumer(channel);
		channel.basicConsume(queue, true, consumer);
	}
	
	public static void infoConsumer() throws Exception{
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(exchange, BuiltinExchangeType.DIRECT);
		String queue = channel.queueDeclare().getQueue();
		channel.queueBind(queue, exchange, "info");
		channel.queueBind(queue, exchange, "debug");
		Consumer consumer = new RoutingInfoConsumer(channel);
		channel.basicConsume(queue, true, consumer);
	}
	
	public static void warningConsumer() throws Exception{
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(exchange, BuiltinExchangeType.DIRECT);
		String queue = channel.queueDeclare().getQueue();
		channel.queueBind(queue, exchange, "warning");
		Consumer consumer = new RoutingWarningConsumer(channel);
		channel.basicConsume(queue, true, consumer);
	}
}
