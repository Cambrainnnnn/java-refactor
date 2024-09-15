package refactor.oddIfBranch;

import com.google.common.base.Strings;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class oddIfBranch {
    @Data
    private class MtaPartnerContactsModel {
        List<MtaPartnerContactModel> mtaPartnerContactModelList;
    }

    private class MtaPartnerContactModel {
    }

    private class MtaPartnerContactService {
        public MtaPartnerContactsModel getOwnerContactInfo(Integer partnerId) {
            return null;
        }

        public String getPhone(MtaPartnerContactModel contact) {
            return null;
        }
    }

    private class Partner {
        Map<String, String> getExtendInfo() {
            return null;
        }
    }

    private class MtaPartnerExtService {
        public Map<String, String> getProcessData(Integer partnerId, PmcExtendAttribute extendAttribute) {
            return null;
        }
    }


    private enum PmcExtendAttribute {
        PARTNER_CERT_CONTACT_PHONE("PARTNER.CERT.CONTACT.PHONE");

        public String keyInPMC;

        PmcExtendAttribute(String keyInPMC) {
            this.keyInPMC = keyInPMC;
        }

        public String getKeyInPMC() {
            return keyInPMC;

        }
    }


    private class PmcRpcPartnerService {
        Partner getById(Integer partnerId) {
            return null;
        }
    }

    private MtaPartnerContactService mtaPartnerContactService;
    private PmcRpcPartnerService pmcRpcPartnerService;
    private MtaPartnerExtService mtaPartnerExtService;


    public String getPhone(Integer partnerId) {
        String phone = null;
        MtaPartnerContactsModel mtaPartnerContactsModel = mtaPartnerContactService.getOwnerContactInfo(partnerId);

        // 甲方联系人
        if (mtaPartnerContactsModel != null && mtaPartnerContactsModel.getMtaPartnerContactModelList() != null
                && mtaPartnerContactsModel.getMtaPartnerContactModelList().size() == 1) {
            phone = mtaPartnerContactService.getPhone(mtaPartnerContactsModel.getMtaPartnerContactModelList().get(0));
        }

        // 审核通过的数据在平台
        if (Strings.isNullOrEmpty(phone)) {
            // 获取商家手机号
            Partner partner = pmcRpcPartnerService.getById(partnerId);
            if (Objects.nonNull(partner)) {
                if (!partner.getExtendInfo().isEmpty()) {
                    phone = partner.getExtendInfo().get(PmcExtendAttribute.PARTNER_CERT_CONTACT_PHONE.getKeyInPMC());
                }
            }
        }

        // 未审核的数据在本地
        if (Strings.isNullOrEmpty(phone)) {
            // 获取商家手机号
            Map<String, String> attMap = mtaPartnerExtService.getProcessData(partnerId, PmcExtendAttribute.PARTNER_CERT_CONTACT_PHONE);
            if (!attMap.isEmpty()) {
                phone = attMap.get(PmcExtendAttribute.PARTNER_CERT_CONTACT_PHONE.getKeyInPMC());
            }
        }

        return phone;
    }
}

