package gdsc.sc.bsafe.web.dto.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SliceResponse<T> implements Serializable {
    private List<T> content;
    private boolean hasNext;
    private boolean isFirst;
    private boolean isLast;

    public SliceResponse(Slice<T> slice){
        this.content = slice.getContent();
        this.hasNext = slice.hasNext();
        this.isFirst = slice.isFirst();
        this.isLast = slice.isLast();
    }
}
