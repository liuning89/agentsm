package com.lifang.agentsm.common;

public class MysqlPaginationSql implements PaginationSql {

    @Override
    public String getSql(Pagination pagination, String sql) {
        StringBuffer stringBuffer = new StringBuffer(sql);
        int offset = (pagination.getPageNo()-1)*pagination.getPageSize();
        String realSortField = pagination.getRealSortField();
        if(realSortField != null){
            stringBuffer.append(" order by ").append(realSortField).append("desc".equalsIgnoreCase(pagination.getSortOrder())?" DESC":" ASC");
        }
        stringBuffer.append(" limit ").append(offset).append(",").append(pagination.getPageSize());
        return stringBuffer.toString();
    }

}
