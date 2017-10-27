package com.cqyanyu.backing.ui.socket.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class EntityFrameJson {
	public int cmd;
	public int type;
	public String session;
	public JSONObject obj;

	public EntityFrameJson() {
	}
	public EntityFrameJson(String json)
	{
		try {//{"cmd":143,"type":129,"parameter":{"sn":0,"msg":{"code":"200"}}}
			//JSONArray arr = new JSONArray(json);
			JSONObject jobj = new JSONObject(json);
			this.cmd=jobj.getInt("cmd");
			this.type=jobj.getInt("type");
			this.obj=jobj.getJSONObject("parameter");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
