# SCRM
SCRM产品各模块部署环境
# 模块介绍
/GW-网关服务/
/JB-定时批处理/
/BI-数据服务/
/BI-RP-报表服务/
/MM-会员营销服务/
/MM-BS-忠诚度基础服务/
/MM-CC-卡券营销服务/
/MM-GD-导购营销服务/
/BS-基础服务/
/OS-认证中心/
/CM-公共模块/
/WX-微信模块/
/WX-WECOM-企业微信模块/
/TP-第三方模块/
/CA-活动定时模块/
/DI-数据接入/
/WECOM-企微模块/
/WECOM-GATEWAY-企微网关/
/CS_*/  针对每个租户会有新的功能  *表示每个租户的个性化模块

/SA-SaaS模块/     不进行注册 
/SA-TL 产品工具服务/   SA的子模块
/JD-京东云鼎插件/    不进行注册
/SAAS多租户管理/     不进行注册 
				
代码  微服务	        端口	    Response代码   	    
GW	  网关   服务	    30002   10 0 000-999
BS	  基础   服务	    30003	11 0 000-999
MM	  忠诚度 服务	    30004	12 0 000-999
MM-BS                          1 000-999
MM-CC                          2 000-999
MM-GD                          3 000-999
MM-IDS                         4 000-999   行业档案
OS	  认证中心	    30005	13 000-999
BI	  数据服务	    30006	14 0 000-999
BI-RP                          1 000-999
JB	  批处理模块	    30007	15 000-999
WX    微信模块        30008   16 0 000-999
WX-BS                           1 000-999
WX-WECOM                        2 000-999
DI    数据接入       30009    17  000-999   对外接口
TP    第三方模块      30010   18 000-999  外部平台接口接入
CA    活动定时模块    30011    19 000-999
WECOM 企微模块       30012    20 000-999
WECOM-GATEWAY 企微网关 30013  21 000-999
CS_*  针对每个租户会有新的功能  *表示每个租户的个性化模块

GW/actuator 监控模块 30120  admin pwd    

SA      SaaS模块       20001   201 00-99
SA-TL   产品工具服务 
JD      京东云鼎插件    20004   203 00-99
SAAS_MGT 多租户      20005   204 00-99
TM      阿里聚石塔    20006   206 00-99   

##Swagger模板
@ApiOperation(
    value = "分页获取岗位信息",
    notes = "入参：<br/>" +
    "postCode：岗位编码，<br/>" +
    "postName：岗位名称，<br/>" +
    "displayOrder：显示顺序，<br/>" +
    "出参：<br/>" +
    "id：主键id，<br/>" +
    "postCode：岗位编码，<br/>" +
    "postName：岗位名称，<br/>" +
    "displayOrder：显示顺序，<br/>" +
    "createdDateTime：创建日期时间，<br/>")
    
@ApiImplicitParam(name = "employId", value = "员工id", required = true, dataType = "String")    

@ApiModel(value = "返回结果对象",description = "API请求后返回的统一的结果对象")
public class WebResponse<T> implements Serializable {
    private static final long serialVersionUID = 515730635420960456L;
    @ApiModelProperty(value = "是否成功")
    private Boolean isSuccess;
    @ApiModelProperty(value = "返回代码")
    private long code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "结果集")
    private T result;

## 开发约定
    所有服务命名规则为{version}/{controller}/{action}
    接口命名规范：查询以query开头，更新以modify开头，新增以create开头，删除以remove开头 便于后续aop编程
	数据库、表名称、字段名称都小写，多个英文单词用下划线隔开，表名按模块作为前缀，字段名以_e结尾的是冗余字段，为了便于出数据、数据库时间日期以_date_time结尾   日期以_date结尾   时间以_time结尾
    type类型从1开始计数, 确定只有两个值的0,1表示。
	数据库初始化表 以模块_init_开头
	父子表，(表头table结尾 表行以line结尾)或(表头无table结尾，表行以detail结尾)， 参数表以_parameter结尾
	多个业务表和一张业务表的同字段关联使用ref_,单个业务表和一张业务表的关联使用实际业务字段
	数据库索引命名规则 idx_表名缩写_自定义名称
	每个微服务模块命名规则{module}
	每个微服务模块包含client和service两个子模块,client中的feignclient端根据实际业务需要创建
	每个Action根据实际需要写日志注解
	每个Action API接口通过swagger2注解,通过notes属性维护出入参描述，模板见第三个sheet页
	不同的原子Mapper之间不能互调，Service中可引入多个mapper，业务校验的逻辑写在service层中，简单的入参校验写在controller层中。
	端口8848-30100
	分支说明：开发基础版为Dev,所有开发人员从Dev分支下载，各自开发完成后按Dev-{username}-{jira任务ID}-{年月日日期}格式新建分支，以便后续做合并
	公共字段创建人，创建时间，修改人，修改时间, 组织通过handler统一处理，实体类需要手工加注解
	entity-数据层持久化对象，vo-前端返回后端的数据， dto-后端返回到前端的数据, bo-模块中和业务相关的其他对象
	配置类统一到commons模块，在需要使用的微服务中通过spring.factories进行装配
	每个action返回值都是webresponse, 成功状态都用0表示, 其他状态为5位编码，对应关系见第二个sheet页
	每天第一件事就是拉取最新的dev分支
	业务层不捕捉异常，将异常全部抛出，以便统一管理
    请求方式有三种GET POST DELETE 其中只有POST时使用form-data方式提交，故GET和DELETE不支持requestbody方式, DELETE和GET支持？或者/
    
    minio分布式图片文件服务器
    根bucket下动态创建不同子bucket---根据mpid创建文件夹
    文件名以 功能缩写(3位字母简写)+雪花ID 命名
    
    定时任务命名规则{模块名}_{任务名}    组:任务集名 
    eg："name":"BI_POPULATION","group":"LABEL"     "name":"BI_TRANSACTION","group":"LABEL"
    
    Service层只负责抛出ServiceException，在controller不进行捕捉, 接口头设置throws Exception
    
    后台程序使用feignclient接口不能用tokenuser进行传值
    
    对外接口路径格式：/v1/{品牌简写}/api/{Controller}/{Method} 
    针对特定场景手工设置tokenuser,例：SecurityUtils.setUser("companycode", "orgid"); 注意需在使用完后重置SecurityUtils.reset();
    小程序接口token使用DigestUtils.md5Hex(DigestUtils.md5Hex(custID))
    测试类按模块分
    
    异常发生时邮件提醒：
    try { } 
    catch (Exception e) {
        NDC.push(CommonConstants.MAIL_NOTIFY);
        log.error("reader读取数据发生异常", e);
        NDC.remove();
    }
    前端必须将响应中错误的code弹出
    接口文档地址 https://gw-dev.esurl.cn/doc.html
               https://gw-dev.esurl.cn/swagger-ui.html
## 版本约定
    dev是全量分支，所有分支的起源都是dev
    uat是测试分支，测试环境以uat作为基础--》每个品牌会有自己的分支，如：uat_hj
    master是正式分支，线上环境以master作为基础--》每个品牌会有自己的分支，如：master_hj
    
    开发在接到任务需求按如下步骤进行代码管理：
    1、从master分支拉取代码
    2、需求开发
    3、以feat_日期_开发人员简称(liujc)_叶子需求ID作为分支名进行代码提交
    4、开发合并对应分支到uat进行自测
    5、发布计划确定后新建 基于master的feature/v1.x.x.x作为发布计划版本，并将对应的feat合并入feature
    6、测试验证后将feature/v1.x.x.x合并至master
    7、打tag后发布到uat 做回归测试
    8、发生产
    发版分支命名规则: release-版本号，版本号格式：1.0.0
## 文档命名规则
    系统设计文档命名案例： SD-优惠券-20200115-01.docs    
## Thinking
   1、数据库初始化表需要统一规则
##开发环境
| 模块名称 | 公网域名 | 公网IP地址 | 内网IP地址 |
| ---- | ---- | ---- | ---- |

#add jar to local maven repos
本地库路径： 项目路径+\lib\    
eg: C:\Oliver\IS\connexus\lib\jyaml-1.4.jar

mvn install:install-file -Dfile=本地jar路径 -DgroupId=组ID -DartifactId=工件ID -Dversion=版本号 -Dpackaging=jar
eg: mvn install:install-file -Dfile=C:\Oliver\IS\connexus\lib\jyaml-1.4.jar -DgroupId=org.jyaml -DartifactId=jyaml -Dversion=1.4 -Dpackaging=jar

#add jar to 私服
找管理员，登录 47.103.104.112:8081/nexus，上传本地jar包。

#其他说明
mongodb campaign_analy_source需要增加dataid的索引


 