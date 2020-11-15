//package com.arc.zero.service.system.impl;
//
//import com.arc.core.model.domain.system.SysLog;
//import com.arc.zero.config.cache.RedisCachingConfiguration;
//import com.arc.zero.mapper.system.SysLogMapper;
//import com.arc.zero.service.system.SysLogService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.CachePut;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.cache.annotation.Caching;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * @author yechao
// * @date 2018/11/10 19:01
// */
//@Slf4j
//@Service("sysLogServiceImpl2")
//public class SysLogServiceImpl2 implements SysLogService {
//    @Resource
//    private SysLogMapper logMapper;
//
//
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
////    @CacheEvict(keyGenerator = RedisCachingConfiguration.KEY_GENERATOR_NAME,cacheNames = RedisCachingConfiguration.CacheNames.CACHE_SYSTEM)
//
//    @Caching(
//            evict = {
////                    @CacheEvict(cacheNames = RedisCachingConfiguration.CacheNames.CACHE_SYSTEM, key = "'SYSTEM:RealmServiceImpl:list'"),
////                    @CacheEvict(cacheNames = RedisCachingConfiguration.CacheNames.CACHE_SYSTEM, key = "'csp-ms-system:RealmServiceImpl:treeRealm'")
////                    @CacheEvict(value = "SYSTEM", allEntries = true)
////                    @CacheEvict(value = "value_sys", allEntries = true, key = "'#id'")
////                    @CacheEvict(value = "value_sys",  key = "'#id'")
//                    @CacheEvict(value = "value_sys",  key = "'#id'")
//
//            })
//    public Integer delete(Long id) {
//        return logMapper.delete(id);
//    }
//
//
//
//
//
//
//    @Override
//    @Transactional(readOnly = true)
////    @Cacheable(value = RedisCachingConfiguration.CacheNames.CACHE_SYSTEM, sync = true)
////    @Cacheable(keyGenerator = RedisCachingConfiguration.KEY_GENERATOR_NAME)
//    @Cacheable(value = "value_sys",key="#id")
//    public SysLog get(Long id) {
//        return logMapper.get(id);
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    @CachePut(value = RedisCachingConfiguration.CacheNames.CACHE_SYSTEM)
//    public Integer update(SysLog sysLog) {
//        return logMapper.update(sysLog);
//    }
//
//
//    //@CacheEvict(value="缓存区域")
//    @Override
//    @Transactional(readOnly = true)
//    @Cacheable(value = RedisCachingConfiguration.CacheNames.CACHE_SYSTEM, sync = true)
//    public List<SysLog> page() {
//        return logMapper.list();
//    }
//
//
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
//    public Long save(SysLog sysLog) {
//        return logMapper.save(sysLog) == 1 ? sysLog.getId() : null;
//    }
//
//}
