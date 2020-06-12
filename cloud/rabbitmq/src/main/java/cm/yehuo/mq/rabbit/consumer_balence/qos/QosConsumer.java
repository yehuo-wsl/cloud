package cm.yehuo.mq.rabbit.consumer_balence.qos;

import com.rabbitmq.client.*;
import lombok.SneakyThrows;

import java.io.IOException;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class QosConsumer {

    public static final String exchange_log = "qos_ex";
    private static int i = 1;
    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_log, BuiltinExchangeType.DIRECT);
        String queue = "erorsor";
        channel.queueDeclare("erorsor",true,false,false,null);
        channel.queueBind(queue, exchange_log, "tom");

        Consumer consumer = new DefaultConsumer(channel) {
            @SneakyThrows
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body,"UTF-8"));
            }
        };
        channel.basicQos(50,true);
        //channel.basicConsume(queue, false, consumer);
        BatchConsumer batchConsumer = new BatchConsumer(channel);
        channel.basicConsume(queue,false,batchConsumer);
    }

}