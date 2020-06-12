package cm.yehuo.mq.rabbit.listener;

import cm.yehuo.mq.rabbit.RabbitConst;
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
@Component
@RabbitListener(queues = RabbitConst.QUEUE_DLX_A)
public class DlxConsumerListener {

    //如果有异常抛出会导致死循环
    @RabbitHandler
    public void process(String hello){
        System.out.println("receive "+hello);
        //throw new RuntimeException();
    }

}