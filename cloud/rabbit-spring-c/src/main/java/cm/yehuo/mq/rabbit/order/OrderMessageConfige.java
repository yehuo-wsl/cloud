package cm.yehuo.mq.rabbit.order;

import cm.yehuo.mq.rabbit.RabbitConst;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月12日
 */
@Configuration
public class OrderMessageConfige {

    @Bean
    public Queue queueordera() {
        return new Queue(RabbitConst.QUEUE_ORDER_A);
    }
    @Bean
    public Queue queueorderb() {
        return new Queue(RabbitConst.QUEUE_ORDER_B);
    }
    @Bean
    public Queue queueorderc() {
        Map<String,Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", RabbitConst.EX_ORDER_A);
        args.put("x-dead-letter-routing-key", RabbitConst.ROUTE_ORDER_DELAY_A);
        return QueueBuilder.durable(RabbitConst.QUEUE_ORDER_DELAY_B).withArguments(args).build();
    }

    @Bean
    public DirectExchange directExchangeOrder(){
        return new DirectExchange(RabbitConst.EX_ORDER_A);
    }


    @Bean
    public Binding bindingordera() {
        return BindingBuilder.bind(queueordera()).to(directExchangeOrder()).with(RabbitConst.ROUTE_ORDER_A);
    }
    @Bean
    public Binding bindingorderb() {
        return BindingBuilder.bind(queueorderb()).to(directExchangeOrder()).with(RabbitConst.ROUTE_ORDER_DELAY_A);
    }
    @Bean
    public Binding bindingorderc() {
        return BindingBuilder.bind(queueorderc()).to(directExchangeOrder()).with(RabbitConst.ROUTE_ORDER_B);
    }




}