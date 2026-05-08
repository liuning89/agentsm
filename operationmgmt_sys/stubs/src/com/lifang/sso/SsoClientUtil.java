package com.lifang.sso;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.lifang.sso.entity.SsoPositionMenu;
import com.lifang.sso.entity.SsoSystem;
import com.lifang.sso.entity.SsoUser;
import com.lifang.sso.entity.SsoUserPositionOrgRelate;

public class SsoClientUtil {
    public SsoUser getCurrentUser() throws Exception { return null; }
    public List<SsoSystem> getUserSystem() throws Exception { return null; }
    public List<SsoUserPositionOrgRelate> getCurrentOrg(SystemInfo systemInfo) throws Exception { return null; }
    public List<SsoPositionMenu> getSystemMenu(SystemInfo systemInfo) throws Exception { return null; }
    public void LogOut(HttpServletRequest request) throws Exception {}
    public String getDefLogoutUrl() { return ""; }
}
