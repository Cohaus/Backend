package gdsc.sc.bsafe.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class CloudStorageConfig {

    @Bean
    public Storage storage() throws IOException {
        ClassPathResource resource = new ClassPathResource("gcp-credentials.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        String projectId = "feisty-ward-411905";

        return StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(credentials)
                .build()
                .getService();
    }
}
