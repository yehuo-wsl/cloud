package cm.yehuo.mq.rabbit.setmsg;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;

/**
 * @Description: 请求-响应 模式
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class PropertyProduct {

    private static final String exchange_log = "property";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_log, BuiltinExchangeType.DIRECT,false);
        String queue = channel.queueDeclare().getQueue();
        String uuid = UUID.randomUUID().toString();
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .replyTo(queue)
                .messageId(uuid)
                .build();
        String routekey = "king";
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("receive message ["+new String(body)+"]");
            }
        };

        channel.basicConsume(queue, true, consumer);

        for (int i=0;i<3;i++){
            channel.basicPublish(exchange_log, routekey,properties,("i am "+routekey).getBytes());
        }
        //channel.close();
        //connection.close();
    }

}