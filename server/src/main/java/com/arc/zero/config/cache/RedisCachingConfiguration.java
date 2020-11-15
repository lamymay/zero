//package com.arc.zero.config.cache;
//
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
///**
// * 配置RedisCacheManager
// *
// * @author 叶超
// * @since 2019/03/19
// */
//@Configuration
////@EnableCaching
//public class RedisCachingConfiguration extends CachingConfigurerSupport {
//
//    Logger log = LoggerFactory.getLogger(this.getClass());
//
//    /**
//     * 自定义key生成器
//     */
//    public static final String KEY_GENERATOR_NAME = "keyGenerator";
//
//    @Value("${spring.application.name:default}")
//    String applicationName;
//
//    @Value("${server.port:8080}")
//    String port;
//
//
//    @Bean
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<Object, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//
//        //使用 Jackson2JsonRedisSerializer 来序列化和反序列化redis的value值
//        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
//        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
//        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        serializer.setObjectMapper(mapper);
//
//        template.setValueSerializer(serializer);
//
//        //使用 StringRedisSerializer 来序列化和反序列化redis的key值
//        template.setKeySerializer(new StringRedisSerializer());
//        template.afterPropertiesSet();
//        return template;
//    }
//
//
//   /* @Bean("keyGenerator")
//    @Override
//    public KeyGenerator keyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object target, Method method, Object... params) {
//                StringBuilder sb = new StringBuilder();
//                //注入的项目名称做为key的一部分【项目名称：全限定类名：方法名称：全部参数】
//                sb.append(applicationName).append(":").append(port).append(":").append(target.getClass().getSimpleName()).append(":").append(method.getName());
//                if (params != null && params.length > 0) {
//                    for (Object p : params) {
//                        sb.append("@").append(p);
//                    }
//                }//SYSTEM::zero-server:80:SysLogServiceImpl2:get@6
//                log.debug("### 缓存时候的key--->{}", sb.toString());
//                return sb.toString();
//            }
//        };
//    }*/
//
//
//    public interface CacheNames {
//
//        String CACHE_SYSTEM = "SYSTEM";
//
//        String CACHE_BUSINESS = "BUSINESS";
//
//        String CACHE_CUSTOM = "CUSTOM";
//
//        String CACHE_ORDER = "ORDER";
//
//
//    }
//
//
//}
