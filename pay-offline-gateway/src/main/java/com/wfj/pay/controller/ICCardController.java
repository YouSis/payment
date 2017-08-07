package com.wfj.pay.controller;

import com.baidu.disconf.client.usertools.DisconfDataGetter;
import com.wfj.pay.constant.SysConstants;
import com.wfj.pay.dto.DecodeDTO;
import com.wfj.pay.dto.ICCard;
import com.wfj.pay.util.ICCardUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 提供PAD刷IC卡的加密解密工具类
 * 
 * @author ghost 2016-07-29
 *
 */
@Controller
public class ICCardController {
	private static final Logger logger = LoggerFactory
			.getLogger(ICCardController.class);

	@RequestMapping("/ic/check")
	public void icDecode(HttpServletResponse response, @RequestBody ICCard bean)
			throws Exception {
		logger.info("接受IC卡加密解密的参数：" + bean.toString());
		String result = "";
		if (bean != null) {
			String address = DisconfDataGetter.getByFileItem(
					SysConstants.STORE_PROPERTIES, bean.getMdh()).toString();
			logger.info("获取PAD中间件分布式配置地址:{},门店号为:{}", address, bean.getMdh());
			if (address != null && address.length() > 0) {
				result = ICCardUtil.check(address, bean.getCardNo(),
						bean.getCardNo());
			}else{
				result="";
			}
		}
		logger.info("返回IC卡加密解密的结果：" + result);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(result);
	}

	@RequestMapping(value = "/ic/decode", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void custDecode(HttpServletResponse response,
			@RequestBody DecodeDTO dto) throws IOException {
		logger.info("接受IC卡解密的参数：" + dto.toString());
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		String result = ICCardUtil.custDecode(dto.getKey());
		logger.info("返回IC卡解密的结果：" + result);
		response.getWriter().write(result);
	}
}
