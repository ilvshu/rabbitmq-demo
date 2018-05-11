package com.lvshu.rabbitMq.demo.fanout;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * fanout 交换器 --注册消息提供者
 * @author xu
 *
 */
public class FanoutRegSend {
	
	private static String host = "192.168.220.128";
	//交换器名称
	private static String exchange_name = "reg_exchange";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		//获取连接
		Connection connection = factory.newConnection();
		//创建信道
		Channel channel = connection.createChannel();
		//设置交换器
		channel.exchangeDeclare(exchange_name, BuiltinExchangeType.FANOUT);
		String msg = "183xxxx6170";
		//发布
		channel.basicPublish(exchange_name, "", null, msg.getBytes());
		System.out.println("send msg:"+msg);
		channel.close();
		connection.close();
	}

}
