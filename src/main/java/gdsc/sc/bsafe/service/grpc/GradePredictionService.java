package gdsc.sc.bsafe.service.grpc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GradePredictionService {

    private final GrpcClientService predictionClientService;

    public int getGradeResult(String modelName, MultipartFile image) throws IOException {
        byte[] convertedImage = convertMultipartFileToByteArray(image);

        return predictionClientService.predictGrade(modelName, convertedImage);
    }

    public static byte[] convertMultipartFileToByteArray(MultipartFile file) throws IOException {
        return file.getBytes();
    }
}