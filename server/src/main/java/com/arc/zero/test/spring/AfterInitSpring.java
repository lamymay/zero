package com.arc.zero.test.spring;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * spring bean容器加载后执行初始化处理@PostConstruct
 *
 * @author yechao
 * @since 2020/8/6 11:09 上午
 */
@Component
public class AfterInitSpring {

    private AfterInitSpring() {
    }

    public static List<Object> configs = new ArrayList<>(16);

    static class SingletonHolder {
        static List<Object> instance = new ArrayList<>(16);
    }

    public static List<Object> getInstance() {
        return SingletonHolder.instance;
    }


    /**
     * 测试
     */
    @PostConstruct
    private void init() {
        //configs = (List<Object>) merchantRepository.findAll();
        configs.add(1L);
        configs.add(2L);
        configs.add(3L);
    }


}
//我们既然使用了spring的初始化处理，那么就得让它发现不是？所以@Component是少不了的。Spring的Bean生命周期简单来说即：创建Bean->Bean的属性注入->Bean初始化->Bean销毁。我们结合上面的Bean来说，MerchantList这个bean先是被Spring容器创建，当然这里也会去创建MerchantRepository这个bean，容器统一管理所有的bean。接着MerchantRepository这个bean被注入到MerchantList这个bean，接着spring发现MerchantList有个初始化注解@PostConstruct就去执行了init方法。
//@PostConstruct在Spring的CommonAnnotationBeanPostProcessor类中接受处理：

//public CommonAnnotationBeanPostProcessor() {
//        this.setOrder(2147483644);
//        this.setInitAnnotationType(PostConstruct.class);
//        this.setDestroyAnnotationType(PreDestroy.class);
//        this.ignoreResourceType("javax.xml.ws.WebServiceContext");
//        }
