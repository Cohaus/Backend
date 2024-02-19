package gdsc.sc.bsafe.service.grpc;

import bsafe.src.main.proto.GradePredictionServiceGrpc;
import bsafe.src.main.proto.GradeRequest;
import bsafe.src.main.proto.GradeResponse;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class GrpcClientService {

    private final ManagedChannel channel;
    private final GradePredictionServiceGrpc.GradePredictionServiceBlockingStub gradePredictionServiceBlockingStub;

    public GrpcClientService() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 8500)
                .usePlaintext()
                .build();

        this.gradePredictionServiceBlockingStub = GradePredictionServiceGrpc.newBlockingStub(channel);
    }

    public int predictGrade(String modelName, byte[] imageBytes) throws IOException {
        byte[] image = preprocessImage(imageBytes);

        GradeRequest request = GradeRequest.newBuilder()
                .setModelName(modelName)
                .setSignatureName("serving_default")
                .setInputData(ByteString.copyFrom(image))
                .build();

        GradeResponse response = gradePredictionServiceBlockingStub.predict(request);

        return response.getGradeResult();
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(10, TimeUnit.SECONDS);
    }

    private static byte[] preprocessImage(byte[] imageBytes) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));

        BufferedImage resizedImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
        resizedImage
                .getGraphics()
                .drawImage(image.getScaledInstance(150, 150, BufferedImage.SCALE_SMOOTH), 0, 0, null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", outputStream);
        byte[] resizedImageBytes = outputStream.toByteArray();
        outputStream.close();

        return resizedImageBytes;

        /* Preprocessing image for Tensorflow Model

        float[] normalizedImage = new float[resizedImageBytes.length];
        for (int i = 0; i < resizedImageBytes.length; ++i) {
            normalizedImage[i] = (resizedImageBytes[i] & 0xFF) / 255.0f;
        }

        return normalizedImage;

         */
    }
}
