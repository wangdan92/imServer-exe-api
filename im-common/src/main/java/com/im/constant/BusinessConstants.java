package com.im.constant;

/**
 * @author ZhangPeng
 * @date 2017/11/17
 */
public class BusinessConstants {
    public static final int TIMESCALE_WEEK = 7;
    public static final int TIMESCALE_MONTH = 30;
    public static final int TIMESCALE_YEAR = 12;
    public static final int STOP_TEMPERATURE = 10;
    public static final int LIGHT_MAX_NUM = 6;

    public static final String TIMESCALE_BY_WEEK = "week";
    public static final String TIMESCALE_BY_MONTH = "month";
    public static final String TIMESCALE_BY_YEAR = "year";

    public static final String AN_MQTT_DATA = "anagr/pklamp/slave/data";
    public static final String AN_MQTT_GPSDATA = "anagr/pklamp/slave/gps_data";
    public static final String AN_MQTT_GPS = "anagr/pklamp/slave/gps";
    public static final String AN_MQTT_WILL = "anagr/pklamp/slave/will";
    public static final String AN_MQTT_OFFLINE = "anagr/pklamp/slave/offline";
    public static final String AN_MQTT_TIMING = "anagr/pklamp/slave/timing";
    public static final String AN_MQTT_CONTROL = "anagr/pklamp/slave/control";
    public static final String AN_MQTT_UPDATE = "anagr/pklamp/slave/update";
    public static final String AN_MQTT_UPDATE_ACK = "anagr/pklamp/slave/updata_ack";

    public static final String AN_CQ_MQTT_DATA = "anagr/insectCP/slave/data";
    public static final String AN_CQ_MQTT_GPS = "anagr/insectCP/slave/gps";
    public static final String AN_CQ_MQTT_GPSDATA= "anagr/insectCP/slave/gps_data";
    public static final String AN_CQ_MQTT_CONTROL = "anagr/insectCP/slave/control";
    public static final String AN_CQ_MQTT_UPDATE = "anagr/insectCP/slave/update";
    public static final String AN_CQ_MQTT_UPDATE_ACK = "anagr/insectCP/slave/updata_ack";
    public static final String AN_CQ_MQTT_WILL = "anagr/insectCP/slave/will";
    public static final String AN_CQ_MQTT_OFFLINE = "anagr/insectCP/slave/offline";
    public static final String AN_CQ_MQTT_TIMING = "anagr/insectCP/slave/timing";

    public static final String AN_CQCB_UPGRADE_FILE = "cqUpdate.bin";
    public static final String AN_SCD_UPGRADE_FILE = "update.bin";

    public static final byte MESSAGE_TYPE_GLOBAL = 0;
    public static final byte MESSAGE_TYPE_COMMON = 1;
    public static final byte MESSAGE_TYPE_ALARM = 2;

    public static final byte ALARM_STAUTS_UNHANDLE = 0;
    public static final byte ALARM_STAUTS_INHANDLE = 1;
    public static final byte ALARM_STAUTS_FINISHED = 2;

    /** 停止运行 */
    public static final int DEVICE_CONTROL_STOP = 0;
    /** 手动模式 */
    public static final int DEVICE_CONTROL_MANUAL = 1;
    /** 时控模式 */
    public static final int DEVICE_CONTROL_TIME = 2;
    /** 光控模式 */
    public static final int DEVICE_CONTROL_LIGHT = 3;

    public static final String DEVICE_TEMPLATE_PATH = "template/deviceTemplate.xlsx";


}
