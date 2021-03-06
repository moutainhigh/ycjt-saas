package com.beitu.saas.rest.controller.auth.response;

import com.beitu.saas.auth.vo.FormedMenuVO;
import com.beitu.saas.risk.helpers.CollectionUtils;
import com.fqgj.common.api.ResponseData;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * @author xiaochong
 * @create 2018/3/31 下午12:19
 * @description
 */
@ApiModel
public class RoleDetailResponse implements ResponseData {

    private String roleName;

    private List<FormedMenuVO.ParentMenu> menuList;

    private List<UserButtonResponse.OperationButton> buttonList;


    public RoleDetailResponse(String roleName, FormedMenuVO formedMenuVO, List<UserButtonResponse.OperationButton> buttonList, List<Integer> buttonIds, List<Integer> menuIds) {

        this.roleName = roleName;
        this.menuList = formedMenuVO.getList();
        this.buttonList = buttonList;
        this.buttonList.forEach(operationButton -> {
            operationButton.setChecked(buttonIds.contains(operationButton.getId().intValue()));
            if (CollectionUtils.isNotEmpty(operationButton.getList())) {
                operationButton.getList().forEach(operationButtonItem -> operationButtonItem.setChecked(buttonIds.contains(operationButtonItem.getId().intValue())));
            }
        });
        this.menuList.forEach((FormedMenuVO.ParentMenu parentMenu) -> {
            parentMenu.setChecked(menuIds.contains(parentMenu.getId().intValue()));
            if (CollectionUtils.isNotEmpty(parentMenu.getChildren())) {
                parentMenu.getChildren().forEach(childrenMenu -> childrenMenu.setChecked(menuIds.contains(childrenMenu.getId().intValue())));
            }
        });
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<FormedMenuVO.ParentMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<FormedMenuVO.ParentMenu> menuList) {
        this.menuList = menuList;
    }

    public List<UserButtonResponse.OperationButton> getButtonList() {
        return buttonList;
    }

    public void setButtonList(List<UserButtonResponse.OperationButton> buttonList) {
        this.buttonList = buttonList;
    }
}
