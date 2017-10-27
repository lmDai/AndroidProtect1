package com.cqyanyu.backing.ui.socket.entity;

/**
 * Created by personal on 2017/10/19.
 */

public class ResponseLocationEntity {

    /**
     * cmd : 143
     * type : 135
     * parameter : {"msg":{"code":"200"},"sn":12}
     */

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
        /**
         * msg : {"code":"200"}
         * sn : 12
         */

        private MsgBean msg;
        private String sn;

        public MsgBean getMsg() {
            return msg;
        }

        public void setMsg(MsgBean msg) {
            this.msg = msg;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public static class MsgBean {
            /**
             * code : 200
             */

            private String code;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }
        }
    }
}
