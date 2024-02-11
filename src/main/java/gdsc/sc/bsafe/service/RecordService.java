package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.AIRecord;
import gdsc.sc.bsafe.domain.BasicRecord;
import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.enums.AIGrade;
import gdsc.sc.bsafe.domain.enums.RecordType;
import gdsc.sc.bsafe.domain.enums.RepairCategory;
import gdsc.sc.bsafe.domain.mapping.Repair;
import gdsc.sc.bsafe.global.exception.CustomException;
import gdsc.sc.bsafe.global.exception.enums.ErrorCode;
import gdsc.sc.bsafe.repository.AIRecordRepository;
import gdsc.sc.bsafe.repository.BasicRecordRepository;
import gdsc.sc.bsafe.repository.RecordRepository;
import gdsc.sc.bsafe.repository.RepairRepository;
import gdsc.sc.bsafe.web.dto.common.SliceResponse;
import gdsc.sc.bsafe.web.dto.request.AIRecordRequest;
import gdsc.sc.bsafe.web.dto.request.BasicRecordRequest;
import gdsc.sc.bsafe.web.dto.request.UpdateSavedRecordRequest;
import gdsc.sc.bsafe.web.dto.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecordService {

    private static final String AI_IMAGE_PATH = "records/ai";
    private static final String BASIC_IMAGE_PATH = "records/basic";

    private final RecordRepository recordRepository;
    private final BasicRecordRepository basicRecordRepository;
    private final AIRecordRepository aiRecordRepository;
    private final RepairRepository repairRepository;
    private final CloudStorageService cloudStorageService;

    public Record findById(Long recordId){
        return recordRepository.findById(recordId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_RECORD));
    }

    @Transactional
    public Record createAIRecord(AIRecordRequest request, User user) throws IOException {
        if (!AIGrade.isValidDescription(request.getGrade())) {
            throw new CustomException(ErrorCode.INVALID_ENUM_DESCRIPTION);
        }
        String imagePath = cloudStorageService.uploadImage(request.getImage(), AI_IMAGE_PATH);
        AIRecord aiRecord = AIRecord.builder()
                .title(request.getTitle())
                .detail(request.getDetail())
                .user(user)
                .category(RepairCategory.convert(request.getCategory()))
                .image(imagePath)
                .grade(AIGrade.convert(request.getGrade()))
                .build();
        return aiRecordRepository.save(aiRecord);
    }

    @Transactional
    public Record createBasicRecord(BasicRecordRequest request, User user) throws IOException {
        String imagePath = cloudStorageService.uploadImage(request.getImage(), BASIC_IMAGE_PATH);
        BasicRecord basicRecord = BasicRecord.builder()
                .title(request.getTitle())
                .detail(request.getDetail())
                .user(user)
                .category(RepairCategory.convert(request.getCategory()))
                .image(imagePath)
                .build();

        return basicRecordRepository.save(basicRecord);
    }

    @Transactional(readOnly = true)
    public SavedRecordResponse getSavedRecord(AIRecord record){
        return new SavedRecordResponse(record);
    }

    @Transactional(readOnly = true)
    public UserRecordListResponse getUserRecords(User user){
        Slice<Repair> repairList = repairRepository.findAllByRecord_UserOrderByCreatedAtDesc(user);
        Slice<Record> repairRecordList = repairList.map(Repair::getRecord);
        Slice<AIRecord> savedRecordList = (repairRecordList.getSize() == 0) ?
                aiRecordRepository.getAIRecordsByUser(user) :
                aiRecordRepository.queryFindSavedRecords(repairRecordList.getContent(), user);

        SliceResponse<RecordItemResponse> savedRecords =  new SliceResponse<>(savedRecordList.map(record -> new RecordItemResponse(record, RecordType.AI))) ;

        SliceResponse<RecordItemResponse> repairRecords  = new SliceResponse<>(repairList.map(repair ->{
            Long id =  repair.getRepairId();
            Record record  = repair.getRecord();
            RecordType type = getRecordType(record);
            return new RecordItemResponse(id, record, type);
        })) ;

        UserRecordListResponse response= new UserRecordListResponse();
        response.setSaved_record(savedRecords);
        response.setRepair_record(repairRecords);
        return response;
    }

    private static RecordType getRecordType(Record record) {
        RecordType type;
        if (record instanceof AIRecord){
            type = RecordType.AI;
        }
        else type = RecordType.BASIC;
        return type;
    }

    @Transactional
    public SavedRecordResponse updateRecord(AIRecord aiRecord, UpdateSavedRecordRequest request){
        aiRecord.updateSavedRecord(request.getTitle(), request.getDetail());
        AIRecord updatedRecord = aiRecordRepository.save(aiRecord);
        return new SavedRecordResponse(updatedRecord);
    }

    @Transactional
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
