package org.pjp.museum;

import org.apache.commons.compress.utils.Sets;
import org.pjp.museum.service.ExhibitService;
import org.pjp.museum.service.MuseumService;
import org.pjp.museum.ui.util.QrCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@Push
@Theme(value = "museumapp")
@PWA(name = "Museum (Audio Guide) App", shortName = "Museum App", backgroundColor="#adcce9", description="An app which provides a QR code driven audio guide for the RAF Manston History Museum.", offlineResources = {})
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-nav", version = "1.0.6")
@EnableScheduling
@SpringBootApplication
public class MuseumApp extends SpringBootServletInitializer implements AppShellConfigurator, ApplicationRunner {

    private static final long serialVersionUID = -6201010168785334238L;

    private static final Logger LOGGER = LoggerFactory.getLogger(MuseumApp.class);

    private static final String APP_DOWNLOAD_URL = "app-download-url.png";

    @Value("${spring.profiles.active:default}")
    private String[] springProfilesActive;

    @Value("${app.download.url}")
    private String appDownloadUrl;

    @Autowired
    private ExhibitService exhibitService;

    @Autowired
    private MuseumService museumService;

    public static void main(String[] args) {
        SpringApplication.run(MuseumApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        museumService.initData();

        if (Sets.newHashSet(springProfilesActive).contains("test")) {
            exhibitService.testData();
        }

        LOGGER.info("creating QR code for app download URL {} and writing to file {}", appDownloadUrl, APP_DOWNLOAD_URL);
        QrCodeUtils.createAndWriteQR(appDownloadUrl, APP_DOWNLOAD_URL);
    }

    @Scheduled(cron = "0 * * * * *")
    public void checkClosingTime() {
        museumService.checkForAndNotifyClosingTime();
    }

}
