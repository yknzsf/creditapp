package com.wanda.creditapp.remote.service;

import com.wanda.creditapp.common.requestmodel.user.CertificationModel;
import com.wanda.creditapp.common.requestmodel.user.LoginModel;
import com.wanda.creditapp.common.requestmodel.user.ResetPwdByPasswordModel;
import com.wanda.creditapp.common.requestmodel.user.ResetPwdByUserPhoneModel;
import com.wanda.creditapp.common.requestmodel.user.UserRegisterModel;
import com.wanda.creditapp.remote.domain.uap.UapResponseModel;

public interface IUapUserManageService {
	
	public UapResponseModel userRegister(UserRegisterModel userRegisterModel);

	public UapResponseModel resetPwdByPhone(ResetPwdByUserPhoneModel resetPwdByUserPhoneModel);

	public UapResponseModel resetPwdByPwd(ResetPwdByPasswordModel resetPwdByPasswordModel);

	public UapResponseModel login(LoginModel loginModel);

	public UapResponseModel userCertificationReq(CertificationModel certificationModel);
	
}
