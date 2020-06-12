package cm.yehuo.mq.rabbit.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月10日
 */
//@Component
//@RabbitListener(queues = "hello")
public class ConsumerListener implements ChannelAwareMessageListener {

    /*@RabbitHandler
    public void process(String hello){
        System.out.println("receive "+hello);
    }*/

    /**
     * Callback for processing a received Rabbit message.
     * <p>Implementors are supposed to process the given Message,
     * typically sending reply messages through the given Session.
     *
     * @param message the received AMQP message (never <code>null</code>)
     * @param channel the underlying Rabbit Channel (never <code>null</code>)
     * @throws Exception Any.
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

    }
}