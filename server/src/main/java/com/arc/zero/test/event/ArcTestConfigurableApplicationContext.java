package com.arc.zero.test.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.*;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

/**
 * @author yechao
 * @date 2020/12/28 5:03 下午
 */
public class ArcTestConfigurableApplicationContext implements ConfigurableApplicationContext {
    @Override
    public void setId(String s) {

    }

    @Override
    public void setParent(ApplicationContext applicationContext) {

    }

    @Override
    public void setEnvironment(ConfigurableEnvironment configurableEnvironment) {

    }

    @Override
    public ConfigurableEnvironment getEnvironment() {
        return null;
    }

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor beanFactoryPostProcessor) {

    }

    @Override
    public void addApplicationListener(ApplicationListener<?> applicationListener) {

    }

    @Override
    public void addProtocolResolver(ProtocolResolver protocolResolver) {

    }

    @Override
    public void refresh() throws BeansException, IllegalStateException {

    }

    @Override
    public void registerShutdownHook() {

    }

    @Override
    public void close() {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getApplicationName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public long getStartupDate() {
        return 0;
    }

    @Override
    public ApplicationContext getParent() {
        return null;
    }

    @Override
    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
        return null;
    }

    /**
     * Return the parent bean factory, or {@code null} if there is none.
     */
    @Override
    public BeanFactory getParentBeanFactory() {
        return null;
    }

    /**
     * Return whether the local bean factory contains a bean of the given name,
     * ignoring beans defined in ancestor contexts.
     * <p>This is an alternative to {@code containsBean}, ignoring a bean
     * of the given name from an ancestor bean factory.
     *
     * @param name the name of the bean to query
     * @return whether a bean with the given name is defined in the local factory
     * @see BeanFactory#containsBean
     */
    @Override
    public boolean containsLocalBean(String name) {
        return false;
    }

    /**
     * Check if this bean factory contains a bean definition with the given name.
     * <p>Does not consider any hierarchy this factory may participate in,
     * and ignores any singleton beans that have been registered by
     * other means than bean definitions.
     *
     * @param beanName the name of the bean to look for
     * @return if this bean factory contains a bean definition with the given name
     * @see #containsBean
     */
    @Override
    public boolean containsBeanDefinition(String beanName) {
        return false;
    }

    /**
     * Return the number of beans defined in the factory.
     * <p>Does not consider any hierarchy this factory may participate in,
     * and ignores any singleton beans that have been registered by
     * other means than bean definitions.
     *
     * @return the number of beans defined in the factory
     */
    @Override
    public int getBeanDefinitionCount() {
        return 0;
    }

    /**
     * Return the names of all beans defined in this factory.
     * <p>Does not consider any hierarchy this factory may participate in,
     * and ignores any singleton beans that have been registered by
     * other means than bean definitions.
     *
     * @return the names of all beans defined in this factory,
     * or an empty array if none defined
     */
    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
    }

    /**
     * Return the names of beans matching the given type (including subclasses),
     * judging from either bean definitions or the value of {@code getObjectType}
     * in the case of FactoryBeans.
     * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
     * check nested beans which might match the specified type as well.
     * <p>Does consider objects created by FactoryBeans, which means that FactoryBeans
     * will get initialized. If the object created by the FactoryBean doesn't match,
     * the raw FactoryBean itself will be matched against the type.
     * <p>Does not consider any hierarchy this factory may participate in.
     * Use BeanFactoryUtils' {@code beanNamesForTypeIncludingAncestors}
     * to include beans in ancestor factories too.
     * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
     * by other means than bean definitions.
     * <p>This version of {@code getBeanNamesForType} matches all kinds of beans,
     * be it singletons, prototypes, or FactoryBeans. In most implementations, the
     * result will be the same as for {@code getBeanNamesForType(type, true, true)}.
     * <p>Bean names returned by this method should always return bean names <i>in the
     * order of definition</i> in the backend configuration, as far as possible.
     *
     * @param type the generically typed class or interface to match
     * @return the names of beans (or objects created by FactoryBeans) matching
     * the given object type (including subclasses), or an empty array if none
     * @see #isTypeMatch(String, ResolvableType)
     * @see FactoryBean#getObjectType
     * @see BeanFactoryUtils#beanNamesForTypeIncludingAncestors(ListableBeanFactory, ResolvableType)
     * @since 4.2
     */
    @Override
    public String[] getBeanNamesForType(ResolvableType type) {
        return new String[0];
    }

    /**
     * Return the names of beans matching the given type (including subclasses),
     * judging from either bean definitions or the value of {@code getObjectType}
     * in the case of FactoryBeans.
     * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
     * check nested beans which might match the specified type as well.
     * <p>Does consider objects created by FactoryBeans if the "allowEagerInit" flag is set,
     * which means that FactoryBeans will get initialized. If the object created by the
     * FactoryBean doesn't match, the raw FactoryBean itself will be matched against the
     * type. If "allowEagerInit" is not set, only raw FactoryBeans will be checked
     * (which doesn't require initialization of each FactoryBean).
     * <p>Does not consider any hierarchy this factory may participate in.
     * Use BeanFactoryUtils' {@code beanNamesForTypeIncludingAncestors}
     * to include beans in ancestor factories too.
     * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
     * by other means than bean definitions.
     * <p>Bean names returned by this method should always return bean names <i>in the
     * order of definition</i> in the backend configuration, as far as possible.
     *
     * @param type                 the generically typed class or interface to match
     * @param includeNonSingletons whether to include prototype or scoped beans too
     *                             or just singletons (also applies to FactoryBeans)
     * @param allowEagerInit       whether to initialize <i>lazy-init singletons</i> and
     *                             <i>objects created by FactoryBeans</i> (or by factory methods with a
     *                             "factory-bean" reference) for the type check. Note that FactoryBeans need to be
     *                             eagerly initialized to determine their type: So be aware that passing in "true"
     *                             for this flag will initialize FactoryBeans and "factory-bean" references.
     * @return the names of beans (or objects created by FactoryBeans) matching
     * the given object type (including subclasses), or an empty array if none
     * @see FactoryBean#getObjectType
     * @see BeanFactoryUtils#beanNamesForTypeIncludingAncestors(ListableBeanFactory, ResolvableType, boolean, boolean)
     * @since 5.2
     */
    @Override
    public String[] getBeanNamesForType(ResolvableType type, boolean includeNonSingletons, boolean allowEagerInit) {
        return new String[0];
    }

    /**
     * Return the names of beans matching the given type (including subclasses),
     * judging from either bean definitions or the value of {@code getObjectType}
     * in the case of FactoryBeans.
     * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
     * check nested beans which might match the specified type as well.
     * <p>Does consider objects created by FactoryBeans, which means that FactoryBeans
     * will get initialized. If the object created by the FactoryBean doesn't match,
     * the raw FactoryBean itself will be matched against the type.
     * <p>Does not consider any hierarchy this factory may participate in.
     * Use BeanFactoryUtils' {@code beanNamesForTypeIncludingAncestors}
     * to include beans in ancestor factories too.
     * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
     * by other means than bean definitions.
     * <p>This version of {@code getBeanNamesForType} matches all kinds of beans,
     * be it singletons, prototypes, or FactoryBeans. In most implementations, the
     * result will be the same as for {@code getBeanNamesForType(type, true, true)}.
     * <p>Bean names returned by this method should always return bean names <i>in the
     * order of definition</i> in the backend configuration, as far as possible.
     *
     * @param type the class or interface to match, or {@code null} for all bean names
     * @return the names of beans (or objects created by FactoryBeans) matching
     * the given object type (including subclasses), or an empty array if none
     * @see FactoryBean#getObjectType
     * @see BeanFactoryUtils#beanNamesForTypeIncludingAncestors(ListableBeanFactory, Class)
     */
    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return new String[0];
    }

    /**
     * Return the names of beans matching the given type (including subclasses),
     * judging from either bean definitions or the value of {@code getObjectType}
     * in the case of FactoryBeans.
     * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
     * check nested beans which might match the specified type as well.
     * <p>Does consider objects created by FactoryBeans if the "allowEagerInit" flag is set,
     * which means that FactoryBeans will get initialized. If the object created by the
     * FactoryBean doesn't match, the raw FactoryBean itself will be matched against the
     * type. If "allowEagerInit" is not set, only raw FactoryBeans will be checked
     * (which doesn't require initialization of each FactoryBean).
     * <p>Does not consider any hierarchy this factory may participate in.
     * Use BeanFactoryUtils' {@code beanNamesForTypeIncludingAncestors}
     * to include beans in ancestor factories too.
     * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
     * by other means than bean definitions.
     * <p>Bean names returned by this method should always return bean names <i>in the
     * order of definition</i> in the backend configuration, as far as possible.
     *
     * @param type                 the class or interface to match, or {@code null} for all bean names
     * @param includeNonSingletons whether to include prototype or scoped beans too
     *                             or just singletons (also applies to FactoryBeans)
     * @param allowEagerInit       whether to initialize <i>lazy-init singletons</i> and
     *                             <i>objects created by FactoryBeans</i> (or by factory methods with a
     *                             "factory-bean" reference) for the type check. Note that FactoryBeans need to be
     *                             eagerly initialized to determine their type: So be aware that passing in "true"
     *                             for this flag will initialize FactoryBeans and "factory-bean" references.
     * @return the names of beans (or objects created by FactoryBeans) matching
     * the given object type (including subclasses), or an empty array if none
     * @see FactoryBean#getObjectType
     * @see BeanFactoryUtils#beanNamesForTypeIncludingAncestors(ListableBeanFactory, Class, boolean, boolean)
     */
    @Override
    public String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
        return new String[0];
    }

    /**
     * Return the bean instances that match the given object type (including
     * subclasses), judging from either bean definitions or the value of
     * {@code getObjectType} in the case of FactoryBeans.
     * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
     * check nested beans which might match the specified type as well.
     * <p>Does consider objects created by FactoryBeans, which means that FactoryBeans
     * will get initialized. If the object created by the FactoryBean doesn't match,
     * the raw FactoryBean itself will be matched against the type.
     * <p>Does not consider any hierarchy this factory may participate in.
     * Use BeanFactoryUtils' {@code beansOfTypeIncludingAncestors}
     * to include beans in ancestor factories too.
     * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
     * by other means than bean definitions.
     * <p>This version of getBeansOfType matches all kinds of beans, be it
     * singletons, prototypes, or FactoryBeans. In most implementations, the
     * result will be the same as for {@code getBeansOfType(type, true, true)}.
     * <p>The Map returned by this method should always return bean names and
     * corresponding bean instances <i>in the order of definition</i> in the
     * backend configuration, as far as possible.
     *
     * @param type the class or interface to match, or {@code null} for all concrete beans
     * @return a Map with the matching beans, containing the bean names as
     * keys and the corresponding bean instances as values
     * @throws BeansException if a bean could not be created
     * @see FactoryBean#getObjectType
     * @see BeanFactoryUtils#beansOfTypeIncludingAncestors(ListableBeanFactory, Class)
     * @since 1.1.2
     */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return null;
    }

    /**
     * Return the bean instances that match the given object type (including
     * subclasses), judging from either bean definitions or the value of
     * {@code getObjectType} in the case of FactoryBeans.
     * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
     * check nested beans which might match the specified type as well.
     * <p>Does consider objects created by FactoryBeans if the "allowEagerInit" flag is set,
     * which means that FactoryBeans will get initialized. If the object created by the
     * FactoryBean doesn't match, the raw FactoryBean itself will be matched against the
     * type. If "allowEagerInit" is not set, only raw FactoryBeans will be checked
     * (which doesn't require initialization of each FactoryBean).
     * <p>Does not consider any hierarchy this factory may participate in.
     * Use BeanFactoryUtils' {@code beansOfTypeIncludingAncestors}
     * to include beans in ancestor factories too.
     * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
     * by other means than bean definitions.
     * <p>The Map returned by this method should always return bean names and
     * corresponding bean instances <i>in the order of definition</i> in the
     * backend configuration, as far as possible.
     *
     * @param type                 the class or interface to match, or {@code null} for all concrete beans
     * @param includeNonSingletons whether to include prototype or scoped beans too
     *                             or just singletons (also applies to FactoryBeans)
     * @param allowEagerInit       whether to initialize <i>lazy-init singletons</i> and
     *                             <i>objects created by FactoryBeans</i> (or by factory methods with a
     *                             "factory-bean" reference) for the type check. Note that FactoryBeans need to be
     *                             eagerly initialized to determine their type: So be aware that passing in "true"
     *                             for this flag will initialize FactoryBeans and "factory-bean" references.
     * @return a Map with the matching beans, containing the bean names as
     * keys and the corresponding bean instances as values
     * @throws BeansException if a bean could not be created
     * @see FactoryBean#getObjectType
     * @see BeanFactoryUtils#beansOfTypeIncludingAncestors(ListableBeanFactory, Class, boolean, boolean)
     */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
        return null;
    }

    /**
     * Find all names of beans which are annotated with the supplied {@link Annotation}
     * type, without creating corresponding bean instances yet.
     * <p>Note that this method considers objects created by FactoryBeans, which means
     * that FactoryBeans will get initialized in order to determine their object type.
     *
     * @param annotationType the type of annotation to look for
     *                       (at class, interface or factory method level of the specified bean)
     * @return the names of all matching beans
     * @see #findAnnotationOnBean
     * @since 4.0
     */
    @Override
    public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
        return new String[0];
    }

    /**
     * Find all beans which are annotated with the supplied {@link Annotation} type,
     * returning a Map of bean names with corresponding bean instances.
     * <p>Note that this method considers objects created by FactoryBeans, which means
     * that FactoryBeans will get initialized in order to determine their object type.
     *
     * @param annotationType the type of annotation to look for
     *                       (at class, interface or factory method level of the specified bean)
     * @return a Map with the matching beans, containing the bean names as
     * keys and the corresponding bean instances as values
     * @throws BeansException if a bean could not be created
     * @see #findAnnotationOnBean
     * @since 3.0
     */
    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
        return null;
    }

    /**
     * Find an {@link Annotation} of {@code annotationType} on the specified bean,
     * traversing its interfaces and super classes if no annotation can be found on
     * the given class itself, as well as checking the bean's factory method (if any).
     *
     * @param beanName       the name of the bean to look for annotations on
     * @param annotationType the type of annotation to look for
     *                       (at class, interface or factory method level of the specified bean)
     * @return the annotation of the given type if found, or {@code null} otherwise
     * @throws NoSuchBeanDefinitionException if there is no bean with the given name
     * @see #getBeanNamesForAnnotation
     * @see #getBeansWithAnnotation
     * @since 3.0
     */
    @Override
    public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) throws NoSuchBeanDefinitionException {
        return null;
    }

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>This method allows a Spring BeanFactory to be used as a replacement for the
     * Singleton or Prototype design pattern. Callers may retain references to
     * returned objects in the case of Singleton beans.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * Will ask the parent factory if the bean cannot be found in this factory instance.
     *
     * @param name the name of the bean to retrieve
     * @return an instance of the bean
     * @throws NoSuchBeanDefinitionException if there is no bean with the specified name
     * @throws BeansException                if the bean could not be obtained
     */
    @Override
    public Object getBean(String name) throws BeansException {
        return null;
    }

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>Behaves the same as {@link #getBean(String)}, but provides a measure of type
     * safety by throwing a BeanNotOfRequiredTypeException if the bean is not of the
     * required type. This means that ClassCastException can't be thrown on casting
     * the result correctly, as can happen with {@link #getBean(String)}.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * Will ask the parent factory if the bean cannot be found in this factory instance.
     *
     * @param name         the name of the bean to retrieve
     * @param requiredType type the bean must match; can be an interface or superclass
     * @return an instance of the bean
     * @throws NoSuchBeanDefinitionException  if there is no such bean definition
     * @throws BeanNotOfRequiredTypeException if the bean is not of the required type
     * @throws BeansException                 if the bean could not be created
     */
    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return null;
    }

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>Allows for specifying explicit constructor arguments / factory method arguments,
     * overriding the specified default arguments (if any) in the bean definition.
     *
     * @param name the name of the bean to retrieve
     * @param args arguments to use when creating a bean instance using explicit arguments
     *             (only applied when creating a new instance as opposed to retrieving an existing one)
     * @return an instance of the bean
     * @throws NoSuchBeanDefinitionException if there is no such bean definition
     * @throws BeanDefinitionStoreException  if arguments have been given but
     *                                       the affected bean isn't a prototype
     * @throws BeansException                if the bean could not be created
     * @since 2.5
     */
    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return null;
    }

    /**
     * Return the bean instance that uniquely matches the given object type, if any.
     * <p>This method goes into {@link ListableBeanFactory} by-type lookup territory
     * but may also be translated into a conventional by-name lookup based on the name
     * of the given type. For more extensive retrieval operations across sets of beans,
     * use {@link ListableBeanFactory} and/or {@link BeanFactoryUtils}.
     *
     * @param requiredType type the bean must match; can be an interface or superclass
     * @return an instance of the single bean matching the required type
     * @throws NoSuchBeanDefinitionException   if no bean of the given type was found
     * @throws NoUniqueBeanDefinitionException if more than one bean of the given type was found
     * @throws BeansException                  if the bean could not be created
     * @see ListableBeanFactory
     * @since 3.0
     */
    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return null;
    }

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>Allows for specifying explicit constructor arguments / factory method arguments,
     * overriding the specified default arguments (if any) in the bean definition.
     * <p>This method goes into {@link ListableBeanFactory} by-type lookup territory
     * but may also be translated into a conventional by-name lookup based on the name
     * of the given type. For more extensive retrieval operations across sets of beans,
     * use {@link ListableBeanFactory} and/or {@link BeanFactoryUtils}.
     *
     * @param requiredType type the bean must match; can be an interface or superclass
     * @param args         arguments to use when creating a bean instance using explicit arguments
     *                     (only applied when creating a new instance as opposed to retrieving an existing one)
     * @return an instance of the bean
     * @throws NoSuchBeanDefinitionException if there is no such bean definition
     * @throws BeanDefinitionStoreException  if arguments have been given but
     *                                       the affected bean isn't a prototype
     * @throws BeansException                if the bean could not be created
     * @since 4.1
     */
    @Override
    public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
        return null;
    }

    /**
     * Return a provider for the specified bean, allowing for lazy on-demand retrieval
     * of instances, including availability and uniqueness options.
     *
     * @param requiredType type the bean must match; can be an interface or superclass
     * @return a corresponding provider handle
     * @see #getBeanProvider(ResolvableType)
     * @since 5.1
     */
    @Override
    public <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType) {
        return null;
    }

    /**
     * Return a provider for the specified bean, allowing for lazy on-demand retrieval
     * of instances, including availability and uniqueness options.
     *
     * @param requiredType type the bean must match; can be a generic type declaration.
     *                     Note that collection types are not supported here, in contrast to reflective
     *                     injection points. For programmatically retrieving a list of beans matching a
     *                     specific type, specify the actual bean type as an argument here and subsequently
     *                     use {@link ObjectProvider#orderedStream()} or its lazy streaming/iteration options.
     * @return a corresponding provider handle
     * @see ObjectProvider#iterator()
     * @see ObjectProvider#stream()
     * @see ObjectProvider#orderedStream()
     * @since 5.1
     */
    @Override
    public <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType) {
        return null;
    }

    /**
     * Does this bean factory contain a bean definition or externally registered singleton
     * instance with the given name?
     * <p>If the given name is an alias, it will be translated back to the corresponding
     * canonical bean name.
     * <p>If this factory is hierarchical, will ask any parent factory if the bean cannot
     * be found in this factory instance.
     * <p>If a bean definition or singleton instance matching the given name is found,
     * this method will return {@code true} whether the named bean definition is concrete
     * or abstract, lazy or eager, in scope or not. Therefore, note that a {@code true}
     * return value from this method does not necessarily indicate that {@link #getBean}
     * will be able to obtain an instance for the same name.
     *
     * @param name the name of the bean to query
     * @return whether a bean with the given name is present
     */
    @Override
    public boolean containsBean(String name) {
        return false;
    }

    /**
     * Is this bean a shared singleton? That is, will {@link #getBean} always
     * return the same instance?
     * <p>Note: This method returning {@code false} does not clearly indicate
     * independent instances. It indicates non-singleton instances, which may correspond
     * to a scoped bean as well. Use the {@link #isPrototype} operation to explicitly
     * check for independent instances.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * Will ask the parent factory if the bean cannot be found in this factory instance.
     *
     * @param name the name of the bean to query
     * @return whether this bean corresponds to a singleton instance
     * @throws NoSuchBeanDefinitionException if there is no bean with the given name
     * @see #getBean
     * @see #isPrototype
     */
    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return false;
    }

    /**
     * Is this bean a prototype? That is, will {@link #getBean} always return
     * independent instances?
     * <p>Note: This method returning {@code false} does not clearly indicate
     * a singleton object. It indicates non-independent instances, which may correspond
     * to a scoped bean as well. Use the {@link #isSingleton} operation to explicitly
     * check for a shared singleton instance.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * Will ask the parent factory if the bean cannot be found in this factory instance.
     *
     * @param name the name of the bean to query
     * @return whether this bean will always deliver independent instances
     * @throws NoSuchBeanDefinitionException if there is no bean with the given name
     * @see #getBean
     * @see #isSingleton
     * @since 2.0.3
     */
    @Override
    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return false;
    }

    /**
     * Check whether the bean with the given name matches the specified type.
     * More specifically, check whether a {@link #getBean} call for the given name
     * would return an object that is assignable to the specified target type.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * Will ask the parent factory if the bean cannot be found in this factory instance.
     *
     * @param name        the name of the bean to query
     * @param typeToMatch the type to match against (as a {@code ResolvableType})
     * @return {@code true} if the bean type matches,
     * {@code false} if it doesn't match or cannot be determined yet
     * @throws NoSuchBeanDefinitionException if there is no bean with the given name
     * @see #getBean
     * @see #getType
     * @since 4.2
     */
    @Override
    public boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
        return false;
    }

    /**
     * Check whether the bean with the given name matches the specified type.
     * More specifically, check whether a {@link #getBean} call for the given name
     * would return an object that is assignable to the specified target type.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * Will ask the parent factory if the bean cannot be found in this factory instance.
     *
     * @param name        the name of the bean to query
     * @param typeToMatch the type to match against (as a {@code Class})
     * @return {@code true} if the bean type matches,
     * {@code false} if it doesn't match or cannot be determined yet
     * @throws NoSuchBeanDefinitionException if there is no bean with the given name
     * @see #getBean
     * @see #getType
     * @since 2.0.1
     */
    @Override
    public boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
        return false;
    }

    /**
     * Determine the type of the bean with the given name. More specifically,
     * determine the type of object that {@link #getBean} would return for the given name.
     * <p>For a {@link FactoryBean}, return the type of object that the FactoryBean creates,
     * as exposed by {@link FactoryBean#getObjectType()}. This may lead to the initialization
     * of a previously uninitialized {@code FactoryBean} (see {@link #getType(String, boolean)}).
     * <p>Translates aliases back to the corresponding canonical bean name.
     * Will ask the parent factory if the bean cannot be found in this factory instance.
     *
     * @param name the name of the bean to query
     * @return the type of the bean, or {@code null} if not determinable
     * @throws NoSuchBeanDefinitionException if there is no bean with the given name
     * @see #getBean
     * @see #isTypeMatch
     * @since 1.1.2
     */
    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return null;
    }

    /**
     * Determine the type of the bean with the given name. More specifically,
     * determine the type of object that {@link #getBean} would return for the given name.
     * <p>For a {@link FactoryBean}, return the type of object that the FactoryBean creates,
     * as exposed by {@link FactoryBean#getObjectType()}. Depending on the
     * {@code allowFactoryBeanInit} flag, this may lead to the initialization of a previously
     * uninitialized {@code FactoryBean} if no early type information is available.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * Will ask the parent factory if the bean cannot be found in this factory instance.
     *
     * @param name                 the name of the bean to query
     * @param allowFactoryBeanInit whether a {@code FactoryBean} may get initialized
     *                             just for the purpose of determining its object type
     * @return the type of the bean, or {@code null} if not determinable
     * @throws NoSuchBeanDefinitionException if there is no bean with the given name
     * @see #getBean
     * @see #isTypeMatch
     * @since 5.2
     */
    @Override
    public Class<?> getType(String name, boolean allowFactoryBeanInit) throws NoSuchBeanDefinitionException {
        return null;
    }

    /**
     * Return the aliases for the given bean name, if any.
     * All of those aliases point to the same bean when used in a {@link #getBean} call.
     * <p>If the given name is an alias, the corresponding original bean name
     * and other aliases (if any) will be returned, with the original bean name
     * being the first element in the array.
     * <p>Will ask the parent factory if the bean cannot be found in this factory instance.
     *
     * @param name the bean name to check for aliases
     * @return the aliases, or an empty array if none
     * @see #getBean
     */
    @Override
    public String[] getAliases(String name) {
        return new String[0];
    }

    @Override
    public void publishEvent(Object o) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public String getMessage(String s, Object[] objects, String s1, Locale locale) {
        return null;
    }

    @Override
    public String getMessage(String s, Object[] objects, Locale locale) throws NoSuchMessageException {
        return null;
    }

    @Override
    public String getMessage(MessageSourceResolvable messageSourceResolvable, Locale locale) throws NoSuchMessageException {
        return null;
    }

    @Override
    public Resource[] getResources(String s) throws IOException {
        return new Resource[0];
    }

    @Override
    public Resource getResource(String s) {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }
}
