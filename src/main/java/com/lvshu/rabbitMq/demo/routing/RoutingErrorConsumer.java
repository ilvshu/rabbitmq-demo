package com.lvshu.rabbitMq.demo.routing;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class RoutingErrorConsumer extends  DefaultConsumer {

	public RoutingErrorConsumer(Channel channel) {
		super(channel);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
			throws IOException {
		String msg = new String(body,"utf-8");
		System.out.println("recv msg : " + msg +"logLevel : "+envelope.getRoutingKey());
		System.out.println("保存错误信息到日志文件" + msg);
		System.out.println("为负责人员发送邮箱" + msg);
		System.out.println("为负责人员发送短信" + msg);
	}

}
