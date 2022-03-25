<#list tables as table>
prompt ${table.tableName};
<#assign module=table.tableName?split("_")[0]>
@./${module}/${table.tableName}.sql;
</#list>