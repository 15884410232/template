package com.sp.config.hibernate;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * 配置SessionFactory
 * @author chenqi
 * @date 2022/3/20
 */
@Slf4j
@Configuration
public class HibernateConfig {

    /**
     * entity扫描路径
     */
    @Value("${spring.jpa.hibernate.packagesToScan}")
    private String PACKAGES_TO_SCAN;

    /**
     * 方言
     */
    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String DIALECT;

    /**
     * 是否显示sql
     */
    @Value("${spring.jpa.show-sql}")
    private String SHOW_SQL;

    /**
     * 是否格式化sql
     */
    @Value("${spring.jpa.format-sql}")
    private boolean FORMAT_SQL;

    /**
     * 是否根据entity自动生成表结构
     */
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String HBM2DDL_AUTO;

    /**
     * session交由spring管理
     */
    @Value("${spring.jpa.properties.hibernate.current_session_context_class}")
    private String current_session_context_class;

    /**
     * SessionFactory配置,因为shiro中有同名的sessionFactory的bean对象，所以此方法就返回实际类对象。否则要报同名冲突
     * @param dataSources
     * @return
     */
    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean(DataSource dataSources) {
        DruidDataSource dataSource=(DruidDataSource)dataSources;
        log.info("DruidDataSource:{}",dataSource.getStatDataForMBean());
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        //包扫描路径可以不配置
        sessionFactory.setPackagesToScan(PACKAGES_TO_SCAN);
        sessionFactory.setHibernateProperties(hibernateProperties());
        try {
            sessionFactory.afterPropertiesSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

    /**
     * 获取hibernate配置
     * @return
     */
    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        //数据库方言
        hibernateProperties.put("hibernate.dialect", DIALECT);
        //是否打印sql:将所有 SQL 语句写入控制台。org.hibernate.SQL这是将日志类别设置为调试的替代方法。
        hibernateProperties.put("hibernate.show_sql", SHOW_SQL);
        //打印sql是否格式化打印:在日志和控制台中漂亮地打印 SQL。
        hibernateProperties.put("hibernate.format_sql", FORMAT_SQL);
        //是否根据实体类自动生成表
        // none 不会执行任何操作。** create-only* 将生成数据库创建。** drop* 将生成数据库删除。** create* 将生成数据库删除，
        // 然后创建数据库。** create-drop* 删除模式并在 SessionFactory 启动时重新创建它。此外，在 SessionFactory 关闭时删除模式。
        // ** validate* 验证数据库架构。** update* 更新数据库架构。
        hibernateProperties.put("hibernate.hbm2ddl.auto", HBM2DDL_AUTO);
        //高亮:使用 ANSI 转义码为控制台中的 SQL 着色。
        hibernateProperties.put("hibernate.highlight_sql", true);
        //如果为 true，Hibernate 会在 SQL 中生成注释，以便于调试。
        hibernateProperties.put("hibernate.use_sql_comments", true);

        //使 Hibernate 收集统计信息以进行性能调整 hibernateProperties.put("hibernate.generate_statistics", true);
        //如果为 true，Hibernate 会在 SQL 中生成注释，以便于调试。
        hibernateProperties.put("hibernate.use_sql_comments", true);

        hibernateProperties.put("hibernate.current_session_context_class",
                current_session_context_class);
        return hibernateProperties;
    }
}
