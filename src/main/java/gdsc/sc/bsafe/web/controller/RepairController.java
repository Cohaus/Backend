package gdsc.sc.bsafe.web.controller;

import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.mapping.Repair;
import gdsc.sc.bsafe.global.annotation.AuthenticationUser;
import gdsc.sc.bsafe.global.common.SuccessResponse;
import gdsc.sc.bsafe.service.RecordService;
import gdsc.sc.bsafe.service.RepairService;
import gdsc.sc.bsafe.web.dto.request.*;
import gdsc.sc.bsafe.web.dto.response.RepairIDResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/repairs")
public class RepairController {

    private final RecordService recordService;
    private final RepairService repairService;

    @Operation(summary = "홈 - 수리 신청 API( AI )", description = "요청 성공 시 기록과 수리 신청 pk 값을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청에 성공했습니다.")
    })
    @PostMapping(value = "/ai", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SuccessResponse<?>> createAIRepair(@Valid @ModelAttribute RepairAIRecordRequest repairAIRecordRequest,
                                                             @AuthenticationUser User user) throws IOException {
        AIRecordRequest aiRecordRequest = new AIRecordRequest(
                repairAIRecordRequest.getTitle(),
                repairAIRecordRequest.getDetail(),
                repairAIRecordRequest.getGrade(),
                repairAIRecordRequest.getCategory(),
                repairAIRecordRequest.getImage()
        );
        Record record = recordService.createAIRecord(aiRecordRequest, user);

        RepairRequest repairRequest = new RepairRequest(
                record,
                repairAIRecordRequest.getDate(),
                repairAIRecordRequest.getPlace_id(),
                repairAIRecordRequest.getAddress(),
                repairAIRecordRequest.getDistrict()
        );
        Repair repair = repairService.createRepair(repairRequest);

        return SuccessResponse.created(new RepairIDResponse(record.getRecordId(), repair.getRepairId()));
    }

    @Operation(summary = "홈 - 수리 신청 API( BASIC )", description = "요청 성공 시 기록과 수리 신청 pk 값을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청에 성공했습니다.")
    })
    @PostMapping(value = "/basic", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SuccessResponse<?>> createBasicRepair(@Valid @ModelAttribute RepairBasicRecordRequest repairBasicRecordRequest,
                                                                @AuthenticationUser User user) throws IOException {
        BasicRecordRequest basicRecordRequest = new BasicRecordRequest(
                repairBasicRecordRequest.getTitle(),
                repairBasicRecordRequest.getDetail(),
                repairBasicRecordRequest.getCategory(),
                repairBasicRecordRequest.getImage()
        );
        Record record = recordService.createBasicRecord(basicRecordRequest, user);

        RepairRequest repairRequest = new RepairRequest(
                record,
                repairBasicRecordRequest.getDate(),
                repairBasicRecordRequest.getPlace_id(),
                repairBasicRecordRequest.getAddress(),
                repairBasicRecordRequest.getDistrict()
        );
        Repair repair = repairService.createRepair(repairRequest);

        return SuccessResponse.created(new RepairIDResponse(record.getRecordId(), repair.getRepairId()));
    }


}
