package cm.yehuo.mq.rabbit;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月10日
 */
public interface RabbitConst {

    String EXCHANG_EDIRECT_A = "spring-direct-a";
    String EXCHANG_EDIRECT_B = "spring-direct-b";
    String EXCHANG_EDIRECT_C = "spring-direct-c";

    String EXCHANG_EFANOUT_A = "spring-fanout-a";
    String EXCHANG_EFANOUT_B = "spring-fanout-b";
    String EXCHANG_EFANOUT_C = "spring-fanout-c";

    String EXCHANG_ETOPIC_A = "spring-topic-a";
    String EXCHANG_ETOPIC_B = "spring-topic-b";
    String EXCHANG_ETOPIC_C = "spring-topic-c";


    String QUEUE_DIRECT_A = "queuedirecta";
    String QUEUE_DIRECT_B = "queuedirectb";
    String QUEUE_DIRECT_C = "queuedirectc";

    String QUEUE_TOPIC_A = "queuetopica";
    String QUEUE_TOPIC_B = "queuetopicb";
    String QUEUE_TOPIC_C = "queuetopicc";

    String QUEUE_FANOUT_A = "queuefanouta";
    String QUEUE_FANOUT_B = "queuefanoutb";
    String QUEUE_FANOUT_C = "queuefanoutc";


    String ROUTEKEY_KING = "king";
    String ROUTEKEY_TOM = "tom";
    String ROUTEKEY_CELE = "cele";
    String ROUTEKEY_LIN = "lin";

    String ROUTEKEY_TOPIC_KING = "*.king";
    String ROUTEKEY_TOPIC_TOM = "#.tom";
    String ROUTEKEY_TOPIC_CELE = "cele.*";
    String ROUTEKEY_TOPIC_LIN = "lin.#";

    String EXCHANGE_DLX_A = "dlxexa";
    String QUEUE_DLX_A = "dlxqua";
    String ROUTE_DLX_A = "routedlxa";

    String EX_ORDER_A="exordera";
    String QUEUE_ORDER_A="queueordera";
    String QUEUE_ORDER_B="queueorderb";
    String QUEUE_ORDER_DELAY_B="queueorderdelayb";
    String ROUTE_ORDER_A = "routeorderA";
    String ROUTE_ORDER_B = "routeorderB";
    String ROUTE_ORDER_DELAY_A = "routeorderdelayA";


}