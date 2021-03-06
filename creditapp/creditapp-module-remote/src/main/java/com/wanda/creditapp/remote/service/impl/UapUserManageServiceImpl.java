package com.wanda.creditapp.remote.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wanda.creditapp.common.requestmodel.user.CertificationModel;
import com.wanda.creditapp.common.requestmodel.user.LoginModel;
import com.wanda.creditapp.common.requestmodel.user.ResetPwdByPasswordModel;
import com.wanda.creditapp.common.requestmodel.user.ResetPwdByUserPhoneModel;
import com.wanda.creditapp.common.requestmodel.user.UserRegisterModel;
import com.wanda.creditapp.remote.common.UapRemoteConstant;
import com.wanda.creditapp.remote.domain.uap.UapRequestModel;
import com.wanda.creditapp.remote.domain.uap.UapResponseModel;
import com.wanda.creditapp.remote.service.IUapUserManageService;
import com.wanda.creditapp.remote.util.RequestHelper;
import com.wanda.creditapp.remote.util.UapRequestHelper;
import com.wanda.creditapp.remote.util.UapRsaGenerator;

@Service("uapUserManageService")
public class UapUserManageServiceImpl implements IUapUserManageService {

	private static final Logger logger = Logger.getLogger(RequestHelper.class);

	// 由于最初统一账户平台是负责发送验证码的，但是现在改成不发送了，所以它会为每个接入这分配一个超级验证码。
	// 每次需要短信验证码的时候就给传这个。说白了没啥用，但又必输
	@Value("${uapMessageAuthenCode}")
	private String uapMessageAuthenCode;

	@Resource
	private UapRequestHelper uapRequestHelper;

	// 用户注册
	public UapResponseModel userRegister(UserRegisterModel userRegisterModel) {
		// 请求模型
		UapRequestModel uapRequestModel = new UapRequestModel();
		// 封装请求参数
		uapRequestModel.put("mobile", userRegisterModel.getUserPhone());
		uapRequestModel.put("password", UapRsaGenerator.encryptToRSA(userRegisterModel.getUserPassword()));
		uapRequestModel.put("messageAuthenCode", uapMessageAuthenCode);

		UapResponseModel uapResponseModel = uapRequestHelper.callUapPlatform(UapRemoteConstant.API_PATH_ONLINE_REGISTRATION, uapRequestModel);
		logger.info("用户注册的结果：" + uapResponseModel);
		return uapResponseModel;
	}

	// 用户登录
	public UapResponseModel login(LoginModel loginModel) {
		// 请求模型
		UapRequestModel uapRequestModel = new UapRequestModel();
		// 封装请求参数
		uapRequestModel.put("loginName", loginModel.getUserPhone());
		uapRequestModel.put("password", UapRsaGenerator.encryptToRSA(loginModel.getUserPassword())); // 加密
		uapRequestModel.put("messageAuthenCode", uapMessageAuthenCode);

		UapResponseModel uapResponseModel = this.uapRequestHelper.callUapPlatform(UapRemoteConstant.API_PATH_LOGIN, uapRequestModel);
		logger.info("用户登录的结果：" + uapResponseModel);
		return uapResponseModel;
	}

	// 通过短信验证码途径，重置登录密码
	public UapResponseModel resetPwdByPhone(ResetPwdByUserPhoneModel resetPwdByUserPhoneModel) {
		// 请求模型
		UapRequestModel uapRequestModel = new UapRequestModel();
		// 封装请求参数 PWID是必须要给的,uap系统必须参数
		uapRequestModel.put("PWID", resetPwdByUserPhoneModel.getPwid());
		uapRequestModel.put("mobile", resetPwdByUserPhoneModel.getUserPhone());
		uapRequestModel.put("password", UapRsaGenerator.encryptToRSA(resetPwdByUserPhoneModel.getUserPassword()));
		uapRequestModel.put("messageAuthenCode", uapMessageAuthenCode);

		UapResponseModel uapResponseModel = this.uapRequestHelper.callUapPlatform(UapRemoteConstant.API_PATH_RESETPWDBYPHONE, uapRequestModel);
		logger.info("用户通过短信验证码,重置密码的结果：" + uapResponseModel);
		return uapResponseModel;
	}

	// 通过原登录密码,重置登录密码
	public UapResponseModel resetPwdByPwd(ResetPwdByPasswordModel resetPwdByPasswordModel) {
		// 请求模型
		UapRequestModel uapRequestModel = new UapRequestModel();
		// 封装请求参数 PWID是必须要给的,uap系统必须参数
		uapRequestModel.put("PWID", resetPwdByPasswordModel.getPwid());
		uapRequestModel.put("oldPwd", UapRsaGenerator.encryptToRSA(resetPwdByPasswordModel.getOldPwd()));
		uapRequestModel.put("password", UapRsaGenerator.encryptToRSA(resetPwdByPasswordModel.getUserPassword()));
		uapRequestModel.put("messageAuthenCode", uapMessageAuthenCode);
		// ......此处原密码认证失败
		UapResponseModel uapResponseModel = this.uapRequestHelper.callUapPlatform(UapRemoteConstant.API_PATH_RESETPWDBYPASSWORD, uapRequestModel);
		logger.info("用户通过原登录密码,重置密码的结果：" + uapResponseModel);
		return uapResponseModel;
	}

	/*
	 * 实名认证(姓名+身份证)
	 */
	@Override
	public UapResponseModel userCertificationReq(CertificationModel certificationModel) {
		// 请求模型
		UapRequestModel uapRequestModel = new UapRequestModel();
		// 封装请求参数
		uapRequestModel.put("PWID", certificationModel.getUapPwid());
		uapRequestModel.put("name", certificationModel.getName());
		uapRequestModel.put("idNumber", certificationModel.getIdNumber());

		UapResponseModel uapResponseModel = uapRequestHelper.callUapPlatform(UapRemoteConstant.API_PATH_CERTIFICATION_REQ, uapRequestModel);
		logger.info("用户注册实名认证结果：" + uapResponseModel);
		return uapResponseModel;

	}

}
