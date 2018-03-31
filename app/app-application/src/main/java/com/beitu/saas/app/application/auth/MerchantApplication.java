package com.beitu.saas.app.application.auth;

import com.beitu.saas.auth.entity.*;
import com.beitu.saas.auth.enums.AdminErrorEnum;
import com.beitu.saas.auth.enums.ContractConfigTypeEnum;
import com.beitu.saas.auth.enums.MerchantConfigTypeEnum;
import com.beitu.saas.auth.service.*;
import com.fqgj.common.utils.GenerOrderNoUtil;
import com.fqgj.common.utils.MD5;
import com.fqgj.common.utils.StringUtils;
import com.fqgj.exception.common.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author xiaochong
 * @create 2018/3/26 下午8:56
 * @description
 */
@Component
public class MerchantApplication {

    @Autowired
    private SaasMerchantService saasMerchantService;

    @Autowired
    private SaasAdminService saasAdminService;

    @Autowired
    private RoleApplication roleApplication;

    @Autowired
    private SaasAdminRoleService saasAdminRoleService;

    @Autowired
    private SaasMerchantConfigService saasMerchantConfigService;

    @Autowired
    private SaasSmsConfigDictionaryService saasSmsConfigDictionaryService;


    @Transactional(rollbackFor = Exception.class)
    public void addMerchant(SaasMerchant saasMerchant, String password) {

        //1.保存机构信息
        saasMerchant.setMerchantCode(GenerOrderNoUtil.generateOrderNo());
        saasMerchantService.create(saasMerchant);
        //2.添加登录用户
        SaasAdmin saasAdmin = new SaasAdmin();
        saasAdmin.setMerchantCode(saasMerchant.getMerchantCode());
        saasAdmin.setCode(GenerOrderNoUtil.generateOrderNo());
        saasAdmin.setJob("系统超级管理员");
        saasAdmin.setName(StringUtils.isNotEmpty(saasMerchant.getCompanyName()) ? saasMerchant.getCompanyName() : saasMerchant.getLenderName());
        saasAdmin.setMobile(StringUtils.isNotEmpty(saasMerchant.getCompanyTel()) ? saasMerchant.getCompanyTel() : saasMerchant.getLenderTel());
        saasAdmin.setPassword(MD5.md5(password));
        saasAdmin.setCreateName("system");
        saasAdmin.setDefault(true);
        saasAdmin.setEnable(true);
        if (saasAdminService.hasRegisteredMobile(saasAdmin.getMobile())) {
            throw new ApplicationException(AdminErrorEnum.MOBILE_EXIST);
        }
        saasAdminService.create(saasAdmin);
        //3.添加机构默认角色
        //超级管理员
        Long roleId = roleApplication.addRoleAndEmpower("超级管理员", "system", saasAdmin.getMerchantCode(),
                Arrays.asList(109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126),
                Arrays.asList(109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155)
        );
        //子管理员
        roleApplication.addRoleAndEmpower("子管理员", "system", saasAdmin.getMerchantCode(),
                Arrays.asList(109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126),
                Arrays.asList(109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152)
        );
        //审核
        roleApplication.addRoleAndEmpower("审核", "system", saasAdmin.getMerchantCode(),
                Arrays.asList(113, 114, 115, 116, 117),
                Arrays.asList(114, 115, 116, 117, 118, 119, 136, 137, 138, 139, 140, 141)
        );
        //财务
        roleApplication.addRoleAndEmpower("财务", "system", saasAdmin.getMerchantCode(),
                Arrays.asList(113, 118, 119, 120),
                Arrays.asList(142, 143, 144, 145, 146, 147, 148, 149, 150)
        );
        //催收
        roleApplication.addRoleAndEmpower("催收", "system", saasAdmin.getMerchantCode(),
                Arrays.asList(121),
                Arrays.asList(151, 152)
        );

        //4.给用户赋权
        SaasAdminRole saasAdminRole = new SaasAdminRole();
        saasAdminRole.setAdminCode(saasAdmin.getCode());
        saasAdminRole.setRoleId(roleId);
        saasAdminRoleService.create(saasAdminRole);
        //5.添加默认机构配置
        SaasMerchantConfig saasMerchantConfig = new SaasMerchantConfig();
        saasMerchantConfig.setMerchantCode(saasAdmin.getMerchantCode());
        saasMerchantConfig.setConfigEnum(ContractConfigTypeEnum.COMPANY_CONTRACT.getKey().toString());
        saasMerchantConfig.setConfigType(MerchantConfigTypeEnum.CONTRACT_CONFIG.getKey().longValue());
        saasMerchantConfigService.create(saasMerchantConfig);

        List<SaasSmsConfigDictionary> smsConfig = saasSmsConfigDictionaryService.getAllSmsConfig();
        smsConfig.forEach(saasSmsConfigDictionary -> {
            SaasMerchantConfig entity = new SaasMerchantConfig();
            saasMerchantConfig.setMerchantCode(saasAdmin.getMerchantCode());
            saasMerchantConfig.setConfigEnum(saasSmsConfigDictionary.getBizCode());
            saasMerchantConfig.setConfigType(MerchantConfigTypeEnum.SMS_CONFIG.getKey().longValue());
            saasMerchantConfigService.create(entity);
        });
    }
}
