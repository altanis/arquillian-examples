package com.github.altanis.arquillian.core.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class JPAConfig {

    @Produces
    @PersistenceContext(unitName = "arquillian-pu")
    private EntityManager entityManager;

}
