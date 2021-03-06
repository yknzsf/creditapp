package com.wanda.creditapp.remote.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanda.creditapp.common.constant.ExceptionConstant;
import com.wanda.creditapp.common.constant.ProductConstant;
import com.wanda.creditapp.common.exception.CreditAppException;
import com.wanda.creditapp.common.requestmodel.product.CentralBankReportModel;
import com.wanda.creditapp.common.requestmodel.product.ProductModel;
import com.wanda.creditapp.remote.service.ICallCreditRemoteService;
import com.wanda.creditapp.remote.util.ValidateUtil;

/**
 * 产品P_C_Q015调用服务,根据调用产品P_C_B265获取的request_id获取人行报告详细信息
 * @author xuxiaobin5
 *
 */
@Service(ProductConstant.productPCQ015Service)
public class ProductPCQ015ServiceImpl extends AbstractProductService{
	
	@Autowired
	private ICallCreditRemoteService callCreditRemoteService;
	
	private final String prod_id = "P_C_Q015";
	
	private final String inf_id = "P_C_Q015";

	@Override
	protected String validateBussinessParam(Map<String,String> param) {
		return ValidateUtil.validateProductModel(param, ProductConstant.request_id);
	}

	@Override
	protected Map<String, String> buildSystemParam() {
		// TODO Auto-generated method stub
		Map<String,String> systemParams = new HashMap<String,String>();
		systemParams.put(ProductConstant.prod_id, prod_id);
		systemParams.put(ProductConstant.inf_id, inf_id);
		return systemParams;
	}

	@Override
	protected Map<String, String> buildBussinessParam(ProductModel model) {
		if(!(model instanceof CentralBankReportModel)){
			throw new CreditAppException(ExceptionConstant.parameterTypeMissmatch);
		}
		CentralBankReportModel centralBankReportModel = (CentralBankReportModel)model;
		Map<String,String> param = new HashMap<String,String>();
		param.put(ProductConstant.request_id, centralBankReportModel.getRequest_id());
		param.put(ProductConstant.prod_id, prod_id);
		return param;
	}

	@Override
	protected ICallCreditRemoteService getCallCreditRemoteService() {
		return this.callCreditRemoteService;
	}

}