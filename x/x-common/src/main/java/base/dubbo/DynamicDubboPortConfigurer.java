package base.dubbo;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import com.alibaba.dubbo.container.Container;
import com.alibaba.dubbo.container.Main;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.config.ProtocolConfig;

/**
 * 在dubbo加载配置文件时,会实例化该类,执行init-method配置的init方法 此时通过spring获取所有ProtocolConfig的实体(实际上好像就一个),并将其端口设为随机一个未被使用的端口 源码中随机端口通过new Socket实现,而后将Socket关闭,更多细节不再说
 */
@Component
public class DynamicDubboPortConfigurer implements ApplicationContextAware {

    @Autowired
    private ApplicationContext applicationContext;
    private int newPort = 20880;

    @PostConstruct
    public void init() {
        /*
        Map<String, ProtocolConfig> beansOfType = applicationContext.getBeansOfType(ProtocolConfig.class);
        for (Entry<String, ProtocolConfig> item : beansOfType.entrySet()) {
            int oldPort = item.getValue().getPort();
            newPort = NetUtils.getAvailablePort();
            item.getValue().setPort(newPort);
            System.err.println("######## dubbo oldPort=" + oldPort + ", newPort=" + newPort + ", name=" + item.getValue().getName());
        }
        */


        /*
        Map<String, ProtocolConfig> beansOfType = applicationContext.getBeansOfType(ProtocolConfig.class);
        for (Entry<String, ProtocolConfig> item : beansOfType.entrySet()) {
            int oldPort = item.getValue().getPort();
            newPort = NetUtils.getAvailablePort();
            item.getValue().setPort(newPort);
            System.err.println("######## dubbo oldPort=" + oldPort + ", newPort=" + newPort + ", name=" + item.getValue().getName());


            Container container = (Container)i$.next();

            try {
                container.stop();
                Main.logger.info("Dubbo " + container.getClass().getSimpleName() + " stopped!");
            } catch (Throwable var6) {
                Main.logger.error(var6.getMessage(), var6);
            }

            Class t = Main.class;
            synchronized(Main.class) {
                Main.running = false;
                Main.class.notify();
            }
        }
        */
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}