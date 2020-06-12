package cm.yehuo.mq.rabbit.setmsg;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Description: 请求-响应模式
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class PropertyConsumer {

    private static final String exchange_log = "property";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_log, BuiltinExchangeType.DIRECT);
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, exchange_log, "king");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body,"UTF-8"));
                AMQP.BasicProperties pro = new AMQP.BasicProperties.Builder()
                        .replyTo(properties.getReplyTo())//私有队列
                        .correlationId(properties.getMessageId())//响应的消息id
                        .build();
                channel.basicPublish("", properties.getReplyTo(), pro, "i sent a message to reply to you".getBytes());
            }
        };
        channel.basicConsume(queue, true, consumer);
    }

}