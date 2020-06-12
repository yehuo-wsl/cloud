package cm.yehuo.mq.rabbit.rejectmsg;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Description: basicReject和basicNack两种拒绝方式，basicNack支持批量的拒绝
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class RejectConsumer3 {

    private static final String exchange_log = "reject_ex";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_log, BuiltinExchangeType.DIRECT);
        String queue = "error";
        channel.queueDeclare(queue,false,false,false,null);
        channel.queueBind(queue, exchange_log, "king");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    System.out.println(new String(body,"UTF-8"));
                    //channel.basicAck(envelope.getDeliveryTag(), false);
                    throw new IOException();
                }catch (Exception e) {
                    channel.basicPublish(exchange_log,"king",null,body);
                    //channel.basicReject(envelope.getDeliveryTag(), true);
                    channel.basicNack(envelope.getDeliveryTag(),false, true);
                }
            }
        };
        channel.basicConsume(queue, false, consumer);
    }

}