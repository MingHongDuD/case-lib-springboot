package com.damon.config;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
/**
 * admin配置类
 *
 * @author damon du/minghongdud
 */
@Component
public class CustomNotifierConfig extends AbstractStatusChangeNotifier {
    public CustomNotifierConfig(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent) {
                System.out.println("实例名称：" + instance.getRegistration().getName());
                System.out.println("实例服务地址：" + instance.getRegistration().getServiceUrl());
                String status = ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus();
                switch (status) {
                    case "DOWN":
                        System.out.println("健康检查没通过！");
                        break;
                    case "OFFLINE":
                        System.out.println("服务离线");
                        break;
                    case "UP":
                        System.out.println("服务上线！");
                        break;
                    case "UNKNOWN":
                        System.out.println("服务未知异常！");
                        break;
                    default:
                        System.out.println(status);
                        break;
                }
            }
        });
    }
}
