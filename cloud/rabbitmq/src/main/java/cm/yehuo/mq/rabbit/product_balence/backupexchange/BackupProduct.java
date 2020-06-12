package cm.yehuo.mq.rabbit.product_balence.backupexchange;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 备用交换器处理
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class BackupProduct {

    private static final String exchange_log = "main_exchange";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        Map<String, Object> map = new HashMap<>();
        map.put("alternate-exchange", BackupConsumer.exchange_log);
        channel.exchangeDeclare(exchange_log, "direct",false,false,map);


        channel.exchangeDeclare(BackupConsumer.exchange_log, BuiltinExchangeType.FANOUT,true,false,null);
        String[] routingkeys = new String[]{"king","tom","yehuo","pkq"};
        for (int i=0;i<4;i++){
            channel.basicPublish(exchange_log, routingkeys[i%4],null,("i am "+routingkeys[i%4]).getBytes());
        }
        channel.close();
        connection.close();
    }

}