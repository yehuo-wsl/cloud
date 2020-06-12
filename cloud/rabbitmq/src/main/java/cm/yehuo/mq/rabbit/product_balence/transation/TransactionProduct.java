package cm.yehuo.mq.rabbit.product_balence.transation;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Description: 发送端事务确认
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class TransactionProduct {

    private static final String exchange_log = "transaction";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_log, BuiltinExchangeType.DIRECT);
        String[] routingkeys = new String[]{"king","tom","yehuo","pkq"};
        channel.txSelect();
        try {
            for (int i=0;i<4;i++){
                channel.basicPublish(exchange_log+"ss", routingkeys[i%4],true,null,("i am "+routingkeys[i%4]).getBytes());
                Thread.sleep(200);
            }
            channel.txCommit();
        }catch (Exception e){
            channel.txRollback();
        }
        channel.close();
        connection.close();
    }

}