package com.lvshu.rabbitMq.demo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SimpleSendDemo {
	private static String host = "192.168.220.128";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare("test", false, false, false, null);
		String msg = "江湖vs浆糊";
		channel.basicPublish("", "test", null, msg.getBytes());
		System.out.println("send msg :" + msg);
		channel.close();
		connection.close();
	}

}
