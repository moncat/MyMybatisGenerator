#MyMyBatisGenerator
* this is a generator for mybatis,you can generate the service dao model mapper with the project. 
* BaseDao and BaseService has been provided.
* This project requires spring boot 1.5.3.
* Mysql is required.
* Mysql must have a primary key named "id",the type is BigInt<20> ,and This field must be in the first row. 
* "item_order create_time  create_by update_time update_by del_flg"  Six fields are required.
 
    `item_order` int(10) DEFAULT NULL COMMENT '排序',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
    `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
    `del_flg` tinyint(4) DEFAULT NULL COMMENT '是否删除  1已删除  0未删除',

*  copy "generator.properties, generatorConfig.xml" to your project, update the jdbc connection in "generator.properties", and search "Update next line" in "generatorConfig.xml"  update the three configuration.


## use the command build your project 
    mvn mybatis-generator:generate

##good luck !
