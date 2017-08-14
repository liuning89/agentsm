package com.lifang.agentsm.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import com.lifang.agentsm.utils.ReflectUtil;


@Intercepts({
    @Signature(type=StatementHandler.class,method="prepare",args={Connection.class})/*,
    @Signature(type = Executor.class, method = "query", args = {  
        MappedStatement.class, Object.class, RowBounds.class,  ResultHandler.class })*/
})
public class PaginationInterceptor implements Interceptor {
    private PaginationSql paginationSql;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler rsh = (RoutingStatementHandler) invocation.getTarget();
        StatementHandler sh = (StatementHandler) ReflectUtil.getFieldValue(rsh, "delegate");
        BoundSql boundSql = sh.getBoundSql();
        
        //拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象
        Object object = boundSql.getParameterObject();
        if(object instanceof Pagination){
            Pagination pagination = (Pagination) object;
            MappedStatement ms = (MappedStatement) ReflectUtil.getFieldValue(sh,"mappedStatement");
            Connection con = (Connection) invocation.getArgs()[0];
            //写在mapper.xml中的sql语句
            String sql = boundSql.getSql();
            this.setTotal(pagination, ms, con);
            //ReflectUtil.setFieldValue(boundSql, "parameterObject", pagination.getParam());
            ReflectUtil.setFieldValue(boundSql, "sql",paginationSql.getSql(pagination, sql));
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
       String cn =  properties.getProperty("paginationSql");
       try {
            this.paginationSql = (PaginationSql) Class.forName(cn).newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 
     * 功能描述:给当前的参数对象pagination设置总记录数
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2015年2月5日      新建
     * </pre>
     *
     * @param pagination
     * @param ms
     * @param con
     */
    private void setTotal(Pagination pagination,MappedStatement ms,Connection con){
        BoundSql boundSql = ms.getBoundSql(pagination);
        //原sql
        String sql = boundSql.getSql().replaceAll("\r\n|\n|\t", " ");
        String lsql = sql.toLowerCase();
        String countSql = "";
        if(lsql.contains("union")){
        	countSql = "select count(0) from ( "+lsql+" ) total_t";
        }else{
        	Pattern reson = Pattern.compile("\\([^)]*\\)");
        	Matcher remat = reson.matcher(lsql);
        	int i =0;
        	while( remat.find()){
        	    String matcherStr = remat.group();
        	    String replaceStr = remat.group().replaceAll(".","a");
        	    if(matcherStr.lastIndexOf("(") != 0){
        	        replaceStr = "("+replaceStr.substring(1); 
        	    }
        		lsql = lsql.replace(remat.group(),replaceStr);
        		if(matcherStr.lastIndexOf("(") != 0){
        		    remat = reson.matcher(lsql);
        		}
        	}
    		countSql = "select count(0) "+sql.substring(lsql.indexOf("from"));
            Pattern pattern = Pattern.compile("group\\s+by",Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(lsql);
            lsql = matcher.replaceAll("<groupBy>");
            
            String [] sarr = lsql.split("<groupBy>");
            if(sarr.length>1){
                String s = sarr[sarr.length-1];
                if (s.indexOf(")")!=-1){
                    if(!Pattern.matches("\\([^)]+\\)",s)) {
                        countSql = "select count(0) from ( "+countSql+" ) total_t";
                    }
                }else{
                    countSql = "select count(0) from ( "+countSql+" ) total_t";
                }
            }
        }
        List<ParameterMapping> pms = boundSql.getParameterMappings();
        BoundSql countBoundSql = new BoundSql(ms.getConfiguration(), countSql, pms,pagination);
        ParameterHandler ph = new DefaultParameterHandler(ms, pagination, countBoundSql);
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps=con.prepareStatement(countSql);
            ph.setParameters(ps);
            rs = ps.executeQuery();
            if(rs.next()){
                int total = rs.getInt(1);
                pagination.setTotal(total);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try {
                if(rs!=null){
                    rs.close();
                }
                if(ps != null){
                    ps.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    
    public static void main(String[] args) {
    	System.out.println("1234567890".replaceAll(".", "1"));
    	
    	
		Pattern pattern = Pattern.compile("\\((.*)\\)()");
		Matcher matcher = pattern.matcher("select estatename as (fffff),(select a from b) as b from c");
		if(matcher.find()){
			for(int i = 0 ;i<matcher.groupCount();i++){
				System.out.println(matcher.group(i));
				
			}
			
		}
	}
}
