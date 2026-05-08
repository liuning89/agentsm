package com.lifang.paysoa.facade;

import java.util.List;
import com.lifang.model.Response;
import com.lifang.paysoa.model.request.BaseRequest;
import com.lifang.paysoa.model.vo.GuestBalanceVo;

public interface WalletFacadeService {
    Response<List<GuestBalanceVo>> batchWalletBalance(List<BaseRequest> queryList);
    Response<Integer> sumUserBalance(List<BaseRequest> queryList);
}
