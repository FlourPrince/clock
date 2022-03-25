<#list tables as table>;
<#assign module=table.tableName?split("_")[0]>
source /${module}/${table.tableName}.sql;
</#list>