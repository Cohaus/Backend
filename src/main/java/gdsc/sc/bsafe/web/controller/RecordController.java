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
    @PostMapping
    //@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SuccessResponse<?>> createSaveRecord(@RequestBody SaveRecordRequest saveRecordRequest,
                                                               //@ModelAttribute MultipartFile file,//
                                                               @AuthenticationUser User user){
        Long recordId = recordService.createSaveRecord(saveRecordRequest, user);
        return SuccessResponse.created(recordId);
    }

    /*
        저장된 기록 조회하기
     */
    @GetMapping(value = "/{recordId}")
    public ResponseEntity<SuccessResponse<?>> getSavedRecord(@PathVariable Long recordId,
                                                             @AuthenticationUser User user){
        AIRecord aiRecord = (AIRecord) recordService.findById(recordId);
        SavedRecordResponse savedRecordResponse = recordService.getSavedRecord(aiRecord);
        return SuccessResponse.ok(savedRecordResponse);
    }
    /*
        기록 수정하기
     */
    @PatchMapping(value = "/{recordId}")
    public ResponseEntity<?> updateSavedRecord(@PathVariable Long recordId,
                                               @RequestBody UpdateSavedRecordRequest updateSavedRecordRequest,
                                               @AuthenticationUser User user){
        AIRecord aiRecord = (AIRecord) recordService.findById(recordId);
        if (aiRecord.getUser() == user) {
            SavedRecordResponse savedRecordResponse = recordService.updateRecord(aiRecord, updateSavedRecordRequest);
            return SuccessResponse.ok(savedRecordResponse);
        }
        else return ErrorResponse.toResponseEntity(new CustomException(ErrorCode.INVALID_PERMISSION));
    }

    /*
        저장 및 수리 신청된 기록 삭제하기
     */
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
