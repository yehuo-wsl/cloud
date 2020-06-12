package cm.yehuo.mq.rabbit.exchange.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class TOPICProduct {

    private static final String exchange_log = "topic";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_log, BuiltinExchangeType.TOPIC);

        for (int i=0;i<3;i++){
            //channel.basicPublish(exchange_log, "king.jvm.a",null,("i am message"+i).getBytes());
            //channel.basicPublish(exchange_log, "king.jvm",null,("i am message"+i).getBytes());
            channel.basicPublish(exchange_log, "a.king.jvm",null,("i am message"+i).getBytes());
        }
        channel.close();
        connection.close();
    }

}