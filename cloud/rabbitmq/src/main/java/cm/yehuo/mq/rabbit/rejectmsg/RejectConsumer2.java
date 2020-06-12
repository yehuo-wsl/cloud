package cm.yehuo.mq.rabbit.rejectmsg;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Description: 先确认再拒绝
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class RejectConsumer2 {

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
                    System.out.println(envelope.isRedeliver());//失败后重新提交的标志
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    throw new IOException();
                }catch (Exception e) {
                    channel.basicReject(envelope.getDeliveryTag(), true);
                }
            }
        };
        channel.basicConsume(queue, false, consumer);
    }

}