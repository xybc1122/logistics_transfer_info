package web.info.LogisticsInfoJiaCheng.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexOrderdetail;
import web.info.LogisticsInfoJiaCheng.provider.TscJcexOrderdetailProvider;

import java.util.List;

@Mapper
public interface TscJcexOrderdetailMapper {

    /**
     * 新增子菜单数据
     */
    @InsertProvider(type = TscJcexOrderdetailProvider.class, method = "saveOrderdetail")
    int saveTscJcexOrderdetail(@Param("orderdetailList") List<TscJcexOrderdetail> orderdetailList);
}
