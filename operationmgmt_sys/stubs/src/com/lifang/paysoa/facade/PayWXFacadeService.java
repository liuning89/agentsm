package com.lifang.paysoa.facade;

import java.util.List;
import com.lifang.model.Response;
import com.lifang.paysoa.model.DonateWkbRequest;

public interface PayWXFacadeService {
    Response donateWkb(List<DonateWkbRequest> requests);
}
