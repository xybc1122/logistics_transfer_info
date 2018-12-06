package web.info.LogisticsInfoJiaCheng.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import web.info.LogisticsInfoJiaCheng.pojo.TscJcexOrderdetail;

@Mapper
public interface TscJcexOrderdetailMapper {

    /**
     * 新增子菜单数据
     */
    @Insert("INSERT INTO `tsc_jcex_orderdetail`(`wbn_id`,`child_number`,`length`,`width`,`hight`,`weight`)\n" +
            "VALUES (#{wbnId},#{childNumber},#{length},#{width},#{hight},#{weight})")
    int saveTscJcexOrderdetail(TscJcexOrderdetail tscJcexOrderdetail);
}
