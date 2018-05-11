package com.lvshu.rabbitMq.demo.routing;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RoutingLogSend {
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
	
	public static void main(String[] args) throws IOException, TimeoutException {
		sendMsg("error","严重错误");
		sendMsg("info","日志打印");
		sendMsg("debug","日志打印");
		sendMsg("warning","警告信息");
		connection.close();
	}
	
	public static void sendMsg(String logLevel,String msg) throws IOException, TimeoutException{
		Channel channel = connection.createChannel();
		//定义交换器
		channel.exchangeDeclare(exchange, BuiltinExchangeType.DIRECT);
		channel.basicPublish(exchange, logLevel, null, msg.getBytes());
		System.out.println(" send msg : "+msg +",logLevel :"+logLevel);
		channel.close();
	}
}
