package gdsc.sc.bsafe.web.controller;

import gdsc.sc.bsafe.domain.AIRecord;
import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.global.annotation.AuthenticationUser;
import gdsc.sc.bsafe.global.common.SuccessResponse;
import gdsc.sc.bsafe.global.exception.CustomException;
import gdsc.sc.bsafe.global.exception.ErrorResponse;
import gdsc.sc.bsafe.global.exception.enums.ErrorCode;
import gdsc.sc.bsafe.service.RecordService;
import gdsc.sc.bsafe.web.dto.request.SaveRecordRequest;
import gdsc.sc.bsafe.web.dto.request.UpdateSavedRecordRequest;
import gdsc.sc.bsafe.web.dto.response.SavedRecordResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/records")
public class RecordController {
    private final RecordService recordService;

    /*
        기록 저장하기
     */
    @Operation(summary = "기록 저장 API", description = "요청 성공 시 기록의 pk 값을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.")
    })
    @PostMapping
    //@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SuccessResponse<?>> createSaveRecord(@RequestBody SaveRecordRequest saveRecordRequest,
                                                               //@ModelAttribute MultipartFile file,//
                                                               @Valid @AuthenticationUser User user){
        Long recordId = recordService.createSaveRecord(saveRecordRequest, user);
        return SuccessResponse.created(recordId);
    }

    /*
        저장된 기록 조회하기
     */
    @Operation(summary = "저장된 1개의 기록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다.")
    })
    @GetMapping(value = "/{recordId}")
    public ResponseEntity<SuccessResponse<?>> getSavedRecord(@PathVariable Long recordId,
                                                             @AuthenticationUser User user){
        AIRecord aiRecord = (AIRecord) recordService.findById(recordId);
        SavedRecordResponse savedRecordResponse = recordService.getSavedRecord(aiRecord);
        return SuccessResponse.ok(savedRecordResponse);
    }
    /*
        저장 기록 수정하기
     */
    @Operation(summary = "저장 기록 수정 API", description = "요청 성공 시 수정된 결과를 반환합니다.")
    @PatchMapping(value = "/{recordId}")
    public ResponseEntity<?> updateSavedRecord(@PathVariable Long recordId,
                                               @Valid @RequestBody UpdateSavedRecordRequest updateSavedRecordRequest,
                                               @AuthenticationUser User user){
        AIRecord aiRecord = (AIRecord) recordService.findById(recordId);
        if (aiRecord.getUser() == user) {
            SavedRecordResponse savedRecordResponse = recordService.updateRecord(aiRecord, updateSavedRecordRequest);
            return SuccessResponse.ok(savedRecordResponse);
        }
        else return ErrorResponse.toResponseEntity(new CustomException(ErrorCode.INVALID_PERMISSION));
    }

    /*
        저장/수리신청 기록 삭제하기
     */
    @Operation(summary = "저장 기록 수정 API", description = "요청 성공 시 삭제된 기록의 pk 값을 반환합니다.")
    @DeleteMapping(value = "/{recordId}")
    public ResponseEntity<?> deleteRecord(@PathVariable Long recordId,
                                          @AuthenticationUser User user){
        Record record = recordService.findById(recordId);
        if (record.getUser() == user) {
            Long id = recordService.deleteRecord(record);
            return SuccessResponse.ok(id);
        }
       else return ErrorResponse.toResponseEntity(new CustomException(ErrorCode.INVALID_PERMISSION));
    }
}
