package gdsc.sc.bsafe.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional
public class CloudStorageService {

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucket;

    private final Storage storage;

    private static final String PUBLIC_URL_PREFIX = "https://storage.googleapis.com/gces_bucket/";

    public String uploadImage(MultipartFile image, String directory) throws IOException {
        String originalName = image.getOriginalFilename();

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timeInfo = now.format(format);

        String fileName = directory + "/" + originalName + "_" + timeInfo;
        String extension = image.getContentType();

        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucket, fileName)
                        .setContentType(extension)
                        .build(),
                image.getInputStream()
        );

        return PUBLIC_URL_PREFIX + blobInfo.getName();
    }
}
