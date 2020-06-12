package cm.yehuo.mq.rabbit.exchange.direct;

import com.rabbitmq.client.*;
import lombok.SneakyThrows;

import java.io.IOException;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class DirectMoreQueueConsumer implements Runnable{

    private static final String exchange_log = "direct";

    public static void main(String[] args) throws Exception {
        new Thread(new DirectMoreQueueConsumer()).start();
    }

    @SneakyThrows
    @Override
    public void run(){//一个信道+多个队列（不同的队列+同一个routekey都可以收到，同一个队列+不同routekey也都可以收到routekey的消息）
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_log, BuiltinExchangeType.DIRECT);
        String queue = channel.queueDeclare().getQueue();
        String queue2 = channel.queueDeclare().getQueue();
        String queue3 = channel.queueDeclare().getQueue();
        channel.queueBind(queue, exchange_log, "king");
        channel.queueBind(queue, exchange_log, "tom");
        channel.queueBind(queue2, exchange_log, "tom");
        channel.queueBind(queue3, exchange_log, "yehuo");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body,"UTF-8"));
            }
        };
        System.out.println(queue);
        System.out.println(queue2);
        System.out.println(queue3);
        channel.basicConsume(queue, true, consumer);
        channel.basicConsume(queue2, true, consumer);
        channel.basicConsume(queue3, true, consumer);
    }

}