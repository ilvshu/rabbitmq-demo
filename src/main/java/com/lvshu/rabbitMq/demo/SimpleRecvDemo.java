package com.lvshu.rabbitMq.demo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class SimpleRecvDemo {
	private static String host = "192.168.220.128";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare("test", false, false, false, null);
		Consumer consum = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body,"utf-8");
				System.out.println("recv msg:" + msg);
				//手动控制提交
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		//取消自动提交
		boolean autoAck = false;
		channel.basicConsume("test", autoAck, consum);
	}
}	
