/**
 * 1、死信队列
 *      进入死信队列三种方式：
 *          消息过期
 *          超出队列长度限制（前提是设置了队列的长度，要不然队列会很长的）或超出队列大小限制
 *          拒绝消息
 *
 * 2、死信队列关注的是队列（在消费端设置），备用交换器关注的是交换器（在发送端设置）（所以在声明时是不同的）
 *  channel.queueDeclare(queue,false,false,false,map);//声明队列
 *      第一个false: 是否持久化
 *      第二个false：是否排他，如果是true，表示其它连接不可创建同名队列且在连接关闭后会自动删除（这种队列适合于一个客户端同时发送和就收消息的场景）
 *      第三个false：当最后一个消费者断开后会自动删除此队列
 *  channel.exchangeDeclare(exchange_log, "direct",false,false,map);//声明交换器
 *  channel.exchangeDeclare(String exchange,String type,boolean durable,boolean autoDelete,boolean internal,Map<String, Object> arguments)
 *      durable：是否持久化
 *      autoDelete：是否自动删除，前提是有队列或交换器与他绑定后都解绑了才会自动删除
 *      internal：是否内置，如果为true只能通过交换器内部转发接收信息，无法通过外部发送消息
 * 3、AMQP.BasicProperties 里面的属性可以设置后->传递消息中使用  可以借此实现请求-响应模式
 *
 * 4、topic模式下 *和#的区别是  *只代表一个.分割的字符串，#可以代表.分割的多个字符串
 * 5、拒绝消息有两种方式：一种是basicReject一种是basicNack 区别是basicNack可以一次拒绝多个消息，两个方法都可以重新投递
 * 6、发送端性能与安全的平衡：
 *      发送端通过mandtory返回通知看是否到达rabbitmq
 *      发送端可以通过单条确认模式看是否到达rabbitmq
 *      发送端可以通过多条后确认（批量确认）是否到达rabbitmq
 *      发送端可以通过异步方式（加监听器）确认是否到达rabbitmq
 *      发送端可以通过事务的开启-提交-回滚确认消息到达rabbitmq
 *      发送端可以通过备用交换器来实现找不到路由键与队列绑定的关系时会统一放到备用交换器，确保能到达rabbitmq
 * 7、消息端平衡
 *      消费端通过ack启动应答或手动应答
 *      拉取的方式进行ack应答
 *      使用qos预取+自定义的消费者实现限流（针对未应答的数量如果大于qos，只会先取qos设置的量）+批量的ack应答
 *
 * 8、channel.queueDeclare(queue,false,false,false,map);//声明队列
 *    channel.exchangeDeclare(exchange_log, "direct",false,false,map);//声明交换器
 *
 * 9、延时队列
 *      正常消息发送到死信队列（无消费者的，然后延时几秒）发送到死信队列关联的map中的队列
 *         A --------> B
 *
 *
 * 10、消息丢失、顺序消息、消息重复？
 *      消息丢失原因：
 *          分为发送方丢失、mq丢失、消费者丢失
 *          发送方丢失：发送发可以使用返回失败通知(失败通知+异步通知)+确认(单+批量)的方式+事务(降低系统的吞吐量)
 *          mq丢失：设置消息+队列+交换器持久化+发送方的确认可以确保mq不丢失数据
 *          消费方丢失：不用自动确认改为手动确认
 *      消息重复原因：
 *          消费者消费后挂掉没给mq响应
 *          消费者消费后确认没到达mq，ma挂掉
 *          解决：业务方处理消息幂等，使用消息id+时间戳标识过滤，如果是写库可以使用唯一索引控制不能插入第二次，使用redis的原子操作控制
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */