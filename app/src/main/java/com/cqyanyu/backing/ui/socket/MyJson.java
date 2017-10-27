package com.cqyanyu.backing.ui.socket;

import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.socket.entity.EntityUnits;
import com.cqyanyu.backing.utils.MyDate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import baidumap.LocationInfo;


public class MyJson {

    public static ArrayList<EntityUnits> getUnitsList(String json) {
        ArrayList<EntityUnits> unitsList = new ArrayList<EntityUnits>();
        try {
            JSONArray jarr = new JSONArray(json);
            int length = jarr.length();
            for (int i = 0; i < length; i++) {
                EntityUnits node = new EntityUnits();
                JSONObject jobj = jarr.getJSONObject(i);
                node.setOid((Integer) (jobj.get("oid")));
                node.setPid((Integer) (jobj.get("pid")));
                node.setTypeid((Integer) (jobj.get("typeid")));
                node.setSelevelid((Integer) (jobj.get("selevelid")));
                node.setName((String) (jobj.get("name")));
                node.setChildunitcount(jobj.getInt("childunitcount"));
                node.setChilddevicecount(jobj.getInt("childdevicecount"));
                unitsList.add(node);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return unitsList;
    }

    public static String[] isJsonFrame(byte[] data, int length) {
        String strFrame = "";
        try {
            strFrame = new String(data, 0, length, "GB2312");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String[] frame = null;
        if (strFrame.length() > 0) {
            frame = strFrame.split("\r\n");
        }
        return frame;
    }

    public static byte[] makeJsonFrame(int cmd, int type, JSONObject obj) {
        byte[] bytes = null;
        JSONObject jf = new JSONObject();
        try {
            jf.put("cmd", cmd);
            jf.put("type", type);
            jf.put("parameter", obj);
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            bytes = (jf.toString() + "\r\n").getBytes("GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static JSONObject makePushParameterJsonNode(int messageSN, JSONObject json) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("sn", messageSN);
            obj.put("msg", json);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return obj;
    }

    /*
    * RequestLoginEntity entity = new RequestLoginEntity();
        entity.setType(type);
        entity.setCmd(cmd);
        RequestLoginEntity.ParameterBean parameter = new RequestLoginEntity.ParameterBean();
        parameter.setSn(sn);
        RequestLoginEntity.MsgBean msg = new RequestLoginEntity.MsgBean();
        msg.setLoginname(CommonInfo.getInstance().getUserInfo().getPhone());
        msg.setLoginpwd(CommonInfo.getInstance().getUserInfo().getPassword());
        msg.setUserid(CommonInfo.getInstance().getUserInfo().getUserid());
        msg.setUnitid(CommonInfo.getInstance().getUserInfo().getUnitid());
        msg.setRoleid(CommonInfo.getInstance().getUserInfo().getRoleid());
        msg.setDevicetoken(InfoManger.getInstance().getPushToken());
        msg.setPushtype(InfoManger.getInstance().getPushType());//1.信鸽推送；2.华为推送；3.小米推送；
        msg.setTerminaltype("1");
        parameter.setMsg(msg);
        entity.setParameter(parameter);
        String request = JSON.toJSONString(entity);
        sendMassage(request);
    *
    * */
    public static JSONObject makeLoginJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("loginname", CommonInfo.getInstance().getUserInfo().getPhone());
            obj.put("loginpwd", CommonInfo.getInstance().getUserInfo().getPassword());
            obj.put("unitid", CommonInfo.getInstance().getUserInfo().getUnitid());
            obj.put("userid", CommonInfo.getInstance().getUserInfo().getUserid());
            obj.put("roleid", CommonInfo.getInstance().getUserInfo().getRoleid());
            obj.put("devicetoken", InfoManger.getInstance().getPushToken());
            obj.put("pushtype", 1);
            obj.put("terminaltype", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /*
    * RequestLocationEntity entity = new RequestLocationEntity();
        entity.setType(type);
        entity.setCmd(cmd);
        RequestLocationEntity.ParameterBean parameter = new RequestLocationEntity.ParameterBean();
        parameter.setSn(sn);
        RequestLocationEntity.MsgBean msg = new RequestLocationEntity.MsgBean();
        msg.setUnitid(CommonInfo.getInstance().getUserInfo().getUnitid());
        msg.setLongitude(LocationInfo.getInstance().getMyLongitude());
        msg.setUserid(CommonInfo.getInstance().getUserInfo().getUserid());
        msg.setLatitude(LocationInfo.getInstance().getMyLatitude());
        msg.setReportdate(MyDate.getNowTime());
        msg.setDevicetoken(InfoManger.getInstance().getPushToken());
        parameter.setMsg(msg);
        entity.setParameter(parameter);
        String request = JSON.toJSONString(entity);
        sendMassage(request);*/
    public static JSONObject makeReportJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("userid", CommonInfo.getInstance().getUserInfo().getUserid());
            obj.put("unitid", CommonInfo.getInstance().getUserInfo().getUnitid());
            obj.put("devicetoken", InfoManger.getInstance().getPushToken());
            obj.put("reportdate", MyDate.getNowTime());
            obj.put("longitude", LocationInfo.getInstance().getMyLongitude());
            obj.put("latitude", LocationInfo.getInstance().getMyLatitude());//reportdate
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return obj;
    }
/*
    public static ArrayList<EntityDictionaries> getDictionaryList(String json) {
		ArrayList<EntityDictionaries> unitsList = new ArrayList<EntityDictionaries>();
		try {
			JSONArray jarr = new JSONArray(json);
			int length = jarr.length();
			for (int i = 0; i < length; i++) {
				EntityDictionaries node = new EntityDictionaries();
				JSONObject jobj = jarr.getJSONObject(i);
				node.setOid((Integer) (jobj.get("oid")));
				node.setPid((Integer) (jobj.get("pid")));
				node.setName((String) (jobj.get("name")));
				node.setChildcount(jobj.getInt("childcount"));
				unitsList.add(node);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return unitsList;
	}*/

	/*public static EntityLoginInfo getLoginInfo(String json) {
		EntityLoginInfo node = new EntityLoginInfo();
		try {//{"result":true,"info":"","userid":2,"unitid":2370,"roleid":42,"permission":[]}
			JSONObject obj = new JSONObject(json);
			node.setResult(obj.getBoolean("result"));
			node.setLoginInfo(obj.getString("info"));
			if (node.isResult()) {
				node.setUserId(obj.getInt("userid"));
				node.setUnitId(obj.getInt("unitid"));
				node.setRoleId(obj.getInt("roleid"));
				JSONArray row = obj.getJSONArray("permission");
				ArrayList<EntityPermission> list = new ArrayList<EntityPermission>();
				int length = row.length();
				for (int i = 0; i < length; i++) {
					EntityPermission tnode = new EntityPermission();
					JSONObject jobj = row.getJSONObject(i);
					tnode.setOid(jobj.getInt("oid"));
					tnode.setPid(jobj.getInt("pid"));
					tnode.setUserid(jobj.getInt("userid"));
					tnode.setMenu(jobj.getString("menu"));
					list.add(tnode);
				}
				node.setpList(list);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return node;
	}*/
}