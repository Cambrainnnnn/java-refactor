package refactor;

import java.util.Calendar;
import java.util.*;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
// import com.google.common.base.Pair;
import lombok.*;

public class SettleRefuseRemindJob {
    private class SettleAuditRepository {
        private List<SettleAuditBO> getByCreateTimeAndStatus(Date start, Date end, int status) {
            SettleAuditBO audit =  new SettleAuditBO();
            return Lists.newArrayList(audit);
        }
    }

    private enum SettleStatusEnum {
        REFUSE(0);

        private int code;

        SettleStatusEnum(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    @Data
    @AllArgsConstructor
    private class TimeRange {
        Date start;
        Date end;
    }


    private class SettleCustomerRepository {
    }

    private class MessageService {
    }

    private class SignerRepository {
    }

    @Data
    private class SettleAuditBO {
        Integer id;
        SettleStatusEnum status;
    }

    private SettleAuditRepository settleAuditRepository;

    private static final List<Integer> daysToRemind  = Lists.newArrayList(1, 5, 7);

    public void handleTask() throws Exception {
        List<SettleAuditBO> settleAuditBOs = Lists.newArrayList();
        for (int day : daysToRemind) {
            TimeRange range = caclTimeRange(day);
            settleAuditBOs.addAll(settleAuditRepository.getByCreateTimeAndStatus(
                    range.getStart(), range.getEnd(), SettleStatusEnum.REFUSE.getCode()));
        }

        remindForRefused(settleAuditBOs);
    }

    private TimeRange caclTimeRange(int dayAgo) {
        Calendar zeroTiming = Calendar.getInstance();
        zeroTiming.set(Calendar.HOUR_OF_DAY, 0);
        zeroTiming.set(Calendar.MINUTE, 0);
        zeroTiming.set(Calendar.SECOND, 0);
        zeroTiming.set(Calendar.MILLISECOND, 0);
        zeroTiming.set(Calendar.DATE, zeroTiming.get(Calendar.DATE) - dayAgo);
        Date end = zeroTiming.getTime();
        zeroTiming.add(Calendar.DATE, -1);
        Date start = zeroTiming.getTime();
        return new TimeRange(start, end);
    }

    private void remindForRefused(List<SettleAuditBO> settleAuditBO) {
        // 不重要
        return;
    }
}