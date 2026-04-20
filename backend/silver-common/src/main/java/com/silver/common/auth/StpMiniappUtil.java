package com.silver.common.auth;

import cn.dev33.satoken.stp.StpLogic;

public final class StpMiniappUtil {

    public static final String LOGIN_TYPE = "miniapp";

    private static final StpLogic STP_LOGIC = new StpLogic(LOGIN_TYPE);

    private StpMiniappUtil() {
    }

    public static StpLogic stpLogic() {
        return STP_LOGIC;
    }
}
