package com.lvshu.rabbitMq.demo.routing;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class RoutingInfoConsumer extends  DefaultConsumer {

	public RoutingInfoConsumer(Channel channel) {
		super(channel);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
			throws IOException {
		String msg = new String(body,"utf-8");
		System.out.println("recv msg : " + msg +"logLevel : "+envelope.getRoutingKey());
		System.out.println("打印日志信息" + msg);
	}

}
