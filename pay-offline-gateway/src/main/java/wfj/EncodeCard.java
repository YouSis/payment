package wfj;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * libencode32.so native方法类
 * @author ghost  2016-07-29
 *
 */
public class EncodeCard {
	static {
		System.loadLibrary("encode32");
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		EncodeCard test = new EncodeCard();
		// 加密
		String a = test.f_cust_encode("2101211111111", 'K', "2101211111112",
				"20110601100756");
		System.out.println(a);
		// 解密
		String b = test
				.f_cust_decode("000475101171014186015223470550125013072880245");
		System.out.println(b);
		// ic卡加密
		String c = test.f_code("123456");
		System.out.println(c);
		// zbkey
		String d = test.f_card_encode("2101211111111");
		System.out.println(d);
	}

	public  String f_cust_decode(String key) {
		String l_time = "";
		String khid = "";
		String kmcode = "";
		char klb = ' ';
		String flag = "";
		if (key.length() == 45) {
			String str = key.substring(3, 17);
			l_time = decode(str, "");
			str = key.substring(17, 30);
			kmcode = decode(str, l_time);
			str = key.substring(30, 43);
			khid = decode(str, l_time);
			klb = (char) (Integer.parseInt(key.substring(43, 45)) + 30);
			flag = "Y";
		} else {
			flag = "N";
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		map.put("code", khid);
		map.put("hyklb", klb);
		map.put("kmcode", kmcode);
		map.put("yyzzno", l_time);
		Gson gson = new Gson();
		return gson.toJson(map);
	}

	public String f_cust_encode(String code, char hyklb, String kmcode,
			String l_time) {
		if (code.equals("") || String.valueOf(hyklb).equals("")
				|| kmcode.equals("") || l_time.equals("")) {
			return "N";
		} else {
			return "000" + encode(l_time, "王府井") + encode(kmcode, l_time)
					+ encode(code, l_time) + String.valueOf((int) hyklb - 30);
		}
	}

	public String f_code(String str) {
		return code(str);
	}

	public String f_card_encode(String code) throws UnsupportedEncodingException {
		String zbkey = "";
		zbkey = encodeick(code, "赵孟刘");
		return zbkey;
	}
	
	private static native String decode(String paramString1, String paramString2);

	private static native String encode(String paramString1, String paramString2);
	
	private static native String encodeick(String paramString1, String paramString2);

	private static native String code(String paramString1);
}
