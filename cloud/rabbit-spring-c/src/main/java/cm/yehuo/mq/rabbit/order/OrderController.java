package cm.yehuo.mq.rabbit.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月12日
 */
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping("/order")
    public void order(){
        orderService.sent("第一个订单");
        orderService.sent("第二个订单",6000);
    }

}