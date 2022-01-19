package com.wx.response.entity;

import lombok.Data;

/**
 * @author : HuangFu
 * @Description : WxCloudDiskUrl
 * @date : 2022-01-13 10:41
 **/
@Data
public class WxCloudDisk {
    private Long id;

    /**
     * 资源名称
     */
    private String cloudName;

    /**
     * 资源标题
     */
    private String cloudTitle;

    /**
     * 资源类型
     * 资源分类：用于自动回复相似5条数据
     * 1：惊悚
     * 2：动漫
     * 3：漫威
     * 4：获奖
     * 5：R级
     * 6：DC
     */
    private int cloudType;

    /**
     * 关键词
     */
    private String cloudKeyWords;

    /**
     * 云盘链接类型
     * 1：迅雷 2：百度 3：阿里云盘
     */
    private int cloudLinkType;

    /**
     * 云盘链接
     */
    private String cloudLink;

    /**
     * 云盘链接验证码
     */
    private String cloudCode;
}
