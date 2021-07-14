package com.dms.pms.utils.api.dto.general;

import com.dms.pms.utils.api.dto.UserInfo;

public class FacebookUserInfo implements UserInfo {
    String name;
    String email;

    @Override
    public String getUserName() {
        return this.name;
    }

    @Override
    public String getEmail() {
        return this.email;
    }
}
