package gdsc.sc.bsafe.web.controller;

import gdsc.sc.bsafe.domain.AIRecord;
import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.mapping.Repair;
import gdsc.sc.bsafe.global.annotation.AuthenticationUser;
import gdsc.sc.bsafe.global.common.SuccessResponse;
import gdsc.sc.bsafe.global.exception.CustomException;
import gdsc.sc.bsafe.global.exception.ErrorResponse;
import gdsc.sc.bsafe.global.exception.enums.ErrorCode;
import gdsc.sc.bsafe.service.RecordService;
import gdsc.sc.bsafe.service.RepairService;
import gdsc.sc.bsafe.web.dto.request.*;
import gdsc.sc.bsafe.web.dto.response.RepairIDResponse;
import gdsc.sc.bsafe.web.dto.response.RepairInfoResponse;
import gdsc.sc.bsafe.web.dto.response.RepairRecordResponse;
import gdsc.sc.bsafe.web.dto.response.SavedRecordResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/repairs")
public class RepairController {

    private final RecordService recordService;
    private final RepairService repairService;

    /*
        AI 수리 신청하기
     */
    @Operation(summary = "홈 - 수리 신청 API( AI )", description = "요청 성공 시 기록과 수리 신청 pk 값을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청에 성공했습니다.", useReturnTypeSchema = true,
                    content = @Content(schema = @Schema (implementation = RepairIDResponse.class)))
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
                repairAIRecordRequest.getDate(),
                repairAIRecordRequest.getPlace_id(),
                repairAIRecordRequest.getAddress(),
                repairAIRecordRequest.getDistrict()
        );
        Repair repair = repairService.createRepair(record, repairRequest);

        return SuccessResponse.created(new RepairIDResponse(record.getRecordId(), repair.getRepairId()));
    }

    /*
       일반 수리 신청하기
     */
    @Operation(summary = "홈 - 수리 신청 API( BASIC )", description = "요청 성공 시 기록과 수리 신청 pk 값을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청에 성공했습니다.", useReturnTypeSchema = true,
                    content = @Content(schema = @Schema (implementation = RepairIDResponse.class)))
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
                repairBasicRecordRequest.getDate(),
                repairBasicRecordRequest.getPlace_id(),
                repairBasicRecordRequest.getAddress(),
                repairBasicRecordRequest.getDistrict()
        );
        Repair repair = repairService.createRepair(record, repairRequest);

        return SuccessResponse.created(new RepairIDResponse(record.getRecordId(), repair.getRepairId()));
    }

    /*
        저장된 기록을 수리 신청하기
     */
    @Operation(summary = "상세 화면 - 수리 신청 API( AI )", description = "저장된 기록을 수리 신청합니다. 요청 성공 시 기록과 수리 신청 pk 값을 반환합니다.")
    @PutMapping(value ="/ai")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청에 성공했습니다.", useReturnTypeSchema = true,
                    content = @Content(schema = @Schema (implementation = RepairIDResponse.class)))
    })
    public ResponseEntity<?> createRepair(@RequestParam(value = "id") Long recordId,
                                                           @Valid @RequestBody RepairRequest repairRequest,
                                                           @AuthenticationUser User user){
        AIRecord aiRecord = (AIRecord) recordService.findById(recordId);
        if (aiRecord.getUser() == user) {
            UpdateSavedRecordRequest updateSavedRecordRequest = new UpdateSavedRecordRequest(
                    repairRequest.getTitle(), repairRequest.getDetail(), repairRequest.getCategory());
            recordService.updateRecord(aiRecord, updateSavedRecordRequest);
            RepairRequest request = new RepairRequest(
                    repairRequest.getDate(),
                    repairRequest.getPlace_id(),
                    repairRequest.getAddress(),
                    repairRequest.getDistrict()
            );
            Repair repair = repairService.createRepair(aiRecord, request);
            return SuccessResponse.created(new RepairIDResponse(recordId, repair.getRepairId()));
        }
        else return ErrorResponse.toResponseEntity(new CustomException(ErrorCode.INVALID_PERMISSION));
    }
    /*
        수리 신청 기록 1개 조회
     */
    @Operation(summary = "상세 화면 - 수리 신청 기록 1개 조회 API", description = "수리 신청된 기록 1개를 조회합니다. BASIC 기록일 경우 grade 값을 null로 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.", useReturnTypeSchema = true,
                    content = @Content(schema = @Schema (implementation = RepairRecordResponse.class)))
    })
    @GetMapping(value = "/{repairId}")
    public ResponseEntity<SuccessResponse<?>> getRepairRecord(@PathVariable Long repairId,
                                                              @AuthenticationUser User user){
        Repair repair = repairService.findByRepairId(repairId);
        RepairRecordResponse repairRecordResponse = repairService.getRepairRecord(repair);
        return SuccessResponse.ok(repairRecordResponse);
    }
    /*
        수리 신청 정보 조회
     */
    @Operation(summary = "수리 신청 정보 화면 - 수리 신청 정보 조회 API ", description = "수리 신청된 기록의 신청 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.", useReturnTypeSchema = true,
            content = @Content(schema = @Schema (implementation = RepairInfoResponse.class)))
    })
    @GetMapping(value = "/{repairId}/info")
    public ResponseEntity<SuccessResponse<?>> getRepairInfo(@PathVariable Long repairId,
                                                            @AuthenticationUser User user){
        Repair repair = repairService.findByRepairId(repairId);
        RepairInfoResponse repairInfoResponse = repairService.getRepairInfo(repair);
        return SuccessResponse.ok(repairInfoResponse);
    }
}
