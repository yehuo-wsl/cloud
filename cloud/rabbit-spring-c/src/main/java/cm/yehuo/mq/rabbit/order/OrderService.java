package cm.yehuo.mq.rabbit.order;

import cm.yehuo.mq.rabbit.RabbitConst;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月12日
 */
@Service
public class OrderService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sent(String message){
        rabbitTemplate.convertAndSend(RabbitConst.EX_ORDER_A, RabbitConst.ROUTE_ORDER_A, message);
    }
    public void sent(String message,long delayelayTime){
        rabbitTemplate.convertAndSend(RabbitConst.EX_ORDER_A, RabbitConst.ROUTE_ORDER_B, message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(delayelayTime+"");
                return message;
            }
        });
    }

}