package com.wx.response.mapper;

import com.wx.response.entity.WxCloudDisk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huangfuluyu
 */
@Repository
@Component
public interface WxCloudDiskMapper {


    /**
     * 根据关键词查询
     *
     * @param keyWords 关键词
     * @return 资源信息
     */
    @Select("select * from wx_cloud_disk where cloud_key_words = #{keyWords}")
    WxCloudDisk selectByKeyWords(String keyWords);

    /**
     * 根据资源类型查询
     *
     * @param cloudType 资源分类类型
     * @return 5条数据
     */
    @Select("select * from wx_cloud_disk where cloud_type = #{cloudType} order by gmt_create Desc limit 5")
    List<WxCloudDisk> listSelectByDataType(Integer cloudType);

    /**
     * 随机5条数据
     *
     * @return 随机5条数据
     */
    @Select("select * from wx_cloud_disk order by gmt_create limit 5")
    List<WxCloudDisk> listSelectTop5();
}
