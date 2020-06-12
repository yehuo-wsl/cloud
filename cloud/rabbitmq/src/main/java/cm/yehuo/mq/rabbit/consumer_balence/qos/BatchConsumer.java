package cm.yehuo.mq.rabbit.consumer_balence.qos;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月09日
 */
public class BatchConsumer extends DefaultConsumer {

    private Channel channel;
    private int count=0;

    public BatchConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    /**
     *
     * @param consumerTag
     * @param envelope
     * @param properties
     * @param body
     */
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        count++;
        String message = new String(body);
        if (count%50==0){
            channel.basicAck(envelope.getDeliveryTag(), true);
        }else if (message.equals("stop")){
            channel.basicAck(envelope.getDeliveryTag(), true);
        }
        System.out.println("receive message ["+message+"]");
    }
}