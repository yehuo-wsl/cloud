package cm.yehuo.mq.rabbit.rejectmsg;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Description: 普通生产者
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class RejectProduct {

    private static final String exchange_log = "reject_ex";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_log, BuiltinExchangeType.DIRECT);
        String routekey = "king";
        for (int i=0;i<10;i++){
            channel.basicPublish(exchange_log, routekey,null,("i am "+routekey).getBytes());
        }
        channel.close();
        connection.close();
    }

}