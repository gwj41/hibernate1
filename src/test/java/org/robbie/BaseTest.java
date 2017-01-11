package org.robbie;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;

/**
 * Created by guxm on 2017/1/10.
 */
public class BaseTest {
    private Configuration configuration;
    private ServiceRegistry serviceRegistry;
    private Session session;
    private SessionFactoryBuilder sessionFactoryBuilder;
    private SessionFactory sessionFactory;
    private Transaction transaction;
    @Before
    public void init() {
        configuration = new Configuration().configure();

        serviceRegistry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            Metadata metadata = new MetadataSources( serviceRegistry )
//                    .addResource( "StudentEntity.hbm.xml" )
//                    .addAnnotatedClass( StudentEntity.class )
//                    .addAnnotatedClassName( "org.robbie.StudentEntity" )
                    .buildMetadata();
//                    .getMetadataBuilder()
//                    .applyImplicitNamingStrategy( ImplicitNamingStrategyJpaCompliantImpl.INSTANCE )
//                    .build();
            sessionFactoryBuilder = metadata.getSessionFactoryBuilder();
//            configuration.buildSessionFactory(serviceRegistry);
            sessionFactory = sessionFactoryBuilder.build();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( serviceRegistry );
        }
//        configuration = new Configuration().configure();
//        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void destroy() {
        if (transaction != null)
        transaction.commit();
        if (session != null)
        session.close();
        if (sessionFactory != null)
        sessionFactory.close();
    }

    public Session getSession() {
        return session;
    }
}
