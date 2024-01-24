package gdsc.sc.bsafe.web.controller;

import gdsc.sc.bsafe.global.common.SuccessResponse;
import gdsc.sc.bsafe.service.CloudStorageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class CloudStorageApiController {

    private final CloudStorageService cloudStorageService;

    @Operation(summary = "Cloud Storage 이미지 업로드 test API", hidden = true)
    @PostMapping("")
    public ResponseEntity<SuccessResponse<?>> uploadImage(
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {
        String uploadImage = cloudStorageService.uploadImage(image, "test");

        return SuccessResponse.created(uploadImage);
    }
}
