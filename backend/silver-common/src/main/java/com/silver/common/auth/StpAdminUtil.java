package com.silver.common.auth;

import cn.dev33.satoken.stp.StpLogic;

public final class StpAdminUtil {

    public static final String LOGIN_TYPE = "admin";

    private static final StpLogic STP_LOGIC = new StpLogic(LOGIN_TYPE);

    private StpAdminUtil() {
    }

    public static StpLogic stpLogic() {
        return STP_LOGIC;
    }
}
