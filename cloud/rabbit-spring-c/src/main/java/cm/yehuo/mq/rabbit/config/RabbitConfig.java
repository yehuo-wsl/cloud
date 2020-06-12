package cm.yehuo.mq.rabbit.config;

import cm.yehuo.mq.rabbit.RabbitConst;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月10日
 */
@Configuration
public class RabbitConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory("127.0.0.1",5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setChannelCacheSize(8);
        factory.setPublisherConfirms(true);
        factory.setVirtualHost("/");
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    //注册direct的队列
    @Bean
    public Queue createDirectQueueA() {
        return new Queue(RabbitConst.QUEUE_DIRECT_A);
    }

    @Bean
    public Queue createDirectQueueB() {
        return new Queue(RabbitConst.QUEUE_DIRECT_B);
    }

    @Bean
    public Queue createDirectQueueC() {
        Map<String,Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", RabbitConst.EXCHANGE_DLX_A);
        args.put("x-dead-letter-routing-key", RabbitConst.ROUTE_DLX_A);
        //args.put("x-message-ttl", 5000);
        //args.put("x-expires", 5000);
        return QueueBuilder.durable(RabbitConst.QUEUE_DIRECT_C).withArguments(args).build();
    }

    //注册topic队列
    @Bean
    public Queue createTopicQueueA() {
        return new Queue(RabbitConst.QUEUE_TOPIC_A);
    }

    @Bean
    public Queue createTopicQueueB() {
        return new Queue(RabbitConst.QUEUE_TOPIC_B);
    }

    @Bean
    public Queue createTopicQueueC() {
        return new Queue(RabbitConst.QUEUE_TOPIC_C);
    }


    //注册死信交换器
    @Bean
    public DirectExchange directExchangeDlx(){
        return new DirectExchange(RabbitConst.EXCHANGE_DLX_A);
    }

    //注册绑定关系
    @Bean
    public Binding bindingg(){
        return BindingBuilder.bind(createDlxQueueA()).to(directExchangeDlx()).with(RabbitConst.ROUTE_DLX_A);
    }

    //注册死信队列
    @Bean
    public Queue createDlxQueueA(){

        return QueueBuilder.durable(RabbitConst.QUEUE_DLX_A).build();
    }


    //注册direct交换器
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitConst.EXCHANG_EDIRECT_A, false, false);
    }

    //注册topic交换器
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitConst.EXCHANG_ETOPIC_A, false, false);
    }

    //绑定direct
    @Bean
    public Binding bindinga(){
        return BindingBuilder.bind(createDirectQueueA()).to(directExchange()).with(RabbitConst.ROUTEKEY_KING);
    }
    @Bean
    public Binding bindingb(){
        return BindingBuilder.bind(createDirectQueueB()).to(directExchange()).with(RabbitConst.ROUTEKEY_KING);
    }
    @Bean
    public Binding bindingc(){
        return BindingBuilder.bind(createDirectQueueC()).to(directExchange()).with(RabbitConst.ROUTEKEY_LIN);
    }

    //绑定topic
    @Bean
    public Binding bindingd(){
        return BindingBuilder.bind(createTopicQueueA()).to(topicExchange()).with(RabbitConst.ROUTEKEY_TOPIC_KING);
    }
    @Bean
    public Binding bindinge(){
        return BindingBuilder.bind(createTopicQueueB()).to(topicExchange()).with(RabbitConst.ROUTEKEY_TOPIC_LIN);
    }
    @Bean
    public Binding bindingf(){
        return BindingBuilder.bind(createTopicQueueC()).to(topicExchange()).with(RabbitConst.ROUTEKEY_TOPIC_CELE);
    }

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(confirmCallback());
        rabbitTemplate.setReturnCallback(returnCallback());
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }

    @Bean
    public RabbitTemplate.ConfirmCallback confirmCallback(){
        return new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack){
                    System.out.println("发送到rabbitmq成功");
                }else {
                    System.out.println("发送到rabbitmq失败");
                }
            }
        };
    }

    @Bean
    public RabbitTemplate.ReturnCallback returnCallback(){
        return new RabbitTemplate.ReturnCallback(){
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("失败返回的message: "+ new String(message.getBody()));
                System.out.println("失败返回的replyCode: "+replyCode);
                System.out.println("失败返回的replyText: "+replyText);
                System.out.println("失败返回的exchange: "+exchange);
                System.out.println("失败返回的routingKey: "+routingKey);
            }
        };
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        List<Queue> objects = new ArrayList<>();
        objects.add(createDirectQueueA());
        objects.add(createDirectQueueB());
        //objects.add(createDirectQueueC());
        objects.add(createTopicQueueA());
        objects.add(createTopicQueueB());
        objects.add(createTopicQueueC());
        container.setQueues(objects.toArray(new Queue[0]));
        container.setMessageListener(channelAwareMessageListener());
        return container;
    }

    @Bean
    public ChannelAwareMessageListener channelAwareMessageListener(){
        return new ChannelAwareMessageListener(){
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                String msg = new String(message.getBody());
                try {
                    System.out.println("成功接收到消息: "+msg);
                    if (message.getMessageProperties().getReceivedRoutingKey().equals(RabbitConst.ROUTEKEY_KING)){

                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    }else {
                        //System.out.println("拒绝接收消息: "+msg);
                        //channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                    }
                    //if (message.getMessageProperties().getCorrelationId()!=null){
                    //    channel.basicPublish("", message.getMessageProperties().getReceivedRoutingKey(), null, "我发给你了，你接收一下".getBytes());
                    //}
                }catch (Exception e){
                    System.out.println("拒绝接收此消息: "+msg+" 请求重发！");
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                }
            }
        };
    }


}