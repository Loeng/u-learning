package com.ky.ulearning.monitor.common.rocketmq;

import com.ky.ulearning.common.core.constant.CommonConstant;
import com.ky.ulearning.common.core.rocketmq.AbstractRocketConsumer;
import com.ky.ulearning.common.core.utils.JsonUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.monitor.service.LogService;
import com.ky.ulearning.spi.monitor.entity.LogEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志消费者
 *
 * @author luyuhao
 * @since 2020/3/26 10:41
 */
@Slf4j
@Component
public class LogConsumerMQ extends AbstractRocketConsumer {

    @Autowired
    private LogService logService;

    public LogConsumerMQ() {
        super();
        //一次最多接收5条
        this.setConsumeMessageBatchMaxSize(5);
    }

    @Override
    public void init() {
        // 设置主题,标签与消费者标题
        super.necessary(CommonConstant.ROCKET_LOG_MONITOR_TOPIC, "*", "日志消费者");
        //消费者具体执行逻辑
        registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                try {
                    List<LogEntity> logEntityList = new ArrayList<>();
                    for (MessageExt msg : msgs) {
                        String json = new String(msg.getBody(), CommonConstant.CHARSET);
                        LogEntity logEntity = JsonUtil.parseObject(json, LogEntity.class);
                        //判断是否为有效日志
                        if (StringUtil.isNotEmpty(logEntity.getLogUsername())
                                || StringUtil.isNotEmpty(logEntity.getLogIp())) {
                            logEntityList.add(logEntity);
                        }
                        log.info("接受来自 {} 的日志信息并处理，队列偏移 {}", msg.getTags(), msg.getQueueOffset());
                    }
                    if (!CollectionUtils.isEmpty(logEntityList)) {
                        logService.batchInsert(logEntityList);
                    }
                    log.info("完成日志消息处理, 消息长度 {}", msgs.size());
                } catch (Exception e) {
                    //日志处理异常不处理，打日志，防止死信和消息堆积
                    log.error(e.getMessage(), e);
                }
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
    }
}
