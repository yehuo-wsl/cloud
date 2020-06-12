package cm.yehuo.mq.rabbit;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月10日
 */
@RestController
public class ProducerController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/test")
    public void test(){
        rabbitTemplate.convertAndSend(RabbitConst.EXCHANG_EDIRECT_A, RabbitConst.ROUTEKEY_KING, "我来了direct_king");
        rabbitTemplate.convertAndSend(RabbitConst.EXCHANG_EDIRECT_A, RabbitConst.ROUTEKEY_LIN, "我来了direct_lin", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("6000");
                //message.getMessageProperties().setDelay(8000);
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                return message;
            }
        });

        //rabbitTemplate.convertAndSend(RabbitConst.EXCHANG_ETOPIC_A, "cele.key", "我来了topic_cele.key");
        //rabbitTemplate.convertAndSend(RabbitConst.EXCHANG_ETOPIC_A, "lin.false.key", "我来了topic_lin.false.key");
        //rabbitTemplate.convertAndSend(RabbitConst.EXCHANG_ETOPIC_A, "cele.king", "我来了topic_cele.king");
    }

}