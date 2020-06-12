package cm.yehuo.mq.rabbit.dlx;
import	java.util.HashMap;

import cm.yehuo.mq.rabbit.dlx.xiangxue.DlxProcessConsumer;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Map;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class DlxConsumer {

    private static final String exchange_log = "dlx_ex";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        Map<String, Object> map = new HashMap<>();
        map.put( "x-dead-letter-exchange", CommonConsumer.exchange_log);
        //TODO 死信路由键，会替换消息原来的路由键
        //map.put("x-dead-letter-routing-key", "deal");
        channel.exchangeDeclare(exchange_log, BuiltinExchangeType.DIRECT);
        String queue = "dlxqueue";
        channel.queueDeclare(queue,false,false,false,map);
        channel.queueBind(queue, exchange_log, "king");
        channel.queueBind(queue, exchange_log, "tom");
        channel.queueBind(queue, exchange_log, "yehuo");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if (envelope.getRoutingKey().equals("king")){
                    System.out.println("receive::"+new String(body,"UTF-8"));
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }else {
                    System.out.println("reject::"+new String(body,"UTF-8"));
                    channel.basicReject(envelope.getDeliveryTag(), false);
                }
            }
        };
        channel.basicConsume(queue, false, consumer);
    }

}