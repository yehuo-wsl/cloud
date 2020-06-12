package cm.yehuo.mq.rabbit.consumer_balence.getmessage;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Description: 主动拉取消息
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class GetMesConsumer {

    public static final String exchange_log = "get_ex";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_log, BuiltinExchangeType.DIRECT);
        String queue = "erorsor";
        channel.queueDeclare("erorsor",true,false,false,null);
        channel.queueBind(queue, exchange_log, "tom");

        while (true){
            GetResponse response = channel.basicGet(queue, false);//取消自动确认
            if (response != null){
                byte[] body = response.getBody();
                System.out.println(new String(body));
                channel.basicAck(response.getEnvelope().getDeliveryTag(), false);//手动确认
            }

            /*GetResponse response = channel.basicGet(queue, true);自动确认
            if (response != null){
                byte[] body = response.getBody();
                System.out.println(new String(body));
            }*/
            Thread.sleep(1000);
        }
    }

}