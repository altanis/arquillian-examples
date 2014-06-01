package com.github.altanis.arquillian.deployment;

import java.io.File;
import java.lang.invoke.MethodHandles;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.filter.ExcludeRegExpPaths;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArquillianDeployment {

    public static final String WEBAPP_SRC = "src/main/webapp";

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static WebArchive prepareStandardWebAppDeployment() {
        logger.info("Preparing standard deployment");
        logger.info("Current directory is: " + new File(".").getAbsolutePath());

        WebArchive webArchive = ShrinkWrap.create(WebArchive.class);

        prepareStandardClassesAndResources(webArchive);
        prepareMavenDependencies(webArchive);
        prepareWebResources(webArchive);

        return webArchive;
    }

    public static WebArchive prepareBddWebAppDeployment() {
        WebArchive webArchive = prepareStandardWebAppDeployment();
        webArchive.addAsResource("bdd/ItemsStock.feature", "bdd/ItemsStock.feature");
        return webArchive;
    }

    private static void prepareWebResources(WebArchive webArchive) {
        logger.info("Preparing Web resources");

        webArchive.setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml"));

        //addAsWebInfResource maps to WEB-INF/
        webArchive.addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/beans.xml"));

        webArchive.merge(ShrinkWrap.create(ExplodedImporter.class).importDirectory(new File(WEBAPP_SRC)).as(
                GenericArchive.class));
    }

    private static void prepareStandardClassesAndResources(WebArchive webArchive) {
        logger.info("Preparing standard classes and resources");

        webArchive.addPackages(true, new ExcludeRegExpPaths(".*Test.class$"), "com.github.altanis.arquillian");

        //addAsResource maps to WEB-INF/classes/
        webArchive.addAsResource("META-INF/persistence.xml");
    }

    private static void prepareMavenDependencies(WebArchive webArchive) {
        logger.info("Preparing Maven dependencies");

        PomEquippedResolveStage mavenResolver = Maven.resolver().loadPomFromFile(new File("pom.xml"));

        File[] runtimeLibs = mavenResolver.importRuntimeDependencies().resolve().withTransitivity().asFile();

        File[] manuallyAddedLibs = mavenResolver.resolve("org.assertj:assertj-core:1.6.0",
                "com.googlecode.catch-exception:catch-exception:1.2.0").withTransitivity().asFile();

        webArchive.addAsLibraries(runtimeLibs);
        webArchive.addAsLibraries(manuallyAddedLibs);
    }
}
