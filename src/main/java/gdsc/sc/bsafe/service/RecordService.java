package gdsc.sc.bsafe.service;

import gdsc.sc.bsafe.domain.AIRecord;
import gdsc.sc.bsafe.domain.Record;
import gdsc.sc.bsafe.domain.mapping.Repair;
import gdsc.sc.bsafe.global.exception.CustomException;
import gdsc.sc.bsafe.global.exception.enums.ErrorCode;
import gdsc.sc.bsafe.repository.RecordRepository;
import gdsc.sc.bsafe.repository.RepairRepository;
import gdsc.sc.bsafe.repository.UserRepository;
import gdsc.sc.bsafe.web.dto.request.SaveRecordRequest;
import gdsc.sc.bsafe.web.dto.response.RepairAIRecordResponse;
import gdsc.sc.bsafe.web.dto.response.SavedRecordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecordService {
    private final UserRepository userRepository;
    private final RepairRepository repairRepository;
    private final RecordRepository<Record> recordRepository;
    public Record findById(Long recordId){
        return recordRepository.findById(recordId).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_RECORD));
    }

    public SavedRecordResponse getSavedRecord(AIRecord record){
        return new SavedRecordResponse(record);
    }

    public Long createSaveRecord(SaveRecordRequest request){


    }
}
