package com.cqyanyu.backing.ui.socket.entity;

/**
 * 响应查岗推送信息
 * Created by Administrator on 2017/7/31.
 */
public class ResponseCheckEntity {
    private String cmd;
    private String type;
    private ParameterBean parameter;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ParameterBean getParameter() {
        return parameter;
    }

    public void setParameter(ParameterBean parameter) {
        this.parameter = parameter;
    }

    public static class ParameterBean {

        private String sn;
        private MsgBean msg;

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public MsgBean getMsg() {
            return msg;
        }

        public void setMsg(MsgBean msg) {
            this.msg = msg;
        }

    }

    public static class MsgBean {

        private String oid;
        private String launchmanid;
        private String launchunitid;
        private String launchdate;
        private String targetmanid;
        private String targetunitid;
        private String ispush;
        private String pushresult;
        private String pushdate;
        private String isanswer;
        private String isanswervalid;
        private String answermanid;
        private String answerdate;
        private String answerlongitude;
        private String answerlatitude;
        private String answerposition;
        private String answerremark;

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getLaunchmanid() {
            return launchmanid;
        }

        public void setLaunchmanid(String launchmanid) {
            this.launchmanid = launchmanid;
        }

        public String getLaunchunitid() {
            return launchunitid;
        }

        public void setLaunchunitid(String launchunitid) {
            this.launchunitid = launchunitid;
        }

        public String getLaunchdate() {
            return launchdate;
        }

        public void setLaunchdate(String launchdate) {
            this.launchdate = launchdate;
        }

        public String getTargetmanid() {
            return targetmanid;
        }

        public void setTargetmanid(String targetmanid) {
            this.targetmanid = targetmanid;
        }

        public String getTargetunitid() {
            return targetunitid;
        }

        public void setTargetunitid(String targetunitid) {
            this.targetunitid = targetunitid;
        }

        public String getIspush() {
            return ispush;
        }

        public void setIspush(String ispush) {
            this.ispush = ispush;
        }

        public String getPushresult() {
            return pushresult;
        }

        public void setPushresult(String pushresult) {
            this.pushresult = pushresult;
        }

        public String getPushdate() {
            return pushdate;
        }

        public void setPushdate(String pushdate) {
            this.pushdate = pushdate;
        }

        public String getIsanswer() {
            return isanswer;
        }

        public void setIsanswer(String isanswer) {
            this.isanswer = isanswer;
        }

        public String getIsanswervalid() {
            return isanswervalid;
        }

        public void setIsanswervalid(String isanswervalid) {
            this.isanswervalid = isanswervalid;
        }

        public String getAnswermanid() {
            return answermanid;
        }

        public void setAnswermanid(String answermanid) {
            this.answermanid = answermanid;
        }

        public String getAnswerdate() {
            return answerdate;
        }

        public void setAnswerdate(String answerdate) {
            this.answerdate = answerdate;
        }

        public String getAnswerlongitude() {
            return answerlongitude;
        }

        public void setAnswerlongitude(String answerlongitude) {
            this.answerlongitude = answerlongitude;
        }

        public String getAnswerlatitude() {
            return answerlatitude;
        }

        public void setAnswerlatitude(String answerlatitude) {
            this.answerlatitude = answerlatitude;
        }

        public String getAnswerposition() {
            return answerposition;
        }

        public void setAnswerposition(String answerposition) {
            this.answerposition = answerposition;
        }

        public String getAnswerremark() {
            return answerremark;
        }

        public void setAnswerremark(String answerremark) {
            this.answerremark = answerremark;
        }
    }
}
