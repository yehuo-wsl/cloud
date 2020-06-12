package cm.yehuo.mq.rabbit.product_balence.backupexchange;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class BackupConsumer {

    public static final String exchange_log = "ae";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_log, BuiltinExchangeType.FANOUT,true,false,null);
        String queue = "fetchOther";
        channel.queueDeclare(queue,false,false,false,null);

        channel.queueBind(queue, exchange_log, "tom");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body,"UTF-8"));
            }
        };
        channel.basicConsume(queue, true, consumer);
    }

}