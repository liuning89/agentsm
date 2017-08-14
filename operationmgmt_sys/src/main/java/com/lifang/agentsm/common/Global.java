package com.lifang.agentsm.common;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lifang.agentsm.utils.ConfigMap;
import com.lifang.agentsm.utils.PropertiesHelper;

public enum Global {
    ;
    private static final Logger logger = LoggerFactory.getLogger(Global.class);

    // 指定哪台机器跑定时任务
    public final static String QUARTZTASK_RUN_IP;
    // 是否限制访问ip
    public final static int DISABLE_VISIT_IP;

    /**
     * 再审核的间隔时间
     */
    public final static int CHECK_AGAIN_MIDTIME;
    /**
     * 再审核最大的次数
     */
    public final static int CHECK_AGAIN_TIMES;

    /** 轮询的间隔的天数 */
    public final static int CHECK_SELL_LOOP_MID_DATE;

    /** 出租轮询的间隔的天数 */
    public final static int CHECK_RENT_LOOP_MID_DATE;
    
    /** 轮询线程时间 */
    public final static int LOOP_THREAD_TIMES;

    static {

        QUARTZTASK_RUN_IP = Config.get("S_constants.quartzTask_run_IP");
        DISABLE_VISIT_IP = Config._get("I_constants.disable_visit_ip");

        CHECK_AGAIN_MIDTIME = Config._get("I_constants.checkAgainMidTime");
        CHECK_AGAIN_TIMES = Config._get("I_constants.checkAgainTimes");
        CHECK_SELL_LOOP_MID_DATE = Config._get("I_constants.checkSellLoopMidDate");
        CHECK_RENT_LOOP_MID_DATE = Config._get("I_constants.checkRentLoopMidDate");
        LOOP_THREAD_TIMES = Config._get("I_constants.loop_thread_times");

    }

    private static class Config {

        static ConfigMap configMap = new ConfigMap();
        static {
            try {
                configMap = PropertiesHelper.loadProperties("/config.properties");
            }
            catch (Exception e) {
                e.printStackTrace();
                logger.error("The current application of information in the [/config.properties] configuration:current.server");
            }
        }

        public static String get(String key) {
            return configMap.getKV(key, StringUtils.EMPTY);
        }

        public static int _get(String key) {
            return configMap.getKV(key, 0);
        }
    }
}
