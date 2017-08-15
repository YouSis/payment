/**
 * 
 */
package com.wfj.pay.util;

import com.wfj.pay.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wfj.EncodeCard;

import java.io.UnsupportedEncodingException;

/**
 * @author ghost
 * @version 创建时间：2016年6月14日 下午6:29:30
 * @类说明 IC卡加密解密工具类
 */
public class ICCardUtil {
	private static Logger logger = LoggerFactory.getLogger(ICCardUtil.class);
	private static String url = null;
	private static EncodeCard encodeCard = new EncodeCard();

	/**
	 * 获得IC卡的校验结果
	 * 
	 * @param url
	 *            校验IC秘钥的url
	 * @param data
	 *            读取IC卡的字符串
	 * @param mdh
	 *            门店编码
	 * @return 校验结果
	 * @throws UnsupportedEncodingException
	 */
	public static String check(String url, String data, String mdh)
			throws UnsupportedEncodingException {
		ICCardUtil.url = url;
		String ls_a = data.substring(12, 12 + 4);
		String ls_cardno = null;
		String ls_pass = null;
		if (!"8115".equals(ls_a)) {
			return "卡型错误";
		}
		// 截取卡号
		ls_a = data.substring(16, 16 + 16);
		if ("FFFFFFFFFFFFFFFF".equals(ls_a) || "FFFFFF".equals(ls_a)
				|| "FFFFFFFF".equals(ls_a)) {
			return "请先进行卡的预制后再进行卡读写操作";
		}
		if ("FFF".equals(ls_a.substring(0, 3))
				|| "C6A".equals(ls_a.substring(0, 3))) {
			ls_pass = checkPass("N", 13, mdh);
			if ("-1".equals(ls_pass)) {
				return "数据库密码错误13!";
			}
			if (!ls_a.substring(0, 3).equals(ls_pass.substring(0, 3))) {
				return "非本系统卡";
			}
			ls_cardno = ls_a.substring(ls_a.length() - 13);
		} else if ("ABC".equals(ls_a.substring(0, 3))) {// 集团卡
			String ls_klb = "9";
			String ls_it = getVerifyCode(1, "IT");
			if (!"Y".equals(ls_it)) {
				return "同城参数为N,不能使用此卡!";
			}
			ls_cardno = ls_a.substring(ls_a.length() - 13);
			String ls_zbkey = "ABC" + encodeCard.f_card_encode(ls_cardno);
			System.out.println("ls_zkby:" + ls_zbkey + ", 待比较字符串:"
					+ data.substring(48, 48 + 16));
			if (!ls_zbkey.equals(data.substring(48, 48 + 16))) {
				return "读卡信息错误1";// 验卡密码失败
			}
		} else if ("61011".equals(mdh)) {// 成都店分8位卡和6位卡
			if ("00".equals(ls_a.substring(ls_a.length() - 8).substring(0, 2))) {// 成都6位卡
				ls_pass = checkPass("N", 6, mdh);
				if ("-1".equals(ls_pass)) {
					return "数据库密码错误6!";
				}
				if (!ls_a.substring(0, 6).equals(ls_pass.substring(0, 6))) {
					return "非本系统卡";
				}
				ls_cardno = ls_a.substring(ls_a.length() - 6);
			} else {// 成都8位卡
				ls_pass = checkPass("N", 8, mdh);
				if ("-1".equals(ls_pass)) {
					return "数据库密码错误8!";
				}
				if (!ls_a.substring(0, 6).equals(ls_pass.substring(0, 6))) {
					return "非本系统卡";
				}
				ls_cardno = ls_a.substring(ls_a.length() - 8);
			}
		} else {// 其他店6位卡
			ls_pass = checkPass("N", 6, mdh);
			if ("-1".equals(ls_pass)) {
				return "数据库密码错误!";
			}
			if (!ls_a.substring(0, 6).equals(ls_pass.substring(0, 6))) {
				return "非本系统卡";
			}
			ls_cardno = ls_a.substring(ls_a.length() - 6);
		}
		String ls_csc = code(ls_a.substring(ls_a.length() - 6)) + "*"
				+ ls_cardno;
		return ls_csc;
	}

	public static String custDecode(String key) {
		return encodeCard.f_cust_decode(key);
	}

	/**
	 * 调用SO文件进行加密
	 * 
	 * @param substring
	 * @return 返回加密串
	 */
	private static String code(String substring) {
		return encodeCard.f_code(substring);
	}

	/**
	 * 获取验证参数
	 * 
	 * @param isDJ
	 *            是否单机
	 * @param cardLen
	 *            卡号长度
	 * @param mdNo
	 *            门店号码
	 * @return 返回验证参数
	 */
	private static String checkPass(String isDJ, int cardLen, String mdNo) {
		String ls_pass = null;
		String ls_pp = null;
		switch (cardLen) {
		case 6:
			ls_pass = getVerifyCode(2, "pass6");
			break;
		case 8:
			ls_pass = getVerifyCode(2, "pass8");
			break;

		case 13:
			ls_pass = getVerifyCode(2, "pass13");
			break;
		default:
			break;
		}
		if (ls_pass == null || "".equals(ls_pass)) {
			return "-1";
		}

		if (cardLen != 13) {
			switch (mdNo) {
			case "61011":// cd
				if (cardLen == 6) {
					ls_pp = getLsPP(isDJ, "99200408", ls_pass);
				} else {
					ls_pp = getLsPP(isDJ, "99200408", ls_pass);
				}
				break;
			case "61021":
			case "61022":// cq
				ls_pp = getLsPP(isDJ, "20031220", ls_pass);
				break;
			case "26011":
			case "26012":// bt
				ls_pp = getLsPP(isDJ, "02291188", ls_pass);
				break;
			case "26021":// hh
				ls_pp = getLsPP(isDJ, "81619555", ls_pass);
				break;
			case "42011":// wh
				ls_pp = getLsPP(isDJ, "01953615", ls_pass);
				break;
			case "51011":
				ls_pp = getLsPP(isDJ, "87301879", ls_pass);
				break;
			case "81011":
				ls_pp = getLsPP(isDJ, "30529712", ls_pass);
				break;
			case "74011":
				ls_pp = getLsPP(isDJ, "20000915", ls_pass);
				break;
			case "40301":
				ls_pp = getLsPP(isDJ, "19552004", ls_pass);
				break;
			case "40101":
				ls_pp = getLsPP(isDJ, "58637032", ls_pass);
				break;
			case "21012":
				ls_pp = getLsPP(isDJ, "11019891", ls_pass);
				break;
			case "21013":
				ls_pp = getLsPP(isDJ, "1268952169861516", ls_pass);
				break;
			case "21014":
				ls_pp = getLsPP(isDJ, "4015140700", ls_pass);
				break;
			case "21011":
				ls_pp = getLsPP(isDJ, "1968080100", ls_pass);
				break;
			case "24011":
				ls_pp = getLsPP(isDJ, "20090418", ls_pass);
				break;
			case "72011":
				ls_pp = getLsPP(isDJ, "30529712", ls_pass);
				break;
			case "62011":
				ls_pp = getLsPP(isDJ, "65738578", ls_pass);
				break;
			default:
				ls_pp = "-1";
				break;
			}
		} else {
			// 长安增加13位卡卡密C6A
			if ("21011".equals(mdNo)) {
				ls_pp = getLsPP(isDJ, "FFF", ls_pass);
			}
			if ("21013".equals(mdNo)) {
				ls_pp = getLsPP(isDJ, "C6A", ls_pass);
			}
		}
		return ls_pp;
	}

	/**
	 * 远程获取校验码
	 * 
	 * @param code
	 * @param value
	 * @return String
	 */
	private static String getVerifyCode(int code, String value) {
		String jsonStr = "{\"flag\":\"" + code + "\",\"code\":\"" + value
				+ "\"}";
		String result = "";
		try {
			result = HttpClientUtil.sendPostJson(url, jsonStr);
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
		return result;
	}

	/**
	 * 提取的公共方法
	 * 
	 * @param isDJ
	 *            是否单机
	 * @param code
	 *            各门店的code
	 * @param ls_pass
	 *            远程获取的校验码
	 * @return ls_pp
	 */
	private static String getLsPP(String isDJ, String code, String ls_pass) {
		String ls_pp = null;
		if ("Y".equals(isDJ)) {
			ls_pp = code;
		} else {
			if (code.equals(ls_pass)) {
				ls_pp = code;
			} else {
				ls_pp = "-1";
			}
		}
		return ls_pp;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String data = "A2131091FFFF8115ABC9011007232661FFFFFFFFFFD27600ABC774640966068972747975FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF30363334313137FFFFFFFFFFFFFFFFFFFF6101110011795100057839021011FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF3230313630373237313132383138FFFFFFFFFFFFFFFFFFFF";
		data = data.replace(" ", "");
		String result = check(
				"http://10.6.2.167:8081/pos-inner/pad-service/jyICK.htm", data,
				"61011");
		System.out.println("结果：" + result);
	}
}
