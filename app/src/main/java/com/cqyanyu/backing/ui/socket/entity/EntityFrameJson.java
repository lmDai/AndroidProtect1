package com.cqyanyu.backing.ui.socket.entity;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;

public class EntityFrameJson {
    public int cmd;
    public int type;
    public String session;
    public JSONObject obj;

    public EntityFrameJson() {
    }

    public EntityFrameJson(String json) {
        try {//{"cmd":143,"type":129,"parameter":{"sn":0,"msg":{"code":"200"}}}
            //JSONArray arr = new JSONArray(json);
            JSONObject jobj = new JSONObject(json);
            this.cmd = jobj.getInt("cmd");
            this.type = jobj.getInt("type");
            this.obj = jobj.getJSONObject("parameter");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
//            String str = sw.toString();
//            XLog.i(str);
        }

    }
}
