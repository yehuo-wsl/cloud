package cm.yehuo.mq.rabbit.exchange.direct;

import com.rabbitmq.client.*;
import lombok.SneakyThrows;

import java.io.IOException;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class DirectMoreChannelConsumer implements Runnable{

    private static final String exchange_log = "direct";
    private static Connection connection =null;
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        for (int i=0;i<3;i++){
            new Thread(new DirectMoreChannelConsumer(connection)).start();
        }
    }

    public DirectMoreChannelConsumer(Connection connection){
        this.connection = connection;
    }

    @SneakyThrows
    @Override
    public void run(){//多信道时 每个信道都会收到一份
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_log, BuiltinExchangeType.DIRECT);
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, exchange_log, "king");
        channel.queueBind(queue, exchange_log, "tom");
        channel.queueBind(queue, exchange_log, "yehuo");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body,"UTF-8"));
            }
        };
        channel.basicConsume(queue, true, consumer);
    }

}