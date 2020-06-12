package cm.yehuo.mq.rabbit.product_balence.mandtory;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Description: 发送端返回失败消息，消费端只有king这个路由键，生产者端发送的其它路由建信息失败
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class MandtoryProduct {

    private static final String exchange_log = "mandtory";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_log, BuiltinExchangeType.DIRECT);
        String[] routingkeys = new String[]{"king","tom","yehuo","pkq"};
        channel.addReturnListener(new ReturnListener() {//添加失败通知监听器
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("返回replyCode："+replyCode);
                System.out.println("返回replyText："+replyText);
                System.out.println("返回exchange："+exchange);
                System.out.println("返回routingKey："+routingKey);
                System.out.println("返回body："+new String(body));
                System.out.println("----------------------");
            }
        });
        for (int i=0;i<4;i++){
            channel.basicPublish(exchange_log, routingkeys[i%4],true,null,("i am "+routingkeys[i%4]).getBytes());
            Thread.sleep(200);
        }
        channel.close();
        connection.close();
    }

}