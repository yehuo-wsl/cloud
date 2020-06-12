package cm.yehuo.mq.rabbit.order;

import cm.yehuo.mq.rabbit.RabbitConst;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月12日
 */
@Component
public class OrderMessageReceive {

    @RabbitListener(queues = RabbitConst.QUEUE_ORDER_A)
    @RabbitHandler
    public void process(String message){
        System.out.println(message);
    }

    //经死信队列转发到此队列后触发
    @RabbitListener(queues = RabbitConst.QUEUE_ORDER_B)
    @RabbitHandler
    public void processDelay(String message){
        System.out.println(message);
    }

}