package com.wfj.pay.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Title: Response.java 
* @Description: 前后端交互类
* @author admin  
 */
public class ResponseDTO<T> implements Serializable{
	
	private static final long serialVersionUID = -4262789607566971273L;
	//成功
	public static final String RESULT_SUCCESS = "success";
	//未登录, 或者roleId获取失败, 典型的处理方式是显示完信息跳转到登录页
	public static final String RESULT_LOGIN = "login";
	//业务规则失败或者业务异常(自己在自己的程序各层抛出的异常), 比如扣款时余额不足
	public static final String RESULT_FAILURE = "failure";
	//表单格式验证失败, 表单业务规则验证失败, 典型的处理方式是显示完信息跳转回表单页
	public static final String RESULT_INPUT = "input";
	//可以预见但是不能处理的异常, 如SQLException, IOException等等
	public static final String RESULT_ERROR = "error";
	
	// 标识变量
	private String result;
	// 数组, 存放业务失败提示
	private List<String> messages;
	// 对象, 存放字段格式错误信息
	private Map<String, String> fieldErrors;
	// 数组, 存放系统错误消息
	private List<String> errors;
	
	private T data;

	public ResponseDTO(){
		messages = new ArrayList<String>(1);
		errors = new ArrayList<String>(1);
		fieldErrors = new HashMap<String, String>();
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<String> getMessages() {
		return messages;
	}
	/**
	 * 设置业务错误信息
	 * @param message
	 */
	public void setMessage(String message) {
		this.messages.clear();
		this.messages.add(message);
	}

	public Map<String, String> getFieldErrors() {
		return fieldErrors;
	}
	/**
	 * 添加字段错误
	 * @param key 
	 * @param value
	 */
	public void addFieldErrors(String key,String value) {
		this.fieldErrors.put(key, value);
	}

	public List<String> getErrors() {
		return errors;
	}
	/**
	 * 设置系统错误信息
	 * @param error
	 */
	public void setError(String error) {
		this.errors.clear();
		this.errors.add(error);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
