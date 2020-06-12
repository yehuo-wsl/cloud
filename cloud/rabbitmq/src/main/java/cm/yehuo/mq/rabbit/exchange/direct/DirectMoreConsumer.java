package cm.yehuo.mq.rabbit.exchange.direct;

import com.rabbitmq.client.*;
import lombok.SneakyThrows;

import java.io.IOException;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class DirectMoreConsumer implements Runnable{

    private static final String exchange_log = "direct";
    private static Channel channel = null;
    private static String queue = null;

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(exchange_log, BuiltinExchangeType.DIRECT);
        queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, exchange_log, "king");
        channel.queueBind(queue, exchange_log, "tom");
        channel.queueBind(queue, exchange_log, "yehuo");

        for (int i=0;i<3;i++){
            new Thread(new DirectMoreConsumer(channel,queue),"thread"+i).start();
        }
    }

    DirectMoreConsumer(Channel channel,String queue){
        this.channel = channel;
        this.queue = queue;
    }

    @SneakyThrows
    @Override
    public void run(){//一个信道+一个队列 --> 多个消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(consumerTag);
                System.out.println(Thread.currentThread().getName()+"::"+new String(body,"UTF-8"));
            }
        };
        System.out.println(queue);
        channel.basicConsume(queue, true, consumer);
    }

}