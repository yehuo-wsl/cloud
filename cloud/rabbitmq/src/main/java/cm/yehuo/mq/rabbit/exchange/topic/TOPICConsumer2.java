package cm.yehuo.mq.rabbit.exchange.topic;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class TOPICConsumer2 {

    private static final String exchange_log = "topic";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_log, BuiltinExchangeType.TOPIC);
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, exchange_log, "king.#");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body,"UTF-8"));
            }
        };
        channel.basicConsume(queue, true, consumer);
    }

}