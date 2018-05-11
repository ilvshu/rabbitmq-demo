package com.lvshu.rabbitMq.demo.fanout;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.Basic.Consume;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * fanout 交换器 --注册消息消费者
 * @author xu
 *
 */
public class FanoutRegRecv {
	private static String host = "192.168.220.128";
	//注册交换器
	private static String exchange = "reg_exchange";
	
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(exchange, BuiltinExchangeType.FANOUT);
		String queue = channel.queueDeclare().getQueue();
		channel.queueBind(queue, exchange, "");
		//创建消费者
		Consumer consumer = new FanoutSmsConsumer(channel);
		channel.basicConsume(queue, true, consumer);
		
	}
}
