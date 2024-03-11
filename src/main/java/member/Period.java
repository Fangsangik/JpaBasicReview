package member;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Period {
    private LocalDateTime starteDate;
    private LocalDateTime endDate;

    public LocalDateTime getStarteDate() {
        return starteDate;
    }

    public void setStarteDate(LocalDateTime starteDate) {
        this.starteDate = starteDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
