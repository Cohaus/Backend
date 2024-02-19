package gdsc.sc.bsafe.service.grpc;

import gdsc.sc.bsafe.web.dto.request.PredictGradeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GradePredictionService {

    private final GrpcClientService predictionClientService;

    public int getGradeResult(PredictGradeRequest predictGradeRequest) throws IOException {
        byte[] convertedImage = convertMultipartFileToByteArray(predictGradeRequest.getImage());
        String modelName = predictGradeRequest.getCategory().toLowerCase();

        return predictionClientService.predictGrade(modelName, convertedImage);
    }

    public static byte[] convertMultipartFileToByteArray(MultipartFile file) throws IOException {
        return file.getBytes();
    }
}