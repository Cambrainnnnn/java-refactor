package refactor;

import java.util.Calendar;
import java.util.*;

import com.google.common.collect.Lists;
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

    public void handleTask() throws Exception {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        List<SettleAuditBO> settleAuditBOs = new ArrayList<>();
        // 2个自然日
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 2);
        Date twoDayStart = date.getTime();
        date.add(Calendar.DATE, 1);
        Date twoDayEnd = date.getTime();
        settleAuditBOs.addAll(
                settleAuditRepository.getByCreateTimeAndStatus(twoDayStart, twoDayEnd, SettleStatusEnum.REFUSE.getCode()));

        // 5 个自然日
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 4); // 再往前推4天
        Date fiveDayStart = date.getTime();
        date.add(Calendar.DATE, 1);
        Date fiveDayEnd = date.getTime();
        settleAuditBOs.addAll(
                settleAuditRepository.getByCreateTimeAndStatus(fiveDayStart, fiveDayEnd, SettleStatusEnum.REFUSE.getCode()));

        // 7个自然日
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 3);
        Date sevenDayStart = date.getTime();
        date.add(Calendar.DATE, 1);
        Date sevenDayEnd = date.getTime();
        settleAuditBOs.addAll(
                settleAuditRepository.getByCreateTimeAndStatus(sevenDayStart, sevenDayEnd, SettleStatusEnum.REFUSE.getCode()));

        remindForRefused(settleAuditBOs);
    }

    private void remindForRefused(List<SettleAuditBO> settleAuditBO) {
        // 不重要
        return;
    }
}