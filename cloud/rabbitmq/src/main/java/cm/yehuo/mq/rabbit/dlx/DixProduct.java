package cm.yehuo.mq.rabbit.dlx;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class DixProduct {

    private static final String exchange_log = "dlx_ex";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //channel.exchangeDeclare(exchange_log, BuiltinExchangeType.DIRECT);
        String[] routingkeys = new String[]{"king","tom","yehuo"};
        for (int i=0;i<3;i++){
            channel.basicPublish(exchange_log, routingkeys[i%3],null,("i am "+routingkeys[i%3]).getBytes());
        }
        channel.close();
        connection.close();
    }

}