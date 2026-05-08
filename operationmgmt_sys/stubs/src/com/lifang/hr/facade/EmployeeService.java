package com.lifang.hr.facade;

import com.lifang.model.Response;

public interface EmployeeService {
    void initWPHRAccount(String phone, String abbreviation, int id, int createBy);
    Response resetWPHRAccountRight(String franchiseePhone, int operatorId);
    void resetPassword(String franchiseePhone, int operatorId);
    void stopWPByfranchiseeId(int franchiseeId, int operatorId);
    Response recoveryWPByfranchiseeId(int id, int operatorId);
    void updateFranchiseeIdByStoreId(int storeId, int franchiseeId);
    void updateFranchiseeIdByAreaOrgId(int areaOrgId, int franchiseeId);
}
