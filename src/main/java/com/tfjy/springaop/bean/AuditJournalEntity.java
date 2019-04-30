package com.tfjy.springaop.bean;


import com.baomidou.mybatisplus.annotation.TableName;
import com.dmsdbj.itoo.tool.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.*;

import java.io.Serializable;
import javax.persistence.Column;

/**
 * AuditJournal实体
 * auditJournal
 *
 * @author 张明慧
 * @version 1.0.0
 * @since 1.0.0 2019-04-28 16:09:52
 */
@ApiModel(value = "AuditJournalEntity:auditJournal")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tc_audit_journal")
public class AuditJournalEntity extends BaseEntity implements Serializable {

    /**
     * 类名
     */
    @ApiModelProperty(value = "类名")
    @Column(name = "class_name")
    private String className;

    /**
     * 方法名
     */
    @ApiModelProperty(value = "方法名")
    @Column(name = "method_name")
    private String methodName;

    /**
     * 参数
     */
    @ApiModelProperty(value = "参数")
    @Column(name = "parameter")
    private String parameter;

    /**
     * 操作人ID
     */
    @ApiModelProperty(value = "操作人ID")
    @Column(name = "operator_id")
    private String operatorId;

    /**
     * 操作人IP
     */
    @ApiModelProperty(value = "操作人IP")
    @Column(name = "operator_ip")
    private String operatorIp;

    /**
     * 0：正常   1：异常
     */
    @ApiModelProperty(value = "0：正常   1：异常")
    @Column(name = "is_exception")
    private Integer isException;

    /**
     * 运行时间
     */
    @ApiModelProperty(value = "运行时间")
    @Column(name = "function_time")
    private String functionTime;

    /**
     * 项目ID
     */
    @ApiModelProperty(value = "项目ID")
    @Column(name = "project_id")
    private String projectId;

    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    @Column(name = "company_id")
    private String companyId;

}
