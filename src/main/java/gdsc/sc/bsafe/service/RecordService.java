package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.AIRecord;
import gdsc.sc.bsafe.domain.BasicRecord;
import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.mapping.Repair;
import gdsc.sc.bsafe.global.exception.CustomException;
import gdsc.sc.bsafe.global.exception.enums.ErrorCode;
import gdsc.sc.bsafe.repository.*;
import gdsc.sc.bsafe.web.dto.common.SliceResponse;
import gdsc.sc.bsafe.web.dto.request.AIRecordRequest;
import gdsc.sc.bsafe.web.dto.request.BasicRecordRequest;
import gdsc.sc.bsafe.web.dto.request.UpdateSavedRecordRequest;
import gdsc.sc.bsafe.web.dto.response.RecordItemResponse;
import gdsc.sc.bsafe.web.dto.response.SavedRecordResponse;
import gdsc.sc.bsafe.web.dto.response.UserRecordListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
    private final UserRepository userRepository;
    private final RecordRepository recordRepository;
    private final BasicRecordRepository basicRecordRepository;
    private final AIRecordRepository aiRecordRepository;
    private final RepairRepository repairRepository;
    public Record findById(Long recordId){
        return recordRepository.findById(recordId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_RECORD));
    }

    @Transactional
    public Record createAIRecord(AIRecordRequest request, User user){
        //
        // 이미지 업로드
        //
        String imgUrl = "imgUrl";
        AIRecord aiRecord = AIRecord.builder()
                .title(request.getTitle())
                .detail(request.getDetail())
                .user(user)
                .category(request.getCategory())
                .image(imgUrl)
                .grade(request.getGrade())
                .build();
        Record record = aiRecordRepository.save(aiRecord);
        return record;
    }
    @Transactional
    public Record createBasicRecord(BasicRecordRequest request, User user){
        //
        // 이미지 업로드
        //
        String imgUrl = "imgUrl";
        BasicRecord basicRecord = BasicRecord.builder()
                .title(request.getTitle())
                .detail(request.getDetail())
                .user(user)
                .category(request.getCategory())
                .image(imgUrl)
                .build();
        Record record = basicRecordRepository.save(basicRecord);
        return record;
    }
    public SavedRecordResponse getSavedRecord(AIRecord record){
        return new SavedRecordResponse(record);
    }

    public UserRecordListResponse getUserRecords(User user){
        Slice<AIRecord> savedRecordList = aiRecordRepository.getAIRecordsByUser(user);
        SliceResponse<RecordItemResponse> savedRecords =  new SliceResponse<>(savedRecordList.map(RecordItemResponse::new));

        Slice<Record> repairRecordList = repairRepository.findAllByRecord_User(user).map(Repair::getRecord);
        SliceResponse<RecordItemResponse> repairRecords  = new SliceResponse<>(repairRecordList.map(RecordItemResponse::new)) ;

        UserRecordListResponse response= new UserRecordListResponse();
        response.setSaved_record(savedRecords);
        response.setRepair_record(repairRecords);
        return response;
    }
    public SavedRecordResponse updateRecord(AIRecord aiRecord, UpdateSavedRecordRequest request){
        aiRecord.updateSavedRecord(request.getTitle(), request.getDetail(), request.getCategory());
        AIRecord updatedRecord = aiRecordRepository.save(aiRecord);
        return new SavedRecordResponse(updatedRecord);
    }
    public Long deleteRecord(Record record) {
            Optional<Repair> repair = repairRepository.findByRecord(record);
            // 수리 신청한 경우
            if (repair.isPresent()) {
                repairRepository.delete(repair.get());
            }
            // 기록 저장만 한 경우
            recordRepository.delete(record);
            return record.getRecordId();
        }
}
