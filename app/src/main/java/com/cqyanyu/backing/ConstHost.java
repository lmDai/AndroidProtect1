package com.cqyanyu.backing;

import com.cqyanyu.mvpframework.XMeatUrl;

/**
 * 网络请求 配置
 * Created by cheng on 2016/9/24.
 */
public final class ConstHost {

    public static final String TEST = XMeatUrl.getHostUrl();
    public static final String LOCAL_HOST = XMeatUrl.getHostUrl();
    //FIXME:COMMON-MODULE(通用模块)
    /**
     * 图片根地址
     */
    public static final String IMAGE = XMeatUrl.getHostUrl() + "/";

    /**
     * 图片根地址
     */
    public static final String WORD = XMeatUrl.getHostUrl() + "/";
    /**
     * 版本更新
     */
    public static final String UPDATE_VERSION = XMeatUrl.getHostUrl() + "/update/checkAppUpdate";

    /**
     * 首页6个图块数据
     */
    public static final String Home_DeviceTotal_Data = XMeatUrl.getHostUrl() + "/device/getDeviceClassifyTotal";
    /**
     * 首页6个图块数据
     */
    public static final String Home_Table_Data = XMeatUrl.getHostUrl() + "/analysis/getMobileHomeData";
    /**
     * 上传图片地址
     * 参数 files
     */
    public static final String UPLOAD_IMAGE = XMeatUrl.getHostUrl() + "/pictures/savePictures";

    //FIXME: LOGIN-MODULE(登录模块)
    /**
     * 登录
     */
    public static final String LOGIN_URL = XMeatUrl.getHostUrl() + "/login/login";

    /**
     * 获取服务次数
     */
    public static final String GET_SERVER_NUM_URL = XMeatUrl.getHostUrl() + "/login/getServiceTimes";

    /**
     * TODO 退出登录
     */
    public static final String EXIT_LOGIN_URL = XMeatUrl.getHostUrl() + "/login/logout";

    //FIXME: UNIT_MANAGE(单位管理)
    /**
     * 增加单位
     */
    public static final String ADD_UNIT_URL = XMeatUrl.getHostUrl() + "/units/saveUnitNode";

    /**
     * 修改单位
     */
    public static final String MODIFY_UNIT_URL = XMeatUrl.getHostUrl() + "/units/updateUnitNode";

    /**
     * 删除单位
     */
    public static final String DELETE_UNIT_URL = XMeatUrl.getHostUrl() + "/units/deleteUnitNode";

    /**
     * 获取下级单位列表
     */
    public static final String GET_UNIT_LIST_URL = XMeatUrl.getHostUrl() + "/units/getUnitsList";
    public static final String GET_UNIT_PAGE_URL = XMeatUrl.getHostUrl() + "/units/getUnitsListWithCondition";
    /**
     * 获取本单位信息
     */
    public static final String GET_UNIT_INFO_URL = XMeatUrl.getHostUrl() + "/units/getUnitsNode";

    //FIXME: USER_MANAGE(用户管理)
    /**
     * 增加用户
     */
    public static final String ADD_USER_URL = XMeatUrl.getHostUrl() + "/user/saveUserNode";

    /**
     * 修改用户信息
     */
    public static final String MODIFY_USER_URL = XMeatUrl.getHostUrl() + "/user/updateUserNode";

    /**
     * 删除用户
     */
    public static final String DELETE_USER_URL = XMeatUrl.getHostUrl() + "/user/deleteUserNode";

    /**
     * 获取用户信息
     */
    public static final String GET_USER_URL = XMeatUrl.getHostUrl() + "/user/getUserNode";

    /**
     * 获取某个单位的用户信息
     */
    public static final String GET_UNIT_USER_URL = XMeatUrl.getHostUrl() + "/user/getUserList";
    public static final String GET_UNIT_USER_PAGE_URL = XMeatUrl.getHostUrl() + "/user/getUserListWithCondition";
    /**
     * TODO 获取某个用户的权限（菜单功能）
     */
    public static final String GET_UNIT_USER_PERMISION_URL = XMeatUrl.getHostUrl() + "";

    /**
     * 增加用户权限
     */
    public static final String ADD_USER_PERMISION_URL = XMeatUrl.getHostUrl() + "/user/saveUserPermissionNode";

    /**
     * 删除用户权限
     */
    public static final String DELETE_USER_PERMISION_URL = XMeatUrl.getHostUrl() + "/user/deleteUserPermissionNode";

    /**
     * 获取用户终端信息
     */
    public static final String GET_USER_TERMINAL_INFO_URL = XMeatUrl.getHostUrl() + "/user/getUserTerminalList";

    /**
     * 修改用户终端登录权限
     */
    public static final String MODIFY_USER_TERMINAL_PERMISION_URL = XMeatUrl.getHostUrl() + "/user/updateUserTerminalNode";
    /**
     * 修改用户密码
     */
    public static final String MODIFY_USER_PASSWORD_URL = XMeatUrl.getHostUrl() + "/user/updateUserPWD";
    /**
     * 增加词典
     */
    public static final String ADD_DICTION_URL = XMeatUrl.getHostUrl() + "/dictionaries/saveDictionariesNode";

    /**
     * 修改词条名称
     */
    public static final String MODIFY_DICTION_URL = XMeatUrl.getHostUrl() + "/dictionaries/updateDictionariesName";

    /**
     * 删除词条
     */
    public static final String DELETE_DICTION_URL = XMeatUrl.getHostUrl() + "/dictionaries/deleteDictionariesNode";

    /**
     * ID 获取词条信息
     */
    public static final String GET_DICTION_URL = XMeatUrl.getHostUrl() + "/dictionaries/getDictionariesNode";

    /**
     * 获取ID下级词条
     */
    public static final String GET_DICTION_LIST_URL = XMeatUrl.getHostUrl() + "/dictionaries/getDictionariesList";

    //FIXME: DEVICE_MANAGE(设备管理)
    /**
     * 增加父设备
     */
    public static final String ADD_DEVICE_URL = XMeatUrl.getHostUrl() + "/device/saveDeviceNode";

    /**
     * 获取单个父设备信息
     */
    public static final String GET_DEVICE_URL = XMeatUrl.getHostUrl() + "/device/getDeviceNode";

    /**
     * 获取单位父设备信息
     */
    public static final String GET_UNIT_DEVICE_URL = XMeatUrl.getHostUrl() + "/device/getDeviceList";
    public static final String GET_UNIT_DEVICE_PAGE_URL = XMeatUrl.getHostUrl() + "/device/getDeviceListWithCondition";
    /**
     * 修改设备
     */
    public static final String MODIFY_DEVICE_URL = XMeatUrl.getHostUrl() + "/device/updateDeviceNode";

    /**
     * 删除设备
     */
    public static final String DELETE_DEVICE_URL = XMeatUrl.getHostUrl() + "/device/deleteDeviceNode";

    /**
     * 增加子设备 （针对压力和液位）
     */
    public static final String ADD_CHILD_DEVICE_URL = XMeatUrl.getHostUrl() + "/device/saveDeviceChildWarnNode";

    /**
     * 修改子设备 （针对压力和液位）
     */
    public static final String MODIFY_CHILD_DEVICE_URL = XMeatUrl.getHostUrl() + "/device/updateDeviceChildWarnNode";

    /**
     * 删除子设备 （针对压力和液位）
     */
    public static final String DELETE_CHILD_DEVICE_URL = XMeatUrl.getHostUrl() + "/device/deleteDeviceChildWarnNode";

    /**
     * 根据父设备ＩＤ获取子设备 （针对压力和液位）
     */
    public static final String GET_CHILD_DEVICE_URL = XMeatUrl.getHostUrl() + "/device/getDeviceChildWarnList";

    //FIXME: WARN_MANAGE(告警管理)
    /**
     * 根据单位ＩＤ获取告警信息
     */
    public static final String GET_WARN_INFO_URL = XMeatUrl.getHostUrl() + "/alarm/getMessageAlarmList";

    /**
     * 获取单位告警综合信息
     */
    public static final String GET_UNIT_WARN_INFO_URL = XMeatUrl.getHostUrl() + "/alarm/getMessageStatisticsList";

    /**
     * 告警处理
     */
    public static final String WARN_HANLDE_URL = XMeatUrl.getHostUrl() + "/alarm/setAlarmMessageStatus";

    //FIXME: WARN_MANAGE(巡检管理)
    /**
     * 根据单位 ID 获取任务数
     */
    public static final String GET_UNIT_TASK_URL = XMeatUrl.getHostUrl() + "/inspection/getUnitTaskNum";


    /**
     * 根据用户 ID 获取任务数
     */
    public static final String GET_USER_TASK_URL = XMeatUrl.getHostUrl() + "/inspection/getUserTaskNum";

    /**
     * 根据任务 ID 获取任务详情
     */
    public static final String GET_TASK_DETAILS_URL = XMeatUrl.getHostUrl() + "/inspection/getTaskDetails";
    public static final String GET_TASK_DETAILS_Page_URL = XMeatUrl.getHostUrl() + "/inspection/getTaskDetailsWithCondition";
    /**
     * 通过用户ID 获取所有任务详情
     */
    public static final String GET_MY_ALL_TASK_DETAILS_URL = XMeatUrl.getHostUrl() + "/inspection/getUserAllTaskDetails";

    /**
     * 提交巡检任务详情
     */
    public static final String COMMIT_TASK_DETAILS_URL = XMeatUrl.getHostUrl() + "/inspection/setTaskDetails";
    /**
     * 根据单位ID获取历史任务数
     * getUnitTaskNumList
     */
    public static final String GET_UNIT_TASKNUM_LIST = XMeatUrl.getHostUrl() + "/inspection/getUnitTaskNumList";
    //FIXME:DUTY_STATUS(当值状态)
    /**
     * 提交查岗请求
     */
    public static final String COMMIT_DUTY_LAUNCH_URL = XMeatUrl.getHostUrl() + "/duty/launch";

    /**
     * 根据 ID 获取查岗详细记录
     */
    public static final String GET_DUTY_DETAILS_LIST_URL = XMeatUrl.getHostUrl() + "/duty/getdutydetailslist";
    public static final String GET_DUTY_DETAILS_PAGE_URL = XMeatUrl.getHostUrl() + "/duty/getdutydetailslistWithCondition";

    /**
     * 根据 ID 获取查岗统计记录
     */
    public static final String GET_DUTY_TOTAL_LIST_URL = XMeatUrl.getHostUrl() + "/duty/getdutytotallist";
    public static final String GET_DUTY_TOTAL_Page_URL = XMeatUrl.getHostUrl() + "/duty/getdutytotallistWithCondition";

    /**
     * 查岗回复
     */
    public static final String GET_DUTY_ANSWER_URL = XMeatUrl.getHostUrl() + "/duty/answer";

    //FIXME:ENTRY(词条)
    /**
     * 获取所有的词条信息
     */
    public static final String GET_ENTRY_URL = XMeatUrl.getHostUrl() + "/dictionaries/getDictionariesAll";

    //FIXME:TRAINING(宣传培训)
    /**
     * 获取宣传文档
     */
    public static final String GET_TRAINING_URL = XMeatUrl.getHostUrl() + "/lierature/getliteraturefile";
    public static final String GET_VOICE = XMeatUrl.getHostUrl() + "/lierature/getliteraturefilewithcondition";

    /**
     * 根据条件获取主机告警信息
     */
    public static final String GET_ALARM_URL = XMeatUrl.getHostUrl() + "/alarm/getAlarmDetailsWithCondition";
    public static final String GET_HOST_ALARM_URL = XMeatUrl.getHostUrl() + "/alarm/getAlarmDevice";
    public static final String GET_HOST_ALARM_REPORT_URL = XMeatUrl.getHostUrl() + "/alarm/getAlarmDeviceDetails";
    public static final String GET_WARN_SYSTEM_URL = XMeatUrl.getHostUrl() + "/alarm/getWarnDevice";
    public static final String GET_WARN_New_URL = XMeatUrl.getHostUrl() + "/alarm/getWarnReportNHDetails";
    public static final String GET_WARN_REPORT_URL = XMeatUrl.getHostUrl() + "/alarm/getWarnDeviceDetails ";

    /**
     * 获取首页未处理告警信息（火警、预警、故障）
     */
    public static final String GET_Home_Alarm_URL = XMeatUrl.getHostUrl() + "/alarm/getAlarmReportNHDetails";
    public static final String GET_Home_Warn_URL = XMeatUrl.getHostUrl() + "/alarm/getWarnReportNHDetails";
    public static final String GET_Home_Warn_NEW_URL = XMeatUrl.getHostUrl() + "/alarm/getWarnReportNHDetails";
    /**
     * 根据条件获取预警告警信息
     */
    public static final String GET_WARM_URL = XMeatUrl.getHostUrl() + "/alarm/getWarnDetailsWithCondition";

    /**
     * 获取对照表
     */
    public static final String GET_PROBLEM_URL = XMeatUrl.getHostUrl() + "/dictionaries/getComparisonList";

    /**
     * 首页banner
     */
    public static final String GET_BANNER_URL = XMeatUrl.getHostUrl() + "/lierature/getliteraturefilewithcondition";
    public static final String GET_HOME_BANNER_URL = XMeatUrl.getHostUrl() + "/lierature/getBannerPic";
    public static final String GET_UNIT_TASK_Page_URL = XMeatUrl.getHostUrl() + "/inspection/getTaskNumWithCondition";

    /**
     * 巡检设备详情typeid=1
     */
    public static final String GET_DEVICECLASSIFY_DETAILS_URL = XMeatUrl.getHostUrl() + "/device/getDeviceClassifyDetails";
    /**
     * 消防主机设备详情 typeid=2
     */
    public static final String GET_AlARM_DEVICE_DETAILS_URL = XMeatUrl.getHostUrl() + "/device/getAlarmDeviceClassifyDetails";
    /**
     * 水系统设备详情 typeid=3
     */
    public static final String GET_WARN_DEVICE_DETAILS_URL = XMeatUrl.getHostUrl() + "/device/getWarnDeviceClassifyDetails";
    /**
     * 公共资源地图资源
     */
    public static final String GET_MAP_TABLE_DATA = XMeatUrl.getHostUrl() + "/analysis/getMapTableData";
    /**
     * 获取问题
     */
    public static final String GET_ALL_PROBLEM = XMeatUrl.getHostUrl() + "/dictionaries/getAllComparisonList";
    /**
     * 单位选择列表
     */
    public static final String GET_UNITS_LIST = XMeatUrl.getHostUrl() + "/units/getUnitsList";
    /**
     * 设备总数的分类接口
     */
    public static final String GET_TOTAL_DEVICE_CLASSIFY = XMeatUrl.getHostUrl() + "/device/getParentDeviceClassify";
    /**
     * 父设备末级接口
     */
    public static final String GET_PARENT_DEVICE = XMeatUrl.getHostUrl() + "/device/getParentDeviceDetails";
    /**
     * 主机子设备统计
     */
    public static final String GET_CHILD_ALARM_CLASSIFY = XMeatUrl.getHostUrl() + "/device/getChildAlarmDeviceClassify";
    /**
     * 主机设备详情
     */
    public static final String GET_CHILD_ALARM_DETAIL = XMeatUrl.getHostUrl() + "/device/getChildAlarmDeviceClassifyDetails";
    /**
     * 统计
     */
    public static final String GET_COUNT_URL = XMeatUrl.getHostUrl() + "/analysis/getMobileStatisticsData";
    /**
     * 完好率
     */
    public static final String GET_PERFECT_RATE = XMeatUrl.getHostUrl() + "/analysis/getProblemData";
    public static final String GET_PERFECT_SECOND_RATE = XMeatUrl.getHostUrl() + "/device/getDeviceListWithCondition";
    /**
     * 日志上传
     */
    public static final String UPLOAD_LOG = XMeatUrl.getHostUrl() + "/log/saveLog";
}
