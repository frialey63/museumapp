package org.pjp.museum;

import org.pjp.museum.service.ExhibitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "museumapp")
@PWA(name = "Museum App", shortName = "Museum App", offlineResources = {})
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-nav", version = "1.0.6")
public class MuseumApp extends SpringBootServletInitializer implements AppShellConfigurator, ApplicationRunner {

    private static final long serialVersionUID = -6201010168785334238L;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @Autowired
    private ExhibitService exhibitService;

    public static void main(String[] args) {
        SpringApplication.run(MuseumApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if ("docker".equals(activeProfile)) {
            exhibitService.testData();
        }
    }

}
