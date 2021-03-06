package com.lvshu.rabbitMq.demo.fanout;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 发送站内信消费者
 * @author xu
 *
 */
public class FanoutNoticeConsumer extends DefaultConsumer{
	


	public FanoutNoticeConsumer(Channel channel) {
		super(channel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
			throws IOException {
		String msg = new String(body,"utf-8");
		System.out.println(this.getClass().getSimpleName()+"recv msg :" + msg);
		sendNotice(msg);
	}
	
	private void sendNotice(String phoneNum){
		System.out.println("注册成功，为"+phoneNum+"发送网站通知");
	}

}
