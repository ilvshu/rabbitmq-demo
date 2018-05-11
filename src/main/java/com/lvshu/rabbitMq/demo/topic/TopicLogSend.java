package com.lvshu.rabbitMq.demo.topic;
/**
 * 通配符 交换器demo
 * @author xu
 *
 */

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.Queue;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TopicLogSend {
	private static  Connection connection = null;
	private static String host = "192.168.220.128";
	private static String exchange = "log_topic_exchange";
	
	static{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		try {
			connection =  factory.newConnection();
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		
		Scanner scan = new Scanner(System.in);
		while(true){
			Channel channel = connection.createChannel();
			channel.exchangeDeclare(exchange, BuiltinExchangeType.TOPIC);
			System.out.println("请输入文字");
			String msg = scan.next();
			String logLevel = getRouting();
			channel.basicPublish(exchange, logLevel, null, msg.getBytes());
			System.out.println(" send msg : "+msg +",logLevel :"+logLevel);
			channel.close();
		}
		
	}
	
	private static String getRouting(){
		Random random = new Random();
		int i = random.nextInt(4);
		String[] routingKey = {"error.user","warning.user","error.order","warning.order"};
		return routingKey[i];
	}
}
